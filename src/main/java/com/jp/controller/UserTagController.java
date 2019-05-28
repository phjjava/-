package com.jp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.UserTag;
import com.jp.service.UserTagService;

@Controller
@RequestMapping("userTag")
public class UserTagController {
	private final Logger log_ = LogManager.getLogger(UserTagController.class);
	@Autowired
	private UserTagService userTagService;

	/**
	 * 创建族圈用户标签
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/createUserTag", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse createUserTag(UserTag entity) {
		return userTagService.createUserTag(entity);
	}

	/**
	 * 删除族圈用户标签
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/removeUserTag", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse removeUserTag(UserTag entity) {
		return userTagService.removeUserTag(entity);
	}

	/**
	 * 编辑族圈用户标签
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/editUserTag", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse editUserTag(UserTag entity) {
		return userTagService.editUserTag(entity);
	}

	/**
	 * 获取族圈标签tag下的用户列表
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getTagUserList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getTagUserList(UserTag entity) {
		return userTagService.getTagUserList(entity);
	}

	/**
	 * 获取当前登录用户下的族圈标签列表
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getTagListByUserid", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getTagListByUserid(UserTag entity) {
		return userTagService.getTagListByUserid(entity);
	}

}
