package com.jp.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jp.common.PageModel;
import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationType;



@Scope("singleton")
@Service("worshipOblationService")
public interface WorshipOblationTypeService {

	PageModel<WorshipOblationType> pageQuery(PageModel<WorshipOblationType> pageModel);

	List<WorshipOblation> selectListByParnetid(String parentid);

	Integer update(WorshipOblationType oblationType);

	Integer insert(WorshipOblationType oblationType);

	int del(WorshipOblationType oblationType);

	WorshipOblationType getOblationTypeById(String id);

}
