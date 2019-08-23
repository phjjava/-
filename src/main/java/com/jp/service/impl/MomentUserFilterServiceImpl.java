package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.MomentUserFilterMapper;
import com.jp.dao.UserDao;
import com.jp.entity.MomentUserFilter;
import com.jp.service.MomentUserFilterService;
import com.jp.service.UserService;
import com.jp.util.UUIDUtils;

@Service
public class MomentUserFilterServiceImpl implements MomentUserFilterService {

	@Resource
	private MomentUserFilterMapper filterMapper;
	@Resource
	private UserDao userMapper;
	@Resource
	private UserService userService;

	@Override
	public JsonResponse updateUserFilter(MomentUserFilter entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = userService.checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		String userid = demoUser.getData().toString();

		if ("ME".equals(entity.getFilterType()) || "HE".equals(entity.getFilterType())) {
			List<MomentUserFilter> list = entity.getFilterUsers();
			if (list != null && list.size() > 0) {
				Date date = new Date();
				for (MomentUserFilter filter : list) {
					filter.setId(UUIDUtils.getUUID());
					filter.setFilterType(entity.getFilterType());
					filter.setUserid(userid);
					filter.setCreateby(userid);
					filter.setCreatetime(date);
					filter.setDeleteflag(ConstantUtils.DELETE_FALSE);
				}
			}
			int status = filterMapper.insertBatch(list);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			return res;
		} else {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数filterType错误！");
			res = new JsonResponse(result);
			return res;
		}
	}

}
