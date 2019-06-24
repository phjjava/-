package com.jp.entity;

import java.util.List;

public class EditorialBoardUser {

	// 用户ID
	private String userid;
	// 姓名
	private String username;
	// 头像
	private String imgurl;
	// 是否管理者
	private int ismanager;
	// 管理分支
	private List<Branch> branchs;
	// 世系
	private Integer genlevel;

	public Integer getGenlevel() {
		return genlevel;
	}

	public void setGenlevel(Integer genlevel) {
		this.genlevel = genlevel;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public int getIsmanager() {
		return ismanager;
	}

	public void setIsmanager(int ismanager) {
		this.ismanager = ismanager;
	}

	public List<Branch> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<Branch> branchs) {
		this.branchs = branchs;
	}

}
