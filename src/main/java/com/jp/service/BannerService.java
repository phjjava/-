package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Banner;

public interface BannerService {
	JsonResponse pageQuery(PageModel<Banner> pageModel, Banner banner);

	JsonResponse get(String bannerid);

	JsonResponse changeStatus(Banner banner);

	JsonResponse save(Banner banner, String bannerurlId);

	JsonResponse batchDelete(String bannerids);

	JsonResponse selectByGoType(String goType);

	/**
	 * 以下方法用于api
	 */

	JsonResponse getBanners(Banner banner);
}
