package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Function;
import com.jp.entity.FunctionQuery;

public interface FunctionDao {
	int countByExample(FunctionQuery example);

	int deleteByExample(FunctionQuery example);

	int deleteByPrimaryKey(String functionid);

	int insert(Function record);

	int insertSelective(Function record);

	List<Function> selectByExample(FunctionQuery example);

	Function selectByPrimaryKey(String functionid);

	int updateByExampleSelective(@Param("record") Function record, @Param("example") FunctionQuery example);

	int updateByExample(@Param("record") Function record, @Param("example") FunctionQuery example);

	int updateByPrimaryKeySelective(Function record);

	int updateByPrimaryKey(Function record);

	List<Function> selectFunctionListByRoleid(@Param("familyid") String familyid, @Param("roleid") String roleid);
	
	List<Function> selectFunctionListByRoleidAndFamilyid(@Param("familyid") String familyid, @Param("roleid") String roleid);

	List<Function> selectFunctionByUserid(@Param("familyid")String familyid ,@Param("userid") String userid);
}