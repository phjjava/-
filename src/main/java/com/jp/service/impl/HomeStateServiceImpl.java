package com.jp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jp.common.JsonResponse;
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

	@Autowired
	private HomeStateMapper homeStateMapper;

	@Override
	public JsonResponse queryHomeState(HomeState homeState) {
		// TODO Auto-generated method stub
		return null;
	}

}
