package com.jp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.SysFamily;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.Userinfo;
import com.jp.service.FamilyService;
import com.jp.service.UserManagerService;
import com.jp.service.UserService;
import com.jp.util.StringTools;

@Controller
@RequestMapping("system/family")
public class FamilyController {
	private final Logger log_ = LogManager.getLogger(FamilyController.class);
	@Autowired
	private FamilyService familyService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserManagerService userManagerService;

	/**
	 * 
	 * @描述 家族分页列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<SysFamily> pageModel, SysFamily sysFamily) {
		return familyService.selectFamilyList(pageModel, sysFamily);
	}

	/**
	 * @描述 家族回显
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getFamily(String familyid) {
		Result result = null;
		JsonResponse res = null;
		SysFamily family = null;
		User user = null;
		try {
			if (StringTools.trimNotEmpty(familyid)) {
				family = familyService.selectByPrimaryKey(familyid);
				UserManager manager = userManagerService.selectByFamilyId(familyid);
				if (manager != null) {
					user = userService.selectByPrimaryKey(manager.getUserid());
				}
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(family);
			res.setData1(user);
			return res;
		} catch (Exception e) {
			log_.error("[editFamily方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
	}

	/**
	 * 
	 * @描述 家族新增或编辑
	 */
	@ResponseBody
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public JsonResponse mergeUser(User user, Userinfo userInfo, SysFamily family) {
		return familyService.merge(user, userInfo, family);
	}

	/**
	 * 
	 * @描述 停用/启用家族
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse changeStatus(SysFamily family, HttpSession httpSession) {
		return familyService.changeStatus(family, httpSession);
	}

	@RequestMapping(value = "/validatePhone", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse validateName(HttpServletRequest request) {
		String userid = StringTools.trimNotEmpty(request.getParameter("userid")) ? request.getParameter("userid").trim()
				: null;
		String familyid = StringTools.trimNotEmpty(request.getParameter("familyid"))
				? request.getParameter("familyid").trim()
				: null;
		String phone = StringTools.trimNotEmpty(request.getParameter("phone")) ? request.getParameter("phone").trim()
				: null;

		return userService.validatePhone(familyid, userid, phone);
	}
}
