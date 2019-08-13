package com.jp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Branch;
import com.jp.service.BranchService;

@Controller
@RequestMapping("branch")
public class BranchController {

	@Autowired
	private BranchService branchService;

	/**
	 * 保存新增或编辑分支
	 * @param branch
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(Branch branch, ModelMap model) {
		return branchService.save(branch);
	}

	/**
	 * 分页查询分支
	 * @param pageModel
	 * @param branch
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Branch> pageModel, Branch branch) {
		return branchService.pageQuery(pageModel, branch);
	}

	/**
	 * 分支回显
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(String branchid) {
		return branchService.get(branchid);
	}

	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(Branch branch) {
		return branchService.changeStatus(branch);
	}

	/**
	 * 新增用户初始化父亲 和 配偶
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initBranch", method = RequestMethod.POST)
	public JsonResponse selectBranch(Branch branch) {
		return branchService.initBranch(branch);
	}

	/**
	 * 验证同以家族中分支名称不能相同
	 * @param branchname
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validateBranchname", method = RequestMethod.POST)
	public JsonResponse validateBranchname(String branchname) {
		return branchService.validateBranchname(branchname);
	}

	/**
	 * 对发起人做出提示(此人是否已经发起过其他分支)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkBeginer", method = RequestMethod.POST)
	public JsonResponse checkBeginer(String beginuserid) {
		return branchService.checkBeginer(beginuserid);
	}

	/**
	 * 以下方法用于api
	 */

	/**
	 * 获取所有分支信息列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllBranch", method = RequestMethod.GET)
	public JsonResponse getAllBranch(Branch branch) {
		return branchService.getAllBranch(branch);
	}

	/**
	 * 获取某分支下的所有人员 - 分支内家族成员通讯录
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchPersons", method = RequestMethod.GET)
	public JsonResponse getBranchPersons(Branch branch) {
		return branchService.getBranchPersons(branch);
	}

	/**
	 * 获取动态、公告等筛选条件中的有效的省份信息 - 省份列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildArea", method = RequestMethod.GET)
	public JsonResponse getBranchVaildArea(Branch branch) {
		return branchService.getBranchVaildArea(branch);
	}

	/**
	 * 获取有效的省份和所在城市列表 - 省份和城市列表（组合）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildAreaAndCity", method = RequestMethod.GET)
	public JsonResponse getBranchVaildAreaAndCity(Branch branch) {
		return branchService.getBranchVaildAreaAndCity(branch);
	}

	/**
	 * 获取有效的县区和分支列表 - 县区和分支列表（组合）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildXQAndBranch", method = RequestMethod.GET)
	public JsonResponse getBranchVaildXQAndBranch(Branch branch) {
		return branchService.getBranchVaildXQAndBranch(branch);
	}

	/**
	 * 获取动态、公告等筛选条件中的有效的城市信息 - 城市列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildCity", method = RequestMethod.GET)
	public JsonResponse getBranchVaildCity(Branch branch) {
		return branchService.getBranchVaildCity(branch);
	}

	/**
	 * 获取动态、公告等筛选条件中的有效的县区信息 - 县区列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchVaildXQ", method = RequestMethod.GET)
	public JsonResponse getBranchVaildXQ(Branch branch) {
		return branchService.getBranchVaildXQ(branch);
	}

	/**
	 * 获取某县区下的分支列表 - 分支列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchOfXQ", method = RequestMethod.POST)
	public JsonResponse getBranchOfXQ(Branch branch) {
		return branchService.getBranchOfXQ(branch);
	}

	/**
	 * 获取某城市下的分支列表
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchByCitycode", method = RequestMethod.POST)
	public JsonResponse getBranchByCitycode(Branch branch) {
		return branchService.getBranchByCitycode(branch);
	}

	/**
	 * 获取指定分支的世系表（结构化数据列表） - 获取指定分支的世系表 (iOS 用)（层次结构数据）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenList", method = RequestMethod.GET)
	public JsonResponse getGenList(Branch branch, HttpServletRequest request) {
		String userid = request.getHeader("userid");
		branch.setParentid(userid);
		return branchService.getGenList(branch);
	}

	/**
	 * 获取指定分支的世系表（仅列表：递归） - 获取指定分支的世系表（列表数据+递归查询）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListOnly", method = RequestMethod.GET)
	public JsonResponse getGenListOnly(Branch branch) {
		return branchService.getGenListOnly(branch);
	}

	/**
	 * 获取指定分支的世系表（仅列表：无递归） - 获取指定分支的世系表(Android用)（列表数据+批量查询）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListOnlyExt", method = RequestMethod.GET)
	public JsonResponse getGenListOnlyExt(Branch branch, HttpServletRequest request) {
		String userid = request.getHeader("userid");
		branch.setParentid(userid);
		return branchService.getGenListOnlyExt(branch);
	}

	/**
	 * 向上查找指定的几代 - 追根溯源指定分支的世系表（追根溯源-列表结构）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListToTop", method = RequestMethod.GET)
	public JsonResponse getGenListToTop(Branch branch) {
		return branchService.getGenListToTop(branch);
	}

	/**
	 * 五世一展示，从当前节点向上查到第一个被5整除的世系根节点，向下查到第一个被5整除的世系 - 追根溯源指定分支的世系表（追根溯源--五世一表）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListOnlyExtMod", method = RequestMethod.GET)
	public JsonResponse getGenListOnlyExtMod(Branch branch) {
		return branchService.getGenListOnlyExtMod(branch);
	}

}
