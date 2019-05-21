package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.common.PageModel;
import com.jp.entity.Banner;
import com.jp.entity.GoTypeResult;

public interface BannerService {
	PageModel<Banner> pageQuery(PageModel<Banner> pageModel,Banner banner) throws Exception;
	Banner get(String bannerid) throws Exception;
	/**
	 * @描述 停用Banner
	 * @作者 hongjun
	 * @时间 2017年5月8日上午8:54:13
	 * @参数 @param banner
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int changeStatus(Banner banner) throws Exception;
	
	/**
	 * @描述 Banner的保存
	 * @作者 hongjun
	 * @时间 2017年5月8日下午3:55:21
	 * @参数 @param dynamic
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return String
	 */
	int insert(Banner banner) throws Exception;
	/**
	 * @描述 Banner的编辑
	 * @作者 hongjun
	 * @时间 2017年5月8日下午4:29:45
	 * @参数 @param branch
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int update (Banner banner) throws Exception;
	/**
	 * @描述 
	 * @作者 hongjun
	 * @时间 2017年5月10日下午5:38:02
	 * @参数 @param bannerids
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int batchDelete(@Param("array") String bannerids[]) throws Exception;
	/**
	 * @描述 跳转类型的级联
	 * @作者 sj
	 * @时间 2017年12月5日下午4:43:54
	 * @参数 @param goType
	 * @参数 @return
	 * @return List<GoTypeResult>
	 */
	List<GoTypeResult> selectByGoType(String goType);
}
