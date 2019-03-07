package com.jp.entity;

import java.io.Serializable;

public class FunctionRoleKey implements Serializable {
    /**
     * 用户id
     */
    private String userid;

    /**
     * 菜单id
     */
    private String functionid;

    /**
     * 编委会id
     */
    private String ebid;

    /**
     * 职务id
     */
    private String postid;

    private static final long serialVersionUID = 1L;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getFunctionid() {
        return functionid;
    }

    public void setFunctionid(String functionid) {
        this.functionid = functionid == null ? null : functionid.trim();
    }

    public String getEbid() {
        return ebid;
    }

    public void setEbid(String ebid) {
        this.ebid = ebid == null ? null : ebid.trim();
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid == null ? null : postid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", functionid=").append(functionid);
        sb.append(", ebid=").append(ebid);
        sb.append(", postid=").append(postid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}