
package com.jp.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.LoginUserInfo;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.dao.UserDao;
import com.jp.dao.UserbranchDao;
import com.jp.dao.UsermatesDao;
import com.jp.entity.Branch;
import com.jp.entity.Function;
import com.jp.entity.SysVersionPrivilege;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserQuery;
import com.jp.entity.Useralbum;
import com.jp.entity.UseralbumKey;
import com.jp.entity.Userbranch;
import com.jp.entity.UserbranchQuery;
import com.jp.entity.Useredu;
import com.jp.entity.Userinfo;
import com.jp.entity.Usermates;
import com.jp.entity.Userphoto;
import com.jp.entity.UserphotoKey;
import com.jp.entity.Userworkexp;
import com.jp.service.BranchService;
import com.jp.service.FunctionService;
import com.jp.service.UserEduService;
import com.jp.service.UserInfoService;
import com.jp.service.UserManagerService;
import com.jp.service.UserService;
import com.jp.service.UserWorkService;
import com.jp.service.UseralbumService;
import com.jp.util.DateUtils;
import com.jp.util.GsonUtil;
import com.jp.util.MD5Util;
//import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.UploadUtil;

@Controller
@RequestMapping("user")
public class UserController {
	private final Logger log_ = LogManager.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private UserWorkService userWorkService;
	@Autowired
	private UserEduService usereduService;
	@Autowired
	private UseralbumService useralbumService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UsermatesDao userMatesDao;
	@Autowired
	private UserbranchDao userBranchDao;
	@Autowired
	private BranchDao branchDao;
	@Autowired 
	private SysVersionPrivilegeMapper sysVersionPrivilegeMapper;
	@Autowired
	private UserManagerService userManagerService;
	@Autowired
	private FunctionService functionService;
	

	/**
	 * 
	 * @描述 用户保存或编辑
	 * @作者 sj
	 * @时间 2017年4月28日上午9:17:16
	 * @参数 @param user
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public JsonResponse mergeUser(HttpServletRequest request,User user, Userinfo userInfo, Useredu userEdu, ModelMap model, String eduExpArray,
			String workExpArray, String parentName, String fatherName) {
//		String result = null;
		Result result = null;
		JsonResponse res = null;
		try {
			if (!fatherName.equals("-1")) {
				user.setPid(fatherName);
                User userfather = userService.selectByPrimaryKey(fatherName);
                user.setBranchid(userfather.getBranchid());
                user.setBranchname(userfather.getBranchname());
				User userP = userService.selectByPrimaryKey(fatherName);
				if (userP != null && userP.getGenlevel() != null) {
					user.setGenlevel(userP.getGenlevel() + 1);
					user.setPname(userP.getUsername());
				}
			} else {
				user.setGenlevel(Integer.valueOf(parentName));
			}
			String isMarry = request.getParameter("isMarry");
			String isdirect = request.getParameter("isdirectjj");
			user.setIsMarry(Integer.valueOf(isMarry));
			user.setIsdirect(Integer.valueOf(isdirect));
			result = userService.merge(user, userInfo, userEdu, eduExpArray, workExpArray);
			res = new JsonResponse(result);
//			result = res;
		} catch (Exception e) {
//			result = "0";
			result = new Result(MsgConstants.USER_SAVE_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
		return res;
	}

	
	/**
	 * 
	 * @描述 逻辑删除用户
	 * @作者 chenxiaobing
	 * @时间 2019年1月15日下午2:24:07
	 * @参数 @param user
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JsonResponse deleteUser(String userids) {
//		Integer result=0;
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = userids.substring(0, userids.length());
			String useridArray[] = userid.split(",");
			Integer count =userService.del(useridArray);
			if(count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
			}else {
				result = new Result(MsgConstants.RESUL_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.RESUL_FAIL);
		}
		res = new JsonResponse(result);
		return res;
	}
	
	/**
	 * 
	 * @描述 去新增修改界面
	 * @作者 sj
	 * @时间 2017年5月1日下午2:24:07
	 * @参数 @param user
	 * @参数 @param userInfo
	 * @参数 @param userEdu
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public JsonResponse editUser(HttpServletRequest request, ModelMap modelMap) {
//		String result = null;
		Result result = null;
		JsonResponse res = null;
		SimpleDateFormat sdfd = new SimpleDateFormat("yyy-MM-dd");
		try {
			String userid = request.getParameter("userid");
			User user = null;
			Userinfo userInfo = null;
			List<Useredu> eduList = null;
			List<Userworkexp> workList = null;
			// selectByPrimaryKey
			if (StringTools.trimNotEmpty(userid)) {
				user = userService.selectByPrimaryKey(userid);
				if (user != null) {
					if (user.getDietime() != null) {
						user.setDietimeStr(user.getDietime());
					}
				}
				userInfo = userInfoService.selectByPrimaryKey(userid);
				if (userInfo != null) {
					if (userInfo.getBirthday() != null) {
						userInfo.setBirthdayStr(userInfo.getBirthday());
					}
				}
				if (userInfo != null) {
					String birth = userInfo.getBirthplace();
					if (StringTools.trimNotEmpty(birth)) {
						String births[] = birth.split("@@");
						if (births.length == 1) {
							userInfo.setBirthplaceP(births[0]);
						} else if (births.length == 2) {
							userInfo.setBirthplaceP(births[0]);
							userInfo.setBirthplaceC(births[1]);
						} else if (births.length == 3) {
							userInfo.setBirthplaceP(births[0]);
							userInfo.setBirthplaceC(births[1]);
							userInfo.setBirthplaceX(births[2]);
						} else if (births.length == 4) {
							userInfo.setBirthplaceP(births[0]);
							userInfo.setBirthplaceC(births[1]);
							userInfo.setBirthplaceX(births[2]);
							userInfo.setBirthDetail(births[3]);
						}
					}

					String home = userInfo.getHomeplace();
					if (StringTools.trimNotEmpty(home)) {
						String homes[] = home.split("@@");
						if (homes.length == 1) {
							userInfo.setHomeplaceP(homes[0]);
						} else if (homes.length == 2) {
							userInfo.setHomeplaceP(homes[0]);
							userInfo.setHomeplaceC(homes[1]);
						} else if (homes.length == 3) {
							userInfo.setHomeplaceP(homes[0]);
							userInfo.setHomeplaceC(homes[1]);
							userInfo.setHomeplaceX(homes[2]);
						} else if (homes.length == 4) {
							userInfo.setHomeplaceP(homes[0]);
							userInfo.setHomeplaceC(homes[1]);
							userInfo.setHomeplaceX(homes[2]);
							userInfo.setHomeDetail(homes[3]);
						}

					}
				}
				workList = userWorkService.selectByUserId(userid);
				for (int i = 0; i < workList.size(); i++) {
					if (workList.get(i).getDatefrom() != null) {
						workList.get(i).setDatefromStr(DateUtils.dayToString(workList.get(i).getDatefrom()));
					}
					if (workList.get(i).getDateto() != null) {
						workList.get(i).setDatetoStr(DateUtils.dayToString(workList.get(i).getDateto()));
					}
				}
				eduList = usereduService.selectByUserId(userid);
				for (int i = 0; i < eduList.size(); i++) {
					if (eduList.get(i).getDatefrom() != null) {
						eduList.get(i).setDatefromStr(DateUtils.dayToString(eduList.get(i).getDatefrom()));
					}
					if (eduList.get(i).getDateto() != null) {
						eduList.get(i).setDatetoStr(DateUtils.dayToString(eduList.get(i).getDateto()));
					}
				}
			}
			// 初始化分支
			PageModel<Branch> pageModel = new PageModel<Branch>();
			Branch branch = new Branch();
			// branchService.initBranch(pageModel,branch);
			// 初始化相册
			List<Useralbum> userAblumList = userService.selectUseralbum(userid);
			//判断是否是其他民族
			if(userInfo!=null&&userInfo.getNation()!=null){
				if(ConstantUtils.DEFAULT_NATION_STR.indexOf(userInfo.getNation())==-1){
					userInfo.setOtherNation(userInfo.getNation());
					//其他民族返回相应字符串
					userInfo.setNation(ConstantUtils.DEFAULT_NATION_OTHER);
				}
			}
			//查询配偶
			List<Usermates> mateList = userMatesDao.selectmateIdByUserId(userid);
			// 增加父（母）亲姓名和世系信息回写
			User puser = userService.selectByPrimaryKey(user.getPid());
			user.setPgenlevel(puser.getGenlevel()+"世");
			user.setUserInfo(userInfo);
			user.setBranchList((List<Branch>) pageModel.getList());
			user.setUserWorkexp(workList);
			user.setUserEdu(eduList);
			user.setUserAblumList(userAblumList);
			user.setMateList(mateList);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(user);
			/*modelMap.put("user", user);
			modelMap.put("userInfo", userInfo);
			modelMap.put("branchList", pageModel.getList());
			modelMap.put("workList", workList);
			modelMap.put("eduList", eduList);
			modelMap.put("userAblumList", userAblumList);
			//配偶
			modelMap.put("mateList", mateList);*/
		} catch (Exception e) {
//			result = "0";
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
		return res;
	}

	/**
	 * 
	 * @描述 用户列表的查询
	 * @作者 sj
	 * @时间 2017年4月28日上午9:18:58
	 * @参数 @param pageModel
	 * @参数 @param model
	 * @参数 @return
	 * @return ModelMap
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public JsonResponse list(PageModel<User> pageModel, User user, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = CurrentUserContext.getCurrentUserId();
			UserbranchQuery ex = new UserbranchQuery();
			ex.or().andUseridEqualTo(userid);
			List<Userbranch> list = userBranchDao.selectByExample(ex);
			Branch bran = new Branch();
			for(Userbranch b : list){
				bran.setBranchid(b.getBranchid());
				bran.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				bran = branchDao.selectByPrimaryKey(bran);
				if(bran.getBranchid()!=null && !"".equals(bran.getBranchid()))
				user.setBranchid(b.getBranchid());
				
			}

			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			List<String> branchList = CurrentUserContext.getCurrentBranchIds();
//			Integer manager = CurrentUserContext.getUserContext().getRole().getIsmanager();
//			Integer type =  CurrentUserContext.getUserContext().getRole().getType();
//			if (manager == 1 && type == 1) {// 验证是否是总编委会主任
//				branchList.clear();
//				user.setBranchid(null);
//			}else if(manager == 0 && type == 1){//总编委成员
//				user.setBranchid(null);
//			}
			userService.selectUserList(pageModel, user, branchList);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						userService.selectUserList(pageModel, user, branchList);
					}
				}
			}
			/*model.put("pageModel", pageModel);
			model.put("user", user);*/
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModel);
			res.setEntity(user);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
		return res;
	}

	/**
	 * @描述 查询非超级管理员的人员
	 * @作者 wumin
	 * @时间 2017年5月22日下午4:34:20
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/selectUserItem", method = RequestMethod.POST)
	public JsonResponse selectUserItem(HttpServletRequest request, User user, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
//		String gsonStr = null;
		try {
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			List<User> userList = userService.selectUserItem(user);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userList);
//			gsonStr = GsonUtil.GsonString(userList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}
	
	/**
	 * @描述 查询所有人员
	 * @作者 chenxiaobing
	 * @时间 2018年8月2日下午4:34:20
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/selectAllUser", method = RequestMethod.POST)
	public JsonResponse selectAllUser(HttpServletRequest request, User user, ModelMap model) {
//		String gsonStr = null;
		Result result = null;
		JsonResponse res = null;
		try {
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			List<User> userList = userService.selectAllUser(user);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userList);
//			gsonStr = GsonUtil.GsonString(userList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}
	
	
	/**
	 * @描述 查询非超级管理员的人员
	 * @作者 wumin
	 * @时间 2017年5月22日下午4:34:20
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/selectUserItemLive", method = RequestMethod.POST)
	public JsonResponse selectUserItemLive(HttpServletRequest request, User user, ModelMap model) {
//		String gsonStr = null;
		Result result = null;
		JsonResponse res = null;
		try {
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			List<User> userList = userService.selectUserItemLive(user);
//			gsonStr = GsonUtil.GsonString(userList);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	/**
	 * 
	 * @描述 停用用户
	 * @作者 sj
	 * @时间 2017年5月3日下午5:41:12
	 * @参数 @param user
	 * @参数 @param userInfo
	 * @参数 @param userEdu
	 * @参数 @param model
	 * @参数 @param eduExpArray
	 * @参数 @param workExpArray
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(User user)  {
//		String result = null;
		Result result = null;
		JsonResponse res = null;
		try {
			// user.setStatus(3);
            user.setUpdateid(CurrentUserContext.getCurrentUserId());
            user.setUpdatetime(new Date());			
			userService.changeStatus(user);
//			result = "1";
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
//			result = "0";
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 
	 * @描述 批量导入用户
	 * @作者 sj
	 * @时间 2017年5月1日下午2:24:07
	 * @参数 @param user
	 * @参数 @param userInfo
	 * @参数 @param userEdu
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/importUser", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public JsonResponse importUser(MultipartFile file, HttpServletRequest request) {
		//String result = "";
		Result result= null;
		JsonResponse res = null;
		try {
			res = userService.importUsers(file, request);
			//result = res;
		} catch (Exception e) {
			//result = "0";
			//result.setStatus(0);
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	/**
	 * 
	 * @描述 批量导入配偶
	 * @作者 sj
	 * @时间 2017年5月1日下午2:24:07
	 * @参数 @param user
	 * @参数 @param userInfo
	 * @参数 @param userEdu
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/importUsermates", method = RequestMethod.POST)
	public JsonResponse importUsermates(MultipartFile file, HttpServletRequest request) {
		//String result = "";
		Result result=null;
		JsonResponse res = null;
		try {
			res = userService.importUserMates(file, request);
		} catch (Exception e) {
			//result = "0";
			//result.setStatus(0);
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * @描述 新增用户初始化父亲 和 配偶
	 * @作者 sj
	 * @时间 2017年5月5日上午11:03:44
	 * @参数 @param familyid
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/selectPnameAndMate", method = RequestMethod.POST)
	public JsonResponse selectPnameAndMate(String familyid) {
//		String gsonStr = null;
		Result result= null;
		JsonResponse res = null;
		try {
			List<String> branchList = CurrentUserContext.getCurrentBranchIds();
//			Integer type = CurrentUserContext.getUserContext().getRole().getIsmanager();
//			if (type == 1) {// 验证是否是总编委会主任
//				branchList.clear();
//			}
			List<User> userList = userService.selectPnameAndMate(CurrentUserContext.getCurrentFamilyId(), branchList);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userList);
//			gsonStr = GsonUtil.GsonString(userList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	/**
	 * 
	 * @描述 待审核用户列表的查询
	 * @作者 sj
	 * @时间 2017年4月28日上午9:18:58
	 * @参数 @param pageModel
	 * @参数 @param model
	 * @参数 @return
	 * @return ModelMap
	 */
	@RequestMapping(value = "/listToReview", method = RequestMethod.POST)
	public JsonResponse listToReview(PageModel<User> pageModel, User user, ModelMap model) {
		Result result= null;
		JsonResponse res = null;
		try {
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			userService.selecUserListToReview(pageModel, user);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						userService.selecUserListToReview(pageModel, user);
					}
				}
			}
			/*model.put("pageModel", pageModel);
			model.put("user", user);*/
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModel);
			res.setEntity(user);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
//		return "user/memberPermission";
		return res;
	}

	/**
	 * 
	 * @描述 通过或拒绝用户
	 * @作者 sj
	 * @时间 2017年5月8日下午7:46:13
	 * @参数 @param user
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/changeStatusOfReview", method = RequestMethod.POST)
	public JsonResponse changeStatusOfReview(User user) {
//		String result = null;
		Result result= null;
		JsonResponse res = null;
		try {
			Integer count = userService.changeStatus(user);
//			result = "1";
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(count);
		} catch (Exception e) {
			// result = "0";
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	/**
	 * 确认导入
	 * @param excelid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmImport", method = RequestMethod.POST)
	public JsonResponse confirmImport(String excelid) {
		//String result = "";
		Result result=null;
		JsonResponse res = null;
		try {
			result = userService.confirmImport(excelid);
		} catch (Exception e) {
			//result = "0";
			//result.setStatus(0);
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
		}
		res = new JsonResponse(result);
		return res;
	}
	
	/**
	 * @描述 新增用户初始化父亲 和 配偶
	 * @作者 sj
	 * @时间 2017年5月5日上午11:03:44
	 * @参数 @param familyid
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/selectBranch", method = RequestMethod.POST)
	public JsonResponse selectBranch(String familyid) {
//		String gsonStr = null;
		Result result= null;
		JsonResponse res = null;
		try {
			PageModel<Branch> pageModelBranch = new PageModel<Branch>();
			Branch branch = new Branch();
			branchService.initBranch(pageModelBranch, branch);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModelBranch.getList());
//			gsonStr = GsonUtil.GsonString(pageModelBranch.getList());
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	/**
	 * 
	 * @描述 去单个用户增加配偶界面
	 * @作者 sj
	 * @时间 2017年5月12日上午9:17:47
	 * @参数 @param request
	 * @参数 @param modelMap
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/addmate", method = RequestMethod.GET)
	public JsonResponse addmate(HttpServletRequest request, ModelMap modelMap) {
		Result result= null;
		JsonResponse res = null;
		try {
			User user = null;
			String userid = request.getParameter("userid");
			if (StringTools.trimNotEmpty(userid)) {
				user = userService.selectByPrimaryKey(userid);
			}
			// modelMap.put("user", user);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(user);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
//		return "user/addmate";
		return res;
	}

	/**
	 * 
	 * @描述 保存增加配偶
	 * @作者 sj
	 * @时间 2017年5月13日上午10:27:32
	 * @参数 @param user
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/mergeMate", method = RequestMethod.POST)
	public JsonResponse mergeUserMate(User user, Userinfo userInfo, String usernameBefore) {
		// String result = null;
		Result result= null;
		JsonResponse res = null;
		try {
			result = userService.mergeMate(user, userInfo, usernameBefore);
//			result = res;
		} catch (Exception e) {
			// result = "0";
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 
	 * @描述 用户相册增加
	 * @作者 sj
	 * @时间 2017年5月15日下午12:02:36
	 * @参数 @param user
	 * @参数 @param userInfo
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAlbum", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public JsonResponse saveAlbum(@RequestParam("file") MultipartFile[] files, HttpServletRequest request, String albumid,
			String userid) {
//		String result = null;
		Result result= null;
		JsonResponse res = null;
		try {
			List<String> fileNams = new ArrayList<String>();
			List<File> fileList = new ArrayList<File>();
			File file = null;
			String fileName = "";
			// String zhName = "";
			for (MultipartFile fileM : files) {
				// zhName = fileM.getName();
				fileName = fileM.getOriginalFilename();
				fileNams.add(fileName);
			}
			String pathDir = "/upload";
			String logoRealPathDir = request.getSession().getServletContext().getRealPath(pathDir);
			for (int i = 0; i < files.length; i++) {
				file = new File(logoRealPathDir);
				// OutputStream output = new FileOutputStream(file);
				// BufferedOutputStream bufferedOutput = new
				// BufferedOutputStream(output);
				// bufferedOutput.write(files[i].getBytes());
				files[i].transferTo(file);
				fileList.add(file);
			}
			String status = UploadUtil.taskFileUpload(fileList, fileNams);
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(status, JsonObject.class);
			JsonObject jsonInfo = gson.fromJson(json.get("data"), JsonObject.class);
			String url = "";
			String iconurl = "";
			url = jsonInfo.get("url").toString();
			iconurl = jsonInfo.get("iconUrl").toString();
			String newStr = url.replaceAll("\"", "");
			String newStrIcon = iconurl.replaceAll("\"", "");
			Userphoto userPhoto = null;
			List<Userphoto> userPhotoList = new ArrayList<Userphoto>();
			for (int i = 0; i < files.length; i++) {
				userPhoto = new Userphoto();
				userPhoto.setUserid(userid);
				userPhoto.setAlbumid(albumid);
				userPhoto.setImgurl(newStr);
				userPhoto.setCreatetime(new Date());
				userPhoto.setCreateid(CurrentUserContext.getCurrentUserId());
				userPhoto.setDeleteflag(0);
				userPhoto.setSmallimgurl(newStrIcon);
				userPhoto.setImgid(UUIDUtils.getUUID());
				userPhoto.setDescription(fileName);
				userPhotoList.add(userPhoto);
			}
			userService.mergeUserPhoto(userPhotoList);
//			result = "{\"result\":\"0\"}";
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
//			result = "{\"result\":\"1\"}";
			result = new Result(MsgConstants.RESUL_FAIL);
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 
	 * @描述 用户新增相册
	 * @作者 sj
	 * @时间 2017年5月17日上午10:31:58
	 * @参数 @param userAlbum
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/mergeUserAlbum", method = RequestMethod.POST)
	public JsonResponse mergeUserAlbum(Useralbum userAlbum) {
		String ablumId = null;
		Result result= null;
		JsonResponse res = null;
		try {
			ablumId = userService.mergeUserAlbum(userAlbum);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(ablumId);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	/**
	 * 
	 * @描述 去相册新增界面
	 * @作者 sj
	 * @时间 2017年5月17日下午6:01:06
	 * @参数 @param request
	 * @参数 @param modelMap
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/toAddAblum", method = RequestMethod.GET)
	public JsonResponse toAddAblum(HttpServletRequest request, ModelMap modelMap) {
		Result result= null;
		JsonResponse res = null;
		try {
			String returnTable = "";
			String userid = request.getParameter("userid");
			String albumid = request.getParameter("albumid");
			String type = request.getParameter("type");
			if (type.equals("0")) {
				returnTable = "photo";
			} else {
				returnTable = "work";
			}
			Useralbum userAlbum = null;
			if (StringTools.trimNotEmpty(albumid)) {
				UseralbumKey key = new UseralbumKey();
				key.setUserid(userid);
				key.setAlbumid(albumid);
				userAlbum = useralbumService.selectByPrimaryKey(key);
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userAlbum);
			res.setEntity(type);
			res.setData1(userid);
			   res.setData2(returnTable);
			/*modelMap.put("userid", userid);
			modelMap.put("useralbum", userAlbum);
			modelMap.put("type", type);
			modelMap.put("returnTable", returnTable);*/
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
//		return "user/useralbum";
		return res;
	}

	/**
	 * @描述 去上传图片界面
	 * @作者 hongjun
	 * @时间 2017年5月26日下午5:52:51
	 * @参数 @param request
	 * @参数 @param modelMap
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/toAddPhoto", method = RequestMethod.GET)
	public JsonResponse toAddPhoto(HttpServletRequest request,ModelMap modelMap)  {
		Result result= null;
		JsonResponse res = null;
		try{
		   String returnTable = "";
		   String userid = request.getParameter("userid");
		   String albumid = request.getParameter("albumid");
		   String type = request.getParameter("type");
		   if(type.equals("0")){
			   returnTable = "photo";
		   }else{
			   returnTable = "work";
		   }
		   result = new Result(MsgConstants.RESUL_SUCCESS);
		   res = new JsonResponse(result);
		   res.setData(albumid);
		   res.setData1(userid);
		   res.setData2(returnTable);
		   res.setEntity(type);
		   /*modelMap.put("userid", userid);
		   modelMap.put("albumid", albumid);
		   modelMap.put("type", type);
		   modelMap.put("returnTable", returnTable);*/
		}catch(Exception e){
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
//		return "user/uploadphoto";
		return res;
	}

	/**
	 * 
	 * @描述 相册详情
	 * @作者 sj
	 * @时间 2017年5月18日上午9:40:56
	 * @参数 @param userAlbumId
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/userAlbumDetail", method = RequestMethod.POST)
	public JsonResponse userAlbumDetail(String albumid, String userid, String type, ModelMap modelMap) {
		Result result= null;
		JsonResponse res = null;
		try {
			List<Userphoto> photoList = useralbumService.selectByAlbumId(albumid);
			UseralbumKey key = new UseralbumKey();
			key.setUserid(userid);
			key.setAlbumid(albumid);
			Useralbum userAlbum = null;
			userAlbum = useralbumService.selectByPrimaryKey(key);
			
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(photoList);
			res.setData1(userAlbum);
			res.setData2(type);
			/*modelMap.put("photoList", photoList);
			modelMap.put("userAlbum", userAlbum);
			modelMap.put("type", type);*/
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
//		return "user/userphotoDetail";
		return res;
	}

	/**
	 * @描述 delete相册
	 * @作者 sj
	 * @时间 2017年5月18日上午11:01:08
	 * @参数 @param userAlbum
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteUserAlbum", method = RequestMethod.POST)
	public JsonResponse deleteUserAlbum(Useralbum userAlbum) {
//		String result = "";
		Result result= null;
		JsonResponse res = null;
		try {
			// 相册删除
			UseralbumKey key = new UseralbumKey();
			key.setAlbumid(userAlbum.getAlbumid());
			key.setUserid(userAlbum.getUserid());
			useralbumService.deleteByPrimaryKey(key);
			// 删除照片
			useralbumService.deleteUserPhoto(userAlbum.getAlbumid());
//			result = "1";
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * @描述 删除单张图片
	 * @作者 sj
	 * @时间 2017年5月18日下午3:02:34
	 * @参数 @param userAlbum
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/deletPhoto", method = RequestMethod.POST)
	public JsonResponse deletPhoto(Userphoto userPhoto) {
//		String result = "";
		Result result= null;
		JsonResponse res = null;
		try {
			UserphotoKey key = new UserphotoKey();
			key.setAlbumid(userPhoto.getAlbumid());
			key.setImgid(userPhoto.getImgid());
			key.setUserid(userPhoto.getUserid());
			// 删除照片
			useralbumService.deleteUserPhotoSingle(key);
//			result = "1";
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 
	 * @描述 验证手机号
	 * @作者 sj
	 * @时间 2017年5月19日上午9:46:38
	 * @参数 @param userPhoto
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/validatePhone", method = RequestMethod.POST)
	public JsonResponse validatePhone(User user) {
//		boolean flag = true;// 默认通过验证
		Result result= new Result(MsgConstants.RESUL_SUCCESS);
		JsonResponse res = null;
		try {
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			List<User> userList = userService.validatePhone(user);
			String userid = "";
			if (StringTools.trimNotEmpty(user.getUserid())) {
				for (int i = 0; i < userList.size(); i++) {
					if (!userList.get(i).getUserid().equals(user.getUserid())) {
						userid += userList.get(i).getUserid() + ",";
					}
				}
				if (StringTools.trimNotEmpty(userid)) {
//					flag = false;
					result = new Result(MsgConstants.RESUL_FAIL);
				}
			} else {
				if (userList != null && userList.size() > 0) {
//					flag = false;
					result = new Result(MsgConstants.RESUL_FAIL);
				}
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
//		return flag ? "true" : "false";
	}

	/**
	 * 
	 * @描述 去单张照片编辑界面
	 * @作者 sj
	 * @时间 2017年5月21日下午4:01:46
	 * @参数 @param request
	 * @参数 @param modelMap
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/edituserphoto", method = RequestMethod.GET)
	public JsonResponse edituserphoto(HttpServletRequest request, ModelMap modelMap) {
		Result result= null;
		JsonResponse res = null;
		try {
			String returnTable = "";
			String userid = request.getParameter("userid");
			String albumid = request.getParameter("albumid");
			String type = request.getParameter("type");
			String imgid = request.getParameter("imgid");
			if (type.equals("0")) {
				returnTable = "photo";
			} else {
				returnTable = "work";
			}
			UserphotoKey key = new UserphotoKey();
			key.setImgid(imgid);
			key.setAlbumid(albumid);
			key.setUserid(userid);
			Userphoto userPhoto = useralbumService.selectByImgId(key);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userPhoto);
			/*modelMap.put("userid", userid);
			modelMap.put("type", type);
			modelMap.put("returnTable", returnTable);
			modelMap.put("userPhoto", userPhoto);*/
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
//		return "user/userphotoedit";
		return res;
	}

	/**
	 * @描述 保存用户照片 编辑照片说明
	 * @作者 sj
	 * @时间 2017年5月21日下午4:17:41
	 * @参数 @param userAlbum
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/mergeUserPhoto", method = RequestMethod.POST)
	public JsonResponse mergeUserPhoto(Userphoto userPhoto) {
//		String result = "";
		Result result= null;
		JsonResponse res = null;
		try {
			userPhoto.setUpdateid(CurrentUserContext.getCurrentUserId());
			userPhoto.setUpdatetime(new Date());
			useralbumService.updateByPrimaryKeySelective(userPhoto);
//			result = "1";
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
//			result = "0";
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
//		return result;
	}
	/**
	 * 
	 * @描述 异步校验旧密码
	 * @作者 jinlizhi
	 * @时间 2017年5月26日下午4:30:55
	 * @参数 @param oldpassword
	 * @参数 @return
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "/checkoldpassword")
	public JsonResponse checkOldPwd(String oldpassword) {
//		Result result = new Result();
		Result result= null;
		JsonResponse res = null;
		try {
			//解密
			byte[] byteStr = Base64.decodeBase64(oldpassword.getBytes("UTF-8"));    
		    oldpassword = new String(byteStr);
			UserQuery example = new UserQuery();
			example.or().andPasswordEqualTo(MD5Util.string2MD5(oldpassword))
					.andUseridEqualTo(CurrentUserContext.getCurrentUserId());
			List<User> selectRt = userDao.selectByExample(example);
			if (selectRt!=null&&selectRt.size()==1) {
//				result.setStatus(1);
				result = new Result(MsgConstants.RESUL_SUCCESS);
			}else{
//				result.setStatus(0);
				result = new Result(MsgConstants.RESUL_FAIL);
			}
		} catch (Exception e) {
//			result.setStatus(0);
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
		res = new JsonResponse(result);
		return res;
//		return result;
	}
	/**
	 * 
	 * @描述 在线用户修改密码
	 * @作者 jinlizhi
	 * @时间 2017年5月26日下午7:32:18
	 * @参数 @param oldpassword
	 * @参数 @param password
	 * @参数 @return
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "/editPassowrd")
	public JsonResponse editPwd(String oldpassword,String password) {
//		Result result = new Result();
		Result result= null;
		JsonResponse res = null;
		try {
			//解密
			byte[] byteStr = Base64.decodeBase64(oldpassword.getBytes("UTF-8"));    
			byte[] byteStr2 = Base64.decodeBase64(password.getBytes("UTF-8"));    
		    oldpassword = new String(byteStr);
		    password = new String(byteStr2);
		    //查询校验
			UserQuery example = new UserQuery();
			example.or().andPasswordEqualTo(MD5Util.string2MD5(oldpassword))
					.andUseridEqualTo(CurrentUserContext.getCurrentUserId());
			List<User> selectRt = userDao.selectByExample(example);
			if (selectRt!=null&&selectRt.size()==1) {
				result=userService.editPwd(MD5Util.string2MD5(password));
				//result.setStatus(1);
			}else{
//				result.setStatus(0);
				result = new Result(MsgConstants.RESUL_FAIL);
			}
		} catch (Exception e) {
//			result.setStatus(0);
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
		res = new JsonResponse(result);
		return res;
//		return result;
	}
	
	/**
	 * 
	 * @描述 批量导入用户
	 * @作者 chenxiaobing
	 * @时间 2018年12月21日下午2:24:07
	 * @参数 @param user
	 * @参数 @param userInfo
	 * @参数 @param userEdu
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/importUserNew", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public JsonResponse importUserNew(PageModel<User> pageModel,MultipartFile file, HttpServletRequest request,ModelMap model) {
		//String result = "";
//		Result result=new Result();
		Result result= null;
		JsonResponse res = null;
		try {
			res = userService.importUsersNew(file, request);
			//result = res;
			/*model.put("pageModel", pageModel);
			model.put("result", result);*/
		} catch (Exception e) {
			//result = "0";
//			result.setStatus(0);
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
//		return "user/userList";
		return res;
	}
	
	/**
	 * 新增家族成员时判断家族容纳人数时候超出版本限制人数
	 * @return 1T0F
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public JsonResponse checkFamilyUserNumber(HttpServletRequest request) {
//		String checkResult = "1";	// 家族容纳人数是否超出版本限制，1T0F
		Integer priValue = 0;		// 最多容纳家族人数
		Result result= new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		// 获取该用户所在家族使用的版本特权信息
		try {
			SysVersionPrivilege versionP = sysVersionPrivilegeMapper.selectByVersionAndCode(CurrentUserContext.getCurrentFamilyId(), ConstantUtils.VERSION_USERCOUNT);
			if(versionP != null && versionP.getPrivilegevalue() != null) {
				if(versionP.getPrivilegevalue().equals(ConstantUtils.VERSION_UNLIMITED)) {
//					checkResult = "0";
					result = new Result(MsgConstants.RESUL_SUCCESS);
					res = new JsonResponse(result);
					return res;
				}
				priValue = Integer.valueOf(versionP.getPrivilegevalue());
			}
			// 获取该用户所在家族已有的人数
			UserQuery userExample = new UserQuery();
			userExample.or().andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
			int haveUserCount = userDao.countByExample(userExample); 	// 家族已有人数
			if(priValue > 0 && priValue > haveUserCount) {
//				checkResult = "0";	//家族人数未超出版本限制最大人数
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}
	
	/**
	 * 新的用户检索接口
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse searchUser(PageModel<User> pageModel, User user,ModelMap model) throws IOException {
//		Result result = new Result();
		Result result= null;
		JsonResponse res = null;
		String gsonStr = null;
		try {
			String userid = CurrentUserContext.getCurrentUserId();
			UserbranchQuery ex = new UserbranchQuery();
			ex.or().andUseridEqualTo(userid);
			List<Userbranch> list = userBranchDao.selectByExample(ex);
			Branch bran = new Branch();
			for(Userbranch b : list){
				bran.setBranchid(b.getBranchid());
				bran.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				bran = branchDao.selectByPrimaryKey(bran);
				if(bran.getBranchid()!=null && !"".equals(bran.getBranchid()))
				user.setBranchid(b.getBranchid());
			}
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			user.setStatus(0);	//用户状态默认启用
			List<String> branchList = CurrentUserContext.getCurrentBranchIds();
			userService.selectUserList(pageModel, user, branchList);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						userService.selectUserList(pageModel, user, branchList);
					}
				}
			}
			
			List<User> userList = new ArrayList<>();
			// 增加address字段
			for (Object obj : pageModel.getList()) {
				User userAddrss = (User) obj;
				userAddrss.setAddress(userDao.getAddressByUserid(userAddrss.getUserid()));
				userList.add(userAddrss);
			}
			/*result.setStatus(ConstantUtils.RESULT_SUCCESS);
			result.setData(userList);
			gsonStr = GsonUtil.GsonString(result);*/
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "getHome", method = RequestMethod.POST)
	public JsonResponse getHomeData(User user) throws Exception {
		Result result= null;
		JsonResponse res = null;
		LoginUserInfo userContext = new LoginUserInfo();
		List<UserManager> managers = userManagerService.selectManagerByUserid(user.getUserid());
		if(managers == null || managers.size()==0){
			result = new Result(MsgConstants.LOGIN_NOT_ADMIN);
			res = new JsonResponse(result);
			return res;
		}
		
		List<Function> functionList = list2Tree(user.getFamilyid(), user.getUserid());
		List<Branch> branchList = branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(), user.getUserid());
		userContext.setUsermanagers(managers);
		userContext.setFunctionList(functionList);
		if(branchList == null){
			branchList = new ArrayList<Branch>();
		}
		userContext.setBranchList(branchList);
		
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userContext);
		return res;
	}
	
	public List<Function> list2Tree(String familyid,String userid) {
		List<Function> functionList =functionService.selectFunctionListByManagerid(familyid, userid);
		List<Function> parentList = new ArrayList<>();
		for (Function function : functionList) {
			if("00000".equals(function.getParentid())) {
				parentList.add(function);
			}
		}
		for (Function parent : parentList) {
			List<Function> childList = new ArrayList<>();
			for (Function  function: functionList) {
				if(function.getParentid().equals(parent.getFunctionid())) {
					childList.add(function);
				}
			}
			parent.setChildList(childList);
		}
		return parentList;
	}
	 
}
