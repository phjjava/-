package com.jp.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.BannerHomePage;
import com.jp.entity.JpSynopsis;
import com.jp.entity.SysUser;
import com.jp.service.SynopsisService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/synopsis")
public class SynopsisController {
	private final Logger log_ = LogManager.getLogger(SynopsisController.class);
	@Autowired
	private SynopsisService syservice;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<JpSynopsis> pageModel, JpSynopsis jpSynopsis, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			syservice.pageQuery(pageModel, jpSynopsis);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						syservice.pageQuery(pageModel, jpSynopsis);
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
			String id = request.getParameter("id");
			JpSynopsis jpSynopsis = syservice.get(id);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(jpSynopsis);
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
	public JsonResponse changeStatus(JpSynopsis jpSynopsis) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			count = syservice.changeStatus(jpSynopsis);
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
	public JsonResponse save(JpSynopsis jpSynopsis, ModelMap model,String id) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			if (StringTools.notEmpty(jpSynopsis.getId())) {
				// 修改
				jpSynopsis.setUpdatetime(new Date());
				jpSynopsis.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				count = syservice.update(jpSynopsis);
			} else {
				//新增
				jpSynopsis.setDeleteflag(ConstantUtils.DELETE_FALSE);
				jpSynopsis.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				jpSynopsis.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				jpSynopsis.setId(UUIDUtils.getUUID());
				jpSynopsis.setUpdatetime(new Date());
				jpSynopsis.setCreatetime(new Date());
				count = syservice.insert(jpSynopsis);
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
	public JsonResponse batchDelete(String ids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			// a,b,c
			String id = ids.substring(0, ids.length());
			String idArray[] = id.split(",");
			syservice.batchDelete(idArray);
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

}
