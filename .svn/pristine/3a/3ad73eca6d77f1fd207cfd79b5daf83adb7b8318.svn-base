package com.jp.entity;

import java.io.Serializable;

public class FunctionRole extends FunctionRoleKey implements Serializable {
    /**
     * 编委会id
     */
    private String ebid;

    private static final long serialVersionUID = 1L;

    public String getEbid() {
        return ebid;
    }

    public void setEbid(String ebid) {
        this.ebid = ebid == null ? null : ebid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ebid=").append(ebid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}