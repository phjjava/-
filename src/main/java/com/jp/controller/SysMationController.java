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
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.entity.MationType;
import com.jp.entity.SysMation;
import com.jp.service.SysMationService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.UploadUtil;

@Controller
@RequestMapping("mation")
public class SysMationController {
	private final Logger log_ = LogManager.getLogger(BannerController.class);
	@Autowired
	private SysMationService mationService;
	@ResponseBody
	@RequestMapping(value = "/selectlist", method = RequestMethod.POST)
	
    public JsonResponse bannerJson(String goType)  {
		Result result = null;
		JsonResponse res = null;
    	List<SysMation> gotypeList = null;
    	
    	try {
    		gotypeList = mationService.selectByGoType(goType);
    			result = new Result(MsgConstants.RESUL_SUCCESS);
        		res = new JsonResponse(result);
        		res.setData(gotypeList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
    	return res;
    }
	/**
	 * 调取下拉框类型值（增加修改时调取使用）
	 * @param mation
	 * @param model
	 * @param mationid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selecttypelist", method = RequestMethod.POST)
    public JsonResponse selecttypelist()  {
		Result result = null;
		JsonResponse res = null;
    	List<MationType> gotypeList = null;
    	try {
    		gotypeList = mationService.selecttypelist();
    			result = new Result(MsgConstants.RESUL_SUCCESS);
        		res = new JsonResponse(result);
        		res.setData(gotypeList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
    	return res;
    }
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(SysMation mation, ModelMap model,String mationid,String relevanceid,Integer stick) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			if (StringTools.notEmpty(mation.getMationid())) {
				// 修改
				mation.setUpdatetime(new Date());
				mation.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				mation.setRelevanceid(relevanceid);
				mation.setStick(stick);
				if(mationid != null){
					mation.setImgid(mationid);
				}
				count = mationService.update(mation);
			} else {
				//新增
				mation.setDeleteflag(ConstantUtils.DELETE_FALSE);
				mation.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				mation.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				mation.setMationid(UUIDUtils.getUUID()); 
				mation.setUpdatetime(new Date());
				mation.setCreatetime(new Date());
				mation.setRelevanceid(relevanceid);
				mation.setStick(stick);
				mation.setCount(0);
				if(mationid != null){
					mation.setImgid(mationid);
				}
				count = mationService.insert(mation);
			}
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
	/**
	 * 
	 * @描述 咨询图片上传
	 * @作者 jinlizhi
	 * @时间 2017年5月22日下午4:59:46
	 * @参数 @param files
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/saveMationPhoto", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public JsonResponse savePhoto(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			List<String> fileNams = new ArrayList<String>();
			List<File> fileList = new ArrayList<File>();
			File file = null;
			for (MultipartFile fileM : files) {
				String fileName = fileM.getOriginalFilename();
				fileNams.add(fileName);
			}
			String pathDir = "/upload";
			//获取图片真实路径
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
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(jsonInfo.toString());
			if ("0".equals(json.get("result").toString())) {
				 // 删除缓存文件
				boolean flag = file.delete();
				if (!flag) {
					log_.error("上传文件缓存删除失败");
				}
			}
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
	 * 
	 * @描述 咨询批量删除
	 * @作者 hongjun
	 * @时间 2017年5月10日下午5:32:11
	 * @参数 @param banner
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/mationDeleteAll", method = RequestMethod.POST)
	public JsonResponse batchDeleteAll(String mationids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			// a,b,c
			String mationid = mationids.substring(0, mationids.length());
			String banneridArray[] = mationid.split(",");
			mationService.batchDeleteAll(banneridArray);
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
	/**
	 * 类型表列表
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/findtypelist", method = RequestMethod.POST)
    public JsonResponse findtypelist()  {
		Result result = null;
		JsonResponse res = null;
    	List<MationType> gotypeList = null;
    	try {
    		gotypeList = mationService.selecttypelist();
    			result = new Result(MsgConstants.RESUL_SUCCESS);
        		res = new JsonResponse(result);
        		res.setData(gotypeList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
    	return res;
    }
	/**
	 * 类型表新增/编辑
	 * @param mation
	 * @param model
	 * @param mationid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/typesave", method = RequestMethod.POST)
	public JsonResponse typesave(MationType mationtype, ModelMap model,String typeid) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			if (StringTools.notEmpty(mationtype.getTypeid())) {
				// 修改
				/*mation.setUpdatetime(new Date());
				mation.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());*/
				/*if(typeid != null){
					mationtype.setImgid(typeid);
				}*/
				count = mationService.updatetype(mationtype);
			} else {
				//新增
				mationtype.setDeleteflag(ConstantUtils.DELETE_FALSE);
				mationtype.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				mationtype.setTypeid(UUIDUtils.getUUID()); 
				mationtype.setCreatetime(new Date());
				/*if(typeid != null){
					mation.setImgid(mationid);
				}*/
				count = mationService.inserttype(mationtype);
			}
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
	/**
	 * 
	 * @描述 咨询类型批量删除
	 * @时间 2017年5月10日下午5:32:11
	 * @参数 @param banner
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/mationtypeDeleteAll", method = RequestMethod.POST)
	public JsonResponse mationtypeDeleteAll(String mationtypeids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			// a,b,c
			String mationid = mationtypeids.substring(0, mationtypeids.length());
			//按逗号截取放入数组
			String mationtypeArray[] = mationid.split(",");
			mationService.mationtypeDeleteAll(mationtypeArray);
			//返回成功
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
}
