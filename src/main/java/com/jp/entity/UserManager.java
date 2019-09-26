package com.jp.entity;

import java.io.Serializable;
import java.util.List;

public class UserManager implements Serializable {
	private String id;

	/**
	 * 关联用户id
	 */
	private String userid;

	/**
	 * 姓名
	 */
	private String username;

	/**
	 *家族id
	 */
	private String familyid;

	/**
	 * 职务id
	 */
	private String postid;

	/**
	 * 职务名称
	 */
	private String postname;

	/**
	 * 编委会id
	 */
	private String ebid;

	/**
	 * 编委会名称
	 */
	private String ebname;

	/**
	 * 编委会类型1总0分
	 */
	private Integer ebtype;

	/**
	 * 是否为主任1是0否
	 */
	private Integer ismanager;

	/**
	 * 排序值
	 */
	private Integer sort;

	/**
	 * 自写属性
	 */
	private String address; // 地址

	private String eddesc; // 编委会简称

	private Integer genlevel;// 世系
	private String imgurl;
	private List<Userbranch> branchs;

	public String getEddesc() {
		return eddesc;
	}

	public void setEddesc(String eddesc) {
		this.eddesc = eddesc;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public List<Userbranch> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<Userbranch> branchs) {
		this.branchs = branchs;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getGenlevel() {
		return genlevel;
	}

	public void setGenlevel(Integer genlevel) {
		this.genlevel = genlevel;
	}

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid == null ? null : familyid.trim();
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid == null ? null : postid.trim();
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname == null ? null : postname.trim();
	}

	public String getEbid() {
		return ebid;
	}

	public void setEbid(String ebid) {
		this.ebid = ebid == null ? null : ebid.trim();
	}

	public String getEbname() {
		return ebname;
	}

	public void setEbname(String ebname) {
		this.ebname = ebname == null ? null : ebname.trim();
	}

	public Integer getEbtype() {
		return ebtype;
	}

	public void setEbtype(Integer ebtype) {
		this.ebtype = ebtype;
	}

	public Integer getIsmanager() {
		return ismanager;
	}

	public void setIsmanager(Integer ismanager) {
		this.ismanager = ismanager;
	}

	@Override
	public String toString() {
		return "UserManager [id=" + id + ", userid=" + userid + ", username=" + username + ", familyid=" + familyid
				+ ", postid=" + postid + ", postname=" + postname + ", ebid=" + ebid + ", ebname=" + ebname
				+ ", ebtype=" + ebtype + ", ismanager=" + ismanager + ", sort=" + sort + ", address=" + address
				+ ", eddesc=" + eddesc + ", genlevel=" + genlevel + ", imgurl=" + imgurl + ", branchs=" + branchs + "]";
	}
}