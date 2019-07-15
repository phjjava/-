package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Platform;

public interface PlatformDao {
	List<Platform> selecPlatformList(@Param("platform") Platform platform);

	int deleteVersion(@Param("id") Integer id);

	int save(@Param("platform") Platform platform);

	int update(@Param("platform") Platform platform);

	Platform selectone(@Param("id") Integer id);

	int isOpen(@Param("id") Integer id, @Param("isUsed") Integer isUsed);

	int closeAllVersion(@Param("fileType") Integer fileType);

	Platform selectPlatform(Integer fileType);
}