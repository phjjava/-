package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.Role;
import com.jp.entity.RoleQuery;

public interface RoleDao {
    int countByExample(RoleQuery example);

    int deleteByExample(RoleQuery example);

    int deleteByPrimaryKey(String roleid);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleQuery example);

    Role selectByPrimaryKey(String roleid);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleQuery example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleQuery example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    Role selectRoleByUserid(@Param("familyid") String familyid, @Param("userid") String userid );

    List<Role> selectByFamilyid(String familyid);
}