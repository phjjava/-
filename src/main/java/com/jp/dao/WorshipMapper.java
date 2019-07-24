package com.jp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jp.entity.Worship;
import com.jp.entity.WorshipExample;

public interface WorshipMapper {
	int countByExample(WorshipExample example);

	int deleteByExample(WorshipExample example);

	int deleteByPrimaryKey(String id);

	int insert(Worship record);

	int insertSelective(Worship record);

	List<Worship> selectByExample(WorshipExample example);

	Worship selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Worship record, @Param("example") WorshipExample example);

	int updateByExample(@Param("record") Worship record, @Param("example") WorshipExample example);

	int updateByPrimaryKeySelective(Worship record);

	int updateByPrimaryKey(Worship record);

	List<Worship> getActiveUserOblations(Worship entity);

	List<Worship> selectNoTimeOutByType(@Param("worshipid") String worshipid,@Param("id")String id);
	
	List<Worship> getWorships(Map<String, Object> params);

	@Select("select * from jp_worship where worshipid = #{worshipid} and deleteflag = 0 ORDER BY createtime desc")
	List<Worship> selectByWorshipid(@Param("worshipid") String worshipid);

	@Select("select * from jp_worship where worshipid = #{worshipid} and createid = #{createid} and deleteflag = 0 ORDER BY createtime desc")
	List<Worship> selectByWorshipidAndCreateid(@Param("worshipid") String worshipid,
			@Param("createid") String createid);
}