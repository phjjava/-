package com.jp.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SysNotice {
	/**
	 * noticeid公告编号
	 */
	private String noticeid;


	/**
	 * noticetitle标题
	 */
	private String noticetitle;

	/**
	 * noticecontent内容
	 */
	private String noticecontent;
	
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
	 * 图片路径
	 */
	private String imgurl;
	/**
	 * 排序id
	 */
	private Integer sortid;
	/**
	 * 置顶(0不置顶,1置顶)
	 */
	private Integer stick;
	/**
	 * 计数浏览量
	 */
	private Integer count;
	/**
	 * 关联类型
	 */
	private Set<SysNoticeType> noticetype = new HashSet<SysNoticeType>();

	private static final long serialVersionUID = 1L;

	public String getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(String noticeid) {
		this.noticeid = noticeid;
	}

	public String getNoticetitle() {
		return noticetitle;
	}

	public void setNoticetitle(String noticetitle) {
		this.noticetitle = noticetitle;
	}

	public String getNoticecontent() {
		return noticecontent;
	}

	public void setNoticecontent(String noticecontent) {
		this.noticecontent = noticecontent;
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

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
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

	public Set<SysNoticeType> getNoticetype() {
		return noticetype;
	}

	public void setNoticetype(Set<SysNoticeType> noticetype) {
		this.noticetype = noticetype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SysNotice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SysNotice(String noticeid, String noticetitle, String noticecontent, Integer deleteflag, String createid,
			Date createtime, String updateid, Date updatetime, String relevanceid, String imgurl, Integer sortid,
			Integer stick, Integer count, Set<SysNoticeType> noticetype) {
		super();
		this.noticeid = noticeid;
		this.noticetitle = noticetitle;
		this.noticecontent = noticecontent;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updateid = updateid;
		this.updatetime = updatetime;
		this.relevanceid = relevanceid;
		this.imgurl = imgurl;
		this.sortid = sortid;
		this.stick = stick;
		this.count = count;
		this.noticetype = noticetype;
	}

	@Override
	public String toString() {
		return "SysNotice [noticeid=" + noticeid + ", noticetitle=" + noticetitle + ", noticecontent=" + noticecontent
				+ ", deleteflag=" + deleteflag + ", createid=" + createid + ", createtime=" + createtime + ", updateid="
				+ updateid + ", updatetime=" + updatetime + ", relevanceid=" + relevanceid + ", imgurl=" + imgurl
				+ ", sortid=" + sortid + ", stick=" + stick + ", count=" + count + ", noticetype=" + noticetype + "]";
	}

	
}
