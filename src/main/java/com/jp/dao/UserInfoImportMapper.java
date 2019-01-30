package com.jp.dao;

import com.jp.entity.UserInfoImport;
import com.jp.entity.UserInfoImportExample;
import com.jp.entity.Userinfo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfoImportMapper {
    int countByExample(UserInfoImportExample example);

    int deleteByExample(UserInfoImportExample example);

    int deleteByPrimaryKey(String userid);

    int insert(UserInfoImport record);

    int insertSelective(UserInfoImport record);

    List<UserInfoImport> selectByExampleWithBLOBs(UserInfoImportExample example);

    List<UserInfoImport> selectByExample(UserInfoImportExample example);

    Userinfo selectByPrimaryKey(String userid);

    int updateByExampleSelective(@Param("record") UserInfoImport record, @Param("example") UserInfoImportExample example);

    int updateByExampleWithBLOBs(@Param("record") UserInfoImport record, @Param("example") UserInfoImportExample example);

    int updateByExample(@Param("record") UserInfoImport record, @Param("example") UserInfoImportExample example);

    int updateByPrimaryKeySelective(UserInfoImport record);

    int updateByPrimaryKeyWithBLOBs(UserInfoImport record);

    int updateByPrimaryKey(UserInfoImport record);

	void importUser(List<Userinfo> userInfoList);
}