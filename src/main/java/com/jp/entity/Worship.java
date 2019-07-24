package com.jp.entity;

import java.util.Date;

public class Worship{
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
     * 被祭拜人
     */
    private String worshipid;

    /**
     * 被祭拜人
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

    //自属性
    private WorshipAnnex annex;
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
        this.id = id == null ? null : id.trim();
    }

    public String getOblationid() {
        return oblationid;
    }

    public void setOblationid(String oblationid) {
        this.oblationid = oblationid == null ? null : oblationid.trim();
    }

    public String getOblation() {
        return oblation;
    }

    public void setOblation(String oblation) {
        this.oblation = oblation == null ? null : oblation.trim();
    }

    public String getOblationtypeid() {
        return oblationtypeid;
    }

    public void setOblationtypeid(String oblationtypeid) {
        this.oblationtypeid = oblationtypeid == null ? null : oblationtypeid.trim();
    }

    public String getOblationtype() {
        return oblationtype;
    }

    public void setOblationtype(String oblationtype) {
        this.oblationtype = oblationtype == null ? null : oblationtype.trim();
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

    public String getWorshipid() {
        return worshipid;
    }

    public void setWorshipid(String worshipid) {
        this.worshipid = worshipid == null ? null : worshipid.trim();
    }

    public String getWorshipname() {
        return worshipname;
    }

    public void setWorshipname(String worshipname) {
        this.worshipname = worshipname == null ? null : worshipname.trim();
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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", oblationid=").append(oblationid);
        sb.append(", oblation=").append(oblation);
        sb.append(", oblationtypeid=").append(oblationtypeid);
        sb.append(", oblationtype=").append(oblationtype);
        sb.append(", createid=").append(createid);
        sb.append(", createname=").append(createname);
        sb.append(", worshipid=").append(worshipid);
        sb.append(", worshipname=").append(worshipname);
        sb.append(", createtime=").append(createtime);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append("]");
        return sb.toString();
    }

	public WorshipAnnex getAnnex() {
		return annex;
	}

	public void setAnnex(WorshipAnnex annex) {
		this.annex = annex;
	}
}