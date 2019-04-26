package com.jp.service;

import com.jp.common.PageModel;
import com.jp.entity.SysVersionPrivilege;

public interface SysVersionPrivilegeService {
	
	/**
	 * 版本特权分页查询
	 * @author zhangnn
	 * @param pageModel
	 * @param sysVersion
	 * @return
	 * @throws Exception
	 */
	PageModel<SysVersionPrivilege> pageQuery(PageModel<SysVersionPrivilege> pageModel,SysVersionPrivilege sysVersionPrivilege) throws Exception;

}
