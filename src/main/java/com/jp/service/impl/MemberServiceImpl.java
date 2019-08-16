package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.dao.JpMemberMapper;
import com.jp.dao.JpMemberRecordMapper;
import com.jp.dao.UserDao;
import com.jp.entity.JpMember;
import com.jp.entity.JpMemberRecord;
import com.jp.entity.User;
import com.jp.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private JpMemberMapper memberMapper;
	
	@Autowired
	private JpMemberRecordMapper memberRecordMapper;
	
	@Autowired
	private UserDao userMapepr;
	/**
	 * 获取会员级别
	 */
	@Override
	public User get(String userid) {
		// TODO Auto-generated method stub
		return userMapepr.selectByPrimaryKey1(userid);
	}
	/**
	 * 添加充值记录
	 */
	@Override
	public Integer insert(JpMemberRecord record) {
		// TODO Auto-generated method stub
		return memberRecordMapper.insertSelective(record);
	}
	/**
	 * 查询充值记录
	 */
	@Override
	public JpMemberRecord selectRecord(String userid) {
		// TODO Auto-generated method stub
		return memberRecordMapper.selectByPrimaryKey(userid);
	}
	/**
	 * 开通会员
	 */
	@Override
	public void insert(JpMember momber) {
		// TODO Auto-generated method stub
		memberMapper.insertSelective(momber);
	}
	@Override
	public JpMember selectMember(String userid) {
		// TODO Auto-generated method stub
		return memberMapper.selectByPrimaryKey(userid);
	}
	@Override
	public void update(JpMember momber) {
		// TODO Auto-generated method stub
		memberMapper.updateByPrimaryKeySelective(momber);
	}
	@Override
	public Integer updateterecord(String userid) {
		// TODO Auto-generated method stub
		return memberRecordMapper.updateStatus(userid);
	}
	

}
