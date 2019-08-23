package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.MomentUserStoreMapper;
import com.jp.entity.MomentUserStore;
import com.jp.entity.MomentUserStoreExample;
import com.jp.service.MomentUserStoreService;
import com.jp.service.UserService;
import com.jp.util.UUIDUtils;

@Service
public class MomentUserStoreServiceImpl implements MomentUserStoreService {

	@Autowired
	private MomentUserStoreMapper momentUserStoreMapper;
	@Autowired
	private UserService userService;

	@Override
	public JsonResponse storeMoment(MomentUserStore entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = userService.checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (StringUtils.isBlank(entity.getMomentId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数momentId为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userId为空！");
			res = new JsonResponse(result);
			return res;
		}
		// 判断是否已经收藏
		MomentUserStoreExample momentUserStoreExample = new MomentUserStoreExample();
		momentUserStoreExample.or().andUseridEqualTo(entity.getUserid()).andMomentIdEqualTo(entity.getMomentId());
		List<MomentUserStore> lists = momentUserStoreMapper.selectByExample(momentUserStoreExample);
		if (lists != null && lists.size() > 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("已经收藏，不能再次收藏");
			res = new JsonResponse(result);
			return res;
		}

		entity.setId(UUIDUtils.getUUID());
		entity.setCreatetime(new Date());
		entity.setCreateby(entity.getUserid());
		entity.setDeleteflag(ConstantUtils.DELETE_FALSE);
		int status = momentUserStoreMapper.insert(entity);
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("收藏成功");
			res = new JsonResponse(result);
			return res;
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			return res;
		}
	}

	@Override
	public JsonResponse cancelStore(MomentUserStore entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = userService.checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (StringUtils.isBlank(entity.getId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		/*
		 * MomentUserStoreExample momentUserStoreExample = new MomentUserStoreExample();
		 * momentUserStoreExample.or().andUseridEqualTo(entity.getUserid())
		 * .andMomentIdEqualTo(entity.getMomentId()); int status =
		 * momentUserStoreMapper.deleteByExample(momentUserStoreExample);
		 */
		entity.setDeleteflag(ConstantUtils.DELETE_TRUE);
		int status = momentUserStoreMapper.updateByPrimaryKeySelective(entity);
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("取消收藏成功");
			res = new JsonResponse(result);
			return res;
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("网络连接失败");
			res = new JsonResponse(result);
			return res;
		}
	}

}
