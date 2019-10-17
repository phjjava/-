package com.jp.entity;

public class GenUser {

	private String username;
	private String userid;
	private Integer sex;
	private String imgurl;
	private Integer genlevel;
	private Integer livestatus;
	private String brotherpos;
	private String branchid;
	private String branchname;

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getBrotherpos() {
		return brotherpos;
	}

	public void setBrotherpos(String brotherpos) {
		this.brotherpos = brotherpos;
	}

	public Integer getLivestatus() {
		return livestatus;
	}

	public void setLivestatus(Integer livestatus) {
		this.livestatus = livestatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getImgurl() {
		return imgurl == null ? "" : imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Integer getSex() {
		return sex == null ? 3 : sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getGenlevel() {
		return genlevel;
	}

	public void setGenlevel(Integer genlevel) {
		this.genlevel = genlevel;
	}

}
