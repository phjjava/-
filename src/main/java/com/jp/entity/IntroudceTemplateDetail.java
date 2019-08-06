package com.jp.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class IntroudceTemplateDetail {
    private String id;

    private String templateid;

    private String type;

    private String title;

    private String createid;

    private Date createtime;

    private String updateid;

    private Date updatetime;

    private Integer deleteflag;

    private Integer sort;

    private String content;
    
    /**
	 * 关联类型
	 */
	private Set<IntroudceTemplate> introudceTemplate = new HashSet<IntroudceTemplate>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<IntroudceTemplate> getIntroudceTemplate() {
		return introudceTemplate;
	}

	public void setIntroudceTemplate(Set<IntroudceTemplate> introudceTemplate) {
		this.introudceTemplate = introudceTemplate;
	}

	public IntroudceTemplateDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IntroudceTemplateDetail(String id, String templateid, String type, String title, String createid,
			Date createtime, String updateid, Date updatetime, Integer deleteflag, Integer sort, String content,
			Set<IntroudceTemplate> introudceTemplate) {
		super();
		this.id = id;
		this.templateid = templateid;
		this.type = type;
		this.title = title;
		this.createid = createid;
		this.createtime = createtime;
		this.updateid = updateid;
		this.updatetime = updatetime;
		this.deleteflag = deleteflag;
		this.sort = sort;
		this.content = content;
		this.introudceTemplate = introudceTemplate;
	}

	@Override
	public String toString() {
		return "IntroudceTemplateDetail [id=" + id + ", templateid=" + templateid + ", type=" + type + ", title="
				+ title + ", createid=" + createid + ", createtime=" + createtime + ", updateid=" + updateid
				+ ", updatetime=" + updatetime + ", deleteflag=" + deleteflag + ", sort=" + sort + ", content="
				+ content + ", introudceTemplate=" + introudceTemplate + "]";
	}

    
}