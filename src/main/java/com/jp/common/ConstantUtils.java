package com.jp.common;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @功能 枚举＆常量工具类
 * @作者 momo
 * @时间 2017年4月11日上午10:54:41
 */
public class ConstantUtils implements Serializable {

	public static ResourceBundle res = AccessController.doPrivileged(new PrivilegedAction<ResourceBundle>() {
		public ResourceBundle run() {
			return ResourceBundle.getBundle("conf/config");
		}
	});
	/* 获取图片附件上传路径 */
	public static final String JIAPU_UPLOAD_URL = res.getString("jiapu.upload.url");
	/*public static final String JIAPU_DOWNLOAD_URL = res.getString("jiapu.download.url");
	public static final String JIAPU_SHOW_URL = res.getString("jiapu.show.url");
	public static final String JIAPU_AVATAR_UPLOAD_URL = res.getString("jiapu.avatar.upload.url");
	public static final String JIAPU_AVATAR_DOWNLOAD_URL = res.getString("jiapu.avatar.download.url");
	public static final String JIAPU_IP = res.getString("jiapu.ip");
	*/
	// avatar
	/**
	 * 删除状态
	 */
	public static Integer DELETE_TRUE = 1;// 删除标记，已删除
	public static Integer DELETE_FALSE = 0;// 删除标记，未删除

	/**
	 * 状态
	 */
	public static Integer RESULT_SUCCESS = 1;// 成功
	public static Integer RESULT_FAIL = 0;// 失败

	/**
	 * 名人录轮播图显示标记
	 */
	public static Integer ISSEE_DEFAULT = 1;// 不可见
	public static Integer ISSEE_SHOW = 0;// 可见

	public static Integer USER_STATUS_OK = 0; // 用户状态标记，正式
	public static Integer USER_STATUS_WAIT = 1; // 用户状态标记，审核中
	public static Integer USER_STATUS_NO = 2; // 用户状态标记，审核未通过

	/**
	 * 默认下拉框民族选项字符串
	 */
	public static String DEFAULT_NATION_STR = "[{\"value\":\"-1\",\"text\":\"请选择\"},{\"value\":\"汉族\",\"text\":\"汉族\"},{\"value\":\"蒙古族\",\"text\":\"蒙古族\"},{\"value\":\"回族\",\"text\":\"回族\"},\n"
			+ "	{\"value\":\"维吾尔族\",\"text\":\"维吾尔族\"},{\"value\":\"苗族\",\"text\":\"苗族\"},{\"value\":\"壮族\",\"text\":\"壮族\"},\n"
			+ "	{\"value\":\"朝鲜族\",\"text\":\"朝鲜族\"},{\"value\":\"满族\",\"text\":\"满族\"},{\"value\":\"鄂温克族\",\"text\":\"鄂温克族\"},\n"
			+ "	{\"value\":\"鄂伦春族\",\"text\":\"鄂伦春族\"},{\"value\":\"达斡尔族\",\"text\":\"达斡尔族\"},{\"value\":\"俄罗斯族\",\"text\":\"俄罗斯族\"},\n"
			+ "	{\"value\":\"其他\",\"text\":\"其他\"}\n" + "	]";
	public static String DEFAULT_NATION_OTHER = "其他";// 其他民族默认字符串

	/**
	 * 家族版本特权code
	 */
	public static String VERSION_BRANCH = "branch"; // 分支特权code
	public static String VERSION_OBLATION = "oblation"; // 祭祖祭品特权code
	public static String VERSION_USERCOUNT = "userCount"; // 家族容纳人数特权code
	public static String VERSION_ALBUMSPACE = "albumSpace"; // 家族相册空间特权code
	public static String VERSION_UNLIMITED = "unlimited"; // 不限

	/**
	 * 请求头信息
	 */
	public static String HEADER_SESSIONID = "sessionid";// sessionid
	public static String HEADER_USERID = "userid";// 用户id
	public static String HEADER_FAMILYID = "familyid";// 家族id
	public static String HEADER_EBID = "ebid";// 编委会id
	public static String HEADER_LOGINTYPE = "logintype";// 登录类型
	public static String HEADER_PHONETYPE = "phonetype";// 手机型号
	public static String HEADER_VERSION = "version";// 版本号
	public static String HEADER_DEVICEID = "deviceid";// 版本号
	public static String HEADER_INTERNETTYPE = "internetType";// 运营商

	// 族圈状态 展示类型ALL,PRIIVATE,SELECT
	public static String MOMENT_SELECT = "SELECT"; // ADMIN_IMPORT后台管理导入
	public static String MOMENT_ALL = "ALL"; //所有人可见
	public static String MOMENT_PRIIVATE = "PRIIVATE"; //仅自己可见
	public static String MOMENT_ISIBLE = "VISIBLE"; //谁可见
	public static String MOMENT_INVISIBLE = "INVISIBLE";//谁不可见

	public static Map<String, String> SESSION_MAP = new HashMap<String, String>();// 用来保存用户的sessionid,key为用户的id

	/**
	 * @功能 性别
	 * @作者 wumin
	 * @时间 2017年4月27日下午12:01:27
	 */
	public enum UserSex {

		USER_SEX_1(1, "男"), USER_SEX_2(2, "女");

		private Integer key;
		private String value;

		private UserSex(Integer key, String value) {
			this.key = key;
			this.value = value;
		}

		static Map<Integer, String> statusMap = new HashMap<Integer, String>();
		static {
			for (UserSex status : UserSex.values()) {
				statusMap.put(status.getKey(), status.getValue());
			}
		}

		public static String getUserSex(Integer key) {
			return statusMap.get(key);
		}

		public Integer getKey() {
			return key;
		}

		public void setKey(Integer key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * @功能 性别
	 * @作者 wumin
	 * @时间 2017年4月27日下午12:01:27
	 */
	public enum UserStatus {

		USER_STATUS_1(1, "男"), USER_STATUS_2(2, "女");

		private Integer key;
		private String value;

		private UserStatus(Integer key, String value) {
			this.key = key;
			this.value = value;
		}

		static Map<Integer, String> statusMap = new HashMap<Integer, String>();
		static {
			for (UserSex status : UserSex.values()) {
				statusMap.put(status.getKey(), status.getValue());
			}
		}

		public static String getUserStatus(Integer key) {
			return statusMap.get(key);
		}

		public Integer getKey() {
			return key;
		}

		public void setKey(Integer key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

}
