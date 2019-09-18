package com.jp.controller;

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
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.EditorialBoard;
import com.jp.service.EditorialBoardService;
import com.jp.util.StringTools;
import com.jp.util.WebUtil;

@Controller
@RequestMapping("editorial")
public class EditorialControll {

	private final Logger log_ = LogManager.getLogger(EditorialControll.class);

	@Autowired
	private EditorialBoardService editorialBoardService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse save(EditorialBoard eb) {
		return editorialBoardService.save(eb);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<EditorialBoard> pageModel, EditorialBoard entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			if (StringTools.isEmpty(familyid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("header中参数familyid为空!");
				res = new JsonResponse(result);
				return res;
			}
			entity.setFamilyid(familyid);
			editorialBoardService.pageQuery(pageModel, entity);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && 1 != pageModel.getPageNo()) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						editorialBoardService.pageQuery(pageModel, entity);
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

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteEditorialBoard(EditorialBoard entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			int status = editorialBoardService.del(entity.getId());
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		EditorialBoard eb = null;
		try {
			String id = request.getParameter("id");
			eb = editorialBoardService.getEditorialBoard(id);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		if (eb == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(eb);
		return res;
	}

	/**
	 * @描述 根据用户获取编委会列表
	 * @作者 sj
	 * @时间 2017年5月5日上午11:03:44
	 * @参数 @param familyid
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/selecteditorialBoardList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse selectRoleList() {
		return editorialBoardService.selecteditorialBoardList();
	}

}
