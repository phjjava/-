package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.UseralbumDao;
import com.jp.dao.UserphotoDao;
import com.jp.entity.PhotosVO;
import com.jp.entity.Useralbum;
import com.jp.entity.UseralbumKey;
import com.jp.entity.UseralbumQuery;
import com.jp.entity.Userphoto;
import com.jp.entity.UserphotoKey;
import com.jp.entity.UserphotoQuery;
import com.jp.service.UseralbumService;
import com.jp.util.UUIDUtils;

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

	/**
	 * api迁移方法分割线
	 * 
	 * @param entity
	 * @return
	 */

	@Override
	public JsonResponse getPersonAlbums(Useralbum entity, int start, int count) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数用户ID不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getType() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("请指定相册类型！");
			res = new JsonResponse(result);
			return res;
		}
		/*
				UseralbumQuery userAlbumExample = new UseralbumQuery();
				userAlbumExample.setStart(entity.getStart());
				userAlbumExample.setCount(entity.getCount()); 
				// 获取type为0（个人相册）的相册列表 
				if (entity.getType() == 0) {
					userAlbumExample.or().andUseridEqualTo(entity.getUserid()).andDeleteflagEqualTo(0).andTypeEqualTo((byte) 0);
				} // 获取type为0（个人作品）的相册列表 
				if (entity.getType() == 1)
					userAlbumExample.or().andUseridEqualTo(entity.getUserid()).andDeleteflagEqualTo(0).andTypeEqualTo((byte) 1);
				userAlbumExample.setOrderByClause("sort asc");
				List<Useralbum> userAlbums = useralbumDao.selectByExample(userAlbumExample);
		*/
		PageHelper.startPage(start, count);
		List<Useralbum> userAlbums = useralbumDao.selectByUseridAndType(entity.getUserid(), entity.getType());
		if (userAlbums.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			if (entity.getType() == 0)
				result.setMsg("当前用户不存在相册");
			if (entity.getType() == 1)
				result.setMsg("当前用户不存在作品");
			res = new JsonResponse(result);
			return res;
		}
		for (Useralbum userAlbum : userAlbums) {
			UserphotoQuery userPhotoExample = new UserphotoQuery();
			userPhotoExample.or().andUseridEqualTo(userAlbum.getUserid()).andAlbumidEqualTo(userAlbum.getAlbumid())
					.andDeleteflagEqualTo(0);
			userAlbum.setPhotoNumbers(userphotoDao.countByExample(userPhotoExample));
			userPhotoExample.setOrderByClause("updatetime desc");
			List<Userphoto> userPhotos = userphotoDao.selectByExample(userPhotoExample);
			if (userPhotos.size() > 0) {
				Userphoto userphoto = userPhotos.get(0);
				userAlbum.setImgurl(userphoto.getImgurl());
			}
		}
		// UserAlbumKey key = new UserAlbumKey();
		// key.setAlbumid(entity.getAlbumid());
		// key.setUserid(userAlbums.get(0).getUserid());
		// Useralbum album = useralbumDao.selectByPrimaryKey(key);
		// //构造返回
		// PhotosVO vo = new PhotosVO();
		// vo.setAlbumname(album.getAlbumname());
		// vo.setRemake(album.getRemark());
		// vo.setCreatetime(DateUtil.date2shortStr(album.getCreatetime()));
		// vo.setPhotos(userAlbums);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userAlbums);
		return res;
	}

	@Override
	public JsonResponse getPersonPhotosOfAlbum(Useralbum entity, int start, int count) {
		Result result = null;
		JsonResponse res = null;
		if ("".equals(entity.getAlbumid()) || entity.getAlbumid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前相册不存在！");
			res = new JsonResponse(result);
			return res;
		}
		/*
		 * UserphotoQuery userPhotoExample = new UserphotoQuery();
		 * userPhotoExample.setCount(entity.getCount());
		 * userPhotoExample.setStart(entity.getStart());
		 * userPhotoExample.or().andAlbumidEqualTo(entity.getAlbumid()).
		 * andDeleteflagEqualTo(0); userPhotoExample.setOrderByClause("sort asc");
		 * List<Userphoto> userPhotos = userphotoDao.selectByExample(userPhotoExample);
		 */
		PageHelper.startPage(start, count);
		List<Userphoto> userPhotos = userphotoDao.selectByAlbumidAndDeleteflag(entity.getAlbumid());
		UseralbumQuery e = new UseralbumQuery();
		e.or().andAlbumidEqualTo(entity.getAlbumid());
		Useralbum album = useralbumDao.selectByExample(e).get(0);
		// 构造返回
		PhotosVO vo = new PhotosVO();
		vo.setAlbumname(album.getAlbumname());
		vo.setRemake(album.getRemark());
		vo.setCreatetime(album.getCreatetime());

		if (userPhotos.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不存在图片");
			res = new JsonResponse(result);
			res.setData(vo);
			return res;
		}

		vo.setPhotos(userPhotos);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(vo);
		return res;
	}

	@Override
	public JsonResponse createNewAlbum(Useralbum userAlbum) {
		Result result = null;
		JsonResponse res = null;
		Integer sort = 0;
		if ("".equals(userAlbum.getUserid()) || userAlbum.getUserid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数用户ID不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(userAlbum.getAlbumname()) || userAlbum.getAlbumname() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数相册名称不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (userAlbum.getSort() != null) {
			sort = userAlbum.getSort();
		}
		userAlbum.setAlbumid(UUIDUtils.getUUID());
		userAlbum.setSort(sort);
		// 个人只能创建个人相册
		userAlbum.setType(0);
		userAlbum.setCreatetime(new Date());
		userAlbum.setUpdatetime(new Date());
		userAlbum.setDeleteflag(0);
		userAlbum.setCreateid(userAlbum.getUserid());
		userAlbum.setUpdateid(userAlbum.getUserid());
		useralbumDao.insert(userAlbum);

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse createPhoto(Userphoto userPhoto) {
		Result result = null;
		JsonResponse res = null;
		Integer sort = 0;
		if ("".equals(userPhoto.getUserid()) || userPhoto.getUserid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数用户ID不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(userPhoto.getAlbumid()) || userPhoto.getAlbumid() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数相册ID不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(userPhoto.getSmallimgurl()) || userPhoto.getSmallimgurl() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数图片缩略图不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if ("".equals(userPhoto.getImgurl()) || userPhoto.getImgurl() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数图片原始图不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (userPhoto.getSort() != null) {
			sort = userPhoto.getSort();
		}
		userPhoto.setImgid(UUIDUtils.getUUID());
		userPhoto.setCreateid(userPhoto.getUserid());
		userPhoto.setUpdateid(userPhoto.getUserid());
		userPhoto.setCreatetime(new Date());
		userPhoto.setUpdatetime(new Date());
		userPhoto.setAlbumid(userPhoto.getAlbumid());
		userPhoto.setDeleteflag(0);
		userPhoto.setDescription(userPhoto.getDescription());
		userPhoto.setSort(sort);
		userphotoDao.insert(userPhoto);

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse createPhotos(Userphoto userPhoto) {
		Result result = null;
		JsonResponse res = null;
		Integer sort = 0;
		if (userPhoto.getPhotos() == null || userPhoto.getPhotos().size() < 1) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数photos不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		for (Userphoto photo : userPhoto.getPhotos()) {
			if (photo.getSort() != null)
				sort = photo.getSort();
			photo.setImgid(UUIDUtils.getUUID());
			photo.setCreateid(photo.getUserid());
			photo.setUpdateid(photo.getUserid());
			photo.setCreatetime(new Date());
			photo.setUpdatetime(new Date());
			photo.setDeleteflag(0);
			photo.setSort(sort);
			userphotoDao.insert(photo);
		}

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

}