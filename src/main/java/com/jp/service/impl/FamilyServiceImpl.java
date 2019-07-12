package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.EditorialBoardMapper;
import com.jp.dao.IntroduceDao;
import com.jp.dao.IntroudceTemplateDao;
import com.jp.dao.IntroudceTemplateDetailDao;
import com.jp.dao.PostMapper;
import com.jp.dao.SysFamilyDao;
import com.jp.dao.UserDao;
import com.jp.dao.UserManagerMapper;
import com.jp.dao.UserinfoMapper;
import com.jp.entity.Branch;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Indexcount;
import com.jp.entity.Introduce;
import com.jp.entity.IntroudceTemplate;
import com.jp.entity.IntroudceTemplateDetail;
import com.jp.entity.IntroudceTemplateDetailExample;
import com.jp.entity.IntroudceTemplateExample;
import com.jp.entity.Post;
import com.jp.entity.SysFamily;
import com.jp.entity.SysFamilyQuery;
import com.jp.entity.SysUser;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.entity.UserQuery;
import com.jp.entity.Userinfo;
import com.jp.entity.Version;
import com.jp.service.FamilyService;
import com.jp.util.MD5Util;
import com.jp.util.PinyinUtil;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class FamilyServiceImpl implements FamilyService {

	private final Logger log_ = LogManager.getLogger(FamilyServiceImpl.class);

	@Autowired
	private SysFamilyDao sysFamilyDao;

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserinfoMapper userInfoDao;
	@Autowired
	private BranchDao branchDao;

	@Autowired
	private EditorialBoardMapper editorialBoardMapper;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@Autowired
	private IntroudceTemplateDao introudceTemplateDao;
	@Autowired
	private IntroudceTemplateDetailDao introudceTemplateDetailDao;
	@Autowired
	private IntroduceDao introduceDao;
	@Autowired
	private PostMapper postMapper;

	@Override
	public JsonResponse merge(User user, Userinfo userInfo, SysFamily family) {
		Result result = null;
		JsonResponse res = null;
		if (user.getUsername() == null || "".equals(user.getUsername())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数username不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (user.getPhone() == null || "".equals(user.getPhone())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数phone不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (user.getFamilyname() == null || "".equals(user.getFamilyname())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数familyname不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (family.getSurname() == null || "".equals(family.getSurname())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数surname不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (family.getVersion() == null || "".equals(family.getVersion())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数version不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (family.getVersionname() == null || "".equals(family.getVersionname())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数versionname不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		SimpleDateFormat sdfd = new SimpleDateFormat("yyy-MM-dd");
		try {
			if (StringTools.trimNotEmpty(family.getFamilyid())) {

				user.setUpdatetime(new Date());
				// 重置密码 根据手机号来截取
				family.setUpdatetime(sdfd.parse(sdfd.format(new Date())));
				family.setUpdateid("sys_admin");
				//family.setFamilycode(PinyinUtil.getPinyinFull(family.getFamilyname()));
				user.setPassword(MD5Util.string2MD5(user.getPhone().substring(user.getPhone().length() - 6)));
				userDao.updateByPrimaryKeySelective(user);
				UserManager userManager = new UserManager();
				userManager.setUsername(user.getUsername());
				UserManagerExample ume = new UserManagerExample();
				ume.or().andUseridEqualTo(user.getUserid());
				userManagerMapper.updateByExampleSelective(userManager, ume);
				// userInfoDao.updateByPrimaryKeySelective(userInfo);
				sysFamilyDao.updateByPrimaryKeySelective(family);
				sysFamilyDao.deleteFunction(family.getFamilyid());
				sysFamilyDao.insertFunction(family.getFamilyid(), family.getVersion());

			} else {
				UserQuery userQuery = new UserQuery();
				userQuery.or().andPhoneEqualTo(user.getPhone()).andStatusEqualTo(0).andDeleteflagEqualTo(0);
				List<User> users = userDao.selectByExample(userQuery);
				if (users.size() > 0) {

					for (User user1 : users) {
						if (user1.getFamilyname().equals(family.getFamilyname())) {
							result = new Result(MsgConstants.RESUL_FAIL);
							result.setMsg("当前用户管理的家族名称已存在，请重试");
							res = new JsonResponse(result);
							return res;
						}
					}

				}
				String userId = UUIDUtils.getUUID();
				String familyId = UUIDUtils.getUUID();

				// user
				user.setUserid(userId);
				user.setFamilyid(familyId);
				user.setStatus(0);
				user.setIsdirect(1);
				user.setDeleteflag(0);
				user.setLivestatus(0);
				user.setCreatetime(new Date());
				user.setUpdatetime(new Date());
				user.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				user.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(user.getUsername()));
				user.setPinyinfull(PinyinUtil.getPinyinFull(user.getUsername()));
				if (StringTools.trimNotEmpty(user.getPhone())) {
					user.setPassword(MD5Util.string2MD5(user.getPhone().substring(user.getPhone().length() - 6)));
				}
				// userinfo
				userInfo.setUserid(userId);
				// 保存 user userinfo
				userDao.insertSelective(user);
				userInfoDao.insertSelective(userInfo);
				// 保存家族
				family.setCreatetime(new Date());
				family.setFamilyid(familyId);
				family.setStatus(0);
				family.setCreateid("sys_admin");
				family.setFamilycode(sysFamilyDao.nextVal() + "");

				// 保存总编委会主任信息 role userrole
				Post post = new Post();
				post.setId(UUIDUtils.getUUID());
				post.setFamilyid(familyId);
				post.setCreatetime(new Date());
				post.setIsmanager(1);
				post.setName("总编委会主任");
				post.setSort(0);
				post.setType(1);
				postMapper.insertSelective(post);
				EditorialBoard eb = new EditorialBoard();
				String ebid = UUIDUtils.getUUID();
				eb.setId(ebid);
				eb.setFamilyid(familyId);
				eb.setName("总编委会");
				eb.setCodetype("0");
				eb.setCode("0");
				eb.setType(1);
				editorialBoardMapper.insertSelective(eb);
				UserManager manager = new UserManager();
				manager.setId(UUIDUtils.getUUID());
				manager.setUserid(userId);
				manager.setUsername(user.getUsername());
				manager.setEbid(ebid);
				manager.setEbname("总编委会");
				manager.setPostid(post.getId());
				manager.setPostname(post.getName());
				manager.setEbtype(1);
				manager.setIsmanager(1);
				manager.setFamilyid(familyId);
				userManagerMapper.insertSelective(manager);
				sysFamilyDao.insertSelective(family);
				sysFamilyDao.insertFunction(familyId, family.getVersion());

				//创建章节模版
				createIntroudce(familyId);

			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			log_.error("[merge方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		// if(result != 1){
		// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		// }
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

	private void createIntroudce(String familyid) {

		//根据模版创建章节内容
		IntroudceTemplateExample introudceTemplateExample = new IntroudceTemplateExample();
		introudceTemplateExample.or().andDeleteflagEqualTo(0);
		List<IntroudceTemplate> introudceTemplates = introudceTemplateDao.selectByExample(introudceTemplateExample);
		if (introudceTemplates.size() > 0) {
			IntroudceTemplateDetailExample introudceTemplateDetailExample = new IntroudceTemplateDetailExample();
			introudceTemplateDetailExample.or().andDeleteflagEqualTo(0)
					.andTemplateidEqualTo(introudceTemplates.get(0).getId());
			List<IntroudceTemplateDetail> introudceTemplateDetails = introudceTemplateDetailDao
					.selectByExampleWithBLOBs(introudceTemplateDetailExample);
			for (IntroudceTemplateDetail introudceTemplateDetail : introudceTemplateDetails) {
				Introduce introduce = new Introduce();
				introduce.setFamilyid(familyid);
				introduce.setIntroduceid(UUIDUtils.getUUID());
				introduce.setIntroducetitle(introudceTemplateDetail.getTitle());
				introduce.setIntroducedetail(introudceTemplateDetail.getContent());
				introduce.setSort(introudceTemplateDetail.getSort());
				introduce.setType(introudceTemplateDetail.getType());
				introduce.setCreateid("admin");
				introduce.setCreatetime(new Date());
				introduce.setUpdatetime(new Date());
				introduce.setUpdateid("admin");
				introduce.setDeleteflag(0);
				introduceDao.insert(introduce);
			}

		}

	}

	@Override
	public JsonResponse selectFamilyList(PageModel<SysFamily> pageModel, SysFamily family) {
		Result result = null;
		JsonResponse res = null;
		if (pageModel.getPageNo() == null || "".equals(pageModel.getPageNo() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageNo不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (pageModel.getPageSize() == null || "".equals(pageModel.getPageSize() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageSize不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<SysFamily> familyList = sysFamilyDao.selectFamilyList(family);
			if (familyList != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(familyList);
				res.setCount(new PageInfo<SysFamily>(familyList).getTotal());
				return res;
			}
		} catch (Exception e) {
			log_.error("[pageQuery方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;

	}

	@Override
	public SysFamily selectByPrimaryKey(String familyid) throws Exception {
		return sysFamilyDao.selectByPrimaryKey(familyid);
	}

	@Override
	public JsonResponse changeStatus(SysFamily family, HttpSession httpSession) {
		Result result = null;
		JsonResponse res = null;
		if (family.getStatus() == null || "".equals(family.getStatus() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数status不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (family.getFamilyid() == null || "".equals(family.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数familyid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			SysUser user = (SysUser) httpSession.getAttribute("systemUserContext");
			family.setUpdatetime(new Date());
			family.setUpdateid(user.getUserid());
			int status = sysFamilyDao.changeStatus(family);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[changeStatus方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public Indexcount countIndex(String familyid, List<String> branchids) {
		return sysFamilyDao.countIndex(familyid, branchids);
	}

	@Override
	public List<Version> selectList() {
		return sysFamilyDao.selectList();
	}

	@Override
	public JsonResponse mergeForApi(User user, Userinfo userinfo, SysFamily family) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			SimpleDateFormat sdfd = new SimpleDateFormat("yyy-MM-dd");

			if (StringUtils.isBlank(user.getUserid())) {
				result.setMsg("用户userid为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (StringUtils.isBlank(user.getUsername())) {
				result.setMsg("用户name为空！");
				res = new JsonResponse(result);
				return res;
			}
			String username = user.getUsername();
			Branch branch = user.getBranch();
			if (family.getFamilyid() != null && !family.getFamilyid().equals("")) {
				user.setUpdatetime(new Date());
				// 重置密码 根据手机号来截取
				family.setUpdatetime(sdfd.parse(sdfd.format(new Date())));
				family.setUpdateid(user.getUserid());
				user.setPassword(MD5Util.string2MD5(user.getPhone().substring(user.getPhone().length() - 6)));
				userDao.updateByPrimaryKeySelective(user);
				sysFamilyDao.updateByPrimaryKeySelective(family);
				sysFamilyDao.deleteFunction(family.getFamilyid());
				sysFamilyDao.insertFunction(family.getFamilyid(), family.getVersion());
			} else {
				if (StringUtils.isBlank(family.getSurname())) {
					result.setMsg("家族姓氏surname为空！");
					res = new JsonResponse(result);
					return res;
				}
				List<User> users = userDao.selectByPhone(user.getPhone());
				if (users.size() > 0) {
					for (User user1 : users) {
						if (family.getFamilyname().equals(user1.getFamilyname())) {
							// result.setStatus(1);
							result.setMsg("当前用户管理的家族名称已存在，请重试");
							return res;
						}
					}

				}
				String userId = user.getUserid();
				String familyId = UUIDUtils.getUUID();
				UserManager manager = new UserManager();
				//如果存在未关联家族的用户，不在新建用户
				UserQuery u1example = new UserQuery();
				u1example.or().andPhoneEqualTo(user.getPhone()).andFamilyidIsNull().andStatusNotEqualTo(0)
						.andDeleteflagEqualTo(0);
				List<User> user1s = userDao.selectByExample(u1example);
				if (user1s.size() > 0) {
					user = user1s.get(0);
					user.setUsername(username);
					userinfo.setUserid(user.getUserid());
					manager.setUserid(user.getUserid());
				} else {
					// user
					user.setUserid(userId);
					user.setCreateid(userId);
					userinfo.setUserid(userId);
					manager.setUserid(userId);
				}
				user.setFamilyid(familyId);
				Integer staa = 0;
				byte bta = (byte) staa.intValue();
				user.setStatus(staa);
				Integer isdir = 1;
				byte isdirset = (byte) isdir.intValue();
				user.setIsdirect(isdir);
				Integer delete = 0;
				byte deleteset = (byte) delete.intValue();
				user.setDeleteflag(delete);
				user.setLivestatus(delete);
				user.setCreatetime(new Date());
				user.setUpdatetime(new Date());
				user.setFamilyname(family.getFamilyname());
				user.setSex(1);
				user.setUpdateid("");
				user.setPinyinfirst(PinyinUtil.getPinYinFirstChar(user.getUsername()));
				user.setPinyinfull(PinyinUtil.getPinyinFull(user.getUsername()));
				if (user.getPhone() != null && !user.getPhone().equals("")) {
					user.setPassword(MD5Util.string2MD5(user.getPhone().substring(user.getPhone().length() - 6)));
				}
				// 保存家族
				//普通版
				family.setVersion("1c810b79c3a64f4c8ec166efd727eaa9");
				family.setCreatetime(new Date());
				family.setFamilyid(familyId);
				Integer sta = 0;
				byte bt = (byte) sta.intValue();
				family.setStatus(sta);
				family.setCreateid("sys_admin");
				family.setFamilycode(sysFamilyDao.nextVal() + "");

				//创建默认分支

				if (branch == null) {
					result.setMsg("地区信息为空！");
					res = new JsonResponse(result);
					return res;
				} else {
					//分支地区信息不为空
					if (branch.getArea() == null || "".equals(branch.getArea()) || branch.getAreacode() == null
							|| "".equals(branch.getAreacode())) {
						result.setMsg("请选择地区信息！");
						res = new JsonResponse(result);
						return res;
					}
				}
				String branchid = UUIDUtils.getUUID();
				branch.setBranchid(branchid);
				branch.setBranchname("默认分支");
				branch.setStatus(0);
				branch.setFamilyid(familyId);
				branch.setBeginuserid(user.getUserid());
				branch.setBeginname(user.getUsername());

				// 保存总编委会主任信息 
				Post post = new Post();
				post.setId(UUIDUtils.getUUID());
				post.setFamilyid(familyId);
				post.setCreatetime(new Date());
				post.setIsmanager(1);
				post.setName("总编委会主任");
				post.setSort(0);
				post.setType(1);

				EditorialBoard eb = new EditorialBoard();
				String ebid = UUIDUtils.getUUID();
				eb.setId(ebid);
				eb.setFamilyid(familyId);
				eb.setName("总编委会");
				eb.setCodetype("0");
				eb.setCode("0");
				eb.setType(1);

				manager.setId(UUIDUtils.getUUID());
				manager.setUsername(user.getUsername());
				manager.setEbid(ebid);
				manager.setEbname("总编委会");
				manager.setPostid(post.getId());
				manager.setPostname(post.getName());
				manager.setEbtype(1);
				manager.setIsmanager(1);
				manager.setFamilyid(familyId);
				// 保存 user userinfo
				if (user1s.size() > 0) {
					userDao.updateByPrimaryKeySelective(user);
					userInfoDao.updateByPrimaryKeySelective(userinfo);
				} else {
					userDao.insertSelective(user);
					userInfoDao.insertSelective(userinfo);
				}
				postMapper.insertSelective(post);
				editorialBoardMapper.insertSelective(eb);
				branchDao.insertSelective(branch);
				userManagerMapper.insertSelective(manager);
				sysFamilyDao.insertSelective(family);
				sysFamilyDao.insertFunction(familyId, family.getVersion());

				//创建章节模版
				createIntroudce(familyId);

				result = new com.jp.common.Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			res = new JsonResponse(result);
			res.setMsg("网络错误");
			log_.error("[FamilyServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse searchFamily(SysFamily family) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			if (StringUtils.isBlank(family.getFamilycode())) {
				result.setMsg("参数familycode为空！");
				res = new JsonResponse(result);
				return res;
			}
			SysFamilyQuery sysFamilyExample = new SysFamilyQuery();
			sysFamilyExample.or().andFamilycodeEqualTo(family.getFamilycode());
			List<SysFamily> sysFamilys = sysFamilyDao.selectByExample(sysFamilyExample);
			if (sysFamilys.size() == 0) {
				result.setMsg("没有该家族");
				res = new JsonResponse(result);
				return res;
			}
			result = new com.jp.common.Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(sysFamilys.get(0));
		} catch (Exception e) {
			res = new JsonResponse(result);
			log_.error("[FamilyServiceImpl---Error:]", e);
		}
		return res;
	}

}