package com.jp.controller;

import javax.servlet.http.HttpServletRequest;
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
import com.jp.entity.SysUser;
import com.jp.service.SysUserService;
import com.jp.util.MD5Util;

@Controller
@RequestMapping("system")
public class SystemController {

	private final Logger log_ = LogManager.getLogger(SystemController.class);
	@Autowired
	private SysUserService sysUserService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JsonResponse login(SysUser sysUser, HttpServletRequest request) {
		return sysUserService.selectByLoginNameAndPassword(request, sysUser.getLoginname(),
				MD5Util.string2MD5(sysUser.getPassword()));
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {

		model.put("menushow", "true");
		return "system/index";
	}

	//动态登录跳转manager重定向到index
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String manager(HttpServletRequest request, ModelMap model) {

		//model.put("menushow", "true");
		return "redirect:/system/index";
	}

	@ResponseBody
	@RequestMapping(value = "/loginout", method = RequestMethod.GET)
	public String loginout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("systemUserContext");
		return "success";
	}
}
