package com.jp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Dynamic implements Serializable {
	public String getTobranchid() {
		return tobranchid;
	}

	public void setTobranchid(String tobranchid) {
		this.tobranchid = tobranchid;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	private String dyid;

	private String branchid;

	private String familyid;

	/**
	 * 0家族动态1分支动态
	 */
	private Integer dytype;

	private String dytitle;

	private Integer deleteflag;

	private String imgurl;

	private String branchname;

	private String branchnamePlus;

	private String createid;

	private Date createtime;

	private String createtimeStr;

	private String updateid;

	private Date updatetime;

	private String createname;

	private Integer type;

	private String dycontent;

	private String tobranchid;
	private List<Dytop> dytops;
	private Integer readNum;

	private List<Dynamicfile> dynamicFiles;

	private Integer meautype;// 获取相册列表得类型 0：家族 1：全部 2按城市编码获取 3按照分支获取

	private int countComments;// 评论
	private int countReads;// 阅读
	private int countPrises;// 点赞
	private int countFiles;// 文件
	private Long start;// 页数
	private Long count;// 每页条数

	public List<Dytop> getDytops() {
		return dytops;
	}

	public void setDytops(List<Dytop> dytops) {
		this.dytops = dytops;
	}

	public String getBranchnamePlus() {
		return branchnamePlus;
	}

	public void setBranchnamePlus(String branchnamePlus) {
		this.branchnamePlus = branchnamePlus;
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

	public int getCountComments() {
		return countComments;
	}

	public void setCountComments(int countComments) {
		this.countComments = countComments;
	}

	public int getCountReads() {
		return countReads;
	}

	public void setCountReads(int countReads) {
		this.countReads = countReads;
	}

	public int getCountPrises() {
		return countPrises;
	}

	public void setCountPrises(int countPrises) {
		this.countPrises = countPrises;
	}

	public int getCountFiles() {
		return countFiles;
	}

	public void setCountFiles(int countFiles) {
		this.countFiles = countFiles;
	}

	public List<Dynamicfile> getDynamicFiles() {
		return dynamicFiles;
	}

	public void setDynamicFiles(List<Dynamicfile> dynamicFiles) {
		this.dynamicFiles = dynamicFiles;
	}

	private static final long serialVersionUID = 1L;

	public String getDyid() {
		return dyid;
	}

	public void setDyid(String dyid) {
		this.dyid = dyid == null ? null : dyid.trim();
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

	public Integer getDytype() {
		return dytype;
	}

	public void setDytype(Integer dytype) {
		this.dytype = dytype;
	}

	public String getDytitle() {
		return dytitle;
	}

	public void setDytitle(String dytitle) {
		this.dytitle = dytitle == null ? null : dytitle.trim();
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

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname == null ? null : branchname.trim();
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDycontent() {
		return dycontent;
	}

	public void setDycontent(String dycontent) {
		this.dycontent = dycontent == null ? null : dycontent.trim();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", dyid=").append(dyid);
		sb.append(", branchid=").append(branchid);
		sb.append(", familyid=").append(familyid);
		sb.append(", dytype=").append(dytype);
		sb.append(", dytitle=").append(dytitle);
		sb.append(", deleteflag=").append(deleteflag);
		sb.append(", imgurl=").append(imgurl);
		sb.append(", branchname=").append(branchname);
		sb.append(", createid=").append(createid);
		sb.append(", createtime=").append(createtime);
		sb.append(", updateid=").append(updateid);
		sb.append(", updatetime=").append(updatetime);
		sb.append(", createname=").append(createname);
		sb.append(", type=").append(type);
		sb.append(", dycontent=").append(dycontent);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
}