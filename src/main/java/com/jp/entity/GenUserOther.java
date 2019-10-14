package com.jp.entity;

public class GenUserOther {
	String username;
	String userid;
	Integer sex;
	String imgurl;
	Integer genlevel;
	Integer livestatus;
	String pid;
	String brotherpos;
	GenUserOther mate;

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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public GenUserOther getMate() {
		return mate;
	}

	public void setMate(GenUserOther mate) {
		this.mate = mate;
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
