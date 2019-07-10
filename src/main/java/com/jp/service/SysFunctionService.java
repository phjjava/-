package com.jp.service;

import java.util.List;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.SysFunction;

public interface SysFunctionService {

	PageModel<SysFunction> pageQuery(PageModel<SysFunction> pageModel, SysFunction sysFunction) throws Exception;

	JsonResponse delete(String functionid);

	JsonResponse save(SysFunction sysFunction);

	JsonResponse get(String functionid);

	JsonResponse selectListByParnetid(String parentid);

	/**
	 * @描述 根据版本id查询功能
	 * @作者 wumin
	 * @时间 2017年5月9日下午5:08:44
	 * @参数 @param pageModel
	 * @参数 @param versionid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<SysFunction>
	 */
	List<SysFunction> selectFunctionListByVersionid(String versionid) throws Exception;

}
