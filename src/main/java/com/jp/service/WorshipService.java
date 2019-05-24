package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.Worship;

public interface WorshipService {

	JsonResponse worship(Worship worship);

	JsonResponse getWorships(Worship worship, int start, int count);

	JsonResponse getMyWorships(Worship worship, int start, int count);

	JsonResponse getWorshipDetali(Worship worship);

	JsonResponse getWorshipOblations(Worship worship);

	JsonResponse uploadPhoto(Worship worship);

	// JsonResponse getActiveUserOblations(Worship worship);

}
