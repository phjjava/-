package com.jp.dao;

import com.jp.entity.JpMemberRecord;
import com.jp.entity.JpMemberRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JpMemberRecordMapper {
    int countByExample(JpMemberRecordExample example);

    int deleteByExample(JpMemberRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(JpMemberRecord record);

    int insertSelective(JpMemberRecord record);

    List<JpMemberRecord> selectByExample(JpMemberRecordExample example);

    JpMemberRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") JpMemberRecord record, @Param("example") JpMemberRecordExample example);

    int updateByExample(@Param("record") JpMemberRecord record, @Param("example") JpMemberRecordExample example);

    int updateByPrimaryKeySelective(JpMemberRecord record);

    int updateByPrimaryKey(JpMemberRecord record);

	Integer updateStatus(String id);
}