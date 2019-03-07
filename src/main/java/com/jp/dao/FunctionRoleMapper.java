package com.jp.dao;

import com.jp.entity.FunctionRoleExample;
import com.jp.entity.FunctionRoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FunctionRoleMapper {
    int countByExample(FunctionRoleExample example);

    int deleteByExample(FunctionRoleExample example);

    int deleteByPrimaryKey(FunctionRoleKey key);

    int insert(FunctionRoleKey record);

    int insertSelective(FunctionRoleKey record);

    List<FunctionRoleKey> selectByExample(FunctionRoleExample example);

    int updateByExampleSelective(@Param("record") FunctionRoleKey record, @Param("example") FunctionRoleExample example);

    int updateByExample(@Param("record") FunctionRoleKey record, @Param("example") FunctionRoleExample example);

	void insertBatch(@Param("userid")String userid, @Param("array")String[] functionids, @Param("ebid")String ebid, @Param("postid")String postid);
}