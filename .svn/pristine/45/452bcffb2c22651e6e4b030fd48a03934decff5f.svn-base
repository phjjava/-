package com.jp.dao;

import com.jp.entity.Fungroup;
import com.jp.entity.FungroupKey;
import com.jp.entity.FungroupQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FungroupDao {
    int countByExample(FungroupQuery example);

    int deleteByExample(FungroupQuery example);

    int deleteByPrimaryKey(FungroupKey key);

    int insert(Fungroup record);

    int insertSelective(Fungroup record);

    List<Fungroup> selectByExample(FungroupQuery example);

    Fungroup selectByPrimaryKey(FungroupKey key);

    int updateByExampleSelective(@Param("record") Fungroup record, @Param("example") FungroupQuery example);

    int updateByExample(@Param("record") Fungroup record, @Param("example") FungroupQuery example);

    int updateByPrimaryKeySelective(Fungroup record);

    int updateByPrimaryKey(Fungroup record);
}