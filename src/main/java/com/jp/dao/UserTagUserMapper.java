package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.UserTagUser;

/**
 * <p>
 * 族圈用户标签与用户关系表 Mapper 接口
 * </p>
 *
 */
public interface UserTagUserMapper {

	int insert(UserTagUser tagUser);

	List<UserTagUser> selectList(@Param("tagId") String tagId);

	List<UserTagUser> selectListByTagIds(@Param("tagIds") String[] tagIds);
}
