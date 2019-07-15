package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Platform;

public interface PlatformService {

	JsonResponse selectPlatformList(PageModel<Platform> pageModel, Platform platform);

	JsonResponse deleteVersion(Integer id);

	int save(Platform platform);

	int update(Platform platform);

	JsonResponse selectone(Integer id);

	JsonResponse isOpen(Integer id);

	void closeAllVersion(Integer fileType);

	JsonResponse getPlatform(Integer fileType);

	JsonResponse getCurrentTime();
}