package com.jp.controller;

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
import com.jp.entity.SysFunction;
import com.jp.service.SysFunctionService;

@Controller
@RequestMapping("system/sysfunction")
public class SysFunctionController {

	private final Logger log_ = LogManager.getLogger(SysFunctionController.class);

	@Autowired
	private SysFunctionService sysFunctionService;

	/**
	 * 编辑或新增菜单的保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse save(SysFunction sysFunction) {
		return sysFunctionService.save(sysFunction);
	}

	/**
	 * @描述 分页查询
	 */
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

	/**
	 * @描述 根据父节点查询子菜单集合
	 */
	@RequestMapping(value = "/childlist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse childlist(String parentid) {
		return sysFunctionService.selectListByParnetid(parentid);
	}

	/**
	 * @描述 单个查询
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String functionid) {
		return sysFunctionService.get(functionid);
	}

	/**
	 * @描述 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse delete(String functionid) {
		return sysFunctionService.delete(functionid);
	}

}
