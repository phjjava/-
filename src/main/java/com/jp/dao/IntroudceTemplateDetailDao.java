package com.jp.dao;

import com.jp.entity.InstructionTemplateQuery;
import com.jp.entity.IntroudceTemplate;
import com.jp.entity.IntroudceTemplateDetail;
import com.jp.entity.IntroudceTemplateDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IntroudceTemplateDetailDao {
    int countByExample(IntroudceTemplateDetailExample example);

    int deleteByExample(IntroudceTemplateDetailExample example);

    int deleteByPrimaryKey(String id);

    int insert(IntroudceTemplateDetail record);

    int insertSelective(IntroudceTemplateDetail record);

    List<IntroudceTemplateDetail> selectByExampleWithBLOBs(IntroudceTemplateDetailExample example);

    List<IntroudceTemplateDetail> selectByExample(String title);

    IntroudceTemplateDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IntroudceTemplateDetail record, @Param("example") IntroudceTemplateDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") IntroudceTemplateDetail record, @Param("example") IntroudceTemplateDetailExample example);

    int updateByExample(@Param("record") IntroudceTemplateDetail record, @Param("example") IntroudceTemplateDetailExample example);

    int updateByPrimaryKeySelective(IntroudceTemplateDetail record);

    int updateByPrimaryKeyWithBLOBs(IntroudceTemplateDetail record);

    int updateByPrimaryKey(IntroudceTemplateDetail record);

	List<IntroudceTemplateDetail> selectByExample(InstructionTemplateQuery iq);

	void intemplateDeleteAll(String[] ids);

	List<IntroudceTemplateDetail> selectByExample1(@Param("iq")InstructionTemplateQuery iq, @Param("title") String title);

	int selectCount();
}