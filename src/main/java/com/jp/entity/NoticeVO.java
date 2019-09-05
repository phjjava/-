package com.jp.entity;

/**
 * 
 * @功能 公告扩展类便于维护添加新字段
 * @作者 jinlizhi
 * @时间 2017年5月25日上午10:34:56
 */
public class NoticeVO extends Notice {
	/**
	 * 记录分支ids
	 */
	private String tobranchid;
	/**
	 * 公告浏览量
	 */
	private Integer readNum;

	private Notice notice;

	private Integer countFiles;

	private Integer countReads;

	public Integer getCountReads() {
		return countReads;
	}

	public void setCountReads(Integer countReads) {
		this.countReads = countReads;
	}

	public Integer getCountFiles() {
		return countFiles;
	}

	public void setCountFiles(Integer countFiles) {
		this.countFiles = countFiles;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public String getTobranchid() {
		return tobranchid;
	}

	public void setTobranchid(String tobranchid) {
		this.tobranchid = tobranchid;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

}
