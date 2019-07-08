package com.jp.entity;

import java.io.Serializable;

import com.jp.common.BaseOrder;

public class BranchKey extends BaseOrder implements Serializable {
	/**
	 * 分支唯一编号
	 */
	private String branchid;

	/**
	 * 家族唯一编号
	 */
	private String familyid;

	private static final long serialVersionUID = 1L;

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid == null ? null : branchid.trim();
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid == null ? null : familyid.trim();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", branchid=").append(branchid);
		sb.append(", familyid=").append(familyid);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}