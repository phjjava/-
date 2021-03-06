package com.jp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
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
import com.jp.service.UserContextService;
import com.jp.util.DateUtils;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

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
	private BranchphotoMapper branchphotoDao;
	@Autowired
	private UserContextService userContextService;

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
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			BannerQuery bq = new BannerQuery();
			Criteria createCriteria = bq.createCriteria();
			if (banner.getDeleteflag() != null) {
				createCriteria.andDeleteflagEqualTo(banner.getDeleteflag());
			}
			createCriteria.andFamilyidEqualTo(familyid);
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
		if (StringTools.trimIsEmpty(bannerid)) {
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
		if (StringTools.trimIsEmpty(banner.getBannerid())) {
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
		if (StringTools.trimIsEmpty(banner.getBannername())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannername不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(banner.getBannerdesc())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerdesc不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(banner.getBannerphoneurl())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerphoneurl不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(banner.getBannerweburl())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerweburl不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(banner.getGotype() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数gotype不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(banner.getBannerurl())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerurl不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.trimIsEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.trimIsEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			if (StringTools.notEmpty(banner.getBannerid())) {
				// 修改
				banner.setUpdatetime(new Date());
				banner.setUpdateid(userid);
				if (bannerurlId != null) {
					banner.setBannerurl(bannerurlId);
				}
				count = bdao.updateByPrimaryKeySelective(banner);
			} else {
				// 新增
				if (!StringTools.trimNotEmpty(banner.getFamilyid())) {
					banner.setFamilyid(familyid);
				}
				banner.setDeleteflag(ConstantUtils.DELETE_FALSE);
				banner.setCreateid(userid);
				banner.setUpdateid(userid);
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
		Integer status = 0;
		if (StringTools.trimIsEmpty(bannerids)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerids不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			String[] banneridArray = bannerids.split(",");
			status = bdao.batchDelete(banneridArray);
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

	@Override
	public JsonResponse selectByGoType(String goType) {
		Result result = null;
		JsonResponse res = null;
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.trimIsEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.trimIsEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人所管理的编委会id
		String ebid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_EBID);
		if (StringTools.trimIsEmpty(ebid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数ebid为空!");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(goType)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数goType不能为空!");
			res = new JsonResponse(result);
			return res;
		}
		GoTypeResult goTypeResult = null;
		List<GoTypeResult> goTypeResultList = new ArrayList<GoTypeResult>();
		try {
			Integer type = userContextService.getUserManagers(userid, ebid).get(0).getEbtype();
			List<String> branchIds = userContextService.getBranchIds(familyid, userid, ebid);
			//动态
			if (goType.equals("1")) {

				List<Dynamic> dynamicList = null;
				if (type == 1) {
					DynamicExample ex = new DynamicExample();
					ex.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
					dynamicList = dynamicDao.selectByExample(ex);
				} else {
					if (branchIds.size() > 0) {
						dynamicList = dynamicDao.selectGoType(branchIds);
					}
				}
				if (dynamicList != null) {
					for (int j = 0; j < dynamicList.size(); j++) {
						String dytitle = dynamicList.get(j).getDytitle();
						if (StringTools.trimNotEmpty(dytitle)) {
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
						if (StringTools.trimNotEmpty(albumname)) {
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
						if (StringTools.trimNotEmpty(eventtitle)) {
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
						if (StringTools.trimNotEmpty(username)) {
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
				criteria.andFamilyidEqualTo(familyid);
				introduce.setDeleteflag(0);
				if (StringTools.trimNotEmpty(introduce.getDeleteflag())) {
					criteria.andDeleteflagEqualTo(introduce.getDeleteflag());
				}
				iq.setOrderByClause("sort");
				List<Introduce> list = itdao.selectByExample(iq);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						String introducetitle = list.get(i).getIntroducetitle();
						if (StringTools.trimNotEmpty(introducetitle)) {
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
				if (branchIds.size() > 0) {
					criteria.andBranchidIn(branchIds);
					List<NoticeVO> list = noticedao.selectNoticeMangeList(nq);
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							String noticetitle = list.get(i).getNoticetitle();
							if (StringTools.trimNotEmpty(noticetitle)) {
								goTypeResult = new GoTypeResult();
								goTypeResult.setId(list.get(i).getNoticeid());
								goTypeResult.setName(noticetitle);
								goTypeResultList.add(goTypeResult);
							}
						}
					}
				}
			} else if (goType.equals("7")) {
				List<Branchphoto> list = new ArrayList<>();
				if (type == 1) {
					list = branchphotoDao.selectByFamilyid(familyid);
				} else {
					if (branchIds.size() > 0) {
						list = branchphotoDao.selectByBranch(branchIds);
					}
				}

				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						String description = list.get(i).getDescription();
						if (StringTools.trimNotEmpty(description)) {
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
