package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Platform;

public interface PlatformDao {
    List<Platform> selecPlatformList(@Param("platform")Platform platform);
    void deleteVersion(@Param("id")Integer id);
    void save(@Param("platform")Platform platform);
    void update(@Param("platform")Platform platform);
    Platform selectone(@Param("id")String id);
    void isOpen(@Param("id")Integer id,@Param("isUsed")Integer isUsed);
    void closeAllVersion(@Param("fileType") String fileType);
}