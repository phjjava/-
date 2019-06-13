package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Dynamic;
import com.jp.entity.Dynamicfile;
import com.jp.entity.DynamicfileQuery;

public interface DynamicService {
	/**
	 * @描述 分页查询动态
	 * @作者 hongjun
	 * @时间 2017年5月3日上午9:18:13
	 * @参数 @param dyid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return Dynamic
	 */
	PageModel<Dynamic> pageQuery(PageModel<Dynamic> pageModel, Dynamic dynamic) throws Exception;

	/**
	 * @描述 根据ID获取动态详细信息
	 * @作者 hongjun
	 * @时间 2017年5月3日下午6:11:03
	 * @参数 @param dyid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return Dynamic
	 */
	Dynamic get(String dyid) throws Exception;

	/**
	 * @描述 置顶状态
	 * @作者 hongjun
	 * @时间 2017年5月9日上午10:18:44
	 * @参数 @param dynamic
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int changeStatus(Dynamic dynamic) throws Exception;

	/**
	 * @描述 保存或编辑动态
	 * @作者 hongjun
	 * @时间 2017年5月10日上午10:20:01
	 * @参数 @param dynamic
	 * @参数 @return
	 * @return String
	 */
	JsonResponse saveDynamic(Dynamic dynamic, List<Dynamicfile> dylist, @Param("array") String dyfids[]);

	/**
	 * @描述 批量删除
	 * @作者 hongjun
	 * @时间 2017年5月11日上午9:00:15
	 * @参数 @param dyids
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int batchDelete(@Param("array") String dyids[]) throws Exception;

	/**
	 * @描述 动态附件回显
	 * @作者 hongjun
	 * @时间 2017年5月18日上午8:43:56
	 * @参数 @param example
	 * @参数 @return
	 * @return List<Dynamicfile>
	 */
	List<Dynamicfile> selectdyfile(DynamicfileQuery example);

	/**
	 * 以下方法用于api
	 */
	JsonResponse getDylist(Dynamic entity);

	JsonResponse getDyDetail(Dynamic entity);

	JsonResponse getDyDetailExt(Dynamic entity);

}
