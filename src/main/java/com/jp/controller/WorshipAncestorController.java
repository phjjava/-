package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.WorshipAncestor;
import com.jp.service.WorshipAncestorService;

@Controller
@RequestMapping("worshipAncestor")
public class WorshipAncestorController {
	
	@Autowired
	private WorshipAncestorService worshipAncestorService;

	/**
	 * 祭拜
	 * 
	 * @param worship
	 * @return
	 */
	@RequestMapping(value = "/worshipAncestor", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse worship(WorshipAncestor worship) {
		return worshipAncestorService.worshipAncestor(worship);
	}

	/**
	 * 祭拜记录
	 * 
	 * @param worship
	 * @param start
	 *            页码
	 * @param count
	 *            每页数量
	 * @return
	 */
	@RequestMapping(value = "/getWorshipAncestors", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getWorships(WorshipAncestor worship, int start, int count) {
		return worshipAncestorService.getWorshipAncestors(worship, start, count);
	}

	/**
	 * 我的祭拜记录
	 * 
	 * @param worship
	 * @param start
	 *            页码
	 * @param count
	 *            每页数量
	 * @return
	 */
	@RequestMapping(value = "/getMyWorshipAncestors", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getMyWorships(WorshipAncestor worship, int start, int count) {
		return worshipAncestorService.getMyWorshipAncestors(worship, start, count);
	}

	
}
