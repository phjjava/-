package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.UserDao;
import com.jp.dao.UsercontentDao;
import com.jp.entity.Usercontent;
import com.jp.service.FamousService;
import com.jp.util.StringTools;
import com.jp.util.WebUtil;

@Service
public class FamousServiceImpl implements FamousService {

	private final Logger log_ = LogManager.getLogger(FamousServiceImpl.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private UsercontentDao usercontentDao;

	@Override
	public PageModel<Usercontent> selectContentList(PageModel<Usercontent> pageModel, Usercontent usercontent) {
		try {
			//当前登录人 familyid
			String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			usercontent.setFamilyid(familyid);
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<Usercontent> list = userDao.selectUserContentList(usercontent);
			pageModel.setList(list);
			pageModel.setPageInfo(new PageInfo<Usercontent>(list));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageModel;
	}

	@Override
	public Usercontent get(String userid) {
		return usercontentDao.selectByPrimaryKey(userid);
	}

	@Override
	public int batchDelete(String[] idArray) {
		int num = usercontentDao.batchDelete(idArray);
		if (num >= 1)
			return 1;
		return 0;

	}

	@Override
	public JsonResponse editOrAdd(Usercontent usercontent, String id) {
		Result result = null;
		JsonResponse res = null;
		Integer count = 0;
		String userid = usercontent.getUserid();
		if (StringTools.trimIsEmpty(userid)) {
			result = new Result(MsgConstants.FAMOUS_NO_USERID);
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(usercontent.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数familyid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(usercontent.getSort())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数sort不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(usercontent.getContent())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数content不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			//当前登录人 userid
			String currentUserId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
			if (StringTools.trimIsEmpty(currentUserId)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户非法！");
				res = new JsonResponse(result);
				return res;
			}
			Usercontent searchUTResult = usercontentDao.selectByPrimaryKey(userid);
			usercontent.setUpdateid(currentUserId);
			usercontent.setUpdatetime(new Date());
			if (StringTools.notEmpty(searchUTResult)) {//已有名人录
				if (StringTools.trimNotEmpty(id)) {//删除原有的名人录记录，重新录入
					count = usercontentDao.deleteByPrimaryKey(userid);
					usercontent.setUserid(id);
					usercontent.setCreateid(currentUserId);
					usercontent.setCreatetime(new Date());
					usercontent.setIssee(ConstantUtils.ISSEE_DEFAULT);
					if (count > 0) {
						count = usercontentDao.insert(usercontent);
					}
				} else {
					//修改原有的名人录
					count = usercontentDao.updateByPrimaryKeySelective(usercontent);
				}
			} else {//没有名人录记录，直接新增
				usercontent.setCreateid(currentUserId);
				usercontent.setCreatetime(new Date());
				usercontent.setIssee(ConstantUtils.ISSEE_DEFAULT);
				count = usercontentDao.insert(usercontent);
			}
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[editOrAdd方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

}
