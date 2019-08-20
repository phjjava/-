package com.jp.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.HomeStateMapper;
import com.jp.entity.HomeState;
import com.jp.service.HomeStateService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author phj123
 * @since 2019-08-19
 */
@Service
public class HomeStateServiceImpl extends ServiceImpl<HomeStateMapper, HomeState> implements HomeStateService {

	private final Logger log_ = LogManager.getLogger(HomeStateServiceImpl.class);

	@Autowired
	private HomeStateMapper homeStateMapper;

	@Override
	public JsonResponse queryHomeState(HomeState homeState) {
		Result result = null;
		JsonResponse res = null;
		// 参数校验
		if (StringUtils.isBlank(homeState.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			// 获取当前用户自定义首页设置
			HomeState state = homeStateMapper.selectByUserid(homeState.getUserid());

			if (state == null) {// 当前用户没有改动过默认设置，直接返回系统默认
				result = new Result(MsgConstants.RESUL_SUCCESS);
				result.setMsg("当前用户没有改动过首页设置，请直接返回系统默认！");
				res = new JsonResponse(result);
				return res;
			} else {// 返回用户自定义的首页设置
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(state);
				return res;
			}
		} catch (Exception e) {
			log_.error("[queryHomeState方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
	}

	@Override
	public JsonResponse editHomeState(HomeState homeState) {
		Result result = null;
		JsonResponse res = null;
		// 参数校验
		if (StringUtils.isBlank(homeState.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (homeState.getMationStatus() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数mationStatus不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (homeState.getNoticeStatus() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数noticeStatus不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (homeState.getBannerStatus() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数bannerStatus不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (homeState.getSynopsisStatus() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数synopsisStatus不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (homeState.getXingStatus() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数xingStatus不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		int status = 0;
		try {
			// 获取当前用户自定义首页设置
			HomeState state = homeStateMapper.selectByUserid(homeState.getUserid());
			if (state != null) {
				status = homeStateMapper.updateByUserid(homeState);
			} else {
				status = homeStateMapper.insertHomeState(homeState);
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[editHomeState方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

}
