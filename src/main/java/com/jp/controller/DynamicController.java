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
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
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

   @ResponseBody
   @RequestMapping(value = "/get", method = RequestMethod.GET)
	public JsonResponse get(HttpServletRequest request, ModelMap model) {
	   Result result = null;
	   JsonResponse res = null;
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
			dynamic.setDynamicFiles(dylist); // 动态包含附件列表
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(dynamic);	// 动态
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return res;
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
   
   @ResponseBody
   @RequestMapping(value="/list",method = RequestMethod.POST)
   public JsonResponse list(PageModel<Dynamic> pageModel,Dynamic dynamic, ModelMap model){
	   Result result = null;
	   JsonResponse res = null;
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
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(pageModel.getList());
		res.setCount(pageModel.getPageInfo().getTotal());
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
	   return res;
	   
   }
   
    @ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
   public JsonResponse changeStatus(Dynamic dynamic)  {
    	Result result = new Result(MsgConstants.RESUL_FAIL);
 	    JsonResponse res = null;
	   	Integer count = 0;
	   	try {
	   		count = dyservice.changeStatus(dynamic);
	   		if(count > 0) {
	   			result = new Result(MsgConstants.RESUL_SUCCESS);
	   		}
			} catch (Exception e) {
				e.printStackTrace();
				log_.error("[JPSYSTEM]", e);
			}
	   	res = new JsonResponse(result);
	   	return res;
   }
    
    @ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public JsonResponse saveDynamic(Dynamic dynamic,HttpServletRequest request,String fileids)  {
    	Result result = new Result(MsgConstants.RESUL_FAIL);
 	    JsonResponse res = null;
		String dyfidArray [] = null;
		try{
			List<Dynamicfile> dyList  = dynamic.getDynamicFiles();
			if(StringTools.trimNotEmpty(dynamic.getDyid())) {
				for (int i = 0; i < dyList.size(); i++) {
					dyfidArray[i] = dyList.get(i).getFileid();  //更新动态时，如果有附件，先删除附件再重新写入
				}
			}
		    dyservice.saveDynamic(dynamic,dyList,dyfidArray);
		    result = new Result(MsgConstants.RESUL_SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
    
    @ResponseBody
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public JsonResponse batchDelete(String dyids){
    	Result result = new Result(MsgConstants.RESUL_FAIL);
 	    JsonResponse res = null;
    	try {
 		//a,b,c 
 		String dyid = dyids.substring(0, dyids.length());
 		String dyidArray [] = dyid.split(",");
 		dyservice.batchDelete(dyidArray);
 		result = new Result(MsgConstants.RESUL_SUCCESS);
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		log_.error("[JPSYSTEM]", e);
	 	}
    	res = new JsonResponse(result);
    	return res;
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
	public JsonResponse savePhoto(@RequestParam("file")MultipartFile[] files,HttpServletRequest request,String albumid,String userid) {
    	Result result = null;
 	    JsonResponse res = null;
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
		    result = new Result(MsgConstants.RESUL_SUCCESS);
		    res = new JsonResponse(result);
		    res.setData(newStr);
		    // result = "{\"result\":"+"\""+newStr+""+"\""+"}";
		}catch(Exception e){
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			// result = "{\"result\":\"1\"}";
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}
    
    /**
	* 以下方法用于api
	*/
    
    /**
     * 获取动态详情 - 家族动态详情
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/getDyDetail", method = RequestMethod.GET)
    public JsonResponse getDyDetail(Dynamic entity) {
    	return dyservice.getDyDetail(entity);
    }
    
    /**
     * 获取指定分支下的动态列表（包括其他分支发布的置顶的动态） - 家族动态列表
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/getDylist", method = RequestMethod.GET)
    public JsonResponse getDylist(Dynamic entity,HttpServletRequest request) {
    	String familyid = request.getHeader("familyid");
    	entity.setFamilyid(familyid);
    	return dyservice.getDylist(entity);
    }
}
   
