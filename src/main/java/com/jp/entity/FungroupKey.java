package com.jp.entity;

import java.io.Serializable;

public class FungroupKey implements Serializable {
    /**
     * 功能组唯一编号
     */
    private String funcgroupid;

    /**
     * 功能唯一编号
     */
    private String funcid;

    private static final long serialVersionUID = 1L;

    public String getFuncgroupid() {
        return funcgroupid;
    }

    public void setFuncgroupid(String funcgroupid) {
        this.funcgroupid = funcgroupid == null ? null : funcgroupid.trim();
    }

    public String getFuncid() {
        return funcid;
    }

    public void setFuncid(String funcid) {
        this.funcid = funcid == null ? null : funcid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", funcgroupid=").append(funcgroupid);
        sb.append(", funcid=").append(funcid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}