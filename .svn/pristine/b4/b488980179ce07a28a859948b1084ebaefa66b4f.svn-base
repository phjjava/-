package com.jp.dao;

import com.jp.entity.Noticeread;
import com.jp.entity.NoticereadQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoticereadDao {
    int countByExample(NoticereadQuery example);

    int deleteByExample(NoticereadQuery example);

    int deleteByPrimaryKey(String id);

    int insert(Noticeread record);

    int insertSelective(Noticeread record);

    List<Noticeread> selectByExample(NoticereadQuery example);

    Noticeread selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Noticeread record, @Param("example") NoticereadQuery example);

    int updateByExample(@Param("record") Noticeread record, @Param("example") NoticereadQuery example);

    int updateByPrimaryKeySelective(Noticeread record);

    int updateByPrimaryKey(Noticeread record);
}