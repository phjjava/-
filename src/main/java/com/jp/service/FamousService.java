package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Usercontent;

public interface FamousService {

	public PageModel<Usercontent> selectContentList(PageModel<Usercontent> pageModel, Usercontent usercontent);

	Usercontent get(String noticeid);

	int batchDelete(String[] idArray);

	JsonResponse editOrAdd(Usercontent usercontent, String id);
}
