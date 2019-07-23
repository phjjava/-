package com.jp.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author SongMingWei
 * @since 2019-07-19
 */
public class WorshipAncestor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 祭品id
     */
    private String oblationid;
    /**
     * 祭品
     */
    private String oblation;
    private String oblationtypeid;
    private String oblationtype;
    /**
     * 祭拜人
     */
    private String createid;
    private String createname;
    /**
     * 被宗派世系家族id
     */
    private String familyid;
    /**
     * 被宗派世系id
     */
    private String worshipid;
    /**
     * 被宗派世系名称
     */
    private String worshipname;
    /**
     * 祭拜时间
     */
    private Date createtime;
    /**
     * 删除标记
     */
    private Integer deleteflag;

    //自有属性
    private String imgurl;//头像
    private String oblationurl;//祭品图片
    
    public String getOblationurl() {
		return oblationurl;
	}

	public void setOblationurl(String oblationurl) {
		this.oblationurl = oblationurl;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOblationid() {
        return oblationid;
    }

    public void setOblationid(String oblationid) {
        this.oblationid = oblationid;
    }

    public String getOblation() {
        return oblation;
    }

    public void setOblation(String oblation) {
        this.oblation = oblation;
    }

    public String getOblationtypeid() {
        return oblationtypeid;
    }

    public void setOblationtypeid(String oblationtypeid) {
        this.oblationtypeid = oblationtypeid;
    }

    public String getOblationtype() {
        return oblationtype;
    }

    public void setOblationtype(String oblationtype) {
        this.oblationtype = oblationtype;
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid;
    }

    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid;
    }

    public String getWorshipid() {
        return worshipid;
    }

    public void setWorshipid(String worshipid) {
        this.worshipid = worshipid;
    }

    public String getWorshipname() {
        return worshipname;
    }

    public void setWorshipname(String worshipname) {
        this.worshipname = worshipname;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(Integer deleteflag) {
        this.deleteflag = deleteflag;
    }

    @Override
    public String toString() {
        return "WorshipAncestor{" +
        "id=" + id +
        ", oblationid=" + oblationid +
        ", oblation=" + oblation +
        ", oblationtypeid=" + oblationtypeid +
        ", oblationtype=" + oblationtype +
        ", createid=" + createid +
        ", createname=" + createname +
        ", familyid=" + familyid +
        ", worshipid=" + worshipid +
        ", worshipname=" + worshipname +
        ", createtime=" + createtime +
        ", deleteflag=" + deleteflag +
        ", imgurl=" + imgurl +
        "}";
    }
}
