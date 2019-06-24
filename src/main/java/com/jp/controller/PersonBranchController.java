package com.jp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.PersonBranch;
import com.jp.service.PersonBranchService;

@Controller
@RequestMapping("personBranch")
public class PersonBranchController {
	private final Logger log_ = LogManager.getLogger(PersonBranchController.class);
	@Autowired
	private PersonBranchService personBranchService;

	/**
	 * 获取统计信息
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getPersonBranchData", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getPersonBranchData(PersonBranch entity) {
		return personBranchService.getPersonBranchData(entity);
	}

	/**
	 * 获取分支统计
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getPersonBranchDataByBranch", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getPersonBranchDataByBranch(PersonBranch entity) {
		return personBranchService.getPersonBranchDataByBranch(entity);
	}

}
