package com.jp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.jp.dao.BannerDao;
import com.jp.dao.BranchalbumMapper;
import com.jp.dao.BranchphotoMapper;
import com.jp.dao.DynamicMapper;
import com.jp.dao.EventDao;
import com.jp.dao.IntroduceDao;
import com.jp.dao.NoticeMapper;
import com.jp.dao.UserDao;
import com.jp.entity.Banner;
import com.jp.entity.BannerQuery;
import com.jp.entity.BannerQuery.Criteria;
import com.jp.entity.Branchalbum;
import com.jp.entity.BranchalbumExample;
import com.jp.entity.Branchphoto;
import com.jp.entity.Dynamic;
import com.jp.entity.DynamicExample;
import com.jp.entity.Event;
import com.jp.entity.GoTypeResult;
import com.jp.entity.Introduce;
import com.jp.entity.IntroduceQuery;
import com.jp.entity.Notice;
import com.jp.entity.NoticeExample;
import com.jp.entity.NoticeVO;
import com.jp.entity.Usercontent;
import com.jp.service.BannerService;
import com.jp.util.DateUtils;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class BannerServiceImpl implements BannerService {
	@Autowired
	private BannerDao bdao;
	@Autowired
	private DynamicMapper dynamicDao;
	@Autowired
	private BranchalbumMapper badao;
	@Autowired
	private EventDao edao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private IntroduceDao itdao;
	@Autowired
	private NoticeMapper noticedao;
	@Autowired
	BranchphotoMapper branchphotoDao;

	@Override
	public JsonResponse pageQuery(PageModel<Banner> pageModel, Banner banner) {
		Result result = null;
		JsonResponse res = null;
		if (pageModel.getPageNo() == null || "".equals(pageModel.getPageNo() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageNo不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (pageModel.getPageSize() == null || "".equals(pageModel.getPageSize() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageSize不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			BannerQuery bq = new BannerQuery();
			Criteria createCriteria = bq.createCriteria();
			if (banner.getDeleteflag() != null) {
				createCriteria.andDeleteflagEqualTo(banner.getDeleteflag());
			}
			createCriteria.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
			bq.setOrderByClause("createtime DESC");
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<Banner> list = bdao.selectByExample(bq);
			if (list != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(list);
				res.setCount(new PageInfo<Banner>(list).getTotal());
				return res;
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;

	}

	@Override
	public JsonResponse get(String bannerid) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(bannerid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			Banner banner = bdao.selectByPrimaryKey(bannerid);
			if (banner != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(banner);
				return res;
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse changeStatus(Banner banner) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		if (StringUtils.isBlank(banner.getBannerid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (banner.getDeleteflag() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数deleteflag不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			Integer count = bdao.updateByPrimaryKeySelective(banner);
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
	}

	@Override
	public JsonResponse save(Banner banner, String bannerurlId) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		if (StringUtils.isBlank(banner.getBannername())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannername不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(banner.getBannerdesc())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerdesc不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(banner.getBannerphoneurl())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerphoneurl不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(banner.getBannerweburl())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerweburl不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(banner.getGotype() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数gotype不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(banner.getBannerurl())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerurl不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			if (StringTools.notEmpty(banner.getBannerid())) {
				// 修改
				banner.setUpdatetime(new Date());
				banner.setUpdateid(CurrentUserContext.getCurrentUserId());
				if (bannerurlId != null) {
					banner.setBannerurl(bannerurlId);
				}
				count = bdao.updateByPrimaryKeySelective(banner);
			} else {
				// 新增
				if (!StringTools.trimNotEmpty(banner.getFamilyid())) {
					banner.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				}
				banner.setDeleteflag(ConstantUtils.DELETE_FALSE);
				banner.setCreateid(CurrentUserContext.getCurrentUserId());
				banner.setUpdateid(CurrentUserContext.getCurrentUserId());
				banner.setBannerid(UUIDUtils.getUUID());
				banner.setUpdatetime(new Date());
				banner.setCreatetime(new Date());
				if (bannerurlId != null) {
					banner.setBannerurl(bannerurlId);
				}
				count = bdao.insertSelective(banner);
			}
			if (count > 0) {
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

	@Override
	public JsonResponse batchDelete(String bannerids) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(bannerids)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerids不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			// a,b,c
			String bannerid = bannerids.substring(0, bannerids.length());
			String banneridArray[] = bannerid.split(",");
			bdao.batchDelete(banneridArray);
			result = new Result(MsgConstants.RESUL_SUCCESS);
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

	@Override
	public JsonResponse selectByGoType(String goType) {
		Result result = null;
		JsonResponse res = null;
		GoTypeResult goTypeResult = null;
		List<GoTypeResult> goTypeResultList = new ArrayList<GoTypeResult>();
		try {
			Integer type = CurrentUserContext.getUserContext().getUsermanagers().get(0).getEbtype();
			String familyid = CurrentUserContext.getCurrentFamilyId();
			//动态
			if (goType.equals("1")) {
				List<String> branchIds = CurrentUserContext.getCurrentBranchIds();

				//			if(type == 1){
				//				branchIds.clear();//验证是否是总编委会
				//				Branch branch = new Branch();
				//				branch.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				//				List<Branch> branchList = branchDao.selectBranchList(branch);
				//				if(branchList != null && branchList.size() > 0){
				//					for (int i = 0; i < branchList.size(); i++) {
				//						branchIds.add(branchList.get(i).getBranchid());
				//					}
				//				}
				//			}
				List<Dynamic> dynamicList = null;
				if (branchIds.size() > 0) {
					if (type == 1) {
						DynamicExample ex = new DynamicExample();
						ex.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
						dynamicList = dynamicDao.selectByExample(ex);
					} else {
						dynamicList = dynamicDao.selectGoType(branchIds);
					}

				}
				if (dynamicList != null) {
					for (int j = 0; j < dynamicList.size(); j++) {
						String dytitle = dynamicList.get(j).getDytitle();
						if (StringTools.notEmpty(dytitle)) {
							goTypeResult = new GoTypeResult();
							goTypeResult.setId(dynamicList.get(j).getDyid());
							goTypeResult.setName(dytitle);
							goTypeResultList.add(goTypeResult);
						}
					}
				}
			} else if (goType.equals("2")) {
				BranchalbumExample example = new BranchalbumExample();
				example.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
				List<Branchalbum> list = badao.selectBranchAlbumMangeList(example);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						String albumname = list.get(i).getAlbumname();
						if (StringTools.notEmpty(albumname)) {
							goTypeResult = new GoTypeResult();
							goTypeResult.setId(list.get(i).getAlbumid());
							goTypeResult.setName(albumname);
							goTypeResultList.add(goTypeResult);
						}
					}
				}
			} else if (goType.equals("3")) {
				Event event = new Event();
				event.setFamilyid(familyid);
				List<Event> list = edao.selecteventread(event);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						String eventtitle = list.get(i).getEventtitle();
						if (StringTools.notEmpty(eventtitle)) {
							goTypeResult = new GoTypeResult();
							goTypeResult.setId(list.get(i).getEventid());
							goTypeResult.setName(eventtitle);
							goTypeResultList.add(goTypeResult);
						}
					}
				}
			} else if (goType.equals("4")) {
				Usercontent usercontent = new Usercontent();
				usercontent.setFamilyid(familyid);
				List<Usercontent> list = userDao.selectUserContentList(usercontent);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						String username = list.get(i).getUsername();
						if (StringTools.notEmpty(username)) {
							goTypeResult = new GoTypeResult();
							goTypeResult.setId(list.get(i).getUserid());
							goTypeResult.setName(username);
							goTypeResultList.add(goTypeResult);
						}
					}
				}
			} else if (goType.equals("5")) {
				Introduce introduce = new Introduce();
				IntroduceQuery iq = new IntroduceQuery();
				com.jp.entity.IntroduceQuery.Criteria criteria = iq.createCriteria();
				criteria.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
				introduce.setDeleteflag(0);
				if (StringTools.trimNotEmpty(introduce.getDeleteflag())) {
					criteria.andDeleteflagEqualTo(introduce.getDeleteflag());
				}
				iq.setOrderByClause("sort");
				List<Introduce> list = itdao.selectByExample(iq);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						String introducetitle = list.get(i).getIntroducetitle();
						if (StringTools.notEmpty(introducetitle)) {
							goTypeResult = new GoTypeResult();
							goTypeResult.setId(list.get(i).getIntroduceid());
							goTypeResult.setName(introducetitle);
							goTypeResultList.add(goTypeResult);
						}
					}
				}
			} else if (goType.equals("6")) {
				Notice notice = new Notice();
				NoticeExample nq = new NoticeExample();
				com.jp.entity.NoticeExample.Criteria criteria = nq.createCriteria();
				if (StringTools.trimNotEmpty(notice.getType())) {
					criteria.andTypeEqualTo(notice.getType());
				}
				if (StringTools.trimNotEmpty(notice.getBranchid())) {
					criteria.andBranchidEqualTo(notice.getBranchid());
				}
				if (StringTools.trimNotEmpty(notice.getDeleteflag())) {
					criteria.andDeleteflagEqualTo(notice.getDeleteflag());
				}
				List<String> currentBranchIds = CurrentUserContext.getCurrentBranchIds();
				if (currentBranchIds != null && currentBranchIds.size() > 0) {
					criteria.andBranchidIn(CurrentUserContext.getCurrentBranchIds());
					List<NoticeVO> list = noticedao.selectNoticeMangeList(nq);
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							String noticetitle = list.get(i).getNoticetitle();
							if (StringTools.notEmpty(noticetitle)) {
								goTypeResult = new GoTypeResult();
								goTypeResult.setId(list.get(i).getNoticeid());
								goTypeResult.setName(noticetitle);
								goTypeResultList.add(goTypeResult);
							}
						}
					}
				}
			} else if (goType.equals("7")) {
				List<String> branchids = CurrentUserContext.getCurrentBranchIds();
				if (StringTools.trimNotEmpty(branchids)) {
					List<Branchphoto> list = new ArrayList<>();
					if (type == 1) {
						list = branchphotoDao.selectByFamilyid(familyid);
					} else {
						list = branchphotoDao.selectByBranch(branchids);
					}

					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							String description = list.get(i).getDescription();
							if (StringTools.notEmpty(description)) {
								goTypeResult = new GoTypeResult();
								goTypeResult.setId(list.get(i).getImgurl());
								goTypeResult.setName(description);
								goTypeResult.setAlbumname(list.get(i).getAlbumname());
								goTypeResult.setDate(DateUtils.FormatDate(list.get(i).getCreatetime(), "yyyy-MM-dd"));
								goTypeResultList.add(goTypeResult);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(goTypeResultList);
		return res;
	}

	@Override
	public JsonResponse getBanners(Banner entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
			result = new Result(MsgConstants.FAMILYID_IS_NULL);
			res = new JsonResponse(result);
			return res;
		}
		BannerQuery bannerExample = new BannerQuery();
		bannerExample.or().andFamilyidEqualTo(entity.getFamilyid()).andDeleteflagEqualTo(0);
		List<Banner> banners = bdao.selectByExample(bannerExample);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(banners);
		return res;
	}
}
