package com.jp.dao;

import com.jp.entity.SysFuncVersion;
import com.jp.entity.SysFuncVersionQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysFuncVersionDao {
	
    int countByExample(SysFuncVersionQuery example);

    int deleteByExample(SysFuncVersionQuery example);

    int deleteByPrimaryKey(SysFuncVersion key);

    int insert(SysFuncVersion record);

    int insertSelective(SysFuncVersion record);

    List<SysFuncVersion> selectByExample(SysFuncVersionQuery example);

    int updateByExampleSelective(@Param("record") SysFuncVersion record, @Param("example") SysFuncVersionQuery example);

    int updateByExample(@Param("record") SysFuncVersion record, @Param("example") SysFuncVersionQuery example);
    
    int deleteByVersionid(String versionid);
    
    int insertBatch(@Param("versionid")String versionid,@Param("array") String [] functionids);
}