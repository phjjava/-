package com.jp.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.Banner;
import com.jp.entity.Introduce;
import com.jp.entity.IntroudceTemplate;
import com.jp.entity.IntroudceTemplateDetail;
import com.jp.entity.MationType;
import com.jp.service.IntroudceTemplateDetailService;
import com.jp.service.IntroudceTemplateService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/intemplateDetail")
public class IntroudceTemplateDetailController {
	@Autowired
	private IntroudceTemplateDetailService inService;
	private final Logger log_ = LogManager.getLogger(IntroudceTemplateDetailController.class);

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		IntroudceTemplateDetail intemplateDetail = null;
		try {
			String id = request.getParameter("id");
			intemplateDetail = inService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(intemplateDetail);
		return res;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<IntroudceTemplateDetail> pageModel, IntroudceTemplateDetail intemplateDetail,String title,String templatename,Integer deleteflag) {
		Result result = null;
		JsonResponse res = null;
		try {
			inService.pageQuery(pageModel, intemplateDetail,title,templatename,deleteflag);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						inService.pageQuery(pageModel, intemplateDetail,title,templatename,deleteflag);
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
		res.setCount(inService.selectCount());
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(IntroudceTemplateDetail intemplateDetail) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			count = inService.changeStatus(intemplateDetail);
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
	public JsonResponse save(IntroudceTemplateDetail intemplateDetail, ModelMap model,String Id) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			if (StringTools.notEmpty(intemplateDetail.getId())) {
				// 修改
				intemplateDetail.setUpdatetime(new Date());
				intemplateDetail.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				count = inService.update(intemplateDetail);
			} else {
				// 新增
				
				intemplateDetail.setDeleteflag(ConstantUtils.DELETE_FALSE);
				intemplateDetail.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				intemplateDetail.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				intemplateDetail.setId(UUIDUtils.getUUID());
				intemplateDetail.setUpdatetime(new Date());
				intemplateDetail.setCreatetime(new Date());
				count = inService.insert(intemplateDetail);
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
	 * 调取下拉框类型值（增加修改时调取使用）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selecttypelist", method = RequestMethod.POST)
    public JsonResponse selecttypelist()  {
		Result result = null;
		JsonResponse res = null;
    	List<IntroudceTemplate> gotypeList = null;
    	try {
    		gotypeList = inService.selecttypelist();
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
	 * api模板书章节目录
	 */
	@RequestMapping(value = "/apiFindList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse apiFindList(String id) {
		Result result = null;
		JsonResponse res = null;
		List<IntroudceTemplateDetail> detail=null;
		try {
			detail=inService.apiFindList(id);
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			res.setData(detail);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(detail);
		return res;
	}
	/**
	 * api详情模板书章节详情
	 */
	@RequestMapping(value = "/apiFindOne", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse apiFindOne(String id) {
		Result result = null;
		JsonResponse res = null;
		IntroudceTemplateDetail detail=null;
		try {
			detail=inService.apiFindOne(id);
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			res.setData(detail);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(detail);
		return res;
	}
}
