package com.jp.controller;




import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.jp.common.PageModel;
import com.jp.entity.Index;
import com.jp.service.FunctionMenuService;

@Controller
@RequestMapping("system/functionMenu")
public class FunctionMenuController {
    
	private final Logger log_ = LogManager.getLogger(FunctionMenuController.class);
	@Autowired
	private FunctionMenuService functionMenuService;
	/**
	 * 初始化功能菜单
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletRequest request, ModelMap modelMap,PageModel<Index> pageModel) {
		//List<Index> menulist = new ArrayList<Index>();
	
		pageModel = functionMenuService.pageQuery(pageModel);
		modelMap.put("pageModel", pageModel);
		
		
		return "system/functionMenu/functionMneuList";
	}
	
	/**
	 * 保存/编辑
	 * @param index
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Index index) {
		String result = null;
		if(StringUtils.isEmpty(index.getId())) {
			//新增
			result = functionMenuService.save(index);
		}else {
			//编辑
			result = functionMenuService.update(index);
		}
		return result;
		
	}
	
	/**
	 * 获取功能菜单详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		
		String id = request.getParameter("id");
		Index index = functionMenuService.get(id);
		model.put("menu", index);
		return "system/functionMenu/functionMenuAdd";
	}
	
	
	/**
	 * 
	 * @描述 删除
	 * @作者 chenxiaobing
	 * @时间 2019年4月17日下午5:32:11
	 * @参数 @param banner
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public String batchDelete(String menuids) {
		String result = null;
		try {
			// a,b,c
			String bannerid = menuids.substring(0, menuids.length());
			String menuArray[] = bannerid.split(",");
			result = functionMenuService.batchDelete(menuArray);
			//result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}

	/**
	 * 
	 * @描述 删除
	 * @作者 chenxiaobing
	 * @时间 2019年4月17日下午5:32:11
	 * @参数 @param banner
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(String menuid) {
		String result = null;
		try {
			if(StringUtils.isEmpty(menuid)) {
				result = "0";
				
			}else {
				result = functionMenuService.delete(menuid);
			}
		
			
			//result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}
	
}
