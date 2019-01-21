package com.jp.dao;

import com.jp.entity.User;
import com.jp.entity.Userinfo;
import com.jp.entity.UserinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserinfoMapper {
    int countByExample(UserinfoExample example);

    int deleteByExample(UserinfoExample example);

    int deleteByPrimaryKey(String userid);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    List<Userinfo> selectByExampleWithBLOBs(UserinfoExample example);

    List<Userinfo> selectByExample(UserinfoExample example);

    Userinfo selectByPrimaryKey(String userid);

    int updateByExampleSelective(@Param("record") Userinfo record, @Param("example") UserinfoExample example);

    int updateByExampleWithBLOBs(@Param("record") Userinfo record, @Param("example") UserinfoExample example);

    int updateByExample(@Param("record") Userinfo record, @Param("example") UserinfoExample example);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKeyWithBLOBs(Userinfo record);

    int updateByPrimaryKey(Userinfo record);
    
    List<User> selecUserList(@Param("user")User user,@Param("list")List<String> branchList);
    
    int importUser(@Param("list")List<Userinfo> userinfoList);

	List<User> selecUserListByBranch(@Param("user")User userAleardy,@Param("branchname") String branchname);
}