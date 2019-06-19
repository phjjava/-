package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.MomentUserStore;
import com.jp.service.MomentUserStoreService;

@Controller
@RequestMapping("momentUserStore")
public class MomentUserStoreController {

	@Autowired
	private MomentUserStoreService momentUserStoreService;

	/**
	 * 收藏族圈信息
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "storeMoment", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse storeMoment(MomentUserStore entity) {
		return momentUserStoreService.storeMoment(entity);
	}

	/**
	 * 取消收藏
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "cancelStore", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse cancelStore(MomentUserStore entity) {
		return momentUserStoreService.cancelStore(entity);
	}

}
