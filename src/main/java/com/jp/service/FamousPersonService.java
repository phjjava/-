package com.jp.service;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.FamousPerson;

public interface FamousPersonService {
	/**
	 * 名人家谱列表
	 * @param pageModel
	 * @param famousPerson
	 * @return
	 */
	PageModel<FamousPerson> pageQuery(PageModel<FamousPerson> pageModel, FamousPerson famousPerson);
	/**
	 * 名人家谱回显
	 * @param famousid
	 * @return
	 */
	FamousPerson get(String famousid);
	
	/**
	 * 名人家谱状态更改
	 * @param famousPerson
	 * @return
	 */
	Integer changeStatus(FamousPerson famousPerson);
	/**
	 * 名人家谱修改
	 * @param famousPerson
	 * @return
	 */
	Integer update(FamousPerson famousPerson);
	/**
	 * 名人家谱新增
	 * @param famousPerson
	 * @return
	 */

	Integer insert(FamousPerson famousPerson);
	/**
	 * 名人家谱物理批量删除
	 * @param famousidArray
	 */ 
	Integer batchDeleteAll(@Param("array") String[] famousids)throws Exception;
	/**
	 * api列表接口
	 * @param pageModel
	 * @param famousPerson
	 * @return
	 */
	JsonResponse pageQueryApi(PageModel<FamousPerson> pageModel, FamousPerson famousPerson);

}
