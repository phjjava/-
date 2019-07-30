package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jp.dao.SysMationdao;
import com.jp.entity.SysMation;
import com.jp.service.SysMationService;

@Service
public class SysMationServiceImpl implements SysMationService{
	@Autowired
    private SysMationdao badao;
	@Override
	public List<SysMation> selectByGoType(String goType) {
		// TODO Auto-generated method stub
		return badao.selectByExample();
	}
	@Override
	public Integer update(SysMation mation) {
		// TODO Auto-generated method stub
		return badao.updateByPrimaryKeySelective(mation);
	}
	@Override
	public Integer insert(SysMation mation) {
		// TODO Auto-generated method stub
		return badao.insertSelective(mation);
	}
	@Override
	public int batchDeleteAll(String[] mationids) throws Exception {
		// TODO Auto-generated method stub
		return badao.mationDeleteAll(mationids);
	}

}
