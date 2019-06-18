package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;

public interface UserManagerMapper {
	int countByExample(UserManagerExample example);

	int deleteByExample(UserManagerExample example);

	int deleteByPrimaryKey(String id);

	int insert(UserManager record);

	int insertSelective(UserManager record);

	List<UserManager> selectByExample(UserManagerExample example);

	UserManager selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") UserManager record, @Param("example") UserManagerExample example);

	int updateByExample(@Param("record") UserManager record, @Param("example") UserManagerExample example);

	int updateByPrimaryKeySelective(UserManager record);

	int updateByPrimaryKey(UserManager record);

	List<UserManager> selectMnangers(String userid);

	List<UserManager> selectByEbid(String id);
}