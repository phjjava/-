package com.jp.entity;

public class SearchComplex {
	// 性别
	Integer sex;
	// 分支id
	String branchid;
	// 年龄
	Integer agefrom;
	// 年龄
	Integer ageto;
	// 婚否
	Integer ismarry;
	// 世系
	Integer genlevel;
	// 地区编码
	String areacode;
	// 直系
	Integer isdirect;
	// 在世离世
	Integer livestatus;
	// 家族id
	String familyid;
	// 学历
	String education;
	// 分页参数
	String start;// 角标
	String count;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public Integer getAgefrom() {
		return agefrom;
	}

	public void setAgefrom(Integer agefrom) {
		this.agefrom = agefrom;
	}

	public Integer getAgeto() {
		return ageto;
	}

	public void setAgeto(Integer ageto) {
		this.ageto = ageto;
	}

	public Integer getIsmarry() {
		return ismarry;
	}

	public void setIsmarry(Integer ismarry) {
		this.ismarry = ismarry;
	}

	public Integer getGenlevel() {
		return genlevel;
	}

	public void setGenlevel(Integer genlevel) {
		this.genlevel = genlevel;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public Integer getIsdirect() {
		return isdirect;
	}

	public void setIsdirect(Integer isdirect) {
		this.isdirect = isdirect;
	}

	public Integer getLivestatus() {
		return livestatus;
	}

	public void setLivestatus(Integer livestatus) {
		this.livestatus = livestatus;
	}

}
