package com.jp.service;

import java.util.List;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
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
	PageModel<UserManager> pageQuery(PageModel<UserManager> pageModel, UserManager entity) throws Exception;

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

	JsonResponse save(UserManager manager, String[] functionids);

	JsonResponse del(String id);

	List<UserManager> selectManagerByUserid(String userid);

	UserManager selectByFamilyId(String familyid);

	JsonResponse getPost(int type, String familyid);

}
