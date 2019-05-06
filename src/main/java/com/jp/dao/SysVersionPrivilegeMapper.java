package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.SysVersionPrivilege;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangnn
 * @since 2019-04-25
 */
public interface SysVersionPrivilegeMapper {
	
	List<SysVersionPrivilege> selectByRecord(SysVersionPrivilege entity);
	
	SysVersionPrivilege selectByPrimaryKey(String id);
	
	int insert(SysVersionPrivilege record);
	
	int updateByPrimaryKeySelective(SysVersionPrivilege record);
	
	int deleteByPrimaryKey(String id);
	
	SysVersionPrivilege selectVersionValue(@Param("versionid") String versionid,@Param("privilegecode")String privilegecode);
	
	/**
	 * 根据版本id和特权code获取对应版本的特权值
	 * @param familyid 家族id
	 * @param privilagecode 特权code
	 * @return
	 */
	SysVersionPrivilege selectByVersionAndCode(@Param("familyid") String familyid,@Param("privilegecode") String privilegecode);

}
