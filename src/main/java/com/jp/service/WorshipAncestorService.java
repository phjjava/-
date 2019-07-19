package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.WorshipAncestor;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SongMingWei
 * @since 2019-07-19
 */
public interface WorshipAncestorService   {
	/**
	 * 	祭拜家族世系
	 * @param entity
	 * @return
	 */
	JsonResponse worshipAncestor(WorshipAncestor entity);
	/**
	 * 获取所有祭拜纪录
	 * @param entity
	 * @param start
	 * @param count
	 * @return
	 */
	JsonResponse getWorshipAncestors(WorshipAncestor entity, int start, int count);
	/**
	 * 获取我的祭拜记录
	 * @param entity
	 * @param start
	 * @param count
	 * @return
	 */
	JsonResponse getMyWorshipAncestors(WorshipAncestor entity, int start, int count);
	/**
	 * 	获取祭拜图展示祭品信息
	 * @param entity
	 * @param start
	 * @param count
	 * @return
	 */
	JsonResponse getWorshipDetali(WorshipAncestor entity);

}
