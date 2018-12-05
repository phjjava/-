package com.jp.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.UserDao;
import com.jp.dao.UsercontentDao;
import com.jp.entity.Usercontent;
import com.jp.entity.UsercontentQuery;
import com.jp.entity.UsercontentQuery.Criteria;
import com.jp.service.FamousService;

@Service
public class FamousServiceImpl implements FamousService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UsercontentDao usercontentDao;

	@Override
	public PageModel<Usercontent> selectContentList(PageModel<Usercontent> pageModel,Usercontent usercontent) {

		usercontent.setFamilyid(CurrentUserContext.getCurrentFamilyId());
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Usercontent> list = userDao.selectUserContentList(usercontent);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Usercontent>(list));
		return pageModel;
	}

	@Override
	public Usercontent get(String userid) {
		UsercontentQuery ucQuery=new UsercontentQuery();
		Criteria createCriteria=ucQuery.createCriteria();
		createCriteria.andUseridEqualTo(userid);
		List<Usercontent> usercontents= usercontentDao.selectByExample(ucQuery);
		return usercontents.get(0);
	}

	@Override
	public int insert(Usercontent usercontent) throws Exception {
		return usercontentDao.insertSelective(usercontent);		
	}

	@Override
	public int update(Usercontent usercontent) throws Exception {		
		return usercontentDao.updateByPrimaryKeySelective(usercontent);
	}
	
}
