package com.jp.service;



import com.jp.common.PageModel;
import com.jp.entity.Index;

public interface FunctionMenuService {

	PageModel<Index> pageQuery(PageModel<Index> pageModel);

	String save(Index index);

	String update(Index index);

	Index get(String id);

	String batchDelete(String[] menuArray);

	String delete(String menuid);

	
}
