package com.jp.entity;

import java.io.Serializable;
import java.util.List;

public class EditorialBoard implements Serializable {
	/**
	 * id
	 */
	private String id;

	/**
	 * ��ί������
	 */
	private String name;

	/**
	 * ��ί������ 1�ܱ�0�ֱ�
	 */
	private Integer type;

	/**
	 * ��ί����� ʡ����������֧id
	 */
	private String code;

	/**
	 * AʡC��X��B��֧
	 */
	private String codetype;

	/**
	 * ����id
	 */
	private String familyid;

	/**
	 * ����
	 */
	private String eddesc;

	/**
	 * 排序
	 */
	private String sort;
	/*
	 * 自属性
	 */
	private List<Post> posts;

	List<UserManager> userManager;

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<UserManager> getUserManager() {
		return userManager;
	}

	public void setUserManager(List<UserManager> userManager) {
		this.userManager = userManager;
	}

	private static final long serialVersionUID = 1L;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

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
		return "EditorialBoard [id=" + id + ", name=" + name + ", type=" + type + ", code=" + code + ", codetype="
				+ codetype + ", familyid=" + familyid + ", eddesc=" + eddesc + ", sort=" + sort + "]";
	}
}