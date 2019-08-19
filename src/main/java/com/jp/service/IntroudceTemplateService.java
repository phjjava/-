package com.jp.service;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.IntroudceTemplate;

public interface IntroudceTemplateService {
	/**
	 * 模板书列表
	 * @param itid
	 * @return
	 */
	IntroudceTemplate get(String itid);
	/**
	 * 模板书回显
	 * @param pageModel
	 * @param intemplate
	 * @return
	 * @throws Exception
	 */
	PageModel<IntroudceTemplate> pageQuery(PageModel<IntroudceTemplate> pageModel, IntroudceTemplate intemplate)throws Exception;
	/**
	 * 模板书新增
	 * @param intemplate
	 * @return
	 */
	Integer insert(IntroudceTemplate intemplate);
	/**
	 * 模板书修改
	 * @param intemplate
	 * @return
	 */
	Integer update(IntroudceTemplate intemplate);
	/**
	 *模板书状态更改
	 * @param intemplate
	 * @return
	 */
	Integer changeStatus(IntroudceTemplate intemplate);
	/**
	 * 模板书批量删除
	 * @param intemplateidsArray
	 */
	void batchDelete(@Param("array") String[] ids)throws Exception;
	/**
	 * api接口
	 * @param pageModel
	 * @param intemplate
	 * @return
	 */
	JsonResponse pageQueryApi(PageModel<IntroudceTemplate> pageModel, IntroudceTemplate intemplate);

}
