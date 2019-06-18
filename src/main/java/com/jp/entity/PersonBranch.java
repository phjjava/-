package com.jp.entity;

public class PersonBranch {

	private String name;// 地区或城市名称

	private Integer totalPerson;// 总人数

	private Integer totalBranch;// 分支总数

	private Integer direct;// 直系人数

	private Integer nonDirect;// 非直系人数

	private Integer man;// 男性成员

	private Integer woman;// 女性成员

	private Person alive;// 在世的人数

	private Person depart;// 离世人数

	// 分页参数
	private Integer start;
	private Integer count;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

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

	public Person getAlive() {
		return alive;
	}

	public void setAlive(Person alive) {
		this.alive = alive;
	}

	public Person getDepart() {
		return depart;
	}

	public void setDepart(Person depart) {
		this.depart = depart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
