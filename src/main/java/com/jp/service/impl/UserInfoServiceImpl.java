package com.jp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.dao.UserinfoMapper;
import com.jp.entity.Userinfo;
import com.jp.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserinfoMapper userInfoDao;
	@Override
	public Userinfo selectByPrimaryKey(String userid) throws Exception {
		return userInfoDao.selectByPrimaryKey(userid);
	}
}