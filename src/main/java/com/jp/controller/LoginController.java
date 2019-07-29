package com.jp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.LoginUserInfo;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.UserDao;
import com.jp.entity.Branch;
import com.jp.entity.Function;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.service.BranchService;
import com.jp.service.FunctionService;
import com.jp.service.RoleService;
import com.jp.service.UserManagerService;
import com.jp.service.UserService;
import com.jp.util.MD5Util;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("login")
public class LoginController {

	private final Logger log_ = LogManager.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunctionService functionService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private UserManagerService userManagerService;
	@Autowired
	private UserDao userMapper;

	/**
	 * @描述 登录
	 * @作者 wumin
	 * @时间 2017年5月13日上午11:56:41
	 * @参数 @param request
	 * @参数 @param response
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public JsonResponse login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));// 支持跨域请求
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true"); // 支持cookie跨域
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization,Origin, " + "X-Requested-With, Content-Type, Accept,Access-Token");

		Result result = null;
		JsonResponse res = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {

			String phone = request.getParameter("phone");
			String password = request.getParameter("password");
			String inputCode = request.getParameter("code");
			// String remember = request.getParameter("remember");

			Object sessionCode = request.getSession().getAttribute("code");
			// 验证验证码是否正确
			if (StringTools.notEmpty(inputCode) && StringTools.notEmpty(sessionCode)
					&& inputCode.toLowerCase().equals(sessionCode.toString().toLowerCase())) {
				// 验证用户、密码是否为空
				if (StringTools.trimNotEmpty(phone) && StringTools.trimNotEmpty(password)) {
					password = MD5Util.string2MD5(password);// 加密
					List<User> userList = userService.login(phone, password);

					// 验证用户名、密码是否正确
					if (userList != null && userList.size() > 0) {

						if (userList.size() == 1) {
							LoginUserInfo userContext = new LoginUserInfo();
							// 用户信息
							User user = userList.get(0);
							userContext.setUser(user);

							// Role role = roleService.selectRoleByUserid(user.getFamilyid(),
							// user.getUserid());
							List<UserManager> managers = userManagerService.selectManagerByUserid(user.getUserid());
							if (managers == null || managers.size() == 0) {
								result = new Result(MsgConstants.LOGIN_NOT_ADMIN);
								res = new JsonResponse(result);
								return res;
							}
							// userContext.setRole(role);
							// userContext.setUsermanager(manager);
							List<Function> functionList = functionService
									.selectFunctionListByManagerid(user.getFamilyid(), user.getUserid());
							List<Branch> branchList = branchService
									.selectBranchListByFamilyAndUserid(user.getFamilyid(), user.getUserid());
							// if(manager.getIsmanager() == 1 && manager.getEbtype() == 1 ){
							// //获取该家族所有功能权限
							//// functionList =
							// functionService.selectFunctionListByRoleid(user.getFamilyid(), null);
							//// branchList =
							// branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(), null);
							// functionList =
							// functionService.selectFunctionListByManagerid(user.getFamilyid(), null);
							// branchList =
							// branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(), null);
							// }else{
							// //functionList =
							// functionService.selectFunctionListByRoleidAndFamilyid(user.getFamilyid(),
							// role.getRoleid());
							// //branchList =
							// branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(),
							// user.getUserid());
							// functionList =
							// functionService.selectFunctionListByManagerid(user.getFamilyid(),
							// user.getUserid());
							// branchList =
							// branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(),
							// user.getUserid());
							// }
							userContext.setUsermanagers(managers);
							userContext.setFunctionList(functionList);
							userContext.setBranchList(branchList);
							if (branchList == null) {
								branchList = new ArrayList<Branch>();
							}
							if (user.getSessionid() == null) {
								// 重置sessionid到数据库
								user.setSessionid(UUIDUtils.getUUID());
							}
							// 记录登陆时间
							user.setLogintime(new Date());
							userMapper.updateByPrimaryKeySelective(user);
							// 保存session作用域
							request.getSession().setAttribute("userContext", userContext);

							result = new Result(MsgConstants.RESUL_SUCCESS);
							res = new JsonResponse(result);
							res.setData(user);
						} else if (userList.size() < 5) {

							request.getSession().setAttribute("loginUserList", userList);

							result = new Result(MsgConstants.LOGIN_USER_CHOOSEFAMILY);
							res = new JsonResponse(result);
							res.setData(userList);
							return res;
						} else {
							result = new Result(MsgConstants.LOGIN_ABNORMAL);
							res = new JsonResponse(result);
							return res;
						}
					} else {
						result = new Result(MsgConstants.LOGIN_USER_WRONG);
						res = new JsonResponse(result);
						return res;
					}
				} else {
					result = new Result(MsgConstants.LOGIN_USER_NULL);
					res = new JsonResponse(result);
					return res;
				}
			} else {
				result = new Result(MsgConstants.LOGIN_ICODE_WRONG);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZ_ERROR登录系统失败:]", e);
			result = new Result(MsgConstants.LOGIN_FAIL);
			res = new JsonResponse(result);
			return res;
		}
		return res;
	}

	// 动态登录跳转manager重定向到index
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {

		// model.put("menushow", "true");
		return "redirect:/index/init";
	}

	@ResponseBody
	@RequestMapping(value = "/loginout", method = RequestMethod.GET)
	public JsonResponse loginout(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpSession session = request.getSession();
		session.removeAttribute("userContext");
		Result result = new Result(MsgConstants.RESUL_SUCCESS);
		JsonResponse res = new JsonResponse(result);
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "tochoose", method = RequestMethod.GET)
	public JsonResponse tochoose(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpSession session = request.getSession();
		List<User> userList = (List<User>) session.getAttribute("loginUserList");
		Result result = new Result(MsgConstants.RESUL_SUCCESS);
		JsonResponse res = new JsonResponse(result);
		res.setData(userList);
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "choose", method = RequestMethod.POST)
	public JsonResponse choose(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Result result = null;
		JsonResponse res = null;
		try {

			String userid = request.getParameter("userid");
			List<User> userList = (List<User>) request.getSession().getAttribute("loginUserList");
			User user = null;
			// 根据选择取出对应的用户
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getUserid().equals(userid)) {
					user = userList.get(i);
				}
			}

			// 验证用户名、密码是否正确
			LoginUserInfo userContext = new LoginUserInfo();
			// 用户信息
			userContext.setUser(user);

			// Role role = roleService.selectRoleByUserid(user.getFamilyid(),
			// user.getUserid());
			//
			// if(role == null){
			// content.append("{\"result\":\"false\",\"info\":\"用户无管理员权限！\"}");
			// return content.toString();
			// }

			List<UserManager> managers = userManagerService.selectManagerByUserid(user.getUserid());
			if (managers == null || managers.size() == 0) {
				result = new Result(MsgConstants.LOGIN_NOT_ADMIN);
				res = new JsonResponse(result);
				return res;
				// return content.toString();
			}
			// userContext.setRole(role);
			// userContext.setUsermanager(manager);
			List<Function> functionList = functionService.selectFunctionListByManagerid(user.getFamilyid(),
					user.getUserid());
			List<Branch> branchList = branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(),
					user.getUserid());

			// userContext.setRole(role);
			// List<Function> functionList = null;
			// List<Branch> branchList = null;
			// if(role.getIsmanager() == 1 && role.getType() == 1){
			// //获取该家族所有功能权限
			// functionList = functionService.selectFunctionListByRoleid(user.getFamilyid(),
			// null);
			// branchList =
			// branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(), null);
			// }else{
			//
			// branchList =
			// branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(),
			// user.getUserid());
			// functionList =
			// functionService.selectFunctionListByRoleidAndFamilyid(user.getFamilyid(),
			// role.getRoleid());
			// }
			userContext.setUsermanagers(managers);
			userContext.setFunctionList(functionList);
			if (branchList == null) {
				branchList = new ArrayList<Branch>();
			}
			userContext.setBranchList(branchList);
			// 创建session
			HttpSession session = request.getSession();
			if (user.getSessionid() == null) {
				// 重置sessionid到数据库
				user.setSessionid(UUIDUtils.getUUID());
			}
			// 记录登陆时间
			user.setLogintime(new Date());
			userMapper.updateByPrimaryKeySelective(user);
			// 保存session作用域
			session.setAttribute("userContext", userContext);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(user);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZ_ERROR登录系统失败:]", e);
			result = new Result(MsgConstants.LOGIN_FAIL);
			res = new JsonResponse(result);
		}
		return res;
	}

}
