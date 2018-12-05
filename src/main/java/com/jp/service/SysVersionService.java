package com.jp.service;

import java.util.List;

import com.jp.common.PageModel;
import com.jp.entity.SysFuncVersion;
import com.jp.entity.SysVersion;

public interface SysVersionService {
	
	
	/**
	 * @描述 分页查询
	 * @作者 wumin
	 * @时间 2017年5月5日上午11:15:00
	 * @参数 @param pageModel
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel<SysVersion>
	 */
	PageModel<SysVersion> pageQuery(PageModel<SysVersion> pageModel,SysVersion sysVersion) throws Exception;

	/**
	 * @描述 新增
	 * @作者 wumin
	 * @时间 2017年5月5日下午3:45:58
	 * @参数 @param sysVersion
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int insert(SysVersion sysVersion, String [] functionids) throws Exception;

	/**
	 * @描述 删除
	 * @作者 wumin
	 * @时间 2017年5月5日下午3:46:06
	 * @参数 @param versionid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int delete(String versionid) throws Exception;
	
	/**
	 * @描述 修改
	 * @作者 wumin
	 * @时间 2017年5月5日下午3:46:34
	 * @参数 @param sysVersion
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int update(SysVersion sysVersion, String [] functionids) throws Exception;
	
	
	/**
	 * @描述 根据版本号删除与功能的关系
	 * @作者 wumin
	 * @时间 2017年5月5日下午4:14:47
	 * @参数 @param sysFuncVersion
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int deleteFuncVersionByVersionid(String versionid) throws Exception;
	
	
	/**
	 * @描述 新增版本与功能的关系
	 * @作者 wumin
	 * @时间 2017年5月5日下午4:28:19
	 * @参数 @param sysFuncVersion
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int insertFuncVersion(SysFuncVersion sysFuncVersion) throws Exception;
	
	
	/**
	 * @描述 获取版本详情
	 * @作者 wumin
	 * @时间 2017年5月8日上午10:09:05
	 * @参数 @param versionid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return SysVersion
	 */
	SysVersion get(String versionid) throws Exception;
	
	/**
	 * @描述 获取版本列表
	 * @作者 wumin
	 * @时间 2017年5月10日下午3:26:38
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<SysVersion>
	 */
	List<SysVersion> getSysVersionList() throws Exception;


}
