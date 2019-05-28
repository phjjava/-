package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class MomentComment implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 族圈信息id
     */
    private String momentId;

    /**
     * 回复人userid
     */
    private String userid;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复目标userid
     */
    private String targetUserid;

    /**
     * 回复目标名称
     */
    private String targetUserName;

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

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId == null ? null : momentId.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTargetUserid() {
        return targetUserid;
    }

    public void setTargetUserid(String targetUserid) {
        this.targetUserid = targetUserid == null ? null : targetUserid.trim();
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName == null ? null : targetUserName.trim();
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
        sb.append(", momentId=").append(momentId);
        sb.append(", userid=").append(userid);
        sb.append(", content=").append(content);
        sb.append(", targetUserid=").append(targetUserid);
        sb.append(", targetUserName=").append(targetUserName);
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