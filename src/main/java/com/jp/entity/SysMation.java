package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public SysMation() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SysMation(String mationid, String mationname, String imgid, String imgurl, String mationtitle,
			String mationcontent, Integer deleteflag, String createid, Date createtime, String updateid,
			Date updatetime) {
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
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", mationid=").append(mationid);
		sb.append(", mationname=").append(mationname);
		sb.append(", imgid=").append(imgid);
		sb.append(", imgurl=").append(imgurl);
		sb.append(", mationtitle=").append(mationtitle);
		sb.append(", mationcontent=").append(mationcontent);
		sb.append(", deleteflag=").append(deleteflag);
		sb.append(", createid=").append(createid);
		sb.append(", createtime=").append(createtime);
		sb.append(", updateid=").append(updateid);
		sb.append(", updatetime=").append(updatetime);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}