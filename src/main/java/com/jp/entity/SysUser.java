package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {
    /**
     * 用户唯一编号
     */
    private String userid;

    /**
     * 账号
     */
    private String loginname;

    /**
     * 密码
     */
    private String password;

    /**
     * 显示名字
     */
    private String name;

    /**
     * 最后登录时间
     */
    private Date logintime;

    private static final long serialVersionUID = 1L;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", loginname=").append(loginname);
        sb.append(", password=").append(password);
        sb.append(", name=").append(name);
        sb.append(", logintime=").append(logintime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}