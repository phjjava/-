package com.jp.entity;

import java.util.List;

public class NoticeDetailVO {
	Notice notice;

	List<Noticefile> noticeFiles;

	List<Noticeread> noticeReads;

	Integer countReads;
	Integer countFiles;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public List<Noticefile> getNoticeFiles() {
		return noticeFiles;
	}

	public void setNoticeFiles(List<Noticefile> noticeFiles) {
		this.noticeFiles = noticeFiles;
	}

	public List<Noticeread> getNoticeReads() {
		return noticeReads;
	}

	public void setNoticeReads(List<Noticeread> noticeReads) {
		this.noticeReads = noticeReads;
	}

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

}
