package com.jp.dao;

import com.jp.entity.Dytop;
import com.jp.entity.DytopQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DytopDao {
    int countByExample(DytopQuery example);

    int deleteByExample(DytopQuery example);

    int deleteByPrimaryKey(String id);

    int insert(Dytop record);

    int insertSelective(Dytop record);

    List<Dytop> selectByExample(DytopQuery example);

    Dytop selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Dytop record, @Param("example") DytopQuery example);

    int updateByExample(@Param("record") Dytop record, @Param("example") DytopQuery example);

    int updateByPrimaryKeySelective(Dytop record);

    int updateByPrimaryKey(Dytop record);
}