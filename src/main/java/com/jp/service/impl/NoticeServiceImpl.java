package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.NoticeMapper;
import com.jp.dao.NoticefileDao;
import com.jp.dao.NoticetopDao;
import com.jp.entity.Notice;
import com.jp.entity.NoticeExample;
import com.jp.entity.NoticeExample.Criteria;
import com.jp.entity.NoticeVO;
import com.jp.entity.Noticefile;
import com.jp.entity.NoticefileQuery;
import com.jp.entity.Noticetop;
import com.jp.entity.NoticetopQuery;
import com.jp.entity.UserManager;
import com.jp.service.NoticeService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeMapper noticedao;
	@Autowired
	private NoticefileDao ntfdao;
	@Autowired
	private NoticetopDao noticetopDao;

	@Override
	public PageModel<NoticeVO> pageQuery(PageModel<NoticeVO> pageModel, Notice notice) throws Exception {
		NoticeExample nq = new NoticeExample();
		Criteria criteria = nq.createCriteria();
		if (StringTools.trimNotEmpty(notice.getType())) {
			criteria.andTypeEqualTo(notice.getType());
		}
		if (StringTools.trimNotEmpty(notice.getBranchid())) {
			criteria.andBranchidEqualTo(notice.getBranchid());
		}
		if (StringTools.trimNotEmpty(notice.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(notice.getDeleteflag());
		}
		List<UserManager> managers = CurrentUserContext.getCurrentUserManager();
		UserManager manager = managers.get(0);

		List<String> currentBranchIds = CurrentUserContext.getCurrentBranchIds();
		if (manager.getEbtype() == 1) {// 验证是否是总编委会主任
			currentBranchIds.add("0");
		}
		if (currentBranchIds != null && currentBranchIds.size() > 0) {
			criteria.andBranchidIn(currentBranchIds);
		} else {
			return pageModel;
		}
		nq.setOrderByClause("createtime DESC");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<NoticeVO> list = noticedao.selectNoticeMangeList(nq);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<NoticeVO>(list));
		return pageModel;
	}

	@Override
	public Notice get(String noticeid) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		NoticeExample nq = new NoticeExample();
		Criteria createCriteria = nq.createCriteria();
		createCriteria.andNoticeidEqualTo(noticeid);
		List<Notice> notice = noticedao.selectByExample(nq);
		if (notice != null && notice.size() > 0) {
			if (notice.get(0).getCreatetime() != null) {
				notice.get(0).setCreatetimeStr(formatter.format(notice.get(0).getCreatetime()));
			}
			return notice.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int changeStatus(Notice notice) {
		int count = noticedao.updateByPrimaryKeySelective(notice);
		if (count == 1) {
			return count;
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
	}

	@Override
	public JsonResponse saveNotice(Notice notice, List<Noticefile> ntlist, String[] ntfids) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		try {
			if (StringTools.trimNotEmpty(notice.getNoticeid())) {
				if (notice.getCreatetimeStr() != null) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					notice.setCreatetime(formatter.parse(notice.getCreatetimeStr()));
				}
				notice.setUpdateid(CurrentUserContext.getCurrentUserId());
				notice.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				if (notice.getNoticetype() == 0) {
					notice.setBranchid("0");
				}
				notice.setUpdatetime(new Date());
				status = noticedao.updateByPrimaryKeySelective(notice);
				for (int i = 0; i < ntlist.size(); i++) {
					ntlist.get(i).setNoticeid(notice.getNoticeid());
				}
				if (ntlist != null && ntlist.size() > 0) {
					status = ntfdao.insertnoticefileSelective(ntlist);
				}
				if (ntfids != null && ntfids.length > 0) {
					status = ntfdao.deletefile(ntfids);
				}
				// 维护修改noticetop表关系
				if (StringTools.trimNotEmpty(notice.getTobranchid())) {
					// 先删除再重新添加
					NoticetopQuery dq = new NoticetopQuery();
					dq.or().andNoticeidEqualTo(notice.getNoticeid());
					status = noticetopDao.deleteByExample(dq);
					saveNoticetop(notice, notice.getNoticeid());
				}
			} else {
				String noticeid = UUIDUtils.getUUID();
				notice.setType(1);
				notice.setNoticeid(noticeid);
				notice.setCreateid(CurrentUserContext.getCurrentUserId());
				notice.setCreatename(CurrentUserContext.getCurrentUserName());
				notice.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				if (notice.getNoticetype() == 0) {
					notice.setBranchid("0");
				}
				Date insertDate = new Date();
				notice.setCreatetime(insertDate);
				notice.setUpdateid(CurrentUserContext.getCurrentUserId());
				notice.setUpdatetime(insertDate);
				notice.setDeleteflag(ConstantUtils.DELETE_FALSE);
				if (notice.getCreatetimeStr() != null) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					notice.setCreatetime(formatter.parse(notice.getCreatetimeStr()));
				}
				status = noticedao.insertSelective(notice);
				for (int i = 0; i < ntlist.size(); i++) {
					ntlist.get(i).setNoticeid(noticeid);
				}
				if (ntlist != null && ntlist.size() > 0) {
					status = ntfdao.insertnoticefileSelective(ntlist);
				}
				// 维护保存dytop表关系
				if (StringTools.trimNotEmpty(notice.getTobranchid())) {
					status = saveNoticetop(notice, noticeid);
				}
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 保存通知置顶信息
	 */
	private int saveNoticetop(Notice notice, String noticeid) {
		String branchid = notice.getTobranchid();
		String[] branchids = branchid.split(",");
		int status = 0;
		for (String str : branchids) {
			// 切除分支名字
			String subId = str.substring(0, 32);
			Noticetop dytop = new Noticetop();
			dytop.setBranchid(subId);
			dytop.setNoticeid(noticeid);
			dytop.setId(UUIDUtils.getUUID());
			status = noticetopDao.insertSelective(dytop);
		}
		return status;
	}

	@Override
	public int batchDelete(String[] noticeids) throws Exception {
		return noticedao.batchDelete(noticeids);
	}

	@Override
	public List<Noticefile> selectntfile(NoticefileQuery example) {

		return ntfdao.selectByExample(example);
	}

}
