package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
	/**
	 * 大事记唯一编号
	 */
	private String eventid;

	/**
	 * 大事记标题
	 */
	private String eventtitle;

	/**
	 * 删除标记
	 */
	private Integer deleteflag;

	/**
	 * 创建者ID
	 */
	private String createid;

	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 
	 */
	private String createtimeStr;
	/**
	 * 更新者ID
	 */
	private String updateid;

	/**
	 * 更新时间
	 */
	private Date updatetime;

	/**
	 * 创建者姓名
	 */
	private String createname;

	/**
	 * 显示缩略图
	 */
	private String imgurl;

	/**
	 * 置顶标记
	 */
	private Integer type;

	/**
	 * 家族ID
	 */
	private String familyid;

	/**
	 * 大事记内容
	 */
	private String eventcontent;

	private Integer readNum;

	/**
	* 以下方法用于api
	*/
	private Integer start;
	private Integer count;
	// 阅读总数
	private Integer readcount;

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

	public Integer getReadcount() {
		return readcount;
	}

	public void setReadcount(Integer readcount) {
		this.readcount = readcount;
	}

	private static final long serialVersionUID = 1L;

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid == null ? null : eventid.trim();
	}

	public String getEventtitle() {
		return eventtitle;
	}

	public void setEventtitle(String eventtitle) {
		this.eventtitle = eventtitle == null ? null : eventtitle.trim();
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid == null ? null : familyid.trim();
	}

	public String getEventcontent() {
		return eventcontent;
	}

	public void setEventcontent(String eventcontent) {
		this.eventcontent = eventcontent == null ? null : eventcontent.trim();
	}

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", eventid=").append(eventid);
		sb.append(", eventtitle=").append(eventtitle);
		sb.append(", deleteflag=").append(deleteflag);
		sb.append(", createid=").append(createid);
		sb.append(", createtime=").append(createtime);
		sb.append(", updateid=").append(updateid);
		sb.append(", updatetime=").append(updatetime);
		sb.append(", createname=").append(createname);
		sb.append(", imgurl=").append(imgurl);
		sb.append(", type=").append(type);
		sb.append(", familyid=").append(familyid);
		sb.append(", eventcontent=").append(eventcontent);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}