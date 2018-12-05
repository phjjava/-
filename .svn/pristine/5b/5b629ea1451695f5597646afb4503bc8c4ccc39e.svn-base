package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.PageModel;
import com.jp.dao.FuncRoleDao;
import com.jp.dao.RoleDao;
import com.jp.entity.Role;
import com.jp.entity.RoleQuery;
import com.jp.entity.RoleQuery.Criteria;
import com.jp.service.RoleService;
import com.jp.util.StringTools;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private FuncRoleDao funcRoleDao;

	@Override
	public PageModel<Role> pageQuery(PageModel<Role> pageModel, Role role) throws Exception {

		RoleQuery rq = new RoleQuery();
		Criteria createCriteria = rq.createCriteria();
		if(StringTools.notEmpty(role.getFamilyid())){
			createCriteria.andFamilyidEqualTo(role.getFamilyid());
		}
		
        createCriteria.andTypeEqualTo(1);
        createCriteria.andIsmanagerEqualTo(0);
		
		RoleQuery rq2 = new RoleQuery();
		Criteria createCriteria2 = rq2.createCriteria();
		
		if(StringTools.notEmpty(role.getFamilyid())){
			createCriteria2.andFamilyidEqualTo(role.getFamilyid());
		}
		// 查询type 0 其他 1 总编委会主任
        createCriteria2.andTypeEqualTo(0);
		// 查询Ismanager 0 其他 1 总编委会主任
        rq.or(createCriteria2);

		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Role> list = roleDao.selectByExample(rq);

		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Role>(list));

		return pageModel;
	}

	@Override
	public int insert(Role role, String[] functionids) throws Exception {
		int count = roleDao.insertSelective(role);

		funcRoleDao.deleteByPrimaryKey(role.getRoleid());

		if (functionids != null && functionids.length > 0) {
			funcRoleDao.insertBatch(role.getRoleid(), functionids);
		}
		if (count == 1) {
			return count;
		} else {
			return 0;
		}
	}

	@Override
    public Role get(String roleid) throws Exception {

		return roleDao.selectByPrimaryKey(roleid);
	}

	@Override
	public int update(Role role, String[] functionids) throws Exception {

		int count = roleDao.updateByPrimaryKeySelective(role);

		funcRoleDao.deleteByPrimaryKey(role.getRoleid());

		if (functionids != null && functionids.length > 0) {
			funcRoleDao.insertBatch(role.getRoleid(), functionids);
		}
		if (count == 1) {
			return count;
		} else {
			return 0;
		}

	}

	@Override
	public List<Role> selectRoleList(String familyid) throws Exception {

        // RoleQuery rq = new RoleQuery();
        //
        // Criteria createCriteria = rq.createCriteria();
        // // 查询type 0 其他 1 总编委会主任
        // createCriteria.andTypeNotEqualTo(1).andIsmanagerNotEqualTo(1);
        // // 查询Ismanager 0 其他 1 总编委会主任
        //// createCriteria.andIsmanagerEqualTo(0);
        // createCriteria.andFamilyidEqualTo(familyid);

        // List<Role> list = roleDao.selectByExample(rq);
        List<Role> list = roleDao.selectByFamilyid(familyid);
		return list;
	}

	@Override
	public Role selectRoleByUserid(String familyid, String userid) throws Exception {
		return roleDao.selectRoleByUserid(familyid, userid);
	}

	@Override
	public int del(String roleid) {
		return roleDao.deleteByPrimaryKey(roleid);
	}

}
