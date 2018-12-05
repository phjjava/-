package com.jp.dao;

import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorshipOblationMapper {
    int countByExample(WorshipOblationExample example);

    int deleteByExample(WorshipOblationExample example);

    int deleteByPrimaryKey(String id);

    int insert(WorshipOblation record);

    int insertSelective(WorshipOblation record);

    List<WorshipOblation> selectByExample(WorshipOblationExample example);

    WorshipOblation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") WorshipOblation record, @Param("example") WorshipOblationExample example);

    int updateByExample(@Param("record") WorshipOblation record, @Param("example") WorshipOblationExample example);

    int updateByPrimaryKeySelective(WorshipOblation record);

    int updateByPrimaryKey(WorshipOblation record);
}