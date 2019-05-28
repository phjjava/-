package com.jp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.Useralbum;
import com.jp.entity.Userphoto;
import com.jp.service.UseralbumService;

@Controller
@RequestMapping("useralbum")
public class UserAlbumController {
	private final Logger log_ = LogManager.getLogger(UserAlbumController.class);
	@Autowired
	private UseralbumService userAlbumService;

	/**
	 * 获取用户的相册和个人作品
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getPersonAlbums", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getPersonAlbums(Useralbum entity, int start, int count) {
		return userAlbumService.getPersonAlbums(entity, start, count);
	}

	/**
	 * 获取某人指定相册的图片
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getPersonPhotosOfAlbum", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getPersonPhotosOfAlbum(Useralbum entity, int start, int count) {
		return userAlbumService.getPersonPhotosOfAlbum(entity, start, count);
	}

	/**
	 * 创建新的相册
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/createNewAlbum", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse createNewAlbum(@RequestBody Useralbum entity) {
		return userAlbumService.createNewAlbum(entity);
	}

	/**
	 * 上传新的照片
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/createPhoto", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse createPhoto(@RequestBody Userphoto entity) {
		return userAlbumService.createPhoto(entity);
	}

	/**
	 * 批量传新的照片
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/createPhotoMulti", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse createPhotoMulti(@RequestBody Userphoto entity) {
		return userAlbumService.createPhotos(entity);
	}

}
