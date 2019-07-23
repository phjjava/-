package com.jp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.service.WorshipAncestorDictService;


@Controller
@RequestMapping("worshipAncestorDict")
public class WorshipAncestorDictController {
	private final Logger log_ = LogManager.getLogger(WorshipAncestorDictController.class);
	@Autowired
	private WorshipAncestorDictService worshipAncestorDictService;
	/**
	 * 获取当前用户家族世系图
	 * @param familyid 家族id
	 * @return
	 */
	@RequestMapping(value = "/getWorshipAncestorDictList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getWorshipAncestorDictList(String  familyid) {
		return worshipAncestorDictService.getWorshipAncestorDictList(familyid);
	}
	/**
	 * 根据家族id以及世系代数 查询该家族改世系的人
	 * @param familyid
	 * @param genlevel
	 * @param familyname
	 * @param count 条数
	 * @param start 页码
	 * @return
	 */
	@RequestMapping(value = "/getWorshipAncestorList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getWorshipAncestorList(String familyid, Integer genlevel,String familyname,Integer count,Integer start) {
		System.out.println("pagesize="+count+"pageNo="+start);
		return worshipAncestorDictService.getWorshipAncestorList(familyid,  genlevel, familyname,count,start);
	}
}
