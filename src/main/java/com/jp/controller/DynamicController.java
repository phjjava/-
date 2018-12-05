package com.jp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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
import com.jp.common.PageModel;
import com.jp.dao.DytopDao;
import com.jp.entity.Dynamic;
import com.jp.entity.Dynamicfile;
import com.jp.entity.DynamicfileQuery;
import com.jp.entity.Dytop;
import com.jp.entity.DytopQuery;
import com.jp.service.DynamicService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.UploadUtil;


@Controller
@RequestMapping("dynamic")
public class DynamicController {
   @Autowired
   private DynamicService dyservice;
   @Autowired
   private DytopDao dytopDao;
   
   private final Logger log_ = LogManager.getLogger(DynamicController.class);

   @RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String dyid = request.getParameter("dyid");
			Dynamic dynamic = dyservice.get(dyid);
			//获取置顶top信息
			initDyTop(dyid, dynamic);
			DynamicfileQuery dfq = new DynamicfileQuery();
			com.jp.entity.DynamicfileQuery.Criteria criteria = dfq.createCriteria();
			if(StringTools.trimNotEmpty(dynamic.getDyid())){
				criteria.andDyidEqualTo(dynamic.getDyid());
			}
			List<Dynamicfile> dylist = dyservice.selectdyfile(dfq);
			model.put("dynamic", dynamic);
			model.put("dylist", dylist);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return "dynamic/dynamic";
	}
   	/**
   	 * 
   	 * @描述 初始化dytop信息
   	 * @作者 jinlizhi
   	 * @时间 2017年5月19日下午9:32:29
   	 * @参数 @param dyid
   	 * @参数 @param dynamic
   	 * @return void
   	 */
	private void initDyTop(String dyid, Dynamic dynamic) {
		DytopQuery example=new DytopQuery();
		example.or().andDyidEqualTo(dyid);
		List<Dytop> dytopList = dytopDao.selectByExample(example);
		if(dytopList != null && ! dytopList.isEmpty()){
			StringBuffer sb=new StringBuffer();
			for (Dytop dytop : dytopList) {
				sb.append(dytop.getBranchid());
				sb.append(",");
			}
			String braStr = sb.toString();
			String substring = braStr.substring(0, braStr.length()-1);
			dynamic.setTobranchid(substring);
		}
	}
   
   @RequestMapping(value="/list",method = RequestMethod.POST)
   public String list(PageModel<Dynamic> pageModel,Dynamic dynamic, ModelMap model){
	   try {
		dyservice.pageQuery(pageModel,dynamic);
		if(pageModel.getList()!=null){
			if(pageModel.getPageSize()==0){
				if(pageModel.getPageNo()!=null&&!"1".equals(pageModel.getPageNo())){
					pageModel.setPageNo(pageModel.getPageNo() - 1);
					dyservice.pageQuery(pageModel,dynamic);
				}
			}
		}
		model.put("pageModel", pageModel);
		model.put("dynamic", dynamic);
	} catch (Exception e) {
		e.printStackTrace();
		log_.error("[JPSYSTEM]", e);
	}
	   return "dynamic/dynamicList";
	   
   }
   
    @ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
   public String changeStatus(Dynamic dynamic)  {
   	String result = null;
   	try {
			dyservice.changeStatus(dynamic);
			result="1";
		} catch (Exception e) {
			result="0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
   	return result;
   }
    
    @ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public String saveDynamic(Dynamic dynamic,@RequestParam("dyfile")MultipartFile file,HttpServletRequest request,String fileids)  {
		String result = null;
		String url = null;
		String dyfidArray [] = null;
		try{
			
			if(StringTools.trimNotEmpty(fileids)){
				String dyfid = fileids.substring(0, fileids.length()-1);
	     		dyfidArray = dyfid.split(",");
			}
    		List<String> fileNams = new  ArrayList<String>();
			List<File> fileList = new ArrayList<File>();
			String fileName = "";
			fileName = file.getOriginalFilename();
			fileNams.add(fileName);
			String pathDir = "/upload";
			String logoRealPathDir = request.getSession().getServletContext().getRealPath(pathDir);
			File dyfile = new File(logoRealPathDir);
			if(StringTools.trimNotEmpty(fileName)){
				OutputStream output = new FileOutputStream(dyfile);
			    BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
				bufferedOutput.write(file.getBytes());
				fileList.add(dyfile);
			}
		    
			String status = "";
			List<Dynamicfile> dyList  = new ArrayList<Dynamicfile>();
			if(!fileName.equals("") && fileName != null){
				 status = UploadUtil.taskFileUpload(fileList, fileNams);
				 Gson gson = new GsonBuilder().create();
				 JsonObject json = gson.fromJson(status, JsonObject.class);
				 JsonObject jsonInfo = gson.fromJson(json.get("data"), JsonObject.class);
				 url = jsonInfo.get("url").toString();
				 String newStr = url.replaceAll("\"","");
				 Dynamicfile dyf = null;
				    
			    	dyf = new Dynamicfile();
			    	dyf.setBranchid(dynamic.getBranchid());
			    	dyf.setFileurl(newStr);
			    	dyf.setFileid(UUIDUtils.getUUID());
			    	dyf.setFilename(fileName);
			    	dyList.add(dyf);
			}
		    dyservice.saveDynamic(dynamic,dyList,dyfidArray);
		    result = "1";
		}catch(Exception e){
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}
    @ResponseBody
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public String batchDelete(String dyids){
    	String result=null;
    	try {
 		//a,b,c 
 		String dyid = dyids.substring(0, dyids.length());
 		String dyidArray [] = dyid.split(",");
 		dyservice.batchDelete(dyidArray);
 		result="1";
 	} catch (Exception e) {
 		result = "0";
 		e.printStackTrace();
 		log_.error("[JPSYSTEM]", e);
 	}
    	return result;
    }
    /**
     * @描述 保存单张图片
     * @作者 hongjun
     * @时间 2017年5月22日上午11:03:33
     * @参数 @param files
     * @参数 @param request
     * @参数 @param albumid
     * @参数 @param userid
     * @参数 @return
     * @return String
     */
    @ResponseBody
	@RequestMapping(value = "/savePhoto", method = RequestMethod.POST)
	public String savePhoto(@RequestParam("file")MultipartFile[] files,HttpServletRequest request,String albumid,String userid) {
		String result = null;
		try{
			List<String> fileNams = new  ArrayList<String>();
			List<File> fileList = new ArrayList<File>();
			File file = null;
			for(MultipartFile fileM : files) {
				String fileName = fileM.getOriginalFilename();
				fileNams.add(fileName);
			}
			String pathDir = "/upload";
			String logoRealPathDir = request.getSession().getServletContext().getRealPath(pathDir);
			for(int i = 0; i < files.length; i++) {
				MultipartFile fileMe = files[i];
   				logoRealPathDir = logoRealPathDir+"/"+fileMe.getOriginalFilename();
				file = new File(logoRealPathDir);
//				OutputStream output = new FileOutputStream(file);
//			    BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
//				bufferedOutput.write(files[i].getBytes());
				files[i].transferTo(file);
				fileList.add(file);
			}
			String status = UploadUtil.taskFileUpload(fileList, fileNams);
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(status, JsonObject.class);
		    JsonObject jsonInfo = gson.fromJson(json.get("data"), JsonObject.class);
//		    result = jsonInfo.toString();
//   		    if ("0".equals(json.get("result").toString())) {
//				// 删除缓存文件
//				boolean flag = file.delete();
//				if (!flag) {
//					log_.error("上传文件缓存删除失败");
//				}
//			}
		    String url = "";
		    url = jsonInfo.get("url").toString();
		    String newStr = url.replaceAll("\"","");
		    result = "{\"result\":"+"\""+newStr+""+"\""+"}";
		}catch(Exception e){
			e.printStackTrace();
			result = "{\"result\":\"1\"}";
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}
}
   
