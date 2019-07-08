package com.jp.entity;

public class UserDetail extends Userinfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 曾用名
	String usedname;
	// 在世状态
	private Byte livestatus;
	// 去世时间
	private String dietime;
	// 埋葬地
	private String fixplace;

	public Byte getLivestatus() {
		return livestatus;
	}

	public void setLivestatus(Byte livestatus) {
		this.livestatus = livestatus;
	}

	public String getDietime() {
		return dietime;
	}

	public void setDietime(String dietime) {
		this.dietime = dietime;
	}

	public String getFixplace() {
		return fixplace;
	}

	public void setFixplace(String fixplace) {
		this.fixplace = fixplace;
	}

	public String getUsedname() {
		return usedname;
	}

	public void setUsedname(String usedname) {
		this.usedname = usedname;
	}

}
