package com.jp.dao;

import com.jp.entity.Useralbum;
import com.jp.entity.UseralbumKey;
import com.jp.entity.UseralbumQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UseralbumDao {
    int countByExample(UseralbumQuery example);

    int deleteByExample(UseralbumQuery example);

    int deleteByPrimaryKey(UseralbumKey key);

    int insert(Useralbum record);

    int insertSelective(Useralbum record);

    List<Useralbum> selectByExample(UseralbumQuery example);

    Useralbum selectByPrimaryKey(UseralbumKey key);

    int updateByExampleSelective(@Param("record") Useralbum record, @Param("example") UseralbumQuery example);

    int updateByExample(@Param("record") Useralbum record, @Param("example") UseralbumQuery example);

    int updateByPrimaryKeySelective(Useralbum record);

    int updateByPrimaryKey(Useralbum record);
    /**
     * 
     * @描述 查询用户相册
     * @作者 sj
     * @时间 2017年5月17日下午4:43:45
     * @参数 @param userid
     * @参数 @return
     * @return List<Useralbum>
     */
    List<Useralbum> selectUseralbum(String userid);
}