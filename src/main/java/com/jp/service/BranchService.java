package com.jp.service;

import java.util.List;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Branch;

public interface BranchService {

	JsonResponse pageQuery(PageModel<Branch> pageModel, Branch branch);

	JsonResponse save(Branch branch);

	JsonResponse get(String branchid);

	JsonResponse changeStatus(Branch branch);

	JsonResponse initBranch(PageModel<Branch> pageModel, Branch branch);

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
	List<Branch> selectBranchListByFamilyAndUserid(String familyid, String userid, String ebid);

	JsonResponse validateBranchname(String branchname);

	JsonResponse checkBeginer(String beginuserid);

	/**
	* 以下方法用于api
	*/

	JsonResponse getAllBranch(Branch branch);

	JsonResponse getBranchPersons(Branch branch);

	JsonResponse getBranchVaildArea(Branch entity);

	JsonResponse getBranchVaildAreaAndCity(Branch entity);

	JsonResponse getBranchVaildXQAndBranch(Branch entity);

	JsonResponse getGenListOnlyExtMod(Branch entity);

	JsonResponse getBranchByCitycode(Branch entity);

	JsonResponse getBranchVaildCity(Branch entity);

	JsonResponse getBranchVaildXQ(Branch entity);

	JsonResponse getBranchOfXQ(Branch entity);

	JsonResponse getGenList(Branch entity);

	JsonResponse getGenListOnly(Branch entity);

	JsonResponse getGenListToTop(Branch entity);

	JsonResponse getGenListOnlyExt(Branch entity);
}
