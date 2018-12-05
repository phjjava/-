package com.jp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.entity.Branch;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Function;
import com.jp.entity.Role;
import com.jp.entity.SysFunction;
import com.jp.entity.SysVersion;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.service.EditorialBoardService;
import com.jp.service.FunctionService;
import com.jp.service.RoleService;
import com.jp.service.UserManagerService;
import com.jp.util.GsonUtil;
import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("userManager")
public class UserManagerControll {

	private final Logger log_ = LogManager.getLogger(UserManagerControll.class);

	@Autowired
	private UserManagerService userManagerService;

	@Autowired
	private FunctionService functionService;
	
	

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, UserManager manager, ModelMap model) {
		Integer result = null;
		String[] functionids = request.getParameterValues("functionids[]");
		try {
			if (StringTools.notEmpty(manager.getId())) {// 修改
				result = userManagerService.update(manager,functionids);
			} else {// 新增
				manager.setId(UUIDUtils.getUUID());
				result = userManagerService.insert(manager,functionids);
			}
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<UserManager> pageModel, UserManager entity, ModelMap model) {
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
			model.put("pageModel", pageModel);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "editorial/usermanagerList";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public Result deleteEditorialBoard(EditorialBoard entity) {
		Result result=new Result();
		try {
			int status =userManagerService.del(entity.getId());
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
			String id = request.getParameter("id");
			if(id !=null && !"".equals(id)) {
				UserManager manager = userManagerService.getUserManager(id);
				model.put("manager", manager);
			}
			String familyid = CurrentUserContext.getCurrentFamilyId();
			List<Function> functionList = functionService.selectFunctionListByRoleid(familyid,"" );
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
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		
		return "editorial/usermanager";
	}

	/**
	 * @描述 根据用户获取编委会列表
	 * @作者 sj
	 * @时间 2017年5月5日上午11:03:44
	 * @参数 @param familyid
	 * @参数 @return
	 * @return String
	 */
//	@ResponseBody
//	@RequestMapping(value = "/selecteditorialBoardList", method = RequestMethod.POST)
//	public String selectRoleList() {
//		String gsonStr = null;
//		try {
//			List<EditorialBoard> list = userManagerService.selecteditorialBoardList(CurrentUserContext.getCurrentUserId());
//			gsonStr = GsonUtil.GsonString(list);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log_.error("[JPSYSTEM]", e);
//		}
//		return gsonStr;
//	}

}
