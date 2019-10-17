package com.jp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysFunction implements Serializable {
	private String functionid;

	/**
	 * 功能名称
	 */
	private String functionname;

	/**
	 * 父功能id
	 */
	private String parentid;

	/**
	 * URL
	 */
	private String functionurl;

	private String createid;

	private Date createtime;

	private Date updatetime;

	/**
	 * icon图标
	 */
	private String updateid;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 状态0使用1停用
	 */
	private Integer status;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 描述
	 */
	private String description;
	/**
	 * 菜单标识码
	 */
	private String code;

	/**
	 * 
	 * 版本授权菜单  id   可能为空
	 */
	private String isfunctionid;

	/**
	 * 子菜单集合
	 */
	private List<SysFunction> childList;

	private static final long serialVersionUID = 1L;

	public List<SysFunction> getChildList() {
		return childList;
	}

	public void setChildList(List<SysFunction> childList) {
		this.childList = childList;
	}

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

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid == null ? null : createid.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getUpdateid() {
		return updateid;
	}

	public void setUpdateid(String updateid) {
		this.updateid = updateid == null ? null : updateid.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getIsfunctionid() {
		return isfunctionid;
	}

	public void setIsfunctionid(String isfunctionid) {
		this.isfunctionid = isfunctionid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "SysFunction [functionid=" + functionid + ", functionname=" + functionname + ", parentid=" + parentid
				+ ", functionurl=" + functionurl + ", createid=" + createid + ", createtime=" + createtime
				+ ", updatetime=" + updatetime + ", updateid=" + updateid + ", icon=" + icon + ", status=" + status
				+ ", sort=" + sort + ", description=" + description + ", code=" + code + ", isfunctionid="
				+ isfunctionid + ", childList=" + childList + "]";
	}
}