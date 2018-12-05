package com.jp.dao;

import com.jp.entity.User;
import com.jp.entity.Userinfo;
import com.jp.entity.UserinfoQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserinfoDao {
    int countByExample(UserinfoQuery example);

    int deleteByExample(UserinfoQuery example);

    int deleteByPrimaryKey(String userid);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    List<Userinfo> selectByExampleWithBLOBs(UserinfoQuery example);

    List<Userinfo> selectByExample(UserinfoQuery example);

    Userinfo selectByPrimaryKey(String userid);

    int updateByExampleSelective(@Param("record") Userinfo record, @Param("example") UserinfoQuery example);

    int updateByExampleWithBLOBs(@Param("record") Userinfo record, @Param("example") UserinfoQuery example);

    int updateByExample(@Param("record") Userinfo record, @Param("example") UserinfoQuery example);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKeyWithBLOBs(Userinfo record);

    int updateByPrimaryKey(Userinfo record);
    
    List<User> selecUserList(@Param("user")User user,@Param("list")List<String> branchList);
    
    int importUser(@Param("list")List<Userinfo> userinfoList);

	List<User> selecUserListByBranch(@Param("user")User userAleardy,@Param("branchname") String branchname);
}