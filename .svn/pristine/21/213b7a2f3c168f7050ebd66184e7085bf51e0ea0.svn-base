package com.jp.service;

import java.util.List;

import com.jp.common.PageModel;
import com.jp.entity.SysFunction;

public interface SysFunctionService {

	/**
	 * @描述 分页查询
	 * @作者 wumin
	 * @时间 2017年5月5日上午11:15:00
	 * @参数 @param pageModel
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel<SysFunction>
	 */
	PageModel<SysFunction> pageQuery(PageModel<SysFunction> pageModel,SysFunction sysFunction) throws Exception;

	/**
	 * @描述 新增
	 * @作者 wumin
	 * @时间 2017年5月5日下午3:45:58
	 * @参数 @param sysFunction
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int insert(SysFunction sysFunction) throws Exception;

	/**
	 * @描述 删除
	 * @作者 wumin
	 * @时间 2017年5月5日下午3:46:06
	 * @参数 @param functionid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int delete(String functionid) throws Exception;
	
	/**
	 * @描述 修改
	 * @作者 wumin
	 * @时间 2017年5月5日下午3:46:34
	 * @参数 @param sysFunction
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int update(SysFunction sysFunction) throws Exception;
	
	/**
	 * @描述 单个查询
	 * @作者 wumin
	 * @时间 2017年5月5日下午4:35:52
	 * @参数 @param functionid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return SysFunction
	 */
	SysFunction get(String functionid) throws Exception;
	
	
	/**
	 * @描述 根据父节点查询子菜单集合
	 * @作者 wumin
	 * @时间 2017年5月8日下午5:37:48
	 * @参数 @param prantid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<SysFunction>
	 */
	List<SysFunction> selectListByParnetid(String parentid) throws Exception;
	
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
