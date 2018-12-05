package com.jp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.entity.Banner;
import com.jp.service.BannerService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.UploadUtil;

@Controller
@RequestMapping("banner")
public class BannerController {
	private final Logger log_ = LogManager.getLogger(BannerController.class);
	@Autowired
	private BannerService bservice;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<Banner> pageModel, Banner banner, ModelMap model) {
		try {
			bservice.pageQuery(pageModel, banner);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						bservice.pageQuery(pageModel, banner);
					}
				}
			}
			model.put("pageModel", pageModel);
			model.put("banner", banner);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "banner/bannerList";

	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String bannerid = request.getParameter("bannerid");
			Banner banner = bservice.get(bannerid);
			model.put("banner", banner);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return "banner/banner";
	}

	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public String changeStatus(Banner banner) {
		String result = null;
		try {
			bservice.changeStatus(banner);
			result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Banner banner, ModelMap model,String bannerurlId) {
		Integer result = null;
		try {
			if (StringTools.notEmpty(banner.getBannerid())) {
				// 修改
				banner.setUpdatetime(new Date());
				banner.setUpdateid(CurrentUserContext.getCurrentUserId());
				if(bannerurlId != null){
					banner.setBannerurl(bannerurlId);
				}
				result = bservice.update(banner);
			} else {
				// 新增
				if (!StringTools.trimNotEmpty(banner.getFamilyid())) {
					banner.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				}
				banner.setDeleteflag(ConstantUtils.DELETE_FALSE);
				banner.setCreateid(CurrentUserContext.getCurrentUserId());
				banner.setUpdateid(CurrentUserContext.getCurrentUserId());
				banner.setBannerid(UUIDUtils.getUUID());
				banner.setUpdatetime(new Date());
				banner.setCreatetime(new Date());
				if(bannerurlId != null){
					banner.setBannerurl(bannerurlId);
				}
				result = bservice.insert(banner);
			}
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}

	/**
	 * 
	 * @描述 批量删除
	 * @作者 hongjun
	 * @时间 2017年5月10日下午5:32:11
	 * @参数 @param banner
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public String batchDelete(String bannerids) {
		String result = null;
		try {
			// a,b,c
			String bannerid = bannerids.substring(0, bannerids.length());
			String banneridArray[] = bannerid.split(",");
			bservice.batchDelete(banneridArray);
			result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
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
	public String savePhoto(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
		String result = null;
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
   				logoRealPathDir = logoRealPathDir+"/"+fileMe.getOriginalFilename();
				file = new File(logoRealPathDir);
				files[i].transferTo(file);
				fileList.add(file);
			}
			String status = UploadUtil.taskFileUpload(fileList, fileNams);
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(status, JsonObject.class);
			JsonObject jsonInfo = gson.fromJson(json.get("data"), JsonObject.class);
			result = jsonInfo.toString();
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
			e.printStackTrace();
			result = "{\"result\":\"1\"}";
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}
	/**
	 * @描述 选择不同的跳转类型级联
	 * @作者 sj
	 * @时间 2017年12月5日下午4:33:00
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/bannerJson", method = RequestMethod.POST)
    public String bannerJson(String goType)  {
    	String resultJson = null;
    	try {
    		resultJson = bservice.selectByGoType(goType);
		} catch (Exception e) {
			resultJson = "";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
    	return resultJson;
    }
}
