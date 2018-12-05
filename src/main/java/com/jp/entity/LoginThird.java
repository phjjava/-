package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class LoginThird implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * �󶨵��û�id
     */
    private String userid;

    /**
     * ��������¼�ṩ��id����΢�ŵ�openid
     */
    private String openid;

    /**
     * ���������ǳ�
     */
    private String ninckname;

    /**
     * �󶨵��ֻ���
     */
    private String phone;

    /**
     * ��������¼���ͣ�΢��/QQ/΢����
     */
    private String type;

    /**
     * ��ʱ��
     */
    private Date createtime;

    /**
     * �߼�ɾ�����
     */
    private Integer deleteflag;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getNinckname() {
        return ninckname;
    }

    public void setNinckname(String ninckname) {
        this.ninckname = ninckname == null ? null : ninckname.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
        sb.append(", userid=").append(userid);
        sb.append(", openid=").append(openid);
        sb.append(", ninckname=").append(ninckname);
        sb.append(", phone=").append(phone);
        sb.append(", type=").append(type);
        sb.append(", createtime=").append(createtime);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}