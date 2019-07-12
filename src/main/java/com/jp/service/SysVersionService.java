package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.SysFuncVersion;
import com.jp.entity.SysVersion;

public interface SysVersionService {

	JsonResponse save(SysVersion sysVersion, String[] functionids);

	JsonResponse pageQuery(PageModel<SysVersion> pageModel, SysVersion sysVersion);

	JsonResponse delete(String versionid);

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

	JsonResponse getSysVersionList();

}
