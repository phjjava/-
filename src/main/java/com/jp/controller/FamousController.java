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

import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.UserDao;
import com.jp.dao.UsercontentDao;
import com.jp.entity.User;
import com.jp.entity.Usercontent;
import com.jp.service.FamousService;

@Controller
@RequestMapping("famous")
public class FamousController {

	@Autowired
	private FamousService famousService;
	@Autowired
	private UsercontentDao usercontentDao;
	@Autowired
	private UserDao userDao;

	private final Logger log_ = LogManager.getLogger(FamousController.class);

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public JsonResponse list(PageModel<Usercontent> pageModel, Usercontent usercontent, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			famousService.selectContentList(pageModel, usercontent);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo() + "")) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						famousService.selectContentList(pageModel, usercontent);
					}
				}
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(pageModel.getList());
			res.setCount(pageModel.getPageInfo().getTotal());
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public JsonResponse get(HttpServletRequest request, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = request.getParameter("userid");
			Usercontent usercontent = famousService.get(userid);
			// 增加回写branchid，branchname，address，genlevel，username字段
			if (usercontent != null) {
				User user = userDao.selectByPrimaryKey(userid);
				usercontent.setAddress(userDao.getAddressByUserid(userid));
				usercontent.setUsername(user.getUsername());
				usercontent.setGenlevel(user.getGenlevel() + "世");
				usercontent.setBranchid(user.getBranchid());
				usercontent.setBranchname(user.getBranchname());
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(usercontent);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(Usercontent usercontent) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			Usercontent uc = usercontentDao.selectByPrimaryKey(usercontent.getUserid());
			uc.setIssee(usercontent.getIssee());
			count = usercontentDao.updateByPrimaryKey(uc);
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 新增或修改名人录
	 * @param usercontent
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse editOrAdd(Usercontent usercontent, String id) {
		return famousService.editOrAdd(usercontent, id);
	}

	@ResponseBody
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public JsonResponse batchDelete(String userids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		int count = 0;
		try {
			//a,b,c 
			String id = userids.substring(0, userids.length());
			String dyidArray[] = id.split(",");
			count = famousService.batchDelete(dyidArray);
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}

}
