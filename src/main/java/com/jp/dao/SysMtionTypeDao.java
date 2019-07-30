package com.jp.dao;

import java.util.List;

import com.jp.entity.MationType;

public interface SysMtionTypeDao {

	List<MationType> selecttypelist();

	int insertSelective(MationType mationtype);

	int updateByPrimaryKeySelective(MationType mationtype);

	int mationtypeDeleteAll(String[] mationtypeids);

}
