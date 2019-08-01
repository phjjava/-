package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.jp.entity.BannerQuery;
import com.jp.entity.MationExample;
import com.jp.entity.MationType;
import com.jp.entity.Nation;
import com.jp.entity.NationExample;
import com.jp.entity.SysMation;

public interface SysMationdao {
	int countByExample(MationExample example);

	int deleteByExample(MationExample example);
	
	//资讯批删
	int mationDeleteAll(@Param("array")String[] mationids);

	int insert(MationExample example);

	SysMation selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Nation record, @Param("example") MationExample example);

	int updateByExample(@Param("record") Nation record, @Param("example") MationExample example);

	int updateByPrimaryKey(MationExample mationid);
	
	List<SysMation> selectByExample(BannerQuery bq);
	
	int insertSelective(SysMation mation);
	
	int updateByPrimaryKeySelective(SysMation mation);

	List<MationType> selecttypelist();

	void updateCount(String mationid);

	List<SysMation> selectByExample(@Param("mationtitle") String mationtitle);
	List<SysMation> selectByExample();

}