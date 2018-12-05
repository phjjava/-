package com.jp.dao;

import com.jp.entity.Userworkexp;
import com.jp.entity.UserworkexpKey;
import com.jp.entity.UserworkexpQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserworkexpDao {
    int countByExample(UserworkexpQuery example);

    int deleteByExample(UserworkexpQuery example);

    int deleteByPrimaryKey(UserworkexpKey key);

    int insert(Userworkexp record);

    int insertSelective(Userworkexp record);

    List<Userworkexp> selectByExampleWithBLOBs(UserworkexpQuery example);

    List<Userworkexp> selectByExample(UserworkexpQuery example);

    Userworkexp selectByPrimaryKey(UserworkexpKey key);

    int updateByExampleSelective(@Param("record") Userworkexp record, @Param("example") UserworkexpQuery example);

    int updateByExampleWithBLOBs(@Param("record") Userworkexp record, @Param("example") UserworkexpQuery example);

    int updateByExample(@Param("record") Userworkexp record, @Param("example") UserworkexpQuery example);

    int updateByPrimaryKeySelective(Userworkexp record);

    int updateByPrimaryKeyWithBLOBs(Userworkexp record);

    int updateByPrimaryKey(Userworkexp record);
    int insertEduExp(@Param("list")List<Userworkexp> userworkexpList);
    List<Userworkexp>selectByUserId(String UserId);
}