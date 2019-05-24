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
import com.jp.entity.Worship;
import com.jp.service.WorshipService;

@Controller
@RequestMapping("worship")
public class WorshipController {
	private final Logger log_ = LogManager.getLogger(WorshipController.class);
	@Autowired
	private WorshipService worshipService;

	/**
	 * 祭拜
	 * 
	 * @param worship
	 * @return
	 */
	@RequestMapping(value = "/worship", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse worship(Worship worship) {
		return worshipService.worship(worship);
	}

	/**
	 * 祭拜记录
	 * 
	 * @param worship
	 * @param start
	 *            页码
	 * @param count
	 *            每页数量
	 * @return
	 */
	@RequestMapping(value = "/getWorships", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getWorships(Worship worship, int start, int count) {
		return worshipService.getWorships(worship, start, count);
	}

	/**
	 * 我的祭拜记录
	 * 
	 * @param worship
	 * @param start
	 *            页码
	 * @param count
	 *            每页数量
	 * @return
	 */
	@RequestMapping(value = "/getMyWorships", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getMyWorships(Worship worship, int start, int count) {
		return worshipService.getMyWorships(worship, start, count);
	}

	/**
	 * 祭拜详情
	 * 
	 * @param worship
	 * @return
	 */
	@RequestMapping(value = "/getWorshipDetali", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getWorshipDetali(Worship worship) {
		return worshipService.getWorshipDetali(worship);
	}

	/**
	 * 祭品列表
	 * 
	 * @param worship
	 * @return
	 */
	@RequestMapping(value = "/getWorshipOblations", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getWorshipOblations(Worship worship) {
		return worshipService.getWorshipOblations(worship);
	}

	/**
	 * 上传遗像
	 * 
	 * @param worship
	 * @return
	 * @throws IOException
	 */
	/*
	 * @RequestMapping(value = "/uploadPhoto1", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public JsonResponse uploadPhoto1(HttpServletRequest request)
	 * throws IOException { BufferedReader reader = request.getReader();
	 * StringBuilder sb = new StringBuilder(); String line = null; while ((line =
	 * reader.readLine()) != null) { sb.append(line); } String jsonstr =
	 * sb.toString(); Worship worship = JacksonUtil.fromJsonToObject(jsonstr,
	 * Worship.class); return worshipService.uploadPhoto(worship); }
	 */
	// 上下等同效果
	/**
	 * 上传遗像
	 * 
	 * @param worship
	 * @return
	 */
	@RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse uploadPhoto(@RequestBody Worship worship) {
		return worshipService.uploadPhoto(worship);
	}

}
