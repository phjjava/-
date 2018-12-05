package com.jp.service.impl;


import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.dao.WorshipOblationMapper;
import com.jp.dao.WorshipOblationTypeMapper;
import com.jp.entity.SysUser;
import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationType;
import com.jp.service.OblationService;




@Service("oblationServiceImpl")
public class OblationServiceImpl implements OblationService {

	
	@Resource
	private WorshipOblationMapper oblationMapper;
	@Resource
	private WorshipOblationTypeMapper oblationTypeMapper;
	@Override
	public Integer insert(WorshipOblation oblation) {
		SysUser user = CurrentSystemUserContext.getSystemUserContext();
		WorshipOblationType wot = oblationTypeMapper.selectByPrimaryKey(oblation.getOblationtypeid());
		if(wot!=null)
			oblation.setOblationtype(wot.getTypename());
		oblation.setCreateid(user.getUserid());
		oblation.setCreatename(user.getName());
		oblation.setCreatetime(new Date());
		return oblationMapper.insertSelective(oblation);
	}

	@Override
	public Integer update(WorshipOblation oblation) {
		SysUser user = CurrentSystemUserContext.getSystemUserContext();
		WorshipOblationType wot = oblationTypeMapper.selectByPrimaryKey(oblation.getOblationtypeid());
		if(wot!=null)
			oblation.setOblationtype(wot.getTypename());
		oblation.setCreateid(user.getUserid());
		oblation.setCreatename(user.getName());
		oblation.setCreatetime(new Date());
		return oblationMapper.updateByPrimaryKeySelective(oblation);
	}

	@Override
	public int del(WorshipOblation oblation) {
		oblation.setDeleteflag(ConstantUtils.DELETE_TRUE);
		return oblationMapper.updateByPrimaryKeySelective(oblation);
	}

	@Override
	public WorshipOblation getOblationById(String id) {
		
		return oblationMapper.selectByPrimaryKey(id);
	}
	
	
}
