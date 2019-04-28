package com.jp.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangnn
 * @since 2019-04-25
 */
public class SysVersionPrivilege implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 特权id
     */
    private String id;
    /**
     * 版本唯一id
     */
    private String versionid;
    /**
     * 版本名称
     */
    private String versionname;
    /**
     * 特权code
     */
    private String privilegecode;
    /**
     * 特权名称
     */
    private String privilegename;
    /**
     * 特权值
     */
    private String privilegevalue;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 停用标记
     */
    private Integer deleteflag;
    private Date createtime;
    private String createby;
    private Date updatetime;
    private String updateby;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getPrivilegecode() {
        return privilegecode;
    }

    public void setPrivilegecode(String privilegecode) {
        this.privilegecode = privilegecode;
    }

    public String getPrivilegename() {
        return privilegename;
    }

    public void setPrivilegename(String privilegename) {
        this.privilegename = privilegename;
    }

    public String getPrivilegevalue() {
        return privilegevalue;
    }

    public void setPrivilegevalue(String privilegevalue) {
        this.privilegevalue = privilegevalue;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    @Override
    public String toString() {
        return "SysVersionPrivilege{" +
        "id=" + id +
        ", versionid=" + versionid +
        ", versionname=" + versionname +
        ", privilegecode=" + privilegecode +
        ", privilegename=" + privilegename +
        ", privilegevalue=" + privilegevalue +
        ", sort=" + sort +
        ", deleteflag=" + deleteflag +
        ", createtime=" + createtime +
        ", createby=" + createby +
        ", updatetime=" + updatetime +
        ", updateby=" + updateby +
        "}";
    }
}
