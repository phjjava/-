package com.jp.dao;

import com.jp.entity.FuncRole;
import com.jp.entity.FuncRoleQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface FuncRoleDao {
    int countByExample(FuncRoleQuery example);

    int deleteByExample(FuncRoleQuery example);

    int deleteByPrimaryKey(String roleid);

    int insert(FuncRole record);

    int insertSelective(FuncRole record);

    List<FuncRole> selectByExample(FuncRoleQuery example);

    FuncRole selectByPrimaryKey(String roleid);

    int updateByExampleSelective(@Param("record") FuncRole record, @Param("example") FuncRoleQuery example);

    int updateByExample(@Param("record") FuncRole record, @Param("example") FuncRoleQuery example);

    int updateByPrimaryKeySelective(FuncRole record);

    int updateByPrimaryKey(FuncRole record);
    
    int insertBatch(@Param("roleid")String roleid,@Param("array") String [] functionids);
}