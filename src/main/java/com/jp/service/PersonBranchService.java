package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.PersonBranch;

public interface PersonBranchService {

	JsonResponse getPersonBranchData(PersonBranch entity);

	JsonResponse getPersonBranchDataByBranch(PersonBranch entity);
}
