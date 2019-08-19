package com.jp.entity;

public class JpXingContent {
    private String xingid;

    private String diccode;

    private String content;

    public String getXingid() {
        return xingid;
    }

    public void setXingid(String xingid) {
        this.xingid = xingid == null ? null : xingid.trim();
    }

    public String getDiccode() {
        return diccode;
    }

    public void setDiccode(String diccode) {
        this.diccode = diccode == null ? null : diccode.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}