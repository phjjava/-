package com.jp.service;

import javax.servlet.http.HttpServletRequest;

import com.jp.common.JsonResponse;

public interface SysUserService {
	JsonResponse selectByLoginNameAndPassword(HttpServletRequest request, String loginName, String password);
}
