package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.entity.SysVersionPrivilege;
import com.jp.service.SysVersionPrivilegeService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class SysVersionPrivilegeServiceImpl implements SysVersionPrivilegeService {

	private final Logger log_ = LogManager.getLogger(SysVersionPrivilegeServiceImpl.class);

	@Autowired
	private SysVersionPrivilegeMapper sysVersionPrivilegeMapper;

	@Override
	public JsonResponse pageQuery(PageModel<SysVersionPrivilege> pageModel, SysVersionPrivilege sysVersionPrivilege) {
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
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<SysVersionPrivilege> list = sysVersionPrivilegeMapper.selectByRecord(sysVersionPrivilege);
			if (list != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(list);
				res.setCount(new PageInfo<SysVersionPrivilege>(list).getTotal());
				return res;
			}
		} catch (Exception e) {
			log_.error("[pageQuery方法---异常:]", e);
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
			SysVersionPrivilege sysVersionPrivilege = sysVersionPrivilegeMapper.selectByPrimaryKey(id);
			if (sysVersionPrivilege != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(sysVersionPrivilege);
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
	public JsonResponse delete(String id) {
		Result result = null;
		JsonResponse res = null;
		if (id == null || "".equals(id)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			int status = sysVersionPrivilegeMapper.deleteByPrimaryKey(id);
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
	public JsonResponse saveOrUpdate(SysVersionPrivilege sysVersionP) {
		Result result = null;
		JsonResponse res = null;
		int count = 0;
		if (sysVersionP.getVersionid() == null || "".equals(sysVersionP.getVersionid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数versionid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (sysVersionP.getVersionname() == null || "".equals(sysVersionP.getVersionname())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数versionname不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (sysVersionP.getPrivilegecode() == null || "".equals(sysVersionP.getPrivilegecode())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数privilegecode不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (sysVersionP.getPrivilegename() == null || "".equals(sysVersionP.getPrivilegename())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数privilegename不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (sysVersionP.getPrivilegevalue() == null || "".equals(sysVersionP.getPrivilegevalue())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数privilegevalue不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (sysVersionP.getSort() == null || "".equals(sysVersionP.getSort() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数sort不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			if (StringTools.notEmpty(sysVersionP.getId())) {
				sysVersionP.setUpdateby(CurrentSystemUserContext.getSystemUserContext().getUserid());
				sysVersionP.setUpdatetime(new Date());
				// 修改版本特权
				count = sysVersionPrivilegeMapper.updateByPrimaryKeySelective(sysVersionP);
			} else {
				sysVersionP.setId(UUIDUtils.getUUID());
				sysVersionP.setDeleteflag(0);
				sysVersionP.setCreateby(CurrentSystemUserContext.getSystemUserContext().getUserid());
				sysVersionP.setCreatetime(new Date());
				// 新增版本特权
				count = sysVersionPrivilegeMapper.insert(sysVersionP);
			}
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[saveOrUpdate方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

}
