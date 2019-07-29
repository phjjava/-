package com.jp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jp.common.JsonResponse;
import com.jp.entity.Moment;
import com.jp.entity.SysMation;

public interface MomentService {

	JsonResponse createMoment(Moment entity, HttpServletRequest request);

	JsonResponse getMomentList(Moment entity, HttpServletRequest request);

	JsonResponse getPersonMomentList(Moment entity, HttpServletRequest request);

	JsonResponse delMoment(Moment entity, HttpServletRequest request);

}
