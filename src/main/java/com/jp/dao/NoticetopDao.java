package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Noticetop;
import com.jp.entity.NoticetopQuery;

public interface NoticetopDao {
	int countByExample(NoticetopQuery example);

	int deleteByExample(NoticetopQuery example);

	int deleteByPrimaryKey(String id);

	int insert(Noticetop record);

	int insertSelective(Noticetop record);

	List<Noticetop> selectByExample(NoticetopQuery example);

	Noticetop selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Noticetop record, @Param("example") NoticetopQuery example);

	int updateByExample(@Param("record") Noticetop record, @Param("example") NoticetopQuery example);

	int updateByPrimaryKeySelective(Noticetop record);

	int updateByPrimaryKey(Noticetop record);
}