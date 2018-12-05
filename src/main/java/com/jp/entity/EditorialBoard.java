package com.jp.entity;

import java.io.Serializable;

public class EditorialBoard implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 编委会名称
     */
    private String name;

    /**
     * 编委会类型 1总编0分编
     */
    private Integer type;

    /**
     * 编委会编码    省市区编码或分支id
     */
    private String code;

    /**
     * A省C市X区B分支
     */
    private String codetype;

    /**
     * 家族id
     */
    private String familyid;

    /**
     * 描述
     */
    private String eddesc;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype == null ? null : codetype.trim();
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid == null ? null : familyid.trim();
    }

    public String getEddesc() {
        return eddesc;
    }

    public void setEddesc(String eddesc) {
        this.eddesc = eddesc == null ? null : eddesc.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", code=").append(code);
        sb.append(", codetype=").append(codetype);
        sb.append(", familyid=").append(familyid);
        sb.append(", eddesc=").append(eddesc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}