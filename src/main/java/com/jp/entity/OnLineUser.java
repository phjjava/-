package com.jp.entity;

import java.util.Date;

import com.jp.util.TransferDateUtil;

public class OnLineUser {
	// 登录用户id
	private String loginUserId;
	// 登陆用户姓名
	private String loginUserName;
	// 登陆用户所属部门
	private String loginUserDept;
	// 登陆方式
	private String loginType;// Android IOS PC
	// 登陆使用网络类型
	private String internetType;// 2/3/4G WIFI 其他
	// 登陆IP
	private String loginIp;
	// 登陆时间
	private Date loginTime;
	// 版本信息
	private String version;
	private String familyid;

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid;
	}

	public OnLineUser() {

	}

	public OnLineUser(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getTimeReduce() {
		long now = new Date().getTime();
		long time2 = this.loginTime.getTime();
		long diff = now - time2;
		long days = diff / (1000 * 60);
		return days + "";
	}

	public String getTime() {
		if (loginTime == null) {
			return null;
		}
		return TransferDateUtil.formatDateTime(loginTime);
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginUserDept() {
		return loginUserDept;
	}

	public void setLoginUserDept(String loginUserDept) {
		this.loginUserDept = loginUserDept;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getInternetType() {
		return internetType;
	}

	public void setInternetType(String internetType) {
		this.internetType = internetType;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OnLineUser other = (OnLineUser) obj;
		if (loginUserId.equals(other.getLoginUserId())) {
			return true;
		} else {
			return false;
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
