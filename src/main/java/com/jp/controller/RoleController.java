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
import com.jp.common.PageModel;
import com.jp.entity.Function;
import com.jp.entity.Role;
import com.jp.entity.User;
import com.jp.service.FunctionService;
import com.jp.service.RoleService;
import com.jp.util.GsonUtil;
import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

@Controller
@RequestMapping("role")
public class RoleController {

	private final Logger log_ = LogManager.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private FunctionService functionService;

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Role role) {
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		Integer result = null;
		try {
			String functionids[] = request.getParameterValues("functionids[]");
			String roleType = request.getParameter("roleType");
			role.setFamilyid(familyid);
			switch (Integer.valueOf(roleType)) {
			case 1:
				role.setIsmanager(0);
				role.setType(1);
				break;
			case 2:
				role.setIsmanager(1);
				role.setType(0);
				break;
			case 3:
				role.setIsmanager(0);
				role.setType(0);
				break;
			default:
				role.setIsmanager(1);
				role.setType(1);
				break;
			}
			if (StringTools.notEmpty(role.getRoleid())) {// 修改
				result = roleService.update(role, functionids);
			} else {// 新增
				role.setRoleid(UUIDUtils.getUUID());
				result = roleService.insert(role, functionids);
			}

		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<Role> pageModel, Role role, ModelMap model) {
		try {
			//当前登录人 familyid
			String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			role.setFamilyid(familyid);
			roleService.pageQuery(pageModel, role);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						roleService.pageQuery(pageModel, role);
					}
				}
			}
			model.put("pageModel", pageModel);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "management/roleList";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public Result deleteUserRole(User user) {
		Result result = new Result();
		try {
			int status = roleService.del(user.getRoleid());
			result.setData(status);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result.setData(0);
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {

			String roleid = request.getParameter("roleid");
			//当前登录人 familyid
			String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			List<Function> functionList = functionService.selectFunctionListByRoleid(familyid, roleid);
			List<Function> plist = null;
			List<Function> clist = null;
			if (functionList != null) {
				// 一级菜单
				plist = new ArrayList<Function>();
				// 二级菜单
				clist = new ArrayList<Function>();
				Function function = null;
				// 循环所有菜单，将一级菜单和二级菜单还有三级封装到不同集合中
				for (int i = 0; i < functionList.size(); i++) {
					function = functionList.get(i);
					if ("00000".equals(function.getParentid())) {
						plist.add(function);
					} else {
						clist.add(function);
					}
				}
			}

			model.put("plist", plist);
			model.put("clist", clist);
			Role role = roleService.get(roleid);
			model.put("role", role);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "management/role";
	}

	/**
	 * @描述 新增用户初始化父亲 和 配偶
	 * @作者 sj
	 * @时间 2017年5月5日上午11:03:44
	 * @参数 @param familyid
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/selectRoleList", method = RequestMethod.POST)
	public String selectRoleList() {
		String gsonStr = null;
		try {
			//当前登录人 familyid
			String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			List<Role> roleList = roleService.selectRoleList(familyid);
			gsonStr = GsonUtil.GsonString(roleList);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return gsonStr;
	}

	/**
	 * api方法分割线--------------------------------------------------------
	 */

	/**
	 * 获取编委会列表以及成员
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getAdminList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getAdminList(Role entity) {
		return roleService.getAdminListNew(entity);
	}

	/**
	 * 获取编委会列表以及成员(新)
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getEditorilaBoardList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getEditorilaBoardList(Role entity) {
		return roleService.getEditorilaBoardList(entity);
	}

	/**
	 * 获取编委会列表以及成员(新v2)
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getEditorilaBoardListNew", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getEditorilaBoardListNew(Role entity) {
		return roleService.getEditorilaBoardListNew(entity);
	}

	/**
	 * 获取分支编委会所在的所有省、市列表
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getBranchAdminCityList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getBranchAdminCityList(Role entity) {
		return roleService.getBranchAdminCityList(entity);
	}

}
