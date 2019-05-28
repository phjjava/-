package com.jp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.jp.entity.MomentLikeTimeline;
import com.jp.entity.MomentLikeTimelineExample;

public interface MomentLikeTimelineMapper {
    int countByExample(MomentLikeTimelineExample example);

    int deleteByExample(MomentLikeTimelineExample example);

    int deleteByPrimaryKey(String id);

    Integer insert(MomentLikeTimeline record);

    int insertSelective(MomentLikeTimeline record);

    List<MomentLikeTimeline> selectByExample(MomentLikeTimelineExample example);

    MomentLikeTimeline selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MomentLikeTimeline record, @Param("example") MomentLikeTimelineExample example);

    int updateByExample(@Param("record") MomentLikeTimeline record, @Param("example") MomentLikeTimelineExample example);

    int updateByPrimaryKeySelective(MomentLikeTimeline record);

    int updateByPrimaryKey(MomentLikeTimeline record);
}