package com.jp.entity;

import java.util.List;

public class WorshipVO {

	List<Worship> oblationImgs;// 获取祭拜未失效的记录

	public List<Worship> getOblationImgs() {
		return oblationImgs;
	}

	public void setOblationImgs(List<Worship> oblationImgs) {
		this.oblationImgs = oblationImgs;
	}

	String img;
	// Map<String,Object> oblationImg;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
