package com.jp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.Banner;
import com.jp.service.BannerService;
import com.jp.util.UploadUtil;

@Controller
@RequestMapping("banner")
public class BannerController {
	private final Logger log_ = LogManager.getLogger(BannerController.class);
	@Autowired
	private BannerService bservice;

	/**
	 * banner列表
	 * @param pageModel
	 * @param banner
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Banner> pageModel, Banner banner, ModelMap model) {
		return bservice.pageQuery(pageModel, banner);
	}

	/**
	 * banner 回显
	 * @param bannerid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String bannerid) {
		return bservice.get(bannerid);
	}

	/**
	 * 停用banner
	 * @param banner
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(Banner banner) {
		return bservice.changeStatus(banner);
	}

	/**
	 * banner新增/编辑保存接口
	 * @param banner
	 * @param bannerurlId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(Banner banner, String bannerurlId) {
		return bservice.save(banner, bannerurlId);
	}

	/**
	 * 批量删除
	 * @param bannerids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public JsonResponse batchDelete(String bannerids) {
		return bservice.batchDelete(bannerids);
	}

	/**
	 * 
	 * @描述 banner图片上传
	 * @作者 jinlizhi
	 * @时间 2017年5月22日下午4:59:46
	 * @参数 @param files
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/savePhoto", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public JsonResponse savePhoto(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		// String result = null;
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
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(status, JsonObject.class);
			JsonObject jsonInfo = gson.fromJson(json.get("data"), JsonObject.class);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(jsonInfo.toString());
			// result = jsonInfo.toString();
			System.out.println(json.get("result"));
			if ("0".equals(json.get("result").toString())) {
				// 删除缓存文件
				boolean flag = file.delete();
				if (!flag) {
					log_.error("上传文件缓存删除失败");
				}
			}
			//			String url = "";
			//		    url = jsonInfo.get("url").toString();
			//		    String newStr = url.replaceAll("\"","");
			//		    if(newStr.equals("0")){
			//				 // 删除缓存文件
			//				boolean flag = file.delete();
			//				if (!flag) {
			//					log_.error("上传文件缓存删除失败");
			//				}
			//		    }

			//		    result = "{\"result\":"+"\""+newStr+""+"\""+"}";
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			// result = "{\"result\":\"1\"}";
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	/**
	 * 选择不同的跳转类型级联
	 * @param goType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bannerJson", method = RequestMethod.POST)
	public JsonResponse bannerJson(String goType) {
		return bservice.selectByGoType(goType);
	}

	/**
	 * 以下方法用于api
	 */

	/**
	 * 根据家族id获取banner列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBanners", method = RequestMethod.GET)
	public JsonResponse getBanners(Banner banner) {
		return bservice.getBanners(banner);
	}
}
