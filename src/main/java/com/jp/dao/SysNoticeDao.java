package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.BannerQuery;
import com.jp.entity.SysNotice;

public interface SysNoticeDao {

	List<SysNotice> selectByExample();

	Integer insertSelective(SysNotice notice);

	Integer updateByPrimaryKeySelective(SysNotice notice);

	List<SysNotice> selectOne(String noticeid);

	void updatecount(String noticeid);

	int noticeDeleteAll(String[] mationtypeids);

	List<SysNotice> selectByExample(@Param("bq") BannerQuery bq, @Param("noticetitle") String noticetitle);
	//api接口
	List<SysNotice> selectByExampleNew(BannerQuery bq);

}
