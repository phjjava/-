package com.jp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.jp.entity.MomentUserStore;
import com.jp.entity.MomentUserStoreExample;

public interface MomentUserStoreMapper {
    int countByExample(MomentUserStoreExample example);

    int deleteByExample(MomentUserStoreExample example);

    int deleteByPrimaryKey(String id);

    int insert(MomentUserStore record);

    int insertSelective(MomentUserStore record);

    List<MomentUserStore> selectByExample(MomentUserStoreExample example);

    MomentUserStore selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MomentUserStore record, @Param("example") MomentUserStoreExample example);

    int updateByExample(@Param("record") MomentUserStore record, @Param("example") MomentUserStoreExample example);

    int updateByPrimaryKeySelective(MomentUserStore record);

    int updateByPrimaryKey(MomentUserStore record);
}