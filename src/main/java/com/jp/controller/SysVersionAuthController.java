package com.jp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.SysVersionPrivilege;
import com.jp.service.SysVersionPrivilegeService;

@Controller
@RequestMapping("system/sysversionauth")
public class SysVersionAuthController {

	private final Logger log_ = LogManager.getLogger(SysVersionAuthController.class);

	@Autowired
	private SysVersionPrivilegeService sysVersionPrivilegeService;

	/**
	 * 版本特权分页查询
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<SysVersionPrivilege> pageModel, SysVersionPrivilege sysVersionP) {
		return sysVersionPrivilegeService.pageQuery(pageModel, sysVersionP);
	}

	/**
	 * 编辑回显
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String id) {
		return sysVersionPrivilegeService.get(id);
	}

	/**
	 * 新增或修改版本特权
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(SysVersionPrivilege sysVersionP, HttpServletRequest request) {
		return sysVersionPrivilegeService.saveOrUpdate(sysVersionP);
	}

	/**
	 * 通过id删除版本特权详情
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResponse delete(String id) {
		return sysVersionPrivilegeService.delete(id);
	}

}
