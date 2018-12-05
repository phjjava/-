package com.jp.dao;

import com.jp.entity.WorshipAnnex;
import com.jp.entity.WorshipAnnexExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorshipAnnexMapper {
    int countByExample(WorshipAnnexExample example);

    int deleteByExample(WorshipAnnexExample example);

    int deleteByPrimaryKey(String id);

    int insert(WorshipAnnex record);

    int insertSelective(WorshipAnnex record);

    List<WorshipAnnex> selectByExample(WorshipAnnexExample example);

    WorshipAnnex selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") WorshipAnnex record, @Param("example") WorshipAnnexExample example);

    int updateByExample(@Param("record") WorshipAnnex record, @Param("example") WorshipAnnexExample example);

    int updateByPrimaryKeySelective(WorshipAnnex record);

    int updateByPrimaryKey(WorshipAnnex record);
}