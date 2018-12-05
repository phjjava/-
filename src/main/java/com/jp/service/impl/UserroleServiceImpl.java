package com.jp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.UserbranchDao;
import com.jp.dao.UserroleDao;
import com.jp.entity.User;
import com.jp.entity.Userbranch;
import com.jp.entity.Userrole;
import com.jp.entity.UserroleQuery;
import com.jp.entity.UserroleQuery.Criteria;
import com.jp.service.UserroleService;
import com.jp.util.Result;
import com.jp.util.UUIDUtils;

@Service
public class UserroleServiceImpl implements UserroleService {

	@Autowired
	private UserroleDao userroledao;
	@Autowired
	private UserbranchDao userbranchDao;

	@Override
	public PageModel<User> pageQuery(PageModel<User> pageModel, Userrole userrole) throws Exception {
		
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<User> list = userroledao.selectUserRoleByFamilyId(userrole.getFamilyid(),userrole.getUsername());
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<User>(list));
		return pageModel;
	}

	@Override
	public Userrole get(String userroleid) throws Exception {
		UserroleQuery uq = new UserroleQuery();
		Criteria createCriteria = uq.createCriteria();
		createCriteria.andUseridEqualTo(userroleid);
		List<Userrole> userrole = userroledao.selectByExample(uq);
		return userrole.get(0);
	}
	@Override
	public Userrole selectByFamilyId(String familyid) throws Exception {
		return userroledao.selectByFamilyId(familyid);
	}

	@Override
	public String mergeUserRoleBranch(Userrole userrole, Userbranch userBranch) throws Exception{
		String result = "";
		try{
			List<Userbranch> userbranchList = new ArrayList<Userbranch>();
			Userbranch userbranch = null;
			String branchid = userBranch.getBranchid();
			String branchids [] = branchid.split(",");
			String userid = userrole.getUserid();
			//先删除后新增 
			userroledao.deleteByPrimaryKey(userrole.getUserid());
			for(int i = 0; i < branchids.length; i++){
				String[] branchidandname = branchids[i].split("_");
				userbranch = new Userbranch();
				userbranch.setUserid(userid);
				userbranch.setBranchid(branchidandname[0]);
				userbranch.setBranchname(branchidandname[1]);
				userbranchList.add(userbranch);
				
			}
			
			if(userbranchList != null && userbranchList.size() > 0){
				userbranchDao.batchDelete(userid);
				userbranchDao.batchInsert(userbranchList);
			}
			userroledao.insertSelective(userrole);
			result = "1";
		}catch(Exception e){
			result = "0";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Userrole selectByPrimaryKey(String userid) {
		return userroledao.selectByPrimaryKey(userid);
	}

	@Override
	public List<Userbranch> userBranchList(String userid) {
		return userroledao.userBranchList(userid);
	}

	@Override
	public Result deleteUserRole(User user) {
		Result result = new Result();
		UserroleQuery example=new UserroleQuery();
		example.or().andFamilyidEqualTo(user.getFamilyid()).andRoleidEqualTo(user.getRoleid())
		.andUseridEqualTo(user.getUserid());
		//先删除用户管理分支关系
		int branchRt=userbranchDao.batchDelete(user.getUserid());
		if(branchRt<1){
			result.setData(0);
			return result;
		}
		//删除角色用户绑定关系
		int rt = userroledao.deleteByExample(example);
		result.setData(rt);
		return result;
	}
}
