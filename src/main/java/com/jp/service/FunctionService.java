package com.jp.service;

import java.util.List;

import com.jp.common.PageModel;
import com.jp.entity.Function;

public interface FunctionService {

	int insert(Function function) throws Exception;

	PageModel<Function> pageQuery(PageModel<Function> pageModel, Function function) throws Exception;

	void deleteAndAll(Function function) throws Exception;

	List<Function> selectFunctionListByRoleid(String familyid, String roleid) throws Exception;

	List<Function> selectFunctionListByRoleidAndFamilyid(String familyid, String roleid) throws Exception;

	List<Function> selectFunctionListByManagerid(String familyid, String userid, String ebid);

	List<Function> selectFunctionListByEbid(String familyid, String userid, String ebid, String postid);

}