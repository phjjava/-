package com.jp.entity;

import java.util.Date;

public class SysNoticeType {
	/**
	 * 类型id
	 */
	private String typeid;
	/**
	 * 类型名称
	 */
	private String typename;
	/**
	 * 排序编号
	 */
	private Integer sort;
	/**
	 * 创建人ID
	 */
	private String createid;

	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 删除标记
	 */
	private Integer deleteflag;
	
	private static final long serialVersionUID = 1L;

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SysNoticeType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SysNoticeType(String typeid, String typename, Integer sort, String createid, Date createtime,
			Integer deleteflag) {
		super();
		this.typeid = typeid;
		this.typename = typename;
		this.sort = sort;
		this.createid = createid;
		this.createtime = createtime;
		this.deleteflag = deleteflag;
	}

	@Override
	public String toString() {
		return "SysNoticeType [typeid=" + typeid + ", typename=" + typename + ", sort=" + sort + ", createid="
				+ createid + ", createtime=" + createtime + ", deleteflag=" + deleteflag + "]";
	}
	
	

}
