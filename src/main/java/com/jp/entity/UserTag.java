package com.jp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 族圈用户标签表
 * </p>
 *
 */
public class UserTag implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 标签名称
	 */
	private String tagName;
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
	 * 标签用户id集合
	 */
	private String userIds;

	/**
	 * 标签用户信息list
	 */
	private List<UserTagUser> tagUserList;

	public List<UserTagUser> getTagUserList() {
		return tagUserList;
	}

	public void setTagUserList(List<UserTagUser> tagUserList) {
		this.tagUserList = tagUserList;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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
		this.createby = createby;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public Integer getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

	@Override
	public String toString() {
		return "UserTag{" + "id=" + id + ", userid=" + userid + ", tagName=" + tagName + ", createtime=" + createtime
				+ ", updatetime=" + updatetime + ", createby=" + createby + ", updateby=" + updateby + ", deleteflag="
				+ deleteflag + "}";
	}
}
