
package com.jp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.dao.UserDao;
import com.jp.dao.UserbranchDao;
import com.jp.entity.Branch;
import com.jp.entity.BranchKey;
import com.jp.entity.SearchComplex;
import com.jp.entity.SysVersionPrivilege;
import com.jp.entity.User;
import com.jp.entity.UserClildInfo;
import com.jp.entity.UserQuery;
import com.jp.entity.Useralbum;
import com.jp.entity.UseralbumKey;
import com.jp.entity.Userbranch;
import com.jp.entity.UserbranchQuery;
import com.jp.entity.Usercode;
import com.jp.entity.Useredu;
import com.jp.entity.Userinfo;
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
import com.jp.util.JacksonUtil;
import com.jp.util.MD5Util;
import com.jp.util.RSAUtils;
//import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

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
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public JsonResponse mergeUser(HttpServletRequest request) throws IOException {
		Result result = null;
		JsonResponse res = null;
		try {
			BufferedReader reader = request.getReader();
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			String jsonstr = sb.toString();
			String jsonReplace = jsonstr.replace("\"\"", "null");
			User user = JacksonUtil.fromJsonToObject(jsonReplace, User.class);
			result = userService.merge(user); // 除了user，其他参数弃用
			res = new JsonResponse(result);
		} catch (Exception e) {
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
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = userids.substring(0, userids.length());
			String useridArray[] = userid.split(",");
			Integer count = userService.del(useridArray);
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
			} else {
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
	 * 获取用户的相册和个人作品
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/getUserAblums", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getUserAblums(@RequestParam(defaultValue = "0") int type, String userid) {
		return userService.selectUseralbum(userid, type);
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
	@ResponseBody
	public JsonResponse editUser(HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		// SimpleDateFormat sdfd = new SimpleDateFormat("yyy-MM-dd");
		try {
			String userid = request.getParameter("userid");
			User user = null;
			Userinfo userInfo = null;
			List<Useredu> eduList = null;
			List<Userworkexp> workList = null;
			// selectByPrimaryKey
			if (StringTools.trimNotEmpty(userid)) {
				user = userService.selectByPrimaryKey(userid);
				/*
				 * if (user != null) { if (user.getDietime() != null) {
				 * user.setDietimeStr(user.getDietime()); } }
				 */
				userInfo = userInfoService.selectByPrimaryKey(userid);
				/*
				 * if (userInfo != null) { if (userInfo.getBirthday() != null) {
				 * userInfo.setBirthdayStr(userInfo.getBirthday()); } }
				 */
				if (userInfo != null) {
					String birth = userInfo.getBirthplace();
					if (StringTools.trimNotEmpty(birth)) {
						String births[] = birth.split("@@");
						if (births.length == 0) {
							userInfo.setBirthplaceP("");
							userInfo.setBirthplaceC("");
							userInfo.setBirthplaceX("");
							userInfo.setBirthDetail("");
						}
						if (births.length == 1) {
							userInfo.setBirthplaceP("null".equals(births[0]) ? "" : births[0]);
						} else if (births.length == 2) {
							userInfo.setBirthplaceP("null".equals(births[0]) ? "" : births[0]);
							userInfo.setBirthplaceC("null".equals(births[1]) ? "" : births[1]);
						} else if (births.length == 3) {
							userInfo.setBirthplaceP("null".equals(births[0]) ? "" : births[0]);
							userInfo.setBirthplaceC("null".equals(births[1]) ? "" : births[1]);
							userInfo.setBirthplaceX("null".equals(births[2]) ? "" : births[2]);
						} else if (births.length == 4) {
							userInfo.setBirthplaceP("null".equals(births[0]) ? "" : births[0]);
							userInfo.setBirthplaceC("null".equals(births[1]) ? "" : births[1]);
							userInfo.setBirthplaceX("null".equals(births[2]) ? "" : births[2]);
							userInfo.setBirthDetail("null".equals(births[3]) ? "" : births[3]);
						}
					} else {
						userInfo.setBirthplace("");
						userInfo.setBirthplaceP("");
						userInfo.setBirthplaceC("");
						userInfo.setBirthplaceX("");
						userInfo.setBirthDetail("");
					}

					String home = userInfo.getHomeplace();
					if (StringTools.trimNotEmpty(home)) {
						String homes[] = home.split("@@");
						if (homes.length == 0) {
							userInfo.setHomeplaceP("");
							userInfo.setHomeplaceC("");
							userInfo.setHomeplaceX("");
							userInfo.setHomeDetail("");
						}
						if (homes.length == 1) {
							userInfo.setHomeplaceP("null".equals(homes[0]) ? "" : homes[0]);
						} else if (homes.length == 2) {
							userInfo.setHomeplaceP("null".equals(homes[0]) ? "" : homes[0]);
							userInfo.setHomeplaceC("null".equals(homes[1]) ? "" : homes[1]);
						} else if (homes.length == 3) {
							userInfo.setHomeplaceP("null".equals(homes[0]) ? "" : homes[0]);
							userInfo.setHomeplaceC("null".equals(homes[1]) ? "" : homes[1]);
							userInfo.setHomeplaceX("null".equals(homes[2]) ? "" : homes[2]);
						} else if (homes.length == 4) {
							userInfo.setHomeplaceP("null".equals(homes[0]) ? "" : homes[0]);
							userInfo.setHomeplaceC("null".equals(homes[1]) ? "" : homes[1]);
							userInfo.setHomeplaceX("null".equals(homes[2]) ? "" : homes[2]);
							userInfo.setHomeDetail("null".equals(homes[3]) ? "" : homes[3]);
						}
					} else {
						userInfo.setHomeplace("");
						userInfo.setHomeplaceP("");
						userInfo.setHomeplaceC("");
						userInfo.setHomeplaceX("");
						userInfo.setHomeDetail("");
					}
				}
				workList = userWorkService.selectByUserId(userid);
				/*
				 * for (int i = 0; i < workList.size(); i++) { if (workList.get(i).getDatefrom()
				 * != null) {
				 * workList.get(i).setDatefromStr(DateUtils.dayToString(workList.get(i).
				 * getDatefrom())); } if (workList.get(i).getDateto() != null) {
				 * workList.get(i).setDatetoStr(DateUtils.dayToString(workList.get(i).getDateto(
				 * ))); } }
				 */
				eduList = usereduService.selectByUserId(userid);
				/*
				 * for (int i = 0; i < eduList.size(); i++) { if (eduList.get(i).getDatefrom()
				 * != null) {
				 * eduList.get(i).setDatefromStr(DateUtils.dayToString(eduList.get(i).
				 * getDatefrom())); } if (eduList.get(i).getDateto() != null) {
				 * eduList.get(i).setDatetoStr(DateUtils.dayToString(eduList.get(i).getDateto())
				 * ); } }
				 */
			}
			// 初始化分支
			PageModel<Branch> pageModel = new PageModel<Branch>();
			BranchKey key = new BranchKey();
			key.setBranchid(user.getBranchid());
			key.setFamilyid(user.getFamilyid());
			Branch branch = branchDao.selectByPrimaryKey(key);
			// branchService.initBranch(pageModel,branch);

			// 判断是否是其他民族
			if (userInfo != null && userInfo.getNation() != null) {
				if (ConstantUtils.DEFAULT_NATION_STR.indexOf(userInfo.getNation()) == -1) {
					userInfo.setOtherNation(userInfo.getNation());
					// 其他民族返回相应字符串
					userInfo.setNation(ConstantUtils.DEFAULT_NATION_OTHER);
				}
			}
			// 查询配偶
			// List<Usermates> mateList = userMatesDao.selectmateIdByUserId(userid);
			List<User> mateList = userDao.selectMateList(userid, user.getMateid());
			// 增加父（母）亲姓名和世系信息回写
			User puser = userService.selectByPrimaryKey(user.getPid());
			// 兼容分支起始人没有父系信息
			if (puser != null) {
				user.setPgenlevel(puser.getGenlevel() + "世");
			}
			user.setUserInfo(userInfo);
			user.setBranchList((List<Branch>) pageModel.getList());
			user.setUserWorkexp(workList);
			user.setUserEdu(eduList);
			user.setMateList(mateList);
			if (branch != null) {
				String area = "";
				if (branch.getArea() != null)
					area += branch.getArea();
				if (branch.getCityname() != null)
					area += "_" + branch.getCityname();
				if (branch.getXname() != null)
					area += "_" + branch.getXname();
				if (branch.getAddress() != null)
					area += "_" + branch.getAddress();
				area += " " + branch.getBranchname();
				branch.setBranchname(area);
				user.setBranch(branch);
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(user);

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
	 * @描述 用户列表的查询
	 * @作者 sj
	 * @时间 2017年4月28日上午9:18:58
	 * @参数 @param pageModel
	 * @参数 @param model
	 * @参数 @return
	 * @return ModelMap
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<User> pageModel, User user, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = CurrentUserContext.getCurrentUserId();
			UserbranchQuery ex = new UserbranchQuery();
			ex.or().andUseridEqualTo(userid);
			List<Userbranch> list = userBranchDao.selectByExample(ex);
			Branch bran = new Branch();
			for (Userbranch b : list) {
				bran.setBranchid(b.getBranchid());
				bran.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				bran = branchDao.selectByPrimaryKey(bran);
				if (bran.getBranchid() != null && !"".equals(bran.getBranchid()))
					user.setBranchid(b.getBranchid());

			}

			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			List<String> branchList = CurrentUserContext.getCurrentBranchIds();

			userService.selectUserList(pageModel, user, branchList);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo().toString())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						userService.selectUserList(pageModel, user, branchList);
					}
				}
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModel.getList());
			res.setCount(pageModel.getPageInfo().getTotal());
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
		// String gsonStr = null;
		try {
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			List<User> userList = userService.selectUserItem(user);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userList);
			// gsonStr = GsonUtil.GsonString(userList);
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
		// String gsonStr = null;
		Result result = null;
		JsonResponse res = null;
		try {
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			List<User> userList = userService.selectAllUser(user);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userList);
			// gsonStr = GsonUtil.GsonString(userList);
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
		// String gsonStr = null;
		Result result = null;
		JsonResponse res = null;
		try {
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			List<User> userList = userService.selectUserItemLive(user);
			// gsonStr = GsonUtil.GsonString(userList);
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
	public JsonResponse changeStatus(User user) {
		return userService.changeStatus(user);
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
	@RequestMapping(value = "/importUser", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public JsonResponse importUser(MultipartFile file, HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			res = userService.importUsers(file, request);
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
	@RequestMapping(value = "/importUsermates", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public JsonResponse importUsermates(MultipartFile file, HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			res = userService.importUserMates(file, request);
		} catch (Exception e) {
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
		// String gsonStr = null;
		Result result = null;
		JsonResponse res = null;
		try {
			List<String> branchList = CurrentUserContext.getCurrentBranchIds();
			// Integer type = CurrentUserContext.getUserContext().getRole().getIsmanager();
			// if (type == 1) {// 验证是否是总编委会主任
			// branchList.clear();
			// }
			List<User> userList = userService.selectPnameAndMate(CurrentUserContext.getCurrentFamilyId(), branchList);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userList);
			// gsonStr = GsonUtil.GsonString(userList);
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
	@ResponseBody
	public JsonResponse listToReview(PageModel<User> pageModel, User user) {
		Result result = null;
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
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModel.getList());
			res.setCount(pageModel.getPageInfo().getTotal());
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
		return userService.changeStatus(user);
	}

	/**
	 * 确认导入
	 * 
	 * @param excelid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmImport", method = RequestMethod.POST)
	public JsonResponse confirmImport(String excelid) {
		// String result = "";
		Result result = null;
		JsonResponse res = null;
		try {
			result = userService.confirmImport(excelid);
		} catch (Exception e) {
			// result = "0";
			// result.setStatus(0);
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
		// String gsonStr = null;
		Result result = null;
		JsonResponse res = null;
		try {
			PageModel<Branch> pageModelBranch = new PageModel<Branch>();
			Branch branch = new Branch();
			branchService.initBranch(pageModelBranch, branch);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModelBranch.getList());
			// gsonStr = GsonUtil.GsonString(pageModelBranch.getList());
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
	@ResponseBody
	public JsonResponse addmate(HttpServletRequest request, ModelMap modelMap) {
		Result result = null;
		JsonResponse res = null;
		try {
			User user = null;
			String userid = request.getParameter("userid");
			if (StringTools.trimNotEmpty(userid)) {
				user = userService.selectByPrimaryKey(userid);
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(user);
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
	 * @描述 保存增加配偶
	 * @作者 sj
	 * @时间 2017年5月13日上午10:27:32
	 * @参数 @param user
	 * @参数 @return
	 * @return String
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/mergeMate", method = RequestMethod.POST)
	public JsonResponse mergeUserMate(HttpServletRequest request) throws IOException {
		Result result = null;
		JsonResponse res = null;
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		String jsonstr = sb.toString();
		String jsonReplace = jsonstr.replace("\"\"", "null");
		User user = JacksonUtil.fromJsonToObject(jsonReplace, User.class);
		try {
			result = userService.mergeMate(user, user.getUserInfo(), "");
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
	 * @描述 用户上传图片保存
	 * @作者 sj
	 * @时间 2017年5月15日下午12:02:36
	 * @参数 @param user
	 * @参数 @param userInfo
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/savePhoto", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public JsonResponse savePhoto(@RequestBody List<Userphoto> userPhotoList) {
		return userService.savePhoto(userPhotoList);
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
		return userService.mergeUserAlbum(userAlbum);
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
	@ResponseBody
	public JsonResponse toAddAblum(HttpServletRequest request, ModelMap modelMap) {
		Result result = null;
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
			/*
			 * modelMap.put("userid", userid); modelMap.put("useralbum", userAlbum);
			 * modelMap.put("type", type); modelMap.put("returnTable", returnTable);
			 */
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		// return "user/useralbum";
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
	@ResponseBody
	public JsonResponse toAddPhoto(HttpServletRequest request, ModelMap modelMap) {
		Result result = null;
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
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(albumid);
			res.setData1(userid);
			res.setEntity(type);
			/*
			 * modelMap.put("userid", userid); modelMap.put("albumid", albumid);
			 * modelMap.put("type", type); modelMap.put("returnTable", returnTable);
			 */
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		// return "user/uploadphoto";
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
	@ResponseBody
	@RequestMapping(value = "/userAlbumDetail", method = RequestMethod.POST)
	public JsonResponse userAlbumDetail(String albumid, String userid, String type) {
		Result result = null;
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
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
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
		Result result = null;
		JsonResponse res = null;
		try {
			// 相册删除
			UseralbumKey key = new UseralbumKey();
			key.setAlbumid(userAlbum.getAlbumid());
			key.setUserid(userAlbum.getUserid());
			useralbumService.deleteByPrimaryKey(key);
			// 删除照片
			useralbumService.deleteUserPhoto(userAlbum.getAlbumid());
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
		// String result = "";
		Result result = null;
		JsonResponse res = null;
		try {
			UserphotoKey key = new UserphotoKey();
			key.setAlbumid(userPhoto.getAlbumid());
			key.setImgid(userPhoto.getImgid());
			key.setUserid(userPhoto.getUserid());
			// 删除照片
			useralbumService.deleteUserPhotoSingle(key);
			// result = "1";
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
		Result result = new Result(MsgConstants.RESUL_SUCCESS); // 默认通过验证
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
					result = new Result(MsgConstants.USER_PHONE_REPEAT);
				}
			} else {
				if (userList != null && userList.size() > 0) {
					result = new Result(MsgConstants.USER_PHONE_REPEAT);
				}
			}
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
	 * @描述 去单张照片编辑界面
	 * @作者 sj
	 * @时间 2017年5月21日下午4:01:46
	 * @参数 @param request
	 * @参数 @param modelMap
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/edituserphoto", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse edituserphoto(HttpServletRequest request, ModelMap modelMap) {
		Result result = null;
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
			/*
			 * modelMap.put("userid", userid); modelMap.put("type", type);
			 * modelMap.put("returnTable", returnTable); modelMap.put("userPhoto",
			 * userPhoto);
			 */
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		// return "user/userphotoedit";
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
		// String result = "";
		Result result = null;
		JsonResponse res = null;
		try {
			userPhoto.setUpdateid(CurrentUserContext.getCurrentUserId());
			userPhoto.setUpdatetime(new Date());
			useralbumService.updateByPrimaryKeySelective(userPhoto);
			// result = "1";
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			// result = "0";
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
		// return result;
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
		Result result = null;
		JsonResponse res = null;
		try {
			// 解密
			byte[] byteStr = Base64.decodeBase64(oldpassword.getBytes("UTF-8"));
			oldpassword = new String(byteStr);
			UserQuery example = new UserQuery();
			example.or().andPasswordEqualTo(MD5Util.string2MD5(oldpassword))
					.andUseridEqualTo(CurrentUserContext.getCurrentUserId());
			List<User> selectRt = userDao.selectByExample(example);
			if (selectRt != null && selectRt.size() == 1) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
			} else {
				result = new Result(MsgConstants.RESUL_FAIL);
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
		res = new JsonResponse(result);
		return res;
		// return result;
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
	public JsonResponse editPwd(String oldpassword, String password) {
		// Result result = new Result();
		Result result = null;
		JsonResponse res = null;
		try {
			// 解密
			byte[] byteStr = Base64.decodeBase64(oldpassword.getBytes("UTF-8"));
			byte[] byteStr2 = Base64.decodeBase64(password.getBytes("UTF-8"));
			oldpassword = new String(byteStr);
			password = new String(byteStr2);
			// 查询校验
			UserQuery example = new UserQuery();
			example.or().andPasswordEqualTo(MD5Util.string2MD5(oldpassword))
					.andUseridEqualTo(CurrentUserContext.getCurrentUserId());
			List<User> selectRt = userDao.selectByExample(example);
			if (selectRt != null && selectRt.size() == 1) {
				result = userService.editPwd(MD5Util.string2MD5(password));
				// result.setStatus(1);
			} else {
				// result.setStatus(0);
				result = new Result(MsgConstants.RESUL_FAIL);
			}
		} catch (Exception e) {
			// result.setStatus(0);
			result = new Result(MsgConstants.RESUL_FAIL);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
		res = new JsonResponse(result);
		return res;
		// return result;
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
	@RequestMapping(value = "/importUserNew", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public JsonResponse importUserNew(PageModel<User> pageModel, MultipartFile file, HttpServletRequest request,
			ModelMap model) {
		// String result = "";
		// Result result=new Result();
		Result result = null;
		JsonResponse res = null;
		try {
			res = userService.importUsersNew(file, request);
			// result = res;
			/*
			 * model.put("pageModel", pageModel); model.put("result", result);
			 */
		} catch (Exception e) {
			// result = "0";
			// result.setStatus(0);
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		// return "user/userList";
		return res;
	}

	/**
	 * 新增家族成员时判断家族容纳人数时候超出版本限制人数
	 * 
	 * @return 1T0F
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public JsonResponse checkFamilyUserNumber(HttpServletRequest request) {
		// String checkResult = "1"; // 家族容纳人数是否超出版本限制，1T0F
		Integer priValue = 0; // 最多容纳家族人数
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		// 获取该用户所在家族使用的版本特权信息
		try {
			SysVersionPrivilege versionP = sysVersionPrivilegeMapper
					.selectByVersionAndCode(CurrentUserContext.getCurrentFamilyId(), ConstantUtils.VERSION_USERCOUNT);
			if (versionP != null && versionP.getPrivilegevalue() != null) {
				if (versionP.getPrivilegevalue().equals(ConstantUtils.VERSION_UNLIMITED)) {
					// checkResult = "0";
					result = new Result(MsgConstants.RESUL_SUCCESS);
					res = new JsonResponse(result);
					return res;
				}
				priValue = Integer.valueOf(versionP.getPrivilegevalue());
			}
			// 获取该用户所在家族已有的人数
			UserQuery userExample = new UserQuery();
			userExample.or().andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
			int haveUserCount = userDao.countByExample(userExample); // 家族已有人数
			if (priValue > 0 && priValue > haveUserCount) {
				// checkResult = "0"; //家族人数未超出版本限制最大人数
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
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse searchUser(PageModel<User> pageModel, User user, ModelMap model) throws IOException {
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = CurrentUserContext.getCurrentUserId();
			UserbranchQuery ex = new UserbranchQuery();
			ex.or().andUseridEqualTo(userid);
			List<Userbranch> list = userBranchDao.selectByExample(ex);
			Branch bran = new Branch();
			for (Userbranch b : list) {
				bran.setBranchid(b.getBranchid());
				bran.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				bran = branchDao.selectByPrimaryKey(bran);
				if (bran.getBranchid() != null && !"".equals(bran.getBranchid()))
					user.setBranchid(b.getBranchid());
			}
			user.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			user.setStatus(0); // 用户状态默认启用
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
			pageModel.setList(userList);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModel);
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
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getAddressByUserid", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getAddressByUserid(User user) {
		return userService.getAddressByUserid(user);
	}

	/**
	 * api方法分割线-------------------------------------------------------------------------------
	 */

	/**
	 * 注册用户
	 * 
	 * @param entity
	 * @param smscode
	 * @return
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse regist(HttpServletRequest req, User entity, String smscode) {
		return userService.regist(entity, smscode);
	}

	/**
	 * 获取在线用户
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getOnlineUser", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getOnlineUser(HttpServletRequest req) {
		ServletContext servletContext = req.getServletContext();
		return userService.getOnlineUser(servletContext);
	}

	/**
	 * 退出登录
	 * 
	 * @param req
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse logout(HttpServletRequest req, User entity) {
		ServletContext servletContext = req.getServletContext();
		return userService.logout(servletContext, entity);
	}

	/**
	 * 1.1.1 登录\切换
	 * 
	 * @since 1.0
	 * @version 5.1.1 支持单家族和多家族用户登录、支持多家族用户登陆后切换企业
	 * @author 李鹏 17-02-15
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse login(HttpServletRequest req, User entity, Usercode usercode, String loginType,
			String internetType, String version) {
		// 先取请求session的id
		String sessionid = UUIDUtils.getUUID();
		entity.setSessionid(sessionid);
		// 进行查询
		if (usercode != null && usercode.getSmscode() != null && !"".equals(usercode.getSmscode())) {
			return userService.login(req, entity, loginType, internetType, version, usercode.getSmscode());
		} else {
			return userService.login(req, entity, loginType, internetType, version, null);
		}
	}

	/**
	 * 微信端登录，使用RSA加密
	 * 
	 * @param req
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginB", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse loginB(HttpServletRequest req, User entity) throws Exception {
		String password = entity.getPassword();
		RSAPrivateKey privateKey = (RSAPrivateKey) req.getSession().getAttribute("privateKey");
		if (privateKey == null) {
			Result result = new Result(MsgConstants.RESUL_FAIL);
			JsonResponse res = new JsonResponse(result);
			res.setData(0);
			return res;
		}
		String descrypedPwd = RSAUtils.decryptByPrivateKey(password, privateKey); // 解密后的密码,password是提交过来的密码
		String md5Pwd = MD5Util.string2MD5(descrypedPwd);
		entity.setPassword(md5Pwd);
		// 先去请求session的id
		String sessionid = req.getSession().getId();
		entity.setSessionid(sessionid);
		// 进行查询
		return userService.login(req, entity, "wechat", "其他", "1.0", "");
	}

	/**
	 * 发送短信验证码 一天之内最多发10条； 系统不存在的手机号（用户）不允许发验证码;一条验证码3分钟内有效，3分钟之内不允许再次发送 by 李鹏
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/sendSMSCode", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse sendSMSCode(User entity) {
		return userService.sendSMSCode(entity);
	}

	/**
	 * 发送短信验证码 一天之内最多发10条；注册家族时使用，系统不存在的用户也可以发；一条验证码3分钟内有效，3分钟之内不允许再次发送 by 李鹏
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/sendSMSCodeForReg", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse sendSMSCodeForReg(User entity) {
		return userService.sendSMSCodeForReg(entity);
	}

	/**
	 * 通过验证码登录
	 * 
	 * @param req
	 * @param entity
	 * @param loginType
	 * @param internetType
	 * @param version
	 * @param usercode
	 * @return
	 */
	@RequestMapping(value = "/loginWithCaptcha", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse loginWithCaptcha(HttpServletRequest req, User entity, String loginType, String internetType,
			String version, Usercode usercode) {
		// 先取请求session的id
		String sessionid = req.getSession().getId();
		entity.setSessionid(sessionid);
		return userService.loginWithCaptcha(entity, loginType, internetType, version, usercode.getSmscode());
	}

	/**
	 * 通过微信/QQ等授权登陆
	 * 
	 * @param req
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/loginWithThirdParty", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse loginWithThirdParty(HttpServletRequest req, User entity) {
		// 先取请求session的id
		String sessionid = req.getSession().getId();
		entity.setSessionid(sessionid);
		return userService.loginWithThirdParty(entity);
	}

	/**
	 * 绑定微信/QQ等的唯一opeinid
	 * 
	 * @param entity
	 * @param usercode
	 * @param loginstatus
	 * @return
	 */
	@RequestMapping(value = "/bindingWithThirdParty", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse bindingWithThirdParty(User entity, Usercode usercode, Integer loginstatus) {
		// 进行查询
		if (usercode != null && usercode.getSmscode() != null && !"".equals(usercode.getSmscode())) {
			return userService.bindingWithThirdParty(entity, usercode.getSmscode(), loginstatus);
		} else {
			return userService.bindingWithThirdParty(entity, null, loginstatus);
		}
	}

	/**
	 * 解除绑定微信/QQ等的唯一opeinid
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/relieveWithThirdParty", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse relieveWithThirdParty(User entity) {
		return userService.relieveWithThirdParty(entity);
	}

	/**
	 * 判断当前openid是否绑定了用户
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/isBindingWithUser", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse isBindingWithUser(User entity) {
		return userService.isBindingWithUser(entity);
	}

	/**
	 * 验证验证码的合法性
	 * 
	 * @param entity
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/checkSMSCode", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse checkSMSCode(User entity, String code) {
		return userService.checkSMSCode(entity, code);
	}

	/**
	 * 获取用户简要信息
	 * 
	 * @param entity
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/getPersonInfoLimit", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getPersonInfoLimit(User entity) {
		return userService.getPersonInfoLimit(entity);
	}

	/**
	 * 重置密码
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/restPassword", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse restPassword(User entity, String firstpwd, String secondpwd) {
		return userService.restPassword(entity, firstpwd, secondpwd);
	}

	/**
	 * 获取所有家族成员（在世）的通讯录信息
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getAllPersonInfos", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getAllPersonInfos(User entity) {
		return userService.getAllPersonInfos(entity);
	}

	/**
	 * 获取更新通讯录信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/updatePersonsByUpdatetime", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updatePersonsByUpdatetime(@RequestBody User user) {
		return userService.updatePersonsByUpdatetime(user);
	}

	/**
	 * 高级查询
	 * 
	 * @param searchComplex
	 * @return
	 */
	@RequestMapping(value = "/searchComplex", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse searchComplex(@RequestBody SearchComplex searchComplex) {
		return userService.searchComplex(searchComplex);
	}

	/**
	 * 获取更新通讯录信息
	 * 
	 * @param searchComplex
	 * @return
	 */
	@RequestMapping(value = "/updatePersonsByUpdatetimeExt", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updatePersonsByUpdatetimeExt(@RequestBody User user) {
		return userService.updatePersonsByUpdatetimeExt(user);
	}

	/**
	 * 获取个人的详细信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getPersonInfos", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getPersonInfos(User user) {
		return userService.getPersonInfos(user);
	}

	/**
	 * 换头像
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/changeImgurl", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse changeImgurl(User user) {
		return userService.changeImgurl(user);
	}

	/**
	 * 修改个人详细资料
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/changeUserinfos", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse changeUserinfos(@RequestBody User user) {
		return userService.changeUserinfos(user);
	}

	/**
	 * 获取用户所在城市下的所有动态列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getCityNoticeList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getCityNoticeList(User user) {
		return userService.getCityNoticeList(user);
	}

	/**
	 * 获取用户指定城市下的所有公告列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getCityNoticeListExt", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getCityNoticeListExt(User user) {
		return userService.getCityNoticeListExt(user);
	}

	/**
	 * 获取用户所在城市下的所有动态列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getCityDyList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getCityDyList(User user) {
		return userService.getCityDyList(user);
	}

	/**
	 * 获取用户指定城市下的所有动态列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getCityDyListExt", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getCityDyListExt(User user) {
		return userService.getCityDyListExt(user);
	}

	/**
	 * 获取用户所在城市下的所有相册列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getCityAlbumList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getCityAlbumList(User user) {
		return userService.getCityAlbumList(user);
	}

	/**
	 * 获取用户指定城市下的所有相册列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getCityAlbumListExt", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getCityAlbumListExt(User user) {
		return userService.getCityAlbumListExt(user);
	}

	/**
	 * 添加子女
	 * 
	 * @param userChildInfo
	 * @return
	 */
	@RequestMapping(value = "/addChild", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse addChild(@RequestBody UserClildInfo userChildInfo) {
		return userService.addChild(userChildInfo);
	}

	/**
	 * 全体通讯录查询
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/searchAllBytitle", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse searchAllBytitle(User user) {
		return userService.searchAllBytitle(user);
	}

	/**
	 * 分之内通讯录查询
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/searchBranchBytitle", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse searchBranchBytitle(User user) {
		return userService.searchBranchBytitle(user);
	}

	/**
	 * 删除指定工作经历
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/deleteUserWorkExp", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse deleteUserWorkExp(User user) {
		return userService.deleteUserWorkExp(user);
	}

	/**
	 * 删除教育经历
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/deleteUserEduExp", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse deleteUserEduExp(User user) {
		return userService.deleteUserEduExp(user);
	}

	/**
	 * 加入家族
	 * 
	 * @param user
	 * @param birthday
	 * @param nation
	 * @return
	 */
	@RequestMapping(value = "/joinFamily", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse joinFamily(User user, String birthday, String nation) {
		return userService.joinFamily(user, birthday, nation);
	}

	/**
	 * 我申请中的家族
	 * 
	 * @param user
	 * @param birthday
	 * @param nation
	 * @return
	 */
	@RequestMapping(value = "/applyingFamily", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse applyingFamily(User user) {
		return userService.applyingFamily(user);
	}

	/**
	 * 我已关联过的家族
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/joinedFamily", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse joinedFamily(User user) {
		return userService.joinedFamily(user);
	}

	/**
	 * 切换家族登录（已登录得情况下）
	 * @param user
	 * @param loginType
	 * @param internetType
	 * @param version
	 * @return
	 */
	@RequestMapping(value = "/changeLoginUser", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse changeLoginUser(User user, String loginType, String internetType, String version) {
		// 先取请求session的id
		String sessionid = UUIDUtils.getUUID();
		user.setSessionid(sessionid);
		return userService.changeLoginUser(user, loginType, internetType, version);
	}

}
