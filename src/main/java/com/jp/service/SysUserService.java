package com.jp.service;

import com.jp.entity.SysUser;


public interface SysUserService {
	SysUser selectByLoginNameAndPassword(String loginName,String password) throws Exception;
}
