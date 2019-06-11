package com.jp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.common.UploadReturnEntity;
import com.jp.entity.Branchalbum;
import com.jp.entity.Branchphoto;
import com.jp.entity.BranchphotoExample;
import com.jp.service.BranchalbumService;
import com.jp.util.GsonUtil;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.UploadUtil;

@Controller
@RequestMapping("branchalbum")
public class BranchalbumController {
	@Autowired
	private BranchalbumService baservice;
	private final Logger log_ = LogManager.getLogger(BranchalbumController.class);

	/**
	 * @描述 分支相册 去新增编辑界面
	 * @作者 sj
	 * @时间 2017年5月21日下午10:44:17
	 * @参数 @param request
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		Branchalbum branchalbum = null;
		try {
			String albumid = request.getParameter("albumid");
			String branchid = request.getParameter("branchid");// 没用到，不知道为什么
			if (StringTools.trimNotEmpty(albumid) && StringTools.trimNotEmpty(branchid)) {
				branchalbum = baservice.get(albumid, branchid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		if (branchalbum == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(branchalbum);
		return res;
	}

	/**
	 * @描述 分支相册列表查询
	 * @作者 sj
	 * @时间 2017年5月21日下午10:43:55
	 * @参数 @param pageModel
	 * @参数 @param ranchalbum
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Branchalbum> pageModel, Branchalbum ranchalbum) {
		Result result = null;
		JsonResponse res = null;
		try {
			baservice.pageQuery(pageModel, ranchalbum);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						baservice.pageQuery(pageModel, ranchalbum);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(pageModel);
		return res;
	}

	/**
	 * @描述 分支上传图片（弃用）
	 * @作者 sj
	 * @时间 2017年5月21日下午11:15:06
	 * @参数 @param files
	 * @参数 @param request
	 * @参数 @param albumid
	 * @参数 @param userid
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/saveBranchAlbum", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveBranchAlbum(@RequestParam("file") MultipartFile[] files, HttpServletRequest request,
			String albumid, String branchid) {
		Result result = null;
		JsonResponse res = null;
		String str = null;
		try {
			List<String> fileNams = new ArrayList<String>();
			List<File> fileList = new ArrayList<File>();
			File file = null;
			for (MultipartFile fileM : files) {
				String fileName = fileM.getOriginalFilename();
				fileNams.add(fileName);
			}
			String pathDir = "/upload";
			String logoRealPathDir = request.getSession().getServletContext().getRealPath(pathDir);
			for (int i = 0; i < files.length; i++) {
				MultipartFile fileMe = files[i];
				logoRealPathDir = logoRealPathDir + "/" + fileMe.getOriginalFilename();
				file = new File(logoRealPathDir);
				files[i].transferTo(file);
				fileList.add(file);
			}
			String status = UploadUtil.taskFileUpload(fileList, fileNams);
			//
			UploadReturnEntity upload = GsonUtil.GsonToBean(status, UploadReturnEntity.class);
			if (upload != null && upload.getResult() == 0) {
				HashMap<String, String> data = upload.getData();
				String imgurl = data.get("url");
				String smallImurl = data.get("iconUrl");
				String picName = data.get("fileName");
				Branchphoto branchPhoto = null;
				List<Branchphoto> userPhotoList = new ArrayList<Branchphoto>();
				for (int i = 0; i < files.length; i++) {
					branchPhoto = new Branchphoto();
					branchPhoto.setAlbumid(albumid);
					branchPhoto.setBranchid(branchid);
					branchPhoto.setImgurl(imgurl);
					branchPhoto.setSmallimgurl(smallImurl);
					branchPhoto.setDescription(picName);
					branchPhoto.setCreatetime(new Date());
					branchPhoto.setCreateid(CurrentUserContext.getCurrentUserId());
					branchPhoto.setDeleteflag(0);
					branchPhoto.setImgid(UUIDUtils.getUUID());
					userPhotoList.add(branchPhoto);
				}
				baservice.insertBranchPhoto(userPhotoList);
				str = "1";
			} else {
				str = "0";
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setData(str);
				return res;
			}
			//
			/*
			 * Gson gson = new GsonBuilder().create(); JsonObject json =
			 * gson.fromJson(status, JsonObject.class); JsonObject jsonInfo =
			 * gson.fromJson(json.get("data"), JsonObject.class); String url = ""; url =
			 * jsonInfo.get("url").toString(); String newStr = url.replaceAll("\"","");
			 */
		} catch (Exception e) {
			e.printStackTrace();
			str = "0";
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			res.setData(str);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(str);
		return res;
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
		List<Branchphoto> branchphotos = new ArrayList<Branchphoto>();
		for (Branchphoto bp : userPhotoList) {
			Branchphoto branchPhoto = new Branchphoto();
			branchPhoto.setImgid(UUIDUtils.getUUID());
			branchPhoto.setAlbumid(bp.getAlbumid());
			branchPhoto.setBranchid(bp.getBranchid());
			branchPhoto.setImgurl(bp.getImgurl());
			branchPhoto.setSmallimgurl(bp.getSmallimgurl());
			branchPhoto.setDescription(bp.getDescription());
			branchPhoto.setCreatetime(new Date());
			branchPhoto.setCreateid(CurrentUserContext.getCurrentUserId());
			branchPhoto.setDeleteflag(0);
			branchphotos.add(branchPhoto);
		}

		return baservice.insertBranchPhoto(branchphotos);
	}

	/**
	 * 
	 * @描述 分支新增相册
	 * @作者 sj
	 * @时间 2017年5月17日上午10:31:58
	 * @参数 @param userAlbum
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/mergeBranchAlbum", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse mergeBranchAlbum(Branchalbum branchAlbum) {
		Result result = null;
		JsonResponse res = null;
		String ablumId = null;
		try {
			ablumId = baservice.mergeBranchAlbum(branchAlbum);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(ablumId);
		return res;
	}

	/**
	 * @描述 相册照片列表
	 * @作者 sj
	 * @时间 2017年5月22日上午12:52:29
	 * @参数 @param albumid
	 * @参数 @param userid
	 * @参数 @param modelMap
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/showPhoto", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse showPhoto(String albumid, String branchid) {
		Result result = null;
		JsonResponse res = null;
		List<Branchphoto> photoList = null;
		Branchalbum branchalbum = null;
		try {
			BranchphotoExample example = new BranchphotoExample();
			com.jp.entity.BranchphotoExample.Criteria criteria = example.createCriteria();
			if (StringTools.trimNotEmpty(albumid)) {
				criteria.andAlbumidEqualTo(albumid);
			}
			criteria.andDeleteflagEqualTo(0);
			photoList = baservice.selectByExample(example);
			branchalbum = baservice.get(albumid, branchid);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(photoList);
		res.setEntity(branchalbum);
		return res;
	}

	/**
	 * @描述 删除单张图片
	 * @作者 sj
	 * @时间 2017年5月18日下午3:02:34
	 * @参数 @param userAlbum
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/deletPhoto", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deletPhoto(Branchphoto branchPhoto) {
		Result result = null;
		JsonResponse res = null;
		String str = "";
		try {
			Branchphoto key = new Branchphoto();
			key.setAlbumid(branchPhoto.getAlbumid());
			key.setImgid(branchPhoto.getImgid());
			key.setBranchid(branchPhoto.getBranchid());
			key.setDeleteflag(1);
			// 删除照片
			baservice.updateByPrimaryKeySelective(key);
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			res.setData("0");
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(str);
		return res;
	}

	/**
	 * 
	 * @描述 去单张照片编辑界面
	 * @作者 sj
	 * @时间 2017年5月21日下午4:01:46
	 * @参数 @param request
	 * @参数 @param modelMap
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/editbranchphoto", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse edituserphoto(HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		Branchphoto branchphoto = null;
		try {
			String branchid = request.getParameter("branchid");
			String albumid = request.getParameter("albumid");
			String imgid = request.getParameter("imgid");
			// BranchphotoKey key = new BranchphotoKey();
			// key.setImgid(imgid);
			// key.setAlbumid(albumid);
			// key.setBranchid(branchid);
			branchphoto = baservice.selectByPrimaryKey(imgid);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(branchphoto);
		System.out.println(branchphoto);
		return res;
	}

	/**
	 * @描述 保存用户照片 编辑照片说明
	 * @作者 sj
	 * @时间 2017年5月21日下午4:17:41
	 * @参数 @param userAlbum
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/mergeBranchPhoto", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse mergeUserPhoto(Branchphoto branchphoto) {
		Result result = null;
		JsonResponse res = null;
		String str = "";
		try {
			baservice.updateByPrimaryKeySelective(branchphoto);
			str = "1";
		} catch (Exception e) {
			str = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			res.setData(str);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(str);
		return res;
	}

	/**
	 * 
	 * @描述 批量删除相册(逻辑删除)
	 * @作者 jinlizhi
	 * @时间 2017年6月5日下午3:11:57
	 * @参数 @param albumids
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse batchDelete(String albumids) {
		Result result = null;
		JsonResponse res = null;
		String str = null;
		try {
			String albumid = albumids.substring(0, albumids.length());
			String albumidArray[] = albumid.split(",");
			baservice.batchDelete(albumidArray);
			str = "1";
		} catch (Exception e) {
			str = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			res.setData(str);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(str);
		return res;
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
		Result result = null;
		JsonResponse res = null;
		String str = null;
		try {
			str = baservice.changeStatus(branchAlbum) + "";
		} catch (Exception e) {
			str = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			res.setData(str);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(str);
		return res;
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
