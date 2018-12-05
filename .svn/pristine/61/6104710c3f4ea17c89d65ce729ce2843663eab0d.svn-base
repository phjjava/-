package com.jp.entity;

import java.io.Serializable;

public class Userfuncgroup implements Serializable {
    /**
     * 用户功能组对应唯一编号
     */
    private String userfuncgroupid;

    /**
     * 用户唯一编号
     */
    private String userid;

    /**
     * 功能组ID
     */
    private String fungroupid;

    private static final long serialVersionUID = 1L;

    public String getUserfuncgroupid() {
        return userfuncgroupid;
    }

    public void setUserfuncgroupid(String userfuncgroupid) {
        this.userfuncgroupid = userfuncgroupid == null ? null : userfuncgroupid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getFungroupid() {
        return fungroupid;
    }

    public void setFungroupid(String fungroupid) {
        this.fungroupid = fungroupid == null ? null : fungroupid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userfuncgroupid=").append(userfuncgroupid);
        sb.append(", userid=").append(userid);
        sb.append(", fungroupid=").append(fungroupid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}