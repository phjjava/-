package com.jp.service;

import com.jp.common.JsonResponse;

/**
 * <p>
 * 家谱祭祖世系字典表 服务类
 * </p>
 *
 * @author SongMingWei
 * @since 2019-07-04
 */
public interface WorshipAncestorDictService {
	/**
	 * 获取当前用户家族世系图
	 * @param familyid 家族id
	 * @return
	 */
	JsonResponse getWorshipAncestorDictList(String familyid);
	/**
	 * 根据家族id以及世系代数 查询该家族改世系的人
	 * @param familyid 家族id
	 * @param genlevel 世系代数
	 * @param familyname 家族姓氏
	 * @param pagesize 条数
	 * @param pageNo 页数
	 * @return
	 */
	JsonResponse getWorshipAncestorList(String familyid, Integer genlevel, String familyname, Integer pagesize,
			Integer pageNo);
}
