package com.jp.dao;

import com.jp.entity.User;
import com.jp.entity.UserImport;
import com.jp.entity.UserImportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserImportMapper {
    int countByExample(UserImportExample example);

    int deleteByExample(UserImportExample example);

    int deleteByPrimaryKey(String userid);

    int insert(UserImport record);

    int insertSelective(UserImport record);

    List<User> selectByExample(UserImportExample example);

    User selectByPrimaryKey(String userid);

    int updateByExampleSelective(@Param("record") UserImport record, @Param("example") UserImportExample example);

    int updateByExample(@Param("record") UserImport record, @Param("example") UserImportExample example);

    int updateByPrimaryKeySelective(UserImport record);

    int updateByPrimaryKey(UserImport record);

	void importUser(List<User> userList);

	void updateAleardyUser(List<User> userAleardyListUpdate);
}