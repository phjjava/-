package com.jp.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.controller.UserController;
import com.jp.dao.BranchDao;
import com.jp.dao.BranchalbumMapper;
import com.jp.dao.BranchphotoMapper;
import com.jp.dao.DynamicMapper;
import com.jp.dao.LoginThirdMapper;
import com.jp.dao.NoticeMapper;
import com.jp.dao.NoticetopDao;
import com.jp.dao.SysFamilyDao;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.dao.UserDao;
import com.jp.dao.UserImportMapper;
import com.jp.dao.UserInfoImportMapper;
import com.jp.dao.UserManagerMapper;
import com.jp.dao.UseralbumDao;
import com.jp.dao.UsercodeDao;
import com.jp.dao.UsereduDao;
import com.jp.dao.UserinfoMapper;
import com.jp.dao.UsermatesDao;
import com.jp.dao.UserphotoDao;
import com.jp.dao.UserworkexpDao;
import com.jp.entity.AddressBook;
import com.jp.entity.Branch;
import com.jp.entity.BranchKey;
import com.jp.entity.BranchQuery;
import com.jp.entity.BranchValidArea;
import com.jp.entity.Branchalbum;
import com.jp.entity.Branchphoto;
import com.jp.entity.BranchphotoExample;
import com.jp.entity.Dynamic;
import com.jp.entity.DynamicVO;
import com.jp.entity.EditorialBoard;
import com.jp.entity.LoginThird;
import com.jp.entity.LoginThirdExample;
import com.jp.entity.Notice;
import com.jp.entity.NoticeVO;
import com.jp.entity.Noticetop;
import com.jp.entity.NoticetopQuery;
import com.jp.entity.OnLineUser;
import com.jp.entity.SearchComplex;
import com.jp.entity.SysFamily;
import com.jp.entity.SysVersionPrivilege;
import com.jp.entity.User;
import com.jp.entity.UserAppLimit;
import com.jp.entity.UserClildInfo;
import com.jp.entity.UserDetail;
import com.jp.entity.UserImportExample;
import com.jp.entity.UserLimitVO;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.entity.UserQuery;
import com.jp.entity.UserVO;
import com.jp.entity.Useralbum;
import com.jp.entity.Usercode;
import com.jp.entity.UsercodeQuery;
import com.jp.entity.Useredu;
import com.jp.entity.UsereduQuery;
import com.jp.entity.UsereduQuery.Criteria;
import com.jp.entity.Userinfo;
import com.jp.entity.Usermates;
import com.jp.entity.Userphoto;
import com.jp.entity.Userworkexp;
import com.jp.entity.UserworkexpQuery;
import com.jp.service.DynamicService;
import com.jp.service.NoticeService;
import com.jp.service.UserContextService;
import com.jp.service.UserService;
import com.jp.util.CalendarUtil;
import com.jp.util.DateUtil;
import com.jp.util.DateUtils;
import com.jp.util.EmaySend;
import com.jp.util.ExcelUtil;
import com.jp.util.GsonUtil;
import com.jp.util.JedisUtil;
import com.jp.util.MD5Util;
import com.jp.util.PinyinUtil;
//import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.ValidatorUtil;
import com.jp.util.WebUtil;
import com.jp.util.ZodiacUtil;

@Service
public class UserServiceImpl implements UserService {
	private final Logger log_ = LogManager.getLogger(UserController.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserinfoMapper userInfoDao;
	@Autowired
	private UserImportMapper userImportMapper;
	@Autowired
	private UserInfoImportMapper userInfoImportMapper;
	@Autowired
	private UsereduDao userEduDao;
	@Autowired
	private UserworkexpDao userworkDao;
	@Autowired
	private UsermatesDao userMatesDao;
	@Autowired
	private UseralbumDao userAlbumDao;
	@Autowired
	private UserphotoDao userPtotoDao;
	@Autowired
	private SysFamilyDao sysFamilyDao;
	@Autowired
	private LoginThirdMapper loginThirdMapper;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private SysVersionPrivilegeMapper sysVersionPrivilegeMapper;
	@Autowired
	private UsercodeDao usercodeDao;
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticetopDao noticeTopMapper;
	@Autowired
	private DynamicMapper dynamicMapper;
	@Autowired
	private DynamicService dynamicService;
	@Autowired
	private BranchalbumMapper branchAlbumMapper;
	@Autowired
	private BranchphotoMapper branchPhotoMapper;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@Autowired
	private UserContextService userContextService;

	// 导入用户时重复的用户
	private ArrayList<String> userStringList;

	// 导入用户时重复的用户
	private ArrayList<String> userMateStringList;

	@Override
	public int insert(User user) throws Exception {
		return 0;
	}

	@Override
	public PageModel<User> pageQuery(PageModel<User> pageModel, User user) throws Exception {

		return null;
	}

	@Override
	public void deleteAndAll(User user) throws Exception {

	}

	/**
	 * 递归查询父子关系
	 * @param pUsers
	 * @param pid
	 * @return
	 */
	public List<User> getPUserList(List<User> pUsers, String pid) {
		User pUser = userDao.selectByPrimaryKey(pid);
		if (pUser != null) {
			pUsers.add(pUser);
		} else {
			return pUsers;
		}
		getPUserList(pUsers, pUser.getPid());
		return pUsers;
	}

	public Result checkPid(User user) {
		Result result = null;
		String pid = user.getPid();
		if (StringUtils.isNotBlank(pid)) {
			if (user.getUserid().equals(pid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("父子不能是自己！");
				return result;
			}
			//获取父亲节点
			//		User pUser = userDao.selectByPrimaryKey(pid);
			List<User> pUsers = new ArrayList<>();
			List<User> pUserList = getPUserList(pUsers, pid);
			for (User user2 : pUserList) {
				if (user.getUserid().equals(user2.getUserid())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("父亲选择错误，请检查您的信息！");
					return result;
				}
			}
			/*	if (pUser.getGenlevel() - user.getGenlevel() != 1) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("父亲选择错误，请检查您的世系或父亲的世系是否错误！");
					return result;
				}*/
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		return result;
	}

	@Override
	public Result merge(User user) throws Exception {
		Result result = null;
		if (StringTools.trimIsEmpty(user.getUsername())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("姓名不能为空！");
			return result;
		}
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			return result;
		}
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			return result;
		}
		try {
			// 点击编辑后保存
			if (StringTools.trimNotEmpty(user.getUserid())) {
				//验证pid
				Result checkPid = checkPid(user);
				if (checkPid.getCode() == 1) {
					return checkPid;
				}
				Userinfo userinfo = user.getUserInfo();
				userinfo.setUserid(user.getUserid());
				// 编辑用户信息
				if (StringTools.trimNotEmpty(user.getPhone())) {
					// 如果是存在手机号，则用之前德密码
					UserQuery ex = new UserQuery();
					ex.or().andPhoneEqualTo(user.getPhone()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE)
							.andFamilyidEqualTo(familyid);
					List<User> list = userDao.selectByExample(ex);
					if (list != null && list.size() > 0) {
						user.setPassword(list.get(0).getPassword());
					} else {
						user.setPassword(MD5Util.string2MD5(user.getPhone().substring(user.getPhone().length() - 6)));
					}

				} else {
					// 如果手机号为空，则将绑定的第三方解绑
					LoginThirdExample example = new LoginThirdExample();
					example.or().andUseridEqualTo(user.getUserid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
					List<LoginThird> list = loginThirdMapper.selectByExample(example);
					if (list != null && list.size() > 0) {
						for (LoginThird t : list) {
							t.setDeleteflag(ConstantUtils.DELETE_TRUE);
							loginThirdMapper.updateByPrimaryKey(t);
						}
					}
				}
				user.setUpdateid(userid);
				user.setUpdatetime(new Date());
				userDao.updateByPrimaryKeySelectivePhone(user);
				UserManagerExample ume = new UserManagerExample();
				ume.or().andUseridEqualTo(user.getUserid());
				List<UserManager> mnangers = userManagerMapper.selectMnangers(user.getUserid());
				if (mnangers.size() > 0) {
					UserManager userManager = new UserManager();
					userManager.setUsername(user.getUsername());
					userManagerMapper.updateByExampleSelective(userManager, ume);
				}
				String birthplaceP = userinfo.getBirthplaceP() == null ? "" : userinfo.getBirthplaceP();
				String birthplaceC = userinfo.getBirthplaceC() == null ? "" : userinfo.getBirthplaceC();
				String birthplaceX = userinfo.getBirthplaceX() == null ? "" : userinfo.getBirthplaceX();
				String birthDetail = userinfo.getBirthDetail() == null ? "" : userinfo.getBirthDetail();
				// 出生地
				userinfo.setBirthplace(birthplaceP + "@@" + birthplaceC + "@@" + birthplaceX + "@@" + birthDetail);
				String homeplaceP = userinfo.getHomeplaceP() == null ? "" : userinfo.getHomeplaceP();
				String homeplaceC = userinfo.getHomeplaceC() == null ? "" : userinfo.getHomeplaceC();
				String homeplaceX = userinfo.getHomeplaceX() == null ? "" : userinfo.getHomeplaceX();
				String homeDetail = userinfo.getHomeDetail() == null ? "" : userinfo.getHomeDetail();
				// 常住地
				userinfo.setHomeplace(homeplaceP + "@@" + homeplaceC + "@@" + homeplaceX + "@@" + homeDetail);
				String birthday = userinfo.getBirthday();
				if (StringUtils.isNotBlank(birthday)) {
					//(农历日期范围19000101~20491229)
					int parseInt = Integer.parseInt(birthday.replace("-", ""));
					if (parseInt > 19000130 && parseInt < 20500101) {
						String solarToLunar = CalendarUtil.solarToLunar(birthday);
						userinfo.setLunarbirthday(solarToLunar);
					}
				}
				// 保存用户详细信息
				userInfoDao.updateByPrimaryKey(userinfo);

				// 删除教育经历
				UsereduQuery fq = new UsereduQuery();
				Criteria createCriteria = fq.createCriteria();
				createCriteria.andUseridEqualTo(user.getUserid());
				userEduDao.deleteByExample(fq);
				// 循环保存教育经历
				List<Useredu> eduList = user.getUserEdu();
				for (Useredu useredu2 : eduList) {
					useredu2.setUserid(user.getUserid());
					useredu2.setEduid(UUIDUtils.getUUID());
				}
				if (eduList.size() > 0) {
					userEduDao.insertEduExp(eduList);
				}
				// 删除工作经历
				UserworkexpQuery fqw = new UserworkexpQuery();
				com.jp.entity.UserworkexpQuery.Criteria createCriteriaw = fqw.createCriteria();
				createCriteriaw.andUseridEqualTo(user.getUserid());
				userworkDao.deleteByExample(fqw);
				// 循环保存工作经历
				List<Userworkexp> workList = user.getUserWorkexp();
				for (Userworkexp userwork : workList) {
					userwork.setUserid(user.getUserid());
					userwork.setWorkid(UUIDUtils.getUUID());
				}
				if (workList.size() > 0) {
					userworkDao.insertEduExp(workList);
				}

				result = new Result(MsgConstants.RESUL_SUCCESS);
			} else {
				boolean flag = checkFamilyUserNumber(1, familyid);
				if (flag == true) {
					User user2 = userDao.selectByPrimaryKey(userid);
					String userId = UUIDUtils.getUUID();
					user.setUserid(userId);
					user.setDeleteflag(0);
					user.setFamilyid(familyid);
					user.setStatus(0);
					user.setIsdirect(1);
					user.setFamilyname(user2.getFamilyname());
					user.setCreatetime(new Date());
					user.setUpdatetime(new Date());
					user.setUpdateid(familyid);
					user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(user.getUsername()));
					user.setPinyinfull(PinyinUtil.getPinyinFull(user.getUsername()));
					/*
					 * if (!user.getDietimeStr().equals("")) { // SimpleDateFormat sdfd = new
					 * SimpleDateFormat("yyy-MM-dd"); //
					 * user.setDietime(sdfd.parse(user.getDietimeStr()));
					 * if(DateUtils.isDate(user.getDietimeStr(), "-"))
					 * user.setDietime(user.getDietimeStr()); }
					 */
					if (StringTools.trimNotEmpty(user.getPhone())) {
						user.setPassword(MD5Util.string2MD5(user.getPhone().substring(user.getPhone().length() - 6)));
					}
					Userinfo userinfo = user.getUserInfo();
					userinfo.setUserid(userId);
					Useredu useredu = new Useredu();
					useredu.setUserid(userId);
					user.setCreateid(userid);
					user.setCreatetime(new Date());
					//验证pid
					Result checkPid = checkPid(user);
					if (checkPid.getCode() == 1) {
						return checkPid;
					}
					if (StringTools.trimNotEmpty(user.getGenlevel())) {
						// 校验方法 返回 true为有重复，false 没重复
						if (checkSameUser(user)) {
							// 校验有重复什么也不做哦
							// result = "500";
							result = new Result(MsgConstants.USER_SAVE_HAVEREPEAT);
							return result;
						}
					}
					// 保存用户信息
					userDao.insertSelective(user);
					String birthplaceP = userinfo.getBirthplaceP() == null ? "" : userinfo.getBirthplaceP();
					String birthplaceC = userinfo.getBirthplaceC() == null ? "" : userinfo.getBirthplaceC();
					String birthplaceX = userinfo.getBirthplaceX() == null ? "" : userinfo.getBirthplaceX();
					String birthDetail = userinfo.getBirthDetail() == null ? "" : userinfo.getBirthDetail();
					// 出生地
					userinfo.setBirthplace(birthplaceP + "@@" + birthplaceC + "@@" + birthplaceX + "@@" + birthDetail);
					String homeplaceP = userinfo.getHomeplaceP() == null ? "" : userinfo.getHomeplaceP();
					String homeplaceC = userinfo.getHomeplaceC() == null ? "" : userinfo.getHomeplaceC();
					String homeplaceX = userinfo.getHomeplaceX() == null ? "" : userinfo.getHomeplaceX();
					String homeDetail = userinfo.getHomeDetail() == null ? "" : userinfo.getHomeDetail();
					// 常住地
					userinfo.setHomeplace(homeplaceP + "@@" + homeplaceC + "@@" + homeplaceX + "@@" + homeDetail);
					String birthday = userinfo.getBirthday();
					if (StringUtils.isNotBlank(birthday)) {
						//(农历日期范围19000101~20491229)
						int parseInt = Integer.parseInt(birthday.replace("-", ""));
						if (parseInt > 19000130 && parseInt < 20500101) {
							String solarToLunar = CalendarUtil.solarToLunar(birthday);
							userinfo.setLunarbirthday(solarToLunar);
						}
					}
					// 保存用户详细信息
					userInfoDao.insertSelective(userinfo);

					// 循环保存教育经历
					List<Useredu> eduList = user.getUserEdu();
					for (Useredu useredu2 : eduList) {
						useredu2.setUserid(userId);
						useredu2.setEduid(UUIDUtils.getUUID());
					}
					if (eduList.size() > 0) {
						userEduDao.insertEduExp(eduList);
					}

					// 循环保存工作经历
					List<Userworkexp> workList = user.getUserWorkexp();
					for (Userworkexp userwork : workList) {
						userwork.setUserid(userId);
						userwork.setWorkid(UUIDUtils.getUUID());
					}
					if (workList.size() > 0) {
						userworkDao.insertEduExp(workList);
					}

					result = new Result(MsgConstants.RESUL_SUCCESS);
				} else {
					result = new Result(MsgConstants.USER_SAVE_OUTMAX);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.USER_SAVE_FAIL);
		}
		return result;
	}

	@Override
	public PageModel<User> selectUserList(PageModel<User> pageModel, User user, List<String> branchList)
			throws Exception {
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		List<User> userList = new ArrayList<>();
		List<UserManager> userManager = userContextService.getUserManagers(userid);
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		for (UserManager um : userManager) {
			if (um.getEbtype() == 1) {
				userList = userInfoDao.selecAllUserList(user);
				break;
			} else {
				if (branchList.size() > 0) {
					userList = userInfoDao.selecUserList(user, branchList);
				}
				break;
			}
		}
		pageModel.setList(userList);
		pageModel.setPageInfo(new PageInfo<User>(userList));
		return pageModel;
	}

	@Override
	public User selectByPrimaryKey(String userid) throws Exception {
		return userDao.selectByPrimaryKey(userid);
	}

	@Override
	public JsonResponse changeStatus(User user) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(user.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (user.getStatus() == null || "".equals(user.getStatus() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数status不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			User dbuser = userDao.selectByPrimaryKey(user.getUserid());
			if (user.getStatus() == ConstantUtils.USER_STATUS_OK) {
				List<User> list = userDao.selectByPhoneAndStatus(dbuser.getPhone());
				if (list.size() >= 2) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("该用户已经关联两个家族，不能加入新的家族了");
					res = new JsonResponse(result);
					return res;
				}
			}
			user.setUpdateid(WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID));
			user.setUpdatetime(new Date());
			Integer count = userDao.changeStatus(user);
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jp.service.UserService#importUsers(org.springframework.web.multipart.
	 * MultipartFile, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public JsonResponse importUsers(MultipartFile file, HttpServletRequest request) throws Exception {
		Result result = null;
		JsonResponse res = null;
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		User user2 = userDao.selectByPrimaryKey(userid);
		String branchid = request.getParameter("branchid");
		BranchKey branchkey = new BranchKey();
		branchkey.setBranchid(branchid);
		branchkey.setFamilyid(familyid);
		Branch branch = branchDao.selectByPrimaryKey(branchkey);
		if (branch == null) {
			result = new Result(MsgConstants.USER_NO_BRANCH);
			res = new JsonResponse(result);
			return res;
		}

		if (file == null) {
			result = new Result(MsgConstants.USER_NO_FILE);
			res = new JsonResponse(result);
			return res;
		}
		// 查当前分支得用户
		UserQuery userExample = new UserQuery();
		userExample.or().andBranchidEqualTo(branch.getBranchid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		List<User> userAleardyList = userDao.selectByExample(userExample);

		List<User> userList = new ArrayList<User>();
		User user = null;
		List<Userinfo> userInfoList = new ArrayList<Userinfo>();
		Map<String, User> userMap = new LinkedHashMap<String, User>();
		// Map<String, User> userPhoneMap = new LinkedHashMap<String, User>();
		Userinfo userInfo = null;
		NumberFormat nf = new DecimalFormat("#");
		// 获取文件名称
		String name = file.getOriginalFilename();
		String excelid = UUIDUtils.getUUID();
		boolean importResult = true;
		if (name.indexOf(".xlsx") != -1) {
			XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
			// 循环三sheet
			int numSheet = 0;
			// for (int numSheet = 0; numSheet < wb.getNumberOfSheets();
			// numSheet++) {
			XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
			// if (xssfSheet == null) {
			// continue;
			// }
			int totalRows = xssfSheet.getLastRowNum();
			boolean flag = checkFamilyUserNumber(totalRows - 1, familyid);
			if (flag == false) {
				/*
				 * result.setStatus(2); result.setMsg("导入用户数量超过版本最大用户限制!");
				 */
				result = new Result(MsgConstants.USER_SAVE_OUTMAX);
				res = new JsonResponse(result);
				return res;
			}
			// 读取Row,从第二行开始
			for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {

					double genlevel = xssfRow.getCell(0).getNumericCellValue();// 世系
					String username = xssfRow.getCell(1).getStringCellValue().trim();// 姓名
					if (!StringTools.trimNotEmpty(username)) {
						continue;
					}
					String sex = xssfRow.getCell(2).getStringCellValue().trim();// 性别
					String liveStatus = xssfRow.getCell(3).getStringCellValue().trim();// 在世状态
					String iMarryied = xssfRow.getCell(4).getStringCellValue().trim();// 是否婚配
					String phone = xssfRow.getCell(5).getStringCellValue().trim();// 手机号
					if (StringUtils.isNotBlank(phone)) {
						List<User> userList1 = userDao.validatePhone(familyid, null, phone);
						if (userList1.size() > 0) {
							result = new Result(MsgConstants.USER_PHONE_REPEAT);
							res = new JsonResponse(result);
							return res;
						}
					}
					if (StringTools.trimNotEmpty(phone)) {
						phone = nf.format(Double.parseDouble(phone));
					}
					String pname = xssfRow.getCell(6).getStringCellValue().trim();// 父亲名称
					String brotherpos = xssfRow.getCell(7).getStringCellValue().trim();// 排行
					String usedname = xssfRow.getCell(8).getStringCellValue().trim();// 曾用名
					String nation = xssfRow.getCell(9).getStringCellValue().trim();// 民族
					String background = xssfRow.getCell(10).getStringCellValue().trim();// 背景
					String idCard = xssfRow.getCell(11).getStringCellValue().trim();// 身份证
					String education = xssfRow.getCell(12).getStringCellValue().trim();// 学历
					if (education != null) {
						if ("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						} else if ("高中".equals(education) || "中专".equals(education)) {
							education = "高中(中专)";
						}

					}
					if (StringTools.trimNotEmpty(idCard)) {
						idCard = nf.format(Double.parseDouble(idCard));
					}
					String birthday = xssfRow.getCell(13).getStringCellValue().trim();// 生日
					String birthplace = xssfRow.getCell(14).getStringCellValue().trim();// 出生地
					birthplace = replacePlace(birthplace);
					String homeplace = xssfRow.getCell(15).getStringCellValue().trim();// 常住地
					homeplace = replacePlace(homeplace);
					String dietime = xssfRow.getCell(16).getStringCellValue().trim();// 离世日期
					String fixplace = xssfRow.getCell(17).getStringCellValue().trim();// 葬于某地
					// birthplace = replacePlace(fixplace);
					String remark = xssfRow.getCell(18).getStringCellValue().trim();// 备注
					String userId = UUIDUtils.getUUID();
					user = new User();
					user.setExcelid(excelid);
					user.setUserid(userId);
					user.setFamilyid(familyid);
					user.setFamilyname(user2.getFamilyname());
					user.setStatus(0);
					user.setIsdirect(1);
					user.setGenlevel((int) genlevel);
					if (DateUtils.isDate(dietime, "-"))
						user.setDietime(dietime);
					user.setUsername(username);
					user.setUsedname(usedname);
					user.setIdcard(idCard);
					user.setPhone(phone);
					user.setBranchid(branch.getBranchid());
					user.setBranchname(branch.getBranchname());
					if (StringTools.trimNotEmpty(phone)) {
						user.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
					}
					if (sex.equals("男")) {
						user.setSex(1);
					} else {
						user.setSex(0);
					}
					user.setBrotherpos(brotherpos);
					user.setPname(pname);
					if ("在世".equals(liveStatus)) {
						user.setLivestatus(0);
						// 去除在世用户中手机重复的数据
						// if (userPhoneMap.containsKey(user.getPhone())) {
						// continue;
						// } else {
						// userPhoneMap.put(user.getPhone(), user);
						// }
					} else if ("离世".equals(liveStatus)) {
						user.setLivestatus(1);
					} else {
						user.setLivestatus(2);
					}
					if ("已婚".equals(iMarryied)) {
						user.setIsMarry(0);
					} else if ("未婚".equals(iMarryied)) {
						user.setIsMarry(1);
					} else {
						user.setIsMarry(2);
					}
					user.setFixplace(fixplace);

					if (userAleardyList != null && userAleardyList.size() > 0) {
						for (User uuu : userAleardyList) {
							if (uuu.getUsername().equals(username) && uuu.getGenlevel() == genlevel) {
								importResult = false;
								user.setIsnormal(0);
								user.setMsg("存在重名同世系用户请特殊处理！");
								break;
							}

						}
					}
					String key = ((int) genlevel - 1) + pname;
					if ((pname == null || "".equals(pname)) && rowNum == 1) {
						if (userMap.containsKey((int) genlevel + username)) {
							importResult = false;
							user.setIsnormal(0);
							user.setMsg("存在重名同世系用户请特殊处理！");
						}
						userMap.put((int) genlevel + username, user);
						// 如果父亲名字为空，则为分支起始人
						branch.setBeginuserid(userId);
						branch.setBeginname(username);
						branchDao.updateByBranchidSelective(branch);
					} else if (userMap.containsKey(key)) {
						User userTemp = userMap.get(key);
						user.setPid(userTemp.getUserid());
						if (userMap.containsKey((int) genlevel + username)) {
							importResult = false;
							user.setIsnormal(0);
							user.setMsg("存在重名同世系用户请特殊处理！");
						}
						userMap.put((int) genlevel + username, user);
					} else {
						importResult = false;
						user.setIsnormal(0);
						user.setMsg("请核对世系或父亲名字！");
					}
					user.setCreateid(userid);
					user.setCreatetime(new Date());
					user.setUpdateid(userid);
					user.setUpdatetime(new Date());
					user.setDeleteflag(ConstantUtils.DELETE_FALSE);
					user.setFamilyname(user2.getFamilyname());
					user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(username));
					user.setPinyinfull(PinyinUtil.getPinyinFull(username));
					userInfo = new Userinfo();
					userInfo.setUserid(userId);
					if (StringTools.trimNotEmpty(birthday)) {
						// userInfo.setBirthday(birthday);
						if (DateUtils.isDate(birthday, "-")) {
							userInfo.setBirthday(birthday);
							int year = Integer.parseInt(birthday.split("-")[0]);
							userInfo.setZodica(ZodiacUtil.date2Zodica(year));
							String[] arr = birthday.split("-");
							if (arr.length == 3) {
								userInfo.setConstellation(ZodiacUtil.date2Constellation(birthday));
							}
						}

					}

					userInfo.setEducation(education);

					userInfo.setBirthplace(birthplace);
					userInfo.setHomeplace(homeplace);
					userInfo.setNation(nation);
					userInfo.setBackground(background);
					userInfo.setRemark(remark);
					// userList.add(user);
					// userInfoList.add(userInfo);
					boolean sameFlag = checkSameUser(user);
					if (sameFlag) {
						importResult = false;
						user.setIsnormal(0);
						user.setMsg("数据库已存在该数据！");
					}
					userList.add(user);
					userInfoList.add(userInfo);
				}
			}

			if (userList != null && userList.size() > 0 && importResult) {
				userImportMapper.importUser(userList);
			}
			if (userInfoList != null && userInfoList.size() > 0 && importResult) {
				userInfoImportMapper.importUser(userInfoList);
			}
			// }
			wb.close();
		} else {
			POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			ExcelUtil eutil = new ExcelUtil(wb, sheet);
			// 获取上传的所有行数
			int lastRowNum = sheet.getLastRowNum();
			// 判断家族人数是否超出当前家族容纳人数上限
			boolean flag = checkFamilyUserNumber(lastRowNum - 1, familyid);
			if (flag == false) {
				/*
				 * result.setStatus(2); result.setMsg("导入用户数量超过版本最大用户限制!");
				 */
				result = new Result(MsgConstants.USER_SAVE_OUTMAX);
				res = new JsonResponse(result);
				return res;
			}
			userStringList = new ArrayList<String>();
			for (int i = 1; i < lastRowNum + 1; i++) {
				if (sheet.getRow(i) != null) {
					String genlevelstr = ExcelUtil
							.toDecimalFormat(eutil.getCellContent(sheet.getRow(i).getCell(0)).trim());// 世系
					String username = eutil.getCellContent(sheet.getRow(i).getCell(1)).trim();
					if (!StringTools.trimNotEmpty(username)) {
						continue;
					}
					String sex = eutil.getCellContent(sheet.getRow(i).getCell(2)).trim();
					String liveStatus = eutil.getCellContent(sheet.getRow(i).getCell(3)).trim();
					String iMarryied = eutil.getCellContent(sheet.getRow(i).getCell(4)).trim();
					String phone = eutil.getCellContent(sheet.getRow(i).getCell(5)).trim();
					if (StringTools.trimNotEmpty(phone)) {
						phone = nf.format(Double.parseDouble(phone));
					}
					String pname = eutil.getCellContent(sheet.getRow(i).getCell(6)).trim();
					String brotherpos = eutil.getCellContent(sheet.getRow(i).getCell(7)).trim();
					String usedname = eutil.getCellContent(sheet.getRow(i).getCell(8)).trim();
					String nation = eutil.getCellContent(sheet.getRow(i).getCell(9)).trim();
					String background = eutil.getCellContent(sheet.getRow(i).getCell(10)).trim();
					String idCard = eutil.getCellContent(sheet.getRow(i).getCell(11)).trim();
					if (StringTools.trimNotEmpty(idCard)) {
						idCard = nf.format(Double.parseDouble(idCard));
					}
					String education = eutil.getCellContent(sheet.getRow(i).getCell(13)).trim();// 学历
					if (education != null) {
						if ("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						} else if ("高中".equals(education) || "中专".equals(education)) {
							education = "高中(中专)";
						}

					}
					String birthday = eutil.getCellContent(sheet.getRow(i).getCell(13)).trim();

					String birthplace = eutil.getCellContent(sheet.getRow(i).getCell(14)).trim();
					birthplace = replacePlace(birthplace);
					String homeplace = eutil.getCellContent(sheet.getRow(i).getCell(15)).trim();
					homeplace = replacePlace(homeplace);
					String dietime = eutil.getCellContent(sheet.getRow(i).getCell(16)).trim();
					String fixplace = eutil.getCellContent(sheet.getRow(i).getCell(17)).trim();
					// fixplace = replacePlace(fixplace);
					String remark = eutil.getCellContent(sheet.getRow(i).getCell(18)).trim();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Integer genlevel = Integer.parseInt(genlevelstr);
					String userId = UUIDUtils.getUUID();
					user = new User();
					user.setExcelid(excelid);
					user.setUserid(userId);
					user.setFamilyid(familyid);
					user.setFamilyname(user2.getFamilyname());
					user.setStatus(0);
					user.setIsdirect(1);
					user.setGenlevel(genlevel);
					if (DateUtils.isDate(dietime, "-"))
						user.setDietime(dietime);

					user.setUsername(username);
					user.setUsedname(usedname);
					user.setIdcard(idCard);
					user.setPhone(phone);
					user.setBranchid(branch.getBranchid());
					user.setBranchname(branch.getBranchname());
					if (StringTools.trimNotEmpty(phone)) {
						user.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
					}
					if (sex.equals("男")) {
						user.setSex(1);
					} else {
						user.setSex(0);
					}
					user.setBrotherpos(brotherpos);
					user.setPname(pname);
					if ("在世".equals(liveStatus)) {
						user.setLivestatus(0);
						// 去除在世用户中手机重复的数据
						// if (userPhoneMap.containsKey(user.getPhone())) {
						// continue;
						// } else {
						// userPhoneMap.put(user.getPhone(), user);
						// }
					} else if ("离世".equals(liveStatus)) {
						user.setLivestatus(1);
					} else {
						user.setLivestatus(2);
					}
					if ("已婚".equals(iMarryied)) {
						user.setIsMarry(0);
					} else if ("未婚".equals(iMarryied)) {
						user.setIsMarry(1);
					} else {
						user.setIsMarry(2);
					}
					user.setFixplace(fixplace);
					String key = ((int) genlevel - 1) + pname;
					if (userAleardyList != null && userAleardyList.size() > 0) {
						for (User uuu : userAleardyList) {
							if (uuu.getUsername().equals(username) && uuu.getGenlevel() == genlevel) {
								importResult = false;
								user.setIsnormal(0);
								user.setMsg("存在重名同世系用户请特殊处理！");
								break;
							}

						}
					}
					if ((pname == null || "".equals(pname)) && i == 1) {
						if (userMap.containsKey((int) genlevel + username)) {
							importResult = false;
							user.setIsnormal(0);
							user.setMsg("存在重名同世系用户请特殊处理！");
						}
						userMap.put((int) genlevel + username, user);
						// 如果父亲名字为空，则为分支起始人
						branch.setBeginuserid(userId);
						branch.setBeginname(username);
						branchDao.updateByBranchidSelective(branch);
					} else if (userMap.containsKey(key)) {
						User userTemp = userMap.get(key);
						user.setPid(userTemp.getUserid());
						if (userMap.containsKey((int) genlevel + username)) {
							importResult = false;
							user.setIsnormal(0);
							user.setMsg("存在重名同世系用户请特殊处理！");
						}
						userMap.put((int) genlevel + username, user);
					} else {
						importResult = false;
						user.setIsnormal(0);
						user.setMsg("请核对世系或父亲名字！");
					}
					user.setCreateid(userid);
					user.setCreatetime(new Date());
					user.setUpdateid(userid);
					user.setUpdatetime(new Date());
					user.setDeleteflag(ConstantUtils.DELETE_FALSE);
					user.setFamilyname(user2.getFamilyname());
					user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(username));
					user.setPinyinfull(PinyinUtil.getPinyinFull(username));
					userInfo = new Userinfo();
					userInfo.setUserid(userId);
					if (StringTools.trimNotEmpty(birthday)) {
						// userInfo.setBirthday(birthday);
						if (DateUtils.isDate(birthday, "-")) {
							userInfo.setBirthday(birthday);
							int year = Integer.parseInt(birthday.split("-")[0]);
							userInfo.setZodica(ZodiacUtil.date2Zodica(year));
							String[] arr = birthday.split("-");
							if (arr.length == 3) {
								userInfo.setConstellation(ZodiacUtil.date2Constellation(birthday));
							}
						}

					}
					userInfo.setBirthplace(birthplace);
					userInfo.setHomeplace(homeplace);
					userInfo.setNation(nation);
					userInfo.setBackground(background);
					userInfo.setRemark(remark);
					// userList.add(user);
					// userInfoList.add(userInfo);
					boolean sameFlag = checkSameUser(user);
					if (sameFlag) {
						importResult = false;
						user.setIsnormal(0);
						user.setMsg("数据库已存在该数据！");
					}
					userList.add(user);
					userInfoList.add(userInfo);
				}
			}
			if (userList != null && userList.size() > 0 && importResult) {
				userImportMapper.importUser(userList);
			}
			if (userInfoList != null && userInfoList.size() > 0 && importResult) {
				userInfoImportMapper.importUser(userInfoList);
			}

		}
		if (userList.size() < 1) {
			result = new Result(MsgConstants.USER_NO_IMPORT);
			res = new JsonResponse(result);
			String userString = GsonUtil.GsonString(userStringList);
			res.setData(userString);
		} else {
			userStringList.add("本次共导入用户" + userList.size() + "人");
			String userString = GsonUtil.GsonString(userStringList);
			if (importResult) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(userList);
				res.setData1(userString);
				res.setEntity(excelid);
			} else {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setData(userList);
				res.setData1(userString);
			}

		}
		return res;
	}

	/**
	 * 
	 * @描述 去数据库检查是否有重复的直系用户数据
	 * @作者 jinlizhi
	 * @时间 2017年5月29日下午3:12:25
	 * @参数 @param user
	 * @参数 @return
	 * @return boolean
	 */
	private boolean checkSameUser(User user) {
		boolean sameFlag = false;
		// 加入list前 判断去重复
		// if (user.getLivestatus().equals(0)) {
		// // 在世 需要判断手机号
		// List<String> phones = userDao.selectPhoneByFamilyId(user.getFamilyid());
		// if (StringTools.trimNotEmpty(user.getPhone()) &&
		// phones.contains(user.getPhone())) {
		// sameFlag = true;
		// userStringList.add("第"+user.getGenlevel()+"世:"+user.getUsername()+"手机号:"+user.getPhone()+"已存在");
		// log_.info("数据库有重复数据无法导入-" + user.toString());
		// System.out.println("数据库有重复数据无法导入-" + user.toString());
		// } else {
		// sameFlag = false;
		// }
		// } else {
		// 离世 不需要判断手机号 使用用户名和父亲名字判断及手机号码判断
		List<User> searchRt = userDao.selectUserByFamilyId(user.getFamilyid());
		for (User userf : searchRt) {
			if (StringUtil.isNotEmpty(userf.getUsername()) && user.getUsername().equals(userf.getUsername())
					&& user.getGenlevel().equals(userf.getGenlevel())) {
				if (userf.getPhone() != null && userf.getPhone().equals(user.getPhone())) {
					if (StringUtil.isNotEmpty(userf.getPname())) {
						if (StringUtil.isNotEmpty(user.getPname()) && userf.getPname().equals(user.getPname())) {
							sameFlag = true;
							userStringList.add("第" + user.getGenlevel() + "世:" + user.getUsername() + "已存在");
							log_.info("数据库有重复数据无法导入-" + user.toString());
							System.out.println("数据库有重复数据无法导入-" + user.toString());
							return sameFlag;
						}
					}
					if (StringUtil.isEmpty(userf.getPname())) {
						if (StringUtil.isEmpty(user.getPname()) && userf.getGenlevel() == user.getGenlevel()) {
							sameFlag = true;
							userStringList.add("第" + user.getGenlevel() + "世:" + user.getUsername() + "已存在");
							log_.info("数据库有重复数据无法导入-" + user.toString());
							System.out.println("数据库有重复数据无法导入-" + user.toString());
							return sameFlag;
						}
					}
					sameFlag = false;
				}

				// sameFlag = true;
				// log_.info("数据库有重复数据无法导入-" + user.toString());
				// System.out.println("数据库有重复数据无法导入-" + user.toString());
				// return sameFlag;
			} else {
				sameFlag = false;
			}
		}
		// }
		return sameFlag;
	}

	/**
	 * 
	 * @描述 去数据库检查是否有重复的配偶用户数据
	 * @作者 lipeng
	 * @时间 2018年3月13日
	 * @参数 @param user
	 * @参数 @return
	 * @return boolean
	 */
	private boolean checkSameMateUser(User user, List<String> userStringList) {
		boolean sameFlag = false;
		// 加入list前 判断去重复

		// 离世 不需要判断手机号 使用用户名和父亲名字判断
		List<User> searchRt = userDao.selectUserByFamilyId(user.getFamilyid());
		for (User userf : searchRt) {
			if (userf.getDeleteflag() == 0 && StringUtil.isNotEmpty(userf.getUsername())
					&& userf.getUsername().equals(user.getUsername()) && userf.getGenlevel() == user.getGenlevel()) {
				if (StringUtil.isNotEmpty(userf.getMatename()) && userf.getMatename().equals(user.getMatename())) {
					sameFlag = true;
					userStringList.add("第" + user.getGenlevel() + "世:" + user.getUsername() + "已存在");
					log_.info("数据库有重复数据无法导入-" + user.toString());
					System.out.println("数据库有重复数据无法导入-" + user.toString());
					return sameFlag;
				}
				sameFlag = false;
				// sameFlag = true;
				// log_.info("数据库有重复数据无法导入-" + user.toString());
				// System.out.println("数据库有重复数据无法导入-" + user.toString());
				// return sameFlag;
			} else {
				sameFlag = false;
			}
		}
		// UserQuery uq = new UserQuery();
		// uq.or().andUsernameEqualTo(user.getUsername())
		// .andMatenameEqualTo(user.getMatename())
		// .andGenlevelEqualTo(user.getGenlevel())
		// .andBranchidEqualTo(user.getBranchid())
		// .andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);

		return sameFlag;
	}

	/**
	 * 验证配偶是否有重复
	 */
	private boolean checkSameMates(Usermates userMates) {
		boolean sameFlag = false;
		List<Usermates> matelist = userMatesDao.selectmateIdByUserId(userMates.getUserid());
		for (Usermates userm : matelist) {
			if ((StringUtil.isNotEmpty(userm.getName()) && userm.getName().equals(userMates.getName()))
					&& (StringUtil.isNotEmpty(userm.getMatetype())
							&& userm.getMatetype().equals(userMates.getMatetype()))) {
				sameFlag = true;
				userMateStringList.add(userm.getName() + "已存在");
				log_.info("数据库有重复数据无法导入-" + userMates.toString());
				System.out.println("数据库有重复数据无法导入-" + userMates.toString());
				return sameFlag;
			} else {
				sameFlag = false;
			}
		}
		return sameFlag;
	}

	@Override
	public List<User> selectPnameAndMate(String familyid, List<String> branchList) throws Exception {
		return userDao.selectPnameAndMate(familyid, branchList);
	}

	@Override
	public JsonResponse importUserMates(MultipartFile file, HttpServletRequest request) throws Exception {
		String msg = "";
		Result result = null;
		JsonResponse res = null;
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		User user2 = userDao.selectByPrimaryKey(userid);
		String branchid = request.getParameter("branchid");
		BranchKey branchkey = new BranchKey();
		branchkey.setBranchid(branchid);
		branchkey.setFamilyid(familyid);
		Branch branch = branchDao.selectByPrimaryKey(branchkey);
		if (branch == null) {
			result = new Result(MsgConstants.USER_NO_BRANCH);
			res = new JsonResponse(result);
			return res;
		}

		if (file == null) {
			result = new Result(MsgConstants.USER_NO_FILE);
			res = new JsonResponse(result);
			return res;
		}
		List<User> userList = new ArrayList<User>();
		// User user = null;
		Usermates userMates = null;
		List<Usermates> userMatesList = new ArrayList<Usermates>();
		// 批量更新 已经存在的用户
		List<User> userAleardyListUpdate = new ArrayList<User>();
		// 查询用户列表
		// User userAleardy = new User();
		// userAleardy.setFamilyid(familyid);
		// userAleardy.setIsdirect(1);
		// userAleardy.setIsMarry(0);
		// 获取文件名称
		String name = file.getOriginalFilename();
		NumberFormat nf = new DecimalFormat("#");
		List<String> userStringList = new ArrayList<String>();
		UserQuery userExample = new UserQuery();
		userExample.or().andBranchidEqualTo(branch.getBranchid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		List<User> userAleardyList = userDao.selectByExample(userExample);

		// Map<String, User> userAleardyMap = new HashMap<String, User>();
		// if (userAleardyList != null && userAleardyList.size() > 0) {
		// for (int i = 0; i < userAleardyList.size(); i++) {
		// userAleardyMap.put(userAleardyList.get(i).getGenlevel() +
		// userAleardyList.get(i).getUsername(), userAleardyList.get(i));
		// }
		// }
		String excelid = UUIDUtils.getUUID();
		Userinfo userInfo;
		List<Userinfo> userInfoList = new ArrayList<Userinfo>();
		boolean importResult = true;
		if (name.indexOf(".xlsx") != -1) {
			XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
			// 循环三sheet
			for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				int totalRows = xssfSheet.getLastRowNum();
				boolean flag = checkFamilyUserNumber(totalRows - 1, familyid);
				if (flag == false) {
					result = new Result(MsgConstants.USER_SAVE_OUTMAX);
					res = new JsonResponse(result);
					return res;
				}
				// 读取Row,从第二行开始
				for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						double genlevel = xssfRow.getCell(0).getNumericCellValue();// 世系
						String username = xssfRow.getCell(1).getStringCellValue().trim();// 姓名
						if (!StringTools.trimNotEmpty(username)) {
							continue;
						}
						String sex = xssfRow.getCell(2).getStringCellValue().trim();// 性别
						String liveStatus = xssfRow.getCell(3).getStringCellValue().trim();// 是否在世
						String phone = xssfRow.getCell(4).getStringCellValue().trim();// 手机号
						if (StringTools.trimNotEmpty(phone)) {
							phone = nf.format(Double.parseDouble(phone));
						}
						String husbandname = xssfRow.getCell(5).getStringCellValue().trim();// 丈夫名称
						String ifMates = xssfRow.getCell(6).getStringCellValue().trim();// 配偶排行
						String usedname = xssfRow.getCell(7).getStringCellValue().trim();// 曾用名
						String nation = xssfRow.getCell(8).getStringCellValue().trim();// 民族
						String background = xssfRow.getCell(9).getStringCellValue().trim();// 政治背景
						String education = xssfRow.getCell(10).getStringCellValue().trim();// 学历
						if (education != null) {
							if ("大学本科".equals(education) || "大学".equals(education)) {
								education = "本科";
							} else if ("高中".equals(education) || "中专".equals(education)) {
								education = "高中(中专)";
							}

						}
						String idCard = xssfRow.getCell(11).getStringCellValue().trim();// 身份证

						if (StringTools.trimNotEmpty(idCard)) {
							idCard = nf.format(Double.parseDouble(idCard));
						}
						String birthday = xssfRow.getCell(12).getStringCellValue().trim();// 生日
						String birthplace = xssfRow.getCell(13).getStringCellValue().trim();// 出生地
						birthplace = replacePlace(birthplace);
						String homeplace = xssfRow.getCell(14).getStringCellValue().trim();// 常住地
						homeplace = replacePlace(homeplace);
						String dietime = xssfRow.getCell(15).getStringCellValue().trim();// 去世时间
						String fixplace = xssfRow.getCell(16).getStringCellValue().trim();// 葬于某地
						// birthplace = replacePlace(fixplace);
						String remark = xssfRow.getCell(17).getStringCellValue().trim();// 简介
						String userId = UUIDUtils.getUUID();

						if (ifMates.equals("妻子") || ifMates.equals("丈夫") || ifMates.equals("元配") || ifMates.equals("继配")
								|| ifMates.equals("次配") && liveStatus.equals("在世")) {
							// 如果配偶存在
							if (userAleardyList != null && userAleardyList.size() > 0) {
								int n = 0;// 计数是否存在多个世系一样并且名字一样的用户
								User userAl = null;
								for (int i = 0; i < userAleardyList.size(); i++) {
									User u = userAleardyList.get(i);
									if (u.getGenlevel() == (int) genlevel && u.getUsername().equals(husbandname)
											&& (u.getMateid() == null || "".equals(u.getMateid()))) {
										n++;
										if (n > 1) {
											break;
										} else {
											userAl = u;
										}
									}
								}

								if (n == 1) {
									User user = new User();
									user.setUserid(userId);
									user.setFamilyid(familyid);
									user.setFamilyname(user2.getFamilyname());
									user.setStatus(0);
									user.setIsdirect(1);
									user.setGenlevel((int) genlevel);
									user.setUsername(username);
									user.setUsedname(usedname);
									user.setIdcard(idCard);
									user.setExcelid(excelid);
									if (sex.equals("男")) {
										user.setSex(1);
									} else {
										user.setSex(0);
									}
									if (StringTools.trimNotEmpty(phone)) {
										user.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
									}
									// user.setBrotherpos(brotherpos);
									if (DateUtils.isDate(dietime, "-"))
										user.setDietime(dietime);
									// user.setDietime(dietime);
									if ("在世".equals(liveStatus)) {
										user.setLivestatus(0);
										// 去除在世用户中手机重复的数据
										// if (userPhoneMap.containsKey(user.getPhone())) {
										// continue;
										// } else {
										// userPhoneMap.put(user.getPhone(), user);
										// }
									} else if ("离世".equals(liveStatus)) {
										user.setLivestatus(1);
									} else {
										user.setLivestatus(2);
									}
									user.setFixplace(fixplace);
									user.setCreatetime(new Date());
									// 维护当前配偶id

									// 维护当前配偶id
									if (userAl == null) {
										importResult = false;
										user.setIsnormal(0);
										user.setMsg("请检查配偶姓名或世系！");
									}
									// else if(userAl.getMateid()!=null && !"".equals(userAl.getMateid())) {
									// user.setIsnormal(0);
									// user.setMsg("请勿重复导入配偶！");
									// }
									else {
										userAl.setMateid(userId);
										userAl.setMatename(username);
										user.setMateid(userAl.getUserid());

										// user.setBranchid(userAl.getBranchid());
										// user.setBranchname(userAl.getBranchname());
										userAl.setIsMarry(0);
									}
									user.setBranchid(branchid);
									user.setBranchname(branch.getBranchname());
									user.setMatename(husbandname);
									user.setIsMarry(0);
									user.setPhone(phone);
									user.setIsdirect(0);
									user.setDeleteflag(ConstantUtils.DELETE_FALSE);
									user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(username));
									user.setPinyinfull(PinyinUtil.getPinyinFull(username));
									user.setCreateid(userid);
									user.setCreatetime(new Date());
									userList.add(user);

									// boolean sameFlag = checkSameUser(user);
									boolean sameFlag = checkSameMateUser(user, userStringList);
									if (sameFlag) {
										importResult = false;
										user.setIsnormal(0);
										user.setMsg("请勿重复导入配偶！");
										continue;
									} else {

										userAleardyListUpdate.add(userAl);
									}
									userInfo = new Userinfo();
									userInfo.setUserid(userId);
									if (StringTools.trimNotEmpty(birthday)) {
										// userInfo.setBirthday(birthday);
										if (DateUtils.isDate(birthday, "-")) {
											userInfo.setBirthday(birthday);
											int year = Integer.parseInt(birthday.split("-")[0]);
											userInfo.setZodica(ZodiacUtil.date2Zodica(year));
											String[] arr = birthday.split("-");
											if (arr.length == 3) {
												userInfo.setConstellation(ZodiacUtil.date2Constellation(birthday));
											}
										}

									}

									userInfo.setEducation(education);

									userInfo.setBirthplace(birthplace);
									userInfo.setHomeplace(homeplace);
									userInfo.setNation(nation);
									userInfo.setBackground(background);
									userInfo.setRemark(remark);

									userInfoList.add(userInfo);
								} else {
									msg += username + ",";
								}
								// if (userAleardyMap.containsKey((int) genlevel + husbandname) &&
								// (StringTools.trimNotEmpty(userAleardyMap.get(genlevel +
								// husbandname).getMateid()))) {}
							}
						} else {
							userMates = new Usermates();
							if (userAleardyList != null && userAleardyList.size() > 0) {
								int n = 0;// 计数是否存在多个世系一样并且名字一样的用户
								for (int i = 0; i < userAleardyList.size(); i++) {
									User u = userAleardyList.get(i);
									if (u.getGenlevel() == (int) genlevel && u.getUsername().equals(husbandname)
											&& StringTools.trimNotEmpty(u.getMateid())) {
										n++;
										// 新增userMates记录
										User userAl = u;
										userMates.setUserid(userAl.getUserid());
										userMates.setMateid(userId);
										userMates.setName(userAl.getUsername());
										if (sex.equals("男")) {
											userMates.setSex(1);
										} else {
											userMates.setSex(0);
										}
										userMates.setNation(nation);
										// userMates.setBirthday(birthday);
										userMates.setBirthplace(birthplace);
										userMates.setRemark(remark);
										// 需要验证
										boolean sameFlag = checkSameMates(userMates);
										if (sameFlag) {
											continue;
										} else {
											userMatesList.add(userMates);
										}

									}
								}
							}
							// if (userAleardyMap.containsKey(genlevel + husbandname)) {}
						}
					}
				}
				// 批量导入妻子或丈夫配偶 user
				if (userList != null && userList.size() > 0 && importResult) {
					userImportMapper.importUser(userList);
				}

				// 批量更新 已存在的用户
				if (userAleardyListUpdate != null && userAleardyListUpdate.size() > 0 && importResult) {
					userDao.updateAleardyUser(userAleardyListUpdate);
				}

				// 批量插入 配偶 表
				if (userMatesList != null && userMatesList.size() > 0 && importResult) {
					userMatesDao.insertMatesList(userMatesList);
				}
				// 批量插入 配偶 表
				if (userInfoList != null && userInfoList.size() > 0 && importResult) {
					userInfoImportMapper.importUser(userInfoList);
				}
			}
		} else {
			ExcelUtil eutil = null;
			HSSFWorkbook wb = null;
			HSSFSheet sheet = null;
			POIFSFileSystem fs;
			fs = new POIFSFileSystem(file.getInputStream());
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			eutil = new ExcelUtil(wb, sheet);
			// 获取上传的所有行数
			int lastRowNum = sheet.getLastRowNum();
			boolean flag = checkFamilyUserNumber(lastRowNum - 1, familyid);
			if (flag == false) {
				result = new Result(MsgConstants.USER_SAVE_OUTMAX);
				res = new JsonResponse(result);
				return res;
			}
			userMateStringList = new ArrayList<String>();
			for (int i = 1; i < lastRowNum + 1; i++) {

				if (sheet.getRow(i) != null) {
					String genlevel = eutil.toDecimalFormat(eutil.getCellContent(sheet.getRow(i).getCell(0)).trim());// 世系
					String username = eutil.getCellContent(sheet.getRow(i).getCell(1)).trim();// 用户名
					if (!StringTools.trimNotEmpty(username)) {
						continue;
					}
					String sex = eutil.getCellContent(sheet.getRow(i).getCell(2)).trim();// 性别
					String liveStatus = eutil.getCellContent(sheet.getRow(i).getCell(3)).trim();// 在世离世
					String phone = eutil.getCellContent(sheet.getRow(i).getCell(4)).trim();// 手机号
					if (StringTools.trimNotEmpty(phone)) {
						phone = nf.format(Double.parseDouble(phone));
					}
					String husbandname = eutil.getCellContent(sheet.getRow(i).getCell(5)).trim();// 配偶姓名
					String ifMates = eutil.getCellContent(sheet.getRow(i).getCell(6)).trim();// 关系
					String usedname = eutil.getCellContent(sheet.getRow(i).getCell(7)).trim();// 曾用名
					String nation = eutil.getCellContent(sheet.getRow(i).getCell(8)).trim();// 民族
					String background = eutil.getCellContent(sheet.getRow(i).getCell(9)).trim();// 政治背景
					String idCard = eutil.getCellContent(sheet.getRow(i).getCell(10)).trim();// 身份证号
					String education = eutil.getCellContent(sheet.getRow(i).getCell(11)).trim();// 学历
					if (education != null) {
						if ("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						} else if ("高中".equals(education) || "中专".equals(education)) {
							education = "高中(中专)";
						}

					}
					String birthday = eutil.getCellContent(sheet.getRow(i).getCell(12)).trim();// 生日

					if (StringTools.trimNotEmpty(idCard)) {
						idCard = nf.format(Double.parseDouble(idCard));
					}

					String birthplace = eutil.getCellContent(sheet.getRow(i).getCell(13)).trim();// 出生地
					birthplace = replacePlace(birthplace);
					String homeplace = eutil.getCellContent(sheet.getRow(i).getCell(14)).trim();// 常住地
					homeplace = replacePlace(homeplace);
					String dietime = eutil.getCellContent(sheet.getRow(i).getCell(15)).trim();// 趋势时间
					String fixplace = eutil.getCellContent(sheet.getRow(i).getCell(16)).trim();// 安葬地
					// birthplace = replacePlace(fixplace);
					String remark = eutil.getCellContent(sheet.getRow(i).getCell(17)).trim();// 个人简介
					String userId = UUIDUtils.getUUID();

					if (ifMates.equals("妻子") || ifMates.equals("丈夫") || ifMates.equals("元配") || ifMates.equals("继配")
							|| ifMates.equals("次配")) {
						// 如果配偶存在
						if (userAleardyList != null && userAleardyList.size() > 0) {
							int n = 0;// 计数是否存在多个世系一样并且名字一样的用户
							User userAl = null;
							for (int k = 0; k < userAleardyList.size(); k++) {

								User u = userAleardyList.get(k);
								if (u.getGenlevel() != null && u.getGenlevel() == Integer.parseInt(genlevel)
										&& u.getUsername().equals(husbandname)) {
									n++;
									if (n > 1) {
										break;
									} else {
										userAl = u;
									}
								}
							}

							User user = new User();
							user.setUserid(userId);
							user.setFamilyid(familyid);
							user.setFamilyname(user2.getFamilyname());
							user.setStatus(0);
							user.setIsdirect(1);
							user.setGenlevel(Integer.parseInt(genlevel));
							user.setUsername(username);
							user.setUsedname(usedname);
							user.setIdcard(idCard);
							user.setExcelid(excelid);
							user.setIsMarry(0);
							if (sex.equals("男")) {
								user.setSex(1);
							} else {
								user.setSex(0);
							}
							if (StringTools.trimNotEmpty(phone)) {
								user.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
							}
							// user.setBrotherpos(brotherpos);
							if (DateUtils.isDate(dietime, "-"))
								user.setDietime(dietime);
							// user.setDietime(DateUtils.stringToDate(dietime));
							if ("在世".equals(liveStatus)) {
								user.setLivestatus(0);
								// 去除在世用户中手机重复的数据
								// if (userPhoneMap.containsKey(user.getPhone())) {
								// continue;
								// } else {
								// userPhoneMap.put(user.getPhone(), user);
								// }
							} else if ("离世".equals(liveStatus)) {
								user.setLivestatus(1);
							} else {
								user.setLivestatus(2);
							}
							user.setFixplace(fixplace);
							user.setCreatetime(new Date());
							// 维护当前配偶id
							if (userAl == null) {
								importResult = false;
								user.setIsnormal(0);
								user.setMsg("请检查配偶姓名或世系！");
							}
							// else if(userAl.getMateid()!=null && !"".equals(userAl.getMateid())) {
							// user.setIsnormal(0);
							// user.setMsg("请勿重复导入配偶！");
							// }
							else {
								userAl.setMateid(userId);
								userAl.setMatename(username);
								user.setMateid(userAl.getUserid());
								// user.setMatename(userAl.getUsername());
								// user.setBranchid(userAl.getBranchid());
								// user.setBranchname(userAl.getBranchname());
								userAl.setIsMarry(0);
							}
							user.setBranchid(branchid);
							user.setBranchname(branch.getBranchname());
							user.setMatename(husbandname);
							user.setPhone(phone);
							user.setIsdirect(0);
							user.setDeleteflag(ConstantUtils.DELETE_FALSE);
							user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(username));
							user.setPinyinfull(PinyinUtil.getPinyinFull(username));
							user.setCreateid(userid);
							user.setCreatetime(new Date());
							// boolean sameFlag = checkSameUser(user);
							userList.add(user);
							boolean sameFlag = checkSameMateUser(user, userStringList);
							if (sameFlag) {
								importResult = false;
								user.setIsnormal(0);
								user.setMsg("请勿重复导入配偶！");
								continue;
							} else {
								userAleardyListUpdate.add(userAl);
							}

							if (n > 1) {
								importResult = false;
								user.setMsg("当前用户存在多个同名同世系配偶！");
							} else {

							}

							userInfo = new Userinfo();
							userInfo.setUserid(userId);
							if (StringTools.trimNotEmpty(birthday)) {
								// userInfo.setBirthday(birthday);
								if (DateUtils.isDate(birthday, "-")) {
									userInfo.setBirthday(birthday);
									int year = Integer.parseInt(birthday.split("-")[0]);
									userInfo.setZodica(ZodiacUtil.date2Zodica(year));
									String[] arr = birthday.split("-");
									if (arr.length == 3) {
										userInfo.setConstellation(ZodiacUtil.date2Constellation(birthday));
									}
								}

							}

							userInfo.setEducation(education);

							userInfo.setBirthplace(birthplace);
							userInfo.setHomeplace(homeplace);
							userInfo.setNation(nation);
							userInfo.setBackground(background);
							userInfo.setRemark(remark);

							userInfoList.add(userInfo);
							// if (userAleardyMap.containsKey((int) genlevel + husbandname) &&
							// (StringTools.trimNotEmpty(userAleardyMap.get(genlevel +
							// husbandname).getMateid()))) {}
						}
					} else {
						userMates = new Usermates();
						if (userAleardyList != null && userAleardyList.size() > 0) {
							int n = 0;// 计数是否存在多个世系一样并且名字一样的用户
							for (int k = 0; k < userAleardyList.size(); k++) {
								User u = userAleardyList.get(k);
								if (u.getGenlevel() == Integer.parseInt(genlevel) && u.getUsername().equals(husbandname)
										&& StringTools.trimNotEmpty(u.getMateid())) {
									n++;
									// 新增userMates记录
									User userAl = u;
									userMates.setUserid(userAl.getUserid());
									userMates.setMateid(userId);
									userMates.setName(userAl.getUsername());
									if (sex.equals("男")) {
										userMates.setSex(1);
									} else {
										userMates.setSex(0);
									}
									userMates.setNation(nation);
									userMates.setBirthday(DateUtils.stringToDate(birthday));
									userMates.setBirthplace(birthplace);
									userMates.setRemark(remark);
									// 需要验证
									boolean sameFlag = checkSameMates(userMates);
									if (sameFlag) {
										continue;
									} else {
										userMatesList.add(userMates);
									}

								}
							}
						}
					}
				}
			}
			// 批量导入妻子或丈夫配偶 user
			if (userList != null && userList.size() > 0 && importResult) {
				userImportMapper.importUser(userList);
			}

			// 批量更新 已存在的用户
			if (userAleardyListUpdate != null && userAleardyListUpdate.size() > 0 && importResult) {
				userDao.updateAleardyUser(userAleardyListUpdate);
			}

			// 批量插入 配偶 表
			if (userMatesList != null && userMatesList.size() > 0 && importResult) {
				userMatesDao.insertMatesList(userMatesList);
			}
			// 批量插入info
			if (userInfoList != null && userInfoList.size() > 0 && importResult) {
				userInfoImportMapper.importUser(userInfoList);
			}
			// 如果符合导入条件的配偶信息条数不为零
			if (userMatesList.size() < 1 && userList.size() < 1) {
				String userMatesString = GsonUtil.GsonString(userMateStringList);
				result = new Result(MsgConstants.USER_NO_IMPORT);
				res = new JsonResponse(result);
				res.setData(userMatesString);
			} else {
				userMateStringList.add("本次共导入配偶" + userMatesList.size() + "人");
				String userMatesString = GsonUtil.GsonString(userMateStringList);
				if (importResult) {
					result = new Result(MsgConstants.RESUL_SUCCESS);
					res = new JsonResponse(result);
					res.setData(userList);
					res.setData1(userMatesString);
					res.setEntity(excelid);
				} else {
					result = new Result(MsgConstants.RESUL_FAIL);
					res = new JsonResponse(result);
					res.setData(userList);
					res.setData1(userMatesString);
				}
				//res.setEntity(userMatesString);
			}
			/*
			 * String msString=""; if(userStringList.size()>0){ for (String ms :
			 * userStringList) { msString+=ms+"\r\n,"; } }
			 * result.setMsg(msString+"本次共导入在世配偶(用户)" + userList.size() + "人,离世配偶" +
			 * userMatesList.size() + "人，未导入"+msg);
			 */
		}

		return res;
	}

	@Override
	public PageModel<User> selecUserListToReview(PageModel<User> pageModel, User user) throws Exception {
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		List<UserManager> managers = userContextService.getUserManagers(userid);
		UserManager manager = managers.get(0);
		List<String> branchids = userContextService.getBranchIds(familyid, userid);
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<User> userList = new ArrayList<>();
		if (manager.getEbtype() == 1) {
			userList = userDao.selecUserListToReview(user, null);
		} else {
			if (branchids.size() > 0) {
				userList = userDao.selecUserListToReview(user, branchids);
			}
		}
		pageModel.setList(userList);
		pageModel.setPageInfo(new PageInfo<User>(userList));
		return pageModel;
	}

	@Override
	public Result mergeMate(User user, Userinfo userInfo) throws Exception {
		Result result = null;
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			return result;
		}
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Integer matetype = user.getMatetype();
			// 丈夫或妻子 matetype 0丈夫;1妻子;2其他
			String userid = UUIDUtils.getUUID();
			String birthplace = userInfo.getBirthplaceP() + "@@" + userInfo.getBirthplaceC() + "@@"
					+ userInfo.getBirthplaceX();
			userInfo.setBirthplace(birthplace);
			User user1 = userDao.selectByPrimaryKey(user.getUserid());
			if (user1 == null) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户不存在！");
				return result;
			}
			if (matetype == 0 || matetype == 1) {
				if (StringTools.trimNotEmpty(user.getMateid())) {
					// 修改用户配偶信息
					User userMmateUpdate = new User();
					String phone = user.getPhone();
					userMmateUpdate.setUserid(user1.getMateid());
					userMmateUpdate.setBranchid(user1.getBranchid());
					userMmateUpdate.setBranchname(user1.getBranchname());
					userMmateUpdate.setGenlevel(user1.getGenlevel());
					userMmateUpdate.setMatename(user1.getUsername());
					userMmateUpdate.setPhone(phone);
					userMmateUpdate.setIsMarry(1);
					userMmateUpdate.setPid(user1.getPid());
					userMmateUpdate.setPname(user1.getPname());
					System.out.println(userMmateUpdate.getBranchname());
					if (StringTools.trimNotEmpty(phone)) {
						userMmateUpdate.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
					}
					userMmateUpdate.setUpdatetime(new Date());
					userMmateUpdate.setCreateid(userid);
					userMmateUpdate.setUsername(user.getMatename());
					userDao.updateByPrimaryKeySelective(userMmateUpdate);
					// 更新 userinfo
					userInfo.setUserid(user.getMateid());
					String birthday = userInfo.getBirthday();
					if (StringUtils.isNotBlank(birthday)) {
						//(农历日期范围19000101~20491229)
						int parseInt = Integer.parseInt(birthday.replace("-", ""));
						if (parseInt > 19000130 && parseInt < 20500101) {
							String solarToLunar = CalendarUtil.solarToLunar(birthday);
							userInfo.setLunarbirthday(solarToLunar);
						}
					}
					/*
					 * if (!userInfo.getBirthdayStr().equals("")) { // SimpleDateFormat sdfd = new
					 * SimpleDateFormat("yyyy-MM-dd"); //
					 * userInfo.setBirthday(sdfd.parse(userInfo.getBirthdayStr()));
					 * if(DateUtils.isDate(userInfo.getBirthdayStr(), "-"))
					 * userInfo.setBirthday(userInfo.getBirthdayStr()); }
					 */
					userInfoDao.updateByPrimaryKeySelective(userInfo);
				} else {
					// 新增用户配偶信息,修改配偶保存逻辑，配偶信息作为主用户存到user表里，jp_usermates单独的用户配偶表弃用
					boolean flag = checkFamilyUserNumber(1, familyid);
					if (flag == true) {
						String phone = user.getPhone();
						User userMmate = new User();
						userMmate.setUserid(userid);
						userMmate.setPhone(phone);
						userMmate.setCreatetime(new Date());
						if (StringTools.trimNotEmpty(phone)) {
							userMmate.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
						}
						userMmate.setFamilyid(familyid);
						userMmate.setFamilyname(user1.getFamilyname());
						userMmate.setCreateid(userid);
						userMmate.setCreatetime(new Date());
						userMmate.setBranchid(user1.getBranchid());
						userMmate.setBranchname(user1.getBranchname());
						userMmate.setGenlevel(user1.getGenlevel());
						userMmate.setMatename(user1.getUsername());
						userMmate.setStatus(0);
						userMmate.setIsdirect(0);
						userMmate.setMateid(user.getUserid());
						userMmate.setUsername(user1.getMatename());
						userMmate.setSex(user.getSex());
						userMmate.setDeleteflag(0);
						userMmate.setIsMarry(1);
						userMmate.setPid(user1.getPid());
						userMmate.setPname(user1.getPname());
						userMmate.setPinyinfirst(PinyinUtil.getPinYinFirstChar(user.getMatename()));
						userMmate.setPinyinfull(PinyinUtil.getPinyinFull(user.getMatename()));
						if (user.getLivestatus() != 0) {
							userMmate.setLivestatus(user.getLivestatus());
							userMmate.setFixplace(user.getFixplace());
							/*
							 * if (!user.getDietimeStr().equals("")) { // SimpleDateFormat sdfd = new
							 * SimpleDateFormat("yyyy-MM-dd"); //
							 * userMmate.setDietime(sdfd.parse(user.getDietimeStr()));
							 * if(DateUtils.isDate(user.getDietimeStr(), "-"))
							 * user.setDietime(user.getDietimeStr()); }
							 */
						} else {
							userMmate.setLivestatus(user.getLivestatus());
						}

						boolean sameFlag = checkSameUser(userMmate);
						if (sameFlag) {
							// 重复什么也不做
							result = new Result(MsgConstants.USER_SAVE_HAVEREPEAT);
						} else {
							userDao.insertSelective(userMmate);
							user1.setIsMarry(1);
							user1.setMateid(userid);
							user1.setMatename(user.getMatename());
							user1.setMatetypeStr(user.getMatetypeStr());
							userDao.updateByPrimaryKeySelective(user1);
							// userinfo 表
							userInfo.setUserid(userid);
							String birthday = userInfo.getBirthday();
							if (StringUtils.isNotBlank(birthday)) {
								//(农历日期范围19000101~20491229)
								int parseInt = Integer.parseInt(birthday.replace("-", ""));
								if (parseInt > 19000130 && parseInt < 20500101) {
									String solarToLunar = CalendarUtil.solarToLunar(birthday);
									userInfo.setLunarbirthday(solarToLunar);
								}
							}
							/*
							 * if (!userInfo.getBirthdayStr().equals("")) { // SimpleDateFormat sdfd = new
							 * SimpleDateFormat("yyy-MM-dd"); //
							 * userInfo.setBirthday(sdfd.parse(userInfo.getBirthdayStr()));
							 * if(DateUtils.isDate(userInfo.getBirthdayStr(), "-"))
							 * userInfo.setBirthday(userInfo.getBirthdayStr()); }
							 */
							userInfoDao.insertSelective(userInfo);
							result = new Result(MsgConstants.RESUL_SUCCESS);
						}
					} else {
						result = new Result(MsgConstants.USER_SAVE_OUTMAX);
						return result;
					}
				}
			} else {// 配偶做为记录
				// 修改配偶保存逻辑，配偶信息作为主用户存到user表里，jp_usermates单独的用户配偶表弃用

				User userMates = new User();
				userMates.setUserid(userid);
				userMates.setMateid(user.getUserid());
				userMates.setCreatetime(new Date());
				userMates.setFamilyid(familyid);
				userMates.setFamilyname(user1.getFamilyname());
				userMates.setCreateid(userid);
				userMates.setCreatetime(new Date());
				userMates.setStatus(0);
				userMates.setIsdirect(0);
				userMates.setMateid(user.getUserid());
				userMates.setUsername(user.getMatename());
				userMates.setBranchid(user1.getBranchid());
				userMates.setBranchname(user1.getBranchname());
				userMates.setGenlevel(user1.getGenlevel());
				userMates.setMatename(user1.getUsername());
				userMates.setSex(user.getSex());
				userMates.setDeleteflag(0);
				userMates.setIsMarry(0);
				userMates.setPid(user1.getPid());
				userMates.setPname(user1.getPname());
				userMates.setPinyinfirst(PinyinUtil.getPinYinFirstChar(user.getMatename()));
				userMates.setPinyinfull(PinyinUtil.getPinyinFull(user.getMatename()));
				userMates.setLivestatus(user.getLivestatus());

				boolean sameFlag = checkSameUser(userMates);
				if (sameFlag) {
					// 重复什么也不做
					result = new Result(MsgConstants.USER_SAVE_HAVEREPEAT);
				} else {
					// userMatesDao.insertSelective(userMates);
					userDao.insertSelective(userMates);
					userInfo.setUserid(userid);
					String birthday = userInfo.getBirthday();
					if (StringUtils.isNotBlank(birthday)) {
						//(农历日期范围19000101~20491229)
						int parseInt = Integer.parseInt(birthday.replace("-", ""));
						if (parseInt > 19000130 && parseInt < 20500101) {
							String solarToLunar = CalendarUtil.solarToLunar(birthday);
							userInfo.setLunarbirthday(solarToLunar);
						}
					}
					userInfoDao.insertSelective(userInfo);
					//修改配偶信息及婚配状态
					user.setIsMarry(0);
					userDao.updateByPrimaryKeySelective(user);
					result = new Result(MsgConstants.RESUL_SUCCESS);
				}
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<User> login(String phone, String password) throws Exception {
		return userDao.login(phone, password);
	}

	@Override
	public JsonResponse validatePhone(String familyid, String userid, String phone) {
		Result result = null;
		JsonResponse res = null;
		try {
			List<User> userList = userDao.validatePhone(familyid, userid, phone);
			if (userList != null && userList.size() < 2) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			} else {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("一个手机号最多创建两个家族！");
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[validatePhone方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}

	}

	@Override
	public JsonResponse mergeUserAlbum(Useralbum userAlbum) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		String ablumId = UUIDUtils.getUUID();
		try {
			if (userAlbum.getUserid() == null || "".equals(userAlbum.getUserid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数userid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (userAlbum.getType() == null || "".equals(userAlbum.getType() + "")) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数type不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (userAlbum.getRemark() == null || "".equals(userAlbum.getRemark())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数remark不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			//当前登录人 userid
			String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
			if (StringTools.isEmpty(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户非法！");
				res = new JsonResponse(result);
				return res;
			}
			if (StringTools.trimNotEmpty(userAlbum.getAlbumid())) {
				userAlbum.setUpdatetime(new Date());
				userAlbum.setUpdateid(userid);
				status = userAlbumDao.updateByPrimaryKeySelective(userAlbum);
			} else {
				userAlbum.setAlbumid(ablumId);
				userAlbum.setCreatetime(new Date());
				userAlbum.setCreateid(userid);
				// 0未删除
				userAlbum.setDeleteflag(0);
				// userAlbum.setType(b);
				status = userAlbumDao.insertSelective(userAlbum);
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(ablumId);
				return res;
			}
		} catch (Exception e) {
			log_.error("[mergeUserAlbum方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse savePhoto(List<Userphoto> userPhotoList) {
		Result result = null;
		JsonResponse res = null;
		try {
			for (Userphoto userphoto : userPhotoList) {
				if (userphoto.getImgid() == null || "".equals(userphoto.getImgid())) {
					userphoto.setImgid(UUIDUtils.getUUID());
				}
				if (userphoto.getUserid() == null || "".equals(userphoto.getUserid())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数userid不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				if (userphoto.getAlbumid() == null || "".equals(userphoto.getAlbumid())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数albumid不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				if (userphoto.getSmallimgurl() == null || "".equals(userphoto.getSmallimgurl())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数smallimgurl不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				if (userphoto.getImgurl() == null || "".equals(userphoto.getImgurl())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数imgurl不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				if (userphoto.getDescription() == null || "".equals(userphoto.getDescription())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数description不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				//当前登录人 userid
				String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
				if (StringTools.isEmpty(userid)) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("用户非法！");
					res = new JsonResponse(result);
					return res;
				}
				userphoto.setCreatetime(new Date());
				userphoto.setCreateid(userid);
				userphoto.setDeleteflag(0);
			}
			int status = userPtotoDao.insertUserPhoto(userPhotoList);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[savePhoto方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse selectUseralbum(String userid, int type) {
		Result result = null;
		JsonResponse res = null;
		List<Useralbum> useralbums = null;
		try {
			if (userid == null || "".equals(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数userid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			useralbums = userAlbumDao.selectUseralbum(userid, type);
		} catch (Exception e) {
			log_.error("[selectUseralbum方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(useralbums);
		return res;
	}

	@Override
	public List<User> validatePhone(User user) throws Exception {
		return userDao.validatePhoneAddUser(user);
	}

	@Override
	public List<User> selectUserItemLive(User user) {
		return userDao.selectUserItemLive(user);
	}

	@Override
	public List<User> selectAllUser(User user) {
		UserQuery query = new UserQuery();
		query.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		return userDao.selectByExample(query);
	}

	@Override
	public List<User> selectUserItem(User user) throws Exception {
		return userDao.selectUserItem(user);
	}

	@Override
	public boolean limitUserNumber(String familyid, Integer addNum) {
		SysFamily family = sysFamilyDao.selectFamilyVersionNum(familyid);
		Integer usercount = family.getUsercount();
		Integer aleardyUser = family.getAleardyUserCount();
		if (usercount - aleardyUser - addNum >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Result editPwd(String string2md5) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result.setMsg("用户非法！");
			return result;
		}
		User record = new User();
		record.setUserid(userid);
		record.setPassword(string2md5);
		int updateRt = userDao.updateByPrimaryKeySelective(record);
		if (updateRt > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			return result;
		}
		return result;
	}

	@Override
	public JsonResponse importUsersNew(MultipartFile file, HttpServletRequest request) {

		// String result = "";
		// Result result = new Result();
		Result result = null;
		JsonResponse res = null;
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		User user2 = userDao.selectByPrimaryKey(userid);
		if (file == null) {
			result = new Result(MsgConstants.USER_NO_FILE);
			res = new JsonResponse(result);
			return res;
		}
		List<User> userList = new ArrayList<User>();
		User user = null;
		List<Userinfo> userInfoList = new ArrayList<Userinfo>();
		Map<String, User> userMap = new LinkedHashMap<String, User>();
		// Map<String, User> userPhoneMap = new LinkedHashMap<String, User>();
		Userinfo userInfo = null;
		NumberFormat nf = new DecimalFormat("#");
		// 获取文件名称
		String name = file.getOriginalFilename();
		if (name.indexOf(".xlsx") != -1) {
			XSSFWorkbook wb = null;
			try {
				wb = new XSSFWorkbook(file.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 循环三sheet
			int numSheet = 0;
			// for (int numSheet = 0; numSheet < wb.getNumberOfSheets();
			// numSheet++) {
			XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
			// if (xssfSheet == null) {
			// continue;
			// }
			int totalRows = xssfSheet.getLastRowNum();
			boolean flag = checkFamilyUserNumber(totalRows - 1, familyid);
			if (flag == false) {
				result = new Result(MsgConstants.USER_SAVE_OUTMAX);
				res = new JsonResponse(result);
				return res;
			}
			// 读取Row,从第二行开始
			for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {

					String isdireect = xssfRow.getCell(0).getStringCellValue().trim();// 0:直系，1:配偶
					double genlevel = xssfRow.getCell(1).getNumericCellValue();// 世系
					String username = xssfRow.getCell(2).getStringCellValue().trim();// 姓名
					if (!StringTools.trimNotEmpty(username)) {
						continue;
					}
					String sex = xssfRow.getCell(3).getStringCellValue().trim();// 性别
					String liveStatus = xssfRow.getCell(4).getStringCellValue().trim();// 在世状态
					String iMarryied = xssfRow.getCell(5).getStringCellValue().trim();// 是否婚配
					String phone = xssfRow.getCell(6).getStringCellValue().trim();// 手机号
					if (StringTools.trimNotEmpty(phone)) {
						phone = nf.format(Double.parseDouble(phone));
					}
					String xssname = xssfRow.getCell(7).getStringCellValue().trim();// 父亲名称/配偶名称
					String brotherpos = xssfRow.getCell(8).getStringCellValue().trim();// 排行
					String matename = xssfRow.getCell(9).getStringCellValue().trim();// 配偶关系
					String usedname = xssfRow.getCell(10).getStringCellValue().trim();// 曾用名

					String nation = xssfRow.getCell(11).getStringCellValue().trim();// 民族
					String background = xssfRow.getCell(12).getStringCellValue().trim();// 背景
					String education = xssfRow.getCell(13).getStringCellValue().trim();// 学历
					if (education != null) {
						if ("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						} else if ("高中".equals(education) || "中专".equals(education)) {
							education = "高中(中专)";
						}

					}
					String idCard = xssfRow.getCell(14).getStringCellValue().trim();// 身份证
					if (StringTools.trimNotEmpty(idCard)) {
						idCard = nf.format(Double.parseDouble(idCard));
					}
					String birthday = xssfRow.getCell(15).getStringCellValue().trim();// 出生日期
					String birthplace = xssfRow.getCell(16).getStringCellValue().trim();// 出生地
					birthplace = replacePlace(birthplace);
					String homeplace = xssfRow.getCell(17).getStringCellValue().trim();// 常住地
					homeplace = replacePlace(homeplace);
					String dietime = xssfRow.getCell(18).getStringCellValue().trim();// 离世日期
					String fixplace = xssfRow.getCell(19).getStringCellValue().trim();// 葬于某地
					// birthplace = replacePlace(fixplace);
					String remark = xssfRow.getCell(20).getStringCellValue().trim();// 个人简介
					String userId = UUIDUtils.getUUID();
					user = new User();
					user.setUserid(userId);
					user.setFamilyid(familyid);
					user.setFamilyname(user2.getFamilyname());
					user.setStatus(0);
					if (!"".equals(isdireect) && "直系".equals(isdireect)) {
						user.setIsdirect(1);
					} else {
						user.setIsdirect(0);
					}
					user.setGenlevel((int) genlevel);
					user.setDietime(dietime);
					user.setUsername(username);
					user.setUsedname(usedname);
					user.setIdcard(idCard);
					user.setPhone(phone);
					if (StringTools.trimNotEmpty(phone)) {
						user.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
					}
					if (sex.equals("男")) {
						user.setSex(1);
					} else {
						user.setSex(0);
					}
					user.setBrotherpos(brotherpos);
					user.setPname(name);
					if (liveStatus.equals("在世")) {
						user.setLivestatus(0);
						// 去除在世用户中手机重复的数据
						// if (userPhoneMap.containsKey(user.getPhone())) {
						// continue;
						// } else {
						// userPhoneMap.put(user.getPhone(), user);
						// }
					} else {
						user.setLivestatus(1);
					}
					if (iMarryied.equals("已婚")) {
						user.setIsMarry(0);
					} else {
						user.setIsMarry(1);
					}
					user.setFixplace(fixplace);
					String key = ((int) genlevel - 1) + name;
					// if (userMap.containsKey(key)) {
					// User userTemp = userMap.get(key);
					// user.setPid(userTemp.getUserid());
					// userMap.put((int) genlevel + username, user);
					// } else {
					// userMap.put((int) genlevel + username, user);
					// }
					user.setCreateid(userid);
					user.setCreatetime(new Date());
					user.setUpdateid(userid);
					user.setUpdatetime(new Date());
					user.setDeleteflag(ConstantUtils.DELETE_FALSE);
					user.setFamilyname(user2.getFamilyname());
					user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(username));
					user.setPinyinfull(PinyinUtil.getPinyinFull(username));
					userInfo = new Userinfo();
					userInfo.setUserid(userId);
					if (StringTools.trimNotEmpty(birthday)) {
						userInfo.setBirthday(birthday);
					}
					userInfo.setBirthplace(birthplace);
					userInfo.setHomeplace(homeplace);
					userInfo.setNation(nation);
					userInfo.setBackground(background);
					userInfo.setRemark(remark);
					// boolean sameFlag = checkSameUser(user);
					// if (sameFlag) {
					// continue;
					// } else {
					// userList.add(user);
					// userInfoList.add(userInfo);
					// }
				}
			}
			if (userList != null && userList.size() > 0) {
				userDao.importUser(userList);
			}
			if (userInfoList != null && userInfoList.size() > 0) {
				userInfoDao.importUser(userInfoList);
			}
			// }
			try {
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			ExcelUtil eutil = null;
			HSSFWorkbook wb = null;
			HSSFSheet sheet = null;
			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(file.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sheet = wb.getSheetAt(0);
			eutil = new ExcelUtil(wb, sheet);
			// 获取上传的所有行数
			int lastRowNum = sheet.getLastRowNum();
			boolean flag = checkFamilyUserNumber(lastRowNum - 1, familyid);
			if (flag == false) {
				result = new Result(MsgConstants.USER_SAVE_OUTMAX);
				res = new JsonResponse(result);
				return res;
			}
			userStringList = new ArrayList<String>();
			for (int i = 1; i < lastRowNum + 1; i++) {
				if (sheet.getRow(i) != null) {
					System.out.println(sheet.getRow(i));
					String isdireect = eutil.toDecimalFormat(eutil.getCellContent(sheet.getRow(i).getCell(0)).trim());// 直系/配偶

					String genlevel = eutil.toDecimalFormat(eutil.getCellContent(sheet.getRow(i).getCell(1)).trim());// 世系
					String username = eutil.getCellContent(sheet.getRow(i).getCell(2)).trim();
					if (!StringTools.trimNotEmpty(username)) {
						continue;
					}
					String sex = eutil.getCellContent(sheet.getRow(i).getCell(3)).trim();
					String liveStatus = eutil.getCellContent(sheet.getRow(i).getCell(4)).trim();
					String iMarryied = eutil.getCellContent(sheet.getRow(i).getCell(5)).trim();
					String phone = eutil.getCellContent(sheet.getRow(i).getCell(6)).trim();
					if (StringTools.trimNotEmpty(phone)) {
						phone = nf.format(Double.parseDouble(phone));
					}
					String xssname = eutil.getCellContent(sheet.getRow(i).getCell(7)).trim();
					String brotherpos = eutil.getCellContent(sheet.getRow(i).getCell(8)).trim();
					String matename = eutil.getCellContent(sheet.getRow(i).getCell(9)).trim();
					String usedname = eutil.getCellContent(sheet.getRow(i).getCell(10)).trim();

					String nation = eutil.getCellContent(sheet.getRow(i).getCell(11)).trim();
					String background = eutil.getCellContent(sheet.getRow(i).getCell(12)).trim();
					String education = eutil.getCellContent(sheet.getRow(i).getCell(13)).trim();// 学历
					if (education != null) {
						if ("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						} else if ("高中".equals(education) || "中专".equals(education)) {
							education = "高中(中专)";
						}

					}
					String idCard = eutil.getCellContent(sheet.getRow(i).getCell(14)).trim();
					if (StringTools.trimNotEmpty(idCard)) {
						idCard = nf.format(Double.parseDouble(idCard));
					}
					String birthday = eutil.getCellContent(sheet.getRow(i).getCell(15)).trim();
					String birthplace = eutil.getCellContent(sheet.getRow(i).getCell(16)).trim();
					birthplace = replacePlace(birthplace);
					String homeplace = eutil.getCellContent(sheet.getRow(i).getCell(17)).trim();
					homeplace = replacePlace(homeplace);
					String dietime = eutil.getCellContent(sheet.getRow(i).getCell(18)).trim();
					String fixplace = eutil.getCellContent(sheet.getRow(i).getCell(19)).trim();
					// birthplace = replacePlace(fixplace);
					String remark = eutil.getCellContent(sheet.getRow(i).getCell(20)).trim();
					String userId = UUIDUtils.getUUID();
					user = new User();
					user.setUserid(userId);
					user.setCreateid(userid);
					user.setCreatetime(new Date());
					user.setFamilyid(familyid);
					user.setFamilyname(user2.getFamilyname());
					user.setStatus(0);
					user.setIsdirect(1);
					user.setGenlevel(Integer.valueOf(genlevel));
					user.setUsername(username);

					user.setUsedname(usedname);
					user.setIdcard(idCard);
					user.setPhone(phone);
					user.setUpdateid(userid);
					user.setUpdatetime(new Date());
					user.setDeleteflag(ConstantUtils.DELETE_FALSE);
					user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(username));
					user.setPinyinfull(PinyinUtil.getPinyinFull(username));
					if (StringTools.trimNotEmpty(phone)) {
						user.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
					}
					if (sex.equals("男")) {
						user.setSex(1);
					} else {
						user.setSex(0);
					}
					user.setBrotherpos(brotherpos);
					user.setPname(name);
					if (liveStatus.equals("在世")) {
						user.setLivestatus(0);
						// 去除在世用户中手机重复的数据
						// if (userPhoneMap.containsKey(user.getPhone())) {
						// continue;
						// } else {
						// userPhoneMap.put(user.getPhone(), user);
						// }
					} else {
						user.setLivestatus(1);
					}
					if (iMarryied.equals("已婚")) {
						user.setIsMarry(0);
					} else {
						user.setIsMarry(1);
					}
					user.setFixplace(fixplace);
					String key = (Integer.valueOf(genlevel) - 1) + name;
					if (userMap.containsKey(key)) {
						User userTemp = userMap.get(key);
						user.setPid(userTemp.getUserid());
						userMap.put(genlevel + username, user);
					} else {
						userMap.put(genlevel + username, user);
					}
					if (StringTools.trimNotEmpty(dietime)) {
						// user.setDietime(formatter.parse(dietime));
						if (DateUtils.isDate(dietime, "-"))
							user.setDietime(dietime);
					}

					userInfo = new Userinfo();
					userInfo.setUserid(userId);
					if (StringTools.trimNotEmpty(birthday)) {
						// userInfo.setBirthday(formatter.parse(birthday));
						if (DateUtils.isDate(userInfo.getBirthdayStr(), "-"))
							userInfo.setBirthday(userInfo.getBirthdayStr());
					}
					userInfo.setBirthplace(birthplace);
					userInfo.setHomeplace(homeplace);
					userInfo.setNation(nation);
					userInfo.setBackground(background);
					userInfo.setRemark(remark);
					// 检查本次导入用户是否有同名同世系的用户
					userList.add(user);
					// boolean sameFlag = checkSameUser(user);
					// if (sameFlag) {
					// continue;
					// } else {
					// userList.add(user);
					// userInfoList.add(userInfo);
					// }
				}
			}
			if (userList != null && userList.size() > 0) {
				userDao.importUser(userList);
			}
			if (userInfoList != null && userInfoList.size() > 0) {
				userInfoDao.importUser(userInfoList);
			}

		}
		// result = "1";
		// result.setStatus(1);
		if (userList.size() < 1) {
			// result.setStatus(500);
			String userString = GsonUtil.GsonString(userStringList);
			// result.setData1(userString);
			result = new Result(MsgConstants.USER_NO_IMPORT);
			res = new JsonResponse(result);
			res.setData(userString);
		} else {
			userStringList.add("本次共导入用户" + userList.size() + "人");
			String userString = GsonUtil.GsonString(userStringList);
			// result.setData1(userString);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userString);
		}
		// result.setMsg("本次共导入用户" + userList.size() + "人");
		return res;

	}

	@Override
	public Integer del(String[] userids) {
		List<String> strs = Arrays.asList(userids);
		UserManagerExample example = new UserManagerExample();
		example.or().andUseridIn(strs);
		userManagerMapper.deleteByExample(example);
		return userDao.delUser(userids);
	}

	@Override
	public Result confirmImport(String excelid) {
		// Result result = new Result();
		Result result = null;
		if (excelid == null || "".equals(excelid)) {
			/*
			 * result.setStatus(ConstantUtils.RESULT_FAIL); result.setMsg("导入失败！");
			 */
			result = new Result(MsgConstants.RESUL_FAIL);
			return result;
		}
		// 1.查临时表的数据
		UserImportExample example = new UserImportExample();
		example.or().andExcelidEqualTo(excelid);
		List<User> users = userImportMapper.selectByExample(example);
		// 2.将临时表数据插入主表
		userDao.importUser(users);

		for (User u : users) {
			// if(u.getMateid()!= null || !"".equals(u.getMateid())) {
			// User uu = userImportMapper.selectByPrimaryKey(u.getMateid());
			// userDao.updateByPrimaryKey(uu);
			// }
			Userinfo info = userInfoImportMapper.selectByPrimaryKey(u.getUserid());
			if (info != null)
				userInfoDao.insertSelective(info);
		}

		/*
		 * result.setStatus(ConstantUtils.RESULT_SUCCESS); result.setMsg("导入成功");
		 */
		result = new Result(MsgConstants.RESUL_SUCCESS);
		return result;
	}

	public String replacePlace(String place) {
		if (place.indexOf("@@") == -1) {
			place = "@@@@@@" + place.trim();
		}
		return place;
	}

	public boolean checkFamilyUserNumber(int importCount, String familyid) {
		boolean checkResult = false;
		Integer priValue = 0; // 最多容纳家族人数
		SysVersionPrivilege versionP = sysVersionPrivilegeMapper.selectByVersionAndCode(familyid,
				ConstantUtils.VERSION_USERCOUNT);
		if (versionP != null && versionP.getPrivilegevalue() != null) {
			if (versionP.getPrivilegevalue().equals(ConstantUtils.VERSION_UNLIMITED)) {
				// 钻石豪华版容纳家族人数不限
				checkResult = true;
				return checkResult;
			}
			priValue = Integer.valueOf(versionP.getPrivilegevalue());
		}
		// 获取该用户所在家族已有的人数
		UserQuery userExample = new UserQuery();
		userExample.or().andFamilyidEqualTo(familyid);
		int haveUserCount = userDao.countByExample(userExample); // 家族已有人数
		if (priValue > 0 && (priValue - haveUserCount - importCount) >= 0) {
			// 导入用户后不超过家族版本容纳人数限制
			checkResult = true;
		}
		return checkResult;
	}

	@Override
	public List<User> validatePhoneForApi(String familyid, String userid, String phone) {
		return userDao.validatePhone(familyid, userid, phone);
	}

	@Override
	public JsonResponse getAddressByUserid(User user) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			if (StringUtils.isBlank(user.getUserid())) {
				result.setMsg("用户userid为空！");
				res = new JsonResponse(result);
				return res;
			}
			user = userDao.selectByPrimaryKey(user.getUserid());
			Branch branch = new Branch();
			branch.setBranchid(user.getBranchid());
			branch.setFamilyid(familyid);
			branch = branchDao.selectByPrimaryKey(branch);
			user.setBranch(branch);

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(user);
		} catch (Exception e) {
			res = new JsonResponse(result);
			e.printStackTrace();
			return res;
		}
		return res;
	}

	public JsonResponse checkCode(Usercode usercode) {
		Result result = null;
		JsonResponse res = null;
		// 检验接口参数
		if (usercode.getPhone() == null || "".equals(usercode.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写手机号！");
			res = new JsonResponse(result);
			return res;
		}
		if (!ValidatorUtil.isMobile(usercode.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("手机号格式不正确！");
			res = new JsonResponse(result);
			return res;
		}
		if (usercode.getSmscode() == null || "".equals(usercode.getSmscode())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写验证码！");
			res = new JsonResponse(result);
			return res;
		}
		// 通过手机号查询当前用户信息
		// UserExample phoneExample = new UserExample();
		// phoneExample.or().andPhoneEqualTo(usercode.getPhone())
		// .andDeleteflagEqualTo((byte)0);
		// List<User> selectByExample = userMapper.selectByExample(phoneExample);
		// if (selectByExample == null || selectByExample.isEmpty()) {
		// result.setStatus(Constants.RESULT_FAIL);
		// result.setMsg("当前手机号用户不存在，请重设后再试！");
		// return result;
		// }

		// 查询数据库该手机号对应的最新的验证码
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", usercode.getPhone());
		Usercode dbusercode = usercodeDao.selectByPhone(params);
		if (dbusercode == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("该手机号没有获取验证码，请获取后再试！");
			res = new JsonResponse(result);
			return res;
		}
		long validtime = 180000l;
		boolean betweenTimeLessThanSetTime = DateUtil.isBetweenTimeLessThanSetTime(dbusercode.getCreatetime().getTime(),
				new Date().getTime(), validtime);
		// //查询数据库该手机号验证码对应有效期
		// UsercodeExample usercodeCheckExample=new UsercodeExample();
		// usercodeCheckExample.or().andPhoneEqualTo(usercode.getPhone())
		// .andCreatetimeBetween(dbusercode.getCreatetime(),
		// DateUtil.calculateTimeForMinute(dbusercode.getCreatetime(), 3));
		// usercodeCheckExample.setOrderByClause("createtime desc");
		// List<Usercode> usercodeList =
		// usercodeMapper.selectByExample(usercodeCheckExample);
		// Usercode checkcodenow=usercodeList.get(0);
		if (!betweenTimeLessThanSetTime) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("验证码已失效，请重新获取后再试！");
			res = new JsonResponse(result);
			return res;
		} else if (dbusercode.getSmscode().equals(usercode.getSmscode())) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("验证通过！");
			res = new JsonResponse(result);
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("验证码错误，请重新输入！");
			res = new JsonResponse(result);
		}
		return res;
	}

	private JsonResponse checkOpenId(User entity) {
		Result result = null;
		JsonResponse res = null;
		List<User> queryByOpenIdUserList = userDao.selectByOpenId(entity);
		// 判断账号是否存在
		if (queryByOpenIdUserList == null || queryByOpenIdUserList.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户不存在或未绑定手机号码！");
			res = new JsonResponse(result);
			return res;
		}
		// 绑定了手机号码
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(queryByOpenIdUserList);
		return res;
	}

	@Override
	public JsonResponse checkCodeForMT(Usercode usercode) {
		Result result = null;
		JsonResponse res = null;
		// 检验接口参数
		if (usercode.getPhone() == null || "".equals(usercode.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写手机号！");
			res = new JsonResponse(result);
			return res;
		}
		if (!ValidatorUtil.isMobile(usercode.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("手机号格式不正确！");
			res = new JsonResponse(result);
			return res;
		}
		if (usercode.getSmscode() == null || "".equals(usercode.getSmscode())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写验证码！");
			res = new JsonResponse(result);
			return res;
		}
		// 通过手机号查询当前用户信息
		UserQuery phoneExample = new UserQuery();
		Map<String, String> userparams = new HashMap<String, String>();
		userparams.put("familyid", null);
		userparams.put("phone", usercode.getPhone());
		List<User> selectByExample = userDao.selectByFamilyId(userparams);
		if (selectByExample == null || selectByExample.isEmpty()) {
			phoneExample.clear();
			phoneExample.or().andPhoneEqualTo(usercode.getPhone()).andStatusNotEqualTo(0).andDeleteflagEqualTo(0);
			selectByExample = userDao.selectByExample(phoneExample);

			if (selectByExample == null || selectByExample.isEmpty()) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("当前手机号用户不存在，请重设后再试！");
				res = new JsonResponse(result);
				return res;
			} else {
				result = new Result(MsgConstants.LOGIN_USER_STATUS);
				res = new JsonResponse(result);
				res.setData(selectByExample);
				return res;
			}
		}

		// 查询数据库该手机号对应的最新的验证码
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", usercode.getPhone());
		Usercode dbusercode = usercodeDao.selectByPhone(params);
		if (dbusercode == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("该手机号没有获取验证码，请获取后再试！");
			res = new JsonResponse(result);
			return res;
		}
		long validtime = 180000l;
		boolean betweenTimeLessThanSetTime = DateUtil.isBetweenTimeLessThanSetTime(dbusercode.getCreatetime().getTime(),
				new Date().getTime(), validtime);
		// //查询数据库该手机号验证码对应有效期
		// UsercodeExample usercodeCheckExample=new UsercodeExample();
		// usercodeCheckExample.or().andPhoneEqualTo(usercode.getPhone())
		// .andCreatetimeBetween(dbusercode.getCreatetime(),
		// DateUtil.calculateTimeForMinute(dbusercode.getCreatetime(), 3));
		// usercodeCheckExample.setOrderByClause("createtime desc");
		// List<Usercode> usercodeList =
		// usercodeMapper.selectByExample(usercodeCheckExample);
		// Usercode checkcodenow=usercodeList.get(0);
		if (!betweenTimeLessThanSetTime) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("验证码已失效，请重新获取后再试！");
			res = new JsonResponse(result);
			return res;
		} else if (dbusercode.getSmscode().equals(usercode.getSmscode())) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("验证通过！");
			res = new JsonResponse(result);
			res.setData(selectByExample);
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("验证码错误，请重新输入！");
			res = new JsonResponse(result);
		}
		return res;
	}

	/**
	 * 检查账号密码是否正确
	 * 
	 * @param entity
	 * @return
	 */
	private JsonResponse phoneAndPasswordCheck(User entity, Result result) {
		// // 查询账号密码是否正确
		// UserExample userExample = new UserExample();
		// UserExample.Criteria userexmCriteria = userExample.createCriteria();
		// // 手机号存在，状态为合法用户，且未删除、且在世
		// userexmCriteria.andPhoneEqualTo(entity.getPhone()).andDeleteflagEqualTo((byte)
		// 0)
		// .andStatusEqualTo((byte) 0).andLivestatusEqualTo((byte) 0);
		// if (entity.getFamilyid() != null && !entity.getFamilyid().isEmpty())
		// {
		// userexmCriteria.andFamilyidEqualTo(entity.getFamilyid());
		// }
		JsonResponse res = null;
		Map<String, String> userparams = new HashMap<String, String>();
		String family = ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) ? null : entity.getFamilyid();
		userparams.put("familyid", family);
		userparams.put("phone", entity.getPhone());
		List<User> queryByPhoneUserList = userDao.selectByFamilyId(userparams);
		// 判断账号是否存在
		if (queryByPhoneUserList == null || queryByPhoneUserList.size() == 0) {
			UserQuery phoneExample = new UserQuery();
			phoneExample.or().andPhoneEqualTo(entity.getPhone()).andStatusNotEqualTo(0).andDeleteflagEqualTo(0);
			phoneExample.setOrderByClause("logintime desc");
			queryByPhoneUserList = userDao.selectByExample(phoneExample);
			if (queryByPhoneUserList == null || queryByPhoneUserList.isEmpty()) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户不存在！");
				res = new JsonResponse(result);
				return res;
			} else {
				result = new Result(MsgConstants.LOGIN_USER_STATUS);
				res = new JsonResponse(result);
				res.setData(queryByPhoneUserList);
				return res;
			}
		}
		// 判断密码是否正确
		User anyUser = queryByPhoneUserList.get(0);
		String password = entity.getPassword();
		// 密码比对
		if (anyUser.getPassword() != null && !"".equals(anyUser.getPassword())) {
			if (!anyUser.getPassword().toLowerCase().equals(password.toLowerCase())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("密码错误！");
				res = new JsonResponse(result);
				return res;
			}
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("未设置登录密码，请使用其他方式登录！");
			res = new JsonResponse(result);
			return res;
		}

		// 账号密码都正确
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(queryByPhoneUserList);
		return res;
	}

	@Override
	public JsonResponse regist(User entity, String smscode) {
		Result result = null;
		JsonResponse res = null;
		if (!ValidatorUtil.isMobile(entity.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("手机号格式错误！");
			res = new JsonResponse(result);
			return res;
		}
		if (smscode != null && !"".equals(smscode)) {
			// 校验验证码
			Usercode usercode = new Usercode();
			usercode.setPhone(entity.getPhone());
			usercode.setSmscode(smscode);
			res = checkCode(usercode);
			if (res.getCode() == 1) {
				return res;
			}
		}
		// 通过手机号查询当前用户信息
		UserQuery phoneExample = new UserQuery();
		phoneExample.or().andPhoneEqualTo(entity.getPhone()).andDeleteflagEqualTo(0)
				.andStatusEqualTo(ConstantUtils.USER_STATUS_OK).andFamilyidIsNotNull();
		List<User> list = userDao.selectByExample(phoneExample);
		if (list != null && list.size() > 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("该手机号已经有家族，快去登录吧！");
			res = new JsonResponse(result);
			return res;
		}
		String token = UUIDUtils.getUUID();
		JedisUtil.saveString(entity.getPhone(), token);
		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put("token", token);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(tokenMap);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JsonResponse getOnlineUser(ServletContext servletContext) {
		Result result = null;
		JsonResponse res = null;
		List<OnLineUser> onlineUserList = (List<OnLineUser>) servletContext.getAttribute("onlineUsers");

		if (onlineUserList != null && onlineUserList.size() > 0) {
			for (int i = onlineUserList.size() - 1; i >= 0; i--) {
				OnLineUser onlineUser = onlineUserList.get(i);
				if (Long.parseLong(onlineUser.getTimeReduce()) > 20) {
					onlineUserList.remove(i);
				}
			}
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(onlineUserList);
		return res;
	}

	@Override
	public JsonResponse logout(ServletContext application, User entity) {
		Result result = null;
		JsonResponse res = null;

		List<OnLineUser> onlineUsers = (List<OnLineUser>) application.getAttribute("onlineUsers");
		onlineUsers.remove(new OnLineUser(entity.getUserid()));
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse login(HttpServletRequest req, User entity, String loginType, String internetType,
			String version, String smscode) {
		Result result = null;
		JsonResponse res = null;
		// 参数校验
		if (entity.getOpenid() != null && !"".equals(entity.getOpenid())) {
			res = checkOpenId(entity);
		} else {
			if (entity.getPhone() == null || entity.getPhone().isEmpty()) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("缺少关键参数phone！");
				res = new JsonResponse(result);
				return res;
			}
			if (smscode != null && !"".equals(smscode)) {
				// 校验验证码
				Usercode usercode = new Usercode();
				usercode.setPhone(entity.getPhone());
				usercode.setSmscode(smscode);
				res = checkCodeForMT(usercode);

			} else {

				if (entity.getPassword() == null || "".equals(entity.getPassword())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("缺少关键参数password！");
					res = new JsonResponse(result);
					return res;
				}
				// 检查账号和密码是否正确
				res = phoneAndPasswordCheck(entity, result);
			}
		}

		if (res.getCode() == 1) {
			return res;
		}
		@SuppressWarnings("unchecked")
		List<User> dbuserList = (List<User>) res.getData();
		if (res.getCode() == 1009) {
			for (User user : dbuserList) {
				if (user.getStatus() == 1) {
					result = new Result(MsgConstants.LOGIN_USER_STATUS_CHECK);
					res = new JsonResponse(result);
					return res;
				}
				if (user.getStatus() == 2) {
					result = new Result(MsgConstants.LOGIN_USER_STATUS_REPULSE);
					res = new JsonResponse(result);
					return res;
				} else {
					result = new Result(MsgConstants.LOGIN_USER_STATUS_STOP);
					res = new JsonResponse(result);
					return res;
				}
			}
		}
		////////////////////////// 添加api登录的app用户
		ServletContext application = req.getServletContext();
		@SuppressWarnings("unchecked")
		List<OnLineUser> onlineUsers = (List<OnLineUser>) application.getAttribute("onlineUsers");
		// 第一次使用前，需要初始化
		if (onlineUsers == null) {
			onlineUsers = new ArrayList<OnLineUser>();
			application.setAttribute("onlineUsers", onlineUsers);
		}
		// 先删除之前存在的账户，再加入用户
		if (dbuserList != null && dbuserList.size() > 0) {
			User user = dbuserList.get(0);
			OnLineUser onLineUser = setLineMsg(req, user, loginType, internetType, version);
			onlineUsers.remove(new OnLineUser(user.getUserid()));
			onlineUsers.add(onLineUser);
		}
		///////////////////////////////////
		// 单企业用户登录或多企业用户登录获取企业列表
		return singleCorpLoginOrMultiCorpGetList(dbuserList, entity.getSessionid());
	}

	/**
	 * 单企业用户登录或多企业用户登录获取企业列表
	 * 
	 * @param entity
	 * @return
	 */
	private JsonResponse singleCorpLoginOrMultiCorpGetList(List<User> userList, String sessionid) {
		Result result = null;
		JsonResponse res = null;
		if (userList.size() == 1) {
			// 单企业用户登录,查询完APP权限后直接返回
			User user = userList.get(0);
			if (user.getFamilystatus() != null && user.getFamilystatus() == 1) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("该家族已被停用！");
				res = new JsonResponse(result);
				return res;
			}
			LoginThirdExample example = new LoginThirdExample();
			example.or().andUseridEqualTo(user.getUserid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			user.setThirds(loginThirdMapper.selectByExample(example));
			boolean flag = user.getPassword()
					.equals(MD5Util.string2MD5(user.getPhone().substring(user.getPhone().length() - 6)));
			if (user.getPassword() == null || "".equals(user.getPassword()) || flag) {
				user.setIsSetPwd(1);
			} else {
				user.setIsSetPwd(0);
			}
			return queryUserAppLimit(user, sessionid);
			// result.setData(userList);
		} else {
			// 多企业用户,查询选择的企业列表然后返回
			List<SysFamily> SysFamilyList = new ArrayList<SysFamily>();
			for (User user : userList) {
				if (user.getFamilyid() != null && !"".equals(user.getFamilyid())) {
					SysFamily dbCorp = sysFamilyDao.selectByPrimaryKey(user.getFamilyid());
					if (dbCorp != null) {
						// 返回只返回企业id和企业名称
						SysFamily retCorp = new SysFamily();
						retCorp.setFamilyid(dbCorp.getFamilyid());
						retCorp.setFamilyname(dbCorp.getFamilyname());
						SysFamilyList.add(retCorp);
					}
				}

			}
			UserAppLimit userAppLimit = new UserAppLimit();
			userAppLimit.setLoginStatus(UserAppLimit.LoginStatus.CHOOSE_CORP.getLoginStatusName());
			// 组装返回数据
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("账号验证通过，请选择家族");
			res = new JsonResponse(result);
			User user = userList.get(0);
			user.setUserAppLimit(userAppLimit);
			user.setFamilys(SysFamilyList);
			res.setData(user);
			return res;
		}
	}

	/**
	 * 查询用户APP权限
	 * 
	 * @param user
	 * @return
	 */
	private JsonResponse queryUserAppLimit(User user, String sessionid) {
		Result result = null;
		JsonResponse res = null;
		// 声明创建APP权限对象
		UserAppLimit userAppLimit = new UserAppLimit();
		List<SysFamily> familys = new ArrayList<SysFamily>();
		if (user.getFamilyid() != null && !"".equals(user.getFamilyid())) {
			SysFamily userCorp = sysFamilyDao.selectByPrimaryKey(user.getFamilyid());
			familys.add(userCorp);
			boolean isCorpCreator = user.getUserid().equals(userCorp.getCreateid());
			if (isCorpCreator) {
				userAppLimit.setRole(UserAppLimit.Role.CORP_CREATOR.getRolename());
			} else {
				userAppLimit.setRole(UserAppLimit.Role.GENERAL_USER.getRolename());
			}
			user.setFamilyname(userCorp.getFamilyname());
		}
		user.setFamilys(familys);
		// 查询是否为企业创建者

		// 重置sessionid到数据库
		User updateSessionisUser = new User();
		updateSessionisUser.setUserid(user.getUserid());
		updateSessionisUser.setSessionid(sessionid);
		// 记录登陆时间
		updateSessionisUser.setLogintime(new Date());
		userDao.updateByPrimaryKeySelective(updateSessionisUser);
		// 再保存到常量类的SESSION_MAP里
		ConstantUtils.SESSION_MAP.put(user.getUserid(), sessionid);
		// 再设置到返回对象里
		user.setSessionid(sessionid);
		// 设置返回用户的公司名称

		if (user.getPassword() == null || "".equals(user.getPassword())) {
			user.setResetpwd(1);
		} else {
			user.setResetpwd(0);
		}

		// 返回对象屏蔽密码
		user.setPassword(null);
		// 登录成功,返回数据
		BranchKey key = new BranchKey();
		key.setFamilyid(user.getFamilyid());
		key.setBranchid(user.getBranchid());
		Branch value = branchDao.selectByPrimaryKey(key);
		if (value != null) // 自助注册管理员登录，未分配任何信息的情况
		{
			user.setCityname(value.getCityname());
			user.setCitycode(value.getCitycode());
		}
		userAppLimit.setLoginStatus(UserAppLimit.LoginStatus.LOGIN_SUCCESS.getLoginStatusName());
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("登录成功");
		res = new JsonResponse(result);
		user.setUserAppLimit(userAppLimit);
		res.setData(user);
		return res;
	}

	/*
	 * 获取用户登录信息
	 */
	public OnLineUser setLineMsg(HttpServletRequest request, User user, String loginType, String internetType,
			String version) {
		OnLineUser onLineUser = new OnLineUser();
		onLineUser.setLoginUserId(user.getUserid());
		onLineUser.setLoginUserName(user.getUsername());
		// onLineUser.setLoginUserDept(user.getDeptname());
		onLineUser.setFamilyid(user.getFamilyid());
		onLineUser.setLoginTime(new Date());
		try {
			onLineUser.setLoginIp(getRemortIP(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
		onLineUser.setLoginType(loginType);
		onLineUser.setInternetType(internetType);
		onLineUser.setVersion(version);
		return onLineUser;
	}

	public final static String getRemortIP(HttpServletRequest request) throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");

			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	@Override
	public JsonResponse sendSMSCode(User entity) {
		Result result = null;
		JsonResponse res = null;
		// 检验接口参数
		if (entity.getPhone() == null || "".equals(entity.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写手机号！");
			res = new JsonResponse(result);
			return res;
		}
		if (!ValidatorUtil.isMobile(entity.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("手机号格式不正确！");
			res = new JsonResponse(result);
			return res;
		}
		String phone = entity.getPhone();
		if ("18647740001".equals(phone)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不可编辑！");
			res = new JsonResponse(result);
			return res;
		}
		UserQuery userExample = new UserQuery();
		userExample.or().andPhoneEqualTo(phone);
		// userExample.setOrderByClause("createtime desc");
		List<User> users = userDao.selectByExample(userExample);
		if (users.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不存在该用戶，请检查重试！");
			res = new JsonResponse(result);
			return res;
		}

		UsercodeQuery userCodeExample = new UsercodeQuery();
		userCodeExample.or().andPhoneEqualTo(phone).andCreatetimeGreaterThan(DateUtil.getOneDayStratTime(new Date()));
		userCodeExample.setOrderByClause("createtime desc");
		List<Usercode> userCodes = usercodeDao.selectByExample(userCodeExample);
		if (userCodes.size() > 10) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当天获取验证码已经达到10次，请明天再试！");
			res = new JsonResponse(result);
			return res;
		}
		long validtime = 180000l;
		boolean betweenTimeLessThanSetTime = false;
		if (userCodes.size() > 0) {
			betweenTimeLessThanSetTime = DateUtil.isBetweenTimeLessThanSetTime(
					userCodes.get(0).getCreatetime().getTime(), new Date().getTime(), validtime);
			// 距离上次获取验证码超过3分钟才发送新的验证码
			if (!betweenTimeLessThanSetTime) {
				res = sendsms(phone);
			} else {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("不要频繁发送验证码，请稍后重试！");
				res = new JsonResponse(result);
				return res;
			}
		} else {
			res = sendsms(phone);
		}

		return res;
	}

	public JsonResponse sendsms(String phone) {
		Result result = null;
		JsonResponse res = null;
		int smscode = new Random().nextInt(900000);
		smscode = smscode + 100000;
		Usercode userCode = new Usercode();
		userCode.setId(UUIDUtils.getUUID());
		userCode.setPhone(phone);
		userCode.setSmscode(String.valueOf(smscode));
		userCode.setType(1);
		userCode.setCreatetime(new Date());
		usercodeDao.insertSelective(userCode);
		int issend = EmaySend.sendSMS(phone, "您此次授权认证的验证码为：" + smscode + "，有效期为3分钟！");
		if (issend == 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("发送成功！");
			res = new JsonResponse(result);
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("发送失败！");
			res = new JsonResponse(result);
		}
		return res;
	}

	@Override
	public JsonResponse sendSMSCodeForReg(User entity) {
		Result result = null;
		JsonResponse res = null;
		// 检验接口参数
		if (entity.getPhone() == null || "".equals(entity.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写手机号");
			res = new JsonResponse(result);
			return res;
		}
		if ("18647740001".equals(entity.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不可编辑！");
			res = new JsonResponse(result);
			return res;
		}
		if (!ValidatorUtil.isMobile(entity.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("手机号格式不正确");
			res = new JsonResponse(result);
			return res;
		}
		String phone = entity.getPhone();
		UserQuery userExample = new UserQuery();
		userExample.or().andPhoneEqualTo(phone).andDeleteflagEqualTo(0).andFamilyidIsNull();
		List<User> users = userDao.selectByExample(userExample);
		if (users != null && users.size() > 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("该手机号已经注册！");
			res = new JsonResponse(result);
			return res;
		}
		UsercodeQuery userCodeExample = new UsercodeQuery();
		userCodeExample.or().andPhoneEqualTo(phone).andCreatetimeGreaterThan(DateUtil.getOneDayStratTime(new Date()));
		userCodeExample.setOrderByClause("createtime desc");
		List<Usercode> userCodes = usercodeDao.selectByExample(userCodeExample);
		if (userCodes.size() > 10) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当天获取验证码已经达到10次，请明天再试！");
			res = new JsonResponse(result);
			return res;
		}
		long validtime = 180000l;
		boolean betweenTimeLessThanSetTime = false;
		if (userCodes.size() > 0) {
			betweenTimeLessThanSetTime = DateUtil.isBetweenTimeLessThanSetTime(
					userCodes.get(0).getCreatetime().getTime(), new Date().getTime(), validtime);
			// 距离上次获取验证码超过3分钟才发送新的验证码
			if (!betweenTimeLessThanSetTime) {
				res = sendsms(phone);
			} else {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("不要频繁发送验证码，请稍后重试！");
				res = new JsonResponse(result);
				return res;
			}
		} else {
			res = sendsms(phone);
		}
		return res;
	}

	@Override
	public JsonResponse loginWithCaptcha(User entity, String loginType, String internetType, String version,
			String smscode) {
		Result result = null;
		JsonResponse res = null;
		// 参数校验
		if (entity.getPhone() == null || entity.getPhone().isEmpty()) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少关键参数phone");
			res = new JsonResponse(result);
			return res;
		} else if (smscode == null || smscode.isEmpty()) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("未填写验证码");
			res = new JsonResponse(result);
			return res;
		}

		// 校验验证码
		Usercode usercode = new Usercode();
		usercode.setPhone(entity.getPhone());
		usercode.setSmscode(smscode);
		JsonResponse checkCodeForMT_Rt = checkCodeForMT(usercode);
		if (checkCodeForMT_Rt.getCode() == 1) {
			return checkCodeForMT_Rt;
		}
		// 查询对应用户信息
		UserQuery userExp = new UserQuery();
		userExp.or().andPhoneEqualTo(entity.getPhone());
		List<User> dbuserList = userDao.selectByExample(userExp);

		ServletContext application = WebUtil.getApplication();
		@SuppressWarnings("unchecked")
		List<OnLineUser> onlineUsers = (List<OnLineUser>) application.getAttribute("onlineUsers");
		// 第一次使用前，需要初始化
		if (onlineUsers == null) {
			onlineUsers = new ArrayList<OnLineUser>();
			application.setAttribute("onlineUsers", onlineUsers);
		}
		// 先删除之前存在的账户，再加入用户
		if (dbuserList != null && dbuserList.size() > 0) {
			User user = dbuserList.get(0);
			if (user.getStatus() != null && user.getStatus() == 1) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("当前用户不可用！");
				res = new JsonResponse(result);
				return res;
			}
			HttpServletRequest request = WebUtil.getRequest();
			OnLineUser onLineUser = setLineMsg(request, user, loginType, internetType, version);
			onlineUsers.remove(new OnLineUser(user.getUserid()));
			onlineUsers.add(onLineUser);
			application.setAttribute("onlineUsers", onlineUsers);

			// 记录登录账户的设备类型
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("phone", user.getPhone());
			// map.put("logintype", loginType);
			// map.put("companyid", user.getCompanyid());
			// map.put("phonetype", entity.getPhonetype());
			// map.put("systemversion", entity.getSystemversion());
			// map.put("appversion", version);
			// userMapper.updateLoginType(map);
		}
		///////////////////////////////////
		// 单企业用户登录或多企业用户登录获取企业列表
		return singleCorpLoginOrMultiCorpGetList(dbuserList, entity.getSessionid());
	}

	@Override
	public JsonResponse loginWithThirdParty(User entity) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getOpenid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数openid为空!");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getThirdType())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数第三方登录类型thirdType为空!");
			res = new JsonResponse(result);
			return res;
		}
		// 查询openid是否绑定手机号
		LoginThirdExample example = new LoginThirdExample();
		example.or().andOpenidEqualTo(entity.getOpenid()).andTypeEqualTo(entity.getThirdType())
				.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		List<LoginThird> list = loginThirdMapper.selectByExample(example);
		if (ValidatorUtil.listIsEmpty(list)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请先绑定用户！");
			res = new JsonResponse(result);
			return res;
		}
		// LoginThird third = list.get(0);
		List<User> dbuserList = new ArrayList<User>();
		for (LoginThird third : list) {
			User u = userDao.selectByPrimaryKey(third.getUserid());
			dbuserList.add(u);
		}

		ServletContext application = WebUtil.getApplication();
		@SuppressWarnings("unchecked")
		List<OnLineUser> onlineUsers = (List<OnLineUser>) application.getAttribute("onlineUsers");
		// 第一次使用前，需要初始化
		if (onlineUsers == null) {
			onlineUsers = new ArrayList<OnLineUser>();
			application.setAttribute("onlineUsers", onlineUsers);
		}
		// 先删除之前存在的账户，再加入用户
		if (dbuserList == null || dbuserList.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户不存在！");
			res = new JsonResponse(result);
			return res;
		} else {
			User user = dbuserList.get(0);
			if (user.getStatus() != null && user.getStatus() == 1) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("当前用户不可用！");
				res = new JsonResponse(result);
				return res;
			}
			HttpServletRequest request = WebUtil.getRequest();
			OnLineUser onLineUser = setLineMsg(request, user, WebUtil.getHeaderInfo(ConstantUtils.HEADER_LOGINTYPE),
					WebUtil.getHeaderInfo(ConstantUtils.HEADER_INTERNETTYPE),
					WebUtil.getHeaderInfo(ConstantUtils.HEADER_VERSION));
			onlineUsers.remove(new OnLineUser(user.getUserid()));
			onlineUsers.add(onLineUser);
			application.setAttribute("onlineUsers", onlineUsers);

			// 记录登录账户的设备类型
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("phone", user.getPhone());
			// map.put("logintype", loginType);
			// map.put("companyid", user.getCompanyid());
			// map.put("phonetype", entity.getPhonetype());
			// map.put("systemversion", entity.getSystemversion());
			// map.put("appversion", version);
			// userMapper.updateLoginType(map);
		}
		///////////////////////////////////
		// 单企业用户登录或多企业用户登录获取企业列表
		return singleCorpLoginOrMultiCorpGetList(dbuserList, entity.getSessionid());
	}

	@Override
	public JsonResponse bindingWithThirdParty(User entity, String smscode, Integer loginstatus) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (StringUtils.isBlank(entity.getOpenid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数openid为空");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getThirdType())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数第三方登录类型thirdType为空");
			res = new JsonResponse(result);
			return res;
		}
		if (loginstatus != null && loginstatus == 0) {
			if (StringUtils.isBlank(entity.getPhone())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数phone为空");
				res = new JsonResponse(result);
				return res;
			}
			if (StringUtils.isBlank(smscode)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("短信验证码为空");
				res = new JsonResponse(result);
				return res;
			}
		}

		// 查询openid是否绑定了用户
		LoginThirdExample example = new LoginThirdExample();
		example.or().andOpenidEqualTo(entity.getOpenid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		List<LoginThird> thirds = loginThirdMapper.selectByExample(example);
		if (thirds != null && thirds.size() > 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前第三方授权登录已经绑定，请更换后重新尝试！");
			res = new JsonResponse(result);
			return res;
		}
		if (loginstatus != null && loginstatus == 0) {
			// 验证验证码
			Usercode usercode = new Usercode();
			usercode.setPhone(entity.getPhone());
			usercode.setSmscode(smscode);
			JsonResponse checkCodeForMT_Rt = checkCodeForMT(usercode);
			if (checkCodeForMT_Rt.getCode() == 1) {
				return checkCodeForMT_Rt;
			}
		}
		List<User> users = null;
		if (loginstatus != null && loginstatus == 0) {
			users = userDao.selectByPhone(entity.getPhone());
		} else {
			User user = userDao.selectByPrimaryKey(WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID));
			users = userDao.selectByPhone(user.getPhone());
		}

		int status = 0;
		if (users != null && users.size() > 0) {
			for (User user : users) {
				LoginThird third = new LoginThird();
				third.setId(UUIDUtils.getUUID());
				third.setDeleteflag(ConstantUtils.DELETE_FALSE);
				third.setCreatetime(new Date());
				third.setUserid(user.getUserid());
				third.setOpenid(entity.getOpenid());
				third.setPhone(entity.getPhone());
				third.setNinckname(entity.getNickname());
				third.setType(entity.getThirdType());
				status = loginThirdMapper.insertSelective(third);
			}
		}
		if (status == 1) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("绑定成功！");
			res = new JsonResponse(result);
			return res;
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("绑定失败！");
			res = new JsonResponse(result);
			return res;
		}
	}

	@Override
	public JsonResponse relieveWithThirdParty(User entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (entity.getOpenid() == null || "".equals(entity.getOpenid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请求失败，openid为空！");
			res = new JsonResponse(result);
			return res;
		}
		User user = userDao.selectByPrimaryKey(demoUser.getData().toString());
		List<User> users = userDao.selectByPhone(user.getPhone());
		if (users.size() > 0) {
			for (User u : users) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("openid", entity.getOpenid());
				params.put("userid", u.getUserid());
				loginThirdMapper.relieveWithThirdParty(params);
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("解绑成功!");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse isBindingWithUser(User entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getOpenid() == null || "".equals(entity.getOpenid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数openid为空！");
			res = new JsonResponse(result);
			return res;
		}
		LoginThirdExample example = new LoginThirdExample();
		example.or().andOpenidEqualTo(entity.getOpenid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		List<LoginThird> list = loginThirdMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前第三方授权未绑定！");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		result.setMsg("当前第三方授权已绑定！");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse checkSMSCode(User user, String code) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(user.getPhone()) || user.getPhone() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写手机号码！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(code) || code == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写验证码！");
			res = new JsonResponse(result);
			return res;
		}
		UsercodeQuery userCodeExample = new UsercodeQuery();
		userCodeExample.or().andPhoneEqualTo(user.getPhone())
				.andCreatetimeGreaterThan(DateUtil.getOneDayStratTime(new Date()));
		userCodeExample.setOrderByClause("createtime desc");
		List<Usercode> userCodes = usercodeDao.selectByExample(userCodeExample);
		if (userCodes.size() > 0) {
			long validtime = 180000l;
			boolean betweenTimeLessThanSetTime = false;
			betweenTimeLessThanSetTime = DateUtil.isBetweenTimeLessThanSetTime(
					userCodes.get(0).getCreatetime().getTime(), new Date().getTime(), validtime);
			// 最新的验证码距离判断时间超过三分钟则提示验证码过期
			if (!betweenTimeLessThanSetTime) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("验证码已过期！");
				res = new JsonResponse(result);
				return res;
			}
			if (!code.equals(userCodes.get(0).getSmscode())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("验证码不正确！");
				res = new JsonResponse(result);
				return res;
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("验证成功！");
			res = new JsonResponse(result);
			return res;
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当天没有可验证的验证码！");
			res = new JsonResponse(result);
			return res;
		}
	}

	@Override
	public JsonResponse getPersonInfoLimit(User entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getUserid() == null || "".equals(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少用户ID参数");
			res = new JsonResponse(result);
			return res;
		}

		UserLimitVO userLimitVO = new UserLimitVO();
		User user = userDao.selectByPrimaryKey(entity.getUserid());
		// User user1 =
		// userMapper.selectByPrimaryKey(WebUtil.getHeaderInfo(Constants.HEADER_USERID));
		if (!hasRights(user)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不具备查看权限,请联系管理员!");
			res = new JsonResponse(result);
			return res;
		}
		userLimitVO.setName(user.getUsername());
		Integer isdirect = user.getIsdirect();
		Integer sex = user.getSex();
		//未知系
		if (user.getIsdirect() == null) {
			Userinfo userInfo = userInfoDao.selectByPrimaryKey(entity.getUserid());
			if (user.getLivestatus() != null) {
				userLimitVO.setLivestatus(user.getLivestatus());
			}
			if (user.getGenlevel() != null) {
				userLimitVO.setGenlevel(user.getGenlevel().toString());
			}
			if (userInfo != null) {
				String home = userInfo.getHomeplace();
				if (StringTools.trimNotEmpty(home)) {
					userLimitVO.setHomeplace(home.replace("@", "").replace("null", ""));
				}
			}
			userLimitVO.setImgurl(user.getImgurl());
			userLimitVO.setPostision(user.getBrotherpos());
		} else {
			// 直系用户
			if (user.getIsdirect() == 1) {
				User pUser = userDao.selectByPrimaryKey(user.getPid());
				Userinfo userInfo = userInfoDao.selectByPrimaryKey(entity.getUserid());
				if (user.getLivestatus() != null) {
					userLimitVO.setLivestatus(user.getLivestatus());
				}
				if (user.getGenlevel() != null) {
					userLimitVO.setGenlevel(user.getGenlevel().toString());
				}
				if (userInfo != null) {
					String home = userInfo.getHomeplace();
					if (StringTools.trimNotEmpty(home)) {
						userLimitVO.setHomeplace(home.replace("@", "").replace("null", ""));
					}
				}
				userLimitVO.setImgurl(user.getImgurl());
				if (pUser != null) {
					userLimitVO.setParentmatename(pUser.getMatename());
					userLimitVO.setParentname(pUser.getUsername());
				}
				userLimitVO.setPostision(user.getBrotherpos());
			}
			// 非直系用户（配偶用户）
			if (user.getIsdirect() == 0) {
				if (user.getLivestatus() != null) {
					userLimitVO.setLivestatus(user.getLivestatus());
				}
				if (user.getGenlevel() != null) {
					userLimitVO.setGenlevel(user.getGenlevel().toString());
				}
				userLimitVO.setImgurl(user.getImgurl());
				User usermate = userDao.selectByPrimaryKey(user.getMateid());
				if (usermate != null) {
					Userinfo userInfo = userInfoDao.selectByPrimaryKey(usermate.getUserid());
					if (userInfo != null) {
						String home = userInfo.getHomeplace();
						if (StringTools.trimNotEmpty(home)) {
							userLimitVO.setHomeplace(home.replace("@", "").replace("null", ""));
						}
					}
					// 设置直系用户暨配偶的名字
					userLimitVO.setMatename(usermate.getUsername());
					// 排行
					userLimitVO.setPostision(usermate.getBrotherpos());
				}
			}
		}
		BranchKey branchKey = new BranchKey();
		branchKey.setFamilyid(user.getFamilyid());
		branchKey.setBranchid(user.getBranchid());
		Branch branch = branchDao.selectByPrimaryKey(branchKey);
		if (branch != null)
			userLimitVO.setBranchname(
					branch.getArea() + branch.getCityname() + branch.getXname() + branch.getBranchname());
		userLimitVO.setIsdirect(isdirect);
		userLimitVO.setSex(sex);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("获取成功");
		res = new JsonResponse(result);
		res.setData(userLimitVO);
		return res;
	}

	/**
	 * 判断时候具备查看分支数据的权限
	 * 
	 * @param entity
	 * @return
	 */
	private boolean hasRights(User entity) {
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		// 查询是否具备当前分支查看权限
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("familyid", entity.getFamilyid());
		List<EditorialBoard> editorialBoards = userDao.selectManagerBranchidsByUserid(map);

		// 定义当前用户具备权限的分支列表
		Set<String> branchids = new HashSet<String>();
		for (EditorialBoard editorialBoard : editorialBoards) {
			if (editorialBoard.getCodetype().equals("0"))
				return true;
			else {
				if ("1".equals(editorialBoard.getCodetype())) // 0总1省2市3区4分支
				{
					List<String> areaBranchids = branchDao.selectBranchidsByAreacode(editorialBoard.getCode(),
							entity.getFamilyid());
					branchids.addAll(areaBranchids);
				}
				if ("2".equals(editorialBoard.getCodetype())) // 0总1省2市3区4分支
				{
					List<String> areaBranchids = branchDao.selectBranchidsByCitycode(editorialBoard.getCode(),
							entity.getFamilyid());
					branchids.addAll(areaBranchids);
				}
				if ("3".equals(editorialBoard.getCodetype())) // 0总1省2市3区4分支
				{
					List<String> areaBranchids = branchDao.selectBranchidsByQxcode(editorialBoard.getCode(),
							entity.getFamilyid());
					branchids.addAll(areaBranchids);
				}
				if ("4".equals(editorialBoard.getCodetype())) // 0总1省2市3区4分支
					branchids.add(editorialBoard.getCode());
			}
		}
		// 查询当前用户所属分支
		User user = userDao.selectByPrimaryKey(userid);
		if (entity.getBranchid() != null && !"".equals(entity.getBranchid())) {
			branchids.add(entity.getBranchid());
		}

		// 如果既不是总编委也不在管理范围内并且个人不在当前分支的分支列表，则认为没有权限,
		if (branchids.contains(user.getBranchid()))
			return true;
		else
			return false;
	}

	@Override
	public JsonResponse restPassword(User entity, String password, String newpassword) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getPhone()) || entity.getPhone() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("没有获取到手机号！");
			res = new JsonResponse(result);
			return res;
		}
		if ("18647740001".equals(entity.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不可编辑！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(password) || password == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写新密码！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(newpassword) || newpassword == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请填写新密码！");
			res = new JsonResponse(result);
			return res;
		}
		if (!password.equals(newpassword)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("新密码不一致，请重新输入！");
			res = new JsonResponse(result);
			return res;
		}
		UserQuery userExample = new UserQuery();
		userExample.or().andPhoneEqualTo(entity.getPhone());
		List<User> users = userDao.selectByExample(userExample);
		if (users.size() <= 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不存在的用户！");
			res = new JsonResponse(result);
			return res;
		}
		for (User user : users) {
			user.setPassword(MD5Util.string2MD5(password));
			user.setUpdatetime(new Date());
			userDao.updateByPrimaryKey(user);
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("密码重置成功！");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse getAllPersonInfos(User entity) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("家族信息不存在！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(entity.getStart() + "") || entity.getStart() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数start不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(entity.getCount() + "") || entity.getCount() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数count不能为空！");
			res = new JsonResponse(result);
			return res;
		}

		// 查询正式用户、未删除用户、在世用户组合查询的结果
		UserQuery userExample = new UserQuery();
		userExample.or().andFamilyidEqualTo(entity.getFamilyid()).andStatusEqualTo(0).andDeleteflagEqualTo(0)
				.andLivestatusEqualTo(0);
		userExample.setOrderByClause("pinyinfirst desc");
		// userExample.setStartRow((int) (entity.getStart() - 1) *
		// entity.getCount().intValue());
		userExample.setPageSize(entity.getCount().intValue());
		userExample.setStartRow(entity.getStart().intValue());// start是索引
		// 获取当前家族所有有效用户
		List<User> users = userDao.selectByExample(userExample);
		String lastDateTime = userDao.getLastUpdateDateTime(entity.getFamilyid());
		if (StringUtils.isBlank(lastDateTime))
			lastDateTime = (new Date()).toString();
		AddressBook addressBook = new AddressBook();
		addressBook.setUsersadd(users);
		addressBook.setUpdatetime(lastDateTime);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("获取成功！");
		res = new JsonResponse(result);
		res.setData(addressBook);
		return res;
	}

	@Override
	public JsonResponse updatePersonsByUpdatetime(User user) {
		Result result = null;
		JsonResponse res = null;
		if (user == null || user.getFamilyid() == null || user.getUpdatetime() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少参数");
			res = new JsonResponse(result);
			return res;
		}
		// 只获取在世的、可用的、未删除的用户，不区分直系非直系用户，新增的用户
		UserQuery userExampleadd = new UserQuery();
		userExampleadd.or().andUpdatetimeGreaterThan(user.getUpdatetime()).andFamilyidEqualTo(user.getFamilyid())
				.andStatusEqualTo(0).andDeleteflagEqualTo(0).andLivestatusEqualTo(0);

		List<User> usersAdd = userDao.selectByExample(userExampleadd);
		// 获取逻辑删除的数据，物理删除的不考虑
		UserQuery userExampledel = new UserQuery();
		userExampledel.or().andUpdatetimeGreaterThanOrEqualTo(user.getUpdatetime())
				.andFamilyidEqualTo(user.getFamilyid()).andDeleteflagEqualTo(1).andLivestatusEqualTo(0);

		List<User> usersDel = userDao.selectByExample(userExampledel);

		String lastDateTime = userDao.getLastUpdateDateTime(user.getFamilyid());
		if (StringUtils.isBlank(lastDateTime)) {
			lastDateTime = (new Date()).toString();
		}
		AddressBook addressBook = new AddressBook();
		addressBook.setUsersadd(usersAdd);
		addressBook.setUserdel(usersDel);
		addressBook.setUpdatetime(lastDateTime);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("查询成功");
		res = new JsonResponse(result);
		res.setData(addressBook);
		return res;
	}

	@Override
	public JsonResponse searchComplex(SearchComplex searchComplex) {
		Result result = null;
		JsonResponse res = null;
		if (searchComplex == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请选择搜索条件");
			res = new JsonResponse(result);
			return res;
		}
		// 执行搜索
		List<User> users = userDao.searchComplex(searchComplex);
		int count = userDao.searchComplexCount(searchComplex);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userList", users);
		map.put("count", count);
		//result.setData1(count);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(map);
		return res;
	}

	public static String ListtoString(List<String> sList) {
		String re = "";
		if (sList != null && sList.size() > 0) {
			for (int i = 0; i < sList.size(); i++) {
				String string = sList.get(i);
				if (i == 0) {
					re += '"' + string + '"';
				} else {
					re += ",\"" + string + '"';
				}
			}
		}
		return re;
	}

	@Override
	public JsonResponse updatePersonsByUpdatetimeExt(User entity) {
		// 1.创建结果集
		Result result = null;
		JsonResponse res = null;
		// 2.数据校验
		if (entity == null || entity.getFamilyid() == null || entity.getUpdatetime() == null
				|| entity.getUserid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少参数");
			res = new JsonResponse(result);
			return res;
		}
		// 获得id字符串
		String ids = entity.getUserid().trim();

		// 创建删除和修改集合
		List<String> useridList_del = new ArrayList<String>();
		List<String> useridList_update = new ArrayList<String>();
		List<User> userList_update = new ArrayList<User>();
		// 3.查询
		if (!"".equals(ids)) {// 判断传入的ids是否为空
			// 获取id集合
			String[] idArr = entity.getUserid().split(",");
			List<String> idList = Arrays.asList(idArr);
			// 不为空，查询比对
			// 3.1查询所有更新时间在传入时间之后的
			List<String> useridList_afterUpdatedtime = userDao.getUseridListOfAfterUpdatedtime(entity);
			// 3.2查询所有已存在用户ids
			List<String> useridList_company = userDao.getUseridListByFamilyid(entity.getFamilyid());
			// 3.3获取需要删除的id列表和需要更新的id列表
			// 1[1,2,3] 2[2,4] 3[2,3,4,5] 删除：[1] 修改[2,4]
			if (useridList_company != null && useridList_company.size() > 0) {
				for (String userid_company : useridList_company) {
					// 判断公司员工是否传入的用户id里
					if (!idList.contains(userid_company)) {
						// 不在，添加到更新集合中
						useridList_update.add(userid_company);
					}
				}
				for (String userid : idList) {
					// 判断传入的员工是否传入的公司id里
					if (!useridList_company.contains(userid)) {
						// 不在，添加到删除集合中
						useridList_del.add(userid);
					}
				}
				// 添加需要更新的id
				useridList_update.addAll(useridList_afterUpdatedtime);
				// Array去重
				HashSet<String> temp = new HashSet<String>();
				temp.addAll(useridList_update);
				useridList_update.clear();
				useridList_update.addAll(temp);
			} else {
				useridList_del = idList;
			}
		} else {
			// 为空，全部更新
			useridList_update = userDao.getUseridListByFamilyid(entity.getFamilyid());
		}

		// 3.4查询所需要更新的用户的信息
		if (useridList_update.size() > 0) {
			Map<String, String> map = new HashMap<String, String>();

			String useridList_update_String = ListtoString(useridList_update);
			map.put("userlist", useridList_update_String);
			map.put("familyid", entity.getFamilyid());
			userList_update = userDao.getUserListByUserids(map);
		}
		String lastDateTime = userDao.getLastUpdateDateTime(entity.getFamilyid());
		if (StringUtils.isBlank(lastDateTime))
			lastDateTime = (new Date()).toString();

		AddressBook addressBook = new AddressBook();
		addressBook.setUsersadd(userList_update);
		addressBook.setIds_del(useridList_del);
		addressBook.setUpdatetime(lastDateTime);
		// result.setData(userList_update);
		// result.setData1(useridList_del);
		// result.setData2(new Date());
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(addressBook);
		return res;
	}

	/**
	 * 通过调用getPersonInfosByUserid方法获取用户详细信息
	 */
	@Override
	public JsonResponse getPersonInfos(User entity) {
		return getPersonInfosByUserid(entity.getUserid());
	}

	/**
	 * 通过UserID唯一键值获取用户详细信息，独立出来以备其他调用
	 * 
	 * @param userid
	 * @return
	 */
	public JsonResponse getPersonInfosByUserid(String userid) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(userid) || userid == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid为空！");
			res = new JsonResponse(result);
			return res;
		}

		// 获取用户基本信息
		User user = userDao.selectByPrimaryKey(userid);
		if (user == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户不存在！");
			res = new JsonResponse(result);
			return res;
		}
		// User user1 =
		// userMapper.selectByPrimaryKey(WebUtil.getHeaderInfo(Constants.HEADER_USERID));
		if (!hasRights(user)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不具备查看权限,请联系管理员!");
			res = new JsonResponse(result);
			return res;
		}
		// 获取用户联系信息和其他信息
		Userinfo userInfo = userInfoDao.selectByPrimaryKey(userid);
		UserDetail userDetail = new UserDetail();
		if (userInfo != null) {
			userDetail.setUserid(user.getUserid());
			userDetail.setLivestatus(user.getLivestatus().byteValue());
			userDetail.setDietime(user.getDietime());
			userDetail.setFixplace(user.getFixplace());
			userDetail.setUsedname(user.getUsedname());
			userDetail.setBackground(userInfo.getBackground());
			userDetail.setBirthday(userInfo.getBirthday());
			if (userInfo.getBirthday() != null) {
				userDetail.setBirthdayStr(userInfo.getBirthday());
			}

			String birth = userInfo.getBirthplace();
			if (StringTools.trimNotEmpty(birth)) {
				userDetail.setBirthplace(birth.replace("@", "").replace("null", ""));
				String births[] = birth.split("@@");
				if (births.length == 0) {
					userDetail.setBirthplaceP("");
					userDetail.setBirthplaceC("");
					userDetail.setBirthplaceX("");
					userDetail.setBirthDetail("");
				} else if (births.length == 1) {
					userDetail.setBirthplaceP("null".equals(births[0]) ? "" : births[0]);
				} else if (births.length == 2) {
					userDetail.setBirthplaceP("null".equals(births[0]) ? "" : births[0]);
					userDetail.setBirthplaceC("null".equals(births[1]) ? "" : births[1]);
				} else if (births.length == 3) {
					userDetail.setBirthplaceP("null".equals(births[0]) ? "" : births[0]);
					userDetail.setBirthplaceC("null".equals(births[1]) ? "" : births[1]);
					userDetail.setBirthplaceX("null".equals(births[2]) ? "" : births[2]);
				} else if (births.length == 4) {
					userDetail.setBirthplaceP("null".equals(births[0]) ? "" : births[0]);
					userDetail.setBirthplaceC("null".equals(births[1]) ? "" : births[1]);
					userDetail.setBirthplaceX("null".equals(births[2]) ? "" : births[2]);
					userDetail.setBirthDetail("null".equals(births[3]) ? "" : births[3]);
				}
			} else {
				userDetail.setBirthplace("");
				userDetail.setBirthplaceP("");
				userDetail.setBirthplaceC("");
				userDetail.setBirthplaceX("");
				userDetail.setBirthDetail("");
			}

			String home = userInfo.getHomeplace();
			if (StringTools.trimNotEmpty(home)) {
				userDetail.setHomeplace(home.replace("@", "").replace("null", ""));
				String homes[] = home.split("@@");
				if (homes.length == 0) {
					userDetail.setHomeplaceP("");
					userDetail.setHomeplaceC("");
					userDetail.setHomeplaceX("");
					userDetail.setHomeDetail("");
				} else if (homes.length == 1) {
					userDetail.setHomeplaceP("null".equals(homes[0]) ? "" : homes[0]);
				} else if (homes.length == 2) {
					userDetail.setHomeplaceP("null".equals(homes[0]) ? "" : homes[0]);
					userDetail.setHomeplaceC("null".equals(homes[1]) ? "" : homes[1]);
				} else if (homes.length == 3) {
					userDetail.setHomeplaceP("null".equals(homes[0]) ? "" : homes[0]);
					userDetail.setHomeplaceC("null".equals(homes[1]) ? "" : homes[1]);
					userDetail.setHomeplaceX("null".equals(homes[2]) ? "" : homes[2]);
				} else if (homes.length == 4) {
					userDetail.setHomeplaceP("null".equals(homes[0]) ? "" : homes[0]);
					userDetail.setHomeplaceC("null".equals(homes[1]) ? "" : homes[1]);
					userDetail.setHomeplaceX("null".equals(homes[2]) ? "" : homes[2]);
					userDetail.setHomeDetail("null".equals(homes[3]) ? "" : homes[3]);
				}
			} else {
				userDetail.setHomeplace("");
				userDetail.setHomeplaceP("");
				userDetail.setHomeplaceC("");
				userDetail.setHomeplaceX("");
				userDetail.setHomeDetail("");
			}
			userDetail.setMail(userInfo.getMail());
			userDetail.setMailsee(userInfo.getMailsee());
			userDetail.setNation(userInfo.getNation());
			userDetail.setQq(userInfo.getQq());
			userDetail.setQqsee(userInfo.getQqsee());
			userDetail.setRemark(userInfo.getRemark());
			userDetail.setRemarksee(userInfo.getRemarksee());
			userDetail.setTel(userInfo.getTel());
			userDetail.setTelsee(userInfo.getTelsee());
			userDetail.setWeixin(userInfo.getWeixin());
			userDetail.setWxsee(userInfo.getWxsee());
		}
		// 获取用户教育信息
		UsereduQuery userEduExample = new UsereduQuery();
		userEduExample.or().andUseridEqualTo(userid);
		List<Useredu> userEdus = userEduDao.selectByExample(userEduExample);
		// 获取用户工作信息
		UserworkexpQuery userWorkexpExample = new UserworkexpQuery();
		userWorkexpExample.or().andUseridEqualTo(userid);
		List<Userworkexp> userWorkexps = userworkDao.selectByExample(userWorkexpExample);

		// // 获取用户群英录
		// UserContent userContent =
		// UserContentMapper.selectByPrimaryKey(userid);
		// // 获取用户相册
		// UserAlbumExample userAlbumExample = new UserAlbumExample();
		// userAlbumExample.or().andUseridEqualTo(userid);
		// List<UserAlbum> userAlbums =
		// UserAlbumMapper.selectByExample(userAlbumExample);

		// 汇总信息到VO
		UserVO userVO = new UserVO();
		userVO.setUser(user);
		// userVO.setUserAlbum(userAlbums);
		// userVO.setUserContent(userContent);
		userVO.setUserEdu(userEdus);
		userVO.setUserInfo(userDetail);
		userVO.setUserWorkexp(userWorkexps);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userVO);
		return res;
	}

	@Override
	public JsonResponse changeImgurl(User entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if ("".equals(entity.getImgurl()) || entity.getImgurl() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户imgurl不存在！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(entity.getUserid()) || entity.getUserid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户ID不存在！");
			res = new JsonResponse(result);
			return res;
		}
		User user = userDao.selectByPrimaryKey(entity.getUserid());
		user.setImgurl(entity.getImgurl());
		int status = userDao.updateByPrimaryKey(user);
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse changeUserinfos(User entity) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		JsonResponse demoUser = checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (entity.getUserid() == null || "".equals(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户ID不存在！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			// 用户表

			entity.setPinyinfull(PinyinUtil.getPinyinFull(entity.getUsedname()));
			status = userDao.updateByPrimaryKeySelective(entity);
			Userinfo userinfo = entity.getUserInfo();
			String birthplaceP = userinfo.getBirthplaceP() == null ? "" : userinfo.getBirthplaceP();
			String birthplaceC = userinfo.getBirthplaceC() == null ? "" : userinfo.getBirthplaceC();
			String birthplaceX = userinfo.getBirthplaceX() == null ? "" : userinfo.getBirthplaceX();
			String birthDetail = userinfo.getBirthDetail() == null ? "" : userinfo.getBirthDetail();
			// 出生地
			userinfo.setBirthplace(birthplaceP + "@@" + birthplaceC + "@@" + birthplaceX + "@@" + birthDetail);
			String homeplaceP = userinfo.getHomeplaceP() == null ? "" : userinfo.getHomeplaceP();
			String homeplaceC = userinfo.getHomeplaceC() == null ? "" : userinfo.getHomeplaceC();
			String homeplaceX = userinfo.getHomeplaceX() == null ? "" : userinfo.getHomeplaceX();
			String homeDetail = userinfo.getHomeDetail() == null ? "" : userinfo.getHomeDetail();
			// 常住地
			userinfo.setHomeplace(homeplaceP + "@@" + homeplaceC + "@@" + homeplaceX + "@@" + homeDetail);
			String birthday = userinfo.getBirthday();
			if (StringUtils.isNotBlank(birthday)) {
				//(农历日期范围19000101~20491229)
				int parseInt = Integer.parseInt(birthday.replace("-", ""));
				if (parseInt > 19000130 && parseInt < 20500101) {
					String solarToLunar = CalendarUtil.solarToLunar(birthday);
					userinfo.setLunarbirthday(solarToLunar);
				}
			}
			// 用户信息表
			status = userInfoDao.updateByPrimaryKeySelective(userinfo);

			// 教育经历
			for (Useredu userEdu : entity.getUserEdu()) {
				// 新增工作经历
				if (userEdu.getEduid() == null || userEdu.getEduid().equals("")) {
					Useredu userEdu2 = new Useredu();
					userEdu2.setUserid(entity.getUserid());
					userEdu2.setUniversity(userEdu.getUniversity());
					userEdu2.setMajor(userEdu.getMajor());
					userEdu2.setDegree(userEdu.getDegree());
					userEdu2.setEduid(UUIDUtils.getUUID());
					userEdu2.setDatefrom(userEdu.getDatefrom());
					userEdu2.setDateto(userEdu.getDateto());
					userEdu2.setIssecret(userEdu.getIssecret());
					status = userEduDao.insert(userEdu2);
				} else {// 修改工作经历
					status = userEduDao.updateByPrimaryKey(userEdu);
				}
			}

			for (Userworkexp userWorkexp : entity.getUserWorkexp()) {
				if (userWorkexp.getWorkid() == null || userWorkexp.getWorkid().equals("")) {
					Userworkexp userWorkexp2 = new Userworkexp();
					userWorkexp2.setWorkid(UUIDUtils.getUUID());
					userWorkexp2.setUserid(entity.getUserid());
					userWorkexp2.setCompany(userWorkexp.getCompany());
					userWorkexp2.setDatefrom(userWorkexp.getDatefrom());
					userWorkexp2.setDateto(userWorkexp2.getDateto());
					userWorkexp2.setIssecret(userWorkexp.getIssecret());
					userWorkexp2.setPosition(userWorkexp.getPosition());
					userWorkexp2.setWorkcontent(userWorkexp.getWorkcontent());
					status = userworkDao.insert(userWorkexp2);
				} else {
					status = userworkDao.updateByPrimaryKey(userWorkexp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse getCityNoticeList(User entity) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(entity.getUserid()) || entity.getUserid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少用户ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		// 获取当前用户所在的城市编码和名称
		res = getCitycodeOfUser(entity.getUserid(), entity.getFamilyid());
		if (res.getCode() == 1)
			return res;
		BranchValidArea branchValidArea = (BranchValidArea) res.getData();
		// 获取城市下的所有分支的动态列表
		return getNoticeListByCity(branchValidArea, entity);
	}

	/**
	 * 获取指定城市的下的所有分支的公告
	 * 
	 * @author 李鹏 17-03-21
	 * @param branchValidArea
	 * @param familyid
	 * @return
	 */
	public JsonResponse getNoticeListByCity(BranchValidArea branchValidArea, User entity) {
		Result result = null;
		JsonResponse res = null;
		// 查询当前城市下的所有分支列表
		BranchQuery branchExample = new BranchQuery();
		branchExample.or().andFamilyidEqualTo(entity.getFamilyid()).andCitycodeEqualTo(branchValidArea.getAreacode())
				.andStatusEqualTo(0);
		List<Branch> branchs = branchDao.selectByExample(branchExample);
		if (branchs.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前城市下不存在分支！");
			res = new JsonResponse(result);
			return res;
		}
		List<String> branchids = new ArrayList<String>();
		for (Branch branch : branchs) {
			branchids.add(branch.getBranchid());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", entity.getStart());
		map.put("count", entity.getCount());
		map.put("list", branchids);
		// List<Dynamic> dynamics = dynamicMapper.findByIdsMap(branchids);
		List<Notice> notices = noticeMapper.findByIdsMap(map);
		if (notices.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前城市下不存在公告！");
			res = new JsonResponse(result);
			return res;
		}
		// 获取当前用户的分支ID
		String branchid = userDao.selectByPrimaryKey(entity.getUserid()).getBranchid();
		if (branchid == null || "".equals(branchid)) {

		}

		List<NoticeVO> noticeVOs = new ArrayList<NoticeVO>();
		for (Notice notice : notices) {
			res = noticeService.getNoticeDetailExt(notice);
			if (res.getCode() == ConstantUtils.RESULT_SUCCESS) {
				noticeVOs.add((NoticeVO) res.getData());
			}
		}
		for (NoticeVO noticeVO : noticeVOs) {
			Notice notice = noticeVO.getNotice();
			NoticetopQuery noticeTopExample = new NoticetopQuery();
			noticeTopExample.or().andNoticeidEqualTo(notice.getNoticeid());
			List<Noticetop> noticeTops = noticeTopMapper.selectByExample(noticeTopExample);
			if (IsInclude(branchid, noticeTops)) {
				notice.setType(1);
			}
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(noticeVOs);
		return res;
	}

	/**
	 * 判断列表中有无分支ID指定数值
	 * 
	 * @param branchid
	 * @param noticeTops
	 * @return
	 */

	private boolean IsInclude(String branchid, List<Noticetop> noticeTops) {
		for (Noticetop noticeTop : noticeTops) {
			if (branchid.equals(noticeTop.getBranchid()))
				return true;
		}
		return false;
	}

	/**
	 * 获取用户所在的城市编码
	 * 
	 * @author 李鹏 17-03-21
	 * @param userid
	 * @param familyid
	 * @return
	 */
	public JsonResponse getCitycodeOfUser(String userid, String familyid) {
		Result result = null;
		JsonResponse res = null;
		User user = userDao.selectByPrimaryKey(userid);
		if (user == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前用户不存在！");
			res = new JsonResponse(result);
			return res;
		}
		// 获取所在分支信息
		if (user.getBranchid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前用户不存在任何分支！");
			res = new JsonResponse(result);
			return res;
		}
		BranchQuery branchExample = new BranchQuery();
		branchExample.or().andFamilyidEqualTo(familyid).andBranchidEqualTo(user.getBranchid()).andStatusEqualTo(0);
		List<Branch> branchs = branchDao.selectByExample(branchExample);
		if (branchs.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前用户不存在任何分支！");
			res = new JsonResponse(result);
			return res;
		}
		Branch branch = branchs.get(0);
		BranchValidArea branchValidArea = new BranchValidArea();
		branchValidArea.setAreacode(branch.getCitycode());
		branchValidArea.setAreaname(branch.getCityname());
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(branchValidArea);
		return res;
	}

	@Override
	public JsonResponse getCityNoticeListExt(User entity) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(entity.getIdcard()) || entity.getIdcard() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少城市ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		BranchValidArea branchValidArea = new BranchValidArea();
		branchValidArea.setAreacode(entity.getIdcard());
		return getNoticeListByCity(branchValidArea, entity);
	}

	@Override
	public JsonResponse getCityDyList(User entity) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(entity.getUserid()) || entity.getUserid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少用户ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		// 获取当前用户所在的城市编码和名称
		JsonResponse result2 = getCitycodeOfUser(entity.getUserid(), entity.getFamilyid());
		if (result2.getCode() == 1)
			return result2;
		BranchValidArea branchValidArea = (BranchValidArea) result2.getData();
		// 获取城市下的所有分支的动态列表
		return getDyListByCity(branchValidArea, entity);
	}

	/**
	 * 获取指定城市的下的所有分支的动态
	 * 
	 * @author 李鹏 17-03-21
	 * @param branchValidArea
	 * @param familyid
	 * @return
	 */
	public JsonResponse getDyListByCity(BranchValidArea branchValidArea, User entity) {
		Result result = null;
		JsonResponse res = null;
		// 查询当前城市下的所有分支列表
		BranchQuery branchExample = new BranchQuery();
		branchExample.or().andFamilyidEqualTo(entity.getFamilyid()).andCitycodeEqualTo(branchValidArea.getAreacode())
				.andStatusEqualTo(0);
		List<Branch> branchs = branchDao.selectByExample(branchExample);
		if (branchs.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前城市下不存在分支！");
			res = new JsonResponse(result);
			return res;
		}
		List<String> branchids = new ArrayList<String>();
		for (Branch branch : branchs) {
			branchids.add(branch.getBranchid());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", entity.getStart());
		map.put("count", entity.getCount());
		map.put("list", branchids);
		// List<Dynamic> dynamics = dynamicMapper.findByIdsMap(branchids);
		List<Dynamic> dynamics = dynamicMapper.findByIdsMap(map);
		if (dynamics.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前城市下不存在动态！");
			res = new JsonResponse(result);
			return res;
		}
		List<DynamicVO> dynamicVOs = new ArrayList<DynamicVO>();
		for (Dynamic dynamic : dynamics) {
			JsonResponse result2 = dynamicService.getDyDetailExt(dynamic);
			if (result2.getCode() == ConstantUtils.RESULT_SUCCESS) {
				dynamicVOs.add((DynamicVO) result2.getData());
			}
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(dynamicVOs);
		return res;
	}

	@Override
	public JsonResponse getCityDyListExt(User entity) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(entity.getIdcard()) || entity.getIdcard() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少城市ID参数！");
			res = new JsonResponse(result);
		}
		BranchValidArea branchValidArea = new BranchValidArea();
		branchValidArea.setAreacode(entity.getIdcard());
		return getDyListByCity(branchValidArea, entity);
	}

	@Override
	public JsonResponse getCityAlbumList(User entity) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(entity.getUserid()) || entity.getUserid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少用户ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		// 获取当前用户所在的城市编码和名称
		JsonResponse result2 = getCitycodeOfUser(entity.getUserid(), entity.getFamilyid());
		if (result2.getCode() == 1)
			return result2;
		BranchValidArea branchValidArea = (BranchValidArea) result2.getData();
		// 获取城市下的所有分支的动态列表
		return getAlbumListByCity(branchValidArea, entity);
	}

	/**
	 * 获取指定城市的下的所有分支的公告
	 * 
	 * @author 李鹏 17-03-21
	 * @param branchValidArea
	 * @param familyid
	 * @return
	 */
	public JsonResponse getAlbumListByCity(BranchValidArea branchValidArea, User entity) {
		Result result = null;
		JsonResponse res = null;
		// 查询当前城市下的所有分支列表
		BranchQuery branchExample = new BranchQuery();
		branchExample.or().andFamilyidEqualTo(entity.getFamilyid()).andCitycodeEqualTo(branchValidArea.getAreacode())
				.andStatusEqualTo(0);
		List<Branch> branchs = branchDao.selectByExample(branchExample);
		if (branchs.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前城市下不存在分支！");
			res = new JsonResponse(result);
			return res;
		}
		List<String> branchids = new ArrayList<String>();
		for (Branch branch : branchs) {
			branchids.add(branch.getBranchid());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", entity.getStart());
		map.put("count", entity.getCount());
		map.put("list", branchids);
		// List<Dynamic> dynamics = dynamicMapper.findByIdsMap(branchids);
		List<Branchalbum> branchAlbumList = branchAlbumMapper.findByIdsMap(map);
		if (branchAlbumList.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前城市下不存在相册！");
			res = new JsonResponse(result);
			return res;
		}
		// 填充头图与图片数量
		addAlbumNumAndPreImg(branchAlbumList);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(branchAlbumList);
		return res;
	}

	/**
	 * 填充头图与图片数量
	 * 
	 * @描述
	 * @作者 jinlizhi
	 * @时间 2017年9月6日下午5:05:16
	 * @参数 @param branchAlbumList
	 * @return void
	 */
	private void addAlbumNumAndPreImg(List<Branchalbum> branchAlbumList) {
		// 填充图片数量
		BranchphotoExample photoEp = new BranchphotoExample();
		for (Branchalbum branchAlbum : branchAlbumList) {
			photoEp.clear();
			photoEp.setOrderByClause("createtime asc");
			photoEp.or().andAlbumidEqualTo(branchAlbum.getAlbumid()).andBranchidEqualTo(branchAlbum.getBranchid())
					.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			List<Branchphoto> photeList = branchPhotoMapper.selectByExample(photoEp);
			if (photeList != null) {
				// 填充头图
				branchAlbum.setPhotoNum(photeList.size());
				if (photeList.size() > 0) {
					String smallimgurl = photeList.get(0).getSmallimgurl();
					branchAlbum.setPrePhotoImg(smallimgurl);
				}
			}

		}
	}

	@Override
	public JsonResponse getCityAlbumListExt(User entity) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(entity.getIdcard()) || entity.getIdcard() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少城市ID参数！");
			res = new JsonResponse(result);
			return res;
		}
		BranchValidArea branchValidArea = new BranchValidArea();
		branchValidArea.setAreacode(entity.getIdcard());
		return getAlbumListByCity(branchValidArea, entity);
	}

	@Override
	public JsonResponse addChild(UserClildInfo userChildInfo) {
		Result result = null;
		JsonResponse res = null;
		int icount = 0;
		JsonResponse demoUser = checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (userChildInfo.getPid() == null || "".equals(userChildInfo.getPid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少父亲ID信息！");
			res = new JsonResponse(result);
			return res;
		}
		/*
		 * if (userChildInfo.getPhone() == null || "".equals(userChildInfo.getPhone()))
		 * { result.setStatus(Constants.RESULT_FAIL); result.setMsg("缺少手机号信息！"); return
		 * result; }
		 */
		if (userChildInfo.getUsername() == null || "".equals(userChildInfo.getUsername())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("需要填写用户姓名！");
			res = new JsonResponse(result);
			return res;
		}
		if (userChildInfo.getSex() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("需要填写用户性别！");
			res = new JsonResponse(result);
			return res;
		}
		if (userChildInfo.getBrotherpos() == null || "".equals(userChildInfo.getBrotherpos())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("需要填写用户排行！");
			res = new JsonResponse(result);
			return res;
		}
		if (userChildInfo.getNation() == null || "".equals(userChildInfo.getNation())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("需要填写用户民族！");
			res = new JsonResponse(result);
			return res;
		}
		// 判断提交手机号码是否有效
		/*
		 * UserExample userExample = new UserExample();
		 * userExample.or().andPhoneEqualTo(userChildInfo.getPhone()).
		 * andDeleteflagEqualTo((byte) 0); if
		 * (userMapper.selectByExample(userExample).size() > 0) {
		 * result.setStatus(Constants.RESULT_FAIL); result.setMsg("该手机号码已存在！"); return
		 * result; }
		 */

		// 获取父节点实例
		User pUser = userDao.selectByPrimaryKey(userChildInfo.getPid());
		///////////////////
		User user = new User();
		// 初始化密码
		int initpwd = new Random().nextInt(900000);
		initpwd = initpwd + 100000;
		user.setPassword(MD5Util.string2MD5(String.valueOf(initpwd)));
		String userid = UUIDUtils.getUUID();
		user.setUserid(userid);
		user.setSex(userChildInfo.getSex());
		user.setBrotherpos(userChildInfo.getBrotherpos());
		// 是否在世： 不填写，默认为在世
		user.setLivestatus((userChildInfo.getLivestatus() == null) ? 0 : userChildInfo.getLivestatus());
		// 是否直系： 不填写，默认为直系
		if (userChildInfo.getIsdirect() == null) {
			user.setIsdirect((pUser.getIsdirect() == null) ? 0 : pUser.getIsdirect());
		} else {
			user.setIsdirect(userChildInfo.getIsdirect());
		}
		// 是否亲生： 不填写，默认为亲生
		user.setIsborn((userChildInfo.getIsborn() == null) ? 0 : userChildInfo.getIsborn());
		// 状态为待审核
		user.setStatus(1);
		user.setDeleteflag(0);
		user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(userChildInfo.getUsername()));
		user.setPinyinfull(PinyinUtil.getPinyinFull(userChildInfo.getUsername()));
		user.setUsername(userChildInfo.getUsername());
		user.setUsedname(userChildInfo.getUsedname());
		user.setIdcard(userChildInfo.getIdcard());
		if (pUser.getGenlevel() != null) {
			user.setGenlevel(pUser.getGenlevel() + 1);
		}
		user.setPhone(userChildInfo.getPhone());
		user.setPid(pUser.getUserid());
		user.setPname(pUser.getUsername());
		user.setFamilyid(pUser.getFamilyid());
		user.setFamilyname(pUser.getFamilyname());
		user.setBranchid(pUser.getBranchid());
		user.setBranchname(pUser.getBranchname());
		user.setCreateid(pUser.getUserid());
		user.setUpdateid(pUser.getUserid());
		user.setCreatetime(new Date());
		user.setUpdatetime(new Date());
		icount = userDao.insertSelective(user);

		Userinfo userInfo = new Userinfo();
		userInfo.setUserid(userid);

		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//前台传参：阳历日期：yyyy-MM-dd
		String birthday = userChildInfo.getBirthday();
		try {
			if (StringUtils.isNotBlank(birthday)) {
				//(农历日期范围19000101~20491229)
				int parseInt = Integer.parseInt(birthday.replace("-", ""));
				if (parseInt > 19000130 && parseInt < 20500101) {
					String solarToLunar = CalendarUtil.solarToLunar(birthday);
					userInfo.setLunarbirthday(solarToLunar);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			result.setMsg("月份有错!");
			res = new JsonResponse(result);
			return res;
		}
		userInfo.setBirthday(birthday);
		userInfo.setNation(userChildInfo.getNation());

		userInfo.setBackground(userChildInfo.getBackground());

		String birthplaceP = userChildInfo.getBirthplaceP() == null ? "" : userChildInfo.getBirthplaceP();
		String birthplaceC = userChildInfo.getBirthplaceC() == null ? "" : userChildInfo.getBirthplaceC();
		String birthplaceX = userChildInfo.getBirthplaceX() == null ? "" : userChildInfo.getBirthplaceX();
		String birthDetail = userChildInfo.getBirthDetail() == null ? "" : userChildInfo.getBirthDetail();
		// 出生地
		userInfo.setBirthplace(birthplaceP + "@@" + birthplaceC + "@@" + birthplaceX + "@@" + birthDetail);
		String homeplaceP = userChildInfo.getHomeplaceP() == null ? "" : userChildInfo.getHomeplaceP();
		String homeplaceC = userChildInfo.getHomeplaceC() == null ? "" : userChildInfo.getHomeplaceC();
		String homeplaceX = userChildInfo.getHomeplaceX() == null ? "" : userChildInfo.getHomeplaceX();
		String homeDetail = userChildInfo.getHomeDetail() == null ? "" : userChildInfo.getHomeDetail();
		// 常住地
		userInfo.setHomeplace(homeplaceP + "@@" + homeplaceC + "@@" + homeplaceX + "@@" + homeDetail);

		userInfo.setMail(userChildInfo.getMail());
		userInfo.setMailsee((userChildInfo.getMailsee() == null) ? 0 : userChildInfo.getMailsee());
		userInfo.setWeixin(userChildInfo.getWeixin());
		userInfo.setWxsee((userChildInfo.getWxsee() == null) ? 0 : userChildInfo.getWxsee());
		userInfo.setQq(userChildInfo.getQQ());
		userInfo.setQqsee((userChildInfo.getQqsee() == null) ? 0 : userChildInfo.getQqsee());
		userInfo.setTel(userChildInfo.getTel());
		userInfo.setTelsee((userChildInfo.getTelsee() == null) ? 0 : userChildInfo.getTelsee());
		userInfoDao.insertSelective(userInfo);
		userInfo.setRemark(userChildInfo.getRemark());
		userInfo.setRemarksee((userChildInfo.getRemarksee() == null) ? 0 : userChildInfo.getRemarksee());

		if (icount > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("添加成功");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		result.setMsg("添加失败");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse searchAllBytitle(User entity) {
		Result result = null;
		JsonResponse res = null;
		// 参数校验
		if (entity.getUsername() == null || entity.getUsername().isEmpty()) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少关键参数");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getFamilyid() == null || entity.getFamilyid().isEmpty()) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID关键参数");
			res = new JsonResponse(result);
			return res;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", entity.getUsername());
		map.put("familyid", entity.getFamilyid());
		List<User> users = userDao.selectByTitle(map);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(users);
		return res;
	}

	@Override
	public JsonResponse searchBranchBytitle(User entity) {
		Result result = null;
		JsonResponse res = null;
		// 参数校验
		if (entity.getUsername() == null || entity.getUsername().isEmpty()) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少关键参数");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getFamilyid() == null || entity.getFamilyid().isEmpty()) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID关键参数");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getBranchid() == null || entity.getBranchid().isEmpty()) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少分支ID关键参数");
			res = new JsonResponse(result);
			return res;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", entity.getUsername());
		map.put("familyid", entity.getFamilyid());
		map.put("branchid", entity.getBranchid());
		List<User> users = userDao.selectBranchByTitle(map);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(users);
		return res;
	}

	@Override
	public JsonResponse deleteUserWorkExp(User entity) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getUpdateid()) || entity.getUpdateid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("工作经历id不存在！");
			res = new JsonResponse(result);
			return res;
		}
		JsonResponse demoUser = checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		UserworkexpQuery userWorkexpExample = new UserworkexpQuery();
		userWorkexpExample.or().andWorkidEqualTo(entity.getUpdateid());
		int status = userworkDao.deleteByExample(userWorkexpExample);
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("删除成功");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		result.setMsg("删除失败");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse deleteUserEduExp(User entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if ("".equals(entity.getUpdateid()) || entity.getUpdateid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("教育经历id不存在！");
			res = new JsonResponse(result);
			return res;
		}
		UsereduQuery userEduExample = new UsereduQuery();
		userEduExample.or().andEduidEqualTo(entity.getUpdateid());
		int status = userEduDao.deleteByExample(userEduExample);
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse joinFamily(User entity, String birthday, String nation) {
		Result result = null;
		JsonResponse res = null;
		// 参数校验
		if (StringUtils.isBlank(entity.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("家族id不能为空，请重设后再试");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getFamilyname())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("家族名称不能为空，请重设后再试");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("手机号不能为空，请重设后再试");
			res = new JsonResponse(result);
			return res;
		}
		if ("18647740001".equals(entity.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不可编辑！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getUsername())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户姓名不能为空，请重设后再试");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getSex().toString())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("性别不能为空，请重设后再试");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getBrotherpos())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("排行不能为空，请重设后再试");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(birthday)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("出生日期不能为空，请重设后再试");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(nation)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("民族不能为空，请重设后再试");
			res = new JsonResponse(result);
			return res;
		}
		//	List<User> byPhone = userDao.selectByPhone(entity.getPhone());
		int status = 0;
		try {
			List<User> byPhoneAndStatus = userDao.selectByPhoneInStatus(entity.getPhone());
			if (byPhoneAndStatus.size() >= 2) {
				result = new Result(MsgConstants.FAMILYID_RESTRICT);
				res = new JsonResponse(result);
				return res;
			}
			for (User user : byPhoneAndStatus) {
				if (user.getFamilyid().equals(entity.getFamilyid())) {
					result = new Result(MsgConstants.FAMILYID_REPETITION);
					res = new JsonResponse(result);
					return res;
				}
			}
			Integer count = userDao.selectByPhoneToStatus(entity.getPhone(), entity.getFamilyid());
			if (count >= 1) {
				result = new Result(MsgConstants.FAMILYID_REPULSE);
				res = new JsonResponse(result);
				return res;
			}
			// 创建用户
			String userid = UUIDUtils.getUUID();
			entity.setUserid(userid);
			entity.setCreateid(userid);
			entity.setCreatetime(new Date());
			entity.setDeleteflag(0);
			entity.setStatus(ConstantUtils.USER_STATUS_WAIT);
			entity.setPassword(MD5Util.string2MD5(entity.getPhone().substring(entity.getPhone().length() - 6)));
			status = userDao.insertSelective(entity);
			Userinfo userInfo = new Userinfo();
			userInfo.setUserid(userid);
			userInfo.setBirthday(birthday);
			userInfo.setNation(nation);
			status = userInfoDao.insertSelective(userInfo);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				result.setMsg("申请成功！");
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		result.setMsg("申请失败！");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse applyingFamily(User entity) {
		Result result = null;
		JsonResponse res = null;

		List<Map<String, Object>> users = userDao.selectFamilycode(entity.getPhone(), ConstantUtils.USER_STATUS_WAIT);
		if (users.size() > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(users);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("您当前还没有申请过家族！");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse joinedFamily(User entity) {
		Result result = null;
		JsonResponse res = null;
		String userid = WebUtil.getRequest().getHeader("userid");
		if (StringUtils.isBlank(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		/*	UserQuery userQuery = new UserQuery();
			userQuery.or().andPhoneEqualTo(entity.getPhone()).andFamilyidIsNotNull().andDeleteflagEqualTo(0)
					.andStatusEqualTo(ConstantUtils.USER_STATUS_OK);
			List<User> users = userDao.selectByExample(userQuery);*/
		List<Map<String, Object>> users = userDao.selectFamilycode(entity.getPhone(), ConstantUtils.USER_STATUS_OK);
		if (users.size() > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(users);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		result.setMsg("您当前还没有关联过家族！");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse changeLoginUser(User entity, String loginType, String internetType, String version) {
		Result result = null;
		JsonResponse res = null;
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		//String familyid = WebUtil.getHeaderInfo(Constants.HEADER_FAMILYID);
		if (StringUtils.isBlank(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数familyid为比传！");
			res = new JsonResponse(result);
			return res;
		}
		User usere = userDao.selectByPrimaryKey(userid);

		Map<String, String> userparams = new HashMap<String, String>();
		userparams.put("familyid", entity.getFamilyid());
		userparams.put("phone", usere.getPhone());
		List<User> dbuserList = userDao.selectByFamilyId(userparams);
		//		@SuppressWarnings("unchecked")
		//		List<User> dbuserList = (List<User>) result.getData();
		////////////////////////// 添加api登录的app用户
		ServletContext application = WebUtil.getApplication();
		@SuppressWarnings("unchecked")
		List<OnLineUser> onlineUsers = (List<OnLineUser>) application.getAttribute("onlineUsers");
		// 第一次使用前，需要初始化
		if (onlineUsers == null) {
			onlineUsers = new ArrayList<OnLineUser>();
			application.setAttribute("onlineUsers", onlineUsers);
		}
		// 先删除之前存在的账户，再加入用户
		if (dbuserList != null && dbuserList.size() > 0) {
			User user = dbuserList.get(0);
			HttpServletRequest request = WebUtil.getRequest();
			OnLineUser onLineUser = setLineMsg(request, user, loginType, internetType, version);
			onlineUsers.remove(new OnLineUser(user.getUserid()));
			onlineUsers.add(onLineUser);
		}
		return singleCorpLoginOrMultiCorpGetList(dbuserList, entity.getSessionid());
	}

	@Override
	public JsonResponse checkDemoUser() {
		Result result = null;
		JsonResponse res = null;
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringUtils.isBlank(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		String phone = userDao.selectUserPhone(userid);
		if ("18647740001".equals(phone)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不可编辑！");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userid);
		return res;
	}

	@Override
	public void updateByPrimaryKeySelective(User user) {
		try {
			userDao.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[记录登录时间---异常:]", e);
		}
	}

	@Override
	public String getAllAddressByUserid(String userid) {
		String address = "";
		try {
			address = userDao.getAddressByUserid(userid);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[获取地址信息---异常:]", e);
		}
		return address;
	}
}