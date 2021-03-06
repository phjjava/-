package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Userposition;
import com.jp.entity.UserpositionQuery;

public interface UserpositionDao {
	int countByExample(UserpositionQuery example);

	int deleteByExample(UserpositionQuery example);

	int deleteByPrimaryKey(String id);

	int insert(Userposition record);

	int insertSelective(Userposition record);

	List<Userposition> selectByExample(UserpositionQuery example);

	Userposition selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Userposition record, @Param("example") UserpositionQuery example);

	int updateByExample(@Param("record") Userposition record, @Param("example") UserpositionQuery example);

	int updateByPrimaryKeySelective(Userposition record);

	int updateByPrimaryKey(Userposition record);

	// @Select("SELECT * FROM jp_userposition WHERE sex = #{sex}")
	List<Userposition> selectBySex(@Param("sex") Integer sex);
}