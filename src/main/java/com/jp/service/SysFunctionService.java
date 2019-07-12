package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.SysFunction;

public interface SysFunctionService {

	JsonResponse pageQuery(PageModel<SysFunction> pageModel, SysFunction sysFunction);

	JsonResponse delete(String functionid);

	JsonResponse save(SysFunction sysFunction);

	JsonResponse get(String functionid);

	JsonResponse selectListByParnetid(String parentid);

	JsonResponse selectFunctionListByVersionid(String versionid);

}
