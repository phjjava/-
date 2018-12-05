package com.jp.dao;

import com.jp.entity.Userbranch;
import com.jp.entity.UserbranchKey;
import com.jp.entity.UserbranchQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserbranchDao {
    int countByExample(UserbranchQuery example);

    int deleteByExample(UserbranchQuery example);

    int deleteByPrimaryKey(UserbranchKey key);

    int insert(Userbranch record);

    int insertSelective(Userbranch record);

    List<Userbranch> selectByExample(UserbranchQuery example);

    Userbranch selectByPrimaryKey(UserbranchKey key);

    int updateByExampleSelective(@Param("record") Userbranch record, @Param("example") UserbranchQuery example);

    int updateByExample(@Param("record") Userbranch record, @Param("example") UserbranchQuery example);

    int updateByPrimaryKeySelective(Userbranch record);

    int updateByPrimaryKey(Userbranch record);
    /**
     * 
     * @描述 批量保存
     * @作者 sj
     * @时间 2017年5月13日上午11:27:25
     * @参数 @param record
     * @参数 @return
     * @return int
     */
    int batchInsert(@Param("list")List<Userbranch>UserbranchList);
    /**
     * 
     * @描述 批量删除
     * @作者 sj
     * @时间 2017年5月13日上午11:27:25
     * @参数 @param record
     * @参数 @return
     * @return int
     */
    int batchDelete(String userid);
}