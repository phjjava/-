package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jp.entity.Post;
import com.jp.entity.PostExample;

public interface PostMapper {
	int countByExample(PostExample example);

	int deleteByExample(PostExample example);

	int deleteByPrimaryKey(String id);

	int insert(Post record);

	int insertSelective(Post record);

	List<Post> selectByExample(PostExample example);

	Post selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Post record, @Param("example") PostExample example);

	int updateByExample(@Param("record") Post record, @Param("example") PostExample example);

	int updateByPrimaryKeySelective(Post record);

	int updateByPrimaryKey(Post record);

	@Select("select * from jp_post")
	List<Post> selectAllPost();
}