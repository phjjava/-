package com.jp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.Usercontent;
import com.jp.service.UserContentService;

@Controller
@RequestMapping("usercontent")
public class UserContentController {
	private final Logger log_ = LogManager.getLogger(UserContentController.class);
	@Autowired
	private UserContentService userContentService;

	/**
	 * 获取群英录列表
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getUserContentList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getUserContentList(Usercontent entity, int start, int count) {
		return userContentService.getUserContentList(entity, start, count);
	}

	/**
	 * 获取首页轮播个人群英录
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getHomePageContents", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getHomePageContents(Usercontent entity, int count) {
		return userContentService.getHomePageContents(entity, count);
	}

	/**
	 * 获取个人群英录
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getUserContent", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getUserContent(Usercontent entity) {
		return userContentService.getPersonContent(entity);
	}

	/**
	 * 设置个人群英录，若没有该用户内容则插入，有则更新
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/publicContent", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse publicContent(@RequestBody Usercontent entity) {
		return userContentService.publicContent(entity);
	}

}
