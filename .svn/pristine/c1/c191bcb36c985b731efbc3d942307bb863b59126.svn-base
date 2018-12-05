package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.dao.UseralbumDao;
import com.jp.dao.UserphotoDao;
import com.jp.entity.Useralbum;
import com.jp.entity.UseralbumKey;
import com.jp.entity.Userphoto;
import com.jp.entity.UserphotoKey;
import com.jp.service.UseralbumService;

@Service
public class UseralbumServiceImpl implements UseralbumService {
	@Autowired
	private UseralbumDao useralbumDao;
	@Autowired
	private UserphotoDao userphotoDao;
	@Override
	public Useralbum selectByPrimaryKey(UseralbumKey key) throws Exception {
		return useralbumDao.selectByPrimaryKey(key);
	}
	@Override
	public List<Userphoto> selectByAlbumId(String albumId) throws Exception {
		return userphotoDao.selectByAlbumId(albumId);
	}
	@Override
	public int deleteByPrimaryKey(UseralbumKey key) {
		return useralbumDao.deleteByPrimaryKey(key);
	}
	@Override
	public int deleteUserPhoto(String albumid) {
		return userphotoDao.deleteUserPhoto(albumid);
	}
	@Override
	public int deleteUserPhotoSingle(UserphotoKey key) {
		return userphotoDao.deleteByPrimaryKey(key);
	}
	@Override
	public Userphoto selectByImgId(UserphotoKey key) throws Exception {
		return userphotoDao.selectByPrimaryKey(key);
	}
	@Override
	public int updateByPrimaryKeySelective(Userphoto userPhoto) {
		return userphotoDao.updateByPrimaryKeySelective(userPhoto);
	}
}