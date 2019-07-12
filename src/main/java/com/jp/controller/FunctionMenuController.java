package com.jp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
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
	 * 功能菜单管理列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Index> pageModel) {
		return functionMenuService.pageQuery(pageModel);
	}

	/**
	 * 保存/编辑
	 * @param index
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse save(Index index) {
		return functionMenuService.save(index);
	}

	/**
	 * 回显获取功能菜单详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String id) {
		return functionMenuService.get(id);
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
	public JsonResponse delete(String menuid) {
		return functionMenuService.delete(menuid);
	}

}
