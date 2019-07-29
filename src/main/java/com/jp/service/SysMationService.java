package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.SysMation;

public interface SysMationService {

	List<SysMation> selectByGoType(String goType);

	Integer update(SysMation mation);

	Integer insert(SysMation mation);

	int batchDeleteAll(@Param("array") String mationids[]) throws Exception;

	

}
