package com.jp.dao;

import com.jp.entity.Indexcount;
import com.jp.entity.SysFamily;
import com.jp.entity.SysFamilyQuery;
import com.jp.entity.Version;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysFamilyDao {
    int countByExample(SysFamilyQuery example);

    int deleteByExample(SysFamilyQuery example);

    int deleteByPrimaryKey(String familyid);

    int insert(SysFamily record);

    int insertSelective(SysFamily record);

    List<SysFamily> selectByExample(SysFamilyQuery example);

    SysFamily selectByPrimaryKey(String familyid);

    int updateByExampleSelective(@Param("record") SysFamily record, @Param("example") SysFamilyQuery example);

    int updateByExample(@Param("record") SysFamily record, @Param("example") SysFamilyQuery example);

    int updateByPrimaryKeySelective(SysFamily record);

    int updateByPrimaryKey(SysFamily record);
    /**
     * 
     * @描述 家族列表的查询
     * @作者 sj
     * @时间 2017年5月10日上午10:01:15
     * @参数 @param family
     * @参数 @return
     * @return List<SysFamily>
     */
    List<SysFamily> selectFamilyList(SysFamily family);
    /**
     * 
     * @描述 增加家族对应的功能
     * @作者 sj
     * @时间 2017年5月10日下午6:15:23
     * @参数 @param familyId
     * @参数 @param versionId
     * @参数 @return
     * @return int
     */
    int insertFunction(@Param("familyid")String familyid,@Param("versionid")String versionid);
    /**
     * 
     * @描述 删除家族对应的功能
     * @作者 sj
     * @时间 2017年5月10日下午6:24:48
     * @参数 @param familyid
     * @参数 @return
     * @return int
     */
    int deleteFunction(@Param("familyid")String familyid);
    /**
     * 
     * @描述 停用家族
     * @作者 sj
     * @时间 2017年5月11日下午5:26:15
     * @参数 @param record
     * @参数 @return
     * @return int
     */
    int changeStatus(SysFamily record);
    SysFamily selectFamilyVersionNum(String familyid);
    /**
     * @描述 首页统计
     * @作者 sj
     * @时间 2017年5月23日上午11:27:18
     * @参数 @param familyid
     * @参数 @param branchids
     * @参数 @return
     * @return Indexcount
     */
    Indexcount countIndex(@Param("familyid")String familyid,@Param("list")List<String> branchids);
    
    /**
     * 获取家族号
     * @return
     */
    Integer nextVal(); 
    
    List<Version> selectList();
    
}