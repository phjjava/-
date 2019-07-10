package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.SysVersionPrivilege;

public interface SysVersionPrivilegeService {

	JsonResponse pageQuery(PageModel<SysVersionPrivilege> pageModel, SysVersionPrivilege sysVersionPrivilege);

	JsonResponse get(String id);

	JsonResponse saveOrUpdate(SysVersionPrivilege sysVersionP);

	JsonResponse delete(String id);

}
