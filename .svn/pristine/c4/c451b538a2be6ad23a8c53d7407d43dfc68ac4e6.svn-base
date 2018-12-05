package com.jp.dao;

import com.jp.entity.Usercode;
import com.jp.entity.UsercodeQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsercodeDao {
    int countByExample(UsercodeQuery example);

    int deleteByExample(UsercodeQuery example);

    int deleteByPrimaryKey(String id);

    int insert(Usercode record);

    int insertSelective(Usercode record);

    List<Usercode> selectByExample(UsercodeQuery example);

    Usercode selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Usercode record, @Param("example") UsercodeQuery example);

    int updateByExample(@Param("record") Usercode record, @Param("example") UsercodeQuery example);

    int updateByPrimaryKeySelective(Usercode record);

    int updateByPrimaryKey(Usercode record);
}