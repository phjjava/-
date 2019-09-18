package com.jp.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.HomeStateMapper;
import com.jp.entity.HomeState;
import com.jp.service.HomeStateService;
import com.jp.service.UserService;
import com.jp.util.WebUtil;

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
	@Autowired
	private UserService userService;

	@Override
	public JsonResponse queryHomeState(HomeState homeState) {
		Result result = null;
		JsonResponse res = null;
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		homeState.setUserid(userid);
		// 参数校验
		if (StringUtils.isBlank(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("非法登录！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			// 获取当前用户自定义首页设置
			HomeState state = homeStateMapper.selectByUserid(userid);
			if (state == null) {// 给用户设置默认开启
				homeState.setBannerStatus(1);
				homeState.setSynopsisStatus(1);
				homeState.setMationStatus(1);
				homeState.setNoticeStatus(1);
				homeState.setXingStatus(1);
				homeStateMapper.insertHomeState(homeState);
				state = homeStateMapper.selectByUserid(userid);
			}
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(state);
			return res;
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
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		// 参数校验
		if (StringUtils.isBlank(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("非法登录！");
			res = new JsonResponse(result);
			return res;
		}
		homeState.setUserid(userid);
		try {
			int count = homeStateMapper.updateByUseridSelective(homeState);
			if (count > 0) {
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
