package com.jp.dao;

import com.jp.entity.Worship;
import com.jp.entity.WorshipExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorshipMapper {
    int countByExample(WorshipExample example);

    int deleteByExample(WorshipExample example);

    int deleteByPrimaryKey(String id);

    int insert(Worship record);

    int insertSelective(Worship record);

    List<Worship> selectByExample(WorshipExample example);

    Worship selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Worship record, @Param("example") WorshipExample example);

    int updateByExample(@Param("record") Worship record, @Param("example") WorshipExample example);

    int updateByPrimaryKeySelective(Worship record);

    int updateByPrimaryKey(Worship record);
}