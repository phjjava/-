package com.jp.dao;

import com.jp.entity.BannerQuery;
import com.jp.entity.InstructionTemplateQuery;
import com.jp.entity.IntroudceTemplate;
import com.jp.entity.IntroudceTemplateExample;
import com.jp.entity.SysMation;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IntroudceTemplateDao {
    int countByExample(IntroudceTemplateExample example);

    int deleteByExample(IntroudceTemplateExample example);

    int deleteByPrimaryKey(String id);

    int insert(IntroudceTemplate record);

    int insertSelective(IntroudceTemplate record);
    
    List<IntroudceTemplate> selectByExample(IntroudceTemplateExample introudceTemplateExample);

    IntroudceTemplate selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IntroudceTemplate record, @Param("example") IntroudceTemplateExample example);

    int updateByExample(@Param("record") IntroudceTemplate record, @Param("example") IntroudceTemplateExample example);

    int updateByPrimaryKeySelective(IntroudceTemplate record);

    int updateByPrimaryKey(IntroudceTemplate record);
    //模板书分页列表
	List<IntroudceTemplate> selectByExample(InstructionTemplateQuery iq);
	
	void intemplateDeleteAll(String[] ids);
	//模板书下拉框
	List<IntroudceTemplate> selectByExample();
	//api接口
	List<SysMation> selectByExampleNew(BannerQuery bq);
}