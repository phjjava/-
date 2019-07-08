package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Dyread implements Serializable {
	/**
	 * 唯一ID
	 */
	private String id;

	/**
	 * 用户唯一ID
	 */
	private String userid;

	/**
	 * 阅读时间
	 */
	private Date createtime;

	/**
	 * 动态唯一编号
	 */
	private String dyid;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDyid() {
		return dyid;
	}

	public void setDyid(String dyid) {
		this.dyid = dyid == null ? null : dyid.trim();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", userid=").append(userid);
		sb.append(", createtime=").append(createtime);
		sb.append(", dyid=").append(dyid);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}