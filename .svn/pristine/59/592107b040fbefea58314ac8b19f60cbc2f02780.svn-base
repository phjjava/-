package com.jp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.PageModel;
import com.jp.entity.SysFunction;
import com.jp.entity.SysVersion;
import com.jp.service.SysFunctionService;
import com.jp.service.SysVersionService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/sysversion")
public class SysVersionController {
	
	private final Logger log_ = LogManager.getLogger(SysVersionController.class);

	@Autowired
	private SysVersionService sysVersionService;
	
	@Autowired
	private SysFunctionService sysFunctionService;


	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(SysVersion sysVersion,HttpServletRequest request, ModelMap model) {
		Integer result = null;
		try {
			String functionids[] = request.getParameterValues("functionids[]");
			if(StringTools.notEmpty(sysVersion.getVersionid())){//修改
				
				result = sysVersionService.update(sysVersion,functionids);
			}else{//新增
				sysVersion.setVersionid(UUIDUtils.getUUID());
				result = sysVersionService.insert(sysVersion,functionids);
			}

		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<SysVersion> pageModel, SysVersion sysVersion, ModelMap model) {
		try {
			sysVersionService.pageQuery(pageModel,sysVersion);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						sysVersionService.pageQuery(pageModel,sysVersion);
					}
				}
			}
			model.put("pageModel", pageModel);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "system/versionList";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {

			String versionid = request.getParameter("versionid");
			
			SysVersion sysVersion = sysVersionService.get(versionid);
			
			List<SysFunction> functionList = sysFunctionService.selectFunctionListByVersionid(versionid);
			List<SysFunction> plist = null;
			List<SysFunction> clist = null;
			if (functionList != null) {
				// 一级菜单
				plist = new ArrayList<SysFunction>();
				// 二级菜单
				clist = new ArrayList<SysFunction>();
				SysFunction sysFunction = null;
				// 循环所有菜单，将一级菜单和二级菜单还有三级封装到不同集合中
				for (int i = 0; i < functionList.size(); i++) {
					sysFunction = functionList.get(i);
					if ("00000".equals(sysFunction.getParentid())) {
						plist.add(sysFunction);
					} else {
						clist.add(sysFunction);
					} 
				}
				
			}
			
			model.put("plist", plist);
			model.put("clist", clist);
			model.put("sysVersion",sysVersion);
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "system/version";
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, ModelMap model) {
		Integer result = null;
		try {

			String versionid = request.getParameter("versionid");
			
			result = sysVersionService.delete(versionid);
			
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result+"";
	}
	
	

}
