package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.dao.UsereduDao;
import com.jp.entity.Useredu;
import com.jp.service.UserEduService;

@Service
public class UserEduServiceImpl implements UserEduService {
	@Autowired
	private UsereduDao usereduDao;

	@Override
	public List<Useredu> selectByUserId(String userid) throws Exception {
		return usereduDao.selectByUserId(userid);
	}
}