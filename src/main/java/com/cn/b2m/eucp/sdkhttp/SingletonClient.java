package com.cn.b2m.eucp.sdkhttp;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class SingletonClient {
	public static String softwareSerialNo = "8SDK-EMY-6699-RJUML";// 软件序列号,请通过亿美销售人员获取
	public static String key = "301772";// 序列号首次激活时自己设定
	public static String password = "298910";// 密码,请通过亿美销售人员获取

	private static Client client = null;

	private SingletonClient() {
	}

	public synchronized static Client getClient(String softwareSerialNo, String key) {
		if (client == null) {
			try {
				client = new Client(softwareSerialNo, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

	public synchronized static Client getClient() {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("conf/config");
		if (client == null) {
			try {
				client = new Client(bundle.getString("softwareSerialNo"), bundle.getString("key"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

}
