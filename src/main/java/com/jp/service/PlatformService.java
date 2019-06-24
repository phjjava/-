package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Platform;

public interface PlatformService {
	/**
	 * @描述 版本管理 安卓 或 ios 列表
	 * @作者 sj
	 * @时间 2017年11月27日下午5:49:10
	 * @参数 @param pageModel
	 * @参数 @param platform
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel
	 */
	PageModel selectPlatformList(PageModel pageModel, Platform platform) throws Exception;

	/**
	 * 删除版本
	 * 
	 * @描述 TODO
	 * @作者 sj
	 * @时间 2017年11月27日下午9:27:41
	 * @参数 @param id
	 * @参数 @return
	 * @return String
	 */
	String deleteVersion(Integer id);

	void save(Platform platform);

	void update(Platform platform);

	Platform selectone(String id);

	void isOpen(Integer id, Integer isUsed);

	void closeAllVersion(Integer fileType);

	JsonResponse getPlatform(Integer fileType);

	JsonResponse getCurrentTime();
}