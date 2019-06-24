package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.MomentLike;
import com.jp.service.MomentLikeService;

@Controller
@RequestMapping("momentLike")
public class MomentLikeController {

	@Autowired
	private MomentLikeService momentLikeService;

	/**
	 * 点赞族圈信息
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "createMomentLike", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse createMomentLike(MomentLike entity) {
		return momentLikeService.createMomentLike(entity);
	}

	/**
	 * 取消点赞族圈信息
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "cancelMomentLike", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse cancelMomentLike(MomentLike entity) {
		return momentLikeService.cancelMomentLike(entity);
	}
}
