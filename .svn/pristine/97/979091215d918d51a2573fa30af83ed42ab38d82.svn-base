package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Useralbum extends UseralbumKey implements Serializable {
    /**
     * 相册类别
     */
    private Integer type;

    /**
     * 相册名称
     */
    private String albumname;

    /**
     * 删除标记
     */
    private Integer deleteflag;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更细时间
     */
    private Date updatetime;

    /**
     * 相册简介
     */
    private String remark;

    /**
     * 创建者
     */
    private String createid;

    /**
     * 更新者
     */
    private String updateid;
    /**
     * 照片地址
     */
    private String imgurl;
    /**
     * 照片数量
     */
    private int photoNumbers;

    private static final long serialVersionUID = 1L;

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname == null ? null : albumname.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
    
    public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
    
	

	public int getPhotoNumbers() {
		return photoNumbers;
	}

	public void setPhotoNumbers(int photoNumbers) {
		this.photoNumbers = photoNumbers;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", type=").append(type);
        sb.append(", albumname=").append(albumname);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append(", sort=").append(sort);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", remark=").append(remark);
        sb.append(", createid=").append(createid);
        sb.append(", updateid=").append(updateid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}