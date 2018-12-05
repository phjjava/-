package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.User;
import com.jp.entity.Userbranch;
import com.jp.entity.Userrole;
import com.jp.entity.UserroleQuery;

public interface UserroleDao {
    int countByExample(UserroleQuery example);

    int deleteByExample(UserroleQuery example);

    int deleteByPrimaryKey(String userid);

    int insert(Userrole record);

    int insertSelective(Userrole record);

    List<Userrole> selectByExample(UserroleQuery example);

    Userrole selectByPrimaryKey(String userid);

    int updateByExampleSelective(@Param("record") Userrole record, @Param("example") UserroleQuery example);

    int updateByExample(@Param("record") Userrole record, @Param("example") UserroleQuery example);

    int updateByPrimaryKeySelective(Userrole record);

    int updateByPrimaryKey(Userrole record);
    
    Userrole selectByFamilyId(String familyId);
    
    List<User> selectUserRoleByFamilyId(@Param("familyid")String familyid,@Param("username")String username);
    
    List<Userbranch> userBranchList(String userid);
    
}