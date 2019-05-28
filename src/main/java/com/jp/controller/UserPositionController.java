package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.Userposition;
import com.jp.service.UserPositionService;

@Controller
@RequestMapping("userposition")
public class UserPositionController {

	@Autowired
	private UserPositionService userPositionService;

	/**
	 * 获取家族排行
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getPositionDic", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getPositionDic(Userposition entity) {
		return userPositionService.getPositionDic(entity);
	}

}
