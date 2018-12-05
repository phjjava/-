package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Userworkexp extends UserworkexpKey implements Serializable {
    /**
     * 所在单位
     */
    private String company;

    /**
     * 职位
     */
    private String position;

    /**
     * 工作日期从
     */
    private Date datefrom;
    private String datefromStr;
    /**
     * 工作日期到
     */
    private Date dateto;
    private String datetoStr;
    /**
     * 是否保密
     */
    private Integer issecret;

    /**
     * 主要工作经历
     */
    private String workcontent;

    private static final long serialVersionUID = 1L;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
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

    public Integer getIssecret() {
        return issecret;
    }

    public void setIssecret(Integer issecret) {
        this.issecret = issecret;
    }

    public String getWorkcontent() {
        return workcontent;
    }

    public void setWorkcontent(String workcontent) {
        this.workcontent = workcontent == null ? null : workcontent.trim();
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
        sb.append(", company=").append(company);
        sb.append(", position=").append(position);
        sb.append(", datefrom=").append(datefrom);
        sb.append(", dateto=").append(dateto);
        sb.append(", issecret=").append(issecret);
        sb.append(", workcontent=").append(workcontent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}