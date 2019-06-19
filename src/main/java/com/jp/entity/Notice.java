package com.jp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Notice implements Serializable {
	private String noticeid;

	private String branchid;

	private String familyid;

	/**
	 * 0家族公告1分支公告
	 */
	private Integer noticetype;

	private String noticetitle;

	private Integer deleteflag;

	private String createid;

	private Date createtime;

	private String createtimeStr;

	private String updateid;

	private Date updatetime;

	private String createname;

	private String imgurl;

	private String branchname;

	private Integer type;

	private String noticecontent;

	/**
	 * 用于存储复选栏分支id,可存多个用逗号间隔
	 */
	private String tobranchid;

	private List<Noticefile> noticeFiles;

	private Integer meautype;// 获取相册列表得类型 0：家族 1：全部 2按城市编码获取 3按照分支获取
	private Integer countReads;
	// 分页参数
	private Integer start;
	private Integer count;

	public Integer getMeautype() {
		return meautype;
	}

	public void setMeautype(Integer meautype) {
		this.meautype = meautype;
	}

	public Integer getCountReads() {
		return countReads;
	}

	public void setCountReads(Integer countReads) {
		this.countReads = countReads;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Noticefile> getNoticeFiles() {
		return noticeFiles;
	}

	public void setNoticeFiles(List<Noticefile> noticeFiles) {
		this.noticeFiles = noticeFiles;
	}

	private static final long serialVersionUID = 1L;

	public String getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(String noticeid) {
		this.noticeid = noticeid == null ? null : noticeid.trim();
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

	public Integer getNoticetype() {
		return noticetype;
	}

	public void setNoticetype(Integer noticetype) {
		this.noticetype = noticetype;
	}

	public String getNoticetitle() {
		return noticetitle;
	}

	public void setNoticetitle(String noticetitle) {
		this.noticetitle = noticetitle == null ? null : noticetitle.trim();
	}

	public Integer getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
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

	public String getCreatename() {
		return createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname == null ? null : createname.trim();
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl == null ? null : imgurl.trim();
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

	public String getNoticecontent() {
		return noticecontent;
	}

	public void setNoticecontent(String noticecontent) {
		this.noticecontent = noticecontent == null ? null : noticecontent.trim();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", noticeid=").append(noticeid);
		sb.append(", branchid=").append(branchid);
		sb.append(", familyid=").append(familyid);
		sb.append(", noticetype=").append(noticetype);
		sb.append(", noticetitle=").append(noticetitle);
		sb.append(", deleteflag=").append(deleteflag);
		sb.append(", createid=").append(createid);
		sb.append(", createtime=").append(createtime);
		sb.append(", updateid=").append(updateid);
		sb.append(", updatetime=").append(updatetime);
		sb.append(", createname=").append(createname);
		sb.append(", imgurl=").append(imgurl);
		sb.append(", branchname=").append(branchname);
		sb.append(", type=").append(type);
		sb.append(", noticecontent=").append(noticecontent);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}

	public String getTobranchid() {
		return tobranchid;
	}

	public void setTobranchid(String tobranchid) {
		this.tobranchid = tobranchid;
	}

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
}