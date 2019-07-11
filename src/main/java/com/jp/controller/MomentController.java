package com.jp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.Moment;
import com.jp.service.MomentService;

@Controller
@RequestMapping("moment")
public class MomentController {

	@Autowired
	private MomentService momentService;

	/**
	 * 发布族圈
	 * 
	 * @author 陈小兵 19-03-29
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/createMoment", method = RequestMethod.POST)
	public JsonResponse createMoment(Moment entity, HttpServletRequest request) {
		return momentService.createMoment(entity, request);
	}

	/**
	 * 获取族圈列表
	 * 
	 * @author 陈小兵 19-03-29
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getMomentList", method = RequestMethod.GET)
	public JsonResponse getMomentList(Moment entity, HttpServletRequest request) {
		return momentService.getMomentList(entity, request);
	}

	/**
	 * 获取个人族圈列表
	 * 
	 * @author 陈小兵 19-03-29
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPersonMomentList", method = RequestMethod.GET)
	public JsonResponse getPersonMomentList(Moment entity, HttpServletRequest request) throws IOException {
		return momentService.getPersonMomentList(entity, request);
	}

	/**
	 * 删除族圈动态
	 * 
	 * @author 陈小兵 19-03-29
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/delMoment", method = RequestMethod.GET)
	public JsonResponse delMoment(Moment entity, HttpServletRequest request) throws IOException {
		return momentService.delMoment(entity, request);
	}

}
