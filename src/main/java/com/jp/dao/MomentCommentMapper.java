package com.jp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.jp.entity.MomentComment;
import com.jp.entity.MomentCommentExample;

public interface MomentCommentMapper {
    int countByExample(MomentCommentExample example);

    int deleteByExample(MomentCommentExample example);

    int deleteByPrimaryKey(String id);

    int insert(MomentComment record);

    int insertSelective(MomentComment record);

    List<MomentComment> selectByExample(MomentCommentExample example);

    MomentComment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MomentComment record, @Param("example") MomentCommentExample example);

    int updateByExample(@Param("record") MomentComment record, @Param("example") MomentCommentExample example);

    int updateByPrimaryKeySelective(MomentComment record);

    int updateByPrimaryKey(MomentComment record);
}