package com.jp.entity;
/**
 * 五世一图查询某世所有人实体类
 * @author SongMingWei
 *
 */
public class GenUserOtherVO {
    String username;
    String userid;
    Integer sex;
    String imgurl;
    Integer genlevel;
    Integer livestatus;
    String birthday;//出生时间
    String dietime;//死亡时间
    String mateusername;
    String mateuserid;
    Integer matesex;
    String mateimgurl;
    Integer mategenlevel;
    Integer matelivestatus;
    String matebirthday;//出生时间
    String matedietime;//死亡时间
    public Integer getLivestatus() {
		return livestatus;
	}

	public void setLivestatus(Integer livestatus) {
		this.livestatus = livestatus;
	}

    public String getMateusername() {
		return mateusername;
	}

	public void setMateusername(String mateusername) {
		this.mateusername = mateusername;
	}

	public String getMateuserid() {
		return mateuserid;
	}

	public void setMateuserid(String mateuserid) {
		this.mateuserid = mateuserid;
	}

	public Integer getMatesex() {
		return matesex;
	}

	public void setMatesex(Integer matesex) {
		this.matesex = matesex;
	}

	public String getMateimgurl() {
		return mateimgurl;
	}

	public void setMateimgurl(String mateimgurl) {
		this.mateimgurl = mateimgurl;
	}

	public Integer getMategenlevel() {
		return mategenlevel;
	}

	public void setMategenlevel(Integer mategenlevel) {
		this.mategenlevel = mategenlevel;
	}

	public Integer getMatelivestatus() {
		return matelivestatus;
	}

	public void setMatelivestatus(Integer matelivestatus) {
		this.matelivestatus = matelivestatus;
	}

	public String getMatebirthday() {
		return matebirthday;
	}

	public void setMatebirthday(String matebirthday) {
		this.matebirthday = matebirthday;
	}

	public String getMatedietime() {
		return matedietime;
	}

	public void setMatedietime(String matedietime) {
		this.matedietime = matedietime;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getImgurl() {
        return imgurl == null ? "" : imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getSex() {
        return sex == null ? 3 : sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getGenlevel() {
        return genlevel;
    }

    public void setGenlevel(Integer genlevel) {
        this.genlevel = genlevel;
    }

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDietime() {
		return dietime;
	}

	public void setDietime(String dietime) {
		this.dietime = dietime;
	}
    
}
