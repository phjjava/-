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
	
	/**
	 * 通过id获取版本特权详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	SysVersionPrivilege get(String id) throws Exception;
	
	/**
	 * 新增或修改版本特权
	 * @param sysVersionP
	 * @return
	 * @throws Exception
	 */
	int saveOrUpdate(SysVersionPrivilege sysVersionP) throws Exception;
	
	/**
	 * 通过id删除版本特权详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int delete(String id) throws Exception;

}
