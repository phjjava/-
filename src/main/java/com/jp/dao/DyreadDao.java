package com.jp.dao;

import com.jp.entity.Dyread;
import com.jp.entity.DyreadQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DyreadDao {
    int countByExample(DyreadQuery example);

    int deleteByExample(DyreadQuery example);

    int deleteByPrimaryKey(String id);

    int insert(Dyread record);

    int insertSelective(Dyread record);

    List<Dyread> selectByExample(DyreadQuery example);

    Dyread selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Dyread record, @Param("example") DyreadQuery example);

    int updateByExample(@Param("record") Dyread record, @Param("example") DyreadQuery example);

    int updateByPrimaryKeySelective(Dyread record);

    int updateByPrimaryKey(Dyread record);
}