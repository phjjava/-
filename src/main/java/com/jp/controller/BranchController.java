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
	public JsonResponse selectBranch(PageModel<Branch> pageModel, Branch branch) {
		return branchService.initBranch(pageModel, branch);
	}

	/**
	 * 验证同一家族中分支名称不能相同
	 * @param branchname
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validateBranchname", method = RequestMethod.POST)
	public JsonResponse validateBranchname(Branch branch) {
		return branchService.validateBranchname(branch);
	}

	/**
	 * 对发起人做出提示(此人是否已经发起过其他分支)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkBeginer", method = RequestMethod.POST)
	public JsonResponse checkBeginer(Branch branch) {
		return branchService.checkBeginer(branch);
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
	 * 获取指定分支的世系表 - 用户世系表（列表数据+递归查询 -Android用）
	 * 
	 * @param branch
	 * @param isManager 不为空则为管理员
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListOnly", method = RequestMethod.GET)
	public JsonResponse getGenListOnly(Branch branch, String isManager) {
		return branchService.getGenListOnly(branch, isManager);
	}

	/**
	 * 向上查找指定的几代 - 追根溯源指定分支的世系表（列表结构 -Android用）
	 * 
	 * @param branch
	 * @param isManager 不为空则为管理员
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListToTop", method = RequestMethod.GET)
	public JsonResponse getGenListToTop(Branch branch, String isManager) {
		return branchService.getGenListToTop(branch, isManager);
	}

	/**
	 * 获取指定分支的世系表 - 用户世系表 (层次结构数据+递归查询- iOS用)
	 * @param branch
	 * @param isManager 不为空则为管理员
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenList", method = RequestMethod.GET)
	public JsonResponse getGenList(Branch branch, String isManager) {
		return branchService.getGenList(branch, isManager);
	}

	/**
	 * 向上查找指定的几代 - 追根溯源指定分支的世系表（层级结构 -ios用）
	 * 
	 * @param branch
	 * @param isManager 不为空则为管理员
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGenListToCount", method = RequestMethod.GET)
	public JsonResponse getGenListToCount(Branch branch, String isManager) {
		return branchService.getGenListToCount(branch, isManager);
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

	/**
	 * 根据用户id获取所管理的分支列表（编委会权限）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchsByUserid", method = RequestMethod.GET)
	public JsonResponse getBranchsByUserid(String userid, String code, Integer pageNo, Integer pageSize) {
		return branchService.getBranchsByUserid(userid, code, pageNo, pageSize);
	}

	/**
	 * 获取家族管理中有效的省份信息 - 省份列表（根据编委会获取）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEbArea", method = RequestMethod.GET)
	public JsonResponse getEbArea(String userid) {
		Branch branch = new Branch();
		branch.setBeginuserid(userid);
		return branchService.getEbArea(branch);
	}

	/**
	 * 获取地区和分支信息 - 省份列表（根据编委会获取）
	 * 
	 * @param branch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getXQAndBranch", method = RequestMethod.GET)
	public JsonResponse getXQAndBranch(Branch entity) {

		return branchService.getXQAndBranch(entity);
	}
}
