package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.UserpositionDao;
import com.jp.entity.Userposition;
import com.jp.service.UserPositionService;

@Service
public class UserPositionServiceImpl implements UserPositionService {

	@Autowired
	private UserpositionDao userpositionMapper;

	@Override
	public JsonResponse getPositionDic(Userposition entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getSex() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("没有性别信息！");
			res = new JsonResponse(result);
			return res;
		}
		/*
		 * UserpositionQuery userpositionQuery = new UserpositionQuery();
		 * userpositionQuery.or().andSexEqualTo(entity.getSex()); List<Userposition>
		 * userPositions = userpositionMapper.selectByExample(userpositionQuery);
		 */
		List<Userposition> userPositions = userpositionMapper.selectBySex(entity.getSex());
		if (userPositions.size() == 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			result.setMsg("没有排行信息！");
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userPositions);
		return res;
	}

}
