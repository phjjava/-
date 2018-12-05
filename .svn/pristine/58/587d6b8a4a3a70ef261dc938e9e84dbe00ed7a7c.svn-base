package com.jp.service;

import java.util.List;

import com.jp.common.PageModel;
import com.jp.entity.Indexcount;
import com.jp.entity.SysFamily;
import com.jp.entity.User;
import com.jp.entity.Userinfo;
import com.jp.util.Result;

public interface FamilyService {
	/**
	 * 
	 * @描述 家族的保存或编辑
	 * @作者 sj
	 * @时间 2017年5月10日上午9:42:10
	 * @参数 @param family
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return Integer
	 */
    Result merge(User user, Userinfo userInfo, SysFamily family) throws Exception;
	/**
	 * 
	 * @描述 查询家族列表
	 * @作者 sj
	 * @时间 2017年5月10日上午9:42:40
	 * @参数 @param pageModel
	 * @参数 @param family
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel
	 */
	PageModel selectFamilyList(PageModel pageModel,SysFamily family) throws Exception;
	/**
	 * 
	 * @描述 查询单个家族
	 * @作者 sj
	 * @时间 2017年5月10日上午9:43:33
	 * @参数 @param familyid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return SysFamily
	 */
	SysFamily selectByPrimaryKey(String familyid) throws Exception;
	/**
	 * 
	 * @描述 改变家族状态
	 * @作者 sj
	 * @时间 2017年5月10日上午9:43:54
	 * @参数 @param family
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int changeStatus(SysFamily family) throws Exception;
	/**
	 * @描述 首页统计
	 * @作者 sj
	 * @时间 2017年5月23日上午11:24:09
	 * @参数 @param familyid
	 * @参数 @param branchids
	 * @参数 @return
	 * @return Indexcount
	 */
	Indexcount countIndex(String familyid,List<String> branchids);
}