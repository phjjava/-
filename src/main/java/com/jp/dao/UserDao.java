package com.jp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jp.entity.EditorialBoard;
import com.jp.entity.GenUserOtherVO;
import com.jp.entity.SearchComplex;
import com.jp.entity.User;
import com.jp.entity.UserQuery;
import com.jp.entity.Usercontent;

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

	int updateByPrimaryKeySelectivePhone(User record);

	int updateByPrimaryKey(User record);

	List<User> selectUserItem(User user);

	int changeStatus(User record);

	int importUser(@Param("list") List<User> userList);

	List<User> selectPnameAndMate(@Param("familyid") String familyId, @Param("list") List<String> branchList);

	int updateAleardyUser(@Param("list") List<User> userList);

	List<User> selecUserListToReview(User user);

	List<Usercontent> selectUserContentList(Usercontent usercontent);

	List<User> login(@Param("phone") String phone, @Param("password") String password);

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
	List<User> validatePhone(@Param("familyid") String familyid, @Param("userid") String userid,
			@Param("phone") String phone);

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
	int addmateid(@Param("userid") String userid, @Param("mateid") String mateid, @Param("matename") String matename);

	List<String> selectPhoneByFamilyId(@Param("familyId") String familyId);

	List<User> selectUserByFamilyId(@Param("familyId") String familyId);

	List<User> selectUserItemLive(User user);

	Integer delUser(@Param("array") String userid[]);

	String getAddressByUserid(@Param("userid") String userid);

	List<User> selectBranchUsers(Map<String, String> map);

	List<User> selectByMapOrder(Map map);

	List<User> selectByMap(Map map);

	List<User> selectByPhone(@Param("phone") String phone);

	List<User> searchUser(Map<String, Object> map);

	List<EditorialBoard> selectManagerBranchidsByUserid(Map<String, String> map);

	List<User> selectUserByBranchids(@Param("array") String[] strs);

	List<User> selectUserByTag(@Param("array") String[] strs);

	/**
	 * 获取用户的其他配偶列表
	 * 
	 * @param userid
	 * @param mateid
	 * @return
	 */
	List<User> selectMateList(@Param("userid") String userid, @Param("mateid") String mateid);

	List<User> selectByOpenId(User entity);

	List<User> selectByFamilyId(Map<String, String> userparams);

	String getLastUpdateDateTime(String familyid);

	List<User> searchComplex(SearchComplex searchComplex);

	List<String> getUseridListOfAfterUpdatedtime(User entity);

	List<String> getUseridListByFamilyid(String familyid);

	List<User> getUserListByUserids(Map<String, String> map);

	List<User> selectByTitle(Map<String, String> map);

	List<User> selectBranchByTitle(Map<String, String> map);

	List<User> selectFamilycode(@Param("phone") String phone, @Param("status") Integer status);

	@Select("select*from jp_user where status in (0,1)  and deleteflag = 0 and familyid is not null and phone = #{phone}")
	List<User> selectByPhoneInStatus(@Param("phone") String phone);

	@Select("select*from jp_user where status = 0  and deleteflag = 0 and familyid is not null and phone = #{phone}")
	List<User> selectByPhoneAndStatus(@Param("phone") String phone);

	@Select("select*from jp_user where status =2 and deleteflag = 0 and familyid is not null and phone = #{phone} and familyid = #{familyid}")
	List<User> selectByPhoneToStatus(@Param("phone") String phone, @Param("familyid") String familyid);

	int countBranch(Map<String, String> totalparams);

	List<User> selectUserByAreaCode(Map<String, String> params);

	int searchComplexCount(SearchComplex searchComplex);

	List<User> selectByUserids(@Param("array") String[] strs, @Param("familyid") String familyid);

	List<User> selectUserByNoBranchids(@Param("array") String[] strs, @Param("familyid") String familyid);

	List<User> selectUserByNoTag(@Param("array") String[] strs, @Param("familyid") String familyid);

	List<User> selectByNoUserids(@Param("array") List<String> strs, @Param("familyid") String familyid);

	//根据用户家族id查询该家族的分支数
	@Select("select max(genlevel) as genlevel from jp_user where familyid =#{familyid}")
	int getUserFamilyid(@Param("familyid") String familyid);

	/**
	* 根据家族id 家族名 世系代数 查找用户
	* @param familyid
	* @param familyname
	* @param genlevel
	* @return
	*/
	List<GenUserOtherVO> getUserByAncestor(@Param("familyid") String familyid, @Param("familyname") String familyname,
			@Param("genlevel") Integer genlevel);

	int getUserByAncestorCount(@Param("familyid") String familyid, @Param("genlevel") Integer genlevel);

	/**
	 * 得到某用户的会员级别，member是null或者1为默认的普通会员
	 * @param userid
	 * @return
	 */
	User selectByPrimaryKey1(String userid);

}