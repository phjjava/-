package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

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
    private Date datefrom;
    private String datefromStr;
    /**
     * 结束日期
     */
    private Date dateto;
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
    
    public Date getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(Date datefrom) {
		this.datefrom = datefrom;
	}

	public Date getDateto() {
		return dateto;
	}

	public void setDateto(Date dateto) {
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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", university=").append(university);
        sb.append(", major=").append(major);
        sb.append(", degree=").append(degree);
        sb.append(", issecret=").append(issecret);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}