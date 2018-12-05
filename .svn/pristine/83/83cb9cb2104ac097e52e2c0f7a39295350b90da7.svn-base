package com.jp.service;

import java.util.List;

import com.jp.common.PageModel;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Role;

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
	PageModel<EditorialBoard> pageQuery(PageModel<EditorialBoard> pageModel,EditorialBoard role) throws Exception;

	

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

	/**
	 * @描述 修改编委会
	 * @作者 chenxiaobing
	 * @时间 2018年8月27日下午4:21:48
	 * @参数 @param eb
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	Integer update(EditorialBoard eb) throws Exception;
	
	
	/**
	 * @描述 新增编委会
	 * @作者 chenxiaobing
	 * @时间 2018年8月27日下午5:00:56
	 * @参数 @param eb
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<Role>
	 */
	Integer insert(EditorialBoard eb) throws Exception;

	int del(String id);



	List<EditorialBoard> selecteditorialBoardList(String userid);



	



	

}
