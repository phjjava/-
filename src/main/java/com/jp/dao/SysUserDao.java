package com.jp.dao;

import com.jp.entity.SysUser;
import com.jp.entity.SysUserQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserDao {
    int countByExample(SysUserQuery example);

    int deleteByExample(SysUserQuery example);

    int deleteByPrimaryKey(String userid);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> selectByExample(SysUserQuery example);

    SysUser selectByPrimaryKey(String userid);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserQuery example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserQuery example);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    SysUser selectByLoginNameAndPassword(@Param("loginname")String loginName,@Param("password")String password);
}