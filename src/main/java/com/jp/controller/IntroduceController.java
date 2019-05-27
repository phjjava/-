package com.jp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.Introduce;
import com.jp.service.IntroduceService;

@Controller
@RequestMapping("introduce")
public class IntroduceController {
	@Autowired
	private IntroduceService itservice;

	private final Logger log_ = LogManager.getLogger(IntroduceController.class);

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		Introduce introduce = null;
		try {
			String itid = request.getParameter("introduceid");
			introduce = itservice.get(itid);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(introduce);
		return res;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Introduce> pageModel, Introduce introduce) {
		Result result = null;
		JsonResponse res = null;
		try {
			itservice.pageQuery(pageModel, introduce);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						itservice.pageQuery(pageModel, introduce);
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

	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse batchDelete(String introduceids) {
		String str = null;
		Result result = null;
		JsonResponse res = null;
		try {
			String introduceid = introduceids.substring(0, introduceids.length());
			String introduceArray[] = introduceid.split(",");
			itservice.batchDelete(introduceArray);
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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse saveIntroduce(Introduce introduce, HttpServletRequest request) {
		String str = null;
		Result result = null;
		JsonResponse res = null;
		try {
			if (request.getCharacterEncoding() == null) {
				request.setCharacterEncoding("UTF-8");
			}
			itservice.saveIntroduce(introduce);
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
	* 以下方法用于api
	*/
	
	/**
	 * 获取家谱章节列表 - 家族谱章节列表
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getIntroduceList", method = RequestMethod.GET)
	public JsonResponse getIntroduceList(Introduce entity) {
		return itservice.getIntroduceList(entity);
	}
	
	/**
	 * 获取家谱章节详细信息 - 家族谱某章节详情
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getIntroduceDetail", method = RequestMethod.GET)
	public JsonResponse getIntroduceDetail(Introduce entity) {
		return itservice.getIntroduceDetail(entity);
	}
	
	/**
	 * 获取菜单列表 - 获取首页菜单列表
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
	public JsonResponse getMenuList(Introduce entity,HttpServletRequest request) {
		return itservice.getMenuList(entity,request);
	}
	
}
