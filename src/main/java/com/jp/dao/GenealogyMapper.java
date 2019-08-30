package com.jp.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jp.entity.Genealogy;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author panhuajie123
 * @since 2019-08-19
 */
public interface GenealogyMapper extends BaseMapper<Genealogy> {

	@Select("SELECT genealogy FROM `jp_genealogy` WHERE loginType = #{loginType}")
	String selectJsonByType(@Param("loginType") String loginType);
}
