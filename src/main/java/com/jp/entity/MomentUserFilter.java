package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class MomentUserFilter implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 过滤类型ME不让看我,HE不看他
     */
    private String filterType;

    /**
     * 过滤用户userid
     */
    private String filterUserid;

    /**
     * 过滤用户名
     */
    private String fitlerUserName;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 创建者
     */
    private String createby;

    /**
     * 更新者
     */
    private String updateby;

    /**
     * 停用标记
     */
    private Integer deleteflag;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType == null ? null : filterType.trim();
    }

    public String getFilterUserid() {
        return filterUserid;
    }

    public void setFilterUserid(String filterUserid) {
        this.filterUserid = filterUserid == null ? null : filterUserid.trim();
    }

    public String getFitlerUserName() {
        return fitlerUserName;
    }

    public void setFitlerUserName(String fitlerUserName) {
        this.fitlerUserName = fitlerUserName == null ? null : fitlerUserName.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby == null ? null : updateby.trim();
    }

    public Integer getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(Integer deleteflag) {
        this.deleteflag = deleteflag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", filterType=").append(filterType);
        sb.append(", filterUserid=").append(filterUserid);
        sb.append(", fitlerUserName=").append(fitlerUserName);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", createby=").append(createby);
        sb.append(", updateby=").append(updateby);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}