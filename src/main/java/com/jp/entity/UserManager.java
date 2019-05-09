package com.jp.entity;

import java.io.Serializable;

public class UserManager implements Serializable {
    private String id;

    /**
     * �û�id
     */
    private String userid;

    /**
     * ����
     */
    private String username;

    /**
     * ����id
     */
    private String familyid;

    /**
     * ְ��id
     */
    private String postid;

    /**
     * ְ������
     */
    private String postname;

    /**
     * ��ί��id
     */
    private String ebid;

    /**
     * ��ί������
     */
    private String ebname;

    /**
     * ��ί������1��0��
     */
    private Integer ebtype;

    /**
     * �Ƿ�Ϊ����1��0��
     */
    private Integer ismanager;
    
    /**
     * 自写属性
     */
    private String address;	//地址
    
    private String genlevel;	// 世系

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGenlevel() {
		return genlevel;
	}

	public void setGenlevel(String genlevel) {
		this.genlevel = genlevel;
	}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid == null ? null : familyid.trim();
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid == null ? null : postid.trim();
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname == null ? null : postname.trim();
    }

    public String getEbid() {
        return ebid;
    }

    public void setEbid(String ebid) {
        this.ebid = ebid == null ? null : ebid.trim();
    }

    public String getEbname() {
        return ebname;
    }

    public void setEbname(String ebname) {
        this.ebname = ebname == null ? null : ebname.trim();
    }

    public Integer getEbtype() {
        return ebtype;
    }

    public void setEbtype(Integer ebtype) {
        this.ebtype = ebtype;
    }

    public Integer getIsmanager() {
        return ismanager;
    }

    public void setIsmanager(Integer ismanager) {
        this.ismanager = ismanager;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", username=").append(username);
        sb.append(", familyid=").append(familyid);
        sb.append(", postid=").append(postid);
        sb.append(", postname=").append(postname);
        sb.append(", ebid=").append(ebid);
        sb.append(", ebname=").append(ebname);
        sb.append(", ebtype=").append(ebtype);
        sb.append(", ismanager=").append(ismanager);
        sb.append(", address=").append(address);
        sb.append(", genlevel=").append(genlevel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}