package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.jp.entity.Dynamicfile;
import com.jp.entity.DynamicfileKey;
import com.jp.entity.DynamicfileQuery;

public interface DynamicfileDao {
	int countByExample(DynamicfileQuery example);

	int deleteByExample(DynamicfileQuery example);

	int deleteByPrimaryKey(DynamicfileKey key);

	int insert(Dynamicfile record);

	int insertSelective(Dynamicfile dylist);

	List<Dynamicfile> selectByExample(DynamicfileQuery example);

	Dynamicfile selectByPrimaryKey(DynamicfileKey key);

	int updateByExampleSelective(@Param("record") Dynamicfile record, @Param("example") DynamicfileQuery example);

	int updateByExample(@Param("record") Dynamicfile record, @Param("example") DynamicfileQuery example);

	int updateByPrimaryKeySelective(Dynamicfile record);

	int updateByPrimaryKey(Dynamicfile record);

	int insertdyfileSelective(List<Dynamicfile> dylist);

	int deletefile(@Param("array") List<String> dyfidList);

	@Delete("delete from jp_dynamicfile where dyid = #{dynamicid}")
	int deleteFileByDyid(@Param("dynamicid") String dynamicid);
}