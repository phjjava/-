package com.jp.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.IndexMapper;
import com.jp.dao.UserDao;
import com.jp.entity.Index;
import com.jp.entity.IndexExample;
import com.jp.entity.SysUser;
import com.jp.service.FunctionMenuService;
import com.jp.util.UUIDUtils;

@Service
public class FunctionMenuServiceImpl implements FunctionMenuService {
	private final Logger log_ = LogManager.getLogger(FunctionMenuServiceImpl.class);

	@Resource
	private IndexMapper indexMapper;
	@Resource
	private UserDao userMapper;

	@Override
	public JsonResponse pageQuery(PageModel<Index> pageModel) {
		Result result = null;
		JsonResponse res = null;
		if (pageModel.getPageNo() == null || "".equals(pageModel.getPageNo() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageNo不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (pageModel.getPageSize() == null || "".equals(pageModel.getPageSize() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageSize不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			IndexExample example = new IndexExample();
			//			example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			example.setOrderByClause("sort asc");
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<Index> list = indexMapper.selectByExample(example);
			if (list != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(list);
				res.setCount(new PageInfo<Index>(list).getTotal());
				return res;
			}
		} catch (Exception e) {
			log_.error("[FunctionMenuServiceImpl---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;

	}

	@Override
	public JsonResponse save(Index index) {
		Result result = null;
		JsonResponse res = null;
		Integer status = 0;
		if (index.getImgurl() == null || "".equals(index.getImgurl())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数imgurl不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (index.getName() == null || "".equals(index.getName())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数name不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (index.getCode() == null || "".equals(index.getCode())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数code不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (index.getSort() == null || "".equals(index.getSort() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数sort不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			SysUser user = CurrentSystemUserContext.getSystemUserContext();
			if (StringUtils.isEmpty(index.getId())) {
				//新增
				index.setId(UUIDUtils.getUUID());
				//User user = userMapper.selectByPrimaryKey(userid);
				Date date = new Date();
				index.setCreateid(user.getUserid());
				index.setCreatename(user.getName());
				index.setCreatetime(date);
				index.setDeleteflag(ConstantUtils.DELETE_FALSE);
				status = indexMapper.insertSelective(index);
			} else {
				//编辑
				Date date = new Date();
				index.setUpdateid(user.getUserid());
				index.setUpdatename(user.getName());
				index.setUpdatetime(date);
				status = indexMapper.updateByPrimaryKeySelective(index);
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

	@Override
	public JsonResponse get(String id) {
		Result result = null;
		JsonResponse res = null;
		if (id == null || "".equals(id)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			Index index = indexMapper.selectByPrimaryKey(id);
			if (index != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(index);
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
	public JsonResponse batchDelete(String menuids) {
		Result result = null;
		JsonResponse res = null;
		if (menuids == null || "".equals(menuids)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数menuids不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			String bannerid = menuids.substring(0, menuids.length());
			String menuArray[] = bannerid.split(",");
			List<String> list = Arrays.asList(menuArray);
			IndexExample example = new IndexExample();
			example.or().andIdIn(list);
			int status = indexMapper.deleteByExample(example);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[batchDelete方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse delete(String menuid) {
		Result result = null;
		JsonResponse res = null;
		if (menuid == null || "".equals(menuid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数menuid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			int status = indexMapper.deleteByPrimaryKey(menuid);
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
	public JsonResponse changeStatus(String menuid, Integer status) {
		Result result = null;
		JsonResponse res = null;
		if (menuid == null || "".equals(menuid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数menuid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (status == null || "".equals(status + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数status不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			Index index = new Index();
			index.setId(menuid);
			index.setDeleteflag(status);
			int count = indexMapper.updateByPrimaryKeySelective(index);
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[changeStatus方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

}
