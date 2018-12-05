package com.jp.dao;

import com.jp.entity.WorshipOblationType;
import com.jp.entity.WorshipOblationTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorshipOblationTypeMapper {
    int countByExample(WorshipOblationTypeExample example);

    int deleteByExample(WorshipOblationTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(WorshipOblationType record);

    int insertSelective(WorshipOblationType record);

    List<WorshipOblationType> selectByExample(WorshipOblationTypeExample example);

    WorshipOblationType selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") WorshipOblationType record, @Param("example") WorshipOblationTypeExample example);

    int updateByExample(@Param("record") WorshipOblationType record, @Param("example") WorshipOblationTypeExample example);

    int updateByPrimaryKeySelective(WorshipOblationType record);

    int updateByPrimaryKey(WorshipOblationType record);
}