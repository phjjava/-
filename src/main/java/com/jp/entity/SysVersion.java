package com.jp.entity;

import java.io.Serializable;

public class SysVersion implements Serializable {
    /**
     * 版本唯一编号
     */
    private String versionid;

    /**
     * 版本名称
     */
    private String versionname;

    /**
     * 限制用户数
     */
    private Integer usercount;

    private static final long serialVersionUID = 1L;

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid == null ? null : versionid.trim();
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname == null ? null : versionname.trim();
    }

    public Integer getUsercount() {
        return usercount;
    }

    public void setUsercount(Integer usercount) {
        this.usercount = usercount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", versionid=").append(versionid);
        sb.append(", versionname=").append(versionname);
        sb.append(", usercount=").append(usercount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}