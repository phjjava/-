package com.jp.dao;

import com.jp.entity.Dyprise;
import com.jp.entity.DypriseKey;
import com.jp.entity.DypriseQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DypriseDao {
    int countByExample(DypriseQuery example);

    int deleteByExample(DypriseQuery example);

    int deleteByPrimaryKey(DypriseKey key);

    int insert(Dyprise record);

    int insertSelective(Dyprise record);

    List<Dyprise> selectByExample(DypriseQuery example);

    Dyprise selectByPrimaryKey(DypriseKey key);

    int updateByExampleSelective(@Param("record") Dyprise record, @Param("example") DypriseQuery example);

    int updateByExample(@Param("record") Dyprise record, @Param("example") DypriseQuery example);

    int updateByPrimaryKeySelective(Dyprise record);

    int updateByPrimaryKey(Dyprise record);
}