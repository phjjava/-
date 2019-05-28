package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.UserTag;

/**
 * 族圈用户标签表 服务类
 */
public interface UserTagService {

	JsonResponse createUserTag(UserTag entity);

	JsonResponse removeUserTag(UserTag entity);

	JsonResponse editUserTag(UserTag entity);

	JsonResponse getTagUserList(UserTag entity);

	JsonResponse getTagListByUserid(UserTag entity);

}
