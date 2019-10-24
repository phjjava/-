package com.jp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Function;
import com.jp.entity.UserManager;
import com.jp.service.FunctionService;
import com.jp.service.UserManagerService;
import com.jp.util.StringTools;
import com.jp.util.WebUtil;

@Controller
@RequestMapping("userManager")
public class UserManagerControll {

	private final Logger log_ = LogManager.getLogger(UserManagerControll.class);

	@Autowired
	private UserManagerService userManagerService;

	@Autowired
	private FunctionService functionService;

	/**
	 * 保存或编辑管理员,入参有id编辑，无id新增
	 * 
	 * @param manager
	 * @param functionids
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse save(UserManager manager, HttpServletRequest request) {
		String[] functionids = request.getParameterValues("functionids[]");
		return userManagerService.save(manager, functionids);
	}

	/**
	 * 分页查询管理员数据
	 * @param pageModel
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<UserManager> pageModel, UserManager entity) {
		return userManagerService.pageQuery(pageModel, entity);
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteEditorialBoard(EditorialBoard entity) {
		return userManagerService.del(entity.getId());
	}

	/**
	 * 获取编委会详情和菜单列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request) {
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
		UserManager manager = null;
		List<Function> treeList = new ArrayList<Function>();// 树状菜单
		try {
			// 获取当前管理员的id
			String id = request.getParameter("id");
			if (id != null && !"".equals(id)) {
				manager = userManagerService.getUserManager(id);
				if (manager != null) {
					manager.setGenlevel(manager.getGenlevel());
				}
			}
			List<Function> functionList = new ArrayList<>();
			if (manager == null || (manager.getIsmanager() == 1 && manager.getEbtype() == 1)) {
				functionList = functionService.selectFunctionListByEbid(familyid, "", "", "");
			} else {
				functionList = functionService.selectFunctionListByEbid(familyid, manager.getUserid(),
						manager.getEbid(), manager.getPostid());
			}

			if (functionList != null) {
				Function function = null;
				// 先找到所有的一级菜单
				for (int i = 0; i < functionList.size(); i++) {
					function = functionList.get(i);
					// 一级菜单父ID为00000
					if ("00000".equals(function.getParentid())) {
						treeList.add(function);
					}
				}
			}
			// 为一级菜单设置子菜单，getChild是递归调用的
			for (Function fun : treeList) {
				fun.setChildList(getChild(fun.getFunctionid(), functionList));
			}

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setEntity(manager);
		res.setData(treeList);
		return res;
	}

	/**
	 * 子菜单递归
	 * 
	 * @param id
	 * @param rootMenu
	 * @return
	 */
	private List<Function> getChild(String id, List<Function> rootFun) {
		// 子菜单
		List<Function> childList = new ArrayList<>();
		for (Function fun : rootFun) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (fun.getParentid().equals(id)) {
				childList.add(fun);
			}
		}

		// 把子菜单的子菜单再循环一遍
		for (Function fun : childList) {
			fun.setChildList(getChild(fun.getFunctionid(), rootFun));// 递归
		}

		// 判断递归结束
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}

	/**
	 * @描述 根据用户获取编委会列表
	 * @作者 sj
	 * @时间 2017年5月5日上午11:03:44
	 * @参数 @param familyid
	 * @参数 @return
	 * @return String
	 */
	// @ResponseBody
	// @RequestMapping(value = "/selecteditorialBoardList", method =
	// RequestMethod.POST)
	// public String selectRoleList() {
	// String gsonStr = null;
	// try {
	// List<EditorialBoard> list =
	// userManagerService.selecteditorialBoardList(CurrentUserContext.getCurrentUserId());
	// gsonStr = GsonUtil.GsonString(list);
	// } catch (Exception e) {
	// e.printStackTrace();
	// log_.error("[JPSYSTEM]", e);
	// }
	// return gsonStr;
	// }

	/**
	 * 获取职务的下拉列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPost", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getPost(int type) {
		return userManagerService.getPost(type);
	}

	/**
	 * 查询当前登录人管理的编委会列表(切换编委会使用)
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/getManagerList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getManagerByUserid(String ebid) {
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
		try {
			List<UserManager> managerList = userManagerService.selectManagerByUserid(userid, ebid);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(managerList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
	}

}
