package com.jp.dao;

import com.jp.entity.Useredu;
import com.jp.entity.UsereduKey;
import com.jp.entity.UsereduQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsereduDao {
    int countByExample(UsereduQuery example);

    int deleteByExample(UsereduQuery example);

    int deleteByPrimaryKey(UsereduKey key);

    int insert(Useredu record);

    int insertSelective(Useredu record);

    List<Useredu> selectByExample(UsereduQuery example);

    Useredu selectByPrimaryKey(UsereduKey key);

    int updateByExampleSelective(@Param("record") Useredu record, @Param("example") UsereduQuery example);

    int updateByExample(@Param("record") Useredu record, @Param("example") UsereduQuery example);

    int updateByPrimaryKeySelective(Useredu record);

    int updateByPrimaryKey(Useredu record);
    int insertEduExp(@Param("list")List<Useredu> userEduList);
    List<Useredu> selectByUserId(String userid);
}