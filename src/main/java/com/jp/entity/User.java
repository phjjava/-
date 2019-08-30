package com.jp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User implements Serializable {
	/**
	 * 用户ID
	 */
	private String userid;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 在世状态
	 */
	private Integer livestatus;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 曾用名
	 */
	private String usedname;

	/**
	 * 创建日期
	 */
	private Date createtime;

	/**
	 * 更新日期
	 */
	@JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Date updatetime;

	/**
	 * 创建人
	 */
	private String createid;

	/**
	 * 更新人
	 */
	private String updateid;

	/**
	 * 其他备注
	 */
	private String remarkelse;

	/**
	 * 分支唯一编号
	 */
	private String branchid;

	/**
	 * 父亲ID
	 */
	private String pid;

	/**
	 * 排行
	 */
	private String brotherpos;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 最后一次登录时间
	 */
	private Date logintime;

	/**
	 * ssionid
	 */
	private String sessionid;

	/**
	 * 家族唯一编号
	 */
	private String familyid;

	/**
	 * 用户状态
	 */
	private Integer status;

	/**
	 * 删除状态
	 */
	private Integer deleteflag;

	/**
	 * 头像地址
	 */
	private String imgurl;

	/**
	 * 姓名
	 */
	private String username;

	/**
	 * 身份证号
	 */
	private String idcard;

	/**
	 * 是否亲生
	 */
	private Integer isborn;

	/**
	 * 离世日期
	 */
	private String dietime;
	/**
	 * 离世日期
	 */
	private String dietimeStr;

	/**
	 * 安葬地
	 */
	private String fixplace;

	/**
	 * 是否直系
	 */
	private Integer isdirect;

	/**
	 * 第几世
	 */
	private Integer genlevel;

	/**
	 * 家族名称
	 */
	private String familyname;

	/**
	 * 姓名拼音缩写
	 */
	private String pinyinfirst;

	/**
	 * 姓名拼音首字母
	 */
	private String pinyinfull;

	/**
	 * 父亲姓名
	 */
	private String pname;

	/**
	 * 配偶ID
	 */
	private String mateid;

	/**
	 * 配偶姓名
	 */
	private String matename;

	/**
	 * 保存配偶类型字符串信息
	 */
	private String matetypeStr;

	/**
	 * 分支名称
	 */
	private String branchname;
	/**
	 * 地址全称分支名称
	 */
	private String branchAllName;
	/**
	 * 已婚未婚
	 */
	private Integer isMarry;
	/**
	 * 用户列表查询新增字段
	 */
	/**
	 * 关联类型
	 */
	private Set<JpMember> member = new HashSet<JpMember>();

	public Set<JpMember> getMember() {
		return member;
	}

	public void setMember(Set<JpMember> member) {
		this.member = member;
	}

	// 自属性
	private String searchBirthplace;
	private String searchBirthday;
	private String address;// 用户所属分支的护体地址
	private String msg;// 导入用户出错提示信息
	private Integer isnormal;// 导入用户是否正常
	private List<String> userids;// 用户id集合 批量操作用户使用
	private String excelid;
	private String pgenlevel; // 父（母）亲的世系

	// 用户信息
	private Userinfo userInfo;
	// 教育经历
	private List<Useredu> userEdu;
	// 工作经历
	private List<Userworkexp> userWorkexp;
	// 家族信息
	private List<Branch> branchList;
	// 用户相册
	private List<Useralbum> userAblumList;
	// 配偶
	private List<User> mateList;
	// 分支信息
	private Branch branch;
	// 管理员信息
	private UserManager userManager;

	// 家族信息
	private List<SysFamily> familys;
	// 登录状态信息
	private UserAppLimit userAppLimit;
	// 第三方登录的openid
	private String openid;
	// 第三方登录的类型
	private String thirdType;
	// 第三方用户密钥
	private String secret;
	// 第三方用户昵称
	private String nickname;
	// 是否需要重新设置密码 1:是0:否
	private Integer resetpwd;
	// 用户绑定的第三方列表
	private List<LoginThird> thirds;
	// 是否设置了初始密码
	private Integer isSetPwd;
	// 用户所在家族编码
	private String familycode;

	// 自写属性:分页数据
	private Long start;
	private Long count;
	private String cityname;
	private String citycode;

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public List<SysFamily> getFamilys() {
		return familys;
	}

	public void setFamilys(List<SysFamily> familys) {
		this.familys = familys;
	}

	public UserAppLimit getUserAppLimit() {
		return userAppLimit;
	}

	public void setUserAppLimit(UserAppLimit userAppLimit) {
		this.userAppLimit = userAppLimit;
	}

	public String getBranchAllName() {
		return branchAllName;
	}

	public void setBranchAllName(String branchAllName) {
		this.branchAllName = branchAllName;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getResetpwd() {
		return resetpwd;
	}

	public void setResetpwd(Integer resetpwd) {
		this.resetpwd = resetpwd;
	}

	public List<LoginThird> getThirds() {
		return thirds;
	}

	public void setThirds(List<LoginThird> thirds) {
		this.thirds = thirds;
	}

	public Integer getIsSetPwd() {
		return isSetPwd;
	}

	public void setIsSetPwd(Integer isSetPwd) {
		this.isSetPwd = isSetPwd;
	}

	public String getFamilycode() {
		return familycode;
	}

	public void setFamilycode(String familycode) {
		this.familycode = familycode;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Userinfo getUserInfo() {
		return userInfo;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setUserInfo(Userinfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<Useredu> getUserEdu() {
		return userEdu;
	}

	public void setUserEdu(List<Useredu> userEdu) {
		this.userEdu = userEdu;
	}

	public List<Userworkexp> getUserWorkexp() {
		return userWorkexp;
	}

	public void setUserWorkexp(List<Userworkexp> userWorkexp) {
		this.userWorkexp = userWorkexp;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public List<Useralbum> getUserAblumList() {
		return userAblumList;
	}

	public void setUserAblumList(List<Useralbum> userAblumList) {
		this.userAblumList = userAblumList;
	}

	public List<User> getMateList() {
		return mateList;
	}

	public void setMateList(List<User> mateList) {
		this.mateList = mateList;
	}

	public String getPgenlevel() {
		return pgenlevel;
	}

	public void setPgenlevel(String pgenlevel) {
		this.pgenlevel = pgenlevel;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 家族启用停用状态
	 */
	private Integer familystatus;

	public Integer getFamilystatus() {
		return familystatus;
	}

	public void setFamilystatus(Integer familystatus) {
		this.familystatus = familystatus;
	}

	/**
	 * 权限管理查询字段
	 */
	private String roleid;

	private String rolename;
	private String roledesc;

	private String funcgroupid;
	private Integer ismanager;
	private Integer ebtype;

	private Integer type;
	private Integer matetype;

	public Integer getEbtype() {
		return ebtype;
	}

	public void setEbtype(Integer ebtype) {
		this.ebtype = ebtype;
	}

	public String getMatetypeStr() {
		return matetypeStr;
	}

	public void setMatetypeStr(String matetypeStr) {
		this.matetypeStr = matetypeStr;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getLivestatus() {
		return livestatus;
	}

	public void setLivestatus(Integer livestatus) {
		this.livestatus = livestatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getUsedname() {
		return usedname;
	}

	public void setUsedname(String usedname) {
		this.usedname = usedname == null ? null : usedname.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid == null ? null : createid.trim();
	}

	public String getUpdateid() {
		return updateid;
	}

	public void setUpdateid(String updateid) {
		this.updateid = updateid == null ? null : updateid.trim();
	}

	public String getRemarkelse() {
		return remarkelse;
	}

	public void setRemarkelse(String remarkelse) {
		this.remarkelse = remarkelse == null ? null : remarkelse.trim();
	}

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid == null ? null : branchid.trim();
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid == null ? null : pid.trim();
	}

	public String getBrotherpos() {
		return brotherpos;
	}

	public void setBrotherpos(String brotherpos) {
		this.brotherpos = brotherpos == null ? null : brotherpos.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid == null ? null : sessionid.trim();
	}

	public String getFamilyid() {
		return familyid;
	}

	public void setFamilyid(String familyid) {
		this.familyid = familyid == null ? null : familyid.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl == null ? null : imgurl.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard == null ? null : idcard.trim();
	}

	public Integer getIsborn() {
		return isborn;
	}

	public void setIsborn(Integer isborn) {
		this.isborn = isborn;
	}

	public String getDietime() {
		return dietime;
	}

	public void setDietime(String dietime) {
		this.dietime = dietime;
	}

	public String getFixplace() {
		return fixplace;
	}

	public void setFixplace(String fixplace) {
		this.fixplace = fixplace == null ? null : fixplace.trim();
	}

	public Integer getIsdirect() {
		return isdirect;
	}

	public void setIsdirect(Integer isdirect) {
		this.isdirect = isdirect;
	}

	public Integer getGenlevel() {
		return genlevel;
	}

	public void setGenlevel(Integer genlevel) {
		this.genlevel = genlevel;
	}

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname == null ? null : familyname.trim();
	}

	public String getPinyinfirst() {
		return pinyinfirst;
	}

	public void setPinyinfirst(String pinyinfirst) {
		this.pinyinfirst = pinyinfirst == null ? null : pinyinfirst.trim();
	}

	public String getPinyinfull() {
		return pinyinfull;
	}

	public void setPinyinfull(String pinyinfull) {
		this.pinyinfull = pinyinfull == null ? null : pinyinfull.trim();
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname == null ? null : pname.trim();
	}

	public String getMateid() {
		return mateid;
	}

	public void setMateid(String mateid) {
		this.mateid = mateid == null ? null : mateid.trim();
	}

	public String getMatename() {
		return matename;
	}

	public void setMatename(String matename) {
		this.matename = matename == null ? null : matename.trim();
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname == null ? null : branchname.trim();
	}

	public String getSearchBirthplace() {
		return searchBirthplace;
	}

	public void setSearchBirthplace(String searchBirthplace) {
		this.searchBirthplace = searchBirthplace;
	}

	public String getSearchBirthday() {
		return searchBirthday;
	}

	public void setSearchBirthday(String searchBirthday) {
		this.searchBirthday = searchBirthday;
	}

	public Integer getIsMarry() {
		return isMarry;
	}

	public void setIsMarry(Integer isMarry) {
		this.isMarry = isMarry;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoledesc() {
		return roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	public String getFuncgroupid() {
		return funcgroupid;
	}

	public void setFuncgroupid(String funcgroupid) {
		this.funcgroupid = funcgroupid;
	}

	public Integer getIsmanager() {
		return ismanager;
	}

	public void setIsmanager(Integer ismanager) {
		this.ismanager = ismanager;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMatetype() {
		return matetype;
	}

	public void setMatetype(Integer matetype) {
		this.matetype = matetype;
	}

	public String getDietimeStr() {
		return dietimeStr;
	}

	public void setDietimeStr(String dietimeStr) {
		this.dietimeStr = dietimeStr;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", sex=" + sex + ", livestatus=" + livestatus + ", phone=" + phone
				+ ", usedname=" + usedname + ", createtime=" + createtime + ", updatetime=" + updatetime + ", createid="
				+ createid + ", updateid=" + updateid + ", remarkelse=" + remarkelse + ", branchid=" + branchid
				+ ", pid=" + pid + ", brotherpos=" + brotherpos + ", password=" + password + ", logintime=" + logintime
				+ ", sessionid=" + sessionid + ", familyid=" + familyid + ", status=" + status + ", deleteflag="
				+ deleteflag + ", imgurl=" + imgurl + ", username=" + username + ", idcard=" + idcard + ", isborn="
				+ isborn + ", dietime=" + dietime + ", dietimeStr=" + dietimeStr + ", fixplace=" + fixplace
				+ ", isdirect=" + isdirect + ", genlevel=" + genlevel + ", familyname=" + familyname + ", pinyinfirst="
				+ pinyinfirst + ", pinyinfull=" + pinyinfull + ", pname=" + pname + ", mateid=" + mateid + ", matename="
				+ matename + ", matetypeStr=" + matetypeStr + ", branchname=" + branchname + ", isMarry=" + isMarry
				+ ", searchBirthplace=" + searchBirthplace + ", searchBirthday=" + searchBirthday + ", address="
				+ address + ", msg=" + msg + ", isnormal=" + isnormal + ", userids=" + userids + ", excelid=" + excelid
				+ ", pgenlevel=" + pgenlevel + ", userInfo=" + userInfo + ", userEdu=" + userEdu + ", userWorkexp="
				+ userWorkexp + ", branchList=" + branchList + ", userAblumList=" + userAblumList + ", mateList="
				+ mateList + ", branch=" + branch + ", familys=" + familys + ", userAppLimit=" + userAppLimit
				+ ", openid=" + openid + ", thirdType=" + thirdType + ", secret=" + secret + ", nickname=" + nickname
				+ ", resetpwd=" + resetpwd + ", thirds=" + thirds + ", isSetPwd=" + isSetPwd + ", familycode="
				+ familycode + ", start=" + start + ", count=" + count + ", cityname=" + cityname + ", citycode="
				+ citycode + ", familystatus=" + familystatus + ", roleid=" + roleid + ", rolename=" + rolename
				+ ", roledesc=" + roledesc + ", funcgroupid=" + funcgroupid + ", ismanager=" + ismanager + ", ebtype="
				+ ebtype + ", type=" + type + ", matetype=" + matetype + "]";
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getIsnormal() {
		return isnormal;
	}

	public void setIsnormal(Integer isnormal) {
		this.isnormal = isnormal;
	}

	public List<String> getUserids() {
		return userids;
	}

	public void setUserids(List<String> userids) {
		this.userids = userids;
	}

	public String getExcelid() {
		return excelid;
	}

	public void setExcelid(String excelid) {
		this.excelid = excelid;
	}

}