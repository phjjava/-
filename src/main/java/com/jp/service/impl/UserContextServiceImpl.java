package com.jp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.entity.Branch;
import com.jp.entity.Function;
import com.jp.entity.UserManager;
import com.jp.service.BranchService;
import com.jp.service.FunctionService;
import com.jp.service.UserContextService;
import com.jp.service.UserManagerService;

@Service
public class UserContextServiceImpl implements UserContextService {

	private final Logger log_ = LogManager.getLogger(UserContextServiceImpl.class);
	@Autowired
	private FunctionService functionService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private UserManagerService userManagerService;

	/**
	 * 获取管理员列表
	 */
	@Override
	public List<UserManager> getUserManagers(String userid, String ebid) {
		return userManagerService.selectManagerByUserid(userid, ebid);
	}

	/**
	 * 获取权限列表
	 */
	@Override
	public List<Function> getFunctions(String familyid, String userid, String ebid) {

		return functionService.selectFunctionListByManagerid(familyid, userid, ebid);
	}

	/**
	 * 获取分支列表
	 */
	@Override
	public List<Branch> getUserBranchs(String familyid, String userid, String ebid) {
		return branchService.selectBranchListByFamilyAndUserid(familyid, userid, ebid);
	}

	/**
	 * 获取分支id列表
	 */
	@Override
	public List<String> getBranchIds(String familyid, String userid, String ebid) {
		List<Branch> branchList = branchService.selectBranchListByFamilyAndUserid(familyid, userid, ebid);
		List<String> branchids = new ArrayList<>();
		for (Branch branch : branchList) {
			branchids.add(branch.getBranchid());
		}
		return branchids;
	}

}
