package com.jp.entity;

import java.util.Date;

public class UserContentVO {
    private String username;
    private String userid;
    private Integer genlevel;
    private String imgurl;
    private String content;
    private Date updatetime;
    private Integer readcount;



    public Integer getGenlevel() {
        return genlevel;
    }

    public void setGenlevel(Integer genlevel) {
        this.genlevel = genlevel;
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
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getReadcount() {
        return readcount;
    }

    public void setReadcount(Integer readcount) {
        this.readcount = readcount;
    }



}
