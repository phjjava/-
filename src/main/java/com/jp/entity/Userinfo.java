package com.jp.entity;

import java.io.Serializable;

public class Userinfo implements Serializable {
    private String userid;

    /**
     * 出生日期
     */
    private String birthday;
    
    private String birthdayStr;

    /**
     * 农历出生日期
     */
    private String lunarbirthday;

    /**
     * 学历
     */
    private String education;

    /**
     * 生肖
     */
    private String zodica;

    /**
     * 星座
     */
    private String constellation;

    /**
     * 名族
     */
    private String nation;

    private String background;

    private String birthplace;

    private String homeplace;

    private String qq;

    private String weixin;

    private String mail;

    private String tel;

    private Integer telsee;

    private Integer qqsee;

    private Integer wxsee;

    private Integer mailsee;

    private Integer remarksee;

    private String remark;
    
  
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
	 /**
     * 用于临时保存其他民族字符串
     */
    private String otherNation;
	

    private static final long serialVersionUID = 1L;

    
    
    public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
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

	public String getOtherNation() {
		return otherNation;
	}

	public void setOtherNation(String otherNation) {
		this.otherNation = otherNation;
	}

	public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getLunarbirthday() {
        return lunarbirthday;
    }

    public void setLunarbirthday(String lunarbirthday) {
        this.lunarbirthday = lunarbirthday == null ? null : lunarbirthday.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getZodica() {
        return zodica;
    }

    public void setZodica(String zodica) {
        this.zodica = zodica == null ? null : zodica.trim();
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation == null ? null : constellation.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", birthday=").append(birthday);
        sb.append(", lunarbirthday=").append(lunarbirthday);
        sb.append(", education=").append(education);
        sb.append(", zodica=").append(zodica);
        sb.append(", constellation=").append(constellation);
        sb.append(", nation=").append(nation);
        sb.append(", background=").append(background);
        sb.append(", birthplace=").append(birthplace);
        sb.append(", homeplace=").append(homeplace);
        sb.append(", qq=").append(qq);
        sb.append(", weixin=").append(weixin);
        sb.append(", mail=").append(mail);
        sb.append(", tel=").append(tel);
        sb.append(", telsee=").append(telsee);
        sb.append(", qqsee=").append(qqsee);
        sb.append(", wxsee=").append(wxsee);
        sb.append(", mailsee=").append(mailsee);
        sb.append(", remarksee=").append(remarksee);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}