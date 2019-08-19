package com.jp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.jp.entity.Dynamic;
import com.jp.entity.Dynamicfile;
import com.jp.service.DynamicService;
import com.jp.util.JacksonUtil;
import com.jp.util.UploadUtil;

@Controller
@RequestMapping("dynamic")
public class DynamicController {
	@Autowired
	private DynamicService dyservice;

	private final Logger log_ = LogManager.getLogger(DynamicController.class);

	/**
	 * 动态详细信息回显
	 * @param dyid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public JsonResponse get(String dyid) {
		return dyservice.get(dyid);
	}

	/**
	 * 分页查询动态
	 * @param pageModel
	 * @param dynamic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public JsonResponse list(PageModel<Dynamic> pageModel, Dynamic dynamic) {
		return dyservice.pageQuery(pageModel, dynamic);
	}

	/**
	 *  置顶状态
	 * @param dynamic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(Dynamic dynamic) {
		return dyservice.changeStatus(dynamic);
	}

	/**
	 * 保存或编辑动态
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public JsonResponse saveDynamic(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		String jsonstr = sb.toString();
		try {
			String jsonReplace = jsonstr.replace("\"\"", "null");
			Dynamic dynamic = JacksonUtil.fromJsonToObject(jsonReplace, Dynamic.class);
			List<Dynamicfile> dyList = dynamic.getDynamicFiles();
			return dyservice.saveDynamic(dynamic, dyList);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			Result result = new Result(MsgConstants.SYS_ERROR);
			return new JsonResponse(result);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public JsonResponse batchDelete(String dyids) {
		return dyservice.batchDelete(dyids);
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
	public JsonResponse savePhoto(@RequestParam("file") MultipartFile[] files, HttpServletRequest request,
			String albumid, String userid) {
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
			String logoRealPathDir = request.getSession().getServletContext().getRealPath(pathDir);
			for (int i = 0; i < files.length; i++) {
				MultipartFile fileMe = files[i];
				logoRealPathDir = logoRealPathDir + "/" + fileMe.getOriginalFilename();
				file = new File(logoRealPathDir);
				// OutputStream output = new FileOutputStream(file);
				// BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
				// bufferedOutput.write(files[i].getBytes());
				files[i].transferTo(file);
				fileList.add(file);
			}
			String status = UploadUtil.taskFileUpload(fileList, fileNams);
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(status, JsonObject.class);
			JsonObject jsonInfo = gson.fromJson(json.get("data"), JsonObject.class);
			// result = jsonInfo.toString();
			// if ("0".equals(json.get("result").toString())) {
			// // 删除缓存文件
			// boolean flag = file.delete();
			// if (!flag) {
			// log_.error("上传文件缓存删除失败");
			// }
			// }
			String url = "";
			url = jsonInfo.get("url").toString();
			String newStr = url.replaceAll("\"", "");
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(newStr);
			// result = "{\"result\":"+"\""+newStr+""+"\""+"}";
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
	 * 以下方法用于api
	 */

	/**
	 * 获取动态详情 - 家族动态详情
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDyDetail", method = RequestMethod.GET)
	public JsonResponse getDyDetail(Dynamic entity) {
		return dyservice.getDyDetail(entity);
	}

	/**
	 * 获取指定分支下的动态列表（包括其他分支发布的置顶的动态） - 家族动态列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDylist", method = RequestMethod.GET)
	public JsonResponse getDylist(Dynamic entity, HttpServletRequest request) {
		String familyid = request.getHeader("familyid");
		entity.setFamilyid(familyid);
		return dyservice.getDylist(entity);
	}
}
