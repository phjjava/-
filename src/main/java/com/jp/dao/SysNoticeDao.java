package com.jp.dao;

import java.util.List;

import com.jp.entity.SysNotice;

public interface SysNoticeDao {

	List<SysNotice> selectByExample();

	Integer insertSelective(SysNotice notice);

	Integer updateByPrimaryKeySelective(SysNotice notice);

}
