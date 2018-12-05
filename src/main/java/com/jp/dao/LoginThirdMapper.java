package com.jp.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.LoginThird;
import com.jp.entity.LoginThirdExample;

public interface LoginThirdMapper {
    int countByExample(LoginThirdExample example);

    int deleteByExample(LoginThirdExample example);

    int deleteByPrimaryKey(String id);

    int insert(LoginThird record);

    int insertSelective(LoginThird record);

    List<LoginThird> selectByExample(LoginThirdExample example);

    LoginThird selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LoginThird record, @Param("example") LoginThirdExample example);

    int updateByExample(@Param("record") LoginThird record, @Param("example") LoginThirdExample example);

    int updateByPrimaryKeySelective(LoginThird record);

    int updateByPrimaryKey(LoginThird record);
}