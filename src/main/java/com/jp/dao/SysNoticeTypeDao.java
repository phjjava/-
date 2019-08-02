package com.jp.dao;

import java.util.List;

import com.jp.entity.SysNoticeType;

public interface SysNoticeTypeDao {

	List<SysNoticeType> selectByExample();
	
	List<SysNoticeType> selecttypelist();
	
	List<SysNoticeType> selecttypeone(String typeid);

	Integer updateByPrimaryKeySelective(SysNoticeType noticetype);

	Integer insertSelective(SysNoticeType noticetype);

	int noticetypeDeleteAll(String[] noticetypeids);

}
