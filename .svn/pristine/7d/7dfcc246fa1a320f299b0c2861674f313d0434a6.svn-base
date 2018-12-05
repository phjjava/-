package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class Usermates extends UsermatesKey implements Serializable {
    private String name;

    private Integer sex;

    private String nation;

    private Date birthday;

    private String birthplace;

    private String remark;
    
    private String matetype;

    public String getMatetype() {
		return matetype;
	}

	public void setMatetype(String matetype) {
		this.matetype = matetype;
	}

	private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace == null ? null : birthplace.trim();
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
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", nation=").append(nation);
        sb.append(", birthday=").append(birthday);
        sb.append(", birthplace=").append(birthplace);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}