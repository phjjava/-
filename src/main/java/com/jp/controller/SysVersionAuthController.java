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

import com.jp.common.PageModel;
import com.jp.entity.SysFunction;
import com.jp.entity.SysVersion;
import com.jp.entity.SysVersionPrivilege;
import com.jp.service.SysFunctionService;
import com.jp.service.SysVersionPrivilegeService;
import com.jp.service.SysVersionService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/sysversionauth")
public class SysVersionAuthController {
	
	private final Logger log_ = LogManager.getLogger(SysVersionAuthController.class);
	
	@Autowired
	private SysVersionPrivilegeService sysVersionPrivilegeService;
	
	@Autowired
	private SysVersionService sysVersionService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<SysVersionPrivilege> pageModel, SysVersionPrivilege sysVersionP, ModelMap model) {
		try {
			sysVersionPrivilegeService.pageQuery(pageModel,sysVersionP);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						sysVersionPrivilegeService.pageQuery(pageModel,sysVersionP);
					}
				}
			}
			model.put("pageModel", pageModel);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "system/versionPrivilegeList";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");	// 版本特权主键id
			
			SysVersionPrivilege sysVersionP = sysVersionPrivilegeService.get(id);
			//初始化版本
			List<SysVersion> versionList = sysVersionService.getSysVersionList();
			model.put("versionList", versionList);
			model.put("sysVersionP",sysVersionP);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "system/versionPrivilegeEdit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(SysVersionPrivilege sysVersionP,HttpServletRequest request, ModelMap model) {
		Integer result = null;
		try {
			
			result = sysVersionPrivilegeService.saveOrUpdate(sysVersionP);
			
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, ModelMap model) {
		Integer result = null;
		try {
			String id = request.getParameter("id");
			result = sysVersionPrivilegeService.delete(id);
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result+"";
	}

}
