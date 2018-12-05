package com.jp.service;

import java.util.List;

import com.jp.common.PageModel;
import com.jp.entity.Branch;

public interface BranchService {

	/**
	 * @描述 分页查询分支数据
	 * @作者 wumin
	 * @时间 2017年5月1日下午11:24:11
	 * @参数 @param pageModel
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel<Branch>
	 */
	PageModel<Branch> pageQuery(PageModel<Branch> pageModel, Branch branch) throws Exception;

	/**
	 * @描述 新增分支数据
	 * @作者 wumin
	 * @时间 2017年5月1日下午11:24:22
	 * @参数 @param branch
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int insert(Branch branch) throws Exception;

	/**
	 * @描述 根据id获取分支详细信息
	 * @作者 wumin
	 * @时间 2017年5月2日下午3:07:51
	 * @参数 @param branchid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return Branch
	 */
	Branch get(String branchid) throws Exception;

	/**
	 * 
	 * @描述 初始化分支
	 * @作者 sj
	 * @时间 2017年5月2日下午5:32:40
	 * @参数 @param pageModel
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel<Branch>
	 */
	PageModel<Branch> initBranch(PageModel<Branch> pageModel, Branch branch) throws Exception;

	/**
	 * @描述 修改分支
	 * @作者 wumin
	 * @时间 2017年5月3日下午4:21:48
	 * @参数 @param branch
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int update(Branch branch) throws Exception;

	/**
	 * @描述 根据家族id userid 查询权限 无userid查询当前家族所有分支
	 * @作者 wumin
	 * @时间 2017年5月13日下午4:35:57
	 * @参数 @param familyid
	 * @参数 @param userid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<Branch>
	 */
	List<Branch> selectBranchListByFamilyAndUserid(String familyid, String userid) throws Exception;

	/**
	 * @描述 停用启用
	 * @作者 wumin
	 * @时间 2017年5月16日上午11:57:13
	 * @参数 @param branch
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int changeStatus(Branch branch) throws Exception;

	/**
	 * @描述 验证同以家族中分支名称不能相同
	 * @作者 wumin
	 * @时间 2017年5月20日下午3:00:15
	 * @参数 @param branch
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return boolean
	 */
	boolean validateBranchname(Branch branch) throws Exception;

	PageModel<Branch> selectBranchListByFamilyAndUserid(PageModel<Branch> pageModel,Branch branch) throws Exception;
}
