package com.jp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Moment;
import com.jp.entity.MomentTimeline;
import com.jp.entity.MomentTimelineExample;

public interface MomentTimelineMapper {
    int countByExample(MomentTimelineExample example);

    int deleteByExample(MomentTimelineExample example);

    int deleteByPrimaryKey(String id);

    Integer insert(MomentTimeline record);

    int insertSelective(MomentTimeline record);

    List<MomentTimeline> selectByExample(MomentTimelineExample example);

    MomentTimeline selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MomentTimeline record, @Param("example") MomentTimelineExample example);

    int updateByExample(@Param("record") MomentTimeline record, @Param("example") MomentTimelineExample example);

    int updateByPrimaryKeySelective(MomentTimeline record);

    int updateByPrimaryKey(MomentTimeline record);

	void insertBatch(@Param("list") List<MomentTimeline> lines);

	List<Moment> selectMomentByUserid(Map<String,Object> params);

	List<Moment> selectPersonListByUserid(Map<String, Object> params);
}