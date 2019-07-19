package com.jp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Moment implements Serializable {
	/**
	 * 主键id
	 */
	private String id;

	/**
	 * 发布用户id
	 */
	private String userid;

	/**
	 * 展示类型ALL,PRIIVATE,SELECT
	 */
	private String showType;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 照片视频
	 */
	private String picture;

	/**
	 * 位置
	 */
	private String location;

	/**
	 * 点赞数
	 */
	private Integer likeNum;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 更新时间
	 */
	private Date updatetime;

	/**
	 * 创建者
	 */
	private String createby;

	/**
	 * 更新者
	 */
	private String updateby;

	/**
	 * 停用标记
	 */
	private Integer deleteflag;

	/**
	 * 分页参数
	 */
	private Integer start;
	private Integer count;

	/*
	 * 自属性
	 */
	private List<MomentLikeTimeline> likeTimelines;//点赞列表
	private List<MomentComment> comments;//评论列表
	private List<User> filterusers;//不让谁看，让谁看的用户列表
	private String tagid;//标签id
	private String tagtype;//标签类型
	private String username;//名字
	private String imgurl;//头像

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public List<MomentLikeTimeline> getLikeTimelines() {
		return likeTimelines;
	}

	public void setLikeTimelines(List<MomentLikeTimeline> likeTimelines) {
		this.likeTimelines = likeTimelines;
	}

	public List<MomentComment> getComments() {
		return comments;
	}

	public void setComments(List<MomentComment> comments) {
		this.comments = comments;
	}

	public String getTagid() {
		return tagid;
	}

	public void setTagid(String tagid) {
		this.tagid = tagid;
	}

	public String getTagtype() {
		return tagtype;
	}

	public void setTagtype(String tagtype) {
		this.tagtype = tagtype;
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

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType == null ? null : showType.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture == null ? null : picture.trim();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location == null ? null : location.trim();
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
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

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby == null ? null : createby.trim();
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby == null ? null : updateby.trim();
	}

	public Integer getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", userid=").append(userid);
		sb.append(", showType=").append(showType);
		sb.append(", content=").append(content);
		sb.append(", picture=").append(picture);
		sb.append(", location=").append(location);
		sb.append(", likeNum=").append(likeNum);
		sb.append(", createtime=").append(createtime);
		sb.append(", updatetime=").append(updatetime);
		sb.append(", createby=").append(createby);
		sb.append(", updateby=").append(updateby);
		sb.append(", deleteflag=").append(deleteflag);
		sb.append(", tagid=").append(tagid);
		sb.append(", tagtype=").append(tagtype);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}

	public List<User> getFilterusers() {
		return filterusers;
	}

	public void setFilterusers(List<User> filterusers) {
		this.filterusers = filterusers;
	}
}