package com.jp.service;

import org.apache.ibatis.annotations.Param;

import com.jp.common.PageModel;
import com.jp.entity.Introduce;
import com.jp.util.Result;

public interface IntroduceService {
	PageModel<Introduce> pageQuery(PageModel<Introduce> pageModel,Introduce introduce) throws Exception;
	Introduce get(String introduceid) throws Exception;
	String saveIntroduce(Introduce introduce);
	int batchDelete(@Param("array") String introduceids[]) throws Exception;
}
