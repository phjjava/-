package com.jp.controller;

import java.util.ArrayList;
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
import com.jp.common.PageModel;
import com.jp.entity.User;
import com.jp.entity.Userbranch;
import com.jp.entity.Userrole;
import com.jp.service.UserService;
import com.jp.service.UserroleService;
import com.jp.util.Result;
import com.jp.util.StringTools;

@Controller
@RequestMapping("userrole")
public class UserroleController {

	private final Logger log_ = LogManager.getLogger(UserroleController.class);

	@Autowired
	private UserroleService urservice;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String userroleid = request.getParameter("userroleid");
			Userrole userrole = urservice.get(userroleid);

			model.put("userrole", userrole);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "userrole/userrole";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<User> pageModel, Userrole userrole, ModelMap model) {
		try {
			userrole.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			urservice.pageQuery(pageModel, userrole);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						urservice.pageQuery(pageModel, userrole);
					}
				}
			}
			model.put("pageModel", pageModel);
			model.put("userrole", userrole);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "management/userrolebranchList";

	}

	/**
	 * @描述 增加管理员
	 * @作者 sj
	 * @时间 2017年5月13日上午10:57:32
	 * @参数 @param user
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/mergeManagent", method = RequestMethod.POST)
	public String mergeManagent(Userrole userrole, Userbranch userBranch) {
		String result = null;
		try {
			userrole.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			urservice.mergeUserRoleBranch(userrole, userBranch);
			result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}

	/**
	 * 
	 * @描述 去管理员编辑界面
	 * @作者 sj
	 * @时间 2017年5月10日上午10:51:49
	 * @参数 @param request
	 * @参数 @param modelMap
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editFamily(HttpServletRequest request, ModelMap modelMap) {
		try {
			List<Userbranch> userbranchList = new ArrayList<Userbranch>();
			Userrole userrole = null;

			String userid = request.getParameter("userid");
			if (StringTools.trimNotEmpty(userid)) {
				userrole = urservice.selectByPrimaryKey(userid);
				User rtUser = userService.selectByPrimaryKey(userid);
				userrole.setUsername(rtUser.getUsername());
				userbranchList = urservice.userBranchList(userid);
			}
			String branch = "";
			if (userbranchList != null && userbranchList.size() > 0) {
				for (int i = 0; i < userbranchList.size(); i++) {
					branch += userbranchList.get(i).getBranchid() + "_" + userbranchList.get(i).getBranchname() + ",";
				}
			}
			branch = branch.substring(0, branch.length() - 1);
			userrole.setBranchidStrs(branch);
			modelMap.put("userrole", userrole);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "management/userrolebranch";
	}

	/**
	 * 删除用户角色关系
	 * @描述 
	 * @作者 jinlizhi
	 * @时间 2017年6月27日下午5:53:23
	 * @参数 @param user
	 * @参数 @return
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
	public Result deleteUserRole(User user) {
		Result result = new Result();
		try {
			result = urservice.deleteUserRole(user);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result.setData(0);
			return result;
		}
		return result;
	}

}
