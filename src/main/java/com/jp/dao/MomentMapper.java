package com.jp.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.jp.entity.Moment;
import com.jp.entity.MomentExample;

public interface MomentMapper {
    int countByExample(MomentExample example);

    int deleteByExample(MomentExample example);

    int deleteByPrimaryKey(String id);

    Integer insert(Moment record);

    int insertSelective(Moment record);

    List<Moment> selectByExample(MomentExample example);

    Moment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Moment record, @Param("example") MomentExample example);

    int updateByExample(@Param("record") Moment record, @Param("example") MomentExample example);

    int updateByPrimaryKeySelective(Moment record);

    int updateByPrimaryKey(Moment record);

	List<Moment> selectListByUserid(Moment entity);
}