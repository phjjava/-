package com.jp.entity;

import java.util.HashMap;
import java.util.Map;

public class SysTempMap {

	private static Map<String, String> instanceMap = new HashMap<>();

	public static Map<String, String> getInstanceMap() {
		return instanceMap;
	}

}
