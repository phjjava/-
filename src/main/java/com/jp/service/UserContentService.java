package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.Usercontent;

public interface UserContentService {

	JsonResponse getUserContentList(Usercontent entity, int start, int count);

	JsonResponse getPersonContent(Usercontent entity);

	JsonResponse publicContent(Usercontent userContent);

	JsonResponse getHomePageContents(Usercontent entity, int count);

}
