package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class BannerHomePage implements Serializable {
	/**
	 * Banner唯一编号
	 */
	private String bannerid;

	/**
	 * Banner名称
	 */
	private String bannername;

	/**
	 * Banner网页图片地址
	 */
	private String bannerweburl;

	/**
	 * Banner手机图片地址
	 */
	private String bannerphoneurl;

	/**
	 * Banner跳转地址
	 */
	private String bannerurl;

	/**
	 * Banner描述
	 */
	private String bannerdesc;

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
	 * 备注
	 */
	private String remark;


	/**
	 * 跳转类型
	 */
	private Integer gotype;

	/**
	 * 删除标记
	 */
	private Integer deleteflag;
	private String goname;

	private static final long serialVersionUID = 1L;

	public String getBannerid() {
		return bannerid;
	}

	public void setBannerid(String bannerid) {
		this.bannerid = bannerid == null ? null : bannerid.trim();
	}

	public String getBannername() {
		return bannername;
	}

	public void setBannername(String bannername) {
		this.bannername = bannername == null ? null : bannername.trim();
	}

	public String getBannerweburl() {
		return bannerweburl;
	}

	public void setBannerweburl(String bannerweburl) {
		this.bannerweburl = bannerweburl == null ? null : bannerweburl.trim();
	}

	public String getBannerphoneurl() {
		return bannerphoneurl;
	}

	public void setBannerphoneurl(String bannerphoneurl) {
		this.bannerphoneurl = bannerphoneurl == null ? null : bannerphoneurl.trim();
	}

	public String getBannerurl() {
		return bannerurl;
	}

	public void setBannerurl(String bannerurl) {
		this.bannerurl = bannerurl == null ? null : bannerurl.trim();
	}

	public String getBannerdesc() {
		return bannerdesc;
	}

	public void setBannerdesc(String bannerdesc) {
		this.bannerdesc = bannerdesc == null ? null : bannerdesc.trim();
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}



	public Integer getGotype() {
		return gotype;
	}

	public void setGotype(Integer gotype) {
		this.gotype = gotype;
	}

	public Integer getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getGoname() {
		return goname;
	}

	public void setGoname(String goname) {
		this.goname = goname;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", bannerid=").append(bannerid);
		sb.append(", bannername=").append(bannername);
		sb.append(", bannerweburl=").append(bannerweburl);
		sb.append(", bannerphoneurl=").append(bannerphoneurl);
		sb.append(", bannerurl=").append(bannerurl);
		sb.append(", bannerdesc=").append(bannerdesc);
		sb.append(", createid=").append(createid);
		sb.append(", createtime=").append(createtime);
		sb.append(", updateid=").append(updateid);
		sb.append(", updatetime=").append(updatetime);
		sb.append(", remark=").append(remark);
		sb.append(", gotype=").append(gotype);
		sb.append(", deleteflag=").append(deleteflag);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}