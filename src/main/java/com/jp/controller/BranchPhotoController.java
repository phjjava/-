package com.jp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.entity.Branchphoto;
import com.jp.service.BranchalbumService;

@Controller
@RequestMapping("branchphoto")
public class BranchPhotoController {
	
	@Autowired
	private BranchalbumService baservice;
	
	
	/**
	 * 获取指定分支相册的相片列表 - 家族相册详情
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getPhotolistOfBranchAlbum", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getPhotolistOfBranchAlbum(Branchphoto entity) {
		return baservice.getPhotolistOfBranchAlbum(entity);
	}
	

}
