package com.jp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.jp.entity.MomentLike;
import com.jp.entity.MomentLikeExample;

public interface MomentLikeMapper{
    int countByExample(MomentLikeExample example);

    int deleteByExample(MomentLikeExample example);

    int deleteByPrimaryKey(String id);

    Integer insert(MomentLike record);

    int insertSelective(MomentLike record);

    List<MomentLike> selectByExample(MomentLikeExample example);

    MomentLike selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MomentLike record, @Param("example") MomentLikeExample example);

    int updateByExample(@Param("record") MomentLike record, @Param("example") MomentLikeExample example);

    int updateByPrimaryKeySelective(MomentLike record);

    int updateByPrimaryKey(MomentLike record);
}