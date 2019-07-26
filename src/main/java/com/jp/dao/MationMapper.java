package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.MationExample;
import com.jp.entity.Nation;
import com.jp.entity.NationExample;
import com.jp.entity.SysMation;

public interface MationMapper {
	int countByExample(NationExample example);

	int deleteByExample(NationExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(Nation record);

	int insertSelective(Nation record);

	Nation selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Nation record, @Param("example") NationExample example);

	int updateByExample(@Param("record") Nation record, @Param("example") NationExample example);

	int updateByPrimaryKeySelective(Nation record);

	int updateByPrimaryKey(Nation record);
	
	List<SysMation> selectByExample(MationExample example);
}