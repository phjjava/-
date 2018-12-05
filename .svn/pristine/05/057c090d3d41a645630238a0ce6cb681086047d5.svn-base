package com.jp.dao;

import com.jp.entity.Userfuncgroup;
import com.jp.entity.UserfuncgroupQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserfuncgroupDao {
    int countByExample(UserfuncgroupQuery example);

    int deleteByExample(UserfuncgroupQuery example);

    int deleteByPrimaryKey(String userfuncgroupid);

    int insert(Userfuncgroup record);

    int insertSelective(Userfuncgroup record);

    List<Userfuncgroup> selectByExample(UserfuncgroupQuery example);

    Userfuncgroup selectByPrimaryKey(String userfuncgroupid);

    int updateByExampleSelective(@Param("record") Userfuncgroup record, @Param("example") UserfuncgroupQuery example);

    int updateByExample(@Param("record") Userfuncgroup record, @Param("example") UserfuncgroupQuery example);

    int updateByPrimaryKeySelective(Userfuncgroup record);

    int updateByPrimaryKey(Userfuncgroup record);
}