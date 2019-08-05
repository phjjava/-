package com.jp.entity;

import java.io.Serializable;

public class Useredu extends UsereduKey implements Serializable {
	/**
	 * 毕业院校
	 */
	private String university;

	/**
	 * 所学专业
	 */
	private String major;

	/**
	 * 学位
	 */
	private String degree;

	/**
	 * 是否保密
	 */
	private Integer issecret;
	/**
	 * 开始日期
	 */
	private String datefrom;
	private String datefromStr;
	/**
	 * 结束日期
	 */
	private String dateto;
	private String datetoStr;
	/**
	 * 教育内容
	 */
	private String educontent;
	private static final long serialVersionUID = 1L;

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university == null ? null : university.trim();
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major == null ? null : major.trim();
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree == null ? null : degree.trim();
	}

	public Integer getIssecret() {
		return issecret;
	}

	public void setIssecret(Integer issecret) {
		this.issecret = issecret;
	}

	public String getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}

	public String getDateto() {
		return dateto;
	}

	public void setDateto(String dateto) {
		this.dateto = dateto;
	}

	public String getEducontent() {
		return educontent;
	}

	public void setEducontent(String educontent) {
		this.educontent = educontent;
	}

	public String getDatefromStr() {
		return datefromStr;
	}

	public void setDatefromStr(String datefromStr) {
		this.datefromStr = datefromStr;
	}

	public String getDatetoStr() {
		return datetoStr;
	}

	public void setDatetoStr(String datetoStr) {
		this.datetoStr = datetoStr;
	}

	@Override
	public String toString() {
		return "Useredu [university=" + university + ", major=" + major + ", degree=" + degree + ", issecret="
				+ issecret + ", datefrom=" + datefrom + ", datefromStr=" + datefromStr + ", dateto=" + dateto
				+ ", datetoStr=" + datetoStr + ", educontent=" + educontent + "]";
	}
}