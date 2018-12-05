package com.jp.service;

import java.util.List;

import com.jp.common.PageModel;
import com.jp.entity.Function;
import com.jp.entity.UserManager;

public interface FunctionService {
	
	int insert(Function function) throws Exception;
	
	PageModel pageQuery(PageModel pageModel,Function function) throws Exception;
	
	
	void deleteAndAll(Function function) throws Exception;
	
	List<Function> selectFunctionListByRoleid(String familyid,String roleid) throws Exception;
	
	List<Function> selectFunctionListByRoleidAndFamilyid(String familyid,String roleid) throws Exception;

	List<Function> selectFunctionListByManagerid(String familyid, String userid);

	
}