package com.jp.service;

import java.util.List;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Role;

public interface RoleService {

	/**
	 * @描述 分页查询角色数据
	 * @作者 wumin
	 * @时间 2017年5月1日下午11:24:11
	 * @参数 @param pageModel
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel<Role>
	 */
	PageModel<Role> pageQuery(PageModel<Role> pageModel, Role role) throws Exception;

	/**
	 * @描述 新增角色数据
	 * @作者 wumin
	 * @时间 2017年5月1日下午11:24:22
	 * @参数 @param role
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int insert(Role role, String[] functionids) throws Exception;

	/**
	 * @描述 根据id获取角色详细信息
	 * @作者 wumin
	 * @时间 2017年5月2日下午3:07:51
	 * @参数 @param roleid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return Role
	 */
	Role get(String roleid) throws Exception;

	/**
	 * @描述 修改角色
	 * @作者 wumin
	 * @时间 2017年5月3日下午4:21:48
	 * @参数 @param role
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int update(Role role, String[] functionids) throws Exception;

	/**
	 * @描述 获取role集合
	 * @作者 wumin
	 * @时间 2017年5月12日下午5:00:56
	 * @参数 @param familyid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<Role>
	 */
	List<Role> selectRoleList(String familyid) throws Exception;

	/**
	 * @描述 根据familyid 与userid 获取角色信息
	 * @作者 wumin
	 * @时间 2017年5月13日下午3:03:08
	 * @参数 @param familyid
	 * @参数 @param userid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return Role
	 */
	Role selectRoleByUserid(String familyid, String userid) throws Exception;

	int del(String roleid);

	JsonResponse getAdminListNew(Role entity);

	JsonResponse getEditorilaBoardList(Role entity);

	JsonResponse getBranchAdminCityList(Role entity);

	JsonResponse getEditorilaBoardListNew(Role entity);
}
