package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.PageModel;
import com.jp.dao.EditorialBoardMapper;
import com.jp.dao.RoleDao;
import com.jp.dao.SysFamilyDao;
import com.jp.dao.SysVersionDao;
import com.jp.dao.UserDao;
import com.jp.dao.UserManagerMapper;
import com.jp.dao.UserinfoDao;
import com.jp.dao.UserroleDao;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Indexcount;
import com.jp.entity.Role;
import com.jp.entity.SysFamily;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserQuery;
import com.jp.entity.Userinfo;
import com.jp.entity.Userrole;
import com.jp.service.FamilyService;
import com.jp.util.MD5Util;
import com.jp.util.PinyinUtil;
import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class FamilyServiceImpl implements FamilyService {
	@Autowired
	private SysFamilyDao sysFamilyDao;
	@Autowired
	private SysVersionDao sysVersionDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserinfoDao userInfoDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserroleDao userRoleDao;
	@Autowired
	private EditorialBoardMapper editorialBoardMapper;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@Override
    public Result merge(User user, Userinfo userInfo, SysFamily family) throws Exception {
        Result result = new Result();
		SimpleDateFormat sdfd = new SimpleDateFormat("yyy-MM-dd");
		try {
			if (StringTools.trimNotEmpty(family.getFamilyid())) {

				user.setUpdatetime(new Date());
				// 重置密码 根据手机号来截取
                family.setUpdatetime(sdfd.parse(sdfd.format(new Date())));
                family.setUpdateid("sys_admin");
                family.setFamilycode(PinyinUtil.getPinyinFull(family.getFamilyname()));
				user.setPassword(MD5Util.string2MD5(user.getPhone().substring(user.getPhone().length() - 8)));
				userDao.updateByPrimaryKeySelective(user);
				// userInfoDao.updateByPrimaryKeySelective(userInfo);
                sysFamilyDao.updateByPrimaryKeySelective(family);
				sysFamilyDao.deleteFunction(family.getFamilyid());
				sysFamilyDao.insertFunction(family.getFamilyid(), family.getVersion());
				
				
				
			} else {
                UserQuery userQuery = new UserQuery();
                userQuery.or().andPhoneEqualTo(user.getPhone()).andStatusEqualTo(0)
                        .andDeleteflagEqualTo(0);
                List<User> users = userDao.selectByExample(userQuery);
                if (users.size() > 0) {
                    User user1 = users.get(0);
                    if (user1.getFamilyname().equals(family.getFamilyname())) {
                        result.setStatus(1);
                        result.setMsg("当前用户管理的家族名称已存在，请重试");
                        return result;
                    }
                }
				String userId = UUIDUtils.getUUID();
				String familyId = UUIDUtils.getUUID();
				String roleId = UUIDUtils.getUUID();
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
                family.setFamilycode(PinyinUtil.getPinyinFull(family.getFamilyname()));

				// 保存总编委会主任信息 role userrole
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
				manager.setEbtype(1);
				manager.setIsmanager(1);
				manager.setFamilyid(familyId);
				userManagerMapper.insertSelective(manager);
				sysFamilyDao.insertSelective(family);
				sysFamilyDao.insertFunction(familyId, family.getVersion());
			}
		} catch (Exception e) {
			result.setStatus(1);
		    result.setMsg("系统异常！");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			return result;
		}
        // if(result != 1){
        // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        // }
        result.setStatus(0);
        result.setMsg("成功");
        return result;
	}

	@Override
	public PageModel selectFamilyList(PageModel pageModel, SysFamily family) throws Exception {

		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<SysFamily> familyList = sysFamilyDao.selectFamilyList(family);
		pageModel.setList(familyList);
		pageModel.setPageInfo(new PageInfo<SysFamily>(familyList));
		return pageModel;

	}

	@Override
	public SysFamily selectByPrimaryKey(String familyid) throws Exception {
		return sysFamilyDao.selectByPrimaryKey(familyid);
	}

	@Override
	public int changeStatus(SysFamily family) throws Exception {
		return sysFamilyDao.changeStatus(family);
	}

	@Override
	public Indexcount countIndex(String familyid, List<String> branchids) {
		return sysFamilyDao.countIndex(familyid, branchids);
	}

}