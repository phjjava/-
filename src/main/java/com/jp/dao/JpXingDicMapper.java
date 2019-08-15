package com.jp.dao;

import com.jp.entity.JpXingDic;
import com.jp.entity.JpXingDicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JpXingDicMapper {
    int countByExample(JpXingDicExample example);

    int deleteByExample(JpXingDicExample example);

    int insert(JpXingDic record);

    int insertSelective(JpXingDic record);

    List<JpXingDic> selectByExample(JpXingDicExample example);

    int updateByExampleSelective(@Param("record") JpXingDic record, @Param("example") JpXingDicExample example);

    int updateByExample(@Param("record") JpXingDic record, @Param("example") JpXingDicExample example);
}