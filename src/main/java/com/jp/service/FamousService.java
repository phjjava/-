package com.jp.service;

import com.jp.common.PageModel;
import com.jp.entity.Banner;
import com.jp.entity.Notice;
import com.jp.entity.Usercontent;

public interface FamousService {

	public PageModel<Usercontent> selectContentList(PageModel<Usercontent> pageModel,Usercontent usercontent);

	Usercontent get(String noticeid);
	/**
	 * 
	 * @描述  保存名人录
	 * @作者 jinlizhi
	 * @时间 2017年5月23日下午4:59:47
	 * @参数 @param usercontent
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int insert(Usercontent usercontent) throws Exception;
	/**
	 * 
	 * @描述 保存名人录
	 * @作者 jinlizhi
	 * @时间 2017年5月23日下午5:03:13
	 * @参数 @param usercontent
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int update (Usercontent usercontent) throws Exception;
}
