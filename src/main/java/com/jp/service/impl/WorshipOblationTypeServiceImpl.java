package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.PageModel;
import com.jp.dao.UserDao;
import com.jp.dao.WorshipAnnexMapper;
import com.jp.dao.WorshipMapper;
import com.jp.dao.WorshipOblationMapper;
import com.jp.dao.WorshipOblationTypeMapper;
import com.jp.entity.SysUser;
import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationExample;
import com.jp.entity.WorshipOblationType;
import com.jp.entity.WorshipOblationTypeExample;
import com.jp.service.WorshipOblationTypeService;

@Service
public class WorshipOblationTypeServiceImpl implements WorshipOblationTypeService {

	@Resource
	private WorshipMapper worshipMapper;
	@Resource
	private WorshipAnnexMapper worshipAnnexMapper;
	@Resource
	private WorshipOblationMapper oblationMapper;
	@Resource
	private WorshipOblationTypeMapper oblationTypeMapper;
	@Resource
	private WorshipAnnexMapper annexMapper;
	@Resource
	private UserDao userMapper;

	@Override
	public PageModel<WorshipOblationType> pageQuery(PageModel<WorshipOblationType> pageModel) {
		WorshipOblationTypeExample example = new WorshipOblationTypeExample();
		example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		example.setOrderByClause("sort asc");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<WorshipOblationType> list = oblationTypeMapper.selectByExample(example);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<WorshipOblationType>(list));
		return pageModel;
	}

	@Override
	public List<WorshipOblation> selectListByParnetid(String parentid) {
		WorshipOblationExample example = new WorshipOblationExample();
		example.or().andOblationtypeidEqualTo(parentid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		example.setOrderByClause(" sort asc");
		List<WorshipOblation> list = oblationMapper.selectByExample(example);
		return list;
	}

	@Override
	public Integer update(WorshipOblationType oblationType) {
		SysUser user = CurrentSystemUserContext.getSystemUserContext();
		oblationType.setCraeteid(user.getUserid());
		oblationType.setCraetename(user.getName());
		oblationType.setCreatetime(new Date());
		return oblationTypeMapper.updateByPrimaryKey(oblationType);
	}

	@Override
	public Integer insert(WorshipOblationType oblationType) {
		SysUser user = CurrentSystemUserContext.getSystemUserContext();
		oblationType.setCraeteid(user.getUserid());
		oblationType.setCraetename(user.getName());
		oblationType.setCreatetime(new Date());
		return oblationTypeMapper.insert(oblationType);
	}

	@Override
	public int del(WorshipOblationType oblationType) {
		oblationType.setDeleteflag(ConstantUtils.DELETE_TRUE);
		WorshipOblationExample ex = new WorshipOblationExample();
		ex.or().andOblationtypeidEqualTo(oblationType.getId());
		WorshipOblation ob = new WorshipOblation();
		ob.setDeleteflag(ConstantUtils.DELETE_TRUE);
		oblationMapper.updateByExampleSelective(ob, ex);
		return oblationTypeMapper.updateByPrimaryKeySelective(oblationType);
	}

	@Override
	public WorshipOblationType getOblationTypeById(String id) {

		return oblationTypeMapper.selectByPrimaryKey(id);
	}

}
