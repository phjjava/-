package com.jp.controller;

import java.util.Date;

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
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.UsercontentDao;
import com.jp.entity.Usercontent;
import com.jp.service.FamousService;
import com.jp.util.StringTools;

@Controller
@RequestMapping("famous")
public class FamousController {

	@Autowired
	private FamousService famousService;
	@Autowired
	private UsercontentDao usercontentDao;

	private final Logger log_ = LogManager.getLogger(FamousController.class);

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<Usercontent> pageModel, Usercontent usercontent, ModelMap model) {
		try {
			famousService.selectContentList(pageModel, usercontent);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						famousService.selectContentList(pageModel, usercontent);
					}
				}
			}
			model.put("pageModel", pageModel);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "famous/famousList";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String userid = request.getParameter("userid");
			Usercontent usercontent = famousService.get(userid);
			model.put("usercontent", usercontent);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return "famous/famous";
	}

	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public String changeStatus(Usercontent usercontent) {
		String result = null;
		try {
			Usercontent uc = usercontentDao.selectByPrimaryKey(usercontent.getUserid());
			uc.setIssee(usercontent.getIssee());
			usercontentDao.updateByPrimaryKey(uc);
			result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}

	/**
	 * 
	 * @描述 名人录saveORupdate
	 * @作者 jinlizhi
	 * @时间 2017年5月23日下午5:17:02
	 * @参数 @param usercontent
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Usercontent usercontent) {
		Integer result = null;
		try {
			if (StringTools.notEmpty(usercontent.getUserid())) {
				Usercontent searchUTResult = usercontentDao.selectByPrimaryKey(usercontent.getUserid());
				if (StringTools.notEmpty(searchUTResult)) {
					// 有记录存在为编辑修改
					usercontent.setUpdatetime(new Date());
					usercontent.setUpdateid(CurrentUserContext.getCurrentUserId());
					result = famousService.update(usercontent);
				} else {
					// 无记录存在为新增
					usercontent.setCreateid(CurrentUserContext.getCurrentUserId());
					usercontent.setUpdateid(CurrentUserContext.getCurrentUserId());
					usercontent.setUpdatetime(new Date());
					usercontent.setCreatetime(new Date());
					usercontent.setIssee(ConstantUtils.ISSEE_SHOW);
					result = famousService.insert(usercontent);
				}
			} else {
				// 用户userid为空
				result = 0;
			}
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}
	
	@ResponseBody
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public String batchDelete(String userids){
    	String result=null;
    	try {
 		//a,b,c 
 		String id = userids.substring(0, userids.length());
 		String dyidArray [] = id.split(",");
 		result = famousService.batchDelete(dyidArray)+"";
 		//result="1";
 	} catch (Exception e) {
 		result = "0";
 		e.printStackTrace();
 		log_.error("[JPSYSTEM]", e);
 	}
    	return result;
    }

}
