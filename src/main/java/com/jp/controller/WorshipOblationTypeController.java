package com.jp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.WorshipOblationType;
import com.jp.service.WorshipOblationTypeService;

@Controller
@RequestMapping("system/oblationType")
public class WorshipOblationTypeController {
	private final Logger log_ = LogManager.getLogger(WorshipOblationTypeController.class);

	@Resource
	private WorshipOblationTypeService worshipOblationService;

	/**
	 * 新增或编辑保存
	 * @param oblationType
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(WorshipOblationType oblationType, ModelMap model) {
		return worshipOblationService.save(oblationType);
	}

	/**
	 * 祭品分类管理列表
	 * @param pageModel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<WorshipOblationType> pageModel, ModelMap model) {
		return worshipOblationService.pageQuery(pageModel);
	}

	@RequestMapping(value = "/childlist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse childlist(String oblationtypeid) {
		return worshipOblationService.selectListByParnetid(oblationtypeid);
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public String deleteEditorialBoard(WorshipOblationType entity) {
		Integer result = 0;
		try {
			result = worshipOblationService.del(entity);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			WorshipOblationType oblationType = new WorshipOblationType();
			if (id != null && !"".equals(id)) {
				oblationType = worshipOblationService.getOblationTypeById(id);
			}

			model.put("oblationType", oblationType);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}

		return "system/worship/oblationTypeAdd";
	}
}
