package com.jp.entity;

import java.io.Serializable;

public class UsereduKey implements Serializable {
    /**
     * 用户ID
     */
    private String userid;

    /**
     * 院校唯一编号
     */
    private String eduid;

    private static final long serialVersionUID = 1L;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getEduid() {
        return eduid;
    }

    public void setEduid(String eduid) {
        this.eduid = eduid == null ? null : eduid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", eduid=").append(eduid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}