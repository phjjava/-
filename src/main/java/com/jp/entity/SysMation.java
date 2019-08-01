package com.jp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SysMation implements Serializable {
	/**
	 * mation唯一编号
	 */
	private String mationid;

	/**
	 * mation名称
	 */
	private String mationname;

	/**
	 * mation网页图片编号
	 */
	private String imgid;

	/**
	 * mation手机图片地址
	 */
	private String imgurl;

	/**
	 * mation标题
	 */
	private String mationtitle;

	/**
	 * mation内容
	 */
	private String mationcontent;
	
	/**
	 * 删除标记
	 */
	private Integer deleteflag;

	/**
	 * 创建人ID
	 */
	private String createid;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 更新人ID
	 */
	private String updateid;

	/**
	 * 更新时间
	 */
	private Date updatetime;
	/**
	 * 关联类型id
	 */
	private String relevanceid;
	/**
	 * 排序id
	 */
	private Integer sortid;
	/**
	 * 置顶(0不置顶,1置顶)
	 */
	private Integer stick;
	/**
	 * 计数
	 */
	private Integer count;
	
	/**
	 * 关联类型
	 */
	private Set<MationType> mationtype = new HashSet<MationType>();

	

	private static final long serialVersionUID = 1L;



	public String getMationid() {
		return mationid;
	}



	public void setMationid(String mationid) {
		this.mationid = mationid;
	}



	public String getMationname() {
		return mationname;
	}



	public void setMationname(String mationname) {
		this.mationname = mationname;
	}



	public String getImgid() {
		return imgid;
	}



	public void setImgid(String imgid) {
		this.imgid = imgid;
	}



	public String getImgurl() {
		return imgurl;
	}



	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}



	public String getMationtitle() {
		return mationtitle;
	}



	public void setMationtitle(String mationtitle) {
		this.mationtitle = mationtitle;
	}



	public String getMationcontent() {
		return mationcontent;
	}



	public void setMationcontent(String mationcontent) {
		this.mationcontent = mationcontent;
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
		this.createid = createid;
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
		this.updateid = updateid;
	}



	public Date getUpdatetime() {
		return updatetime;
	}



	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}



	public String getRelevanceid() {
		return relevanceid;
	}



	public void setRelevanceid(String relevanceid) {
		this.relevanceid = relevanceid;
	}



	public Integer getSortid() {
		return sortid;
	}



	public void setSortid(Integer sortid) {
		this.sortid = sortid;
	}



	public Integer getStick() {
		return stick;
	}



	public void setStick(Integer stick) {
		this.stick = stick;
	}



	public Integer getCount() {
		return count;
	}



	public void setCount(Integer count) {
		this.count = count;
	}



	public Set<MationType> getMationtype() {
		return mationtype;
	}



	public void setMationtype(Set<MationType> mationtype) {
		this.mationtype = mationtype;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public SysMation() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SysMation(String mationid, String mationname, String imgid, String imgurl, String mationtitle,
			String mationcontent, Integer deleteflag, String createid, Date createtime, String updateid,
			Date updatetime, String relevanceid, Integer sortid, Integer stick, Integer count,
			Set<MationType> mationtype) {
		super();
		this.mationid = mationid;
		this.mationname = mationname;
		this.imgid = imgid;
		this.imgurl = imgurl;
		this.mationtitle = mationtitle;
		this.mationcontent = mationcontent;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updateid = updateid;
		this.updatetime = updatetime;
		this.relevanceid = relevanceid;
		this.sortid = sortid;
		this.stick = stick;
		this.count = count;
		this.mationtype = mationtype;
	}



	@Override
	public String toString() {
		return "SysMation [mationid=" + mationid + ", mationname=" + mationname + ", imgid=" + imgid + ", imgurl="
				+ imgurl + ", mationtitle=" + mationtitle + ", mationcontent=" + mationcontent + ", deleteflag="
				+ deleteflag + ", createid=" + createid + ", createtime=" + createtime + ", updateid=" + updateid
				+ ", updatetime=" + updatetime + ", relevanceid=" + relevanceid + ", sortid=" + sortid + ", stick="
				+ stick + ", count=" + count + ", mationtype=" + mationtype + "]";
	}



	
}