package com.jp.entity;

public class UserClildInfo extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	/**
	* 新增出生地 省市县 详细地址
	*/
	private String birthplaceP;
	private String birthplaceC;
	private String birthplaceX;
	private String birthDetail;
	/**
	 * 新增常住地 省市县 详细地址
	 */
	private String homeplaceP;
	private String homeplaceC;
	private String homeplaceX;
	private String homeDetail;

	@Override
	public String toString() {
		return "UserClildInfo [pid=" + pid + ", birthday=" + birthday + ", nation=" + nation + ", background="
				+ background + ", birthplace=" + birthplace + ", homeplace=" + homeplace + ", QQ=" + QQ + ", weixin="
				+ weixin + ", mail=" + mail + ", tel=" + tel + ", telsee=" + telsee + ", qqsee=" + qqsee + ", wxsee="
				+ wxsee + ", mailsee=" + mailsee + ", remarksee=" + remarksee + ", remark=" + remark + ", birthdayStr="
				+ birthdayStr + ", birthplaceP=" + birthplaceP + ", birthplaceC=" + birthplaceC + ", birthplaceX="
				+ birthplaceX + ", birthDetail=" + birthDetail + ", homeplaceP=" + homeplaceP + ", homeplaceC="
				+ homeplaceC + ", homeplaceX=" + homeplaceX + ", homeDetail=" + homeDetail + "]";
	}

	public String getBirthplaceP() {
		return birthplaceP;
	}

	public void setBirthplaceP(String birthplaceP) {
		this.birthplaceP = birthplaceP;
	}

	public String getBirthplaceC() {
		return birthplaceC;
	}

	public void setBirthplaceC(String birthplaceC) {
		this.birthplaceC = birthplaceC;
	}

	public String getBirthplaceX() {
		return birthplaceX;
	}

	public void setBirthplaceX(String birthplaceX) {
		this.birthplaceX = birthplaceX;
	}

	public String getBirthDetail() {
		return birthDetail;
	}

	public void setBirthDetail(String birthDetail) {
		this.birthDetail = birthDetail;
	}

	public String getHomeplaceP() {
		return homeplaceP;
	}

	public void setHomeplaceP(String homeplaceP) {
		this.homeplaceP = homeplaceP;
	}

	public String getHomeplaceC() {
		return homeplaceC;
	}

	public void setHomeplaceC(String homeplaceC) {
		this.homeplaceC = homeplaceC;
	}

	public String getHomeplaceX() {
		return homeplaceX;
	}

	public void setHomeplaceX(String homeplaceX) {
		this.homeplaceX = homeplaceX;
	}

	public String getHomeDetail() {
		return homeDetail;
	}

	public void setHomeDetail(String homeDetail) {
		this.homeDetail = homeDetail;
	}

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
