package com.jp.dao;

import com.jp.entity.User;
import com.jp.entity.Usermates;
import com.jp.entity.UsermatesKey;
import com.jp.entity.UsermatesQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UsermatesDao {
    int countByExample(UsermatesQuery example);

    int deleteByExample(UsermatesQuery example);

    int deleteByPrimaryKey(UsermatesKey key);

    int insert(Usermates record);

    int insertSelective(Usermates record);

    List<Usermates> selectByExampleWithBLOBs(UsermatesQuery example);

    List<Usermates> selectByExample(UsermatesQuery example);

    Usermates selectByPrimaryKey(UsermatesKey key);

    int updateByExampleSelective(@Param("record") Usermates record, @Param("example") UsermatesQuery example);

    int updateByExampleWithBLOBs(@Param("record") Usermates record, @Param("example") UsermatesQuery example);

    int updateByExample(@Param("record") Usermates record, @Param("example") UsermatesQuery example);

    int updateByPrimaryKeySelective(Usermates record);

    int updateByPrimaryKeyWithBLOBs(Usermates record);

    int updateByPrimaryKey(Usermates record);
    /**
     * 
     * @描述 批量导入配偶表
     * @作者 sj
     * @时间 2017年5月8日下午6:17:33
     * @参数 @param userMatesList
     * @参数 @return
     * @return int
     */
    int insertMatesList(@Param("list") List<Usermates> userMatesList);
    /**
     * @描述 根据用户id查询对应的所有配偶id信息,判断有无重复
     * @return List<String>
     */
    List<Usermates> selectmateIdByUserId(@Param("userId")String userId);
}