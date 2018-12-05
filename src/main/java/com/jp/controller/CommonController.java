package com.jp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.CurrentUserContext;

/**
 * @功能 基础控制器 获取数据权限
 * @作者 wumin
 * @时间 2017年5月18日上午10:54:41
 */
@Controller
@RequestMapping("common")
public class CommonController {
	
	private final Logger log_ = LogManager.getLogger(CommonController.class);
	
	/**
	 * @描述 获取当前登录用户的分支权限
	 * @作者 wumin
	 * @时间 2017年5月18日上午11:12:49
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/currentBranchJson", method = RequestMethod.POST)
    public String currentBranchJson(HttpServletRequest request)  {
    	String branchJson = null;
    	try {
    		branchJson = CurrentUserContext.getCurrentBranch();
		} catch (Exception e) {
			branchJson = "";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
    	return branchJson;
    }
	

}
