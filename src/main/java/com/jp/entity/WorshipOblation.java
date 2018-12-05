package com.jp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WorshipOblation implements Serializable {
    private String id;

    /**
     * 祭品名称
     */
    private String oblation;

    /**
     * 时长(分钟)
     */
    private Integer duration;

    /**
     * 祭品类型
     */
    private String oblationtypeid;

    private String oblationtype;

    /**
     * 寓意
     */
    private String moral;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 祭品图片
     */
    private String imgurl;

    private String createid;

    private String createname;

    private Date createtime;

    private Integer sort;

    /**
     * 删除标记
     */
    private Integer deleteflag;
    
    /*自写属性*/
    //家族版本id
    private String versionid;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOblation() {
        return oblation;
    }

    public void setOblation(String oblation) {
        this.oblation = oblation == null ? null : oblation.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public String getMoral() {
        return moral;
    }

    public void setMoral(String moral) {
        this.moral = moral == null ? null : moral.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        sb.append(", oblation=").append(oblation);
        sb.append(", duration=").append(duration);
        sb.append(", oblationtypeid=").append(oblationtypeid);
        sb.append(", oblationtype=").append(oblationtype);
        sb.append(", moral=").append(moral);
        sb.append(", price=").append(price);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", createid=").append(createid);
        sb.append(", createname=").append(createname);
        sb.append(", createtime=").append(createtime);
        sb.append(", sort=").append(sort);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public String getVersionid() {
		return versionid;
	}

	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
}