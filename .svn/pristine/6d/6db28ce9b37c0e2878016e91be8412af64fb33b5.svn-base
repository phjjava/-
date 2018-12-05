package com.jp.dao;

import com.jp.entity.Banner;
import com.jp.entity.BannerQuery;
import com.jp.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BannerDao {
    int countByExample(BannerQuery example);

    int deleteByExample(BannerQuery example);

    int deleteByPrimaryKey(String bannerid);

    int insert(Banner record);

    int insertSelective(Banner record);

    List<Banner> selectByExample(BannerQuery example);

    Banner selectByPrimaryKey(String bannerid);

    int updateByExampleSelective(@Param("record") Banner record, @Param("example") BannerQuery example);

    int updateByExample(@Param("record") Banner record, @Param("example") BannerQuery example);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);
    
    int batchDelete(@Param("array") String bannerid[]);
}