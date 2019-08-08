package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.BannerQuery;
import com.jp.entity.JpSynopsis;
import com.jp.entity.JpSynopsisExample;

public interface SynopsisDao {
	int countByExample(JpSynopsisExample example);

    int deleteByExample(JpSynopsisExample example);

    int deleteByPrimaryKey(String id);

    int insert(JpSynopsis record);

    int insertSelective(JpSynopsis record);

    List<JpSynopsis> selectByExample(BannerQuery bq);

    JpSynopsis selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") JpSynopsis record, @Param("example") JpSynopsisExample example);

    int updateByExample(@Param("record") JpSynopsis record, @Param("example") JpSynopsisExample example);

    int updateByPrimaryKeySelective(JpSynopsis record);

    int updateByPrimaryKey(JpSynopsis record);

	int batchDelete(String[] ids);
}
