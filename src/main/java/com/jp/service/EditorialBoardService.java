package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.EditorialBoard;

public interface EditorialBoardService {

	/**
	 * @描述 分页查询编委会数据
	 * @作者 chenxiaobing
	 * @时间 2018年8月23日下午11:24:11
	 * @参数 @param pageModel
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel<Role>
	 */
	PageModel<EditorialBoard> pageQuery(PageModel<EditorialBoard> pageModel, EditorialBoard role) throws Exception;

	/**
	 * @描述 获取编委会详情
	 * @作者 chenxiaobing
	 * @时间 2018年8月27日下午11:24:22
	 * @参数 @param id
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	EditorialBoard getEditorialBoard(String id) throws Exception;

	int del(String id);

	JsonResponse selecteditorialBoardList(String userid);

	JsonResponse save(EditorialBoard eb);

}
