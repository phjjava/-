package com.jp.service;

import javax.servlet.http.HttpServletRequest;

import com.jp.common.JsonResponse;
import com.jp.entity.Moment;

public interface MomentService {

	JsonResponse createMoment(Moment entity,HttpServletRequest request);

	JsonResponse getMomentList(Moment entity,HttpServletRequest request);

	JsonResponse getPersonMomentList(Moment entity,HttpServletRequest request);
}
