package com.jp.service;

import java.util.List;

import com.jp.entity.Useralbum;
import com.jp.entity.UseralbumKey;
import com.jp.entity.UseralbumQuery;
import com.jp.entity.Userphoto;
import com.jp.entity.UserphotoKey;

public interface UseralbumService {
	
	
	/**
	 * @描述 查询单个相册
	 * @作者 sj
	 * @时间 2017年5月1日下午2:28:11
	 * @参数 @param userid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return User
	 */
	Useralbum selectByPrimaryKey(UseralbumKey key) throws Exception;
	/**
	 * 
	 * @描述 查询相册详情
	 * @作者 sj
	 * @时间 2017年5月18日上午9:48:10
	 * @参数 @param albumId
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<Userphoto>
	 */
	List<Userphoto> selectByAlbumId(String albumId) throws Exception;
	/**
	 * 
	 * @描述 删除相册
	 * @作者 sj
	 * @时间 2017年5月18日上午11:05:01
	 * @参数 @param key
	 * @参数 @return
	 * @return int
	 */
	int deleteByPrimaryKey(UseralbumKey key);
	/**
	 * 
	 * @描述 删除
	 * @作者 sj
	 * @时间 2017年5月18日上午11:30:23
	 * @参数 @param key
	 * @参数 @return
	 * @return int
	 */
	int deleteUserPhoto(String albumid);
	/**
	 * 
	 * @描述 删除单张照片
	 * @作者 sj
	 * @时间 2017年5月18日下午3:03:33
	 * @参数 @param albumid
	 * @参数 @return
	 * @return int
	 */
	int deleteUserPhotoSingle(UserphotoKey key);
	/**
	 * @描述 查询单张图片
	 * @作者 sj
	 * @时间 2017年5月21日下午4:12:25
	 * @参数 @param key
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return Userphoto
	 */
	Userphoto selectByImgId(UserphotoKey key) throws Exception;
	/**
	 * @描述 更新单张照片
	 * @作者 sj
	 * @时间 2017年5月21日下午4:20:41
	 * @参数 @param key
	 * @参数 @return
	 * @return int
	 */
	int updateByPrimaryKeySelective(Userphoto userPhoto);
}