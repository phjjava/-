package com.jp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.SysFunctionDao;
import com.jp.dao.SysVersionDao;
import com.jp.entity.SysFunction;
import com.jp.entity.SysFunctionQuery;
import com.jp.entity.SysFunctionQuery.Criteria;
import com.jp.entity.SysVersion;
import com.jp.service.SysFunctionService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class SysFunctionServiceImpl implements SysFunctionService {

	private final Logger log_ = LogManager.getLogger(SysFunctionServiceImpl.class);

	@Autowired
	private SysFunctionDao sysFunctionDao;
	@Autowired
	private SysVersionDao sysVersionDao;

	@Override
	public PageModel<SysFunction> pageQuery(PageModel<SysFunction> pageModel, SysFunction sysFunction)
			throws Exception {

		SysFunctionQuery sfq = new SysFunctionQuery();

		Criteria createCriteria = sfq.createCriteria();

		if (StringTools.trimNotEmpty(sysFunction.getFunctionname())) {
			createCriteria.andFunctionnameLike("%" + sysFunction.getFunctionname().trim() + "%");
		}
		if (StringTools.notEmpty(sysFunction.getParentid())) {
			createCriteria.andParentidEqualTo(sysFunction.getParentid());
		}
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		sfq.setOrderByClause(" sort desc");
		List<SysFunction> list = sysFunctionDao.selectByExample(sfq);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<SysFunction>(list));
		return pageModel;
	}

	@Override
	public JsonResponse selectListByParnetid(String parentid) {
		Result result = null;
		JsonResponse res = null;
		if (parentid == null || "".equals(parentid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数parentid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		SysFunctionQuery sfq = new SysFunctionQuery();
		sfq.or().andParentidEqualTo(parentid);
		sfq.setOrderByClause(" sort desc");
		try {
			List<SysFunction> list = sysFunctionDao.selectByExample(sfq);
			if (list != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(list);
				return res;
			}
		} catch (Exception e) {
			log_.error("[selectListByParnetid方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse selectFunctionListByVersionid(String versionid) {
		Result result = null;
		JsonResponse res = null;
		List<SysFunction> treeList = new ArrayList<SysFunction>();
		SysVersion sysVersion = new SysVersion();
		try {
			//获取版本详情
			sysVersion = sysVersionDao.selectByPrimaryKey(versionid);
			//根据版本id查询功能
			List<SysFunction> functionList = sysFunctionDao.selectFunctionListByVersionid(versionid);
			if (functionList != null) {
				SysFunction sysFunction = new SysFunction();
				// 先找到所有的一级菜单
				for (int i = 0; i < functionList.size(); i++) {
					sysFunction = functionList.get(i);
					// 一级菜单父ID为00000
					if ("00000".equals(sysFunction.getParentid())) {
						treeList.add(sysFunction);
					}
				}
			}
			// 为一级菜单设置子菜单，getChild是递归调用的
			for (SysFunction sysFun : treeList) {
				sysFun.setChildList(getChild(sysFun.getFunctionid(), functionList));
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
		res.setData(treeList);
		res.setEntity(sysVersion);
		return res;
	}

	/**
	 * 子菜单递归
	 * 
	 * @param id
	 * @param rootMenu
	 * @return
	 */
	private List<SysFunction> getChild(String id, List<SysFunction> rootFun) {
		// 子菜单
		List<SysFunction> childList = new ArrayList<>();
		for (SysFunction sysFun : rootFun) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (sysFun.getParentid().equals(id)) {
				childList.add(sysFun);
			}
		}

		// 把子菜单的子菜单再循环一遍
		for (SysFunction fun : childList) {
			fun.setChildList(getChild(fun.getFunctionid(), rootFun));// 递归
		}
		// 判断递归结束
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}

	@Override
	public JsonResponse delete(String functionid) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (functionid == null || "".equals(functionid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数functionid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			status = sysFunctionDao.deleteByPrimaryKey(functionid);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[delete方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse get(String functionid) {
		Result result = null;
		JsonResponse res = null;
		SysFunction sysFunction = null;
		if (functionid == null || "".equals(functionid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数functionid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			sysFunction = sysFunctionDao.selectByPrimaryKey(functionid);
			if (sysFunction != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(sysFunction);
				return res;
			}
		} catch (Exception e) {
			log_.error("[get方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse save(SysFunction sysFunction) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (sysFunction.getFunctionname() == null || "".equals(sysFunction.getFunctionname())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数functionname不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (sysFunction.getFunctionurl() == null || "".equals(sysFunction.getFunctionurl())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数functionurl不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (sysFunction.getSort() == null || "".equals(sysFunction.getSort() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数sort不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			if (StringTools.notEmpty(sysFunction.getFunctionid())) {//修改
				sysFunction.setUpdateid("sys_admin");
				sysFunction.setUpdatetime(new Date());
				status = sysFunctionDao.updateByPrimaryKeySelective(sysFunction);
			} else {//新增
				sysFunction.setCreateid("sys_admin");
				sysFunction.setCreatetime(new Date());
				if (!StringTools.notEmpty(sysFunction.getParentid())) {
					sysFunction.setParentid("00000");
				}
				sysFunction.setFunctionid(UUIDUtils.getUUID());
				status = sysFunctionDao.insertSelective(sysFunction);
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[save方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}
}
