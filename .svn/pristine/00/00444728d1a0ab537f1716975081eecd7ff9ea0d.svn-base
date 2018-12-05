package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.dao.UserworkexpDao;
import com.jp.entity.Userworkexp;
import com.jp.service.UserWorkService;

@Service
public class UserWorkServiceImpl implements UserWorkService {
	@Autowired
	private UserworkexpDao userworkDao;

	@Override
	public List<Userworkexp> selectByUserId(String userid) throws Exception {
		return userworkDao.selectByUserId(userid);
	}
}