package com.jp.service;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.JpSynopsis;

public interface SynopsisService {
	/**
	 * 家谱介绍列表分页
	 * @param pageModel
	 * @param jpSynopsis
	 * @return
	 */
	PageModel<JpSynopsis> pageQuery(PageModel<JpSynopsis> pageModel, JpSynopsis jpSynopsis);
	/**
	 * 家谱介绍回显
	 * @param id
	 * @return
	 */
	JpSynopsis get(String id);
	/**
	 * 更改状态
	 * @param jpSynopsis
	 * @return
	 */
	Integer changeStatus(JpSynopsis jpSynopsis);
	/**
	 * 修改
	 * @param jpSynopsis
	 * @return
	 */
	Integer update(JpSynopsis jpSynopsis);
	/**
	 * 新增
	 * @param jpSynopsis
	 * @return
	 */
	Integer insert(JpSynopsis jpSynopsis);
	/**
	 * 批删
	 * @param ids
	 */
	int batchDelete(@Param("array") String[] ids);
	/**
	 * api列表
	 * @param pageModel
	 * @param jpSynopsis
	 * @return
	 */
	JsonResponse pageQueryApi(PageModel<JpSynopsis> pageModel, JpSynopsis jpSynopsis);

}
