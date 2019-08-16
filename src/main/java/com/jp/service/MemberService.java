package com.jp.service;

import java.util.List;

import com.jp.entity.JpMember;
import com.jp.entity.JpMemberRecord;
import com.jp.entity.User;

public interface MemberService {
	/**
	 * 获取会员级别
	 * @param userid
	 * @return
	 */
	User get(String userid);
	/**
	 * 添加充值记录
	 * @param momberrecord
	 * @return
	 */
	Integer insert(JpMemberRecord record);
	/**
	 * 查询充值记录
	 * @param userid
	 * @return
	 */
	JpMemberRecord selectRecord(String userid);
	/**
	 * 开通会员
	 * @param momber
	 */
	void insert(JpMember momber);
	/**
	 * 有此会员记录则修改
	 * @param momber
	 */
	void update(JpMember momber);
	/**
	 * 查询是否有当前用户的会员表记录
	 * @param userid
	 * @return
	 */
	JpMember selectMember(String userid);
	/**
	 * 更改此用户之前的充值记录状态值
	 * @param userid
	 * @return
	 */
	Integer updateterecord(String userid);

}
