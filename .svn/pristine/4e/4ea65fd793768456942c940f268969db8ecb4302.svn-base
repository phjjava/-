package com.jp.dao;

import com.jp.entity.FunctionRole;
import com.jp.entity.FunctionRoleExample;
import com.jp.entity.FunctionRoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FunctionRoleMapper {
    int countByExample(FunctionRoleExample example);

    int deleteByExample(FunctionRoleExample example);

    int deleteByPrimaryKey(FunctionRoleKey key);

    int insert(FunctionRole record);

    int insertSelective(FunctionRole record);

    List<FunctionRole> selectByExample(FunctionRoleExample example);

    FunctionRole selectByPrimaryKey(FunctionRoleKey key);

    int updateByExampleSelective(@Param("record") FunctionRole record, @Param("example") FunctionRoleExample example);

    int updateByExample(@Param("record") FunctionRole record, @Param("example") FunctionRoleExample example);

    int updateByPrimaryKeySelective(FunctionRole record);

    int updateByPrimaryKey(FunctionRole record);

	void insertBatch(@Param("userid")String currentUserId, @Param("array")String[] functionids, @Param("ebid")String ebid);
}