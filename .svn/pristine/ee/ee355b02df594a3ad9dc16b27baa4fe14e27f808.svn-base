package com.jp.entity;

import java.io.Serializable;

public class Role implements Serializable {
    /**
     * 主键
     */
    private String roleid;

    /**
     * 角色名
     */
    private String rolename;

    /**
     * 角色排序
     */
    private String roledesc;

    /**
     * 家族id
     */
    private String familyid;

    private String funcgroupid;

    /**
     * 是否管理员
     */
    private Integer ismanager;

    private Integer type;

    private static final long serialVersionUID = 1L;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc == null ? null : roledesc.trim();
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid == null ? null : familyid.trim();
    }

    public String getFuncgroupid() {
        return funcgroupid;
    }

    public void setFuncgroupid(String funcgroupid) {
        this.funcgroupid = funcgroupid == null ? null : funcgroupid.trim();
    }

    public Integer getIsmanager() {
        return ismanager;
    }

    public void setIsmanager(Integer ismanager) {
        this.ismanager = ismanager;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleid=").append(roleid);
        sb.append(", rolename=").append(rolename);
        sb.append(", roledesc=").append(roledesc);
        sb.append(", familyid=").append(familyid);
        sb.append(", funcgroupid=").append(funcgroupid);
        sb.append(", ismanager=").append(ismanager);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}