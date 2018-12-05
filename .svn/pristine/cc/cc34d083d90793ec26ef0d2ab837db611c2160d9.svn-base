package com.jp.entity;

import java.io.Serializable;

public class Userrole implements Serializable {
    private String userid;

    private String roleid;

    private String familyid;
    /**
     * 回显多个分支
     */
    private String branchidStrs;
    /**
     * 查询条件
     */
    private String username;
    private static final long serialVersionUID = 1L;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid == null ? null : familyid.trim();
    }
    
    public String getBranchidStrs() {
		return branchidStrs;
	}

	public void setBranchidStrs(String branchidStrs) {
		this.branchidStrs = branchidStrs;
	}
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", roleid=").append(roleid);
        sb.append(", familyid=").append(familyid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}