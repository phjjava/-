package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Userinfo implements Serializable {
    /**
     * 用户ID
     */
    private String userid;

    /**
     * 生日
     */
    private Date birthday;
    /**
     * 生日String
     */
    private String birthdayStr;
    /**
     * 民族
     */
    private String nation;

    /**
     * 政治背景
     */
    private String background;

    /**
     * 出生地
     */
    private String birthplace;
   /**
    * 新增出生地 省市县 详细地址
    */
    private String birthplaceP;
    private String birthplaceC;
    private String birthplaceX;
    private String birthDetail;
    /**
     * 常住地
     */
    private String homeplace;
    /**
     * 新增常住地 省市县 详细地址
     */
    private String homeplaceP;
    private String homeplaceC;
    private String homeplaceX;
    private String homeDetail;
    /**
     * 简介
     */
    private String remark;

    /**
     * QQ
     */
    private String qq;

    /**
     * 微信
     */
    private String weixin;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 电话
     */
    private String tel;

    /**
     * 电话可见
     */
    private Integer telsee;

    /**
     * 微信可见
     */
    private Integer wxsee;

    /**
     * QQ可见
     */
    private Integer qqsee;

    /**
     * 邮箱可见
     */
    private Integer mailsee;

    /**
     * 简介可见
     */
    private Integer remarksee;
    /**
     * 用于临时保存其他民族字符串
     */
    private String otherNation;

    public String getOtherNation() {
		return otherNation;
	}

	public void setOtherNation(String otherNation) {
		this.otherNation = otherNation;
	}

	private static final long serialVersionUID = 1L;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background == null ? null : background.trim();
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace == null ? null : birthplace.trim();
    }

    public String getHomeplace() {
        return homeplace;
    }

    public void setHomeplace(String homeplace) {
        this.homeplace = homeplace == null ? null : homeplace.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Integer getTelsee() {
        return telsee;
    }

    public void setTelsee(Integer telsee) {
        this.telsee = telsee;
    }

    public Integer getWxsee() {
        return wxsee;
    }

    public void setWxsee(Integer wxsee) {
        this.wxsee = wxsee;
    }

    public Integer getQqsee() {
        return qqsee;
    }

    public void setQqsee(Integer qqsee) {
        this.qqsee = qqsee;
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
	
	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
    

	public String getBirthDetail() {
		return birthDetail;
	}

	public void setBirthDetail(String birthDetail) {
		this.birthDetail = birthDetail;
	}

	public String getHomeDetail() {
		return homeDetail;
	}

	public void setHomeDetail(String homeDetail) {
		this.homeDetail = homeDetail;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", birthday=").append(birthday);
        sb.append(", nation=").append(nation);
        sb.append(", background=").append(background);
        sb.append(", birthplace=").append(birthplace);
        sb.append(", homeplace=").append(homeplace);
        sb.append(", remark=").append(remark);
        sb.append(", qq=").append(qq);
        sb.append(", weixin=").append(weixin);
        sb.append(", mail=").append(mail);
        sb.append(", tel=").append(tel);
        sb.append(", telsee=").append(telsee);
        sb.append(", wxsee=").append(wxsee);
        sb.append(", qqsee=").append(qqsee);
        sb.append(", mailsee=").append(mailsee);
        sb.append(", remarksee=").append(remarksee);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}