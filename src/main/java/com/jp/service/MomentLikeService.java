package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.MomentLike;

public interface MomentLikeService {

	JsonResponse createMomentLike(MomentLike entity);

	JsonResponse cancelMomentLike(MomentLike entity);

	JsonResponse getMomentLikeInfo(String momentId);

}
