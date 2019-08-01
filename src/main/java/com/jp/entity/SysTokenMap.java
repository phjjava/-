package com.jp.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例map用来临时储存token
 * @author phj
 *
 */
public class SysTokenMap {

	private static Map<String, String> instanceMap = new HashMap<>();

	public static Map<String, String> getInstanceMap() {
		return instanceMap;
	}

}
