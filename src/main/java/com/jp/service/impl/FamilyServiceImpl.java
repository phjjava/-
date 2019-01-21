package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.PageModel;
import com.jp.dao.EditorialBoardMapper;
import com.jp.dao.IntroduceDao;
import com.jp.dao.IntroudceTemplateDao;
import com.jp.dao.IntroudceTemplateDetailDao;
import com.jp.dao.PostMapper;
import com.jp.dao.SysFamilyDao;
import com.jp.dao.UserDao;
import com.jp.dao.UserManagerMapper;
import com.jp.dao.UserinfoMapper;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Indexcount;
import com.jp.entity.Introduce;
import com.jp.entity.IntroudceTemplate;
import com.jp.entity.IntroudceTemplateDetail;
import com.jp.entity.IntroudceTemplateDetailExample;
import com.jp.entity.IntroudceTemplateExample;
import com.jp.entity.Post;
import com.jp.entity.SysFamily;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserQuery;
import com.jp.entity.Userinfo;
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
	private UserDao userDao;
	@Autowired
	private UserinfoMapper userInfoDao;
	
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

	
	
	private void  createIntroudce(String familyid) {
	
		//根据模版创建章节内容
		IntroudceTemplateExample introudceTemplateExample =new IntroudceTemplateExample();
		introudceTemplateExample.or().andDeleteflagEqualTo(0);
		List<IntroudceTemplate> introudceTemplates=introudceTemplateDao.selectByExample(introudceTemplateExample);
		if(introudceTemplates.size()>0)
		{
			IntroudceTemplateDetailExample introudceTemplateDetailExample=new IntroudceTemplateDetailExample();
			introudceTemplateDetailExample.or().andDeleteflagEqualTo(0).andTemplateidEqualTo(introudceTemplates.get(0).getId());
			List<IntroudceTemplateDetail> introudceTemplateDetails =introudceTemplateDetailDao.selectByExampleWithBLOBs(introudceTemplateDetailExample);
			for (IntroudceTemplateDetail introudceTemplateDetail : introudceTemplateDetails) {
				Introduce introduce=new Introduce();
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