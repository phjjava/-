package com.jp.dao;

import com.jp.entity.OblationVersionExample;
import com.jp.entity.OblationVersionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OblationVersionMapper {
    int countByExample(OblationVersionExample example);

    int deleteByExample(OblationVersionExample example);

    int deleteByPrimaryKey(OblationVersionKey key);

    int insert(OblationVersionKey record);

    int insertSelective(OblationVersionKey record);

    List<OblationVersionKey> selectByExample(OblationVersionExample example);

    int updateByExampleSelective(@Param("record") OblationVersionKey record, @Param("example") OblationVersionExample example);

    int updateByExample(@Param("record") OblationVersionKey record, @Param("example") OblationVersionExample example);
}