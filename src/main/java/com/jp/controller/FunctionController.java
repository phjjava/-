package com.jp.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jp.common.PageModel;
import com.jp.entity.Function;
import com.jp.service.FunctionService;

@Controller
@RequestMapping("function")
public class FunctionController {
	
	private final Logger log_ = LogManager.getLogger(FunctionController.class);
	
	@Autowired
	private FunctionService functionService;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<Function> pageModel, ModelMap model) {
		try {
			Function function = new Function();
			functionService.pageQuery(pageModel,function);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo()+"")) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						functionService.pageQuery(pageModel,function);
					}
				}
			}
			model.put("pageModel", pageModel);
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "system/functionList";
	}
	

}
