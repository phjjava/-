package com.jp.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Introduce;

public interface IntroduceService {
	PageModel<Introduce> pageQuery(PageModel<Introduce> pageModel,Introduce introduce) throws Exception;
	Introduce get(String introduceid) throws Exception;
	String saveIntroduce(Introduce introduce);
	int batchDelete(@Param("array") String introduceids[]) throws Exception;
	
	/**
	* 以下方法用于api
	*/
	
	JsonResponse getIntroduceList(Introduce entity);

	JsonResponse getIntroduceDetail(Introduce entity);
	
	JsonResponse getMenuList(Introduce entity,HttpServletRequest request);
}
