package com.jp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.dao.SysUserDao;
import com.jp.entity.SysUser;
import com.jp.service.SysUserService;
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserdao;

	@Override
	public SysUser selectByLoginNameAndPassword(String loginName,String password) throws Exception {
		return sysUserdao.selectByLoginNameAndPassword(loginName,password);
	}
	
}
