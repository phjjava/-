package com.jp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.jp.common.PageModel;
import com.jp.entity.SysVersionPrivilege;
import com.jp.service.SysVersionPrivilegeService;

@Controller
@RequestMapping("system/sysversionauth")
public class SysVersionAuthController {
	
	private final Logger log_ = LogManager.getLogger(SysVersionAuthController.class);
	
	@Autowired
	private SysVersionPrivilegeService sysVersionPrivilegeService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<SysVersionPrivilege> pageModel, SysVersionPrivilege sysVersion, ModelMap model) {
		try {
			sysVersionPrivilegeService.pageQuery(pageModel,sysVersion);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						sysVersionPrivilegeService.pageQuery(pageModel,sysVersion);
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

}
