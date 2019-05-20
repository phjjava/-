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
	
	//用户管理异常码
	public static final Result USER_SAVE_FAIL = new Result(2001,"系统异常，用户信息保存失败");
	public static final Result USER_SAVE_OUTMAX = new Result(2002,"保存失败，用户数量超过版本最大用户数量");
	public static final Result USER_SAVE_HAVEREPEAT = new Result(2003,"数据库有重复,保存失败");
	public static final Result USER_NO_BRANCH = new Result(2004,"当前分支不存在");
	public static final Result USER_NO_FILE = new Result(2005,"当前文件不存在");
	public static final Result USER_NO_IMPORT = new Result(2006,"当前没有导入任何数据");
	public static final Result USER_PHONE_REPEAT = new Result(2007,"手机号重复");
	
	//分支管理异常码
	public static final Result BRANCH_VALIDATA_NAME = new Result(3001,"当前分支名称已存在");
	public static final Result BRANCH_CHECK_BEGINER = new Result(3002,"该用户已发起过其他分支");
	public static final Result BRANCH_NO_BEGINERID = new Result(3003,"分支起始人ID为空");
	
	//名人录管理异常码
	public static final Result FAMOUS_NO_USERID = new Result(4001,"userid为空");
	
	
	
}
