package com.jp.entity;

import java.io.Serializable;

public class DypriseKey implements Serializable {
    /**
     * 唯一编号
     */
    private String praiseid;

    /**
     * 动态唯一编号
     */
    private String dyid;

    /**
     * 动态所属分支ID
     */
    private String branchid;

    private static final long serialVersionUID = 1L;

    public String getPraiseid() {
        return praiseid;
    }

    public void setPraiseid(String praiseid) {
        this.praiseid = praiseid == null ? null : praiseid.trim();
    }

    public String getDyid() {
        return dyid;
    }

    public void setDyid(String dyid) {
        this.dyid = dyid == null ? null : dyid.trim();
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid == null ? null : branchid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", praiseid=").append(praiseid);
        sb.append(", dyid=").append(dyid);
        sb.append(", branchid=").append(branchid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}