package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.Nation;
import com.jp.service.NationService;

@Controller
@RequestMapping("nation")
public class NationController {

	@Autowired
	private NationService nationService;

	/**
	 * 获取民族信息列表
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getNationDic", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getNationDic(Nation entity) {
		return nationService.getNationDic(entity);
	}

}
