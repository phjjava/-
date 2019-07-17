package com.jp.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jp.common.JsonResponse;
import com.jp.entity.WorshipOblation;

@Scope("singleton")
@Service("OblationService")
public interface OblationService {

	JsonResponse save(WorshipOblation oblation);

	JsonResponse del(WorshipOblation oblation);

	JsonResponse getOblationById(String id);

}
