package com.jp.dao;

import com.jp.entity.Usercontent;
import com.jp.entity.UsercontentQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsercontentDao {
    int countByExample(UsercontentQuery example);

    int deleteByExample(UsercontentQuery example);

    int deleteByPrimaryKey(String userid);

    int insert(Usercontent record);

    int insertSelective(Usercontent record);

    List<Usercontent> selectByExampleWithBLOBs(UsercontentQuery example);

    List<Usercontent> selectByExample(UsercontentQuery example);

    Usercontent selectByPrimaryKey(String userid);

    int updateByExampleSelective(@Param("record") Usercontent record, @Param("example") UsercontentQuery example);

    int updateByExampleWithBLOBs(@Param("record") Usercontent record, @Param("example") UsercontentQuery example);

    int updateByExample(@Param("record") Usercontent record, @Param("example") UsercontentQuery example);

    int updateByPrimaryKeySelective(Usercontent record);

    int updateByPrimaryKeyWithBLOBs(Usercontent record);

    int updateByPrimaryKey(Usercontent record);
}