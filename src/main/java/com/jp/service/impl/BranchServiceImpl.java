package com.jp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.BranchDao;
import com.jp.dao.SysFamilyDao;
import com.jp.dao.SysVersionDao;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.dao.UserDao;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.Branch;
import com.jp.entity.BranchQuery;
import com.jp.entity.BranchQuery.Criteria;
import com.jp.entity.SysFamily;
import com.jp.entity.SysVersionPrivilege;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.entity.UserQuery;
import com.jp.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchDao branchDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserManagerMapper userManagerMapper;
    @Autowired
    private SysFamilyDao sysFamilyDao;
    @Autowired
    private SysVersionPrivilegeMapper sysVersionPrivilegeMapper;
    
    /**
     * 从起始人按父子关系，递归更新分支用户（包括配偶）的分支属性
     * 
     * @param userid
     */
    private void updateUserBranch(String userid, String branchid, String branchname) {
        // 获取直系起始用户
        User pUser = userDao.selectByPrimaryKey(userid);
        if(pUser!=null && !"".equals(pUser.getUserid())) {
        	pUser.setBranchid(branchid);
            pUser.setBranchname(branchname);
            userDao.updateByPrimaryKey(pUser);
            if (pUser.getMateid() != null || !"".equals(pUser.getMateid())) {
                User pUserMate = userDao.selectByPrimaryKey(pUser.getMateid());
                if (pUserMate != null) {
                    pUserMate.setBranchid(branchid);
                    pUserMate.setBranchname(branchname);
                    userDao.updateByPrimaryKey(pUserMate);
                }
            }
            // 获取孩子节点
            UserQuery userQuery = new UserQuery();
            userQuery.or().andPidEqualTo(pUser.getUserid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
            List<User> children = userDao.selectByExample(userQuery);
            if (children.size() == 0)
                return;
            for (User user : children) {
                user.setBranchid(branchid);
                user.setBranchname(branchname);
                userDao.updateByPrimaryKey(user);
                updateUserBranch(user.getUserid(), branchid, branchname);
            }
        }
        
    }


    @Override
    public PageModel<Branch> pageQuery(PageModel<Branch> pageModel, Branch branch)
            throws Exception {
        PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
        List<Branch> list = branchDao.selectBranchListByFamilyAndUserid(CurrentUserContext.getCurrentFamilyId(),null,branch.getBranchname());
        UserQuery ex = new UserQuery();
        for(Branch b : list) {
         	ex.clear();
         	ex.or().andBranchidEqualTo(b.getBranchid())
         			.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
         	int num = userDao.countByExample(ex);
         	b.setUsercount(num);
         }
        pageModel.setList(list);
        pageModel.setPageInfo(new PageInfo<Branch>(list));
        return pageModel;
    }

    @Override
    public int insert(Branch branch) throws Exception {
    	//查询家族，获取家族使用的版本
    	SysFamily sysFamily = sysFamilyDao.selectByPrimaryKey(branch.getFamilyid());
    	//查询家族版本特权，获取家族可创建的分支数量
    	SysVersionPrivilege sysVersionPrivilege = sysVersionPrivilegeMapper.selectVersionValue(sysFamily.getVersion(),ConstantUtils.VERSION_BRANCH);
    	//查询家族现在已创建的分支数量
    	List<String> branchids = branchDao.selectByFamilyid(branch.getFamilyid());
    	if("1".equals(sysVersionPrivilege.getPrivilegevalue())) {
    		//普通版只能免费创建分支1个
    		if(branchids.size()>=1) {
    			return -1;//暂时返回-1，表示不能创建分支了
    		}
    	}
    	if("5".equals(sysVersionPrivilege.getPrivilegevalue())) {
    		//旗舰版只能免费创建分支5个
    		if(branchids.size()>=5) {
    			return -1;//暂时返回-1，表示不能创建分支了
    		}
    	}
        int count = branchDao.insertSelective(branch);
        updateUserBranch(branch.getBeginuserid(), branch.getBranchid(), branch.getBranchname());
        return count;
    }

    @Override
    public Branch get(String branchid) throws Exception {

        BranchQuery bq = new BranchQuery();
        Criteria createCriteria = bq.createCriteria();

        createCriteria.andBranchidEqualTo(branchid);

        List<Branch> branch = branchDao.selectByExample(bq);
        return branch.get(0);
    }

    @Override
    public PageModel<Branch> initBranch(PageModel<Branch> pageModel, Branch branch)
            throws Exception {
        List<Branch> list = branchDao.selectBranchList(branch);
        pageModel.setList(list);
        pageModel.setPageInfo(new PageInfo<Branch>(list));
        return pageModel;
    }

    @Override
    public int update(Branch branch) throws Exception {
        updateUserBranch(branch.getBeginuserid(), branch.getBranchid(), branch.getBranchname());
        return branchDao.updateByPrimaryKeySelective(branch);
    }

    
    
    @Override
    public List<Branch> selectBranchListByFamilyAndUserid(String familyid, String userid)
            throws Exception {
    	//List<Branch> branchs =null;
    	List<Branch> rtnlist = new ArrayList<Branch>();
    	UserManagerExample example = new UserManagerExample();
    	example.or().andUseridEqualTo(userid);
    	List<UserManager> managers = userManagerMapper.selectByExample(example);
//    	List<String> managerids =new ArrayList<String>();
    	for(UserManager manager : managers){
    		 //branchs = new ArrayList<Branch>();
    		//总编委会查看全部
    		if(manager.getEbtype()==1 ) {
    			rtnlist = branchDao.selectBranchListByFamilyAndUserid(familyid, null,null);
    			break;
    		}
    		rtnlist = branchDao.getBranchsByFamilyAndUserid(familyid, userid,null);
    		break;
    	}
    	//List<Branch> branchs = branchDao.selectBranchListByFamilyAndManagerids(familyid, managerids);
    	//branchDao.selectBranchListByFamilyAndUserid(familyid, userid);
        return rtnlist;
    }


    @Override
    public int changeStatus(Branch branch) throws Exception {
        return branchDao.updateByBranchidSelective(branch);
    }


	@Override
	public boolean validateBranchname(Branch branch) throws Exception {
		String result = "";
		int count  = branchDao.validateBranchname(branch);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}


	@Override
	public PageModel<Branch> selectBranchListByFamilyAndUserid(PageModel<Branch> pageModel,Branch branch) throws Exception {
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Branch> list = branchDao.getBranchsByFamilyAndUserid(CurrentUserContext.getCurrentFamilyId(), CurrentUserContext.getCurrentUserId(),branch.getBranchname());
	    UserQuery ex = new UserQuery();
	    for(Branch b : list) {
         	ex.clear();
         	ex.or().andBranchidEqualTo(b.getBranchid());
         	int num = userDao.countByExample(ex);
         	b.setUsercount(num);
         }
	    pageModel.setList(list);
	    pageModel.setPageInfo(new PageInfo<Branch>(list));
	    return pageModel;
	}
	
}
