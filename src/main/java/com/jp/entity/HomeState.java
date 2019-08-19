package com.jp.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author phj123
 * @since 2019-08-19
 */
@TableName("jp_home_state")
public class HomeState extends Model<HomeState> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userid;
    /**
     * banner是否展示
     */
    private Integer bannerStatus;
    /**
     * 资讯是否展示
     */
    private Integer mationStatus;
    /**
     * 公告是否展示
     */
    private Integer noticeStatus;
    /**
     * 百家姓是否展示
     */
    private Integer xingStatus;
    /**
     * 介绍家谱是否展示
     */
    private Integer synopsisStatus;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getBannerStatus() {
        return bannerStatus;
    }

    public void setBannerStatus(Integer bannerStatus) {
        this.bannerStatus = bannerStatus;
    }

    public Integer getMationStatus() {
        return mationStatus;
    }

    public void setMationStatus(Integer mationStatus) {
        this.mationStatus = mationStatus;
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Integer getXingStatus() {
        return xingStatus;
    }

    public void setXingStatus(Integer xingStatus) {
        this.xingStatus = xingStatus;
    }

    public Integer getSynopsisStatus() {
        return synopsisStatus;
    }

    public void setSynopsisStatus(Integer synopsisStatus) {
        this.synopsisStatus = synopsisStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.userid;
    }

    @Override
    public String toString() {
        return "HomeState{" +
        ", userid=" + userid +
        ", bannerStatus=" + bannerStatus +
        ", mationStatus=" + mationStatus +
        ", noticeStatus=" + noticeStatus +
        ", xingStatus=" + xingStatus +
        ", synopsisStatus=" + synopsisStatus +
        "}";
    }
}
