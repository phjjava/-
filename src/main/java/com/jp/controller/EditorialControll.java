package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.EditorialBoard;
import com.jp.service.EditorialBoardService;

@Controller
@RequestMapping("editorial")
public class EditorialControll {

	@Autowired
	private EditorialBoardService editorialBoardService;

	/**
	 * 编辑或新增编委会
	 * @param eb
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse save(EditorialBoard eb) {
		return editorialBoardService.save(eb);
	}

	/**
	 * 分页查询编委会数据
	 * @param pageModel
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<EditorialBoard> pageModel, EditorialBoard entity) {
		return editorialBoardService.pageQuery(pageModel, entity);
	}

	/**
	 * 删除编委会
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteEditorialBoard(EditorialBoard entity) {
		return editorialBoardService.del(entity.getId());
	}

	/**
	 * 获取编委会详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String id) {
		return editorialBoardService.getEditorialBoard(id);
	}

	/**
	 * 根据用户获取编委会列表
	 * @return
	 */
	@RequestMapping(value = "/selecteditorialBoardList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse selectRoleList() {
		return editorialBoardService.selecteditorialBoardList();
	}

}
