package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.MomentUserSpot;

public interface MomentUserSpotService {

	JsonResponse createUserSpot(MomentUserSpot entity);

	JsonResponse cancelUserSpot(MomentUserSpot entity);

	JsonResponse getUserSpotList(MomentUserSpot entity);
}
