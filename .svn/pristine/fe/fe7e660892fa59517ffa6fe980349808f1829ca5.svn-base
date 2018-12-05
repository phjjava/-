package com.jp.entity;

import java.io.Serializable;

public class Dytop implements Serializable {
    /**
     * 关系ID
     */
    private String id;

    /**
     * 分支唯一编号
     */
    private String branchid;

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

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid == null ? null : branchid.trim();
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
        sb.append(", branchid=").append(branchid);
        sb.append(", dyid=").append(dyid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}