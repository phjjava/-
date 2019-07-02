package com.jp.common;

import java.io.Serializable;
import java.util.List;

import com.jp.entity.Branch;
import com.jp.entity.Function;
import com.jp.entity.Role;
import com.jp.entity.User;
import com.jp.entity.UserManager;

/**
 * @功能 当前用户
 * @作者 liuht
 * @时间 2017年4月9日下午6:35:25
 */
public class LoginUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private Role role;

	private List<UserManager> usermanagers;

	private List<Function> functionList;
	private List<Branch> branchList;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Function> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public List<UserManager> getUsermanagers() {
		return usermanagers;
	}

	public void setUsermanagers(List<UserManager> usermanagers) {
		this.usermanagers = usermanagers;
	}

	@Override
	public String toString() {
		return "LoginUserInfo [user=" + user + ", role=" + role + ", usermanagers=" + usermanagers + ", functionList="
				+ functionList + ", branchList=" + branchList + "]";
	}

}
