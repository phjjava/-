package com.jp.entity;

import java.io.Serializable;

public class Indexcount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer applycount;
	private Integer usercount;
	private Integer shicount;
	private Integer branchcount;
	private Integer rolecount;

	public Integer getApplycount() {
		return applycount;
	}

	public void setApplycount(Integer applycount) {
		this.applycount = applycount;
	}

	public Integer getUsercount() {
		return usercount;
	}

	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
	}

	public Integer getShicount() {
		return shicount;
	}

	public void setShicount(Integer shicount) {
		this.shicount = shicount;
	}

	public Integer getBranchcount() {
		return branchcount;
	}

	public void setBranchcount(Integer branchcount) {
		this.branchcount = branchcount;
	}

	public Integer getRolecount() {
		return rolecount;
	}

	public void setRolecount(Integer rolecount) {
		this.rolecount = rolecount;
	}

}