package com.jp.dao;

import com.jp.entity.Index;
import com.jp.entity.IndexExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IndexMapper {
    int countByExample(IndexExample example);

    int deleteByExample(IndexExample example);

    int deleteByPrimaryKey(String id);

    int insert(Index record);

    int insertSelective(Index record);

    List<Index> selectByExample(IndexExample example);

    Index selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Index record, @Param("example") IndexExample example);

    int updateByExample(@Param("record") Index record, @Param("example") IndexExample example);

    int updateByPrimaryKeySelective(Index record);

    int updateByPrimaryKey(Index record);
}