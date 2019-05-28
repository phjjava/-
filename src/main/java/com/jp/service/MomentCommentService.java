package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.MomentComment;

public interface MomentCommentService {

	JsonResponse createMomentComment(MomentComment entity);
	
	JsonResponse delMomentComment(MomentComment entity);
	
	JsonResponse getAllMomentComment(MomentComment entity);
}
