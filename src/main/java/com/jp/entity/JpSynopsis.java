package com.jp.entity;

import java.util.Date;

public class JpSynopsis {
    private String id;

    private String title;

    private String synopsis;

    private String createid;

    private Date createtime;

    private String updateid;

    private Date updatetime;

    private Integer deleteflag;

    private Integer sort;
    
    private String imgurl;
    
    private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
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

	public String getUpdateid() {
		return updateid;
	}

	public void setUpdateid(String updateid) {
		this.updateid = updateid;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JpSynopsis() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JpSynopsis(String id, String title, String synopsis, String createid, Date createtime, String updateid,
			Date updatetime, Integer deleteflag, Integer sort, String imgurl) {
		super();
		this.id = id;
		this.title = title;
		this.synopsis = synopsis;
		this.createid = createid;
		this.createtime = createtime;
		this.updateid = updateid;
		this.updatetime = updatetime;
		this.deleteflag = deleteflag;
		this.sort = sort;
		this.imgurl = imgurl;
	}

	@Override
	public String toString() {
		return "JpSynopsis [id=" + id + ", title=" + title + ", synopsis=" + synopsis + ", createid=" + createid
				+ ", createtime=" + createtime + ", updateid=" + updateid + ", updatetime=" + updatetime
				+ ", deleteflag=" + deleteflag + ", sort=" + sort + ", imgurl=" + imgurl + "]";
	}

    
}