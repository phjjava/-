package com.jp.entity;

import java.io.Serializable;

public class Dynamicfile extends DynamicfileKey implements Serializable {
	/**
	 * 附件类型
	 */
	private Integer filetype;

	/**
	 * 文件地址
	 */
	private String fileurl;

	/**
	 * 显示排序
	 */
	private Integer sort;

	private String filename;
	private static final long serialVersionUID = 1L;

	public Integer getFiletype() {
		return filetype;
	}

	public void setFiletype(Integer filetype) {
		this.filetype = filetype;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl == null ? null : fileurl.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", filetype=").append(filetype);
		sb.append(", fileurl=").append(fileurl);
		sb.append(", sort=").append(sort);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}