package com.jp.service;

import java.util.List;

import com.jp.entity.Userworkexp;

public interface UserWorkService {
	List<Userworkexp> selectByUserId(String userid) throws Exception;
}