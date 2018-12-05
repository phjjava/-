package com.jp.common;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jp.entity.SysUser;



/**
 * @功能 获取登录用户信息
 * @作者 momo
 * @时间 2017年4月10日下午3:23:04
 */
public class CurrentSystemUserContext implements Serializable {

private static final Logger log_ = LogManager.getLogger(CurrentSystemUserContext.class);
	
	
	/**
	 * @描述 获取登录对象
	 * @作者 liuht
	 * @时间 2017年4月10日上午11:18:41
	 * @参数 @return
	 * @return UserContext
	 */
	public static SysUser getSystemUserContext(){
		SysUser sysUser = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			
			sysUser = (SysUser) session.getAttribute("systemUserContext");
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZERROR-获取登录用户信息失败：]", e);
		}
		return sysUser;
	}
	
	

}
