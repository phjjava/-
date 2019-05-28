package com.jp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.jp.entity.MomentUserSpot;
import com.jp.entity.MomentUserSpotExample;

public interface MomentUserSpotMapper {
    int countByExample(MomentUserSpotExample example);

    int deleteByExample(MomentUserSpotExample example);

    int deleteByPrimaryKey(String id);

    Integer insert(MomentUserSpot record);

    int insertSelective(MomentUserSpot record);

    List<MomentUserSpot> selectByExample(MomentUserSpotExample example);

    MomentUserSpot selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MomentUserSpot record, @Param("example") MomentUserSpotExample example);

    int updateByExample(@Param("record") MomentUserSpot record, @Param("example") MomentUserSpotExample example);

    int updateByPrimaryKeySelective(MomentUserSpot record);

    int updateByPrimaryKey(MomentUserSpot record);
}