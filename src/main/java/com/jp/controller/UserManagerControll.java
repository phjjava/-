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

import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Function;
import com.jp.entity.UserManager;
import com.jp.service.FunctionService;
import com.jp.service.UserManagerService;

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
	public JsonResponse save(UserManager manager, String[] functionids) {
		return userManagerService.save(manager, functionids);
	}

	//
	// @RequestMapping(value = "/save", method = RequestMethod.POST)
	// @ResponseBody
	// public JsonResponse save(HttpServletRequest request, UserManager manager) {
	// Result result = null;
	// JsonResponse res = null;
	// Integer status = null;
	// String[] functionids = request.getParameterValues("functionids[]");
	// try {
	// if (StringTools.notEmpty(manager.getId())) {// 修改
	// status = userManagerService.update(manager, functionids);
	// } else {// 新增
	// manager.setId(UUIDUtils.getUUID());
	// status = userManagerService.insert(manager, functionids);
	// }
	// } catch (Exception e) {
	// status = 0;
	// e.printStackTrace();
	// log_.error("[JPSYSTEM]", e);
	// result = new Result(MsgConstants.SYS_ERROR);
	// res = new JsonResponse(result);
	// res.setData(status);
	// return res;
	// }
	// if (status == 0) {
	// result = new Result(MsgConstants.RESUL_FAIL);
	// res = new JsonResponse(result);
	// res.setData(status);
	// return res;
	// }
	// result = new Result(MsgConstants.RESUL_SUCCESS);
	// res = new JsonResponse(result);
	// res.setData(status);
	// return res;
	// }

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<UserManager> pageModel, UserManager entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			entity.setUserid(CurrentUserContext.getCurrentUserId());
			userManagerService.pageQuery(pageModel, entity);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						userManagerService.pageQuery(pageModel, entity);
					}
				}
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
		res.setData(pageModel);
		return res;
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
		UserManager manager = null;
		List<Function> treeList = new ArrayList<Function>();// 树状菜单
		try {
			// 获取当前管理员的id
			String id = request.getParameter("id");
			if (id != null && !"".equals(id)) {
				manager = userManagerService.getUserManager(id);
				if (manager != null) {
					manager.setGenlevel(manager.getGenlevel() + "世");
				}
			}
			String familyid = CurrentUserContext.getCurrentFamilyId();
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
	public JsonResponse getPost() {
		return userManagerService.getPost();

	}
}
