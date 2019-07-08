package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Index implements Serializable {
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 节点图标
	 */
	private String imgurl;

	/**
	 * 识别码，标识菜单
	 */
	private String code;

	/**
	 * 参数1
	 */
	private String param1;

	/**
	 * 参数2
	 */
	private String param2;

	/**
	 * 创建人id
	 */
	private String createid;

	/**
	 * 创建人
	 */
	private String createname;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 更新者id
	 */
	private String updateid;

	/**
	 * 更新人
	 */
	private String updatename;

	/**
	 * 更新时间
	 */
	private Date updatetime;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 删除标记
	 */
	private Integer deleteflag;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl == null ? null : imgurl.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1 == null ? null : param1.trim();
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2 == null ? null : param2.trim();
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid == null ? null : createid.trim();
	}

	public String getCreatename() {
		return createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname == null ? null : createname.trim();
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

	public String getUpdatename() {
		return updatename;
	}

	public void setUpdatename(String updatename) {
		this.updatename = updatename == null ? null : updatename.trim();
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
		sb.append(", name=").append(name);
		sb.append(", imgurl=").append(imgurl);
		sb.append(", code=").append(code);
		sb.append(", param1=").append(param1);
		sb.append(", param2=").append(param2);
		sb.append(", createid=").append(createid);
		sb.append(", createname=").append(createname);
		sb.append(", createtime=").append(createtime);
		sb.append(", updateid=").append(updateid);
		sb.append(", updatename=").append(updatename);
		sb.append(", updatetime=").append(updatetime);
		sb.append(", sort=").append(sort);
		sb.append(", deleteflag=").append(deleteflag);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}