package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.common.PageModel;
import com.jp.entity.BannerHomePage;
import com.jp.entity.GoTypeResult;
import com.jp.entity.SysGoTypeResult;
import com.jp.entity.SysMation;

public interface SysBannerService {

	PageModel<BannerHomePage> pageQuery(PageModel<BannerHomePage> pageModel, BannerHomePage banner)throws Exception;;

	BannerHomePage get(String bannerid) throws Exception;
	
	/**
	 * @描述 停用Banner
	 * @作者 hongjun
	 * @时间 2019年7月24日上午8:54:13
	 * @参数 @param banner
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	Integer changeStatus(BannerHomePage banner);
	
	/**
	 * banner的修改
	 * @param banner
	 * @return
	 */
	Integer update(BannerHomePage banner);
	
	/**
	 * banner的增加
	 * @param banner
	 * @return
	 */
	Integer insert(BannerHomePage banner);
	
	/**
	 * banner的批量删除
	 * @param banner
	 * @return
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
	List<SysMation> selectByGoType(String goType);

	/**
	 * 物理删除banner图
	 * @param bannerid
	 */
	void realDelete(String bannerid);
	
	/**
	 * banner的批量删除
	 * @param banner
	 * @return
	 */
	int batchDeleteAll(@Param("array") String bannerids[]) throws Exception;
	
	/**
	 * 资讯详情
	 * @param mationid
	 */

	SysMation SelectMationOne(String mationid);
	
	
	
	
	

}
