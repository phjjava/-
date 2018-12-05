package com.jp.dao;

import com.jp.entity.Noticefile;
import com.jp.entity.NoticefileKey;
import com.jp.entity.NoticefileQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NoticefileDao {
    int countByExample(NoticefileQuery example);

    int deleteByExample(NoticefileQuery example);

    int deleteByPrimaryKey(NoticefileKey key);

    int insert(Noticefile record);

    int insertSelective(Noticefile record);

    List<Noticefile> selectByExample(NoticefileQuery example);

    Noticefile selectByPrimaryKey(NoticefileKey key);

    int updateByExampleSelective(@Param("record") Noticefile record, @Param("example") NoticefileQuery example);

    int updateByExample(@Param("record") Noticefile record, @Param("example") NoticefileQuery example);

    int updateByPrimaryKeySelective(Noticefile record);

    int updateByPrimaryKey(Noticefile record);
    
    int insertnoticefileSelective(List<Noticefile> dylist);
    
    int deletefile(@Param("array") String ntfid[]);
}