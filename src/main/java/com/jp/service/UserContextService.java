package com.jp.service;

import java.util.List;

import com.jp.entity.Branch;
import com.jp.entity.Function;
import com.jp.entity.UserManager;

public interface UserContextService {

	List<UserManager> getUserManagers(String userid, String ebid);

	List<Function> getFunctions(String familyid, String userid, String ebid);

	List<Branch> getUserBranchs(String familyid, String userid, String ebid);

	List<String> getBranchIds(String familyid, String userid, String ebid);
}
