package com.jp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.jp.entity.MomentUserFilter;
import com.jp.entity.MomentUserFilterExample;

public interface MomentUserFilterMapper {
    int countByExample(MomentUserFilterExample example);

    int deleteByExample(MomentUserFilterExample example);

    int deleteByPrimaryKey(String id);

    int insert(MomentUserFilter record);

    int insertSelective(MomentUserFilter record);

    List<MomentUserFilter> selectByExample(MomentUserFilterExample example);

    MomentUserFilter selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MomentUserFilter record, @Param("example") MomentUserFilterExample example);

    int updateByExample(@Param("record") MomentUserFilter record, @Param("example") MomentUserFilterExample example);

    int updateByPrimaryKeySelective(MomentUserFilter record);

    int updateByPrimaryKey(MomentUserFilter record);

	List<String> selectFilterUsers(String userid);
}