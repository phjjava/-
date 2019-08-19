package com.jp.entity;

import java.util.Date;

public class JpMember {
    private String mid;

    private String mname;

    private String mimgurl;

    private Integer member;

    private Date starttime;

    private Integer sort;

    private Integer memberstatus;
    
    private Date endtime;

    public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getMimgurl() {
        return mimgurl;
    }

    public void setMimgurl(String mimgurl) {
        this.mimgurl = mimgurl == null ? null : mimgurl.trim();
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getMemberstatus() {
        return memberstatus;
    }

    public void setMemberstatus(Integer memberstatus) {
        this.memberstatus = memberstatus;
    }
}