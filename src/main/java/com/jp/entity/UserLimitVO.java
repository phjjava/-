package com.jp.entity;

public class UserLimitVO {
	// 名字
	String name;
	// 头像地址
	String imgurl;
	// 世系
	String genlevel;
	// 父亲或母亲姓名
	String parentname;
	// 母亲或父亲姓名
	String parentmatename;
	// 排行
	String postision;
	// 分支名称
	String branchname;
	// 常住地
	String homeplace;
	// 配偶名称
	String matename;
	// 是否直系 1：直系 0：非直系
	Integer isdirect;
	// 1在世0离世
	Integer livestatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLivestatus() {
		return livestatus;
	}

	public void setLivestatus(Integer livestatus) {
		this.livestatus = livestatus;
	}

	// 性别
	Integer sex;

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getIsdirect() {
		return isdirect;
	}

	public void setIsdirect(Integer isdirect) {
		this.isdirect = isdirect;
	}

	public String getParentmatename() {
		return parentmatename;
	}

	public void setParentmatename(String parentmatename) {
		this.parentmatename = parentmatename;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getGenlevel() {
		return genlevel;
	}

	public void setGenlevel(String genlevel) {
		this.genlevel = genlevel;
	}

	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public String getMatename() {
		return matename;
	}

	public void setMatename(String matename) {
		this.matename = matename;
	}

	public String getPostision() {
		return postision;
	}

	public void setPostision(String postision) {
		this.postision = postision;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getHomeplace() {
		return homeplace;
	}

	public void setHomeplace(String homeplace) {
		this.homeplace = homeplace;
	}

}
