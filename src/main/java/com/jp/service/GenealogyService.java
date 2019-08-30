package com.jp.service;

import com.baomidou.mybatisplus.service.IService;
import com.jp.common.JsonResponse;
import com.jp.entity.Genealogy;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author panhuajie123
 * @since 2019-08-19
 */
public interface GenealogyService extends IService<Genealogy> {

	JsonResponse queryGenealogy(Genealogy genealogy);

}
