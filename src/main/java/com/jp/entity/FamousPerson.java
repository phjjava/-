package com.jp.entity;

import java.util.Date;

public class FamousPerson {
    private String famousid;

    private String famoustitle;

    private String famouscontent;

    private String createid;

    private Date createtime;

    private String updateid;

    private Date updatetime;

    private Integer deleteflag;

    private Integer sort;
    
    private String imgurl;
    
    public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getFamousid() {
        return famousid;
    }

    public void setFamousid(String famousid) {
        this.famousid = famousid == null ? null : famousid.trim();
    }

    public String getFamoustitle() {
        return famoustitle;
    }

    public void setFamoustitle(String famoustitle) {
        this.famoustitle = famoustitle == null ? null : famoustitle.trim();
    }

    public String getFamouscontent() {
        return famouscontent;
    }

    public void setFamouscontent(String famouscontent) {
        this.famouscontent = famouscontent == null ? null : famouscontent.trim();
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
}