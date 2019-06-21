package com.jp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.SysFamilyDao;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.dao.UserDao;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.Branch;
import com.jp.entity.BranchAreaCity;
import com.jp.entity.BranchCityBranch;
import com.jp.entity.BranchKey;
import com.jp.entity.BranchQuery;
import com.jp.entity.BranchQuery.Criteria;
import com.jp.entity.BranchValidArea;
import com.jp.entity.GenUser;
import com.jp.entity.GenUserOther;
import com.jp.entity.GenUserVO;
import com.jp.entity.SysFamily;
import com.jp.entity.SysVersionPrivilege;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.entity.UserQuery;
import com.jp.service.BranchService;
import com.jp.util.UUIDUtils;

@Service
public class BranchServiceImpl implements BranchService {

	private final Logger log_ = LogManager.getLogger(BranchServiceImpl.class);

	@Autowired
	private BranchDao branchDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@Autowired
	private SysFamilyDao sysFamilyDao;
	@Autowired
	private SysVersionPrivilegeMapper sysVersionPrivilegeMapper;

	/**
	 * 从起始人按父子关系，递归更新分支用户（包括配偶）的分支属性
	 * 
	 * @param userid
	 */
	private void updateUserBranch(String userid, String branchid, String branchname) {
		// 获取直系起始用户
		User pUser = userDao.selectByPrimaryKey(userid);
		if (pUser != null && !"".equals(pUser.getUserid())) {
			pUser.setBranchid(branchid);
			pUser.setBranchname(branchname);
			userDao.updateByPrimaryKey(pUser);
			if (pUser.getMateid() != null || !"".equals(pUser.getMateid())) {
				User pUserMate = userDao.selectByPrimaryKey(pUser.getMateid());
				if (pUserMate != null) {
					pUserMate.setBranchid(branchid);
					pUserMate.setBranchname(branchname);
					userDao.updateByPrimaryKey(pUserMate);
				}
			}
			// 获取孩子节点
			UserQuery userQuery = new UserQuery();
			userQuery.or().andPidEqualTo(pUser.getUserid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
			List<User> children = userDao.selectByExample(userQuery);
			if (children.size() == 0)
				return;
			for (User user : children) {
				user.setBranchid(branchid);
				user.setBranchname(branchname);
				userDao.updateByPrimaryKey(user);
				updateUserBranch(user.getUserid(), branchid, branchname);
			}
		}

	}

	@Override
	public PageModel<Branch> pageQuery(PageModel<Branch> pageModel, Branch branch) throws Exception {
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Branch> list = branchDao.selectBranchListByFamilyAndUserid(CurrentUserContext.getCurrentFamilyId(), null,
				branch.getBranchname());
		UserQuery ex = new UserQuery();
		for (Branch b : list) {
			ex.clear();
			ex.or().andBranchidEqualTo(b.getBranchid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			int num = userDao.countByExample(ex);
			b.setUsercount(num);
		}
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Branch>(list));
		return pageModel;
	}

	@Override
	public int insert(Branch branch) throws Exception {
		// 查询家族，获取家族使用的版本
		SysFamily sysFamily = sysFamilyDao.selectByPrimaryKey(branch.getFamilyid());
		// 查询家族版本特权，获取家族可创建的分支数量
		SysVersionPrivilege sysVersionPrivilege = sysVersionPrivilegeMapper.selectVersionValue(sysFamily.getVersion(),
				ConstantUtils.VERSION_BRANCH);
		// 查询家族现在已创建的分支数量
		List<String> branchids = branchDao.selectByFamilyid(branch.getFamilyid());
		if ("1".equals(sysVersionPrivilege.getPrivilegevalue())) {
			// 普通版只能免费创建分支1个
			if (branchids.size() >= 1) {
				return -1;// 暂时返回-1，表示不能创建分支了
			}
		}
		if ("5".equals(sysVersionPrivilege.getPrivilegevalue())) {
			// 旗舰版只能免费创建分支5个
			if (branchids.size() >= 5) {
				return -1;// 暂时返回-1，表示不能创建分支了
			}
		}
		int count = branchDao.insertSelective(branch);
		updateUserBranch(branch.getBeginuserid(), branch.getBranchid(), branch.getBranchname());
		return count;
	}

	@Override
	public Branch get(String branchid) throws Exception {

		BranchQuery bq = new BranchQuery();
		Criteria createCriteria = bq.createCriteria();

		createCriteria.andBranchidEqualTo(branchid);

		List<Branch> branch = branchDao.selectByExample(bq);
		return branch.get(0);
	}

	@Override
	public PageModel<Branch> initBranch(PageModel<Branch> pageModel, Branch branch) throws Exception {
		List<Branch> list = branchDao.selectBranchList(branch);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Branch>(list));
		return pageModel;
	}

	@Override
	public int update(Branch branch) throws Exception {
		updateUserBranch(branch.getBeginuserid(), branch.getBranchid(), branch.getBranchname());
		return branchDao.updateByPrimaryKeySelective(branch);
	}

	@Override
	public List<Branch> selectBranchListByFamilyAndUserid(String familyid, String userid) throws Exception {
		// List<Branch> branchs =null;
		List<Branch> rtnlist = new ArrayList<Branch>();
		UserManagerExample example = new UserManagerExample();
		example.or().andUseridEqualTo(userid);
		example.setOrderByClause("ebtype desc,ismanager desc");
		List<UserManager> managers = userManagerMapper.selectByExample(example);
		// List<String> managerids =new ArrayList<String>();
		for (UserManager manager : managers) {
			// branchs = new ArrayList<Branch>();
			// 总编委会查看全部
			if (manager.getEbtype() == 1) {
				rtnlist = branchDao.selectBranchListByFamilyAndUserid(familyid, null, null);
				break;
			}
			rtnlist = branchDao.getBranchsByFamilyAndUserid(familyid, userid, null);
			break;
		}
		// List<Branch> branchs =
		// branchDao.selectBranchListByFamilyAndManagerids(familyid, managerids);
		// branchDao.selectBranchListByFamilyAndUserid(familyid, userid);
		return rtnlist;
	}

	@Override
	public int changeStatus(Branch branch) throws Exception {
		return branchDao.updateByBranchidSelective(branch);
	}

	@Override
	public boolean validateBranchname(Branch branch) throws Exception {
		String result = "";
		int count = branchDao.validateBranchname(branch);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public PageModel<Branch> selectBranchListByFamilyAndUserid(PageModel<Branch> pageModel, Branch branch)
			throws Exception {
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Branch> list = branchDao.getBranchsByFamilyAndUserid(CurrentUserContext.getCurrentFamilyId(),
				CurrentUserContext.getCurrentUserId(), branch.getBranchname());
		UserQuery ex = new UserQuery();
		for (Branch b : list) {
			ex.clear();
			ex.or().andBranchidEqualTo(b.getBranchid());
			int num = userDao.countByExample(ex);
			b.setUsercount(num);
		}
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Branch>(list));
		return pageModel;
	}

	/**
	 * 以下方法用于api
	 */

	@Override
	public JsonResponse getAllBranch(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			BranchQuery branchExample = new BranchQuery();
			branchExample.or().andStatusEqualTo(0).andFamilyidEqualTo(entity.getFamilyid());
			branchExample.setOrderByClause("sort asc");
			if (entity.getStart() != null && entity.getCount() != null) {
				branchExample.setPageSize(entity.getCount().intValue());
				branchExample.setStartRow(entity.getStart().intValue());
			}
			List<Branch> branchs = branchDao.selectByExample(branchExample);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchs);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getBranchPersons(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if ("".equals(entity.getBranchid()) || entity.getBranchid() == null) {
				result = new Result(MsgConstants.BRANCHID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("familyid", entity.getFamilyid());
			map.put("branchid", entity.getBranchid());
			List<User> users = userDao.selectBranchUsers(map);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(users);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getBranchVaildArea(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			List<BranchValidArea> branchValidAreas = branchDao.selectValidArea(entity.getFamilyid());
			if (branchValidAreas.size() == 0) {
				result = new Result(MsgConstants.AREA_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchValidAreas);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getBranchVaildAreaAndCity(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			List<BranchValidArea> branchValidAreas = branchDao.selectValidArea(entity.getFamilyid());
			List<BranchAreaCity> branchAreaCities = new ArrayList<BranchAreaCity>();
			for (BranchValidArea branchValidArea : branchValidAreas) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("familyid", entity.getFamilyid());
				map.put("areacode", branchValidArea.getAreacode());
				List<BranchValidArea> branchValidAreas2 = branchDao.selectValidCity(map);
				BranchAreaCity branchAreaCity = new BranchAreaCity();
				branchAreaCity.setAreacode(branchValidArea.getAreacode());
				branchAreaCity.setAreaname(branchValidArea.getAreaname());
				branchAreaCity.setCitys(branchValidAreas2);
				branchAreaCities.add(branchAreaCity);
			}
			if (branchAreaCities.size() == 0) {
				result = new Result(MsgConstants.AREA_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchAreaCities);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getBranchVaildXQAndBranch(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getCitycode() == null || "".equals(entity.getCitycode())) {
				result = new Result(MsgConstants.CITYCODE_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("familyid", entity.getFamilyid());
			map.put("citycode", entity.getCitycode());
			List<BranchValidArea> branchValidAreas = branchDao.selectValidXQ(map);
			if (branchValidAreas.size() == 0) {
				result = new Result(MsgConstants.AREA_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			List<BranchCityBranch> branchCityBranchs = new ArrayList<BranchCityBranch>();
			for (BranchValidArea branchValidArea : branchValidAreas) {

				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("familyid", entity.getFamilyid());
				map2.put("xcode", branchValidArea.getAreacode());
				List<Branch> branchs = branchDao.selectBranchByXQ(map2);
				BranchCityBranch branchCityBranch = new BranchCityBranch();
				branchCityBranch.setAreacode(branchValidArea.getAreacode());
				branchCityBranch.setAreaname(branchValidArea.getAreaname());
				branchCityBranch.setBranchs(branchs);
				branchCityBranchs.add(branchCityBranch);
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchCityBranchs);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getGenListOnlyExtMod(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getParentid() == null || "".equals(entity.getParentid())) {
				result = new Result(MsgConstants.USERID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			User user = userDao.selectByPrimaryKey(entity.getParentid());
			User genuser = getUserUpto5Mod(user);

			return getGenListOnlyExt_Mod(genuser);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getBranchByCitycode(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getCitycode() == null || "".equals(entity.getCitycode())) {
				result = new Result(MsgConstants.CITYCODE_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("familyid", entity.getFamilyid());
			map.put("citycode", entity.getCitycode());
			List<Branch> branchs = branchDao.selectBranchByCitycode(map);
			if (branchs.size() == 0) {
				result = new Result(MsgConstants.BRANCHS_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchs);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getBranchVaildCity(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getAreacode() == null || "".equals(entity.getAreacode())) {
				result = new Result(MsgConstants.AREACODE_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("familyid", entity.getFamilyid());
			map.put("areacode", entity.getAreacode());
			List<BranchValidArea> branchValidAreas = branchDao.selectValidCity(map);
			if (branchValidAreas.size() == 0) {
				result = new Result(MsgConstants.AREA_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchValidAreas);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getBranchVaildXQ(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getCitycode() == null || "".equals(entity.getCitycode())) {
				result = new Result(MsgConstants.CITYCODE_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("familyid", entity.getFamilyid());
			map.put("citycode", entity.getCitycode());
			List<BranchValidArea> branchValidAreas = branchDao.selectValidXQ(map);
			if (branchValidAreas.size() == 0) {
				result = new Result(MsgConstants.AREA_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchValidAreas);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getBranchOfXQ(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getXcode() == null || "".equals(entity.getXcode())) {
				result = new Result(MsgConstants.XCODE_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("familyid", entity.getFamilyid());
			map.put("xcode", entity.getXcode());
			List<Branch> branchs = branchDao.selectBranchByXQ(map);
			if (branchs.size() == 0) {
				result = new Result(MsgConstants.BRANCHS_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchs);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getGenList(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getBranchid()) || entity.getBranchid() == null) {
				result = new Result(MsgConstants.BRANCHID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getParentid()) || entity.getParentid() == null) {
				result = new Result(MsgConstants.USERID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			BranchKey branchKey = new BranchKey();
			branchKey.setFamilyid(entity.getFamilyid());
			branchKey.setBranchid(entity.getBranchid());
			Branch branch = branchDao.selectByPrimaryKey(branchKey);
			if (branch.getBeginuserid() == null || "".equals(branch.getBeginuserid())) {
				result = new Result(MsgConstants.BEGINUSERID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			// 获取起始人
			UserQuery userExample = new UserQuery();
			userExample.or().andUseridEqualTo(branch.getBeginuserid()).andStatusEqualTo(0).andDeleteflagEqualTo(0);
			List<User> users = userDao.selectByExample(userExample);
			if (users.size() == 0) {
				result = new Result(MsgConstants.USERS_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			User mate_user = new User();
			User gen_user = new User();
			gen_user = users.get(0);
			// 获取配偶
			if (gen_user.getMateid() == null || "".equals(gen_user.getMateid())) {
				// 不存在配偶的情况
			} else {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample1);
				if (users2.size() > 0) {
					mate_user = users2.get(0);
				}
			}
			// 初始化起始人实例
			GenUser genUser = new GenUser();
			genUser.setGenlevel(gen_user.getGenlevel());
			genUser.setImgurl(gen_user.getImgurl());
			genUser.setSex(gen_user.getSex());
			genUser.setUserid(gen_user.getUserid());
			genUser.setUsername(gen_user.getUsername());
			genUser.setLivestatus(gen_user.getLivestatus());
			// 初始化配偶实例
			GenUser mateuser = new GenUser();
			mateuser.setGenlevel(mate_user.getGenlevel());
			mateuser.setImgurl(mate_user.getImgurl());
			mateuser.setSex(mate_user.getSex());
			mateuser.setUserid(mate_user.getUserid());
			mateuser.setUsername(mate_user.getUsername());
			genUser.setLivestatus(gen_user.getLivestatus());

			// 初始化起始节点
			GenUserVO genUserVO = new GenUserVO();
			genUserVO.setUser(genUser);
			genUserVO.setMateuser(mateuser);

			if (gen_user.getGenlevel() == null || "".equals(gen_user.getGenlevel())) {
				result = new Result(MsgConstants.GENLEVEL_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			getUserListFromGenUser(genUserVO, gen_user.getGenlevel());
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(genUserVO);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getGenListOnly(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getBranchid()) || entity.getBranchid() == null) {
				result = new Result(MsgConstants.BRANCHID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			BranchKey branchKey = new BranchKey();
			branchKey.setFamilyid(entity.getFamilyid());
			branchKey.setBranchid(entity.getBranchid());
			Branch branch = branchDao.selectByPrimaryKey(branchKey);
			if (branch.getBeginuserid() == null || "".equals(branch.getBeginuserid())) {
				result = new Result(MsgConstants.BEGINUSERID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			// 获取起始人
			UserQuery userExample = new UserQuery();
			userExample.or().andUseridEqualTo(branch.getBeginuserid()).andStatusEqualTo(0).andDeleteflagEqualTo(0);
			List<User> users = userDao.selectByExample(userExample);
			if (users.size() == 0) {
				result = new Result(MsgConstants.USERS_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			User mate_user = new User();
			User gen_user = new User();
			gen_user = users.get(0);

			// 初始化起始人实例
			GenUserOther genUserOther = new GenUserOther();
			genUserOther.setGenlevel(gen_user.getGenlevel());
			genUserOther.setImgurl(gen_user.getImgurl());
			genUserOther.setSex(gen_user.getSex());
			genUserOther.setUserid(gen_user.getUserid());
			genUserOther.setUsername(gen_user.getUsername());
			genUserOther.setPid(gen_user.getPid());

			// 初始化配偶实例
			if (gen_user.getMateid() == null || "".equals(gen_user.getMateid())) {
				// 不存在配偶的情况
			} else {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample1);
				if (users2.size() > 0) {
					mate_user = users2.get(0);
				}
			}

			GenUserOther mateuser = new GenUserOther();
			mateuser.setGenlevel(mate_user.getGenlevel());
			mateuser.setImgurl(mate_user.getImgurl());
			mateuser.setSex(mate_user.getSex());
			mateuser.setUserid(mate_user.getUserid());
			mateuser.setUsername(mate_user.getUsername());
			if (mate_user.getLivestatus() != null)
				mateuser.setLivestatus(Integer.valueOf(mate_user.getLivestatus()));
			mateuser.setLivestatus(mate_user.getLivestatus().intValue());
			genUserOther.setMate(mateuser);
			List<GenUserOther> genUserOthers = new ArrayList<GenUserOther>();
			genUserOthers.add(genUserOther);

			getUserListOnlyFromGenUser(genUserOther, genUserOther.getGenlevel(), genUserOthers);

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(genUserOthers);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getGenListToTop(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			// 获取当前用户向上几代的根节点
			if (entity.getParentid() == null || "".equals(entity.getParentid())) {
				result = new Result(MsgConstants.USERID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if (entity.getStatus() == null || "".equals(entity.getStatus())) {
				result = new Result(MsgConstants.STATUS_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			User user = userDao.selectByPrimaryKey(entity.getParentid());
			User mate_user = new User();
			user.setPid(user.getPid());
			// 获取向上几世的节点实例
			User gen_user = getUserUptoTop(user, user.getGenlevel(), entity.getStatus());
			List<GenUserOther> genUserOthers = new ArrayList<GenUserOther>();
			if (gen_user != null) {
				// 初始化起始人实例
				GenUserOther genUserOther = new GenUserOther();
				if (gen_user.getLivestatus() != null)
					genUserOther.setLivestatus(Integer.valueOf(gen_user.getLivestatus()));
				genUserOther.setGenlevel(gen_user.getGenlevel());
				genUserOther.setImgurl(gen_user.getImgurl());
				genUserOther.setSex(gen_user.getSex());
				genUserOther.setUserid(gen_user.getUserid());
				genUserOther.setUsername(gen_user.getUsername());
				genUserOther.setPid(gen_user.getPid());

				// 初始化配偶实例
				if (gen_user.getMateid() == null || "".equals(gen_user.getMateid())) {
					// 不存在配偶的情况
				} else {
					UserQuery userExample = new UserQuery();
					userExample.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
					List<User> users2 = userDao.selectByExample(userExample);
					if (users2.size() > 0) {
						mate_user = users2.get(0);
					}
				}
				GenUserOther mateuser = new GenUserOther();
				if (mate_user.getLivestatus() != null)
					mateuser.setLivestatus(Integer.valueOf(mate_user.getLivestatus()));
				mateuser.setGenlevel(mate_user.getGenlevel());
				mateuser.setImgurl(mate_user.getImgurl());
				mateuser.setSex(mate_user.getSex());
				mateuser.setUserid(mate_user.getUserid());
				mateuser.setUsername(mate_user.getUsername());
				genUserOther.setMate(mateuser);

				genUserOthers.add(genUserOther);

				getUserListOnlyFromGenUser(genUserOther, genUserOther.getGenlevel(), genUserOthers);
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(genUserOthers);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getGenListOnlyExt(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getBranchid()) || entity.getBranchid() == null) {
				result = new Result(MsgConstants.BRANCHID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if ("".equals(entity.getParentid()) || entity.getParentid() == null) {
				result = new Result(MsgConstants.USERID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}

			BranchKey branchKey = new BranchKey();
			branchKey.setFamilyid(entity.getFamilyid());
			branchKey.setBranchid(entity.getBranchid());
			Branch branch = branchDao.selectByPrimaryKey(branchKey);
			if (branch.getBeginuserid() == null || "".equals(branch.getBeginuserid())) {
				result = new Result(MsgConstants.BEGINUSERID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			// 获取起始人
			UserQuery userExample = new UserQuery();
			userExample.or().andUseridEqualTo(branch.getBeginuserid()).andStatusEqualTo(0).andDeleteflagEqualTo(0);
			List<User> users = userDao.selectByExample(userExample);
			if (users.size() == 0) {
				result = new Result(MsgConstants.USERS_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}

			User gen_user = new User();
			gen_user = users.get(0);
			if (gen_user.getGenlevel() == null || "".equals(gen_user.getGenlevel())) {
				result = new Result(MsgConstants.GENLEVEL_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}

			// 修改加入排行的排序
			Map map = new HashMap<String, String>();
			map.put("genlevel", gen_user.getGenlevel());
			map.put("status", "0");
			map.put("isdirect", "1");
			map.put("branchid", gen_user.getBranchid());
			map.put("deleteflag", "0");
			List<User> users2 = userDao.selectByMap(map);
			List<GenUserOther> genUserOthers = new ArrayList<GenUserOther>();
			// 先增加自己
			// 初始化起始人实例
			GenUserOther genUserSelf = new GenUserOther();
			if (gen_user.getLivestatus() != null)
				genUserSelf.setLivestatus(Integer.valueOf(gen_user.getLivestatus()));
			genUserSelf.setGenlevel(gen_user.getGenlevel());
			genUserSelf.setImgurl(gen_user.getImgurl());
			genUserSelf.setSex(gen_user.getSex());
			genUserSelf.setUserid(gen_user.getUserid());
			genUserSelf.setUsername(gen_user.getUsername());
			genUserSelf.setPid(gen_user.getPid());
			// 初始化配偶实例
			User self_mate = new User();
			if (gen_user.getMateid() == null || "".equals(gen_user.getMateid())) {
				// 不存在配偶的情况
			} else {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users3 = userDao.selectByExample(userExample1);
				if (users3.size() > 0) {
					self_mate = users3.get(0);
				}
			}
			GenUserOther self_mate_user = new GenUserOther();
			if (self_mate.getLivestatus() != null)
				self_mate_user.setLivestatus(Integer.valueOf(self_mate.getLivestatus()));
			self_mate_user.setGenlevel(self_mate.getGenlevel());
			self_mate_user.setImgurl(self_mate.getImgurl());
			self_mate_user.setSex(self_mate.getSex());
			self_mate_user.setUserid(self_mate.getUserid());
			self_mate_user.setUsername(self_mate.getUsername());
			genUserSelf.setMate(self_mate_user);
			genUserOthers.add(genUserSelf);
			// 先增加自己

			for (User user : users2) {
				User mate_user = new User();
				// 初始化起始人实例
				GenUserOther genUserOther = new GenUserOther();
				if (user.getLivestatus() != null)
					genUserOther.setLivestatus(Integer.valueOf(user.getLivestatus()));
				genUserOther.setGenlevel(user.getGenlevel());
				genUserOther.setImgurl(user.getImgurl());
				genUserOther.setSex(user.getSex());
				genUserOther.setUserid(user.getUserid());
				genUserOther.setUsername(user.getUsername());
				genUserOther.setPid(user.getPid());

				// 初始化配偶实例
				if (user.getMateid() == null || "".equals(user.getMateid())) {
					// 不存在配偶的情况
				} else {
					UserQuery userExample1 = new UserQuery();
					userExample1.or().andUseridEqualTo(user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
					List<User> users3 = userDao.selectByExample(userExample1);
					if (users3.size() > 0) {
						mate_user = users3.get(0);
					}
				}
				GenUserOther mateuser = new GenUserOther();
				if (mate_user.getLivestatus() != null)
					mateuser.setLivestatus(Integer.valueOf(mate_user.getLivestatus()));
				mateuser.setGenlevel(mate_user.getGenlevel());
				mateuser.setImgurl(mate_user.getImgurl());
				mateuser.setSex(mate_user.getSex());
				mateuser.setUserid(mate_user.getUserid());
				mateuser.setUsername(mate_user.getUsername());
				genUserOther.setMate(mateuser);
				genUserOthers.add(genUserOther);
			}

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(genUserOthers);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

	/**
	 * 递归从起始人查询世系表
	 * 
	 * @param entity:起始人
	 * @param level：递归的层级
	 * @return
	 */
	public void getUserListFromGenUser(GenUserVO entity, int genlevel) {
		// 查询孩子列表
		String userid = entity.getUser().getUserid();
		UserQuery userExample = new UserQuery();
		userExample.or().andPidEqualTo(userid).andDeleteflagEqualTo(0).andStatusEqualTo(0);
		List<User> users = userDao.selectByExample(userExample);
		List<GenUserVO> genUserVOs = new ArrayList<GenUserVO>();
		for (User user : users) {
			// 获取孩子配偶实例
			User mateuser = null;
			// 获取配偶
			if (user.getMateid() == null || "".equals(user.getMateid())) {
				// 不存在配偶的情况
			} else {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample1);
				if (users2.size() > 0) {
					mateuser = users2.get(0);
				}
			}
			// 初始孩子实例
			GenUser gen_User = new GenUser();
			gen_User.setGenlevel(user.getGenlevel());
			gen_User.setImgurl(user.getImgurl());
			gen_User.setSex(user.getSex());
			gen_User.setUserid(user.getUserid());
			gen_User.setUsername(user.getUsername());
			gen_User.setLivestatus(user.getLivestatus());
			// 初始孩子配偶实例
			GenUser mate_user = new GenUser();
			if (mateuser != null) {
				mate_user.setGenlevel(mateuser.getGenlevel());
				mate_user.setImgurl(mateuser.getImgurl());
				mate_user.setSex(mateuser.getSex());
				mate_user.setUserid(mateuser.getUserid());
				mate_user.setUsername(mateuser.getUsername());
				mate_user.setLivestatus(mateuser.getLivestatus());
			}
			GenUserVO genUserVO = new GenUserVO();
			genUserVO.setUser(gen_User);
			genUserVO.setMateuser(mate_user);
			genUserVOs.add(genUserVO);
			entity.setChildren(genUserVOs);
			getUserListFromGenUser(genUserVO, genlevel);
		}
	}

	public void getUserListOnlyFromGenUser(GenUserOther entity, int genlevel, List<GenUserOther> genUserOthers) {
		// 查询孩子列表
		String userid = entity.getUserid();
		Map map = new HashMap<String, String>();
		map.put("status", "0");
		map.put("pid", userid);
		map.put("deleteflag", "0");
		List<User> users = userDao.selectByMapOrder(map);

		for (User user : users) {
			// 获取孩子配偶实例
			User mateuser = new User();
			// 获取配偶
			if (user.getMateid() == null || "".equals(user.getMateid())) {
				// 不存在配偶的情况
			} else {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample1);
				if (users2.size() > 0) {
					mateuser = users2.get(0);
				}
			}
			// 初始孩子实例
			GenUserOther gen_UserOther = new GenUserOther();
			if (user.getLivestatus() != null)
				gen_UserOther.setLivestatus(Integer.valueOf(user.getLivestatus()));
			gen_UserOther.setGenlevel(user.getGenlevel());
			gen_UserOther.setImgurl(user.getImgurl());
			gen_UserOther.setSex(user.getSex());
			gen_UserOther.setUserid(user.getUserid());
			gen_UserOther.setUsername(user.getUsername());
			gen_UserOther.setPid(user.getPid());

			// 初始孩子配偶实例
			GenUserOther mate_user_other = new GenUserOther();
			if (mateuser != null) {
				if (mateuser.getLivestatus() != null)
					mate_user_other.setLivestatus(Integer.valueOf(mateuser.getLivestatus()));
				mate_user_other.setGenlevel(mateuser.getGenlevel());
				mate_user_other.setImgurl(mateuser.getImgurl());
				mate_user_other.setSex(mateuser.getSex());
				mate_user_other.setUserid(mateuser.getUserid());
				mate_user_other.setUsername(mateuser.getUsername());
			}
			gen_UserOther.setMate(mate_user_other);
			genUserOthers.add(gen_UserOther);
			getUserListOnlyFromGenUser(gen_UserOther, genlevel, genUserOthers);
		}
	}

	/**
	 * 获取当前用户向上几代的根节点：如果超出指定几代查不到则返回最顶级实例。
	 * 
	 * @author 李鹏 17-04-07
	 * @param entity：当前用户实例
	 * @param iUserLevel
	 *            ：当前用户的世代
	 * @param iLevel：向上几代
	 * @return
	 */
	public User getUserUptoTop(User entity, int iUserLevel, int iLevel) {
		User pUser = new User();
		// 父用户
		if (entity.getPid() == null || "".equals(entity.getPid()))
			return entity;
		else {
			UserQuery userExample = new UserQuery();
			userExample.or().andDeleteflagEqualTo(0).andStatusEqualTo(0).andIsdirectEqualTo(1)
					.andUseridEqualTo(entity.getPid());
			List<User> users = userDao.selectByExample(userExample);
			if (users.size() > 0) {
				pUser = users.get(0);
				if (Math.abs(pUser.getGenlevel() - iUserLevel) >= iLevel)
					return pUser;
				else
					pUser = getUserUptoTop(pUser, iUserLevel, iLevel);
			} else
				return entity;
		}
		return pUser;
	}

	/**
	 * 获取当前用户向上被5整除代数，直到被5整除的世系节点截止
	 * 
	 * @param 要查询的用户节点
	 * @return
	 */
	public User getUserUpto5Mod(User entity) {
		// 没有世系属性
		if (entity.getGenlevel() == null || "".equals(entity.getGenlevel())
				|| entity.getPid() == null | "".equals(entity.getPid()) || (entity.getGenlevel() - 1) % 5 == 0)
			return entity;
		// 世系不能被5整除
		else {
			// 继续向上查找节点
			System.out.println(entity.getPid());
			if (entity.getPid() != null || !"".equals(entity.getPid())) {
				UserQuery userExample = new UserQuery();
				userExample.or().andDeleteflagEqualTo(0).andStatusEqualTo(0).andUseridEqualTo(entity.getPid());
				List<User> users = userDao.selectByExample(userExample);
				// 有父节点，继续递归
				if (users.size() > 0) {
					User user = users.get(0);
					return getUserUpto5Mod(user);
				}
				// 没有父节点，返回自己
				return entity;
			}
			// 不存在父节点，返回自己
			else
				return entity;
		}
	}

	public JsonResponse getGenListOnlyExt_Mod(User gen_user) {
		Result result = null;
		JsonResponse res = null;
		List<GenUserOther> genUserOthers = new ArrayList<GenUserOther>();
		if (gen_user != null) {
			// 初始化起始人实例
			GenUserOther genUserOther = new GenUserOther();
			genUserOther.setGenlevel(gen_user.getGenlevel());
			genUserOther.setImgurl(gen_user.getImgurl());
			genUserOther.setSex(gen_user.getSex());
			genUserOther.setUserid(gen_user.getUserid());
			genUserOther.setUsername(gen_user.getUsername());
			genUserOther.setPid(gen_user.getPid());

			User mate_user = new User();
			// 初始化配偶实例
			if (gen_user.getMateid() == null || "".equals(gen_user.getMateid())) {
				// 不存在配偶的情况
			} else {
				UserQuery userExample = new UserQuery();
				userExample.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample);
				if (users2.size() > 0) {
					mate_user = users2.get(0);
				}
			}
			GenUserOther mateuser = new GenUserOther();
			mateuser.setGenlevel(mate_user.getGenlevel());
			mateuser.setImgurl(mate_user.getImgurl());
			mateuser.setSex(mate_user.getSex());
			mateuser.setUserid(mate_user.getUserid());
			mateuser.setUsername(mate_user.getUsername());
			genUserOther.setMate(mateuser);

			List<GenUserOther> genVirtualUsers = new ArrayList<GenUserOther>();
			// 虚构上级部分节点
			if ((genUserOther.getGenlevel() - 1) % 5 != 0) {
				for (int i = 0; i < (genUserOther.getGenlevel() % 5 - 1); i++) {
					GenUserOther user_other = new GenUserOther();
					user_other.setUsername("不详");
					user_other.setUserid(UUIDUtils.getUUID());
					user_other.setGenlevel(genUserOther.getGenlevel() - i - 1);
					if (user_other.getGenlevel() == genUserOther.getGenlevel() - 1)
						genUserOther.setPid(user_other.getUserid());
					user_other.setSex(1);
					genVirtualUsers.add(user_other);
					// genUserOthers.add(user_other);
				}
			}

			// 构造虚拟的父子关系
			for (GenUserOther genUserOther2 : genVirtualUsers) {
				InitVirtual(genUserOther2, genVirtualUsers);
			}
			List<GenUserOther> sortUsers = new ArrayList<GenUserOther>();
			// 倒序排列
			for (int i = 0; i < genVirtualUsers.size(); i++) {
				sortUsers.add(genVirtualUsers.get(genVirtualUsers.size() - 1 - i));
			}
			genUserOthers.addAll(sortUsers);
			genUserOthers.add(genUserOther);
			getUserListOnlyFromGenUserMod(genUserOther, genUserOther.getGenlevel(), genUserOthers);
		}
		// 排序操作
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(genUserOthers);
		return res;
	}

	private void InitVirtual(GenUserOther genUserOther, List<GenUserOther> genVirtualUsers) {
		for (GenUserOther genUserOther2 : genVirtualUsers) {
			if (genUserOther2.getGenlevel() == genUserOther.getGenlevel() - 1)
				genUserOther.setPid(genUserOther2.getUserid());
		}
	}

	public void getUserListOnlyFromGenUserMod(GenUserOther entity, int genlevel, List<GenUserOther> genUserOthers) {
		if (entity.getGenlevel() % 5 == 0)
			return;
		// 查询孩子列表
		String userid = entity.getUserid();
		UserQuery userExample = new UserQuery();
		userExample.or().andPidEqualTo(userid).andDeleteflagEqualTo(0).andStatusEqualTo(0);
		List<User> users = userDao.selectByExample(userExample);
		for (User user : users) {
			// 获取孩子配偶实例
			User mateuser = new User();
			// 获取配偶
			if (user.getMateid() == null || "".equals(user.getMateid())) {
				// 不存在配偶的情况
			} else {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample1);
				if (users2.size() > 0) {
					mateuser = users2.get(0);
				}
			}
			// 初始孩子实例
			GenUserOther gen_UserOther = new GenUserOther();
			gen_UserOther.setGenlevel(user.getGenlevel());
			gen_UserOther.setImgurl(user.getImgurl());
			gen_UserOther.setSex(user.getSex());
			gen_UserOther.setUserid(user.getUserid());
			gen_UserOther.setUsername(user.getUsername());
			gen_UserOther.setPid(user.getPid());

			// 初始孩子配偶实例
			GenUserOther mate_user_other = new GenUserOther();
			if (mateuser != null) {
				mate_user_other.setGenlevel(mateuser.getGenlevel());
				mate_user_other.setImgurl(mateuser.getImgurl());
				mate_user_other.setSex(mateuser.getSex());
				mate_user_other.setUserid(mateuser.getUserid());
				mate_user_other.setUsername(mateuser.getUsername());
			}
			gen_UserOther.setMate(mate_user_other);
			genUserOthers.add(gen_UserOther);
			getUserListOnlyFromGenUserMod(gen_UserOther, genlevel, genUserOthers);
		}
		return;
	}

}
