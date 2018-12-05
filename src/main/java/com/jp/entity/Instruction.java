package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Instruction implements Serializable {
    /**
     * 家训唯一编号
     */
    private String instructionid;

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
     * 家训封面图片地址
     */
    private String imgurl;

    /**
     * 家族唯一编号
     */
    private String familyid;

    /**
     * 家训内容
     */
    private String instructioncontent;

    private static final long serialVersionUID = 1L;

    public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	public String getInstructionid() {
        return instructionid;
    }

    public void setInstructionid(String instructionid) {
        this.instructionid = instructionid == null ? null : instructionid.trim();
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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid == null ? null : familyid.trim();
    }

    public String getInstructioncontent() {
        return instructioncontent;
    }

    public void setInstructioncontent(String instructioncontent) {
        this.instructioncontent = instructioncontent == null ? null : instructioncontent.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", instructionid=").append(instructionid);
        sb.append(", createid=").append(createid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updateid=").append(updateid);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", familyid=").append(familyid);
        sb.append(", instructioncontent=").append(instructioncontent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}