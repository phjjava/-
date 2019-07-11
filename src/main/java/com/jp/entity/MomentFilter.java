package com.jp.entity;

import java.io.Serializable;

public class MomentFilter implements Serializable {
	private String momentid;

	/**
	 * 过滤id  用户id/分支id/标签id
	 */
	private String filterid;

	/**
	 * person个人   tag标签    branch分支
	 */
	private String filtertype;

	private static final long serialVersionUID = 1L;

	public String getMomentid() {
		return momentid;
	}

	public void setMomentid(String momentid) {
		this.momentid = momentid == null ? null : momentid.trim();
	}

	public String getFilterid() {
		return filterid;
	}

	public void setFilterid(String filterid) {
		this.filterid = filterid == null ? null : filterid.trim();
	}

	public String getFiltertype() {
		return filtertype;
	}

	public void setFiltertype(String filtertype) {
		this.filtertype = filtertype == null ? null : filtertype.trim();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", momentid=").append(momentid);
		sb.append(", filterid=").append(filterid);
		sb.append(", filtertype=").append(filtertype);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}