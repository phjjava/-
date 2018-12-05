package com.jp.dao;

import com.jp.entity.SysFunction;
import com.jp.entity.SysFunctionQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysFunctionDao {
    int countByExample(SysFunctionQuery example);

    int deleteByExample(SysFunctionQuery example);

    int deleteByPrimaryKey(String functionid);

    int insert(SysFunction record);

    int insertSelective(SysFunction record);

    List<SysFunction> selectByExample(SysFunctionQuery example);

    SysFunction selectByPrimaryKey(String functionid);

    int updateByExampleSelective(@Param("record") SysFunction record, @Param("example") SysFunctionQuery example);

    int updateByExample(@Param("record") SysFunction record, @Param("example") SysFunctionQuery example);

    int updateByPrimaryKeySelective(SysFunction record);

    int updateByPrimaryKey(SysFunction record);
    
    List<SysFunction> selectFunctionListByVersionid(@Param("versionid")String versionid);
}