package com.jp.service;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jp.entity.WorshipOblation;




@Scope("singleton")
@Service("OblationService")
public interface OblationService {

	Integer insert(WorshipOblation oblation);

	Integer update(WorshipOblation oblation);

	int del(WorshipOblation oblation);

	WorshipOblation getOblationById(String id);




}
