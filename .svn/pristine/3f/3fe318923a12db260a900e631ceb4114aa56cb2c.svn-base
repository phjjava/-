package com.jp.dao;

import com.jp.entity.Dycomment;
import com.jp.entity.DycommentKey;
import com.jp.entity.DycommentQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DycommentDao {
    int countByExample(DycommentQuery example);

    int deleteByExample(DycommentQuery example);

    int deleteByPrimaryKey(DycommentKey key);

    int insert(Dycomment record);

    int insertSelective(Dycomment record);

    List<Dycomment> selectByExampleWithBLOBs(DycommentQuery example);

    List<Dycomment> selectByExample(DycommentQuery example);

    Dycomment selectByPrimaryKey(DycommentKey key);

    int updateByExampleSelective(@Param("record") Dycomment record, @Param("example") DycommentQuery example);

    int updateByExampleWithBLOBs(@Param("record") Dycomment record, @Param("example") DycommentQuery example);

    int updateByExample(@Param("record") Dycomment record, @Param("example") DycommentQuery example);

    int updateByPrimaryKeySelective(Dycomment record);

    int updateByPrimaryKeyWithBLOBs(Dycomment record);

    int updateByPrimaryKey(Dycomment record);
}