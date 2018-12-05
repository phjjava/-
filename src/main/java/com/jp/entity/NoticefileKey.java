package com.jp.entity;

import java.io.Serializable;

public class NoticefileKey implements Serializable {
    /**
     * 公告唯一编号
     */
    private String noticeid;

    /**
     * 附件唯一编号
     */
    private String fileid;

    /**
     * 公告所属分支ID
     */
    private String branchid;

    private static final long serialVersionUID = 1L;

    public String getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(String noticeid) {
        this.noticeid = noticeid == null ? null : noticeid.trim();
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid == null ? null : fileid.trim();
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
        sb.append(", noticeid=").append(noticeid);
        sb.append(", fileid=").append(fileid);
        sb.append(", branchid=").append(branchid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}