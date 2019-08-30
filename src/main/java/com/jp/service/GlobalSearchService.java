package com.jp.service;

import javax.servlet.http.HttpServletRequest;

import com.jp.common.JsonResponse;
import com.jp.entity.GlobalSearch;

public interface GlobalSearchService {

	JsonResponse searchAll(GlobalSearch entity,HttpServletRequest request);

	JsonResponse searchMoreUser(GlobalSearch entity,HttpServletRequest request);

	JsonResponse searchMoreDynamics(GlobalSearch entity,HttpServletRequest request);

	JsonResponse searchMoreEvents(GlobalSearch entity,HttpServletRequest request);

	JsonResponse searchMoreUserContentVOs(GlobalSearch entity,HttpServletRequest request);

	JsonResponse searchMoreBranchAlbums(GlobalSearch entity,HttpServletRequest request);

	JsonResponse searchMoreUserContentVOsNew(GlobalSearch entity, HttpServletRequest request, String username);
	
}
