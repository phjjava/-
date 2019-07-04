package com.jp.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.SysUserDao;
import com.jp.entity.SysUser;
import com.jp.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	private final Logger log_ = LogManager.getLogger(SysUserServiceImpl.class);

	@Autowired
	private SysUserDao sysUserdao;

	@Override
	public JsonResponse selectByLoginNameAndPassword(HttpServletRequest request, String loginName, String password) {
		Result result = null;
		JsonResponse res = null;
		try {
			SysUser sysUser = sysUserdao.selectByLoginNameAndPassword(loginName, password);
			if (sysUser != null) {
				request.getSession().setAttribute("systemUserContext", sysUser);
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(sysUser);
				return res;
			}
		} catch (Exception e) {
			log_.error("[selectByLoginNameAndPassword方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		result.setMsg("用户名或密码不正确！");
		res = new JsonResponse(result);
		return res;
	}

}
