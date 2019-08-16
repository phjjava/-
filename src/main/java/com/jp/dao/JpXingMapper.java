package com.jp.dao;

import com.jp.entity.InstructionTemplateQuery;
import com.jp.entity.JpXing;
import com.jp.entity.JpXingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JpXingMapper {
    int countByExample(JpXingExample example);

    int deleteByExample(JpXingExample example);

    int deleteByPrimaryKey(String id);

    int insert(JpXing record);

    int insertSelective(JpXing record);

    List<JpXing> selectByExample(JpXingExample example);

    JpXing selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") JpXing record, @Param("example") JpXingExample example);

    int updateByExample(@Param("record") JpXing record, @Param("example") JpXingExample example);

    int updateByPrimaryKeySelective(JpXing record);

    int updateByPrimaryKey(JpXing record);

	List<JpXing> selectByExample(InstructionTemplateQuery iq);

	List<JpXing> selectByExample1(@Param("xname")String xname,@Param("iq") InstructionTemplateQuery iq);

	int xingDeleteAll(@Param("array") String[] ids);

	int SelectCount(@Param("xname")String xname);

	void updateRcount(String xname);

	List<JpXing> hotlist();

	List<JpXing> selectByExampleNew(InstructionTemplateQuery iq);
}