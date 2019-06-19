package com.jp.entity;

public class Nation {
	private Integer id;

	private Integer nationcode;

	private String nationname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNationcode() {
		return nationcode;
	}

	public void setNationcode(Integer nationcode) {
		this.nationcode = nationcode;
	}

	public String getNationname() {
		return nationname;
	}

	public void setNationname(String nationname) {
		this.nationname = nationname == null ? null : nationname.trim();
	}
}