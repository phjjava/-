package com.jp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;

public interface UserManagerMapper {
	int countByExample(UserManagerExample example);

	int deleteByExample(UserManagerExample example);

	int deleteByPrimaryKey(String id);

	int insert(UserManager record);

	int insertSelective(UserManager record);

	List<UserManager> selectByExample(UserManagerExample example);

	List<UserManager> selectManagerByUserid(@Param("userid") String userid, @Param("ebid") String ebid);

	UserManager selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") UserManager record, @Param("example") UserManagerExample example);

	int updateByExample(@Param("record") UserManager record, @Param("example") UserManagerExample example);

	int updateByPrimaryKeySelective(UserManager record);

	int updateByPrimaryKey(UserManager record);

	List<UserManager> selectMnangers(String userid);

	List<UserManager> selectByEbid(String id);

	List<UserManager> selectByParams(Map<String, Object> params);

	@Select("SELECT * FROM `jp_user_manager` WHERE id = #{id}")
	UserManager selectByManagerId(@Param("id") String id);

	//根据得到的编委会查询编委会成员
	List<UserManager> selectbyEditor(String id);
	//根据得到的家族id查询总编委会成员
	List<UserManager> selectbyEditorOver(String family);
	//根据得到的家族id查询总编委会主任
	List<UserManager> selectbyEditorOverOne(String family);
	//查询所在编委名称
	List<UserManager> selectbyEditorEbname(String id);
	//查询对应总编委会名称
	String selectEbname(@Param("userid")String userid, @Param("familyId")String familyId);
}