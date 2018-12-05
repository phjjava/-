package com.jp.entity;

import java.util.Date;

public class WorshipOblationType {
    private String id;

    /**
     * 分类名称
     */
    private String typename;

    private String craeteid;

    private String craetename;

    private Date createtime;

    /**
     * 排序
     */
    private Integer sort;

    private Integer deleteflag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

    public String getCraeteid() {
        return craeteid;
    }

    public void setCraeteid(String craeteid) {
        this.craeteid = craeteid == null ? null : craeteid.trim();
    }

    public String getCraetename() {
        return craetename;
    }

    public void setCraetename(String craetename) {
        this.craetename = craetename == null ? null : craetename.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", typename=").append(typename);
        sb.append(", craeteid=").append(craeteid);
        sb.append(", craetename=").append(craetename);
        sb.append(", createtime=").append(createtime);
        sb.append(", sort=").append(sort);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append("]");
        return sb.toString();
    }
}