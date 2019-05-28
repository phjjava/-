package com.jp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Userphoto extends UserphotoKey implements Serializable {
	/**
	 * 图片缩略图地址
	 */
	private String smallimgurl;

	/**
	 * 图片地址
	 */
	private String imgurl;

	/**
	 * 删除标记
	 */
	private Integer deleteflag;

	/**
	 * 上传时间
	 */
	private Date createtime;

	/**
	 * 图片描述
	 */
	private String description;

	/**
	 * 排序字段
	 */
	private Integer sort;

	/**
	 * 创建者
	 */
	private String createid;

	/**
	 * 更新者
	 */
	private String updateid;
	/**
	 * 更新时间
	 */
	private Date updatetime;

	private List<Userphoto> photos;

	private static final long serialVersionUID = 1L;

	public List<Userphoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Userphoto> photos) {
		this.photos = photos;
	}

	public String getSmallimgurl() {
		return smallimgurl;
	}

	public void setSmallimgurl(String smallimgurl) {
		this.smallimgurl = smallimgurl == null ? null : smallimgurl.trim();
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl == null ? null : imgurl.trim();
	}

	public Integer getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid == null ? null : createid.trim();
	}

	public String getUpdateid() {
		return updateid;
	}

	public void setUpdateid(String updateid) {
		this.updateid = updateid == null ? null : updateid.trim();
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		return "Userphoto [smallimgurl=" + smallimgurl + ", imgurl=" + imgurl + ", deleteflag=" + deleteflag
				+ ", createtime=" + createtime + ", description=" + description + ", sort=" + sort + ", createid="
				+ createid + ", updateid=" + updateid + ", updatetime=" + updatetime + ", photos=" + photos + "]";
	}
}