package com.jp.service;

import com.baomidou.mybatisplus.service.IService;
import com.jp.common.JsonResponse;
import com.jp.entity.HomeState;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author phj123
 * @since 2019-08-19
 */
public interface HomeStateService extends IService<HomeState> {

	JsonResponse queryHomeState(HomeState homeState);

}
