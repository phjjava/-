package com.jp.dao;

import java.util.List;

import com.jp.entity.UserTag;

/**
 * <p>
 * 族圈用户标签表 Mapper 接口
 * </p>
 *
 */
public interface UserTagMapper {

	/**
	 * 根据标签id更新标签用户关系表的删除标识
	 * 
	 * @param entity
	 * @return
	 */
	int deleteByTagId(UserTag entity);

	/**
	 * 根据标签id更新用户标签表的删除标识
	 * 
	 * @param entity
	 * @return
	 */
	int deleteById(UserTag entity);

	/**
	 * 根据标签di获取标签详情
	 * 
	 * @param id
	 * @return
	 */
	UserTag selectByPrimaryKey(String id);

	/**
	 * 根据当前登录用户id获取该用户下的标签列表
	 * 
	 * @param userid
	 * @return
	 */
	List<UserTag> selectListByUserid(String userid);

	int updateById(UserTag entity);

	int insert(UserTag entity);
}
