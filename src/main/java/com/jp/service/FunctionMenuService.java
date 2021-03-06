package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Index;

public interface FunctionMenuService {

	JsonResponse pageQuery(PageModel<Index> pageModel);

	JsonResponse save(Index index);

	JsonResponse get(String id);

	JsonResponse batchDelete(String menuids);

	JsonResponse delete(String menuid);

	JsonResponse changeStatus(String menuid, Integer status);

}
