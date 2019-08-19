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
 * @author panhuajie123
 * @since 2019-08-19
 */
@TableName("jp_genealogy")
public class Genealogy extends Model<Genealogy> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户登录类型
     */
    private String loginType;
    /**
     * 世系表json数据格式
     */
    private String genealogy;


    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getGenealogy() {
        return genealogy;
    }

    public void setGenealogy(String genealogy) {
        this.genealogy = genealogy;
    }

    @Override
    protected Serializable pkVal() {
        return this.loginType;
    }

    @Override
    public String toString() {
        return "Genealogy{" +
        ", loginType=" + loginType +
        ", genealogy=" + genealogy +
        "}";
    }
}
