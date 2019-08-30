package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.Genealogy;
import com.jp.service.GenealogyService;

@Controller
@RequestMapping("genealogy")
public class GenealogyController {

	@Autowired
	private GenealogyService genealogyService;

	/**
	 * 未登录的世系表
	 * @param genealogy
	 * @return
	 */
	@RequestMapping(value = "/queryGenealogy", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse queryGenealogy(Genealogy genealogy) {
		return genealogyService.queryGenealogy(genealogy);
	}

}
