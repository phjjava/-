package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.MomentFilter;
import com.jp.entity.MomentFilterExample;

public interface MomentFilterMapper {
	int countByExample(MomentFilterExample example);

	int deleteByExample(MomentFilterExample example);

	int insert(MomentFilter record);

	int insertSelective(MomentFilter record);

	List<MomentFilter> selectByExample(MomentFilterExample example);

	int updateByExampleSelective(@Param("record") MomentFilter record, @Param("example") MomentFilterExample example);

	int updateByExample(@Param("record") MomentFilter record, @Param("example") MomentFilterExample example);

	Integer insertBatch(@Param("list") List<MomentFilter> filters);
}