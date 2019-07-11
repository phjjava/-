package com.jp.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 家谱祭祖世系字典表
 * </p>
 *
 * @author SongMingWei
 * @since 2019-07-04
 */
public class WorshipAncestorDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 派系
     */
    private String faction;
    /**
     * 世系
     */
    private String lineage;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序（升序）
     */
    private Integer dictSort;
    /**
     * 创建人姓名
     */
    private String createname;
    /**
     * 创建id
     */
    private String createid;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 是否停用
     */
    private Integer deleteflag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getLineage() {
        return lineage;
    }

    public void setLineage(String lineage) {
        this.lineage = lineage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDictSort() {
        return dictSort;
    }

    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid;
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
        return "WorshipAncestorDict{" +
        "id=" + id +
        ", faction=" + faction +
        ", lineage=" + lineage +
        ", description=" + description +
        ", dictSort=" + dictSort +
        ", createname=" + createname +
        ", createid=" + createid +
        ", createtime=" + createtime +
        ", deleteflag=" + deleteflag +
        "}";
    }
}
