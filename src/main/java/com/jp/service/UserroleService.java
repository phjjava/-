package com.jp.service;

import java.util.List;

import com.jp.common.PageModel;
import com.jp.entity.User;
import com.jp.entity.Userbranch;
import com.jp.entity.Userrole;
import com.jp.util.Result;

public interface UserroleService {
	
	PageModel<User> pageQuery(PageModel<User> pageModel, Userrole userrole) throws Exception;

	Userrole get(String userroleid) throws Exception;
	
	Userrole selectByFamilyId(String familyid) throws Exception;
	
	String mergeUserRoleBranch(Userrole userrole,Userbranch userBranch) throws Exception;
	Userrole selectByPrimaryKey (String userid);
	List<Userbranch> userBranchList(String userid);
	/**
	 * 根据userid,roleid,familyid删除用户角色关系,管理分支关系
	 * @描述 
	 * @作者 jinlizhi
	 * @时间 2017年6月27日下午5:51:58
	 * @参数 @param user
	 * @参数 @return
	 * @return Result
	 */
	Result deleteUserRole(User user);
}
