package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 族圈用户标签与用户关系表
 * </p>
 *
 * @author zhangningning
 * @since 2019-04-03
 */
public class UserTagUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 标签id
	 */
	private String tagId;
	/**
	 * 用户id
	 */
	private String userid;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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
		return "UserTagUser{" + "id=" + id + ", tagId=" + tagId + ", userid=" + userid + ", createtime=" + createtime
				+ ", updatetime=" + updatetime + ", createby=" + createby + ", updateby=" + updateby + ", deleteflag="
				+ deleteflag + "}";
	}
}
