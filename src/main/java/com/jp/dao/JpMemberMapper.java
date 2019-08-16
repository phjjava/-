package com.jp.dao;

import com.jp.entity.JpMember;
import com.jp.entity.JpMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JpMemberMapper {
    int countByExample(JpMemberExample example);

    int deleteByExample(JpMemberExample example);

    int deleteByPrimaryKey(String mid);

    int insert(JpMember record);

    int insertSelective(JpMember record);

    List<JpMember> selectByExample(JpMemberExample example);

    JpMember selectByPrimaryKey(String mid);

    int updateByExampleSelective(@Param("record") JpMember record, @Param("example") JpMemberExample example);

    int updateByExample(@Param("record") JpMember record, @Param("example") JpMemberExample example);

    int updateByPrimaryKeySelective(JpMember record);

    int updateByPrimaryKey(JpMember record);
}