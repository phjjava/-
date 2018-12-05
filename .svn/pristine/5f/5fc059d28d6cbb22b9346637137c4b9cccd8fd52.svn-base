package com.jp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.entity.EditorialBoard;
import com.jp.service.EditorialBoardService;

import com.jp.util.GsonUtil;
import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("editorial")
public class EditorialControll {

	private final Logger log_ = LogManager.getLogger(EditorialControll.class);

	@Autowired
	private EditorialBoardService editorialBoardService;

	
	

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, EditorialBoard eb, ModelMap model) {
		Integer result = null;
		try {
			if (StringTools.notEmpty(eb.getId())) {// 修改
				result = editorialBoardService.update(eb);
			} else {// 新增
				eb.setId(UUIDUtils.getUUID());
				eb.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				eb.setType(0);
				result = editorialBoardService.insert(eb);
			}

		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<EditorialBoard> pageModel, EditorialBoard entity, ModelMap model) {
		try {
			entity.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			editorialBoardService.pageQuery(pageModel, entity);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						editorialBoardService.pageQuery(pageModel, entity);
					}
				}
			}
			model.put("pageModel", pageModel);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "editorial/editorialboardList";
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public Result deleteEditorialBoard(EditorialBoard entity) {
		Result result=new Result();
		try {
			int status =editorialBoardService.del(entity.getId());
			result.setData(status);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result.setData(0);
			return result;
		}
		return result;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {

			String id = request.getParameter("id");
			EditorialBoard eb = editorialBoardService.getEditorialBoard(id);
			model.put("editorial", eb);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "editorial/editorialboard";
	}

	/**
	 * @描述 根据用户获取编委会列表
	 * @作者 sj
	 * @时间 2017年5月5日上午11:03:44
	 * @参数 @param familyid
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/selecteditorialBoardList", method = RequestMethod.POST)
	public String selectRoleList() {
		String gsonStr = null;
		try {
			List<EditorialBoard> list = editorialBoardService.selecteditorialBoardList(CurrentUserContext.getCurrentUserId());
			gsonStr = GsonUtil.GsonString(list);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return gsonStr;
	}

}
