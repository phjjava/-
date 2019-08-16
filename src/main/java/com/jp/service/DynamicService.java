package com.jp.service;

import java.util.List;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Dynamic;
import com.jp.entity.Dynamicfile;

public interface DynamicService {

	JsonResponse pageQuery(PageModel<Dynamic> pageModel, Dynamic dynamic);

	JsonResponse get(String dyid);

	JsonResponse changeStatus(Dynamic dynamic);

	JsonResponse saveDynamic(Dynamic dynamic, List<Dynamicfile> dylist);

	/**
	 * @描述 批量删除
	 * @作者 hongjun
	 * @时间 2017年5月11日上午9:00:15
	 * @参数 @param dyids
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	JsonResponse batchDelete(String dyids);

	/**
	 * 以下方法用于api
	 */
	JsonResponse getDylist(Dynamic entity);

	JsonResponse getDyDetail(Dynamic entity);

	JsonResponse getDyDetailExt(Dynamic entity);

}
