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
import com.jp.entity.IntroudceTemplate;
import com.jp.service.IntroudceTemplateService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/intemplate")
public class IntroudceTemplateController {
	@Autowired
	private IntroudceTemplateService inService;
	private final Logger log_ = LogManager.getLogger(IntroudceTemplateController.class);

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		IntroudceTemplate intemplate = null;
		try {
			String id = request.getParameter("id");
			intemplate = inService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(intemplate);
		return res;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<IntroudceTemplate> pageModel, IntroudceTemplate intemplate) {
		Result result = null;
		JsonResponse res = null;
		try {
			inService.pageQuery(pageModel, intemplate);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						inService.pageQuery(pageModel, intemplate);
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
		res.setCount(pageModel.getPageInfo().getTotal());
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(IntroudceTemplate intemplate) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			count = inService.changeStatus(intemplate);
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
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(IntroudceTemplate intemplate, ModelMap model, String Id) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			if (StringTools.notEmpty(intemplate.getId())) {
				// 修改
				intemplate.setUpdatetime(new Date());
				intemplate.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				count = inService.update(intemplate);
			} else {
				// 新增

				intemplate.setDeleteflag(ConstantUtils.DELETE_FALSE);
				intemplate.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				intemplate.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				intemplate.setId(UUIDUtils.getUUID());
				intemplate.setUpdatetime(new Date());
				intemplate.setCreatetime(new Date());
				count = inService.insert(intemplate);
			}
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
			String intemplateids = ids.substring(0, ids.length());
			String intemplateidsArray[] = intemplateids.split(",");
			inService.batchDelete(intemplateidsArray);
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * api模板书列表
	 */
	@RequestMapping(value = "/apilist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse namelist(PageModel<IntroudceTemplate> pageModel, IntroudceTemplate intemplate) {
		return inService.pageQueryApi(pageModel, intemplate);
	}
}
