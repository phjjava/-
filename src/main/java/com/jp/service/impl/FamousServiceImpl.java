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
import com.jp.service.FamousService;

@Service
public class FamousServiceImpl implements FamousService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UsercontentDao usercontentDao;

	@Override
	public PageModel<Usercontent> selectContentList(PageModel<Usercontent> pageModel, Usercontent usercontent) {

		usercontent.setFamilyid(CurrentUserContext.getCurrentFamilyId());
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Usercontent> list = userDao.selectUserContentList(usercontent);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Usercontent>(list));
		return pageModel;
	}

	@Override
	public Usercontent get(String userid) {
		Usercontent usercontent = usercontentDao.selectByPrimaryKey(userid);
		if (usercontent != null) {
			Integer readcount = usercontent.getReadcount();
			if (readcount == null || "".equals(readcount.toString())) {
				usercontent.setReadcount(1);
			} else {
				usercontent.setReadcount(readcount + 1);
			}
			usercontentDao.updateByPrimaryKey(usercontent);
		}
		return usercontent;
	}

	@Override
	public int insert(Usercontent usercontent) throws Exception {
		return usercontentDao.insertSelective(usercontent);
	}

	@Override
	public int update(Usercontent usercontent) throws Exception {
		return usercontentDao.updateByPrimaryKeySelective(usercontent);
	}

	@Override
	public int batchDelete(String[] idArray) {
		int num = usercontentDao.batchDelete(idArray);
		if (num >= 1)
			return 1;
		return 0;

	}

}
