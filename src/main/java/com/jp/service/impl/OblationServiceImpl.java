package com.jp.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.WorshipOblationMapper;
import com.jp.dao.WorshipOblationTypeMapper;
import com.jp.entity.SysUser;
import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationType;
import com.jp.service.OblationService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service("oblationServiceImpl")
public class OblationServiceImpl implements OblationService {
	private final Logger log_ = LogManager.getLogger(OblationServiceImpl.class);

	@Resource
	private WorshipOblationMapper oblationMapper;
	@Resource
	private WorshipOblationTypeMapper oblationTypeMapper;

	@Override
	public JsonResponse save(WorshipOblation oblation) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (oblation.getOblation() == null || "".equals(oblation.getOblation())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数oblation不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (oblation.getDuration() == null || "".equals(oblation.getDuration())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数duration不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (oblation.getPrice() == null || "".equals(oblation.getPrice())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数price不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (oblation.getMoral() == null || "".equals(oblation.getMoral())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数moral不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (oblation.getSort() == null || "".equals(oblation.getSort())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数sort不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			SysUser user = CurrentSystemUserContext.getSystemUserContext();
			WorshipOblationType wot = oblationTypeMapper.selectByPrimaryKey(oblation.getOblationtypeid());
			if (StringTools.notEmpty(oblation.getId())) {//修改
				if (wot != null) {
					oblation.setOblationtype(wot.getTypename());
				}
				status = oblationMapper.updateByPrimaryKeySelective(oblation);
			} else {//新增
				oblation.setId(UUIDUtils.getUUID());
				oblation.setDeleteflag(ConstantUtils.DELETE_FALSE);// 使用中
				if (wot != null) {
					oblation.setOblationtype(wot.getTypename());
				}
				oblation.setCreateid(user.getUserid());
				oblation.setCreatename(user.getName());
				oblation.setCreatetime(new Date());
				status = oblationMapper.insertSelective(oblation);
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
	public JsonResponse del(WorshipOblation oblation) {
		Result result = null;
		JsonResponse res = null;
		if (oblation.getId() == null || "".equals(oblation.getId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			oblation.setDeleteflag(ConstantUtils.DELETE_TRUE);
			int status = oblationMapper.updateByPrimaryKeySelective(oblation);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[del方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse getOblationById(String id) {
		Result result = null;
		JsonResponse res = null;
		if (id == null || "".equals(id)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			WorshipOblation worshipOblation = oblationMapper.selectByPrimaryKey(id);
			if (worshipOblation != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(worshipOblation);
				return res;
			}
		} catch (Exception e) {
			log_.error("[getOblationById方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

}
