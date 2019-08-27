package com.jp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Platform;
import com.jp.service.PlatformService;

@Controller
public class PlatformController {
	private final Logger log_ = LogManager.getLogger(PlatformController.class);
	@Autowired
	private PlatformService platformService;

	/**
	 * @描述 安卓或ios升级版本列表
	 */
	@RequestMapping(value = "/system/platform/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Platform> pageModel, Platform platform, ModelMap model) {
		return platformService.selectPlatformList(pageModel, platform);
	}

	/**
	 * @描述 删除版本
	 */
	@ResponseBody
	@RequestMapping(value = "/system/platform/deleteVersion", method = RequestMethod.POST)
	public JsonResponse deleteVersion(Integer id) {
		return platformService.deleteVersion(id);
	}

	/**
	 * @描述 增加或编辑版本
	 */
	@ResponseBody
	@RequestMapping(value = "/system/platform/save", method = RequestMethod.POST)
	public JsonResponse save(Platform platform) {
		return platformService.saveOrEdit(platform);
	}

	/**
	 * 编辑回显
	 * 
	 */
	@RequestMapping(value = "/system/platform/edit", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse edit(Integer id) {
		return platformService.selectone(id);
	}

	/**
	 * @描述 开启或关闭版本
	 */
	@ResponseBody
	@RequestMapping(value = "/system/platform/isOpen", method = RequestMethod.POST)
	public JsonResponse isOpen(Integer id) {
		return platformService.isOpen(id);
	}

	/**
	 * api方法分割线--------------------------------------------------------
	 */

	/**
	 * 获取在用的版本号内容
	 * 
	 * @param entity
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/platform/getPlatform", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getPlatform(Platform entity) {
		return platformService.getPlatform(entity.getFileType());
	}

	/**
	 * 返回当前系统时间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/platform/getCurrentTime", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getCurrentTime() {
		
		return platformService.getCurrentTime();
	}

}
