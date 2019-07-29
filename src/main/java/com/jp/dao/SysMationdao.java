package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.jp.entity.MationExample;
import com.jp.entity.Nation;
import com.jp.entity.NationExample;
import com.jp.entity.SysMation;

public interface SysMationdao {
	int countByExample(MationExample example);

	int deleteByExample(MationExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(MationExample example);

	int insertSelective(MationExample example);

	SysMation selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Nation record, @Param("example") MationExample example);

	int updateByExample(@Param("record") Nation record, @Param("example") MationExample example);

	int updateByPrimaryKeySelective(MationExample mationid);

	int updateByPrimaryKey(MationExample mationid);
	
	List<SysMation> selectByExample();
}