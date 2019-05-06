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

}
