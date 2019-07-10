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
import com.jp.entity.SysVersion;
import com.jp.service.SysFunctionService;
import com.jp.service.SysVersionService;

@Controller
@RequestMapping("system/sysversion")
public class SysVersionController {

	private final Logger log_ = LogManager.getLogger(SysVersionController.class);

	@Autowired
	private SysVersionService sysVersionService;

	@Autowired
	private SysFunctionService sysFunctionService;

	/**
	 * 编辑或新增菜单的保存
	 * @param sysVersion
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(SysVersion sysVersion, HttpServletRequest request) {
		String functionids[] = request.getParameterValues("functionids[]");
		return sysVersionService.save(sysVersion, functionids);
	}

	/**
	 * 分页查询
	 * @param pageModel
	 * @param sysVersion
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<SysVersion> pageModel, SysVersion sysVersion) {
		Result result = null;
		JsonResponse res = null;
		try {
			sysVersionService.pageQuery(pageModel, sysVersion);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo() + "")) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						sysVersionService.pageQuery(pageModel, sysVersion);
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
	 * 新增版本和编辑回显
	 * @param versionid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String versionid) {
		return sysFunctionService.selectFunctionListByVersionid(versionid);
	}

	/**
	 * @描述 删除
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResponse delete(String versionid) {
		return sysVersionService.delete(versionid);
	}

}
