package com.jp.entity;

import java.io.Serializable;
import java.util.List;

public class Function implements Serializable {
    private String functionid;

    private String functionname;

    private String parentid;

    private String functionurl;

    private String description;

    private Integer sort;

    private String familyid;

    /**
     * 图标
     */
    private String icon;
    
    /**
     * 
     * 版本授权菜单  id   可能为空
     */
    private String isfunctionid; 
    
    private List<Function> childList;	// 子菜单集合

    public List<Function> getChildList() {
		return childList;
	}

	public void setChildList(List<Function> childList) {
		this.childList = childList;
	}

	private static final long serialVersionUID = 1L;

    public String getFunctionid() {
        return functionid;
    }

    public void setFunctionid(String functionid) {
        this.functionid = functionid == null ? null : functionid.trim();
    }

    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname == null ? null : functionname.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getFunctionurl() {
        return functionurl;
    }

    public void setFunctionurl(String functionurl) {
        this.functionurl = functionurl == null ? null : functionurl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid == null ? null : familyid.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", functionid=").append(functionid);
        sb.append(", functionname=").append(functionname);
        sb.append(", parentid=").append(parentid);
        sb.append(", functionurl=").append(functionurl);
        sb.append(", description=").append(description);
        sb.append(", sort=").append(sort);
        sb.append(", familyid=").append(familyid);
        sb.append(", icon=").append(icon);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public String getIsfunctionid() {
		return isfunctionid;
	}

	public void setIsfunctionid(String isfunctionid) {
		this.isfunctionid = isfunctionid;
	}
    
}