package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Introduce implements Serializable {
    /**
     * 介绍唯一编号
     */
    private String introduceid;

    /**
     * 介绍章节标题
     */
    private String introducetitle;

    /**
     * 创建者ID
     */
    private String createid;

    /**
     * 创建时间
     */
    private Date createtime;
    private String createtimeStr;
    /**
     * 更新者ID
     */
    private String updateid;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 删除标记
     */
    private Integer deleteflag;

    /**
     * 章节排序
     */
    private Integer sort;

    /**
     * 家族唯一编号
     */
    private String familyid;

    /**
     * 介绍详情
     */
    private String introducedetail;
    
    /**
     * 章节类型
     */
    private String type;
     

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private static final long serialVersionUID = 1L;

    public String getIntroduceid() {
        return introduceid;
    }

    public void setIntroduceid(String introduceid) {
        this.introduceid = introduceid == null ? null : introduceid.trim();
    }

    public String getIntroducetitle() {
        return introducetitle;
    }

    public void setIntroducetitle(String introducetitle) {
        this.introducetitle = introducetitle == null ? null : introducetitle.trim();
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

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid == null ? null : familyid.trim();
    }

    public String getIntroducedetail() {
        return introducedetail;
    }

    public void setIntroducedetail(String introducedetail) {
        this.introducedetail = introducedetail == null ? null : introducedetail.trim();
    }

    public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", introduceid=").append(introduceid);
        sb.append(", introducetitle=").append(introducetitle);
        sb.append(", createid=").append(createid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updateid=").append(updateid);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append(", sort=").append(sort);
        sb.append(", familyid=").append(familyid);
        sb.append(", introducedetail=").append(introducedetail);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}