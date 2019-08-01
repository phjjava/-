package com.jp.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GlobalSearch {

	//通讯录
	List<User> users;

	//家族动态
	List<Dynamic> dynamics;

	//家族大事记
	List<Event> events;

	//人物志
	List<UserContentVO> userContentVOs;

	//家族相册
	List<Branchalbum> branchAlbums;

	//查询内容
	@JsonIgnore
	String content;
	@JsonIgnore
	Long start;
	@JsonIgnore
	Long count;

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Dynamic> getDynamics() {
		return dynamics;
	}

	public void setDynamics(List<Dynamic> dynamics) {
		this.dynamics = dynamics;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<UserContentVO> getUserContentVOs() {
		return userContentVOs;
	}

	public void setUserContentVOs(List<UserContentVO> userContentVOs) {
		this.userContentVOs = userContentVOs;
	}

	public List<Branchalbum> getBranchAlbums() {
		return branchAlbums;
	}

	public void setBranchAlbums(List<Branchalbum> branchAlbums) {
		this.branchAlbums = branchAlbums;
	}

}
