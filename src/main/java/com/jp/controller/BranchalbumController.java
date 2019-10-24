package com.jp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Branchalbum;
import com.jp.entity.Branchphoto;
import com.jp.service.BranchalbumService;

@Controller
@RequestMapping("branchalbum")
public class BranchalbumController {
	@Autowired
	private BranchalbumService baservice;

	/**
	 * 分支相册（回显）
	 * @param albumid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String albumid) {
		return baservice.get(albumid);
	}

	/**
	 * 分支相册列表查询
	 * @param pageModel
	 * @param ranchalbum
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Branchalbum> pageModel, Branchalbum ranchalbum) {
		return baservice.pageQuery(pageModel, ranchalbum);
	}

	/**
	 * 批量保存照片信息
	 * 
	 * @param userPhotoList
	 * @return
	 */
	@RequestMapping(value = "/batchSavePhoto", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse batchSavePhoto(@RequestBody List<Branchphoto> userPhotoList) {
		return baservice.insertBranchPhoto(userPhotoList);
	}

	/**
	 * 分支新增相册
	 * @param branchAlbum
	 * @return
	 */
	@RequestMapping(value = "/mergeBranchAlbum", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse mergeBranchAlbum(Branchalbum branchAlbum) {
		return baservice.mergeBranchAlbum(branchAlbum);
	}

	/**
	 * 相册信息和照片列表
	 * @param albumid
	 * @param branchid
	 * @return
	 */
	@RequestMapping(value = "/showPhoto", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse showPhoto(String albumid, String branchid) {
		return baservice.getAlbumAndPhotos(albumid, branchid);
	}

	/**
	 * 删除单张图片
	 * @param branchPhoto
	 * @return
	 */
	@RequestMapping(value = "/deletPhoto", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deletPhoto(Branchphoto branchPhoto) {
		// 删除时改变删除标记
		branchPhoto.setDeleteflag(1);
		return baservice.updateByPrimaryKeySelective(branchPhoto);
	}

	/**
	 * 单张照片编辑回显
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editbranchphoto", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse edituserphoto(String imgid) {
		return baservice.selectByPrimaryKey(imgid);
	}

	/**
	 * 保存用户照片 编辑照片说明
	 * @param branchphoto
	 * @return
	 */
	@RequestMapping(value = "/mergeBranchPhoto", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse mergeUserPhoto(Branchphoto branchphoto) {
		return baservice.updateByPrimaryKeySelective(branchphoto);
	}

	/**
	 * 批量删除相册(逻辑删除)
	 * @param albumids
	 * @return
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse batchDelete(String albumids) {
		return baservice.batchDelete(albumids);
	}

	/**
	 * 修改相册状态
	 * 
	 * @描述 TODO
	 * @作者 jinlizhi
	 * @时间 2017年6月5日下午3:18:53
	 * @参数 @param branchAlbum
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse changeStatus(Branchalbum branchAlbum) {
		return baservice.changeStatus(branchAlbum);
	}

	/**
	 * 以下方法用于api
	 */

	/**
	 * 获取家族相册列表
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getAlbum", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getAlbum(Branchalbum entity, HttpServletRequest request) {
		String familyid = request.getHeader("familyid");
		entity.setFamilyid(familyid);
		return baservice.getAlbum(entity);
	}
}
