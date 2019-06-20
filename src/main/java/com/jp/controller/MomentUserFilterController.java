package com.jp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.MomentUserFilter;
import com.jp.service.MomentUserFilterService;

@Controller
@RequestMapping("momentUserFilter")
public class MomentUserFilterController {

	@Autowired
	private MomentUserFilterService momentUserFilterService;

	/**
	 * 添加/删除族圈用户权限
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "updateUserFilter", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse updateUserFilter(MomentUserFilter entity) {
		return momentUserFilterService.updateUserFilter(entity);
	}

}
