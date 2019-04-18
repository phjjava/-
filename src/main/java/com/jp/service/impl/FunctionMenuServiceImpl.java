package com.jp.service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.IndexMapper;
import com.jp.dao.UserDao;
import com.jp.entity.Index;
import com.jp.entity.IndexExample;
import com.jp.entity.SysUser;
import com.jp.entity.User;
import com.jp.service.FunctionMenuService;
import com.jp.util.UUIDUtils;

@Service
public class FunctionMenuServiceImpl implements FunctionMenuService {

	@Resource
	private IndexMapper indexMapper;
	@Resource
	private UserDao userMapper;
	
	@Override
	public PageModel<Index> pageQuery(PageModel<Index> pageModel) {
		IndexExample example = new IndexExample();
		example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		example.setOrderByClause("sort asc");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Index> list = indexMapper.selectByExample(example);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Index>(list));
		return pageModel;
		
	}

	@Override
	public String save(Index index) {
		index.setId(UUIDUtils.getUUID());
		SysUser user = CurrentSystemUserContext.getSystemUserContext();
		//User user = userMapper.selectByPrimaryKey(userid);
		Date date = new Date();
		index.setCreateid(user.getUserid());
		index.setCreatename(user.getName());
		index.setCreatetime(date);
		index.setDeleteflag(ConstantUtils.DELETE_FALSE);
		Integer result = indexMapper.insertSelective(index);
		return result+"";
	}

	@Override
	public String update(Index index) {
		SysUser user = CurrentSystemUserContext.getSystemUserContext();
		Date date = new Date();
		index.setUpdateid(user.getUserid());
		index.setUpdatename(user.getName());
		index.setUpdatetime(date);
		Integer result = indexMapper.updateByPrimaryKeySelective(index);
		return result+"";
	}

	@Override
	public Index get(String id) {
		
		return indexMapper.selectByPrimaryKey(id);
	}

	@Override
	public String batchDelete(String[] menuArray) {
		
		return indexMapper.batchDelete(menuArray);
	}

	@Override
	public String delete(String menuid) {
		Index index = new Index();
		index.setId(menuid);
		index.setDeleteflag(ConstantUtils.DELETE_TRUE);
		return indexMapper.updateByPrimaryKeySelective(index)+"";
	}

	
}
