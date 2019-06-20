package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.MomentUserSpot;
import com.jp.service.MomentUserSpotService;

@Controller
@RequestMapping("momentUserSpot")
public class MomentUserSpotController {

	@Autowired
	private MomentUserSpotService momentUserSpotService;

	/**
	 * 新增族圈用户关注
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "createUserSpot", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse createUserSpot(MomentUserSpot entity) {
		return momentUserSpotService.createUserSpot(entity);
	}

	/**
	 * 取消族圈用户关注
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "cancelUserSpot", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse cancelUserSpot(MomentUserSpot entity) {
		return momentUserSpotService.cancelUserSpot(entity);
	}

	/**
	 * 获取族圈用户的关注列表
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "getUserSpotList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getUserSpotList(MomentUserSpot entity) {
		return momentUserSpotService.getUserSpotList(entity);
	}

}
