package com.jp.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jp.common.CurrentUserContext;
import com.jp.common.LoginUserInfo;
import com.jp.entity.Function;
import com.jp.entity.Indexcount;
import com.jp.service.FamilyService;

@Controller
@RequestMapping("index")
public class IndexController {
    
	private final Logger log_ = LogManager.getLogger(IndexController.class);
	@Autowired
	private FamilyService familyService;
	/**
	 * 初始化菜单与场馆
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String initIndex(HttpServletRequest request, ModelMap modelMap) {
		List<Function> functions = null;//所有菜单
		List<Function> parentFunctions = new ArrayList<Function>();//父节点
		List<Function> childFunctions = null;//子节点
		Map<String, List<Function>> childFunctionsMap = new HashMap<String, List<Function>>();//key 父节点id value 子节点list
	
		try {
			LoginUserInfo userInfo = (LoginUserInfo) request.getSession().getAttribute("userContext");
			if (userInfo != null) {
				functions = userInfo.getFunctionList();
				for (Function function : functions) {
					//遍历出所有父节点
					if("00000".equals(function.getParentid())){
						parentFunctions.add(function);//存储父节点
						//通过父节点找到对应所有子节点
						childFunctions = new ArrayList<Function>();
						for (Function childFunction : functions) {
							if (function.getFunctionid().equals(childFunction.getParentid())) {
								childFunctions.add(childFunction);//存储子节点
							}
						}
						childFunctionsMap.put(function.getFunctionid(), childFunctions);//父子关系
					}
				}
			}
			List<String> branchids = CurrentUserContext.getCurrentBranchIds();
//			Integer type = CurrentUserContext.getUserContext().getRole().getIsmanager();
//			if(type == 1){
//				branchids.clear();
//			}
			String familyid = CurrentUserContext.getCurrentFamilyId();
			Indexcount countIndex = familyService.countIndex(familyid, branchids);
			modelMap.put("parentFunctions", parentFunctions);
			modelMap.put("childFunctionsMap", childFunctionsMap);
			modelMap.put("countIndex", countIndex);
		} catch (Exception e) {
			log_.error("[PLMERROR:]", e);
		}
		return "/index";
	}
	
}
