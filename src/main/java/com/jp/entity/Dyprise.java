package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Dyprise extends DypriseKey implements Serializable {
	/**
	 * 创建者ID
	 */
	private String createid;

	/**
	 * 创建者姓名
	 */
	private String createname;

	/**
	 * 创建时间
	 */
	private Date createtime;

	private static final long serialVersionUID = 1L;

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid == null ? null : createid.trim();
	}

	public String getCreatename() {
		return createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname == null ? null : createname.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", createid=").append(createid);
		sb.append(", createname=").append(createname);
		sb.append(", createtime=").append(createtime);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}