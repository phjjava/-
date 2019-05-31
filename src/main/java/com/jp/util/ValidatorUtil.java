package com.jp.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字段验证工具
 * @author zx
 *
 */
public class ValidatorUtil {
	
	public static void main(String[] args) {
		System.out.println(isMobile("19913147235"));
	}
	
	/**
	 * 判断是否为浮点数或者整数
	 * @param str
	 * @return true Or false
	 */
	public static boolean isNumeric(String str){
          Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
          Matcher isNum = pattern.matcher(str);
          if( !isNum.matches() ){
                return false;
          }
          return true;
    }
	
	/**
	 * 判断是否为正确的邮件格式
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmail(String str){
		if(isEmpty(str))
			return false;
		return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
	}
	/**
	 * 判断密码强度
	 * @param str
	 * @return
	 */
	public static boolean isStrongPwd(String str){
		if(isEmpty(str))
			return false;
		return str.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,14}$");
	}
	
	/**
	 * 判断字符串是否为合法手机号 11位 13 14 15 18开头
	 * @param str
	 * @return boolean
	 * "^(13|14|15|18)\\d{9}$"
	 */
	public static boolean isMobile(String str){
		if(isEmpty(str))
			return false;
		return str.matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$");
	}
	
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		try{
			Integer.parseInt(str);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
		
	/**
	 * 判断字符串是否为非空(包含null与"")
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(str == null || "".equals(str))
			return false;
		return true;
	}
	
	/**
	 * 判断字符串是否为非空(包含null与"","    ")
	 * @param str
	 * @return
	 */
	public static boolean isNotEmptyIgnoreBlank(String str){
		if(str == null || "".equals(str) || "".equals(str.trim()))
			return false;
		return true;
	}
	
	/**
	 * 判断字符串是否为空(包含null与"")
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str))
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是否为空(包含null与"","    ")
	 * @param str
	 * @return
	 */
	public static boolean isEmptyIgnoreBlank(String str){
		if(str == null || "".equals(str) || "".equals(str.trim()))
			return true;
		return false;
	}
	
	
	/**
	 * 判断list是否为空
	 * @param str
	 * @return
	 */
	public static boolean listIsEmpty(List<?> list){
		if(list==null || list.size()<1)
			return true;
		return false;
	}
	
	//禁止实例化
	private ValidatorUtil(){} 
}
