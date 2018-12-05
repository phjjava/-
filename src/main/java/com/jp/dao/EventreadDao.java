package com.jp.dao;

import com.jp.entity.Eventread;
import com.jp.entity.EventreadQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EventreadDao {
    int countByExample(EventreadQuery example);

    int deleteByExample(EventreadQuery example);

    int deleteByPrimaryKey(String id);

    int insert(Eventread record);

    int insertSelective(Eventread record);

    List<Eventread> selectByExample(EventreadQuery example);

    Eventread selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Eventread record, @Param("example") EventreadQuery example);

    int updateByExample(@Param("record") Eventread record, @Param("example") EventreadQuery example);

    int updateByPrimaryKeySelective(Eventread record);

    int updateByPrimaryKey(Eventread record);
}