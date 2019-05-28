package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jp.entity.Userphoto;
import com.jp.entity.UserphotoKey;
import com.jp.entity.UserphotoQuery;

public interface UserphotoDao {
	int countByExample(UserphotoQuery example);

	int deleteByExample(UserphotoQuery example);

	int deleteByPrimaryKey(UserphotoKey key);

	int insert(Userphoto record);

	int insertSelective(Userphoto record);

	List<Userphoto> selectByExample(UserphotoQuery example);

	Userphoto selectByPrimaryKey(UserphotoKey key);

	int updateByExampleSelective(@Param("record") Userphoto record, @Param("example") UserphotoQuery example);

	int updateByExample(@Param("record") Userphoto record, @Param("example") UserphotoQuery example);

	int updateByPrimaryKeySelective(Userphoto record);

	int updateByPrimaryKey(Userphoto record);

	int insertUserPhoto(List<Userphoto> record);

	/**
	 * 
	 * @描述 查询相册详情根据相册id
	 * @作者 sj
	 * @时间 2017年5月18日上午9:49:09
	 * @参数 @param albumId
	 * @参数 @return
	 * @return List<Userphoto>
	 */
	List<Userphoto> selectByAlbumId(String albumId);

	/**
	 * 
	 * @描述 删除相册下的照片
	 * @作者 sj
	 * @时间 2017年5月18日上午11:38:43
	 * @参数 @param example
	 * @参数 @return
	 * @return int
	 */
	int deleteUserPhoto(String albumid);

	@Select("select * from jp_userphoto where albumid = #{albumid} and deleteflag = 0 order by sort asc")
	List<Userphoto> selectByAlbumidAndDeleteflag(@Param("albumid") String albumid);
}