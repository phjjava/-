package com.jp.dao;

import com.jp.entity.Dynamic;
import com.jp.entity.DynamicExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DynamicMapper {
    int countByExample(DynamicExample example);

    int deleteByExample(DynamicExample example);

    int deleteByPrimaryKey(String dyid);

    int insert(Dynamic record);

    int insertSelective(Dynamic record);

    List<Dynamic> selectByExampleWithBLOBs(DynamicExample example);

    List<Dynamic> selectByExample(DynamicExample example);

    Dynamic selectByPrimaryKey(String dyid);

    int updateByExampleSelective(@Param("record") Dynamic record, @Param("example") DynamicExample example);

    int updateByExampleWithBLOBs(@Param("record") Dynamic record, @Param("example") DynamicExample example);

    int updateByExample(@Param("record") Dynamic record, @Param("example") DynamicExample example);

    int updateByPrimaryKeySelective(Dynamic record);

    int updateByPrimaryKeyWithBLOBs(Dynamic record);

    int updateByPrimaryKey(Dynamic record);
    
List<Dynamic> selectBranchDynamicList(Dynamic dynamic);
    
    int batchDelete(@Param("array") String dyid[]);
    
    List<Dynamic> selectdyread(@Param("dynamic")Dynamic dynamic,@Param("list")List<String> branchids);
    /**
     * @描述 banner级联 动态 goType == 1
     * @作者 sj
     * @时间 2017年12月5日下午5:29:37
     * @参数 @param branchids
     * @参数 @return
     * @return List<Dynamic>
     */
    List<Dynamic> selectGoType(@Param("list")List<String> branchids);
    
    List<Dynamic> selectReadOfManager(@Param("dynamic")Dynamic dynamic);
    
    /**
	* 以下方法用于api
	*/	
    List<Dynamic> selectByCityCode(Map<String, Object> params);
    
    List<Dynamic> searchAllDynamic(Map<String, Object> map);
}