package com.jp.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jp.entity.Branch;
import com.jp.entity.UserManager;
import com.jp.util.GsonUtil;

/**
 * @功能 获取登录用户信息
 * @作者 momo
 * @时间 2017年4月10日下午3:23:04
 */
public class CurrentUserContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log_ = LogManager.getLogger(LoginUserInfo.class);

	/**
	 * @描述 获取登录对象
	 * @作者 wumin
	 * @时间 2017年5月17日下午4:34:52
	 * @参数 @return
	 * @return LoginUserInfo
	 */
	public static LoginUserInfo getUserContext() {
		LoginUserInfo userContext = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession();
			userContext = (LoginUserInfo) session.getAttribute("userContext");
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZERROR-获取登录用户信息失败：]", e);
		}
		return userContext;
	}

	/**
	 * @描述 获取登录用户ID
	 * @作者 wumin
	 * @时间 2017年5月18日上午10:49:38
	 * @参数 @return
	 * @return String
	 */
	public static String getCurrentUserId() {
		LoginUserInfo userContext = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession();

			userContext = (LoginUserInfo) session.getAttribute("userContext");

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZERROR-获取登录用户信息失败：]", e);
		}
		return userContext.getUser().getUserid();
	}

	/**
	 * @描述 获取登录用户家族ID
	 * @作者 wumin
	 * @时间 2017年5月18日上午10:49:19
	 * @参数 @return
	 * @return String
	 */
	public static String getCurrentFamilyId() {

		LoginUserInfo userContext = null;
		String familyid = "";
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession();

			userContext = (LoginUserInfo) session.getAttribute("userContext");
			familyid = userContext.getUser().getFamilyid();
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZERROR-获取登录用户信息失败：]", e);
		}
		return familyid;
	}

	/**
	 * 
	 * @描述 获取登录用户家族名称
	 * @作者 jinlizhi
	 * @时间 2017年5月24日下午10:53:45
	 * @参数 @return
	 * @return String
	 */
	public static String getCurrentFamilyName() {

		LoginUserInfo userContext = null;
		String familyid = "";
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession();

			userContext = (LoginUserInfo) session.getAttribute("userContext");
			familyid = userContext.getUser().getFamilyname();
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZERROR-获取登录用户信息失败：]", e);
		}
		return familyid;
	}

	/**
	 * @描述获取登录用户名称
	 * @作者 wumin
	 * @时间 2017年5月18日上午10:49:10
	 * @参数 @return
	 * @return String
	 */
	public static String getCurrentUserName() {
		LoginUserInfo userContext = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession();

			userContext = (LoginUserInfo) session.getAttribute("userContext");

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZERROR-获取登录用户信息失败：]", e);
		}
		return userContext.getUser().getUsername();

		/*
		 * String userId = null; // LoginUserInfo loginUserInfo = getUserContext(); //
		 * if (loginUserInfo != null) { // userId =
		 * loginUserInfo.getUserInfo().getName(); // }
		 * 
		 * return userId;
		 */
	}

	public static String getCurrentBranch() {
		LoginUserInfo userContext = null;
		String branchJson = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession();

			userContext = (LoginUserInfo) session.getAttribute("userContext");
			List<Branch> branchList = userContext.getBranchList();
			branchJson = GsonUtil.GsonString(branchList);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZERROR-获取登录用户信息失败：]", e);
		}
		return branchJson;
	}

	public static List<String> getCurrentBranchIds() {
		LoginUserInfo userContext = null;
		List<String> branchids = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession();

			userContext = (LoginUserInfo) session.getAttribute("userContext");
			List<Branch> branchList = userContext.getBranchList();
			branchids = new ArrayList<String>();

			for (Branch branch : branchList) {
				branchids.add(branch.getBranchid());
			}

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZERROR-获取登录用户信息失败：]", e);
		}
		return branchids;
	}

	public static List<UserManager> getCurrentUserManager() {
		LoginUserInfo userContext = null;
		List<UserManager> managers = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession();

			userContext = (LoginUserInfo) session.getAttribute("userContext");
			managers = userContext.getUsermanagers();

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[HNFZERROR-获取登录用户信息失败：]", e);
		}
		return managers;
	}
}
