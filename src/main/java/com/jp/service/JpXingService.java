package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.JpXing;
import com.jp.entity.JpXingContent;
import com.jp.entity.JpXingDic;

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
	
	int SelectCount(String xname);
	/**api
	 * 热门姓氏
	 * @return
	 */
	List<JpXing> hotlist();
	/**
	 * api百家姓列表接口
	 * @param pageModel
	 * @param xing
	 * @param xname
	 * @return
	 */
	JsonResponse pageQuery1(PageModel<JpXing> pageModel, JpXing xing, String xname);
	/**
	 * 姓氏字典数据
	 * @return
	 */
	List<JpXingDic> diclist();
	/**
	 * 查询是否含有已对应数据
	 * @param id
	 * @param code
	 * @return
	 */
	String selectContent(String id, String code);
	/**
	 * 姓氏内容表新增
	 * @param xingContent
	 */
	void insert(JpXingContent xingContent);
	/**
	 * 姓氏内容表修改
	 * @param xingContent
	 */
	void update(JpXingContent xingContent);

}
