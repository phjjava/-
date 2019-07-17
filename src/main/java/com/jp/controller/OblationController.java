package com.jp.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.WorshipOblation;
import com.jp.service.OblationService;
import com.jp.service.SysVersionService;

@Controller
@RequestMapping("system/oblation")
public class OblationController {
	private final Logger log_ = LogManager.getLogger(OblationController.class);

	@Resource
	private OblationService oblationService;
	@Resource
	private SysVersionService sysVersionService;

	/**
	 * 新增或编辑祭品
	 * @param oblation
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(WorshipOblation oblation, ModelMap model) {
		return oblationService.save(oblation);
	}

	/**
	 * 删除祭品
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResponse deleteEditorialBoard(WorshipOblation entity) {
		return oblationService.del(entity);
	}

	/**
	 * 编辑祭品回显
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOblation", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String id) {
		return oblationService.getOblationById(id);
	}
}
