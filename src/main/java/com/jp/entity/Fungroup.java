package com.jp.entity;

import java.io.Serializable;

public class Fungroup extends FungroupKey implements Serializable {
    /**
     * 功能组名
     */
    private String funcgroupname;

    /**
     * 家族唯一编号
     */
    private String familyid;

    private static final long serialVersionUID = 1L;

    public String getFuncgroupname() {
        return funcgroupname;
    }

    public void setFuncgroupname(String funcgroupname) {
        this.funcgroupname = funcgroupname == null ? null : funcgroupname.trim();
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid == null ? null : familyid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", funcgroupname=").append(funcgroupname);
        sb.append(", familyid=").append(familyid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}