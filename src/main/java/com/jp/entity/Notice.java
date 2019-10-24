package com.jp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
	private String branchnamePlus;

	private Integer type;

	private String noticecontent;
	//审核状态字段
	private Integer examinestatus;
	//流程taskid字段
	private String taskid;
	//存放编委会名称
	private String leard;
	

	public String getLeard() {
		return leard;
	}

	public void setLeard(String leard) {
		this.leard = leard;
	}

	private List<HttpApprovalHistory> record;

	public List<HttpApprovalHistory> getRecord() {
		return record;
	}

	public List<HttpApprovalHistory> setRecord(List<HttpApprovalHistory> httpApprovalHistoryList) {
		return this.record = httpApprovalHistoryList;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public Integer getExaminestatus() {
		return examinestatus;
	}

	public void setExaminestatus(Integer examinestatus) {
		this.examinestatus = examinestatus;
	}

	/**
	 * 用于存储复选栏分支id,可存多个用逗号间隔
	 */
	private String tobranchid;
	private List<Noticetop> noticetops;

	private List<Noticefile> noticeFiles;

	private Integer meautype;// 获取相册列表得类型 0：家族 1：全部 2按城市编码获取 3按照分支获取
	private Integer countReads;
	// 分页参数
	private Integer start;
	private Integer count;

	public List<Noticetop> getNoticetops() {
		return noticetops;
	}

	public void setNoticetops(List<Noticetop> noticetops) {
		this.noticetops = noticetops;
	}

	public String getBranchnamePlus() {
		return branchnamePlus;
	}

	public void setBranchnamePlus(String branchnamePlus) {
		this.branchnamePlus = branchnamePlus;
	}

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
		return "Notice [noticeid=" + noticeid + ", branchid=" + branchid + ", familyid=" + familyid + ", noticetype="
				+ noticetype + ", noticetitle=" + noticetitle + ", deleteflag=" + deleteflag + ", createid=" + createid
				+ ", createtime=" + createtime + ", createtimeStr=" + createtimeStr + ", updateid=" + updateid
				+ ", updatetime=" + updatetime + ", createname=" + createname + ", imgurl=" + imgurl + ", branchname="
				+ branchname + ", branchnamePlus=" + branchnamePlus + ", type=" + type + ", noticecontent="
				+ noticecontent + ", tobranchid=" + tobranchid + ", noticetops=" + noticetops + ", noticeFiles="
				+ noticeFiles + ", meautype=" + meautype + ", countReads=" + countReads + ", start=" + start
				+ ", count=" + count + "]";
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