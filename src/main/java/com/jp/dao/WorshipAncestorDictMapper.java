package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.WorshipAncestorDict;

/**
 * <p>
 * 家谱祭祖世系字典表 Mapper 接口
 * </p>
 *
 * @author SongMingWei
 * @since 2019-07-04
 */
public interface WorshipAncestorDictMapper {
	/**
	 * 获取当前用户祭祖世系图
	 * @param last
	 * @return
	 */
	List<WorshipAncestorDict> selectList(@Param("last")Integer last);
}
