package com.jp.util;

import com.cn.b2m.eucp.sdkhttp.SingletonClient;

/*
 * 亿美短信发送
 * */
public class EmaySend {
	/*
	 * public static String softwareSerialNo = "8SDK-EMY-6699-RJUML";//
	 * 软件序列号,请通过亿美销售人员获取 public static String key = "301772";// 序列号首次激活时自己设定
	 * public static String password = "298910";// 密码,请通过亿美销售人员获取
	 */

	/**
	 * 发短信的另外一种方法，需要配置config.properties flag=0 发送成功 其他失败
	 */
	public static int sendSMS(String mobile, String smsContent) {
		String[] phsStrings = { mobile };
		int flag = -1;
		try {
            flag = SingletonClient.getClient().sendSMS(phsStrings, "【家谱】" + smsContent, "", 3);// 带扩展码
			System.out.println("testSendSMS=====" + flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

    // public static boolean sendSMSNew(String mobile, String smsContent) {
    // boolean status=false;
    // try {
    // Client client = null;
    // client = new Client("8SDK-EMY-6699-RJUML", "298910");
    // int i = client.sendSMS(new String[] { mobile },
    // smsContent, "", 3);// 带扩展码
    // System.out.println("testSendSMS=====" + i);
    // if(i==0){
    // status=true;
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return status;
    // }


	// 发短信的一种方式
	public static void main(String[] args) {
		try {
            // Client client = null;
            // client = new Client("8SDK-EMY-6699-RJUML", "298910");
            // int i = client.sendSMS(new String[] {"18947755565"}, "【宝方】您好，欢迎注册保方科技，你的验证码是123", "",
            // 3);// 带扩展码
            // System.out.println("testSendSMS=====" + i);

            EmaySend.sendSMS("18947755565", "fff");
			// 另一种
			// EmaySend.sendSMS("18947755565", "haha ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
