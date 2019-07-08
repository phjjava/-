package com.jp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.jp.common.Result;
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

	@ResponseBody
	@RequestMapping(value = "/loginout", method = RequestMethod.GET)
	public JsonResponse loginout(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpSession session = request.getSession();
		session.removeAttribute("systemUserContext");
		Result result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("退出成功！");
		JsonResponse res = new JsonResponse(result);
		return res;
	}
}
