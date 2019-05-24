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
import com.jp.dao.UsereduDao;
import com.jp.dao.UserinfoMapper;
import com.jp.dao.UsermatesDao;
import com.jp.dao.UserphotoDao;
import com.jp.dao.UserworkexpDao;
import com.jp.entity.Branch;
import com.jp.entity.BranchKey;
import com.jp.entity.BranchQuery;
import com.jp.entity.LoginThird;
import com.jp.entity.LoginThirdExample;
import com.jp.entity.SysFamily;
import com.jp.entity.SysVersionPrivilege;
import com.jp.entity.User;
import com.jp.entity.UserImportExample;
import com.jp.entity.UserInfoImportExample;
import com.jp.entity.UserQuery;
import com.jp.entity.Useralbum;
import com.jp.entity.Useredu;
import com.jp.entity.UsereduQuery;
import com.jp.entity.UsereduQuery.Criteria;
import com.jp.entity.Userinfo;
import com.jp.entity.Usermates;
import com.jp.entity.Userphoto;
import com.jp.entity.Userworkexp;
import com.jp.entity.UserworkexpQuery;
import com.jp.service.UserService;
import com.jp.util.DateUtils;
import com.jp.util.ExcelUtil;
import com.jp.util.GsonUtil;
import com.jp.util.MD5Util;
import com.jp.util.PinyinUtil;
//import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
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
	
	
	//导入用户时重复的用户
	private ArrayList<String> userStringList;
	
	//导入用户时重复的用户
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
			if (StringTools.trimNotEmpty(user.getUserid()) 
					) {
				Userinfo userinfo = new Userinfo();
				userinfo = user.getUserInfo();
				userinfo.setUserid(user.getUserid());
				// 编辑用户信息
				if (StringTools.trimNotEmpty(user.getPhone())) {
					//如果是存在手机号，则用之前德密码
					UserQuery ex = new UserQuery();
					ex.or().andPhoneEqualTo(user.getPhone())
							.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
					List<User> list = userDao.selectByExample(ex);
					if(list!=null && list.size()>0) {
						user.setPassword(list.get(0).getPassword());
					}else {
						user.setPassword(MD5Util.string2MD5(user.getPhone().substring(user.getPhone().length() - 6)));
					}
					
				}else {
					//如果手机号为空，则将绑定的第三方解绑
					LoginThirdExample example = new LoginThirdExample();
					example.or().andUseridEqualTo(user.getUserid())
										.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
					List<LoginThird> list = loginThirdMapper.selectByExample(example);
					if(list!=null && list.size()>0) {
						for(LoginThird t : list) {
							t.setDeleteflag(ConstantUtils.DELETE_TRUE);
							loginThirdMapper.updateByPrimaryKey(t);
						}
					}
				}
				user.setUpdateid(CurrentUserContext.getCurrentUserId());
				user.setUpdatetime(new Date());
				userDao.updateByPrimaryKeySelective(user);
				// 出生地
				userinfo.setBirthplace(userinfo.getBirthplaceP()+"@@"+userinfo.getBirthplaceC()+"@@"+userinfo.getBirthplaceX()+"@@"+userinfo.getBirthDetail());
				// 常住地
				userinfo.setHomeplace(userinfo.getHomeplaceP()+"@@"+userinfo.getHomeplaceC()+"@@"+userinfo.getHomeplaceX()+"@@"+userinfo.getHomeDetail());
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
				if(eduList.size() > 0) {
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
				if(workList.size() > 0) {
					userworkDao.insertEduExp(workList);
				}
				
				result = new Result(MsgConstants.RESUL_SUCCESS);
			} else {
				// 新增用户信息
//				boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), 1);
				boolean flag = checkFamilyUserNumber(1);
				if (flag == true) {
					String userId = UUIDUtils.getUUID();
					user.setUserid(userId);
					user.setDeleteflag(0);
					user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
					user.setStatus(0);
//					user.setIsdirect(1);
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
					user.setCreatetime(new Date());
					user.setUpdatetime(new Date());
					user.setUpdateid(CurrentUserContext.getCurrentUserId());
					user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(user.getUsername()));
					user.setPinyinfull(PinyinUtil.getPinyinFull(user.getUsername()));
					/*if (!user.getDietimeStr().equals("")) {
//						SimpleDateFormat sdfd = new SimpleDateFormat("yyy-MM-dd");
//						user.setDietime(sdfd.parse(user.getDietimeStr()));
						if(DateUtils.isDate(user.getDietimeStr(), "-"))
							user.setDietime(user.getDietimeStr());
					}*/
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
//						result = "500";
						result = new Result(MsgConstants.USER_SAVE_HAVEREPEAT);
					} else {
						// 保存用户信息
						userDao.insertSelective(user);
						// 出生地
						userinfo.setBirthplace(userinfo.getBirthplaceP()+"@@"+userinfo.getBirthplaceC()+"@@"+userinfo.getBirthplaceX()+"@@"+userinfo.getBirthDetail());
						// 常住地
						userinfo.setHomeplace(userinfo.getHomeplaceP()+"@@"+userinfo.getHomeplaceC()+"@@"+userinfo.getHomeplaceX()+"@@"+userinfo.getHomeDetail());
						// 保存用户详细信息
						userInfoDao.insertSelective(userinfo);
						
						// 循环保存教育经历
						List<Useredu> eduList = user.getUserEdu();
						for (Useredu useredu2 : eduList) {
							useredu2.setUserid(userId);
							useredu2.setEduid(UUIDUtils.getUUID());
						}
						if(eduList.size() > 0) {
							userEduDao.insertEduExp(eduList);
						}
						
						// 循环保存工作经历
						List<Userworkexp> workList = user.getUserWorkexp();
						for (Userworkexp userwork : workList) {
							userwork.setUserid(userId);
							userwork.setWorkid(UUIDUtils.getUUID());
						}
						if(workList.size() > 0) {
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
	public PageModel selectUserList(PageModel pageModel, User user, List<String> branchList) throws Exception {
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

	/* (non-Javadoc)
	 * @see com.jp.service.UserService#importUsers(org.springframework.web.multipart.MultipartFile, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public JsonResponse importUsers(MultipartFile file, HttpServletRequest request) throws Exception {
		// String result = "";
		Result result = null;
		JsonResponse res = null;
		String branchid  = request.getParameter("branchid");
		BranchKey branchkey = new BranchKey();
		branchkey.setBranchid(branchid);
		branchkey.setFamilyid(CurrentUserContext.getCurrentFamilyId());
		Branch branch = branchDao.selectByPrimaryKey(branchkey);
		if(branch==null) {
			/*result.setStatus(0);
			result.setMsg("当前分支不存在！");*/
			result = new Result(MsgConstants.USER_NO_BRANCH);
			res = new JsonResponse(result);
			return res;
		}
		
		if (file == null) {
			// result = "0";
			/*result.setStatus(0);
			result.setMsg("当前文件不存在！");*/
			result = new Result(MsgConstants.USER_NO_FILE);
			res = new JsonResponse(result);
			return res;
		}
		//查当前分支得用户
		UserQuery userExample = new UserQuery();
		userExample.or().andBranchidEqualTo(branch.getBranchid())
					.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		List<User> userAleardyList = userDao.selectByExample(userExample);
		
		List<User> userList = new ArrayList<User>();
		User user = null;
		List<Userinfo> userInfoList = new ArrayList<Userinfo>();
		Map<String, User> userMap = new LinkedHashMap<String, User>();
//		Map<String, User> userPhoneMap = new LinkedHashMap<String, User>();
		Userinfo userInfo = null;
		NumberFormat nf = new DecimalFormat("#");
		// 获取文件名称
		String name = file.getOriginalFilename();
		String excelid = UUIDUtils.getUUID();
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
//			boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), totalRows - 1);
			// 判断家族人数是否超出当前家族容纳人数上限
			boolean flag = checkFamilyUserNumber(totalRows - 1);
			if (flag == false) {
				/*result.setStatus(2);
				result.setMsg("导入用户数量超过版本最大用户限制!");*/
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
					if(education !=null) {
						if("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						}else if("高中".equals(education) || "中专".equals(education)) {
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
					//birthplace = replacePlace(fixplace);
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
					if(DateUtils.isDate(dietime, "-"))
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
					} else if("离世".equals(liveStatus)){
						user.setLivestatus(1);
					}else {
						user.setLivestatus(2);
					}
					if ("已婚".equals(iMarryied)) {
						user.setIsMarry(0);
					} else if("未婚".equals(iMarryied)){
						user.setIsMarry(1);
					}else {
						user.setIsMarry(2);
					}
					user.setFixplace(fixplace);
					
					if(userAleardyList !=null && userAleardyList.size()>0) {
						for(User uuu : userAleardyList) {
							if(uuu.getUsername().equals(username) && uuu.getGenlevel()==genlevel) {
								user.setIsnormal(0);
								user.setMsg("存在重名同世系用户请特殊处理！");
								break;
							}
							
						}
					}
					String key = ((int) genlevel - 1) + pname;
					if((pname==null || "".equals(pname))&&rowNum==1){
						if(userMap.containsKey((int) genlevel + username)) {
							user.setIsnormal(0);
							user.setMsg("存在重名同世系用户请特殊处理！");
						}
						userMap.put((int) genlevel + username, user);
						//如果父亲名字为空，则为分支起始人
						branch.setBeginuserid(userId);
						branch.setBeginname(username);
						branchDao.updateByBranchidSelective(branch);
					}else if (userMap.containsKey(key)) {
						User userTemp = userMap.get(key);
						user.setPid(userTemp.getUserid());
						if(userMap.containsKey((int) genlevel + username)) {
							user.setIsnormal(0);
							user.setMsg("存在重名同世系用户请特殊处理！");
						}
						userMap.put((int) genlevel + username, user);
					} else {
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
						//userInfo.setBirthday(birthday);
						if(DateUtils.isDate(birthday,"-")) {
							userInfo.setBirthday(birthday);
							int year = Integer.parseInt(birthday.split("-")[0]);
							userInfo.setZodica(ZodiacUtil.date2Zodica(year));
							String[] arr = birthday.split("-");
							if(arr.length==3){
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
					userList.add(user);
					userInfoList.add(userInfo);
//					boolean sameFlag = checkSameUser(user);
//					if (sameFlag) {
//						continue;
//					} else {
//						userList.add(user);
//						userInfoList.add(userInfo);
//					}
				}
			}
			
			if (userList != null && userList.size() > 0) {
				userImportMapper.importUser(userList);
			}
			if (userInfoList != null && userInfoList.size() > 0) {
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
//			boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), lastRowNum - 1);
			// 判断家族人数是否超出当前家族容纳人数上限
			boolean flag = checkFamilyUserNumber(lastRowNum - 1);
			if (flag == false) {
				/*result.setStatus(2);
				result.setMsg("导入用户数量超过版本最大用户限制!");*/
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
					if(education !=null) {
						if("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						}else if("高中".equals(education) || "中专".equals(education)) {
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
					//fixplace = replacePlace(fixplace);
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
					if(DateUtils.isDate(dietime, "-"))
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
					} else if("离世".equals(liveStatus)){
						user.setLivestatus(1);
					}else {
						user.setLivestatus(2);
					}
					if ("已婚".equals(iMarryied)) {
						user.setIsMarry(0);
					} else if("未婚".equals(iMarryied)){
						user.setIsMarry(1);
					}else {
						user.setIsMarry(2);
					}
					user.setFixplace(fixplace);
					String key = ((int) genlevel - 1) + pname;
					if(userAleardyList !=null && userAleardyList.size()>0) {
						for(User uuu : userAleardyList) {
							if(uuu.getUsername().equals(username) && uuu.getGenlevel()==genlevel) {
								user.setIsnormal(0);
								user.setMsg("存在重名同世系用户请特殊处理！");
								break;
							}
							
						}
					}
					if((pname==null || "".equals(pname))&& i==1){
						if(userMap.containsKey((int) genlevel + username)) {
							user.setIsnormal(0);
							user.setMsg("存在重名同世系用户请特殊处理！");
						}
						userMap.put((int) genlevel + username, user);
						//如果父亲名字为空，则为分支起始人
						branch.setBeginuserid(userId);
						branch.setBeginname(username);
						branchDao.updateByBranchidSelective(branch);
					}else if (userMap.containsKey(key)) {
						User userTemp = userMap.get(key);
						user.setPid(userTemp.getUserid());
						if(userMap.containsKey((int) genlevel + username)) {
							user.setIsnormal(0);
							user.setMsg("存在重名同世系用户请特殊处理！");
						}
						userMap.put((int) genlevel + username, user);
					} else {
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
						//userInfo.setBirthday(birthday);
						if(DateUtils.isDate(birthday,"-")) {
							userInfo.setBirthday(birthday);
							int year = Integer.parseInt(birthday.split("-")[0]);
							userInfo.setZodica(ZodiacUtil.date2Zodica(year));
							String[] arr = birthday.split("-");
							if(arr.length==3){
								userInfo.setConstellation(ZodiacUtil.date2Constellation(birthday));
							}
						}
							
					}
					userInfo.setBirthplace(birthplace);
					userInfo.setHomeplace(homeplace);
					userInfo.setNation(nation);
					userInfo.setBackground(background);
					userInfo.setRemark(remark);
					userList.add(user);
					userInfoList.add(userInfo);
//					boolean sameFlag = checkSameUser(user);
//					if (sameFlag) {
//						continue;
//					} else {
//						userList.add(user);
//						userInfoList.add(userInfo);
//					}
				}
			}
			if (userList != null && userList.size() > 0) {
				userImportMapper.importUser(userList);
			}
			if (userInfoList != null && userInfoList.size() > 0) {
				userInfoImportMapper.importUser(userInfoList);
			}

		}
		// result = "1";
//		result.setStatus(1);
		if (userList.size() < 1) {
			result = new Result(MsgConstants.USER_NO_IMPORT); 
			res = new JsonResponse(result);
//			result.setStatus(500);
			String userString = GsonUtil.GsonString(userStringList);
			res.setData(userString);
//			result.setData1(userString);
		}else{
			userStringList.add("本次共导入用户" + userList.size() + "人");
			String userString = GsonUtil.GsonString(userStringList);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userString);
			res.setEntity(excelid);
			/*result.setData(userList);
			result.setData1(userString);
			result.setData2(excelid);*/
		}
	//	result.setMsg("本次共导入用户" + userList.size() + "人");
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
//		if (user.getLivestatus().equals(0)) {
//			// 在世 需要判断手机号
//			List<String> phones = userDao.selectPhoneByFamilyId(user.getFamilyid());
//			if (StringTools.trimNotEmpty(user.getPhone()) && phones.contains(user.getPhone())) {
//				sameFlag = true;
//				userStringList.add("第"+user.getGenlevel()+"世:"+user.getUsername()+"手机号:"+user.getPhone()+"已存在");
//				log_.info("数据库有重复数据无法导入-" + user.toString());
//				System.out.println("数据库有重复数据无法导入-" + user.toString());
//			} else {
//				sameFlag = false;
//			}
//		} else {
			// 离世 不需要判断手机号 使用用户名和父亲名字判断及手机号码判断
			List<User> searchRt = userDao.selectUserByFamilyId(user.getFamilyid());
			for (User userf : searchRt) {
				if (StringUtil.isNotEmpty(userf.getUsername()) && userf.getUsername().equals(user.getUsername())&&userf.getGenlevel().equals(user.getGenlevel())) {
					if(userf.getPhone() != null && userf.getPhone().equals(user.getPhone())) {
						if (StringUtil.isNotEmpty(userf.getPname()) ) {
							if(StringUtil.isNotEmpty(user.getPname())&&userf.getPname().equals(user.getPname())){
							sameFlag = true;
							userStringList.add("第"+user.getGenlevel()+"世:"+user.getUsername()+"已存在");
							log_.info("数据库有重复数据无法导入-" + user.toString());
							System.out.println("数据库有重复数据无法导入-" + user.toString());
							return sameFlag;
							}					
						}
						if (StringUtil.isEmpty(userf.getPname()) ) {
							if(StringUtil.isEmpty(user.getPname())&&userf.getGenlevel()==user.getGenlevel()){
								sameFlag = true;
								userStringList.add("第"+user.getGenlevel()+"世:"+user.getUsername()+"已存在");
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
//		}
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
	private boolean checkSameMateUser(User user,List<String> userStringList) {
		boolean sameFlag = false;
		// 加入list前 判断去重复

			// 离世 不需要判断手机号 使用用户名和父亲名字判断
			List<User> searchRt = userDao.selectUserByFamilyId(user.getFamilyid());
			for (User userf : searchRt) {
				if (StringUtil.isNotEmpty(userf.getUsername()) && userf.getUsername().equals(user.getUsername())&&userf.getGenlevel()==user.getGenlevel()) {
					if (StringUtil.isNotEmpty(userf.getMatename()) && userf.getMatename().equals(user.getMatename())) {
						sameFlag = true;
						userStringList.add("第"+user.getGenlevel()+"世:"+user.getUsername()+"已存在");
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
//			UserQuery uq = new UserQuery();
//			uq.or().andUsernameEqualTo(user.getUsername())
//					.andMatenameEqualTo(user.getMatename())
//					.andGenlevelEqualTo(user.getGenlevel())
//					.andBranchidEqualTo(user.getBranchid())
//					.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
					
		return sameFlag;
	}


	/**
	 * 验证配偶是否有重复
	 */
	private boolean checkSameMates(Usermates userMates) {
		boolean sameFlag = false;
		List<Usermates> matelist = userMatesDao.selectmateIdByUserId(userMates.getUserid());
		for (Usermates userm : matelist) {
			if ((StringUtil.isNotEmpty(userm.getName()) && userm.getName().equals(userMates.getName())) && (StringUtil.isNotEmpty(userm.getMatetype()) && userm.getMatetype().equals(userMates.getMatetype()))) {
				sameFlag = true;
				userMateStringList.add(userm.getName()+"已存在");
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
		// String result = "";
		String msg = "";
//		Result result = new Result();
		Result result= null;
		JsonResponse res = null;
		String branchid  = request.getParameter("branchid");
		BranchKey branchkey = new BranchKey();
		branchkey.setBranchid(branchid);
		branchkey.setFamilyid(CurrentUserContext.getCurrentFamilyId());
		Branch branch = branchDao.selectByPrimaryKey(branchkey);
		if(branch==null) {
			/*result.setStatus(0);
			result.setMsg("当前分支不存在！");*/
			result = new Result(MsgConstants.USER_NO_BRANCH);
			res = new JsonResponse(result);
			return res;
		}
		
		if (file == null) {
			// result = "0";
			//result.setStatus(0);
			result = new Result(MsgConstants.USER_NO_FILE);
			res = new JsonResponse(result);
			return res;
		}
		Map<String, User> userPhoneMap = new HashMap<>();
		List<User> userList = new ArrayList<User>();
//		User user = null;
		Usermates userMates = null;
		List<Usermates> userMatesList = new ArrayList<Usermates>();
		// 批量更新 已经存在的用户
		List<User> userAleardyListUpdate = new ArrayList<User>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 查询用户列表
//		User userAleardy = new User();
//		userAleardy.setFamilyid(CurrentUserContext.getCurrentFamilyId());
//		userAleardy.setIsdirect(1);
//		userAleardy.setIsMarry(0);
		// 获取文件名称
				String name = file.getOriginalFilename();
				NumberFormat nf = new DecimalFormat("#");
				List<String> userStringList=new ArrayList<String>();
				//获取导入的分支名字
				//String branchname =  name.substring(0, name.indexOf("世系"));
		//
		//List<String> branchList = CurrentUserContext.getCurrentBranchIds();
//		Integer type = CurrentUserContext.getUserContext().getUsermanagers().get(0).getEbtype();
//		Integer manager = CurrentUserContext.getUserContext().getUsermanagers().get(0).getIsmanager();
//		if (type != 1 && manager != 1) {
//			branchList.clear();
//		}
		UserQuery userExample = new UserQuery();
		userExample.or().andBranchidEqualTo(branch.getBranchid())
					.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		List<User> userAleardyList = userDao.selectByExample(userExample);
		
//		Map<String, User> userAleardyMap = new HashMap<String, User>();
//		if (userAleardyList != null && userAleardyList.size() > 0) {
//			for (int i = 0; i < userAleardyList.size(); i++) {
//				userAleardyMap.put(userAleardyList.get(i).getGenlevel() + userAleardyList.get(i).getUsername(), userAleardyList.get(i));
//			}
//		}
		String excelid = UUIDUtils.getUUID();
		Userinfo userInfo ;
		List<Userinfo> userInfoList = new ArrayList<Userinfo>();
		if (name.indexOf(".xlsx") != -1) {
			XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
			// 循环三sheet
			for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				int totalRows = xssfSheet.getLastRowNum();
//				boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), totalRows - 1);
				boolean flag = checkFamilyUserNumber(totalRows - 1);
				if (flag == false) {
					/*result.setStatus(2);
					result.setMsg("导入配偶数量超过版本最大用户限制!");
					return result;*/
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
						if(education !=null) {
							if("大学本科".equals(education) || "大学".equals(education)) {
								education = "本科";
							}else if("高中".equals(education) || "中专".equals(education)) {
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
						//birthplace = replacePlace(fixplace);
						String remark = xssfRow.getCell(17).getStringCellValue().trim();// 简介
						String userId = UUIDUtils.getUUID();
						
						if (ifMates.equals("妻子") || ifMates.equals("丈夫") 
								|| ifMates.equals("元配") || ifMates.equals("继配")  
								||ifMates.equals("次配") && liveStatus.equals("在世")) {
							// 如果配偶存在
							if (userAleardyList != null && userAleardyList.size() > 0) {
								int n = 0;//计数是否存在多个世系一样并且名字一样的用户
								User userAl = null;
								for(int i = 0;i< userAleardyList.size();i++) {
									User u = userAleardyList.get(i);
									if(u.getGenlevel()==(int)genlevel && u.getUsername().equals(husbandname)&&(u.getMateid()==null||"".equals(u.getMateid()))) {
										n++;
										if(n>1) {
											break;
										}else {
											userAl = u;
										}
									}
								}
								
								if(n==1) {
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
									if(DateUtils.isDate(dietime, "-"))
										user.setDietime(dietime);
									//user.setDietime(dietime);
									if ("在世".equals(liveStatus)) {
										user.setLivestatus(0);
										// 去除在世用户中手机重复的数据
                                        // if (userPhoneMap.containsKey(user.getPhone())) {
                                        // continue;
                                        // } else {
                                        // userPhoneMap.put(user.getPhone(), user);
                                        // }
									} else if("离世".equals(liveStatus)){
										user.setLivestatus(1);
									}else{
										user.setLivestatus(2);
									}
									user.setFixplace(fixplace);
									user.setCreatetime(new Date());
									// 维护当前配偶id
									
									
									// 维护当前配偶id
									if(userAl==null) {
										user.setIsnormal(0);
										user.setMsg("请检查配偶姓名或世系！");
									}
//									else if(userAl.getMateid()!=null && !"".equals(userAl.getMateid())) {
//										user.setIsnormal(0);
//										user.setMsg("请勿重复导入配偶！");
//									}
									else {
										userAl.setMateid(userId);
										userAl.setMatename(username);
										user.setMateid(userAl.getUserid());
										
										//user.setBranchid(userAl.getBranchid());
										//user.setBranchname(userAl.getBranchname());
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
									
//									boolean sameFlag = checkSameUser(user);
									boolean sameFlag = checkSameMateUser(user,userStringList);	
									if (sameFlag) {
										user.setIsnormal(0);
										user.setMsg("请勿重复导入配偶！");
										continue;
									} else {
										
										userAleardyListUpdate.add(userAl);
									}
									userInfo = new Userinfo();
									userInfo.setUserid(userId);
									if (StringTools.trimNotEmpty(birthday)) {
										//userInfo.setBirthday(birthday);
										if(DateUtils.isDate(birthday,"-")) {
											userInfo.setBirthday(birthday);
											int year = Integer.parseInt(birthday.split("-")[0]);
											userInfo.setZodica(ZodiacUtil.date2Zodica(year));
											String[] arr = birthday.split("-");
											if(arr.length==3){
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
								}else {
									msg += username+",";
								}
								//if (userAleardyMap.containsKey((int) genlevel + husbandname) && (StringTools.trimNotEmpty(userAleardyMap.get(genlevel + husbandname).getMateid()))) {}
							}
						} else {
							userMates = new Usermates();
							if (userAleardyList != null && userAleardyList.size() > 0) {
								int n = 0;//计数是否存在多个世系一样并且名字一样的用户
								for(int i = 0;i< userAleardyList.size();i++) {
									User u = userAleardyList.get(i);
									if(u.getGenlevel()==(int)genlevel && u.getUsername().equals(husbandname)&&StringTools.trimNotEmpty(u.getMateid())) {
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
										//userMates.setBirthday(birthday);
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
							//if (userAleardyMap.containsKey(genlevel + husbandname)) {}
						}
					}
				}
				// 批量导入妻子或丈夫配偶 user
				if (userList != null && userList.size() > 0) {
					userImportMapper.importUser(userList);
				}

				// 批量更新 已存在的用户
				if (userAleardyListUpdate != null && userAleardyListUpdate.size() > 0) {
					userDao.updateAleardyUser(userAleardyListUpdate);
				}

				// 批量插入 配偶 表
				if (userMatesList != null && userMatesList.size() > 0) {
					userMatesDao.insertMatesList(userMatesList);
				}
				// 批量插入 配偶 表
				if (userInfoList != null && userInfoList.size() > 0) {
					userInfoImportMapper.importUser(userInfoList);
				}
				// result ="1";
//				result.setStatus(1);
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
//			boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), lastRowNum - 1);
			boolean flag = checkFamilyUserNumber(lastRowNum - 1);
			if (flag == false) {
				/*result.setStatus(2);
				result.setMsg("导入配偶数量超过版本最大用户限制!");
				return result;*/
				result = new Result(MsgConstants.USER_SAVE_OUTMAX);
				res = new JsonResponse(result);
				return res;
			}
			userMateStringList = new ArrayList<String>();
			for (int i = 1; i < lastRowNum + 1; i++) {
				
				if (sheet.getRow(i) != null) {
					String genlevel = eutil.toDecimalFormat(eutil.getCellContent(sheet.getRow(i).getCell(0)).trim());// 世系
					String username = eutil.getCellContent(sheet.getRow(i).getCell(1)).trim();//用户名
					if (!StringTools.trimNotEmpty(username)) {
						continue;
					}
					String sex = eutil.getCellContent(sheet.getRow(i).getCell(2)).trim();//性别
					String liveStatus = eutil.getCellContent(sheet.getRow(i).getCell(3)).trim();//在世离世
					String phone = eutil.getCellContent(sheet.getRow(i).getCell(4)).trim();//手机号
					if (StringTools.trimNotEmpty(phone)) {
						phone = nf.format(Double.parseDouble(phone));
					}
					String husbandname = eutil.getCellContent(sheet.getRow(i).getCell(5)).trim();//配偶姓名
					String ifMates = eutil.getCellContent(sheet.getRow(i).getCell(6)).trim();//关系
					String usedname = eutil.getCellContent(sheet.getRow(i).getCell(7)).trim();//曾用名
					String nation = eutil.getCellContent(sheet.getRow(i).getCell(8)).trim();//民族
					String background = eutil.getCellContent(sheet.getRow(i).getCell(9)).trim();//政治背景
					String idCard = eutil.getCellContent(sheet.getRow(i).getCell(10)).trim();//身份证号	
					String education = eutil.getCellContent(sheet.getRow(i).getCell(11)).trim();//学历
					if(education !=null) {
						if("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						}else if("高中".equals(education) || "中专".equals(education)) {
							education = "高中(中专)";
						}
						
					}
					String birthday = eutil.getCellContent(sheet.getRow(i).getCell(12)).trim();//生日
					
					if (StringTools.trimNotEmpty(idCard)) {
						idCard = nf.format(Double.parseDouble(idCard));
					}
					
					String birthplace = eutil.getCellContent(sheet.getRow(i).getCell(13)).trim();//出生地
					birthplace = replacePlace(birthplace);
					String homeplace = eutil.getCellContent(sheet.getRow(i).getCell(14)).trim();//常住地
					homeplace = replacePlace(homeplace);
					String dietime = eutil.getCellContent(sheet.getRow(i).getCell(15)).trim();//趋势时间
					String fixplace = eutil.getCellContent(sheet.getRow(i).getCell(16)).trim();//安葬地
					//birthplace = replacePlace(fixplace);
					String remark = eutil.getCellContent(sheet.getRow(i).getCell(17)).trim();//个人简介
					String userId = UUIDUtils.getUUID();
					
					if (ifMates.equals("妻子") || ifMates.equals("丈夫") 
							|| ifMates.equals("元配") || ifMates.equals("继配")  
							||ifMates.equals("次配") ) {
						// 如果配偶存在
						if (userAleardyList != null && userAleardyList.size() > 0) {
							int n = 0;//计数是否存在多个世系一样并且名字一样的用户
							User userAl = null;
							for(int k = 0;k< userAleardyList.size();k++) {
								
								User u = userAleardyList.get(k);
								if(u.getGenlevel()==Integer.parseInt(genlevel) && u.getUsername().equals(husbandname)) {
									n++;
									if(n>1) {
										break;
									}else {
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
								if(DateUtils.isDate(dietime, "-"))
									user.setDietime(dietime);
								//user.setDietime(DateUtils.stringToDate(dietime));
								if ("在世".equals(liveStatus)) {
									user.setLivestatus(0);
									// 去除在世用户中手机重复的数据
                                    // if (userPhoneMap.containsKey(user.getPhone())) {
                                    // continue;
                                    // } else {
                                    // userPhoneMap.put(user.getPhone(), user);
                                    // }
								} else if("离世".equals(liveStatus)){
									user.setLivestatus(1);
								} else {
									user.setLivestatus(2);
								}
								user.setFixplace(fixplace);
								user.setCreatetime(new Date());
								// 维护当前配偶id
								if(userAl==null) {
									user.setIsnormal(0);
									user.setMsg("请检查配偶姓名或世系！");
								}
//								else if(userAl.getMateid()!=null && !"".equals(userAl.getMateid())) {
//									user.setIsnormal(0);
//									user.setMsg("请勿重复导入配偶！");
//								}
								else {
									userAl.setMateid(userId);
									userAl.setMatename(username);
									user.setMateid(userAl.getUserid());
									//user.setMatename(userAl.getUsername());
									//user.setBranchid(userAl.getBranchid());
									//user.setBranchname(userAl.getBranchname());
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
//								boolean sameFlag = checkSameUser(user);
								userList.add(user);
								boolean sameFlag = checkSameMateUser(user,userStringList);	
								if (sameFlag) {
									user.setIsnormal(0);
									user.setMsg("请勿重复导入配偶！");
									continue;
								} else {
									userAleardyListUpdate.add(userAl);
								}
								
								if(n>1) {
									user.setMsg("当前用户存在多个同名同世系配偶！");
								}else {
								
								
								}
								
								userInfo = new Userinfo();
								userInfo.setUserid(userId);
								if (StringTools.trimNotEmpty(birthday)) {
									//userInfo.setBirthday(birthday);
									if(DateUtils.isDate(birthday,"-")) {
										userInfo.setBirthday(birthday);
										int year = Integer.parseInt(birthday.split("-")[0]);
										userInfo.setZodica(ZodiacUtil.date2Zodica(year));
										String[] arr = birthday.split("-");
										if(arr.length==3){
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
							//if (userAleardyMap.containsKey((int) genlevel + husbandname) && (StringTools.trimNotEmpty(userAleardyMap.get(genlevel + husbandname).getMateid()))) {}
						}
					} else {
						userMates = new Usermates();
						if (userAleardyList != null && userAleardyList.size() > 0) {
							int n = 0;//计数是否存在多个世系一样并且名字一样的用户
							for(int k = 0;k< userAleardyList.size();k++) {
								User u = userAleardyList.get(k);
								if(u.getGenlevel()==Integer.parseInt(genlevel) && u.getUsername().equals(husbandname)&&StringTools.trimNotEmpty(u.getMateid())) {
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
			if (userList != null && userList.size() > 0) {
				userImportMapper.importUser(userList);
			}

			// 批量更新 已存在的用户
			if (userAleardyListUpdate != null && userAleardyListUpdate.size() > 0) {
				userDao.updateAleardyUser(userAleardyListUpdate);
			}

			// 批量插入 配偶 表
			if (userMatesList != null && userMatesList.size() > 0) {
				userMatesDao.insertMatesList(userMatesList);
			}
			// 批量插入info
			if (userInfoList != null && userInfoList.size() > 0) {
				userInfoImportMapper.importUser(userInfoList);
			}
			// result ="1";
			// result.setStatus(1);
			// 如果符合导入条件的配偶信息条数不为零
			if (userMatesList.size() < 1&&userList.size()<1) {
				/*result.setStatus(500);
				result.setData1(userMatesString);*/
				String userMatesString = GsonUtil.GsonString(userMateStringList);
				result = new Result(MsgConstants.USER_NO_IMPORT);
				res = new JsonResponse(result);
				res.setData(userMatesString);
			}else{
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				userMateStringList.add("本次共导入配偶" + userMatesList.size() + "人");
				String userMatesString = GsonUtil.GsonString(userMateStringList);
				res.setData(userList);
				res.setEntity(userMatesString);
				/*result.setData(userList);
				result.setData1(userMatesString);
				result.setData2(excelid);*/
			}
			/*String msString="";
			if(userStringList.size()>0){
				for (String ms : userStringList) {
					msString+=ms+"\r\n,";
				}
			}
			result.setMsg(msString+"本次共导入在世配偶(用户)" + userList.size() + "人,离世配偶" + userMatesList.size() + "人，未导入"+msg);*/
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
			// 丈夫或妻子
			String userid = UUIDUtils.getUUID();
			String birthplace = userInfo.getBirthplaceP()+"@@"+userInfo.getBirthplaceC()+"@@"+userInfo.getBirthplaceX();
			if (matetype == 0 || matetype == 1) {
				if (StringTools.trimNotEmpty(user.getMateid())) {
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
					/*if (!userInfo.getBirthdayStr().equals("")) {
//						SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
//						userInfo.setBirthday(sdfd.parse(userInfo.getBirthdayStr()));
						if(DateUtils.isDate(userInfo.getBirthdayStr(), "-"))
							userInfo.setBirthday(userInfo.getBirthdayStr());
					}*/
					userInfo.setBirthplace(birthplace);
					userInfoDao.updateByPrimaryKeySelective(userInfo);
				} else {
//					boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), 1);
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
							/*if (!user.getDietimeStr().equals("")) {
//								SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
//								userMmate.setDietime(sdfd.parse(user.getDietimeStr()));
								if(DateUtils.isDate(user.getDietimeStr(), "-"))
									user.setDietime(user.getDietimeStr());
							}*/
						} else {
							userMmate.setLivestatus(user.getLivestatus());
						}

						/*
						 * if(matetype == 0){ userMmate.setMatename("丈夫");
						 * }else{ userMmate.setMatename("妻子"); }
						 */
						boolean sameFlag = checkSameUser(userMmate);
						if (sameFlag) {
							// 重复什么也不做
							// result = "500";
							result = new Result(MsgConstants.USER_SAVE_HAVEREPEAT);
						} else {
							userDao.insertSelective(userMmate);
							userDao.addmateid(user.getUserid(), userid, user.getMatename());
							// userinfo 表
							userInfo.setUserid(userid);
							/*if (!userInfo.getBirthdayStr().equals("")) {
//								SimpleDateFormat sdfd = new SimpleDateFormat("yyy-MM-dd");
//								userInfo.setBirthday(sdfd.parse(userInfo.getBirthdayStr()));
								if(DateUtils.isDate(userInfo.getBirthdayStr(), "-"))
									userInfo.setBirthday(userInfo.getBirthdayStr());
							}*/
							userInfoDao.insertSelective(userInfo);
//							result = "1";
							result = new Result(MsgConstants.RESUL_SUCCESS);
						}
					} else {
//						result = "2";
						result = new Result(MsgConstants.USER_SAVE_OUTMAX);
						return result;
					}
				}
			} else {// 配偶做为记录
				Usermates userMates = new Usermates();
				// userMates.setBirthday(user.get);
				userMates.setMateid(userid);
				userMates.setName(user.getMatename());
				userMates.setUserid(user.getUserid());
				userMates.setSex(user.getSex());
				userMates.setRemark(userInfo.getRemark());
				userMates.setNation(userInfo.getNation());
				userMates.setBirthplace(birthplace);
				userMates.setMatetype(user.getMatetypeStr());
				/*if (!userInfo.getBirthdayStr().equals("")) {
					SimpleDateFormat sdfd = new SimpleDateFormat("yyy-MM-dd");
					userMates.setBirthday(sdfd.parse(userInfo.getBirthdayStr()));
				}*/
				boolean sameFlag = checkSameMates(userMates);
				if (sameFlag) {
					// 重复什么也不做
					// result = "500";
					result = new Result(MsgConstants.USER_SAVE_HAVEREPEAT);
				} else {
					userMatesDao.insertSelective(userMates);
//					result = "1";
					result = new Result(MsgConstants.RESUL_SUCCESS);
				}
			}
		} catch (Exception e) {
//			result = "0";
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
//		Result result = new Result();
		Result result = new Result(MsgConstants.RESUL_FAIL);
		User record = new User();
		record.setUserid(CurrentUserContext.getCurrentUserId());
		record.setPassword(string2md5);
		int updateRt = userDao.updateByPrimaryKeySelective(record);
		if (updateRt != 1) {
//			result.setStatus(0);
			return result;
		}
//		result.setStatus(updateRt);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		return result;
	}

	@Override
	public JsonResponse importUsersNew(MultipartFile file, HttpServletRequest request) {

		// String result = "";
//		Result result = new Result();
		Result result= null;
		JsonResponse res = null;
		if (file == null) {
			// result = "0";
			/*result.setStatus(0);
			return result;*/
			result = new Result(MsgConstants.USER_NO_FILE);
			res = new JsonResponse(result);
			return res;
		}
		List<User> userList = new ArrayList<User>();
		User user = null;
		List<Userinfo> userInfoList = new ArrayList<Userinfo>();
		Map<String, User> userMap = new LinkedHashMap<String, User>();
//		Map<String, User> userPhoneMap = new LinkedHashMap<String, User>();
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
//			boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), totalRows - 1);
			boolean flag = checkFamilyUserNumber(totalRows - 1);
			if (flag == false) {
				/*result.setStatus(2);
				result.setMsg("导入用户数量超过版本最大用户限制!");
				return result;*/
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
					if(education !=null) {
						if("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						}else if("高中".equals(education) || "中专".equals(education)) {
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
					//birthplace = replacePlace(fixplace);
					String remark = xssfRow.getCell(20).getStringCellValue().trim();// 个人简介
					String userId = UUIDUtils.getUUID();
					user = new User();
					user.setUserid(userId);
					user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
					user.setFamilyname(CurrentUserContext.getCurrentFamilyName());
					user.setStatus(0);
					if(!"".equals(isdireect) && "直系".equals(isdireect)) {
						user.setIsdirect(1);
					}else {
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
//					if (userMap.containsKey(key)) {
//						User userTemp = userMap.get(key);
//						user.setPid(userTemp.getUserid());
//						userMap.put((int) genlevel + username, user);
//					} else {
//						userMap.put((int) genlevel + username, user);
//					}
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
//					boolean sameFlag = checkSameUser(user);
//					if (sameFlag) {
//						continue;
//					} else {
//						userList.add(user);
//						userInfoList.add(userInfo);
//					}
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
//			boolean flag = limitUserNumber(CurrentUserContext.getCurrentFamilyId(), lastRowNum - 1);
			boolean flag = checkFamilyUserNumber(lastRowNum - 1);
			if (flag == false) {
				/*result.setStatus(2);
				result.setMsg("导入用户数量超过版本最大用户限制!");
				return result;*/
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
					String education = eutil.getCellContent(sheet.getRow(i).getCell(13)).trim();//学历
					if(education !=null) {
						if("大学本科".equals(education) || "大学".equals(education)) {
							education = "本科";
						}else if("高中".equals(education) || "中专".equals(education)) {
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
					//birthplace = replacePlace(fixplace);
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
						//user.setDietime(formatter.parse(dietime));
						if(DateUtils.isDate(dietime, "-"))
							user.setDietime(dietime);
					}

					userInfo = new Userinfo();
					userInfo.setUserid(userId);
					if (StringTools.trimNotEmpty(birthday)) {
						//userInfo.setBirthday(formatter.parse(birthday));
						if(DateUtils.isDate(userInfo.getBirthdayStr(), "-"))
							userInfo.setBirthday(userInfo.getBirthdayStr());
					}
					userInfo.setBirthplace(birthplace);
					userInfo.setHomeplace(homeplace);
					userInfo.setNation(nation);
					userInfo.setBackground(background);
					userInfo.setRemark(remark);
					//检查本次导入用户是否有同名同世系的用户
					userList.add(user);
//					boolean sameFlag = checkSameUser(user);
//					if (sameFlag) {
//						continue;
//					} else {
//						userList.add(user);
//						userInfoList.add(userInfo);
//					}
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
//		result.setStatus(1);
		if (userList.size() < 1) {
//			result.setStatus(500);
			String userString = GsonUtil.GsonString(userStringList);
//			result.setData1(userString);
			result = new Result(MsgConstants.USER_NO_IMPORT);
			res = new JsonResponse(result);
			res.setData(userString);
		}else{
			userStringList.add("本次共导入用户" + userList.size() + "人");
			String userString = GsonUtil.GsonString(userStringList);
//			result.setData1(userString);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userString);
		}
	//	result.setMsg("本次共导入用户" + userList.size() + "人");
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
		if(excelid == null || "".equals(excelid)){
			/*result.setStatus(ConstantUtils.RESULT_FAIL);
			result.setMsg("导入失败！");*/
			result = new Result(MsgConstants.RESUL_FAIL);
			return result;
		}
		//1.查临时表的数据
		UserImportExample example = new UserImportExample();
		example.or().andExcelidEqualTo(excelid);
		List<User> users = userImportMapper.selectByExample(example);
		//2.将临时表数据插入主表
		userDao.importUser(users);
		
		for(User u : users) {
//			if(u.getMateid()!= null || !"".equals(u.getMateid())) {
//				User uu = userImportMapper.selectByPrimaryKey(u.getMateid());
//				userDao.updateByPrimaryKey(uu);
//			}
			Userinfo info = userInfoImportMapper.selectByPrimaryKey(u.getUserid());
			if(info!=null)
			userInfoDao.insertSelective(info);
		}
		
		
		/*result.setStatus(ConstantUtils.RESULT_SUCCESS);
		result.setMsg("导入成功");*/
		result = new Result(MsgConstants.RESUL_SUCCESS);
		return result;
	}

	public String replacePlace(String place) {
		if(place.indexOf("@@")==-1) {
			place = "@@@@@@"+place.trim();
		}
		return place;
	}
	
	public boolean checkFamilyUserNumber(int importCount) {
		boolean checkResult = false;
		Integer priValue = 0;		// 最多容纳家族人数
		SysVersionPrivilege versionP = sysVersionPrivilegeMapper.selectByVersionAndCode(CurrentUserContext.getCurrentFamilyId(), ConstantUtils.VERSION_USERCOUNT);
		if(versionP != null && versionP.getPrivilegevalue() != null) {
			if(versionP.getPrivilegevalue().equals(ConstantUtils.VERSION_UNLIMITED)) {
				// 钻石豪华版容纳家族人数不限
				checkResult = true;
				return checkResult;
			}
			priValue = Integer.valueOf(versionP.getPrivilegevalue());
		}
		// 获取该用户所在家族已有的人数
		UserQuery userExample = new UserQuery();
		userExample.or().andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
		int haveUserCount = userDao.countByExample(userExample); 	// 家族已有人数
		if(priValue > 0 && (priValue - haveUserCount - importCount) >=0 ) {
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
			if(StringUtils.isBlank(user.getUserid())) {
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
	
	
}