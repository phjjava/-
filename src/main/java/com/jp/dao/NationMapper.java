package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Nation;
import com.jp.entity.NationExample;

public interface NationMapper {
	int countByExample(NationExample example);

	int deleteByExample(NationExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(Nation record);

	int insertSelective(Nation record);

	List<Nation> selectByExample(NationExample example);

	Nation selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Nation record, @Param("example") NationExample example);

	int updateByExample(@Param("record") Nation record, @Param("example") NationExample example);

	int updateByPrimaryKeySelective(Nation record);

	int updateByPrimaryKey(Nation record);
}