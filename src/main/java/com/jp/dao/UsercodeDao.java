package com.jp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Usercode;
import com.jp.entity.UsercodeQuery;

public interface UsercodeDao {
	int countByExample(UsercodeQuery example);

	int deleteByExample(UsercodeQuery example);

	int deleteByPrimaryKey(String id);

	int insert(Usercode record);

	int insertSelective(Usercode record);

	List<Usercode> selectByExample(UsercodeQuery example);

	Usercode selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Usercode record, @Param("example") UsercodeQuery example);

	int updateByExample(@Param("record") Usercode record, @Param("example") UsercodeQuery example);

	int updateByPrimaryKeySelective(Usercode record);

	int updateByPrimaryKey(Usercode record);

	Usercode selectByPhone(Map<String, String> params);
}