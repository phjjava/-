package com.jp.advice;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jp.dao.UserDao;
import com.jp.entity.User;
import com.jp.entity.UserQuery;

public class LoginAroundAdvice implements MethodInterceptor {

	private final Logger log_ = LogManager.getLogger(LoginAroundAdvice.class);
	@Autowired
	private UserDao userDao;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Object returnValue = invocation.proceed();
		// 后置通知
		Object[] args = invocation.getArguments();
		User user = (User) args[1];
		UserQuery uq = new UserQuery();
		uq.or().andSessionidEqualTo(user.getSessionid());
		List<User> list = userDao.selectByExample(uq);
		if (list.size() > 0) {
			user.setUserid(list.get(0).getUserid());
			int status = userDao.updateByPrimaryKeySelective(user);
			System.out.println("**********后置通知*********=" + status);
		}

		/*
			Map<String, String> userparams = new HashMap<String, String>();
			String family = ("".equals(user.getFamilyid()) || user.getFamilyid() == null) ? null : user.getFamilyid();
			userparams.put("familyid", family);
			userparams.put("phone", user.getPhone());
			//此时的phone已经为空了，所以查不到
			List<User> userList = userDao.selectByFamilyId(userparams);
			for (User user2 : userList) {
				user2.setPhone(user.getPhone());
				System.out.println("*************=" + user2);
				int status = userDao.updateByPrimaryKeySelective(user2);
				System.out.println("***************=" + status);
			}
		*/
		return returnValue;

	}

}
