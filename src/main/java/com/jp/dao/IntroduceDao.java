package com.jp.dao;

import com.jp.entity.Introduce;
import com.jp.entity.IntroduceQuery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IntroduceDao {
    int countByExample(IntroduceQuery example);

    int deleteByExample(IntroduceQuery example);

    int deleteByPrimaryKey(String introduceid);

    int insert(Introduce record);

    int insertSelective(Introduce record);

    List<Introduce> selectByExampleWithBLOBs(IntroduceQuery example);

    List<Introduce> selectByExample(IntroduceQuery example);

    Introduce selectByPrimaryKey(String introduceid);

    int updateByExampleSelective(@Param("record") Introduce record, @Param("example") IntroduceQuery example);

    int updateByExampleWithBLOBs(@Param("record") Introduce record, @Param("example") IntroduceQuery example);

    int updateByExample(@Param("record") Introduce record, @Param("example") IntroduceQuery example);

    int updateByPrimaryKeySelective(Introduce record);

    int updateByPrimaryKeyWithBLOBs(Introduce record);

    int updateByPrimaryKey(Introduce record);
    
    int batchDelete(@Param("array") String introduceid[]);
    
    /**
	* 以下方法用于api
	*/
    
    List<Introduce> selectByPrimaryID(String introduceid);
    
    Introduce selectPreIntroduce(Map<String, String> map);
    
    Introduce selectNextIntroduce(Map<String, String> map);
    
    Introduce getIntroduceByType(Map<String, Object> params);
    
}