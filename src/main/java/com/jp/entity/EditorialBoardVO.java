package com.jp.entity;

import java.util.List;

public class EditorialBoardVO {

	private String name;

	private String id;
	// 编委会类型
	private int type;

	List<EditorialBoardUser> editorialBoardUsers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<EditorialBoardUser> getEditorialBoardUsers() {
		return editorialBoardUsers;
	}

	public void setEditorialBoardUsers(List<EditorialBoardUser> editorialBoardUsers) {
		this.editorialBoardUsers = editorialBoardUsers;
	}

}
