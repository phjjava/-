package com.jp.entity;

public class UserClildInfo extends User {

	private String pid;

	private String birthday;

	private String nation;

	private String background;

	private String birthplace;

	private String homeplace;

	private String QQ;

	private String weixin;

	private String mail;

	private String tel;

	private Integer telsee;

	private Integer qqsee;

	private Integer wxsee;

	private Integer mailsee;

	private Integer remarksee;

	private String remark;

	private String birthdayStr; // 针对安卓做的，ios不需要

	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getHomeplace() {
		return homeplace;
	}

	public void setHomeplace(String homeplace) {
		this.homeplace = homeplace;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getTelsee() {
		return telsee;
	}

	public void setTelsee(Integer telsee) {
		this.telsee = telsee;
	}

	public Integer getQqsee() {
		return qqsee;
	}

	public void setQqsee(Integer qqsee) {
		this.qqsee = qqsee;
	}

	public Integer getWxsee() {
		return wxsee;
	}

	public void setWxsee(Integer wxsee) {
		this.wxsee = wxsee;
	}

	public Integer getMailsee() {
		return mailsee;
	}

	public void setMailsee(Integer mailsee) {
		this.mailsee = mailsee;
	}

	public Integer getRemarksee() {
		return remarksee;
	}

	public void setRemarksee(Integer remarksee) {
		this.remarksee = remarksee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
