package com.jp.service;

import org.apache.ibatis.annotations.Param;

import com.jp.common.PageModel;
import com.jp.entity.BannerHomePage;
import com.jp.entity.JpXing;

public interface JpXingService {
	/**
	 * 修改百家姓
	 * @param jpxing
	 * @return
	 */
	Integer update(JpXing jpxing);
	/**
	 * 新增姓氏
	 * @param jpxing
	 * @return
	 */
	Integer insert(JpXing jpxing);
	/**
	 * 分页模糊列表
	 * @param pageModel
	 * @param xing
	 * @param xname
	 * @return
	 */
	PageModel<JpXing> pageQuery(PageModel<JpXing> pageModel, JpXing xing, String xname);
	/**
	 * 回显姓氏详情
	 * @param id
	 * @return
	 */
	JpXing get(String id);
	/**
	 * 姓氏批删
	 * @param xingtypeArray
	 */
	int xingDeleteAll(@Param("array") String[] ids);

}
