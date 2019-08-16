package com.jp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.PageModel;
import com.jp.dao.FunctionDao;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.Function;
import com.jp.entity.FunctionQuery;
import com.jp.entity.FunctionQuery.Criteria;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.service.FunctionService;
import com.jp.util.StringTools;

@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionDao functionDao;
	@Autowired
	private UserManagerMapper userManagerMapper;

	@Override
	public PageModel<Function> pageQuery(PageModel<Function> pageModel, Function function) throws Exception {

		FunctionQuery fq = new FunctionQuery();
		Criteria createCriteria = fq.createCriteria();

		if (StringTools.trimNotEmpty(function.getFunctionid())) {
			createCriteria.andFunctionidEqualTo(function.getFunctionid());
		}

		if (StringTools.trimNotEmpty(pageModel.getSortOrder())) {
			fq.setOrderByClause(pageModel.getSortOrder() + " " + pageModel.getSortType());
		} else {
			//			fq.setOrderByClause("");
			//			pageModel.setSortOrder("id");
			//			pageModel.setSortType("desc");
		}

		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Function> list = functionDao.selectByExample(fq);
		pageModel.setPageInfo(new PageInfo<Function>(list));

		return pageModel;
	}

	@Override
	public int insert(Function function) throws Exception {
		return 0;
	}

	@Override
	public void deleteAndAll(Function function) throws Exception {

	}

	@Override
	public List<Function> selectFunctionListByRoleid(String familyid, String roleid) throws Exception {

		return functionDao.selectFunctionListByRoleid(familyid, roleid);
	}

	@Override
	public List<Function> selectFunctionListByRoleidAndFamilyid(String familyid, String roleid) throws Exception {

		return functionDao.selectFunctionListByRoleidAndFamilyid(familyid, roleid);
	}

	@Override
	public List<Function> selectFunctionListByManagerid(String familyid, String userid) {
		UserManagerExample example = new UserManagerExample();
		example.or().andUseridEqualTo(userid);
		example.setOrderByClause("ebtype desc,ismanager desc");
		List<UserManager> managers = userManagerMapper.selectByExample(example);
		List<Function> rtnlist = new ArrayList<Function>();
		for (UserManager manager : managers) {
			//总编委会主任查询所有的菜单
			if (manager.getIsmanager() == 1 && manager.getEbtype() == 1) {
				rtnlist = functionDao.selectFunctionListByRoleid(familyid, null);
				return rtnlist;
			}
			rtnlist = functionDao.selectFunctionByUserid(familyid, userid);
		}

		return rtnlist;
	}

	@Override
	public List<Function> selectFunctionListByEbid(String familyid, String userid, String ebid, String postid) {
		return functionDao.selectFunctionListByEbid(familyid, userid, ebid, postid);
	}

}