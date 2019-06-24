package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.MomentUserStore;

public interface MomentUserStoreService {

	JsonResponse storeMoment(MomentUserStore entity);

	JsonResponse cancelStore(MomentUserStore entity);

}
