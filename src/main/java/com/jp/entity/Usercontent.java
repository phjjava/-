package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Usercontent implements Serializable {
	/**
	 * 用户ID
	 */
	private String userid;

	/**
	 * 创建者
	 */
	private String createid;

	/**
	 * 更新者
	 */
	private String updateid;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 更新时间
	 */
	private Date updatetime;

	/**
	 * 图片地址
	 */
	private String imgurl;

	/**
	 * 轮播图显示标记
	 */
	private Integer issee;

	/**
	 * 阅读总量
	 */
	private Integer readcount;

	/**
	 * 排序
	 */
	private Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 家族ID
	 */
	private String familyid;

	/**
	 * 群英录
	 */
	private String content;

	/*自写字段*/

	/**
	 * 姓名
	 */

	private String username;

	/**
	 * 世系
	 */
	private String genlevel;

	/**
	 * 分支id
	 */
	private String branchid;

	/**
	 * 分支名称
	 */
	private String branchname;

	/**
	 * 地址
	 */
	private String address;

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private static final long serialVersionUID = 1L;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
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

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl == null ? null : imgurl.trim();
	}

	public Integer getIssee() {
		return issee;
	}

	public void setIssee(Integer issee) {
		this.issee = issee;
	}

	public Integer getReadcount() {
		return readcount;
	}

	public void setReadcount(Integer readcount) {
		this.readcount = readcount;
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid == null ? null : familyid.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGenlevel() {
		return genlevel;
	}

	public void setGenlevel(String genlevel) {
		this.genlevel = genlevel;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", userid=").append(userid);
		sb.append(", createid=").append(createid);
		sb.append(", updateid=").append(updateid);
		sb.append(", createtime=").append(createtime);
		sb.append(", updatetime=").append(updatetime);
		sb.append(", imgurl=").append(imgurl);
		sb.append(", issee=").append(issee);
		sb.append(", readcount=").append(readcount);
		sb.append(", familyid=").append(familyid);
		sb.append(", content=").append(content);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}