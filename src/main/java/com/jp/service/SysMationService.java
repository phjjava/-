package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.common.PageModel;
import com.jp.entity.MationType;
import com.jp.entity.SysMation;

public interface SysMationService {
	/**
	 * 咨询列表
	 * @param goType
	 * @return
	 */
	List<SysMation> selectByGoType(String goType);
	/**
	 * 修改
	 * @param mation
	 * @return
	 */
	Integer update(SysMation mation);
	/**
	 * 新增
	 * @param mation
	 * @return
	 */
	Integer insert(SysMation mation);
	/**
	 * 物理批量删除
	 * @param mationids
	 * @return
	 * @throws Exception
	 */
	int batchDeleteAll(@Param("array") String mationids[]) throws Exception;
	/**
	 * 类型下拉框
	 * @return
	 */
	List<MationType> selecttypelist();
	
	/**
	 * 资讯类型修改
	 * @param mationtype
	 * @return
	 */
	Integer updatetype(MationType mationtype);
	/**
	 * 资讯类型新增
	 * @param mationtype
	 * @return
	 */
	Integer inserttype(MationType mationtype);
	/**
	 * 资讯类型批删
	 * @param mationtypeArray
	 */
	int mationtypeDeleteAll(@Param("array")String[] mationtypeArray)throws Exception;
	
	PageModel<SysMation> pageQuery(PageModel<SysMation> pageModel, SysMation mation, String mationtitle);
	
	/**
	 * 类型详情
	 * @param typeid
	 * @return
	 */
	List<MationType> selecttypeone(String typeid);
	/**
	 * 类型列表
	 * @param pageModel
	 * @param mation
	 * @return
	 */
	PageModel<MationType> pageQuery(PageModel<MationType> pageModel, MationType mation);

	

}
