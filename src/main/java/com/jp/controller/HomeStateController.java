package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.HomeState;
import com.jp.service.HomeStateService;

@Controller
@RequestMapping("homeState")
public class HomeStateController {

	@Autowired
	private HomeStateService homeStateService;

	@RequestMapping(value = "/queryHomeState", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse queryHomeState(HomeState homeState) {
		return homeStateService.queryHomeState(homeState);
	}

}
