package com.jp.common;

public class MsgConstants {
	public static final Result RESUL_SUCCESS = new Result(0, "成功");
	public static final Result RESUL_FAIL = new Result(1, "失败");
	public static final Result SESSION_FAIL = new Result(-400, "您的信息已过期，请重新登录！");
	public static final Result SYS_ERROR = new Result(1000, "系统错误或异常");
	public static final Result NO_DATA = new Result(900, "为获取到有效数据！");

	// 用户登录异常码
	public static final Result LOGIN_FAIL = new Result(1001, "登录系统失败，请稍后重试");
	public static final Result LOGIN_NOT_ADMIN = new Result(1002, "用户无管理员权限");
	public static final Result LOGIN_ABNORMAL = new Result(1003, "用户登录异常，请联系管理员");
	public static final Result LOGIN_USER_WRONG = new Result(1004, "用户帐号密码不正确");
	public static final Result LOGIN_USER_NULL = new Result(1005, "用户帐号密码不能为空");
	public static final Result LOGIN_ICODE_WRONG = new Result(1006, "验证码不正确");
	public static final Result LOGIN_USER_CHOOSEFAMILY = new Result(1007, "该用户存在多个家族，请选择");
	public static final Result LOGIN_STATUS = new Result(1008, "未登录状态首页Banner,成功");
	public static final Result LOGIN_USER_STATUS = new Result(1009, "非正常登录状态");
	public static final Result LOGIN_USER_STATUS_CHECK = new Result(1010, "您的账号正在审核中，请耐心等待");
	public static final Result LOGIN_USER_STATUS_REPULSE = new Result(1011, "您的申请已被拒绝，请重新申请");
	public static final Result LOGIN_USER_STATUS_STOP = new Result(1012, "您已被该家族停用，请重新申请一个家族或联系管理员");

	// 用户管理异常码
	public static final Result USER_SAVE_FAIL = new Result(2001, "系统异常，用户信息保存失败");
	public static final Result USER_SAVE_OUTMAX = new Result(2002, "保存失败，用户数量超过版本最大用户数量");
	public static final Result USER_SAVE_HAVEREPEAT = new Result(2003, "数据库有重复,保存失败");
	public static final Result USER_NO_BRANCH = new Result(2004, "当前分支不存在");
	public static final Result USER_NO_FILE = new Result(2005, "当前文件不存在");
	public static final Result USER_NO_IMPORT = new Result(2006, "当前没有导入任何数据");
	public static final Result USER_PHONE_REPEAT = new Result(2007, "手机号重复");
	public static final Result IMPORT_USER_NAMEREPEAT = new Result(2008, "存在重名同世系用户请特殊处理！");
	public static final Result JUMP_MESSAGE = new Result(2009, "此跳转编号暂不支持!");

	// 分支管理异常码
	public static final Result BRANCH_SAVE_OUTMAX = new Result(2002, "保存失败，分支数量超过版本最大分支数量");
	public static final Result BRANCH_VALIDATA_NAME = new Result(3001, "当前分支名称已存在");
	public static final Result BRANCH_CHECK_BEGINER = new Result(3002, "该用户已发起过其他分支");
	public static final Result BRANCH_NO_BEGINERID = new Result(3003, "分支起始人ID为空");

	// 名人录管理异常码
	public static final Result FAMOUS_NO_USERID = new Result(4001, "userid为空");

	// api异常码
	public static final Result FAMILYID_IS_NULL = new Result(5001, "家族id不能为空");
	public static final Result BRANCHID_IS_NULL = new Result(5002, "分支id不能为空");
	public static final Result AREA_IS_NULL = new Result(5003, "当前家族没有有效的地区信息");
	public static final Result CITYCODE_IS_NULL = new Result(5004, "没有当前有效的城市信息");
	public static final Result AREACODE_IS_NULL = new Result(5005, "没有当前有效的地区信息");
	public static final Result XCODE_IS_NULL = new Result(5006, "没有当前有效的县区信息");
	public static final Result BRANCHS_IS_NULL = new Result(5007, "没有有效的分支信息");
	public static final Result GENLEVEL_IS_NULL = new Result(5008, "起始人世系为空");
	public static final Result USERID_IS_NULL = new Result(5009, "用户id不能为空");
	public static final Result BEGINUSERID_IS_NULL = new Result(5010, "当前分支没有设置起始人");
	public static final Result USERS_IS_NULL = new Result(5011, "当前分支起始人不存在");
	public static final Result STATUS_IS_NULL = new Result(5012, "缺少向上查询世系参数");
	public static final Result MEAUTYPE_IS_NULL = new Result(5013, "参数meautype不能为空");
	public static final Result ALBUMID_IS_NULL = new Result(5014, "参数albumid不能为空");

	public static final Result FAMILYID_REPETITION = new Result(6001, "请不要重复申请该家族");
	public static final Result FAMILYID_REPULSE = new Result(6002, "该家族已拒绝您的申请");
	public static final Result FAMILYID_RESTRICT = new Result(6003, "一个用户最多只能关联两个家族");
	public static final Result MOMBER_RECORD = new Result(6004, "未找到对应的充值记录");

	public static final Result TO_EXAMIN = new Result(7001, "未找到对应的待审核记录");
	public static final Result TO_ALERDY = new Result(7002, "未找到对应的已审核记录");

}
