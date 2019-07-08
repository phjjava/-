package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Branchalbum implements Serializable {
	private String albumid;

	private String branchid;

	private String familyid;

	private String albumname;

	private String remark;

	private String createid;

	private Date createtime;

	private String updateid;

	private Date updatetime;

	private Integer deleteflag;

	private String branchname;

	private Integer sort;

	private Integer type;

	private Integer albumNum;

	//添加额外显示属性
	private Integer photoNum; //相册图片数量

	private String prePhotoImg; //相册显示头图

	private Integer meautype;//获取相册列表得类型  0：家族  1：全部   2按城市编码获取 3按照分支获取

	private Long start;// 页数

	private Long count;// 每页条数

	public Integer getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(Integer photoNum) {
		this.photoNum = photoNum;
	}

	public String getPrePhotoImg() {
		return prePhotoImg;
	}

	public void setPrePhotoImg(String prePhotoImg) {
		this.prePhotoImg = prePhotoImg;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getMeautype() {
		return meautype;
	}

	public void setMeautype(Integer meautype) {
		this.meautype = meautype;
	}

	private static final long serialVersionUID = 1L;

	public String getAlbumid() {
		return albumid;
	}

	public void setAlbumid(String albumid) {
		this.albumid = albumid == null ? null : albumid.trim();
	}

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid == null ? null : branchid.trim();
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid == null ? null : familyid.trim();
	}

	public String getAlbumname() {
		return albumname;
	}

	public void setAlbumname(String albumname) {
		this.albumname = albumname == null ? null : albumname.trim();
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

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname == null ? null : branchname.trim();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", albumid=").append(albumid);
		sb.append(", branchid=").append(branchid);
		sb.append(", familyid=").append(familyid);
		sb.append(", albumname=").append(albumname);
		sb.append(", remark=").append(remark);
		sb.append(", createid=").append(createid);
		sb.append(", createtime=").append(createtime);
		sb.append(", updateid=").append(updateid);
		sb.append(", updatetime=").append(updatetime);
		sb.append(", deleteflag=").append(deleteflag);
		sb.append(", branchname=").append(branchname);
		sb.append(", type=").append(type);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}

	public Integer getAlbumNum() {
		return albumNum;
	}

	public void setAlbumNum(Integer albumNum) {
		this.albumNum = albumNum;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}