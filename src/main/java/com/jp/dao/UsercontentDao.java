package com.jp.dao;

import com.jp.entity.UserContentVO;
import com.jp.entity.Usercontent;
import com.jp.entity.UsercontentExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UsercontentDao {
    int countByExample(UsercontentExample example);

    int deleteByExample(UsercontentExample example);

    int deleteByPrimaryKey(String userid);

    int insert(Usercontent record);

    int insertSelective(Usercontent record);

    List<Usercontent> selectByExampleWithBLOBs(UsercontentExample example);

    List<Usercontent> selectByExample(UsercontentExample example);

    Usercontent selectByPrimaryKey(String userid);

    int updateByExampleSelective(@Param("record") Usercontent record, @Param("example") UsercontentExample example);

    int updateByExampleWithBLOBs(@Param("record") Usercontent record, @Param("example") UsercontentExample example);

    int updateByExample(@Param("record") Usercontent record, @Param("example") UsercontentExample example);

    int updateByPrimaryKeySelective(Usercontent record);

    int updateByPrimaryKeyWithBLOBs(Usercontent record);

    int updateByPrimaryKey(Usercontent record);

	int batchDelete(@Param("array")String[] idArray);
	
	List<UserContentVO> searchtUserContent(Map<String, Object> map);
}