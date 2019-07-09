package com.jp.service.impl;

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
import com.jp.entity.SysFunction;
import com.jp.entity.SysFunctionQuery;
import com.jp.entity.SysFunctionQuery.Criteria;
import com.jp.service.SysFunctionService;
import com.jp.util.StringTools;

@Service
public class SysFunctionServiceImpl implements SysFunctionService {

	private final Logger log_ = LogManager.getLogger(SysFunctionServiceImpl.class);

	@Autowired
	private SysFunctionDao sysFunctionDao;

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
		List<SysFunction> list = null;
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
			list = sysFunctionDao.selectByExample(sfq);
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
	public List<SysFunction> selectFunctionListByVersionid(String versionid) throws Exception {

		return sysFunctionDao.selectFunctionListByVersionid(versionid);
	}

	@Override
	public int insert(SysFunction sysFunction) throws Exception {

		return sysFunctionDao.insertSelective(sysFunction);
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
	public int update(SysFunction sysFunction) throws Exception {
		return sysFunctionDao.updateByPrimaryKeySelective(sysFunction);
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
}
