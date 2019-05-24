package com.jp.entity;

import java.util.List;

public class WorshipOblationVO {
	String typeid;// 分类id

	String typename;// 分类名称

	List<WorshipOblation> oblations;// 祭品列表

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

	public List<WorshipOblation> getOblations() {
		return oblations;
	}

	public void setOblations(List<WorshipOblation> oblations) {
		this.oblations = oblations;
	}
}
