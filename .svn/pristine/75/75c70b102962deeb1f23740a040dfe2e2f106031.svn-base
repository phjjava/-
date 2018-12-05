package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Userphoto extends UserphotoKey implements Serializable {
    /**
     * 图片缩略图地址
     */
    private String smallimgurl;

    /**
     * 图片地址
     */
    private String imgurl;

    /**
     * 删除标记
     */
    private Integer deleteflag;

    /**
     * 上传时间
     */
    private Date createtime;

    /**
     * 图片描述
     */
    private String description;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 创建者
     */
    private String createid;

    /**
     * 更新者
     */
    private String updateid;
    /**
     * 更新时间
     */
    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public String getSmallimgurl() {
        return smallimgurl;
    }

    public void setSmallimgurl(String smallimgurl) {
        this.smallimgurl = smallimgurl == null ? null : smallimgurl.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
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

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", smallimgurl=").append(smallimgurl);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append(", createtime=").append(createtime);
        sb.append(", description=").append(description);
        sb.append(", sort=").append(sort);
        sb.append(", createid=").append(createid);
        sb.append(", updateid=").append(updateid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}