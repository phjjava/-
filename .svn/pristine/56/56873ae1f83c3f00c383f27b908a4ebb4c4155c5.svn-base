package com.jp.entity;

import java.io.Serializable;

public class DynamicfileKey implements Serializable {
    /**
     * 附件唯一编号
     */
    private String fileid;

    /**
     * 动态唯一编号
     */
    private String dyid;

    /**
     * 动态所属分支ID
     */
    private String branchid;

    private static final long serialVersionUID = 1L;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid == null ? null : fileid.trim();
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
        sb.append(", fileid=").append(fileid);
        sb.append(", dyid=").append(dyid);
        sb.append(", branchid=").append(branchid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}