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
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.JpXing;
import com.jp.service.JpXingService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/xing")
public class JpXingController {
	private final Logger log_ = LogManager.getLogger(JpXingController.class);
	@Autowired
	private JpXingService xingService;
	/**
	 * 百家姓管理
	 * @param pageModel
	 * @param xing
	 * @param xname
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<JpXing> pageModel, JpXing xing,String xname) {
		Result result = null;
		JsonResponse res = null;
		try {
			xingService.pageQuery(pageModel, xing,xname);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						xingService.pageQuery(pageModel, xing,xname);
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
		
		if(pageModel.getList()==null) {
			res.setCount(0);
		}else {
			if(xname.equals("ALL")) {
				xname=null;
				res.setCount(xingService.SelectCount(xname));
			}else {
				res.setCount(xingService.SelectCount(xname));
			}
			
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(JpXing jpxing, ModelMap model,String Id) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		
		try {
			if (StringTools.notEmpty(jpxing.getId())) {
				// 修改
				jpxing.setUpdatetime(new Date());
				jpxing.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				count = xingService.update(jpxing);
			} else {
				// 新增
					jpxing.setDeleteflag(ConstantUtils.DELETE_FALSE);
					jpxing.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
					jpxing.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
					jpxing.setId(UUIDUtils.getUUID());
					jpxing.setUpdatetime(new Date());
					jpxing.setCreatetime(new Date());
					jpxing.setRcount(0);
					count = xingService.insert(jpxing);
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
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			String id = request.getParameter("id");
			JpXing xing = xingService.get(id);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(xing);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return res;
	}
	@ResponseBody
	@RequestMapping(value = "/xingDeleteAll", method = RequestMethod.POST)
	public JsonResponse noticeDeleteAll(String ids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			// a,b,c
			String xingid = ids.substring(0, ids.length());
			//按逗号截取放入数组
			String xingtypeArray[] = xingid.split(",");
			xingService.xingDeleteAll(xingtypeArray);
			//返回成功
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
	 */
	//热门姓氏
	@RequestMapping(value = "/hotlist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse hotlist() {
		Result result = null;
		JsonResponse res = null;
		try {
			List<JpXing> list= xingService.hotlist();
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(list);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return res;
	}
	
	/**
	 * api百家姓列表接口
	 * @param pageModel
	 * @param xing
	 * @param xname
	 * @return
	 */
	@RequestMapping(value = "/namelist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse namelist(PageModel<JpXing> pageModel, JpXing xing,String xname) {
		return xingService.pageQuery1(pageModel, xing,xname);
	}
}
