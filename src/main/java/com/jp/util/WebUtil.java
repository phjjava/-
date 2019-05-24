package com.jp.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jp.common.Result;

public class WebUtil {

	public static void getWritter(HttpServletResponse resp, Result result) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		String str = JacksonUtil.fromObjectToJson(result);
		PrintWriter out = resp.getWriter();
		out.println(str);
		out.flush();
		out.close();
	}

	public static void getWritter(String json) throws IOException {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		// 以下代码从JSON.java中拷过来的
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	public static String getBasePath(HttpServletRequest paramHttpServletRequest) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(paramHttpServletRequest.getScheme());
		localStringBuffer.append("://");
		localStringBuffer.append(paramHttpServletRequest.getServerName());
		localStringBuffer.append(":");
		localStringBuffer.append(paramHttpServletRequest.getServerPort());
		localStringBuffer.append(paramHttpServletRequest.getContextPath());
		return localStringBuffer.toString();
	}

	public static HttpSession getSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		return session;
	}

	public static ServletContext getApplication() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getServletContext();
	}

	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		return response;
	}

	public static void addCookies(Cookie[] cookies) {

		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();

		// HttpServletResponse response = ServletActionContext.getResponse();
		for (int i = 0; i < cookies.length; i++) {
			response.addCookie(cookies[i]);
		}
	}

	/**
	 * 获取请求头部消息
	 * 
	 * @param headerstr
	 *            请求头参数
	 * @return
	 */
	public static String getHeaderInfo(String headerstr) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String familyid = request.getHeader(headerstr);
		return familyid;
	}

}
