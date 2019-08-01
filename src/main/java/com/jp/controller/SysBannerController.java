package com.jp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.BannerHomePage;
import com.jp.entity.GoTypeResult;
import com.jp.entity.SysGoTypeResult;
import com.jp.entity.SysMation;
import com.jp.entity.SysUser;
import com.jp.service.SysBannerService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.UploadUtil;

@Controller
@RequestMapping("system/bannerhomepage")
public class SysBannerController {
	private final Logger log_ = LogManager.getLogger(SysBannerController.class);
	@Autowired
	private SysBannerService bpservice;
	@RequestMapping(value = "/homepagelist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse homepagelist(PageModel<BannerHomePage> pageModel, BannerHomePage banner, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			bpservice.pageQuery(pageModel, banner);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						bpservice.pageQuery(pageModel, banner);
					}
				}
			}

			//获取是否登录
			SysUser loginStatus = CurrentSystemUserContext.getSystemUserContext();
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
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			String bannerid = request.getParameter("bannerid");
			BannerHomePage banner = bpservice.get(bannerid);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(banner);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(BannerHomePage banner) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			count = bpservice.changeStatus(banner);
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
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(BannerHomePage banner, ModelMap model,String bannerurlId) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			if (StringTools.notEmpty(banner.getBannerid())) {
				// 修改
				banner.setUpdatetime(new Date());
				banner.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				if(bannerurlId != null){
					banner.setBannerurl(bannerurlId);
				}
				count = bpservice.update(banner);
			} else {
				//新增
				banner.setDeleteflag(ConstantUtils.DELETE_FALSE);
				banner.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				banner.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				banner.setBannerid(UUIDUtils.getUUID());
				banner.setUpdatetime(new Date());
				banner.setCreatetime(new Date());
				if(bannerurlId != null){
					banner.setBannerurl(bannerurlId);
				}
				count = bpservice.insert(banner);
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
	public JsonResponse batchDelete(String bannerids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			// a,b,c
			String bannerid = bannerids.substring(0, bannerids.length());
			String banneridArray[] = bannerid.split(",");
			bpservice.batchDelete(banneridArray);
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
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
			// result = jsonInfo.toString();
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
	 * @描述 选择不同的跳转类型级联
	 * @作者 sj
	 * @时间 2017年12月5日下午4:33:00
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/bannerJson", method = RequestMethod.POST)
    public JsonResponse bannerJson(String goType)  {
		Result result = null;
		JsonResponse res = null;
    	List<SysMation> gotypeList = null;
    	try {
    		gotypeList = bpservice.selectByGoType(goType);
    		if(gotypeList==null) {
    			result = new Result(MsgConstants.JUMP_MESSAGE);
        		res = new JsonResponse(result);
        		
    		}else {
    			result = new Result(MsgConstants.RESUL_SUCCESS);
        		res = new JsonResponse(result);
        		res.setData(gotypeList);
    		}
    		
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
    	return res;
    }
	
	/**
	 * @描述 物理删除
	 * @作者 lyc
	 * @时间 2019年7月26日下午3:33:00
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/realDelete", method = RequestMethod.POST)
	public JsonResponse realDelete(String bannerid) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			bpservice.realDelete(bannerid);
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
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
	@RequestMapping(value = "/realDeleteAll", method = RequestMethod.POST)
	public JsonResponse batchDeleteAll(String bannerids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			// a,b,c
			String bannerid = bannerids.substring(0, bannerids.length());
			String banneridArray[] = bannerid.split(",");
			bpservice.batchDeleteAll(banneridArray);
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
	/**
	 * @描述 资讯详情页
	 * @作者 lyc
	 * @时间 2019年7月26日下午3:33:00
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/SelectMationOne", method = RequestMethod.POST)
	public JsonResponse SelectMationOne(String mationid) {
		SysMation mationOneList = null;
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			mationOneList = bpservice.SelectMationOne(mationid);
			bpservice.updateCount(mationid);
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		res.setData(mationOneList);
		return res;
	}

}
