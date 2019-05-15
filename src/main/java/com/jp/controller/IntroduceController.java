package com.jp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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
import com.jp.entity.Introduce;
import com.jp.service.IntroduceService;

@Controller
@RequestMapping("introduce")
public class IntroduceController {
	@Autowired
	private IntroduceService itservice;
	
	private final Logger log_ = LogManager.getLogger(IntroduceController.class);
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String itid = request.getParameter("introduceid");
			Introduce introduce = itservice.get(itid);
			model.put("introduce", introduce);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return "genealogy/introduce";
	}
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Introduce> pageModel,Introduce introduce){
		Result result = null;
		JsonResponse res = null;
		try {
			itservice.pageQuery(pageModel,introduce);
			if(pageModel.getList()!=null){
				if(pageModel.getPageSize()==0){
					if(pageModel.getPageNo()!=null&&!"1".equals(pageModel.getPageNo())){
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						itservice.pageQuery(pageModel,introduce);
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
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public String batchDelete(String introduceids){
    	String result=null;
    	try {
	 		String introduceid = introduceids.substring(0, introduceids.length());
	 		String introduceArray [] = introduceid.split(",");
	 		itservice.batchDelete(introduceArray);
	 		result="1";
	 	} catch (Exception e) {
	 		result = "0";
	 		e.printStackTrace();
	 		log_.error("[JPSYSTEM]", e);
	 	}
	    	return result;
	}
    
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveIntroduce(Introduce introduce,HttpServletRequest request)  {
		String result = null;
		try{
			if (request.getCharacterEncoding() == null) {
				request.setCharacterEncoding("UTF-8");
				}
			itservice.saveIntroduce(introduce);
		    result = "1";
		}catch(Exception e){
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}
}
