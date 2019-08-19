package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.common.PageModel;
import com.jp.entity.IntroudceTemplate;
import com.jp.entity.IntroudceTemplateDetail;

public interface IntroudceTemplateDetailService {
	/**
	 * 模板书分支回显
	 * @param pageModel
	 * @param deleteflag 
	 * @param templatename 
	 * @param intemplate
	 * @return
	 * @throws Exception
	 */
	IntroudceTemplateDetail get(String itid);
	/**
	 * 模板书分支列表
	 * @param itid
	 * @return
	 */
	PageModel<IntroudceTemplateDetail> pageQuery(PageModel<IntroudceTemplateDetail> pageModel, IntroudceTemplateDetail intemplateDetail,String title, String templatename, Integer deleteflag)throws Exception;
	/**
	 * 模板书分支新增
	 * @param intemplate
	 * @return
	 */
	Integer insert(IntroudceTemplateDetail intemplateDetail);
	/**
	 * 模板书分支修改
	 * @param intemplate
	 * @return
	 */
	Integer update(IntroudceTemplateDetail intemplateDetail);
	/**
	 *模板书分支状态更改
	 * @param intemplate
	 * @return
	 */
	Integer changeStatus(IntroudceTemplateDetail intemplateDetail);
	/**
	 * 模板书分支批量删除
	 * @param intemplateidsArray
	 */
	void batchDelete(@Param("array") String[] ids)throws Exception;
	/**
	 * 模板书下拉框
	 * @return
	 */
	List<IntroudceTemplate> selecttypelist();
	
	int selectCount();
	/**
	 * api模板书章节目录
	 * @param id
	 * @return
	 */
	List<IntroudceTemplateDetail> apiFindList(String id);
	/**
	 * 对应章节详情
	 * @param id
	 * @return
	 */
	IntroudceTemplateDetail apiFindOne(String id);

}
