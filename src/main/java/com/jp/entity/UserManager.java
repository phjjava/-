package com.jp.entity;

import java.io.Serializable;
import java.util.List;

public class UserManager implements Serializable {
	private String id;

	/**
	 * �û�id
	 */
	private String userid;

	/**
	 * ����
	 */
	private String username;

	/**
	 * ����id
	 */
	private String familyid;

	/**
	 * ְ��id
	 */
	private String postid;

	/**
	 * ְ������
	 */
	private String postname;

	/**
	 * ��ί��id
	 */
	private String ebid;

	/**
	 * ��ί������
	 */
	private String ebname;

	/**
	 * ��ί������1��0��
	 */
	private Integer ebtype;

	/**
	 * �Ƿ�Ϊ����1��0��
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

	private Integer genlevel;// 世系
	private String imgurl;
	private List<Userbranch> branchs;

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
				+ ", genlevel=" + genlevel + ", imgurl=" + imgurl + ", branchs=" + branchs + "]";
	}
}