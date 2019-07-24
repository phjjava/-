package com.jp.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jp.entity.Worship;
import com.jp.entity.WorshipAncestor;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SongMingWei
 * @since 2019-07-19
 */
public interface WorshipAncestorMapper  {
	/**
	 * 插入祭拜家族世系
	 * @param record
	 * @return
	 */
	int insertSelective(WorshipAncestor record);
	/**
	 * 获取所有祭拜纪录
	 * @param worshipid 祭拜世图id
	 * @param familyid  世图家族id
	 * @return
	 */
	//@Select("select * from jp_worship_ancestor where worshipid = #{worshipid} and familyid = #{familyid} and deleteflag = 0 ORDER BY createtime desc")
	List<WorshipAncestor> selectByWorshipid(@Param("worshipid") String worshipid,@Param("familyid")String familyid);
	/**
	 * 获取我的祭拜记录
	 * @param worshipid 祭拜世图id
	 * @param familyid  世图家族id
	 * @param createid  查询用户id
	 * @return
	 */
	//@Select("select * from jp_worship_ancestor where worshipid = #{worshipid}  and familyid = #{familyid} and createid = #{createid} and deleteflag = 0 ORDER BY createtime desc")
	List<WorshipAncestor> selectByWorshipidAndCreateid(@Param("worshipid") String worshipid,
			@Param("familyid")String familyid,@Param("createid") String createid);
	/**
	 * 获取祭拜未失效的记录
	 * @param familyid 家族id
	 * @param worshipid  祭拜世系图worshipid
	 * @param typeid  祭品分类id
	 * @return
	 */
	List<WorshipAncestor> selectNoTimeOutByType(@Param("familyid")String familyid,@Param("worshipid")String worshipid,@Param("typeid")String typeid);
}
