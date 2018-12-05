package com.jp.service;

import java.util.List;

import com.jp.common.PageModel;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Role;
import com.jp.entity.UserManager;

public interface UserManagerService {

	/**
	 * @描述 分页查询管理员数据
	 * @作者 chenxiaobing
	 * @时间 2018年8月23日下午11:24:11
	 * @参数 @param pageModel
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel<Role>
	 */
	PageModel<UserManager> pageQuery(PageModel<UserManager> pageModel,UserManager entity) throws Exception;

	

	/**
	 * @描述 获取编委会详情
	 * @作者 chenxiaobing
	 * @时间 2018年8月27日下午11:24:22
	 * @参数 @param id
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	UserManager getUserManager(String id) throws Exception;

	/**
	 * @描述 修改编委会
	 * @作者 chenxiaobing
	 * @时间 2018年8月27日下午4:21:48
	 * @参数 @param eb
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	Integer update(UserManager manager,String[] functionids) throws Exception;
	
	
	/**
	 * @描述 新增编委会
	 * @作者 chenxiaobing
	 * @时间 2018年8月27日下午5:00:56
	 * @参数 @param eb
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<Role>
	 */
	Integer insert(UserManager manager,String[] functionids) throws Exception;

	int del(String id);



	List<UserManager> selectManagerByUserid(String userid);



	UserManager selectByFamilyId(String familyid);

}
