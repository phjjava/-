package com.jp.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
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
import com.jp.entity.Event;
import com.jp.service.EventService;
import com.jp.util.UploadUtil;

@Controller
@RequestMapping("event")
public class EventController {
	private final Logger log_ = LogManager.getLogger(EventController.class);
	@Autowired
	private EventService eservice;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public JsonResponse list(PageModel<Event> pageModel, ModelMap model, Event event) {
		Result result = null;
		JsonResponse res = null;
		try {
			eservice.pageQuery(pageModel, event);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						eservice.pageQuery(pageModel, event);
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
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public JsonResponse get(HttpServletRequest request, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			String eventid = request.getParameter("eventid");
			Event event = eservice.get(eventid);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(event);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public JsonResponse batchDelete(String eventids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			String eventid = eventids.substring(0, eventids.length());
			String eventArray[] = eventid.split(",");
			count = eservice.batchDelete(eventArray);
			if (count > 0) {
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
	 * @描述 保存大事记
	 * @作者 hongjun
	 * @时间 2017年5月12日上午10:41:57
	 * @参数 @param event
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse saveEvent(Event event, HttpServletRequest request) {
		if (request.getCharacterEncoding() == null) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				Result result = new Result(MsgConstants.SYS_ERROR);
				return new JsonResponse(result);
			}
		}
		return eservice.saveEvent(event);
	}

	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(Event event) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		int count = 0;
		try {
			count = eservice.changeStatus(event);
			if (count > 0) {
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
	@RequestMapping(value = "/savePhoto", method = RequestMethod.POST)
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
			// result = "{\"result\":"+"\""+newStr+"\""+"}";
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
	 * 获取大事记列表信息 - 家族大事记列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEventList", method = RequestMethod.GET)
	public JsonResponse getEventList(Event entity) {
		return eservice.getEventList(entity);
	}

	/**
	 * 获取大事记详情 - 家族大事记详情
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEventDetail", method = RequestMethod.GET)
	public JsonResponse getEventDetail(Event entity) {
		return eservice.getEventDetail(entity);
	}
}
