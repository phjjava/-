package com.jp.entity;

import java.util.Date;
import java.util.Set;

public class JpXing {
    private String id;

    private String xname;

    private String intro;

    private String createid;

    private Date createtime;

    private String updateid;

    private Date updatetime;

    private Integer sort;

    private Integer deleteflag;
    
    private Integer rcount;
    
    private Set<JpXingContent> xingContent;

	public Set<JpXingContent> getXingContent() {
		return xingContent;
	}

	public void setXingContent(Set<JpXingContent> xingContent) {
		this.xingContent = xingContent;
	}

	public Integer getRcount() {
		return rcount;
	}

	public void setRcount(Integer rcount) {
		this.rcount = rcount;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getXname() {
        return xname;
    }

    public void setXname(String xname) {
        this.xname = xname == null ? null : xname.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
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
        this.updateid = updateid == null ? null : updateid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(Integer deleteflag) {
        this.deleteflag = deleteflag;
    }
}