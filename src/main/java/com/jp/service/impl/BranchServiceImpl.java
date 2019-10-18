package com.jp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.jp.dao.BranchDao;
import com.jp.dao.DynamicMapper;
import com.jp.dao.EditorialBoardMapper;
import com.jp.dao.NoticeMapper;
import com.jp.dao.SysFamilyDao;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.dao.UserDao;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.Branch;
import com.jp.entity.BranchAreaCity;
import com.jp.entity.BranchCityBranch;
import com.jp.entity.BranchKey;
import com.jp.entity.BranchQuery;
import com.jp.entity.BranchValidArea;
import com.jp.entity.Dynamic;
import com.jp.entity.DynamicExample;
import com.jp.entity.GenUser;
import com.jp.entity.GenUserOther;
import com.jp.entity.GenUserVO;
import com.jp.entity.Notice;
import com.jp.entity.NoticeExample;
import com.jp.entity.SysFamily;
import com.jp.entity.SysVersionPrivilege;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserQuery;
import com.jp.service.BranchService;
import com.jp.service.UserContextService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

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
	@Autowired
	private UserContextService userContextService;
	@Autowired
	private EditorialBoardMapper editorialBoardMapper;
	@Autowired
	private DynamicMapper dynamicMapper;
	@Autowired
	private NoticeMapper noticeMapper;

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
	public JsonResponse pageQuery(PageModel<Branch> pageModel, Branch branch) {
		Result result = null;
		JsonResponse res = null;
		try {
			//当前登录人 userid
			String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
			if (StringTools.isEmpty(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户非法！");
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
			//当前登录人所管理的编委会id
			String ebid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_EBID);
			if (StringTools.isEmpty(ebid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("header中参数ebid为空!");
				res = new JsonResponse(result);
				return res;
			}
			String code = editorialBoardMapper.selectCodeByEbid(ebid);
			String[] codeList = code.split(",");
			branch.setFamilyid(familyid);
			UserManager manager = userContextService.getUserManagers(userid, ebid).get(0);
			List<Branch> branchList = new ArrayList<>();
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			if (manager.getEbtype() == 1) {
				branchList = branchDao.selectBranchListByFamilyAndUserid(branch.getStatus(), familyid,
						branch.getBranchname());
			} else {
				//branchList = branchDao.getBranchsByFamilyAndUserid(familyid, userid, branch.getBranchname());
				if (codeList.length > 1) {
					branchList = branchDao.getBranchListByFamilyAndCodes(familyid, codeList, branch.getBranchname());
				} else {
					branchList = branchDao.getBranchListByFamilyAndCode(familyid, code, branch.getBranchname());
				}
			}
			UserQuery userQuery = new UserQuery();
			for (Branch b : branchList) {
				userQuery.clear();
				userQuery.or().andBranchidEqualTo(b.getBranchid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE)
						.andStatusEqualTo(ConstantUtils.USER_STATUS_OK);
				int num = userDao.countByExample(userQuery);
				b.setUsercount(num);
			}
			if (branchList != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(branchList);
				res.setCount(new PageInfo<Branch>(branchList).getTotal());
				return res;
			}
		} catch (Exception e) {
			log_.error("[pageQuery方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse save(Branch branch) {
		Result result = null;
		JsonResponse res = null;
		Integer count = 0;
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
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
			String branchid = branch.getBranchid();
			String branchname = branch.getBranchname();
			String beginuserid = branch.getBeginuserid();
			//验证同一家族中分支名称不能相同
			JsonResponse validateRes = validateBranchname(branch);
			if (validateRes.getCode() != 0) {
				return validateRes;
			}
			//检查发起人是否已经发起过其他分支
			JsonResponse checkBeginer = checkBeginer(branch);
			if (checkBeginer.getCode() != 0) {
				return checkBeginer;
			}
			if (StringTools.notEmpty(branchid)) {// 修改
				branch.setUpdateid(userid);
				branch.setUpdatetime(new Date());
				branch.setFamilyid(familyid);
				count = branchDao.updateByPrimaryKeySelective(branch);
				if (count > 0) {
					updateUserBranch(beginuserid, branchid, branchname);
					//同步用户分支名称
					UserQuery uq = new UserQuery();
					uq.or().andBranchidEqualTo(branchid);
					User user = new User();
					user.setBranchname(branchname);
					userDao.updateByExampleSelective(user, uq);
					//同步资讯分支名称
					DynamicExample de = new DynamicExample();
					de.or().andBranchidEqualTo(branchid);
					Dynamic dynamic = new Dynamic();
					dynamic.setBranchname(branchname);
					dynamicMapper.updateByExampleSelective(dynamic, de);
					//同步公告分支名称
					NoticeExample ne = new NoticeExample();
					ne.or().andBranchidEqualTo(branchid);
					Notice notice = new Notice();
					notice.setBranchname(branchname);
					noticeMapper.updateByExampleSelective(notice, ne);
				}
			} else {// 新增
				boolean flag = checkFamilyBranchNumber(1, familyid);
				if (flag) {
					branch.setStatus(0);// 使用中
					branch.setBranchid(UUIDUtils.getUUID());
					branch.setFamilyid(familyid);
					branch.setCreateid(userid);
					count = branchDao.insertSelective(branch);
					if (count > 0) {
						updateUserBranch(beginuserid, branch.getBranchid(), branch.getBranchname());
					}
				} else {
					result = new Result(MsgConstants.BRANCH_SAVE_OUTMAX);
					res = new JsonResponse(result);
					return res;
				}
			}
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 检查新增分支是否超过了家族版本限制
	 * @param count  新增分支的数量
	 * @param familyid 家族id
	 * @return
	 */
	public boolean checkFamilyBranchNumber(int count, String familyid) {
		Integer branchCount = 0; // 最多容纳家族分支数量
		// 查询家族，获取家族使用的版本
		SysFamily sysFamily = sysFamilyDao.selectByPrimaryKey(familyid);
		// 查询家族版本特权，获取家族可创建的分支数量
		SysVersionPrivilege sysVersionPrivilege = sysVersionPrivilegeMapper.selectVersionValue(sysFamily.getVersion(),
				ConstantUtils.VERSION_BRANCH);
		if (sysVersionPrivilege != null && sysVersionPrivilege.getPrivilegevalue() != null) {
			if (sysVersionPrivilege.getPrivilegevalue().equals(ConstantUtils.VERSION_UNLIMITED)) {
				// 钻石豪华版容纳家族人数不限
				return true;
			}
			branchCount = Integer.valueOf(sysVersionPrivilege.getPrivilegevalue());
		}
		// 查询家族现在已创建的分支数量
		int haveBranchCount = branchDao.selectByFamilyid(familyid);
		if (branchCount - haveBranchCount - count >= 0) {
			return true;
		}
		return false;
	}

	@Override
	public JsonResponse get(String branchid) {
		Result result = null;
		JsonResponse res = null;
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			BranchKey key = new BranchKey();
			key.setBranchid(branchid);
			key.setFamilyid(familyid);
			Branch branch = branchDao.selectByPrimaryKey(key);
			// 根据beginuserid获取世系信息
			User user = userDao.selectByPrimaryKey(branch.getBeginuserid());
			// 增加返回世系信息
			if (user != null) {
				branch.setGenlevel(user.getGenlevel() + "世");
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branch);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
	}

	@Override
	public JsonResponse initBranch(PageModel<Branch> pageModel, Branch branch) {
		Result result = null;
		JsonResponse res = null;
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
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
		//当前登录人所管理的编委会id
		String ebid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_EBID);
		if (StringTools.isEmpty(ebid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数ebid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			branch.setFamilyid(familyid);
			List<UserManager> userManager = userContextService.getUserManagers(userid, ebid);
			List<String> branchIds = userContextService.getBranchIds(familyid, userid, ebid);
			List<Branch> list = new ArrayList<>();
			for (UserManager um : userManager) {
				if (um.getEbtype() == 1) {
					list = branchDao.selectBranchList(branch, null);
					break;
				} else {
					if (branchIds.size() < 1) {
						result = new Result(MsgConstants.RESUL_FAIL);
						result.setMsg("您的账号当前没有分支");
						res = new JsonResponse(result);
						return res;
					}
					list = branchDao.selectBranchList(branch, branchIds);
					break;
				}
			}
			if (list != null) {
				pageModel.setList(list);
				pageModel.setPageInfo(new PageInfo<Branch>(list));
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(pageModel);
				return res;
			}
		} catch (Exception e) {
			log_.error("[initBranch方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public List<Branch> selectBranchListByFamilyAndUserid(String familyid, String userid, String ebid) {
		//ebid为null时可能会查询多条（一人管理多个编委会），web端需要切换编委会ebid必传（肯定是单条）
		List<UserManager> managers = userContextService.getUserManagers(userid, ebid);
		List<Branch> rtnlist = new ArrayList<Branch>();

		if (StringTools.trimIsEmpty(ebid)) {//app端不需要切换编委会id
			//统计管理地区的编码
			List<String> codeList = new ArrayList<String>();
			for (UserManager um : managers) {
				if (um.getEbtype() == 1) {
					rtnlist = branchDao.selectBranchListByFamily(familyid);
					return rtnlist;
				}
				String areacode = editorialBoardMapper.selectCodeByEbid(um.getEbid());
				String[] codes = areacode.split(",");
				for (String str : codes) {
					if (!codeList.contains(str)) {
						codeList.add(str);
					}
				}
			}
			String[] codeArray = codeList.toArray(new String[codeList.size()]);
			rtnlist = branchDao.getBranchListByFamilyAndCodes(familyid, codeArray, null);
		} else {
			UserManager userManager = managers.get(0);
			String code = editorialBoardMapper.selectCodeByEbid(ebid);
			String[] codeArray = code.split(",");
			// 总编委会查看全部
			if (userManager.getEbtype() == 1) {
				rtnlist = branchDao.selectBranchListByFamily(familyid);
			} else {
				//	rtnlist = branchDao.getBranchsByFamilyAndUserid(familyid, userid, null);
				if (codeArray.length > 1) {
					rtnlist = branchDao.getBranchListByFamilyAndCodes(familyid, codeArray, null);
				} else {
					rtnlist = branchDao.getBranchListByFamilyAndCode(familyid, code, null);
				}
			}
		}
		return rtnlist;
	}

	@Override
	public JsonResponse changeStatus(Branch branch) {
		Result result = null;
		JsonResponse res = null;
		try {
			int status = branchDao.updateByBranchidSelective(branch);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[changeStatus方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 验证分支名称不能重复
	 */
	@Override
	public JsonResponse validateBranchname(Branch branch) {
		Result result = new Result(MsgConstants.RESUL_SUCCESS); // 默认通过验证
		JsonResponse res = null;
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		String branchname = branch.getBranchname().trim();
		if (StringTools.isEmpty(branchname)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数branchname不能为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			BranchQuery example = new BranchQuery();
			example.or().andBranchnameEqualTo(branchname).andFamilyidEqualTo(familyid);
			List<Branch> branchList = branchDao.selectByExample(example);
			if (branchList.size() == 0) {
				res = new JsonResponse(result);
				return res;
			}
			if (StringTools.notEmpty(branch.getBranchid())) {
				for (int i = 0; i < branchList.size(); i++) {
					//编辑时要去除自己本身
					if (!branchList.get(i).getBranchid().equals(branch.getBranchid())) {
						result = new Result(MsgConstants.BRANCH_VALIDATA_NAME);
					}
				}
			} else {
				result = new Result(MsgConstants.BRANCH_VALIDATA_NAME);
			}
		} catch (Exception e) {
			log_.error("[PLMERROR:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
		}
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 验证分支起始人不能重复
	 */
	@Override
	public JsonResponse checkBeginer(Branch branch) {
		Result result = new Result(MsgConstants.RESUL_SUCCESS); // 默认通过验证
		JsonResponse res = null;
		String beginuserid = branch.getBeginuserid();
		if (StringTools.isEmpty(beginuserid)) {
			result = new Result(MsgConstants.BRANCH_NO_BEGINERID);
			res = new JsonResponse(result);
			return res;
		}
		try {
			BranchQuery example = new BranchQuery();
			example.or().andBeginuseridEqualTo(beginuserid);
			List<Branch> branchList = branchDao.selectByExample(example);
			if (branchList.size() == 0) {
				res = new JsonResponse(result);
				return res;
			}
			if (StringTools.trimNotEmpty(branch.getBranchid())) {
				for (int i = 0; i < branchList.size(); i++) {
					//编辑时要去除自己本身
					if (!branchList.get(i).getBranchid().equals(branch.getBranchid())) {
						result = new Result(MsgConstants.BRANCH_CHECK_BEGINER);
					}
				}
			} else {
				result = new Result(MsgConstants.BRANCH_CHECK_BEGINER);
			}
		} catch (Exception e) {
			log_.error("[PLMERROR:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
		}
		res = new JsonResponse(result);
		return res;
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
			String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
			if (StringTools.isEmpty(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户非法！");
				res = new JsonResponse(result);
				return res;
			}
			entity.setParentid(userid);
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
			User gen_user = users.get(0);
			if (gen_user.getGenlevel() == null) {
				result = new Result(MsgConstants.GENLEVEL_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			GenUserVO genUserVO = new GenUserVO();
			// 初始化起始人实例
			GenUser genUser = new GenUser();
			genUser.setGenlevel(gen_user.getGenlevel());
			genUser.setImgurl(gen_user.getImgurl());
			genUser.setSex(gen_user.getSex());
			genUser.setUserid(gen_user.getUserid());
			genUser.setUsername(gen_user.getUsername());
			genUser.setLivestatus(gen_user.getLivestatus());
			genUser.setBrotherpos(gen_user.getBrotherpos());
			genUser.setBranchid(gen_user.getBranchid());
			genUser.setBranchname(gen_user.getBranchname());
			// 初始化起始节点
			genUserVO.setUser(genUser);
			// 获取配偶
			if (StringTools.notEmpty(gen_user.getMateid())) {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample1);
				if (users2.size() > 0) {
					User mate_user = users2.get(0);
					// 初始化配偶实例
					GenUser mateuser = new GenUser();
					mateuser.setGenlevel(gen_user.getGenlevel());
					mateuser.setImgurl(mate_user.getImgurl());
					mateuser.setSex(mate_user.getSex());
					mateuser.setUserid(mate_user.getUserid());
					mateuser.setUsername(mate_user.getUsername());
					mateuser.setLivestatus(mate_user.getLivestatus());
					mateuser.setBranchid(mate_user.getBranchid());
					mateuser.setBranchname(mate_user.getBranchname());
					genUserVO.setMateuser(mateuser);
				}
			}
			getUserListFromGenUser(genUserVO, null);
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
			User gen_user = users.get(0);
			if (gen_user.getGenlevel() == null) {
				result = new Result(MsgConstants.GENLEVEL_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			// 初始化起始人实例
			GenUserOther genUserOther = new GenUserOther();
			genUserOther.setGenlevel(gen_user.getGenlevel());
			genUserOther.setImgurl(gen_user.getImgurl());
			genUserOther.setSex(gen_user.getSex());
			genUserOther.setUserid(gen_user.getUserid());
			genUserOther.setUsername(gen_user.getUsername());
			genUserOther.setPid(gen_user.getPid());
			genUserOther.setBrotherpos(gen_user.getBrotherpos());
			genUserOther.setBranchid(gen_user.getBranchid());
			genUserOther.setBranchname(gen_user.getBranchname());
			// 初始化配偶实例
			if (StringUtils.isNotBlank(gen_user.getMateid())) {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample1);
				if (users2.size() > 0) {
					User mate_user = users2.get(0);
					GenUserOther mateuser = new GenUserOther();
					mateuser.setGenlevel(gen_user.getGenlevel());
					mateuser.setImgurl(mate_user.getImgurl());
					mateuser.setSex(mate_user.getSex());
					mateuser.setUserid(mate_user.getUserid());
					mateuser.setUsername(mate_user.getUsername());
					mateuser.setLivestatus(mate_user.getLivestatus());
					mateuser.setBranchid(mate_user.getBranchid());
					mateuser.setBranchname(mate_user.getBranchname());
					genUserOther.setMate(mateuser);
				}
			}
			List<GenUserOther> genUserOthers = new ArrayList<GenUserOther>();
			genUserOthers.add(genUserOther);

			getUserListCurrentToGenUser(genUserOther, null, genUserOthers);

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
			if (entity.getStatus() == null) {
				result = new Result(MsgConstants.STATUS_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			User user = userDao.selectByPrimaryKey(entity.getParentid());
			// 获取向上几世的节点实例
			User gen_user = getUserUptoTop(user, user.getGenlevel(), entity.getStatus());
			List<GenUserOther> genUserOthers = new ArrayList<GenUserOther>();
			if (gen_user != null) {
				// 初始化起始人实例
				GenUserOther genUserOther = new GenUserOther();
				genUserOther.setLivestatus(gen_user.getLivestatus());
				genUserOther.setGenlevel(gen_user.getGenlevel());
				genUserOther.setImgurl(gen_user.getImgurl());
				genUserOther.setSex(gen_user.getSex());
				genUserOther.setUserid(gen_user.getUserid());
				genUserOther.setUsername(gen_user.getUsername());
				genUserOther.setPid(gen_user.getPid());

				// 初始化配偶实例
				if (StringTools.notEmpty(gen_user.getMateid())) {
					UserQuery userExample = new UserQuery();
					userExample.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
					List<User> users2 = userDao.selectByExample(userExample);
					if (users2.size() > 0) {
						User mate_user = users2.get(0);
						GenUserOther mateuser = new GenUserOther();
						mateuser.setLivestatus(mate_user.getLivestatus());
						mateuser.setGenlevel(gen_user.getGenlevel());
						mateuser.setImgurl(mate_user.getImgurl());
						mateuser.setSex(mate_user.getSex());
						mateuser.setUserid(mate_user.getUserid());
						mateuser.setUsername(mate_user.getUsername());
						genUserOther.setMate(mateuser);
					}
				}

				genUserOthers.add(genUserOther);

				getUserListCurrentToGenUser(genUserOther, entity.getParentid(), genUserOthers);
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
	public JsonResponse getGenListToCount(Branch entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			// 获取当前用户向上几代的根节点
			if (entity.getParentid() == null || "".equals(entity.getParentid())) {
				result = new Result(MsgConstants.USERID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if (entity.getStatus() == null) {
				result = new Result(MsgConstants.STATUS_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			User user = userDao.selectByPrimaryKey(entity.getParentid());
			// 获取向上几世的节点实例
			User gen_user = getUserUptoTop(user, user.getGenlevel(), entity.getStatus());
			GenUserVO genUserVO = new GenUserVO();
			if (gen_user != null) {
				// 初始化起始人实例
				GenUser genUser = new GenUser();
				genUser.setGenlevel(gen_user.getGenlevel());
				genUser.setImgurl(gen_user.getImgurl());
				genUser.setSex(gen_user.getSex());
				genUser.setUserid(gen_user.getUserid());
				genUser.setUsername(gen_user.getUsername());
				genUser.setLivestatus(gen_user.getLivestatus());
				genUser.setBrotherpos(gen_user.getBrotherpos());
				genUser.setBranchid(gen_user.getBranchid());
				genUser.setBranchname(gen_user.getBranchname());
				// 初始化起始节点
				genUserVO.setUser(genUser);
				// 获取配偶
				if (StringTools.notEmpty(gen_user.getMateid())) {
					UserQuery userExample1 = new UserQuery();
					userExample1.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0)
							.andStatusEqualTo(0);
					List<User> users2 = userDao.selectByExample(userExample1);
					if (users2.size() > 0) {
						User mate_user = users2.get(0);
						// 初始化配偶实例
						GenUser mateuser = new GenUser();
						mateuser.setGenlevel(gen_user.getGenlevel());
						mateuser.setImgurl(mate_user.getImgurl());
						mateuser.setSex(mate_user.getSex());
						mateuser.setUserid(mate_user.getUserid());
						mateuser.setUsername(mate_user.getUsername());
						mateuser.setLivestatus(mate_user.getLivestatus());
						mateuser.setBranchid(gen_user.getBranchid());
						mateuser.setBranchname(gen_user.getBranchname());
						genUserVO.setMateuser(mateuser);
					}
				}
				getUserListFromGenUser(genUserVO, entity.getParentid());
			}
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

	/**
	 * 从根向下查询五代
	 * @param entity
	 * @param genlevel
	 * @param genUserOthers
	 */
	public void getUserListCurrentToGenUser(GenUserOther entity, String currentId, List<GenUserOther> genUserOthers) {
		// 查询孩子列表
		String userid = entity.getUserid();
		List<User> users = userDao.selectChildren(userid);
		for (User user : users) {
			// 初始孩子实例
			GenUserOther gen_UserOther = new GenUserOther();
			gen_UserOther.setLivestatus(user.getLivestatus());
			gen_UserOther.setGenlevel(user.getGenlevel());
			gen_UserOther.setImgurl(user.getImgurl());
			gen_UserOther.setSex(user.getSex());
			gen_UserOther.setUserid(user.getUserid());
			gen_UserOther.setUsername(user.getUsername());
			gen_UserOther.setPid(user.getPid());
			gen_UserOther.setBrotherpos(user.getBrotherpos());
			gen_UserOther.setBranchid(user.getBranchid());
			gen_UserOther.setBranchname(user.getBranchname());
			// 获取孩子配偶
			if (StringTools.notEmpty(user.getMateid())) {
				UserQuery uq = new UserQuery();
				uq.or().andUseridEqualTo(user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(uq);
				if (users2.size() > 0) {
					User mateuser = users2.get(0);
					// 初始孩子配偶实例
					GenUserOther mate_user_other = new GenUserOther();
					mate_user_other.setLivestatus(mateuser.getLivestatus());
					mate_user_other.setGenlevel(user.getGenlevel());
					mate_user_other.setImgurl(mateuser.getImgurl());
					mate_user_other.setSex(mateuser.getSex());
					mate_user_other.setUserid(mateuser.getUserid());
					mate_user_other.setUsername(mateuser.getUsername());
					mate_user_other.setBranchid(user.getBranchid());
					mate_user_other.setBranchname(user.getBranchname());
					gen_UserOther.setMate(mate_user_other);
				}
			}
			genUserOthers.add(gen_UserOther);
			if (currentId != null && currentId.equals(user.getUserid())) {
				return;
			}
			getUserListCurrentToGenUser(gen_UserOther, currentId, genUserOthers);
		}
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
			if (gen_user.getGenlevel() == null || "".equals(gen_user.getGenlevel() + "")) {
				result = new Result(MsgConstants.GENLEVEL_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}

			// 修改加入排行的排序
			Map<String, Object> map = new HashMap<String, Object>();
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
			if (gen_user.getLivestatus() != null) {
				genUserSelf.setLivestatus(gen_user.getLivestatus());
			}
			genUserSelf.setGenlevel(gen_user.getGenlevel());
			genUserSelf.setImgurl(gen_user.getImgurl());
			genUserSelf.setSex(gen_user.getSex());
			genUserSelf.setUserid(gen_user.getUserid());
			genUserSelf.setUsername(gen_user.getUsername());
			genUserSelf.setPid(gen_user.getPid());
			// 初始化配偶实例
			User self_mate = new User();
			if (StringTools.notEmpty(gen_user.getMateid())) {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users3 = userDao.selectByExample(userExample1);
				if (users3.size() > 0) {
					self_mate = users3.get(0);
					GenUserOther self_mate_user = new GenUserOther();
					self_mate_user.setLivestatus(self_mate.getLivestatus());
					self_mate_user.setGenlevel(gen_user.getGenlevel());
					self_mate_user.setImgurl(self_mate.getImgurl());
					self_mate_user.setSex(self_mate.getSex());
					self_mate_user.setUserid(self_mate.getUserid());
					self_mate_user.setUsername(self_mate.getUsername());
					genUserSelf.setMate(self_mate_user);
				}
			}
			genUserOthers.add(genUserSelf);
			// 先增加自己

			for (User user : users2) {
				User mate_user = new User();
				// 初始化起始人实例
				GenUserOther genUserOther = new GenUserOther();
				genUserOther.setLivestatus(user.getLivestatus());
				genUserOther.setGenlevel(user.getGenlevel());
				genUserOther.setImgurl(user.getImgurl());
				genUserOther.setSex(user.getSex());
				genUserOther.setUserid(user.getUserid());
				genUserOther.setUsername(user.getUsername());
				genUserOther.setPid(user.getPid());

				// 初始化配偶实例
				if (StringTools.notEmpty(user.getMateid())) {
					UserQuery userExample1 = new UserQuery();
					userExample1.or().andUseridEqualTo(user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
					List<User> users3 = userDao.selectByExample(userExample1);
					if (users3.size() > 0) {
						mate_user = users3.get(0);
						GenUserOther mateuser = new GenUserOther();
						mateuser.setLivestatus(mate_user.getLivestatus());
						mateuser.setGenlevel(user.getGenlevel());
						mateuser.setImgurl(mate_user.getImgurl());
						mateuser.setSex(mate_user.getSex());
						mateuser.setUserid(mate_user.getUserid());
						mateuser.setUsername(mate_user.getUsername());
						genUserOther.setMate(mateuser);
					}
				}
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
	public void getUserListFromGenUser(GenUserVO entity, String currentId) {
		String userid = entity.getUser().getUserid();
		/*UserQuery userExample = new UserQuery();
		userExample.or().andPidEqualTo(userid).andDeleteflagEqualTo(0).andStatusEqualTo(0);
		List<User> users = userDao.selectByExample(userExample);*/
		// 查询孩子列表
		List<User> users = userDao.selectChildren(userid);
		List<GenUserVO> genUserVOs = new ArrayList<GenUserVO>();
		for (User user : users) {
			GenUserVO genUserVO = new GenUserVO();
			// 初始孩子实例
			GenUser gen_User = new GenUser();
			gen_User.setGenlevel(user.getGenlevel());
			gen_User.setImgurl(user.getImgurl());
			gen_User.setSex(user.getSex());
			gen_User.setUserid(user.getUserid());
			gen_User.setUsername(user.getUsername());
			gen_User.setLivestatus(user.getLivestatus());
			gen_User.setBrotherpos(user.getBrotherpos());
			gen_User.setBranchid(user.getBranchid());
			gen_User.setBranchname(user.getBranchname());
			genUserVO.setUser(gen_User);
			// 获取配偶
			if (StringUtils.isNotBlank(user.getMateid())) {
				UserQuery userExample1 = new UserQuery();
				userExample1.or().andUseridEqualTo(user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample1);
				if (users2.size() > 0) {
					User mateuser = users2.get(0);
					// 初始孩子配偶实例
					GenUser mate_user = new GenUser();
					mate_user.setGenlevel(user.getGenlevel());
					mate_user.setImgurl(mateuser.getImgurl());
					mate_user.setSex(mateuser.getSex());
					mate_user.setUserid(mateuser.getUserid());
					mate_user.setUsername(mateuser.getUsername());
					mate_user.setLivestatus(mateuser.getLivestatus());
					mate_user.setBranchid(user.getBranchid());
					mate_user.setBranchname(user.getBranchname());
					genUserVO.setMateuser(mate_user);
				}
			}
			genUserVOs.add(genUserVO);
			entity.setChildren(genUserVOs);
			if (currentId != null && currentId.equals(user.getUserid())) {
				return;
			}
			getUserListFromGenUser(genUserVO, currentId);
		}
	}

	/**
	 * 获取当前用户向上几代的根节点：如果超出指定几代查不到则返回最顶级实例。
	 * 
	 * @author 李鹏 17-04-07
	 * @param currentUser ：当前用户实例
	 * @param currentGenlevel ：当前用户的世代
	 * @param count ：向上几代
	 * @return
	 */
	public User getUserUptoTop(User currentUser, int currentGenlevel, int count) {
		User pUser = new User();
		// 父用户		
		if (currentUser.getPid() == null || "".equals(currentUser.getPid())) {
			return currentUser;
		} else {
			UserQuery userExample = new UserQuery();
			userExample.or().andDeleteflagEqualTo(0).andStatusEqualTo(0).andIsdirectEqualTo(1)
					.andUseridEqualTo(currentUser.getPid());
			List<User> users = userDao.selectByExample(userExample);
			if (users.size() > 0) {
				pUser = users.get(0);
				if (Math.abs(pUser.getGenlevel() - currentGenlevel) >= count) {
					return pUser;
				} else {
					pUser = getUserUptoTop(pUser, currentGenlevel, count);
				}
			} else {
				return currentUser;
			}
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
		if (entity.getGenlevel() == null || entity.getPid() == null | "".equals(entity.getPid())
				|| (entity.getGenlevel() - 1) % 5 == 0)
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
			if (StringTools.notEmpty(gen_user.getMateid())) {
				UserQuery userExample = new UserQuery();
				userExample.or().andUseridEqualTo(gen_user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
				List<User> users2 = userDao.selectByExample(userExample);
				if (users2.size() > 0) {
					mate_user = users2.get(0);
					GenUserOther mateuser = new GenUserOther();
					mateuser.setGenlevel(gen_user.getGenlevel());
					mateuser.setImgurl(mate_user.getImgurl());
					mateuser.setSex(mate_user.getSex());
					mateuser.setUserid(mate_user.getUserid());
					mateuser.setUsername(mate_user.getUsername());
					genUserOther.setMate(mateuser);
				}
			}

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
				mate_user_other.setGenlevel(user.getGenlevel());
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

	//测试审批
	@Override
	public Branch selectbyEditor(String userid) {
		// TODO Auto-generated method stub
		return branchDao.selectbyEditor(userid);
	}

	@Override
	public JsonResponse getBranchsByUserid(String userid, String code, Integer pageNo, Integer pageSize) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(userid)) {
			result = new Result(1, "用户userid为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (pageNo == null || pageSize == null) {
			result = new Result(1, "分页参数为空！");
			res = new JsonResponse(result);
			return res;
		}
		User user = userDao.selectByPrimaryKey(userid);
		if (user == null || user.getDeleteflag() == 1 || user.getStatus() != 0) {
			result = new Result(ConstantUtils.RESULT_FAIL, "当前用户不存在");
			res = new JsonResponse(result);
			return res;
		}
		//查询所属编委会
		List<UserManager> managers = userManagerMapper.selectMnangers(userid);
		if (managers == null || managers.size() < 1) {
			result = new Result(ConstantUtils.RESULT_FAIL, "当前用户无管理权限");
			res = new JsonResponse(result);
			return res;
		}
		//统计管理地区的编码
		List<String> codeList = new ArrayList<String>();

		boolean flag = false;
		//根据地区编码查询分支
		for (UserManager manager : managers) {
			flag = false;
			if (manager.getEbtype() == 1) {
				///branchValidAreas = branchDao.selectValidArea(user.getFamilyid());
				flag = true;
				break;
			}
			String areacode = editorialBoardMapper.selectCodeByEbid(manager.getEbid());
			String[] codes = areacode.split(",");
			for (String str : codes) {
				if (!codeList.contains(str)) {
					codeList.add(str);
				}
			}

		}
		List<Branch> branchs = new ArrayList<Branch>();
		PageHelper.startPage(pageNo, pageSize);
		String[] code1 = {};
		if (code != null)
			code1 = code.split(",");
		if (code != null && !"".equals(code) && flag) {
			//总编委会按照地区筛选获取
			branchs = branchDao.getBranchListByFamilyAndCodes(user.getFamilyid(), code1, null);
		} else if (code != null && !"".equals(code) && flag == false) {
			//分 编委会筛选分支
			if (codeList.contains(code)) {
				//如果有权限管理则查询
				branchs = branchDao.getBranchListByFamilyAndCodes(user.getFamilyid(), code1, null);
			} else {
				result = new Result(MsgConstants.NO_DATA);
				res = new JsonResponse(result);
				return res;
			}
		} else if (flag) {
			branchs = branchDao.selectBranchListByFamilyAndUserid(0, user.getFamilyid(), null);
		} else {
			String[] strs = codeList.toArray(new String[codeList.size()]);
			branchs = branchDao.getBranchListByFamilyAndCodes(user.getFamilyid(), strs, null);
		}
		if (branchs != null) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchs);
			res.setCount(new PageInfo<Branch>(branchs).getTotal());
			return res;
		} else {
			result = new Result(MsgConstants.NO_DATA);
			res = new JsonResponse(result);
			return res;
		}

	}

	@Override
	public JsonResponse getEbArea(Branch branch) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(branch.getBeginuserid())) {
			result = new Result(1, "用户userid为空！");
			res = new JsonResponse(result);
			return res;
		}
		String userid = branch.getBeginuserid();
		User user = userDao.selectByPrimaryKey(userid);
		if (user == null || user.getDeleteflag() == 1 || user.getStatus() != 0) {
			result = new Result(ConstantUtils.RESULT_FAIL, "当前用户不存在");
			res = new JsonResponse(result);
			return res;
		}
		//查询所属编委会
		List<UserManager> managers = userManagerMapper.selectMnangers(userid);
		if (managers == null || managers.size() < 1) {
			result = new Result(ConstantUtils.RESULT_FAIL, "当前用户无编委会权限");
			res = new JsonResponse(result);
			return res;
		}
		//统计管理地区的编码
		List<String> codeList = new ArrayList<String>();
		//地区集合
		List<BranchValidArea> branchValidAreas = new ArrayList<BranchValidArea>();

		//根据地区编码查询分支
		for (UserManager manager : managers) {
			//flag = false ;
			if (manager.getEbtype() == 1) {
				branchValidAreas = branchDao.selectValidArea(user.getFamilyid());
				List<BranchAreaCity> branchAreaCities = new ArrayList<BranchAreaCity>();
				for (BranchValidArea branchValidArea : branchValidAreas) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("familyid", user.getFamilyid());
					map.put("areacode", branchValidArea.getAreacode());
					List<BranchValidArea> branchValidAreas2 = branchDao.selectValidCity(map);
					BranchAreaCity branchAreaCity = new BranchAreaCity();
					branchAreaCity.setAreacode(branchValidArea.getAreacode());
					branchAreaCity.setAreaname(branchValidArea.getAreaname());
					branchAreaCity.setCitys(branchValidAreas2);
					branchAreaCities.add(branchAreaCity);
				}
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(branchAreaCities);
				return res;
			}
			String areacode = editorialBoardMapper.selectCodeByEbid(manager.getEbid());
			String[] codes = areacode.split(",");
			for (String str : codes) {
				if (!codeList.contains(str)) {
					codeList.add(str);
				}
			}

		}
		List<Branch> branchs = new ArrayList<Branch>();
		//if(flag == false) {
		//			branchs = branchDao.selectBranchListByFamilyAndUserid(0, user.getFamilyid(),null);
		//		}else{
		branchs = branchDao.getBranchListByFamilyAndCodes(user.getFamilyid(), codeList.toArray(new String[] {}), null);
		//}
		List<BranchAreaCity> area = new ArrayList<BranchAreaCity>();
		List<BranchValidArea> citys = new ArrayList<BranchValidArea>();
		for (Branch bra : branchs) {
			BranchAreaCity a = new BranchAreaCity();
			a.setAreacode(bra.getAreacode());
			a.setAreaname(bra.getArea());
			if (area.contains(a)) {
				BranchValidArea c = new BranchAreaCity();
				c.setAreacode(bra.getCitycode());
				c.setAreaname(bra.getCityname());
				for (int i = 0; i < area.size(); i++) {
					if (a.equals(area.get(i))) {
						citys = area.get(i).getCitys();
						if (!citys.contains(c)) {
							citys.add(c);
						}
						area.get(i).setCitys(citys);
					}
				}
			} else {
				area.add(a);
				BranchValidArea b = new BranchAreaCity();
				b.setAreacode(bra.getCitycode());
				b.setAreaname(bra.getCityname());
				citys.add(b);
				a.setCitys(citys);
			}
		}

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(area);
		return res;
	}

	@Override
	public JsonResponse getXQAndBranch(Branch entity) {
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
			//查询当前所有的县区
			Map<String, String> map = new HashMap<String, String>();
			map.put("familyid", entity.getFamilyid());
			map.put("citycode", entity.getCitycode());
			List<BranchValidArea> branchValidAreas = branchDao.selectValidXQ(map);
			if (branchValidAreas.size() == 0) {
				result = new Result(MsgConstants.AREA_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}

			String userid = entity.getBeginuserid();
			//User user =userDao.selectByPrimaryKey(userid);
			List<UserManager> managers = userManagerMapper.selectMnangers(userid);
			if (managers == null || managers.size() < 1) {
				result = new Result(ConstantUtils.RESULT_FAIL, "当前用户无编委会权限");
				res = new JsonResponse(result);
				return res;
			}
			//地区及分支
			List<BranchCityBranch> branchCityBranchs = new ArrayList<BranchCityBranch>();
			//统计管理地区的编码
			List<String> codeList = new ArrayList<String>();
			//根据地区编码查询分支
			for (UserManager manager : managers) {
				//flag = false ;
				if (manager.getEbtype() == 1) {

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
					return res;
				}
				String areacode = editorialBoardMapper.selectCodeByEbid(manager.getEbid());
				String[] codes = areacode.split(",");
				for (String str : codes) {
					if (!codeList.contains(str)) {
						codeList.add(str);
					}
				}

			}
			List<Branch> branchs = branchDao.getBranchListByFamilyAndCodes(entity.getFamilyid(),
					codeList.toArray(new String[] {}), null);
			//查询条件
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("familyid", entity.getFamilyid());
			boolean flag = false;
			for (int i = 0; i < branchValidAreas.size(); i++) {
				flag = true;
				BranchValidArea branchValidArea = branchValidAreas.get(i);
				for (Branch branch : branchs) {
					//flag = false;
					if (branchValidArea.getAreacode().equals(branch.getXcode())) {
						flag = false;
						break;
					}
				}
				if (flag) {
					branchValidAreas.remove(i);
					i = i - 1;
					continue;
				}

				map3.put("xcode", branchValidArea.getAreacode());
				List<Branch> branchs1 = branchDao.selectBranchByXQ(map3);
				BranchCityBranch branchCityBranch = new BranchCityBranch();
				branchCityBranch.setAreacode(branchValidArea.getAreacode());
				branchCityBranch.setAreaname(branchValidArea.getAreaname());
				branchCityBranch.setBranchs(branchs1);
				branchCityBranchs.add(branchCityBranch);
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchCityBranchs);
			return res;

			//result = new Result(MsgConstants.RESUL_SUCCESS);
			//res = new JsonResponse(result);
			//res.setData(branchCityBranchs);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchServiceImpl---Error:]", e);
		}
		return res;
	}

}
