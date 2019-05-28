package com.jp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.jp.entity.MomentCommentTimeline;
import com.jp.entity.MomentCommentTimelineExample;

public interface MomentCommentTimelineMapper {
    int countByExample(MomentCommentTimelineExample example);

    int deleteByExample(MomentCommentTimelineExample example);

    int deleteByPrimaryKey(String id);

    int insert(MomentCommentTimeline record);

    int insertSelective(MomentCommentTimeline record);

    List<MomentCommentTimeline> selectByExample(MomentCommentTimelineExample example);

    MomentCommentTimeline selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MomentCommentTimeline record, @Param("example") MomentCommentTimelineExample example);

    int updateByExample(@Param("record") MomentCommentTimeline record, @Param("example") MomentCommentTimelineExample example);

    int updateByPrimaryKeySelective(MomentCommentTimeline record);

    int updateByPrimaryKey(MomentCommentTimeline record);
}