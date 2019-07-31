package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jp.dao.SysMationdao;
import com.jp.dao.SysMtionTypeDao;
import com.jp.entity.MationType;
import com.jp.entity.SysMation;
import com.jp.service.SysMationService;

@Service
public class SysMationServiceImpl implements SysMationService{
	@Autowired
    private SysMationdao badao;
	@Autowired
	private SysMtionTypeDao typedao;
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
	@Override
	public List<MationType> selecttypelist() {
		// TODO Auto-generated method stub
		return typedao.selecttypelist();
	}
	@Override
	public Integer updatetype(MationType mationtype) {
		// TODO Auto-generated method stub
		return typedao.updateByPrimaryKeySelective(mationtype);
	}
	@Override
	public Integer inserttype(MationType mationtype) {
		// TODO Auto-generated method stub
		return typedao.insertSelective(mationtype);
	}
	@Override
	public int mationtypeDeleteAll(String[] mationtypeids) throws Exception {
		// TODO Auto-generated method stub
		return typedao.mationtypeDeleteAll(mationtypeids);
	}

}
