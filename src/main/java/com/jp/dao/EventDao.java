package com.jp.dao;

import com.jp.entity.Event;
import com.jp.entity.EventQuery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface EventDao {
    int countByExample(EventQuery example);

    int deleteByExample(EventQuery example);

    int deleteByPrimaryKey(String eventid);

    int insert(Event record);

    int insertSelective(Event record);

    List<Event> selectByExampleWithBLOBs(EventQuery example);

    List<Event> selectByExample(EventQuery example);

    Event selectByPrimaryKey(String eventid);

    int updateByExampleSelective(@Param("record") Event record, @Param("example") EventQuery example);

    int updateByExampleWithBLOBs(@Param("record") Event record, @Param("example") EventQuery example);

    int updateByExample(@Param("record") Event record, @Param("example") EventQuery example);

    int updateByPrimaryKeySelective(Event record);

    int updateByPrimaryKeyWithBLOBs(Event record);

    int updateByPrimaryKey(Event record);
    
    int batchDelete(@Param("array") String noticeid[]);
    
    List<Event> selecteventread(Event event);
    
    /**
	* 以下方法用于api
	*/
    
    List<Event> getEventList(Map<String, String> map);
}