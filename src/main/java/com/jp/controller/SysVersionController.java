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
import com.jp.entity.SysVersion;
import com.jp.service.SysFunctionService;
import com.jp.service.SysVersionService;

@Controller
@RequestMapping("system/sysversion")
public class SysVersionController {

	private final Logger log_ = LogManager.getLogger(SysVersionController.class);

	@Autowired
	private SysVersionService sysVersionService;

	@Autowired
	private SysFunctionService sysFunctionService;

	/**
	 * 编辑或新增菜单的保存
	 * @param sysVersion
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(SysVersion sysVersion, HttpServletRequest request) {
		String functionids[] = request.getParameterValues("functionids[]");
		return sysVersionService.save(sysVersion, functionids);
	}

	/**
	 * 版本分页列表
	 * @param pageModel
	 * @param sysVersion
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<SysVersion> pageModel, SysVersion sysVersion) {
		return sysVersionService.pageQuery(pageModel, sysVersion);
	}

	/**
	 * 新增版本和编辑回显
	 * @param versionid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String versionid) {
		return sysFunctionService.selectFunctionListByVersionid(versionid);
	}

	/**
	 * @描述 删除
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResponse delete(String versionid) {
		return sysVersionService.delete(versionid);
	}

	/**
	 * 获取版本列表
	 */
	@ResponseBody
	@RequestMapping(value = "/getSysVersionList", method = RequestMethod.POST)
	public JsonResponse getSysVersionList() {
		return sysVersionService.getSysVersionList();
	}

}
