package com.jp.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.WorshipOblationType;
import com.jp.service.WorshipOblationTypeService;

@Controller
@RequestMapping("system/oblationType")
public class WorshipOblationTypeController {

	@Resource
	private WorshipOblationTypeService worshipOblationService;

	/**
	 * 新增或编辑保存
	 * @param oblationType
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(WorshipOblationType oblationType, ModelMap model) {
		return worshipOblationService.save(oblationType);
	}

	/**
	 * 祭品分类管理列表
	 * @param pageModel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<WorshipOblationType> pageModel, ModelMap model) {
		return worshipOblationService.pageQuery(pageModel);
	}

	/**
	 * 祭品分类子菜单列表
	 * @param oblationtypeid
	 * @return
	 */
	@RequestMapping(value = "/childlist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse childlist(String oblationtypeid) {
		return worshipOblationService.selectListByParnetid(oblationtypeid);
	}

	/**
	 * 删除祭品分类
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteEditorialBoard(WorshipOblationType entity) {
		return worshipOblationService.del(entity);
	}

	/**
	 * 编辑祭品分类回显
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String id) {
		return worshipOblationService.getOblationTypeById(id);
	}
}
