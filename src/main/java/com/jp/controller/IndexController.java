package com.jp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.LoginUserInfo;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.entity.Function;
import com.jp.entity.Indexcount;
import com.jp.entity.UserManager;
import com.jp.service.FamilyService;
import com.jp.service.UserContextService;
import com.jp.util.StringTools;
import com.jp.util.WebUtil;

@Controller
@RequestMapping("index")
public class IndexController {

	private final Logger log_ = LogManager.getLogger(IndexController.class);
	@Autowired
	private FamilyService familyService;
	@Autowired
	private UserContextService userContextService;

	/**
	 * 初始化菜单与场馆
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse initIndex(HttpServletRequest request, ModelMap modelMap) {
		Result result = null;
		JsonResponse res = null;
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		List<Function> functions = null;//所有菜单
		List<Function> menuFunctions = null;
		/*List<Function> parentFunctions = new ArrayList<Function>();//父节点
		List<Function> childFunctions = null;//子节点
		Map<String, List<Function>> childFunctionsMap = new HashMap<String, List<Function>>();*///key 父节点id value 子节点list

		try {
			LoginUserInfo userInfo = (LoginUserInfo) request.getSession().getAttribute("userContext");
			if (userInfo != null) {
				functions = userInfo.getFunctionList();
				menuFunctions = list2Tree(functions);
				/*for (Function function : functions) {
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
				}*/
			}
			List<String> branchids = userContextService.getBranchIds(familyid, userid);
			List<UserManager> userManager = userContextService.getUserManagers(userid);
			Indexcount countIndex = new Indexcount();
			for (UserManager um : userManager) {
				if (um.getEbtype() == 1) {
					countIndex = familyService.countIndex(familyid, null);
					break;
				} else {
					countIndex = familyService.countIndex(familyid, branchids);
					break;
				}

			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(menuFunctions);
			res.setData1(countIndex);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[PLMERROR:]", e);
		}
		return res;
	}

	public List<Function> list2Tree(List<Function> functionList) {
		List<Function> parentList = new ArrayList<>();
		for (Function function : functionList) {
			if ("00000".equals(function.getParentid())) {
				parentList.add(function);
			}
		}
		for (Function parent : parentList) {
			List<Function> childList = new ArrayList<>();
			for (Function function : functionList) {
				if (function.getParentid().equals(parent.getFunctionid())) {
					childList.add(function);
				}
			}
			parent.setChildList(childList);
		}
		return parentList;
	}

}
