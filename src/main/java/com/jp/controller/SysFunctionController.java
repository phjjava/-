package com.jp.controller;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.SysFunction;
import com.jp.service.SysFunctionService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/sysfunction")
public class SysFunctionController {

	private final Logger log_ = LogManager.getLogger(SysFunctionController.class);

	@Autowired
	private SysFunctionService sysFunctionService;

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(SysFunction sysFunction, ModelMap model) {
		Integer result = null;
		try {
			if (StringTools.notEmpty(sysFunction.getFunctionid())) {//修改
				sysFunction.setUpdateid("sys_admin");
				sysFunction.setUpdatetime(new Date());
				result = sysFunctionService.update(sysFunction);
			} else {//新增
				sysFunction.setStatus(0);// 使用中
				sysFunction.setCreateid("sys_admin");
				if (!StringTools.notEmpty(sysFunction.getParentid())) {
					sysFunction.setParentid("00000");
				}
				sysFunction.setFunctionid(UUIDUtils.getUUID());
				result = sysFunctionService.insert(sysFunction);
			}

		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<SysFunction> pageModel) {
		Result result = null;
		JsonResponse res = null;
		try {
			SysFunction sysFunction = new SysFunction();
			sysFunction.setParentid("00000");

			sysFunctionService.pageQuery(pageModel, sysFunction);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo() + "")) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						sysFunctionService.pageQuery(pageModel, sysFunction);
					}
				}
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModel.getList());
			res.setCount(pageModel.getPageInfo().getTotal());
			return res;
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@RequestMapping(value = "/childlist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse childlist(String parentid) {
		return sysFunctionService.selectListByParnetid(parentid);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String functionid) {
		return sysFunctionService.get(functionid);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse delete(String functionid) {
		return sysFunctionService.delete(functionid);
	}

}
