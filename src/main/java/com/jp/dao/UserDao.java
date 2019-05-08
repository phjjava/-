package com.jp.dao;

import com.jp.entity.User;
import com.jp.entity.UserQuery;
import com.jp.entity.Usercontent;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
	int countByExample(UserQuery example);

	int deleteByExample(UserQuery example);

	int deleteByPrimaryKey(String userid);

	int insert(User record);

	int insertSelective(User record);

	List<User> selectByExample(UserQuery example);

	User selectByPrimaryKey(String userid);

	int updateByExampleSelective(@Param("record") User record, @Param("example") UserQuery example);

	int updateByExample(@Param("record") User record, @Param("example") UserQuery example);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	List<User> selectUserItem(User user);

	int changeStatus(User record);

	int importUser(@Param("list") List<User> userList);

	List<User> selectPnameAndMate(@Param("familyid")String familyId,@Param("list")List<String>branchList);

	int updateAleardyUser(@Param("list") List<User> userList);

	List<User> selecUserListToReview(User user);

	List<Usercontent> selectUserContentList(Usercontent usercontent);
	
	List<User> login(@Param("phone") String phone,@Param("password") String password);
	
	/**
	 * @描述 校验手机号是否存在多个家族
	 * @作者 wumin
	 * @时间 2017年5月17日上午11:37:59
	 * @参数 @param familyid
	 * @参数 @param userid
	 * @参数 @param phone
	 * @参数 @return
	 * @return User
	 */
	List<User> validatePhone(@Param("familyid") String familyid,@Param("userid") String userid,@Param("phone") String phone);
	/**
	 * 
	 * @描述 新增用户或编辑 验证手机号不重复
	 * @作者 sj
	 * @时间 2017年5月19日上午9:51:29
	 * @参数 @param user
	 * @参数 @return
	 * @return List<User>
	 */
	List<User> validatePhoneAddUser(User user);
	/**
	 * 
	 * @描述 更新 user mateid
	 * @作者 sj
	 * @时间 2017年5月21日下午7:58:36
	 * @参数 @param mateid
	 * @参数 @return
	 * @return int
	 */
	int addmateid(@Param("userid")String userid,@Param("mateid")String mateid,@Param("matename")String matename);

	List<String> selectPhoneByFamilyId(@Param("familyId") String familyId);
	
	List<User> selectUserByFamilyId(@Param("familyId") String familyId);

	List<User> selectUserItemLive(User user);

	Integer delUser(@Param("array") String userid[]);
	
	String getAddressByUserid(@Param("userid")String userid);
	
	
	
	
}