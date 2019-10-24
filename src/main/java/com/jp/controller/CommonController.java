package com.jp.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.entity.Branch;
import com.jp.service.BranchService;
import com.jp.util.StringTools;
import com.jp.util.WebUtil;

/**
 * @功能 基础控制器 获取数据权限
 * @作者 wumin
 * @时间 2017年5月18日上午10:54:41
 */
@Controller
@RequestMapping("common")
public class CommonController {

	private final Logger log_ = LogManager.getLogger(CommonController.class);

	@Autowired
	private BranchService branchService;

	/**
	 * @描述 获取当前登录用户的分支权限
	 * @作者 wumin
	 * @时间 2017年5月18日上午11:12:49
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/currentBranchJson", method = RequestMethod.POST)
	public JsonResponse currentBranchJson() {
		Result result = null;
		JsonResponse res = null;
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人所管理的编委会id
		String ebid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_EBID);
		if (StringTools.isEmpty(ebid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数ebid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			List<Branch> branchList = branchService.selectBranchListByFamilyAndUserid(familyid, userid, ebid);
			if (branchList.size() > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(branchList);
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
		result.setMsg("当前用户没有分支!");
		res = new JsonResponse(result);
		return res;
	}

}
