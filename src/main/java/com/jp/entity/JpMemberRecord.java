package com.jp.entity;

import java.util.Date;

public class JpMemberRecord {
    private String id;

    private String recordid;

    private String recordname;

    private String recordmoney;

    private Date recordtime;

    private Integer recordstatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid == null ? null : recordid.trim();
    }

    public String getRecordname() {
        return recordname;
    }

    public void setRecordname(String recordname) {
        this.recordname = recordname == null ? null : recordname.trim();
    }

    public String getRecordmoney() {
        return recordmoney;
    }

    public void setRecordmoney(String recordmoney) {
        this.recordmoney = recordmoney == null ? null : recordmoney.trim();
    }

    public Date getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }

    public Integer getRecordstatus() {
        return recordstatus;
    }

    public void setRecordstatus(Integer recordstatus) {
        this.recordstatus = recordstatus;
    }
}