package com.jp.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jp.common.CurrentSystemUserContext;
import com.jp.dao.UserDao;
import com.jp.entity.SysUser;
import com.jp.entity.User;
import com.jp.util.DateUtils;

public class LoginInterceptor implements HandlerInterceptor {
	private final Logger log_ = LoggerFactory.getLogger(LoginInterceptor.class);

	@Resource
	private UserDao userDao;

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log_.info("执行顺序: 1、preHandle请求前");
		String userid = request.getHeader("userid");
		String sessionid = request.getHeader("sessionid");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		String servletPath = request.getServletPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/";
		log_.info("requestUri：" + requestUri);
		log_.info("contextPath：" + contextPath);
		log_.info("basePath:" + basePath);
		log_.info("url：" + url);
		String dbsessionid = null;
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));// 支持跨域请求
		response.setHeader("Access-Control-Allow-Credentials", "true"); // 支持cookie跨域
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,userid,sessionid");
		response.addHeader("Access-Control-Max-Age", "1800");
		if (servletPath.startsWith("/system/")) {//平台系统拦截  （请求地址以system开头的是平台）

			SysUser systemUserContext = CurrentSystemUserContext.getSystemUserContext();
			if (systemUserContext == null) {
				log_.info("非法请求：请先登录！");
				//		request.getRequestDispatcher("/jsp/system/login.jsp").forward(request, response);
				return false;
			} else
				return true;

		}
		if (userid == null || userid.equals("")) {
			return false;
		} else {
			// 获取数据库中的对应用户的sessionid
			User loginUser = userDao.selectByPrimaryKey(userid);
			dbsessionid = loginUser.getSessionid();
			// 查询用户的登陆间隔时间	
			Date lastLogintime = loginUser.getLogintime();
			long timeDifferenceOfMin = DateUtils.timeDifferenceOfMin(new Date(), lastLogintime);
			if (timeDifferenceOfMin / (60 * 24) >= 30) {
				log_.info("sessionid过期！");
				return false;
			}
		}
		// 校验session是否失效
		if (dbsessionid == null) {
			log_.info("数据库sessionid为空！");
			return false;
		} else {
			if (dbsessionid != null && !dbsessionid.equals(sessionid)) {
				log_.info("sessionid不一致！");
				return false; // sessionid失效
			} else {
				return true;
			}
		}

		/*if (servletPath.startsWith("/system/")) {//平台系统拦截  （请求地址以system开头的是平台）
		
			SysUser systemUserContext = CurrentSystemUserContext.getSystemUserContext();
			if (systemUserContext == null) {
				log_.info("非法请求：跳转到login页面！");
				request.getRequestDispatcher("/jsp/system/login.jsp").forward(request, response);
		//				response.sendRedirect("/jsp/system/login.jsp");
				return false;
			} else
				return true;
		
		} else {
			//家族系统拦截
			LoginUserInfo userContext = CurrentUserContext.getUserContext();
			if (userContext == null) {
				log_.info("非法请求：跳转到login页面！");
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				return false;
			} else
				return true;
		}*/

	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// log_.info("执行顺序: 2、postHandle请求中");

	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// log_.info("==============执行顺序: 3、afterCompletion================");
	}

	private void init(HttpServletRequest request) {

	}
}
