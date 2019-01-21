package com.jp.dao;

import com.jp.entity.IntroudceTemplate;
import com.jp.entity.IntroudceTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IntroudceTemplateDao {
    int countByExample(IntroudceTemplateExample example);

    int deleteByExample(IntroudceTemplateExample example);

    int deleteByPrimaryKey(String id);

    int insert(IntroudceTemplate record);

    int insertSelective(IntroudceTemplate record);

    List<IntroudceTemplate> selectByExample(IntroudceTemplateExample example);

    IntroudceTemplate selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IntroudceTemplate record, @Param("example") IntroudceTemplateExample example);

    int updateByExample(@Param("record") IntroudceTemplate record, @Param("example") IntroudceTemplateExample example);

    int updateByPrimaryKeySelective(IntroudceTemplate record);

    int updateByPrimaryKey(IntroudceTemplate record);
}