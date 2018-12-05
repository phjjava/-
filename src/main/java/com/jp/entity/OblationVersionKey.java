package com.jp.entity;

import java.io.Serializable;

public class OblationVersionKey implements Serializable {
    /**
     * 祭品id
     */
    private String oblationid;

    /**
     * 版本id
     */
    private String versionid;

    private static final long serialVersionUID = 1L;

    public String getOblationid() {
        return oblationid;
    }

    public void setOblationid(String oblationid) {
        this.oblationid = oblationid == null ? null : oblationid.trim();
    }

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid == null ? null : versionid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oblationid=").append(oblationid);
        sb.append(", versionid=").append(versionid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}