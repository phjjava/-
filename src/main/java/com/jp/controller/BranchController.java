package com.jp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.LoginUserInfo;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.UserDao;
import com.jp.dao.UserManagerMapper;
import com.jp.dao.UserbranchDao;
import com.jp.entity.Branch;
import com.jp.entity.BranchQuery;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.entity.Userbranch;
import com.jp.entity.UserbranchQuery;
import com.jp.service.BranchService;
import com.jp.service.RoleService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("branch")
public class BranchController {

	private final Logger log_ = LogManager.getLogger(BranchController.class);

	@Autowired
	private BranchService branchService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private UserbranchDao userBranchDao;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@Autowired
	private UserDao userDao;

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(Branch branch, ModelMap model) {
		Integer count = null;
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			if (StringTools.notEmpty(branch.getBranchid())) {// 修改
				branch.setUpdateid(CurrentUserContext.getCurrentUserId());
				branch.setUpdatetime(new Date());
				count = branchService.update(branch);
			} else {// 新增
				branch.setStatus(0);// 使用中
				branch.setBranchid(UUIDUtils.getUUID());
				branch.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				branch.setCreateid(CurrentUserContext.getCurrentUserId());
				count = branchService.insert(branch);
				if (count > 0) {
					// 更新session中的分支信息
					LoginUserInfo userContext = CurrentUserContext.getUserContext();
					User user = userContext.getUser();
					// Role role = roleService.selectRoleByUserid(user.getFamilyid(),
					// user.getUserid());
					List<Branch> branchList = null;
					// if(role.getIsmanager() == 1){
					// branchList =
					// branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(), null);
					// }else{
					branchList = branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(), user.getUserid());
					// }
					if (branchList == null) {
						branchList = new ArrayList<Branch>();
					}
					userContext.setBranchList(branchList);
				}
			}
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Branch> pageModel, Branch branch, ModelMap model) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			String userid = CurrentUserContext.getCurrentUserId();
			UserbranchQuery ex = new UserbranchQuery();
			ex.or().andUseridEqualTo(userid);
			List<Userbranch> list = userBranchDao.selectByExample(ex);
			Branch bran = new Branch();
			for (Userbranch b : list) {
				bran.setBranchid(b.getBranchid());
				bran.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				bran = branchDao.selectByPrimaryKey(bran);
				if (bran.getBranchid() != null && !"".equals(bran.getBranchid()))
					branch.setBranchid(b.getBranchid());

			}
			branch.setFamilyid(CurrentUserContext.getCurrentFamilyId());

			UserManagerExample example = new UserManagerExample();
			example.or().andUseridEqualTo(CurrentUserContext.getCurrentUserId());
			example.setOrderByClause("ebtype desc,ismanager desc");
			List<UserManager> managers = userManagerMapper.selectByExample(example);
			for (UserManager manager : managers) {
				if (manager.getEbtype() == 1) {
					branchService.pageQuery(pageModel, branch);
				} else {
					branchService.selectBranchListByFamilyAndUserid(pageModel, branch);
				}
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModel.getList());
			res.setCount(pageModel.getPageInfo().getTotal());
		} catch (Exception e) {
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request, ModelMap model) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {

			String branchid = request.getParameter("branchid");
			Branch branch = branchService.get(branchid);
			// 根据beginuserid获取世系信息
			User user = userDao.selectByPrimaryKey(branch.getBeginuserid());
			// 增加返回世系信息
			if (user != null) {
				branch.setGenlevel(user.getGenlevel() + "世");
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branch);
		} catch (Exception e) {
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(Branch branch) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = null;
		try {
			count = branchService.changeStatus(branch);
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * @描述 新增用户初始化父亲 和 配偶
	 * @作者 sj
	 * @时间 2017年5月5日上午11:03:44
	 * @参数 @param familyid
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/initBranch", method = RequestMethod.POST)
	public JsonResponse selectBranch(Branch branch) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			PageModel<Branch> pageModelBranch = new PageModel<Branch>();
			
			branch.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			branchService.initBranch(pageModelBranch, branch);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModelBranch);
		} catch (Exception e) {
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/validateBranchname", method = RequestMethod.POST)
	public JsonResponse validateBranchname(HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		boolean flag = true;// 默认通过验证

		String branchname = StringTools.trimNotEmpty(request.getParameter("branchname"))
				? request.getParameter("branchname").trim()
				: null;

		try {
			BranchQuery example = new BranchQuery();
			example.or().andBranchnameEqualTo(branchname).andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
			List<Branch> selectRt = branchDao.selectByExample(example);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			if (selectRt != null && selectRt.size() > 0) {
				result = new Result(MsgConstants.BRANCH_VALIDATA_NAME);
				flag = false;
			}
			res = new JsonResponse(result);
			res.setData(flag);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[PLMERROR:]", e);
		}
		return res;
	}

	/**
	 * 
	 * @描述 对发起人做出提示(此人是否已经发起过其他分支)
	 * @作者 jinlizhi
	 * @时间 2017年6月28日下午4:50:25
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/checkBeginer", method = RequestMethod.POST)
	public JsonResponse checkBeginer(HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		boolean flag = true;// 默认通过验证
		String beginuserid = StringTools.trimNotEmpty(request.getParameter("beginuserid"))
				? request.getParameter("beginuserid").trim()
				: null;
		if (beginuserid == null) {
			result = new Result(MsgConstants.BRANCH_NO_BEGINERID);
			res = new JsonResponse(result);
			res.setData(false);
			return res;
		}
		try {
			BranchQuery example = new BranchQuery();
			example.or().andBeginuseridEqualTo(beginuserid);
			List<Branch> selectRt = branchDao.selectByExample(example);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			if (selectRt != null && selectRt.size() > 0) {
				result = new Result(MsgConstants.BRANCH_CHECK_BEGINER);
				flag = false;
			}
			res = new JsonResponse(result);
			res.setData(flag);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[PLMERROR:]", e);
		}
		return res;
	}

	/**
	 * 以下方法用于api
	 */

	/**
	 * 获取所有分支信息列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllBranch", method = RequestMethod.GET)
	public JsonResponse getAllBranch(Branch branch) {
		return branchService.getAllBranch(branch);
	}

	/**
	 * 获取某分支下的所有人员 - 分支内家族成员通讯录
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchPersons", method = RequestMethod.GET)
	public JsonResponse getBranchPersons(Branch branch) {
		return branchService.getBranchPersons(branch);
	}

	/**
	 * 获取动态、公告等筛选条件中的有效的省份信息 - 省份列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildArea", method = RequestMethod.GET)
	public JsonResponse getBranchVaildArea(Branch branch) {
		return branchService.getBranchVaildArea(branch);
	}

	/**
	 * 获取有效的省份和所在城市列表 - 省份和城市列表（组合）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildAreaAndCity", method = RequestMethod.GET)
	public JsonResponse getBranchVaildAreaAndCity(Branch branch) {
		return branchService.getBranchVaildAreaAndCity(branch);
	}

	/**
	 * 获取有效的县区和分支列表 - 县区和分支列表（组合）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildXQAndBranch", method = RequestMethod.GET)
	public JsonResponse getBranchVaildXQAndBranch(Branch branch) {
		return branchService.getBranchVaildXQAndBranch(branch);
	}

	/**
	 * 获取动态、公告等筛选条件中的有效的城市信息 - 城市列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildCity", method = RequestMethod.GET)
	public JsonResponse getBranchVaildCity(Branch branch) {
		return branchService.getBranchVaildCity(branch);
	}

	/**
	 * 获取动态、公告等筛选条件中的有效的县区信息 - 县区列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildXQ", method = RequestMethod.GET)
	public JsonResponse getBranchVaildXQ(Branch branch) {
		return branchService.getBranchVaildXQ(branch);
	}

	/**
	 * 获取某县区下的分支列表 - 分支列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchOfXQ", method = RequestMethod.POST)
	public JsonResponse getBranchOfXQ(Branch branch) {
		return branchService.getBranchOfXQ(branch);
	}

	/**
	 * 获取某城市下的分支列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchByCitycode", method = RequestMethod.POST)
	public JsonResponse getBranchByCitycode(Branch branch) {
		return branchService.getBranchByCitycode(branch);
	}

	/**
	 * 获取指定分支的世系表（结构化数据列表） - 获取指定分支的世系表 (iOS 用)（层次结构数据）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenList", method = RequestMethod.GET)
	public JsonResponse getGenList(Branch branch, HttpServletRequest request) {
		String userid = request.getHeader("userid");
		branch.setParentid(userid);
		return branchService.getGenList(branch);
	}

	/**
	 * 获取指定分支的世系表（仅列表：递归） - 获取指定分支的世系表（列表数据+递归查询）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListOnly", method = RequestMethod.GET)
	public JsonResponse getGenListOnly(Branch branch) {
		return branchService.getGenListOnly(branch);
	}

	/**
	 * 获取指定分支的世系表（仅列表：无递归） - 获取指定分支的世系表(Android用)（列表数据+批量查询）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListOnlyExt", method = RequestMethod.GET)
	public JsonResponse getGenListOnlyExt(Branch branch, HttpServletRequest request) {
		String userid = request.getHeader("userid");
		branch.setParentid(userid);
		return branchService.getGenListOnlyExt(branch);
	}

	/**
	 * 向上查找指定的几代 - 追根溯源指定分支的世系表（追根溯源-列表结构）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListToTop", method = RequestMethod.GET)
	public JsonResponse getGenListToTop(Branch branch) {
		return branchService.getGenListToTop(branch);
	}

	/**
	 * 五世一展示，从当前节点向上查到第一个被5整除的世系根节点，向下查到第一个被5整除的世系 - 追根溯源指定分支的世系表（追根溯源--五世一表）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListOnlyExtMod", method = RequestMethod.GET)
	public JsonResponse getGenListOnlyExtMod(Branch branch) {
		return branchService.getGenListOnlyExtMod(branch);
	}

}
