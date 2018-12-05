package com.jp.dao;

import com.jp.entity.SysVersion;
import com.jp.entity.SysVersionQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysVersionDao {
    int countByExample(SysVersionQuery example);

    int deleteByExample(SysVersionQuery example);

    int deleteByPrimaryKey(String versionid);

    int insert(SysVersion record);

    int insertSelective(SysVersion record);

    List<SysVersion> selectByExample(SysVersionQuery example);

    SysVersion selectByPrimaryKey(String versionid);

    int updateByExampleSelective(@Param("record") SysVersion record, @Param("example") SysVersionQuery example);

    int updateByExample(@Param("record") SysVersion record, @Param("example") SysVersionQuery example);

    int updateByPrimaryKeySelective(SysVersion record);

    int updateByPrimaryKey(SysVersion record);
}