package com.jp.dao;

import com.jp.entity.BannerQuery;
import com.jp.entity.FamousPerson;
import com.jp.entity.FamousPersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FamousPersonMapper {
    int countByExample(FamousPersonExample example);

    int deleteByExample(FamousPersonExample example);

    int deleteByPrimaryKey(String famousid);

    int insert(FamousPerson record);

    int insertSelective(FamousPerson record);

    List<FamousPerson> selectByExample(BannerQuery bq);

    FamousPerson selectByPrimaryKey(String famousid);

    int updateByExampleSelective(@Param("record") FamousPerson record, @Param("example") FamousPersonExample example);

    int updateByExample(@Param("record") FamousPerson record, @Param("example") FamousPersonExample example);

    int updateByPrimaryKeySelective(FamousPerson record);

    int updateByPrimaryKey(FamousPerson record);

	List<FamousPerson> selectByExampleNew(BannerQuery bq);

	int batchDeleteAll(String[] famousids);
}