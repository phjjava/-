package com.jp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.PageModel;
import com.jp.entity.SysVersion;
import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationType;
import com.jp.service.WorshipOblationTypeService;
import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/oblationType")
public class WorshipOblationTypeController {
	private final Logger log_ = LogManager.getLogger(WorshipOblationTypeController.class);
	
	@Resource
	private WorshipOblationTypeService worshipOblationService;
	
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(WorshipOblationType oblationType, ModelMap model) {
		Integer result = null;
		try {
			if(StringTools.notEmpty(oblationType.getId())){//修改
				
				result = worshipOblationService.update(oblationType);
			}else{//新增
				oblationType.setDeleteflag(ConstantUtils.DELETE_FALSE);// 使用中
				
				
				oblationType.setId(UUIDUtils.getUUID());
				result = worshipOblationService.insert(oblationType);
			}

		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<WorshipOblationType> pageModel, ModelMap model) {
		try {
			worshipOblationService.pageQuery(pageModel);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						worshipOblationService.pageQuery(pageModel);
					}
				}
			}
			model.put("pageModel", pageModel);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "system/worship/oblationTypeList";
	}
	
	@RequestMapping(value = "/childlist", method = RequestMethod.POST)
	public String childlist(HttpServletRequest request, ModelMap model) {
		try {
			
			String oblationtypeid  = request.getParameter("oblationtypeid");
			List<WorshipOblation> childList = worshipOblationService.selectListByParnetid(oblationtypeid);
					
			model.put("childList", childList);
			model.put("oblationtypeid", oblationtypeid);
			

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "system/worship/oblationTypeChildList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public String deleteEditorialBoard(WorshipOblationType entity) {
		Integer result= 0;
		try {
			result =worshipOblationService.del(entity);
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result+"";
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			WorshipOblationType oblationType = new WorshipOblationType();
			if(id !=null && !"".equals(id)) {
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
