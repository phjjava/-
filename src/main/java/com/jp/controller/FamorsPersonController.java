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
import com.jp.entity.FamousPerson;
import com.jp.entity.SysUser;
import com.jp.service.FamousPersonService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/famousperson")
public class FamorsPersonController {
	private final Logger log_ = LogManager.getLogger(FamorsPersonController.class);
	@Autowired
	private FamousPersonService personservice;
	@RequestMapping(value = "/famouspersonlist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse homepagelist(PageModel<FamousPerson> pageModel, FamousPerson famousPerson, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			personservice.pageQuery(pageModel, famousPerson);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						personservice.pageQuery(pageModel, famousPerson);
					}
				}
			}

			//获取是否登录
			SysUser loginStatus = null;
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
			String famousid = request.getParameter("famousid");
			FamousPerson famousPerson = personservice.get(famousid);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(famousPerson);
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
	public JsonResponse changeStatus(FamousPerson famousPerson) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			count = personservice.changeStatus(famousPerson);
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
	public JsonResponse save(FamousPerson famousPerson, ModelMap model,String famousid) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			if (StringTools.notEmpty(famousPerson.getFamousid())) {
				// 修改
				famousPerson.setUpdatetime(new Date());
				famousPerson.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				
				count = personservice.update(famousPerson);
			} else {
				//新增
				famousPerson.setDeleteflag(ConstantUtils.DELETE_FALSE);
				famousPerson.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				famousPerson.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				famousPerson.setFamousid(UUIDUtils.getUUID());
				famousPerson.setUpdatetime(new Date());
				famousPerson.setCreatetime(new Date());
				
				count = personservice.insert(famousPerson);
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
	@RequestMapping(value = "/realDeleteAll", method = RequestMethod.POST)
	public JsonResponse batchDeleteAll(String famousids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			// a,b,c
			String famousid = famousids.substring(0, famousids.length());
			String famousidArray[] = famousid.split(",");
			personservice.batchDeleteAll(famousidArray);
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
	
	/**
	 * api接口
	 * 名人家谱列表接口
	 * @param pageModel
	 * @return
	 */
	@RequestMapping(value = "/apilist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse namelist(PageModel<FamousPerson> pageModel,  FamousPerson famousPerson) {
		return personservice.pageQueryApi(pageModel, famousPerson);
	}
}
