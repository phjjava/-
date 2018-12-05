package com.jp.entity;

import java.io.Serializable;

public class UsermatesKey implements Serializable {
    private String userid;

    private String mateid;

    private static final long serialVersionUID = 1L;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getMateid() {
        return mateid;
    }

    public void setMateid(String mateid) {
        this.mateid = mateid == null ? null : mateid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", mateid=").append(mateid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}