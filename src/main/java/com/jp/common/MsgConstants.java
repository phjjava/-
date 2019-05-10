package com.jp.common;

public class MsgConstants {
	public static final Result RESUL_SUCCESS = new Result(0,"成功");
	public static final Result RESUL_FAIL = new Result(1,"失败");
	public static final Result SYS_ERROR = new Result(1000,"系统错误或异常");
	
	//用户登录异常码
	public static final Result LOGIN_FAIL = new Result(1001,"登录系统失败，请稍后重试");
	public static final Result LOGIN_NOT_ADMIN = new Result(1002,"用户无管理员权限");
	public static final Result LOGIN_ABNORMAL = new Result(1003,"用户登录异常，请联系管理员");
	public static final Result LOGIN_USER_WRONG = new Result(1004,"用户帐号密码不正确");
	public static final Result LOGIN_USER_NULL = new Result(1005,"用户帐号密码不能为空");
	public static final Result LOGIN_ICODE_WRONG = new Result(1006,"验证码不正确");
	public static final Result LOGIN_USER_CHOOSEFAMILY = new Result(1007,"该用户存在多个家族，请选择");
	
}
