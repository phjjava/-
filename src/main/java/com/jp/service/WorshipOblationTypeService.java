package com.jp.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.WorshipOblationType;

@Scope("singleton")
@Service("worshipOblationService")
public interface WorshipOblationTypeService {

	JsonResponse pageQuery(PageModel<WorshipOblationType> pageModel);

	JsonResponse selectListByParnetid(String parentid);

	JsonResponse save(WorshipOblationType oblationType);

	JsonResponse del(WorshipOblationType oblationType);

	JsonResponse getOblationTypeById(String id);

}
