package com.jp.dao;

import com.jp.entity.JpXingContent;
import com.jp.entity.JpXingContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JpXingContentMapper {
    int countByExample(JpXingContentExample example);

    int deleteByExample(JpXingContentExample example);

    int insert(JpXingContent record);

    int insertSelective(JpXingContent record);

    List<JpXingContent> selectByExample(JpXingContentExample example);

    int updateByExampleSelective(@Param("record") JpXingContent record);

    int updateByExample(@Param("record") JpXingContent record, @Param("example") JpXingContentExample example);

	String selectByExample(@Param("id")String id,@Param("code") String code);

	void updateByPrimaryKeySelective(JpXingContent xingContent);
}