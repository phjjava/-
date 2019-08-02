package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.SysNotice;
import com.jp.entity.SysNoticeType;

public interface SysNoticeService {

	List<SysNotice> selectNotice();
	/**
	 * 类型下拉
	 * @return
	 */
	List<SysNoticeType> selecttypelist();
	/**
	 * 修改
	 * @param notice
	 * @return
	 */
	Integer update(SysNotice notice);
	/**
	 * 新增
	 * @param notice
	 * @return
	 */
	Integer insert(SysNotice notice);
	/**
	 * 公告详情
	 * @param noticeid
	 * @return
	 */
	List<SysNotice> selectOne(String noticeid);
	
	/**
	 * 以下公告类型接口
	 *
	 * @return
	 */
	
	List<SysNoticeType> selecttype();
	/**
	 * 详情
	 * @param typeid
	 * @return
	 */
	List<SysNoticeType> selecttypeone(String typeid);
	/**
	 * 类型修改
	 * @param noticetype
	 * @return
	 */
	Integer updatetype(SysNoticeType noticetype);
	/**
	 * 类型新增
	 * @param noticetype
	 * @return
	 */
	Integer inserttype(SysNoticeType noticetype);
	/**
	 * 类型批删
	 * @param mationtypeArray
	 * @return
	 * @throws Exception
	 */
	int noticetypeDeleteAll(@Param("array")String[] mationtypeArray)throws Exception;
	/**
	 * 公告批删
	 * @param mationtypeArray
	 */
	int noticeDeleteAll(@Param("array")String[] mationtypeArray)throws Exception;


}
