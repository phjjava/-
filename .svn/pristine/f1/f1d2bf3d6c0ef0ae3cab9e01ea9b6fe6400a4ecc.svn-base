package com.jp.dao;

import com.jp.entity.Userrelation;
import com.jp.entity.UserrelationQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserrelationDao {
    int countByExample(UserrelationQuery example);

    int deleteByExample(UserrelationQuery example);

    int deleteByPrimaryKey(String relationid);

    int insert(Userrelation record);

    int insertSelective(Userrelation record);

    List<Userrelation> selectByExample(UserrelationQuery example);

    Userrelation selectByPrimaryKey(String relationid);

    int updateByExampleSelective(@Param("record") Userrelation record, @Param("example") UserrelationQuery example);

    int updateByExample(@Param("record") Userrelation record, @Param("example") UserrelationQuery example);

    int updateByPrimaryKeySelective(Userrelation record);

    int updateByPrimaryKey(Userrelation record);
}