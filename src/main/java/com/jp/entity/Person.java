package com.jp.entity;

public class Person {
	private Integer totalPerson;// 总人数

	private Integer totalBranch;// 分支总数

	private Integer direct;// 直系人数

	private Integer nonDirect;// 非直系人数
	private Integer unDirect;// 未知系人数

	private Integer man;// 男性成员

	private Integer woman;// 女性成员

	public Integer getTotalPerson() {
		return totalPerson;
	}

	public void setTotalPerson(Integer totalPerson) {
		this.totalPerson = totalPerson;
	}

	public Integer getTotalBranch() {
		return totalBranch;
	}

	public void setTotalBranch(Integer totalBranch) {
		this.totalBranch = totalBranch;
	}

	public Integer getDirect() {
		return direct;
	}

	public void setDirect(Integer direct) {
		this.direct = direct;
	}

	public Integer getNonDirect() {
		return nonDirect;
	}

	public void setNonDirect(Integer nonDirect) {
		this.nonDirect = nonDirect;
	}

	public Integer getMan() {
		return man;
	}

	public void setMan(Integer man) {
		this.man = man;
	}

	public Integer getWoman() {
		return woman;
	}

	public void setWoman(Integer woman) {
		this.woman = woman;
	}

	public Integer getUnDirect() {
		return unDirect;
	}

	public void setUnDirect(Integer unDirect) {
		this.unDirect = unDirect;
	}

}
