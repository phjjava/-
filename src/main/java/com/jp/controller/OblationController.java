package com.jp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.jp.entity.Function;
import com.jp.entity.SysVersion;
import com.jp.entity.UserManager;
import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationType;
import com.jp.service.OblationService;
import com.jp.service.SysVersionService;
import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.UploadUtil;


@Controller
@RequestMapping("system/oblation")
public class OblationController {
	private final Logger log_ = LogManager.getLogger(OblationController.class);
	
	@Resource
	private OblationService oblationService;
	@Resource
	private SysVersionService sysVersionService;
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editFamily(WorshipOblation oblation,ModelMap modelMap)  {
		try{
			
			//初始化版本
			List<SysVersion> versionList = sysVersionService.getSysVersionList();
		  
		    modelMap.put("versionList", versionList);
		}catch(Exception e){
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "system/worship/oblationAdd";
	}
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(WorshipOblation oblation, ModelMap model) {
		Integer result = null;
		try {
			if(StringTools.notEmpty(oblation.getId())){//修改
				
				result = oblationService.update(oblation);
			}else{//新增
				oblation.setDeleteflag(ConstantUtils.DELETE_FALSE);// 使用中
				
				
				oblation.setId(UUIDUtils.getUUID());
				result = oblationService.insert(oblation);
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
	
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public String deleteEditorialBoard(WorshipOblation entity) {
		Integer result=0;
		try {
			result =oblationService.del(entity);
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			
			
		}
		return result+"";
	}
	
	@RequestMapping(value = "/getOblation", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			WorshipOblation oblation = new WorshipOblation();
			if(id !=null && !"".equals(id)) {
				oblation = oblationService.getOblationById(id);
			}

			model.put("oblation", oblation);
			
			List<SysVersion> versionList = sysVersionService.getSysVersionList();
			  
			model.put("versionList", versionList);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		
		return "system/worship/oblationAdd";
	}
}
