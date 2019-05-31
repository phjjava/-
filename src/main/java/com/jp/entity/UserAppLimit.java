package com.jp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 用户登录返回给手机端的该用户的APP权限
 * 
 * @since 5.1.1
 * @version 5.1.1
 * @author xm*
 */
@JsonInclude(Include.NON_NULL)
public class UserAppLimit {

	private String loginStatus;// 登录状态

	private String role;// 用户角色

	private Integer lackdeptinfo;// 用户账号所属企业缺少组织架构信息(企业创建者才返回)

	private Integer lackuserinfo;// 用户账号缺少用户关键信息

	private Integer corpnum;// 企业数量

	public UserAppLimit() {
		super();
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getLackdeptinfo() {
		return lackdeptinfo;
	}

	public void setLackdeptinfo(Integer lackdeptinfo) {
		this.lackdeptinfo = lackdeptinfo;
	}

	public Integer getLackuserinfo() {
		return lackuserinfo;
	}

	public void setLackuserinfo(Integer lackuserinfo) {
		this.lackuserinfo = lackuserinfo;
	}

	public Integer getCorpnum() {
		return corpnum;
	}

	public void setCorpnum(Integer corpnum) {
		this.corpnum = corpnum;
	}

	public enum LoginStatus {
		// 枚举列表
		LOGIN_SUCCESS("LOGIN_SUCCESS"), CHOOSE_CORP("CHOOSE_CORP");

		// 私有附件变量
		private String loginStatusName;

		private LoginStatus(String loginStatusName) {
			this.loginStatusName = loginStatusName;
		}

		public String getLoginStatusName() {
			return loginStatusName;
		}

		public void setLoginStatusName(String loginStatusName) {
			this.loginStatusName = loginStatusName;
		}
	}

	/**
	 * 用户角色的枚举类型
	 */
	public enum Role {
		// 枚举列表
		CORP_CREATOR("CORP_CREATOR"), CORP_ADMIN("CORP_ADMIN"), GENERAL_USER("GENERAL_USER");

		// 私有附加变量
		private String rolename;

		private Role(String rolename) {
			this.rolename = rolename;
		}

		public String getRolename() {
			return rolename;
		}

		public void setRolename(String rolename) {
			this.rolename = rolename;
		}
	}
}
