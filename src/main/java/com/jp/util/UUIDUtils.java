package com.jp.util;

import java.util.UUID;

public class UUIDUtils {
	/**
	 * @描述 获得一个UUID
	 * @作者 momo
	 * @时间 2017年3月28日下午5:38:50
	 * @参数 @return
	 * @return String
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	/**
	 * 
	 * @描述 获得指定数目的UUID
	 * @作者 momo
	 * @时间 2017年3月28日下午5:39:03
	 * @参数 @param number 需要获得的UUID数量
	 * @参数 @return UUID数组
	 * @return String[]
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

	public static void main(String[] args) {
		String[] ss = getUUID(10);
		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i]);
		}
	}
}
