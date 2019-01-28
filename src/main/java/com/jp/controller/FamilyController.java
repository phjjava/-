package com.jp.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.PageModel;
import com.jp.entity.SysFamily;
import com.jp.entity.SysUser;
import com.jp.entity.SysVersion;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.Userinfo;
import com.jp.service.FamilyService;
import com.jp.service.SysVersionService;
import com.jp.service.UserManagerService;
import com.jp.service.UserService;
import com.jp.util.Result;
import com.jp.util.StringTools;

@Controller
@RequestMapping("system/family")
public class FamilyController {
	private final Logger log_ = LogManager.getLogger(FamilyController.class);
	@Autowired
	private FamilyService familyService;
	@Autowired
	private SysVersionService sysVersionService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserManagerService userManagerService;
	/**
	 * 
	 * @描述 用户列表的查询
	 * @作者 sj
	 * @时间 2017年4月28日上午9:18:58
	 * @参数 @param pageModel
	 * @参数 @param model
	 * @参数 @return
	 * @return ModelMap
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<SysFamily> pageModel,SysFamily sysFamily,ModelMap model)  {
		try{
			familyService.selectFamilyList(pageModel, sysFamily);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						familyService.selectFamilyList(pageModel, sysFamily);
					}
				}
			}
			model.put("pageModel", pageModel);
			model.put("user", sysFamily);
		}catch(Exception e){
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
	     return "system/familyList";
	}
	/**
	 * 
	 * @描述 去家族新增或编辑界面
	 * @作者 sj
	 * @时间 2017年5月10日上午10:51:49
	 * @参数 @param request
	 * @参数 @param modelMap
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editFamily(HttpServletRequest request,ModelMap modelMap)  {
		try{
			SysFamily family = null;
			User user = null;
			String familyid = request.getParameter("familyid");
			if(StringTools.trimNotEmpty(familyid)){
				family = familyService.selectByPrimaryKey(familyid);
				UserManager manager = userManagerService.selectByFamilyId(familyid);
				if(manager != null){
					user = userService.selectByPrimaryKey(manager.getUserid());
				}
			}
			//初始化版本
			List<SysVersion> versionList = sysVersionService.getSysVersionList();
		    modelMap.put("family", family);
		    modelMap.put("versionList", versionList);
		    modelMap.put("user", user);
		}catch(Exception e){
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "system/family";
	}
	/**
	 * 
	 * @描述 家族新增或编辑
	 * @作者 sj
	 * @时间 2017年5月10日下午3:54:56
	 * @参数 @param user
	 * @参数 @param userInfo
	 * @参数 @param versin
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
    public Result mergeUser(User user, Userinfo userInfo, SysFamily family, ModelMap model) {
        Result result = new Result();
		try{
			result = familyService.merge(user, userInfo,family);
		}catch(Exception e){
            result.setStatus(1);
            result.setMsg(e.getMessage());
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
        return result;
	}
	/**
	 * 
	 * @描述 停用/启用家族
	 * @作者 sj
	 * @时间 2017年5月11日下午5:24:42
	 * @参数 @param user
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public String changeStatus(SysFamily family,HttpSession httpSession){
		String result = null;
		try{
			//HttpSession session=ServletActionContext.getRequest().getSession()；
			SysUser user = (SysUser) httpSession.getAttribute("systemUserContext");
			family.setUpdatetime(new Date());
			family.setUpdateid(user.getUserid());
			familyService.changeStatus(family);
			result = "1";
		}catch(Exception e){
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/validatePhone", method = RequestMethod.POST)
	public String validateName(HttpServletRequest request) {
		boolean flag = true;//默认通过验证
		String userid = StringTools.trimNotEmpty(request.getParameter("userid")) ? request.getParameter("userid").trim() : null;
		String familyid = StringTools.trimNotEmpty(request.getParameter("familyid")) ? request.getParameter("familyid").trim() : null;	
		String phone = StringTools.trimNotEmpty(request.getParameter("phone")) ? request.getParameter("phone").trim() : null;
		
		try {
			
			flag = userService.validatePhone(familyid, userid, phone);
			
		} catch (Exception e) {
			log_.error("[PLMERROR:]", e);
		}
		return flag ? "true" : "false";
	}
}
