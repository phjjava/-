package com.jp.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.controller.UserController;
import com.jp.dao.BranchDao;
import com.jp.dao.LoginThirdMapper;
import com.jp.dao.SysFamilyDao;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.dao.UserDao;
import com.jp.dao.UserImportMapper;
import com.jp.dao.UserInfoImportMapper;
import com.jp.dao.UseralbumDao;
import com.jp.dao.UsercodeDao;
import com.jp.dao.UsereduDao;
import com.jp.dao.UserinfoMapper;
import com.jp.dao.UsermatesDao;
import com.jp.dao.UserphotoDao;
import com.jp.dao.UserworkexpDao;
import com.jp.entity.Branch;
import com.jp.entity.BranchKey;
import com.jp.entity.LoginThird;
import com.jp.entity.LoginThirdExample;
import com.jp.entity.OnLineUser;
import com.jp.entity.SysFamily;
import com.jp.entity.SysVersionPrivilege;
import com.jp.entity.User;
import com.jp.entity.UserAppLimit;
import com.jp.entity.UserImportExample;
import com.jp.entity.UserQuery;
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
import com.jp.service.UserService;
import com.jp.util.DateUtil;
import com.jp.util.DateUtils;
import com.jp.util.EmaySend;
import com.jp.util.ExcelUtil;
import com.jp.util.GsonUtil;
import com.jp.util.MD5Util;
import com.jp.util.PinyinUtil;
//import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.ValidatorUtil;
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

	// 导入用户时重复的用户
	private ArrayList<String> userStringList;

	// 导入用户时重复的用户
	private ArrayList<String> userMateStringList;

	@Override
	public int insert(User user) throws Exception {
		return 0;
	}

	@Override
	public PageModel pageQuery(PageModel pageModel, User user) throws Exception {

		return null;
	}

	@Override
	public void deleteAndAll(User user) throws Exception {

	}

	@Override
	public Result merge(User user) throws Exception {
		Result result = null;
		try {
			// 点击编辑后保存
			if (StringTools.trimNotEmpty(user.getUserid())) {
				Userinfo userinfo = new Userinfo();
				userinfo = user.getUserInfo();
				userinfo.setUserid(user.getUserid());
				// 编辑用户信息
				if (StringTools.trimNotEmpty(user.getPhone())) {
					// 如果是存在手机号，则用之前德密码
					UserQuery ex = new UserQuery();
					ex.or().andPhoneEqualTo(user.getPhone()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
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
				user.setUpdateid(CurrentUserContext.getCurrentUserId());
				user.setUpdatetime(new Date());
				userDao.updateByPrimaryKeySelective(user);
				// 出生地
				userinfo.setBirthplace(userinfo.getBirthplaceP() + "@@" + userinfo.getBirthplaceC() + "@@"
						+ userinfo.getBirthplaceX() + "@@" + userinfo.getBirthDetail());
				// 常住地
				userinfo.setHomeplace(userinfo.getHomeplaceP() + "@@" + userinfo.getHomeplaceC() + "@@"
						+ userinfo.getHomeplaceX() + "@@" + userinfo.getHomeDetail());
				// 保存用户详细信息
				userInfoDao.updateByPrimaryKeySelective(userinfo);
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
				// 新增用户信息
				// boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), 1);
				boolean flag = checkFamilyUserNumber(1);
				if (flag == true) {
					String userId = UUIDUtils.getUUID();
					user.setUserid(userId);
					user.setDeleteflag(0);
					user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
					user.setStatus(0);
					// user.setIsdirect(1);
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
					user.setCreatetime(new Date());
					user.setUpdatetime(new Date());
					user.setUpdateid(CurrentUserContext.getCurrentUserId());
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
					Userinfo userinfo = new Userinfo();
					userinfo.setUserid(userId);
					Useredu useredu = new Useredu();
					useredu.setUserid(userId);
					user.setCreateid(CurrentUserContext.getCurrentUserId());
					user.setCreatetime(new Date());
					// 校验方法 返回 true为有重复，false 没重复
					boolean sameFlag = checkSameUser(user);
					if (sameFlag) {
						// 校验有重复什么也不做哦
						// result = "500";
						result = new Result(MsgConstants.USER_SAVE_HAVEREPEAT);
					} else {
						// 保存用户信息
						userDao.insertSelective(user);
						// 出生地
						userinfo.setBirthplace(userinfo.getBirthplaceP() + "@@" + userinfo.getBirthplaceC() + "@@"
								+ userinfo.getBirthplaceX() + "@@" + userinfo.getBirthDetail());
						// 常住地
						userinfo.setHomeplace(userinfo.getHomeplaceP() + "@@" + userinfo.getHomeplaceC() + "@@"
								+ userinfo.getHomeplaceX() + "@@" + userinfo.getHomeDetail());
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
					}
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
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());

		// CurrentUserContext.getUserContext().getRole().getIsmanager();
		List<User> userList = userInfoDao.selecUserList(user, branchList);
		pageModel.setList(userList);
		pageModel.setPageInfo(new PageInfo<User>(userList));
		return pageModel;
	}

	@Override
	public User selectByPrimaryKey(String userid) throws Exception {
		return userDao.selectByPrimaryKey(userid);
	}

	@Override
	public int changeStatus(User user) throws Exception {
		return userDao.changeStatus(user);
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
		String branchid = request.getParameter("branchid");
		BranchKey branchkey = new BranchKey();
		branchkey.setBranchid(branchid);
		branchkey.setFamilyid(CurrentUserContext.getCurrentFamilyId());
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
			// String familyid =
			// boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(),
			// totalRows - 1);
			// 判断家族人数是否超出当前家族容纳人数上限
			boolean flag = checkFamilyUserNumber(totalRows - 1);
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
					user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
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
					user.setCreateid(CurrentUserContext.getCurrentUserId());
					user.setCreatetime(new Date());
					user.setUpdateid(CurrentUserContext.getCurrentUserId());
					user.setUpdatetime(new Date());
					user.setDeleteflag(ConstantUtils.DELETE_FALSE);
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
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
			// boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(),
			// lastRowNum - 1);
			// 判断家族人数是否超出当前家族容纳人数上限
			boolean flag = checkFamilyUserNumber(lastRowNum - 1);
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
					String genlevelstr = eutil.toDecimalFormat(eutil.getCellContent(sheet.getRow(i).getCell(0)).trim());// 世系
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
					user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
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
					user.setCreateid(CurrentUserContext.getCurrentUserId());
					user.setCreatetime(new Date());
					user.setUpdateid(CurrentUserContext.getCurrentUserId());
					user.setUpdatetime(new Date());
					user.setDeleteflag(ConstantUtils.DELETE_FALSE);
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
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
			if (StringUtil.isNotEmpty(userf.getUsername()) && userf.getUsername().equals(user.getUsername())
					&& userf.getGenlevel().equals(user.getGenlevel())) {
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
			if (StringUtil.isNotEmpty(userf.getUsername()) && userf.getUsername().equals(user.getUsername())
					&& userf.getGenlevel() == user.getGenlevel()) {
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
		String branchid = request.getParameter("branchid");
		BranchKey branchkey = new BranchKey();
		branchkey.setBranchid(branchid);
		branchkey.setFamilyid(CurrentUserContext.getCurrentFamilyId());
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
		Map<String, User> userPhoneMap = new HashMap<>();
		List<User> userList = new ArrayList<User>();
		// User user = null;
		Usermates userMates = null;
		List<Usermates> userMatesList = new ArrayList<Usermates>();
		// 批量更新 已经存在的用户
		List<User> userAleardyListUpdate = new ArrayList<User>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 查询用户列表
		// User userAleardy = new User();
		// userAleardy.setFamilyid(CurrentUserContext.getCurrentFamilyId());
		// userAleardy.setIsdirect(1);
		// userAleardy.setIsMarry(0);
		// 获取文件名称
		String name = file.getOriginalFilename();
		NumberFormat nf = new DecimalFormat("#");
		List<String> userStringList = new ArrayList<String>();
		// 获取导入的分支名字
		// String branchname = name.substring(0, name.indexOf("世系"));
		//
		// List<String> branchList = CurrentUserContext.getCurrentBranchIds();
		// Integer type =
		// CurrentUserContext.getUserContext().getUsermanagers().get(0).getEbtype();
		// Integer manager =
		// CurrentUserContext.getUserContext().getUsermanagers().get(0).getIsmanager();
		// if (type != 1 && manager != 1) {
		// branchList.clear();
		// }
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
				// boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(),
				// totalRows - 1);
				boolean flag = checkFamilyUserNumber(totalRows - 1);
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
									user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
									user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
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
									user.setCreateid(CurrentUserContext.getCurrentUserId());
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
			// boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(),
			// lastRowNum - 1);
			boolean flag = checkFamilyUserNumber(lastRowNum - 1);
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
								if (u.getGenlevel() == Integer.parseInt(genlevel)
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
							user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
							user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
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
							user.setCreateid(CurrentUserContext.getCurrentUserId());
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
				res.setEntity(userMatesString);
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
	public PageModel selecUserListToReview(PageModel pageModel, User user) throws Exception {
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<User> userList = userDao.selecUserListToReview(user);
		pageModel.setList(userList);
		pageModel.setPageInfo(new PageInfo<User>(userList));
		return pageModel;
	}

	@Override
	public Result mergeMate(User user, Userinfo userInfo, String usernameBefore) throws Exception {
		Result result = null;
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Integer matetype = user.getMatetype();
			// 丈夫或妻子 matetype 0丈夫;1妻子;2其他
			String userid = UUIDUtils.getUUID();
			String birthplace = userInfo.getBirthplaceP() + "@@" + userInfo.getBirthplaceC() + "@@"
					+ userInfo.getBirthplaceX();
			userInfo.setBirthplace(birthplace);
			if (matetype == 0 || matetype == 1) {
				if (StringTools.trimNotEmpty(user.getMateid())) {
					// 修改用户配偶信息
					User userMmateUpdate = new User();
					String phone = user.getPhone();
					userMmateUpdate.setUserid(user.getMateid());
					userMmateUpdate.setPhone(phone);
					if (StringTools.trimNotEmpty(phone)) {
						userMmateUpdate.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
					}
					userMmateUpdate.setUpdatetime(new Date());
					userMmateUpdate.setCreateid(CurrentUserContext.getCurrentUserId());
					userMmateUpdate.setUsername(user.getMatename());
					userDao.updateByPrimaryKeySelective(userMmateUpdate);
					// 更新 userinfo
					userInfo.setUserid(user.getMateid());
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
					// boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), 1);
					boolean flag = checkFamilyUserNumber(1);
					if (flag == true) {
						String phone = user.getPhone();
						User userMmate = new User();
						userMmate.setUserid(userid);
						userMmate.setPhone(phone);
						userMmate.setCreatetime(new Date());
						if (StringTools.trimNotEmpty(phone)) {
							userMmate.setPassword(MD5Util.string2MD5(phone.substring(phone.length() - 6)));
						}
						userMmate.setFamilyid(CurrentUserContext.getCurrentFamilyId());
						userMmate.setFamilyname(CurrentUserContext.getCurrentFamilyName());
						userMmate.setCreateid(CurrentUserContext.getCurrentUserId());
						userMmate.setCreatetime(new Date());
						userMmate.setStatus(0);
						userMmate.setIsdirect(0);
						userMmate.setMateid(user.getUserid());
						userMmate.setMatename(user.getUsername());
						userMmate.setUsername(user.getMatename());
						userMmate.setSex(user.getSex());
						userMmate.setGenlevel(user.getGenlevel());
						userMmate.setDeleteflag(0);
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
							userDao.addmateid(user.getUserid(), userid, user.getMatename());
							// userinfo 表
							userInfo.setUserid(userid);
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
				userMates.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				userMates.setFamilyname(CurrentUserContext.getCurrentFamilyName());
				userMates.setCreateid(CurrentUserContext.getCurrentUserId());
				userMates.setCreatetime(new Date());
				userMates.setStatus(0);
				userMates.setIsdirect(0);
				userMates.setMateid(user.getUserid());
				userMates.setMatename(user.getUsername());
				userMates.setUsername(user.getMatename());
				userMates.setSex(user.getSex());
				userMates.setGenlevel(user.getGenlevel());
				userMates.setDeleteflag(0);
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
					userInfoDao.insertSelective(userInfo);
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
	public boolean validatePhone(String familyid, String userid, String phone) {
		List<User> userList = userDao.validatePhone(familyid, userid, phone);
		if (userList != null && userList.size() < 2) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String mergeUserAlbum(Useralbum userAlbum) throws Exception {
		String ablumId = "";
		try {
			if (StringTools.trimNotEmpty(userAlbum.getAlbumid())) {
				userAlbum.setUpdatetime(new Date());
				userAlbum.setUpdateid(CurrentUserContext.getCurrentUserId());
				userAlbumDao.updateByPrimaryKeySelective(userAlbum);
			} else {
				ablumId = UUIDUtils.getUUID();
				userAlbum.setAlbumid(ablumId);
				userAlbum.setCreatetime(new Date());
				userAlbum.setCreateid(CurrentUserContext.getCurrentUserId());
				// 0未删除
				userAlbum.setDeleteflag(0);

				// userAlbum.setType(b);
				userAlbumDao.insertSelective(userAlbum);
			}
		} catch (Exception e) {
			ablumId = "";
			e.printStackTrace();
		}
		return ablumId;
	}

	@Override
	public String mergeUserPhoto(List<Userphoto> userPhotoList) throws Exception {
		String result = "";
		try {
			userPtotoDao.insertUserPhoto(userPhotoList);
			result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Useralbum> selectUseralbum(String userid) throws Exception {
		return userAlbumDao.selectUseralbum(userid);
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
		// Result result = new Result();
		Result result = new Result(MsgConstants.RESUL_FAIL);
		User record = new User();
		record.setUserid(CurrentUserContext.getCurrentUserId());
		record.setPassword(string2md5);
		int updateRt = userDao.updateByPrimaryKeySelective(record);
		if (updateRt != 1) {
			// result.setStatus(0);
			return result;
		}
		// result.setStatus(updateRt);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		return result;
	}

	@Override
	public JsonResponse importUsersNew(MultipartFile file, HttpServletRequest request) {

		// String result = "";
		// Result result = new Result();
		Result result = null;
		JsonResponse res = null;
		if (file == null) {
			// result = "0";
			/*
			 * result.setStatus(0); return result;
			 */
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
			// String familyid =
			// boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(),
			// totalRows - 1);
			boolean flag = checkFamilyUserNumber(totalRows - 1);
			if (flag == false) {
				/*
				 * result.setStatus(2); result.setMsg("导入用户数量超过版本最大用户限制!"); return result;
				 */
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
					user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
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
					user.setCreateid(CurrentUserContext.getCurrentUserId());
					user.setCreatetime(new Date());
					user.setUpdateid(CurrentUserContext.getCurrentUserId());
					user.setUpdatetime(new Date());
					user.setDeleteflag(ConstantUtils.DELETE_FALSE);
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
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
			// boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(),
			// lastRowNum - 1);
			boolean flag = checkFamilyUserNumber(lastRowNum - 1);
			if (flag == false) {
				/*
				 * result.setStatus(2); result.setMsg("导入用户数量超过版本最大用户限制!"); return result;
				 */
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
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String userId = UUIDUtils.getUUID();
					user = new User();
					user.setUserid(userId);
					user.setCreateid(CurrentUserContext.getCurrentUserId());
					user.setCreatetime(new Date());
					user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
					user.setStatus(0);
					user.setIsdirect(1);
					user.setGenlevel(Integer.valueOf(genlevel));
					user.setUsername(username);

					user.setUsedname(usedname);
					user.setIdcard(idCard);
					user.setPhone(phone);
					user.setUpdateid(CurrentUserContext.getCurrentUserId());
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

	public boolean checkFamilyUserNumber(int importCount) {
		boolean checkResult = false;
		Integer priValue = 0; // 最多容纳家族人数
		SysVersionPrivilege versionP = sysVersionPrivilegeMapper
				.selectByVersionAndCode(CurrentUserContext.getCurrentFamilyId(), ConstantUtils.VERSION_USERCOUNT);
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
		userExample.or().andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
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
		try {
			if (StringUtils.isBlank(user.getUserid())) {
				result.setMsg("用户userid为空！");
				res = new JsonResponse(result);
				return res;
			}
			user = userDao.selectByPrimaryKey(user.getUserid());
			Branch branch = new Branch();
			branch.setBranchid(user.getBranchid());
			branch.setFamilyid(CurrentUserContext.getCurrentFamilyId());
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
		phoneExample.or().andPhoneEqualTo(usercode.getPhone()).andStatusEqualTo(0).andDeleteflagEqualTo(0);
		phoneExample.setOrderByClause("logintime desc");
		List<User> selectByExample = userDao.selectByExample(phoneExample);
		if (selectByExample == null || selectByExample.isEmpty()) {
			phoneExample.clear();
			phoneExample.or().andPhoneEqualTo(usercode.getPhone()).andStatusNotEqualTo(0).andDeleteflagEqualTo(0);
			selectByExample = userDao.selectByExample(phoneExample);

			if (selectByExample == null || selectByExample.isEmpty()) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("当前手机号用户不存在，请重设后再试！");
				res = new JsonResponse(result);
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
				result.setMsg("用户不存在或家族已被停用！");
				res = new JsonResponse(result);
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
		phoneExample.or().andPhoneEqualTo(entity.getPhone()).andFamilyidIsNotNull().andDeleteflagEqualTo(0);
		List<User> list = userDao.selectByExample(phoneExample);
		User user = new User();
		List<SysFamily> sysFamilyList = new ArrayList<SysFamily>();
		if (list != null && list.size() > 0) {
			// 多企业用户,查询选择的企业列表然后返回
			for (User u : list) {
				// 如果有注册过未添加家族的用户，则不在新建用户
				if (StringUtils.isBlank(u.getFamilyid())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("该手机号已经注册！");
					res = new JsonResponse(result);
					return res;
				} else {
					SysFamily dbCorp = sysFamilyDao.selectByPrimaryKey(u.getFamilyid());
					if (dbCorp != null) {
						// 返回只返回企业id和企业名称
						SysFamily retCorp = new SysFamily();
						retCorp.setFamilyid(dbCorp.getFamilyid());
						retCorp.setFamilyname(dbCorp.getFamilyname());
						sysFamilyList.add(retCorp);
					}
				}
				u.setPassword(MD5Util.string2MD5(entity.getPhone().substring(entity.getPhone().length() - 6)));
				userDao.updateByPrimaryKey(u);
			}

			// user.setUserAppLimit(userAppLimit);

		}
		// 新建用户
		Date date = new Date();
		String userid = UUIDUtils.getUUID();
		user.setUserid(userid);
		user.setSex(1);
		user.setPhone(entity.getPhone());
		user.setCreateid(userid);
		user.setCreatetime(date);
		user.setDeleteflag(0);
		user.setStatus(ConstantUtils.USER_STATUS_WAIT.intValue());
		user.setPassword(MD5Util.string2MD5(entity.getPhone().substring(entity.getPhone().length() - 6)));
		userDao.insertSelective(user);
		Userinfo userinfo = new Userinfo();
		userinfo.setUserid(userid);
		userInfoDao.insertSelective(userinfo);

		user.setFamilys(sysFamilyList);
		user.setPassword(null);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(user);
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

		if (res.getCode() == ConstantUtils.RESULT_FAIL) {
			return res;
		}
		@SuppressWarnings("unchecked")
		List<User> dbuserList = (List<User>) res.getData();
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

}