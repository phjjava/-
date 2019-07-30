package com.jp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.entity.Branch;
import com.jp.entity.SysFamily;
import com.jp.entity.User;
import com.jp.entity.Userinfo;
import com.jp.entity.Version;
import com.jp.service.FamilyService;
import com.jp.service.UserService;

@Controller
@RequestMapping("family")
public class FamilyApiController {

	@Autowired
	private FamilyService familyService;
	@Autowired
	private UserService userService;

	private String username;
	private String phone;
	private String userid;
	private String familyid;
	private String versionname;
	private String surename;
	private com.jp.entity.User user;
	private Userinfo ui;
	private SysFamily sysFamily;

	/**
	 * 获取在用的版本号内容 - 注册家族
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public JsonResponse merge(HttpServletRequest request) throws IOException {
		JsonResponse res = null;
		Result result = null;
		String isValid = valide();
		if (isValid.equals("1")) {
			String userid = request.getHeader("userid");
			user.setUserid(userid);
			return familyService.mergeForApi(user, ui, sysFamily);
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数错误");
			res = new JsonResponse(result);
			return res;
		}

	}

	/**
	 * 
	 * @描述 创建家族
	 */
	@ResponseBody
	@RequestMapping(value = "/createFamily", method = RequestMethod.POST)
	public JsonResponse createFamily(User user, Branch branch, Userinfo userInfo, SysFamily family, String token) {
		return familyService.createFamily(user, branch, userInfo, family, token);
	}

	/**
	 * 搜索家族
	 * @param family
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchFamily", method = RequestMethod.POST)
	public JsonResponse searchFamily(SysFamily family) {
		return familyService.searchFamily(family);
	}

	public String valide() {
		//参数验证
		String result = "1";
		ui = new Userinfo();
		if (username == null || username.equals("")) {
			result = "0";
			return result;
		}
		user.setUsername(username);
		if (phone == null || phone.equals("")) {
			result = "0";
			return result;
		}
		user.setUserid(userid);
		user.setPhone(phone);
		ui.setTel(phone);

		if (sysFamily.getFamilyname() == null || sysFamily.getFamilyname().equals("")) {
			result = "0";
			return result;
		}
		if (sysFamily.getVersion() == null || sysFamily.getVersion().equals("")) {
			List<Version> list = familyService.selectList();
			if (list == null) {
				result = "0";
				return result;
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getVersionname().equals("普通版")) {
					sysFamily.setVersion(list.get(i).getVersionid());
					sysFamily.setVersionname(list.get(i).getVersionname());
				}
			}
		}
		List<User> userList = userService.validatePhoneForApi(familyid, userid, phone);
		if (userList != null && userList.size() < 2) {
			result = "1";
			return result;
		} else {
			result = "0";
			return result;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid;
	}

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public String getSurename() {
		return surename;
	}

	public void setSurename(String surename) {
		this.surename = surename;
	}

	public com.jp.entity.User getUser() {
		return user;
	}

	public void setUser(com.jp.entity.User user) {
		this.user = user;
	}

	public Userinfo getUi() {
		return ui;
	}

	public void setUi(Userinfo ui) {
		this.ui = ui;
	}

	public SysFamily getSysFamily() {
		return sysFamily;
	}

	public void setSysFamily(SysFamily sysFamily) {
		this.sysFamily = sysFamily;
	}

}
