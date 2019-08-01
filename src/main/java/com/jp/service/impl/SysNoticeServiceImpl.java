package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.dao.SysMtionTypeDao;
import com.jp.dao.SysNoticeDao;
import com.jp.dao.SysNoticeTypeDao;
import com.jp.entity.SysMation;
import com.jp.entity.SysNotice;
import com.jp.entity.SysNoticeType;
import com.jp.service.SysNoticeService;

@Service
public class SysNoticeServiceImpl implements SysNoticeService{
	@Autowired
	private SysNoticeDao noticedao;
	
	@Autowired
	private SysNoticeTypeDao noticetypedao;
	@Override
	//资讯列表
	public List<SysNotice> selectNotice() {
		
		// TODO Auto-generated method stub
		return noticedao.selectByExample();
	}
	//类型下拉框
	@Override
	public List<SysNoticeType> selecttypelist() {
		// TODO Auto-generated method stub
		return noticetypedao.selectByExample();
	}
	//修改
	@Override
	public Integer update(SysNotice notice) {
		// TODO Auto-generated method stub
		return noticedao.updateByPrimaryKeySelective(notice);
	}
	//新增
	@Override
	public Integer insert(SysNotice notice) {
		// TODO Auto-generated method stub
		return noticedao.insertSelective(notice);
	}

}
