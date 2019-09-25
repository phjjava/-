package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jp.entity.EditorialBoard;
import com.jp.entity.EditorialBoardExample;

public interface EditorialBoardMapper {
	int countByExample(EditorialBoardExample example);

	int deleteByExample(EditorialBoardExample example);

	int deleteByPrimaryKey(String id);

	int insert(EditorialBoard record);

	int insertSelective(EditorialBoard record);

	List<EditorialBoard> selectByExample(EditorialBoardExample example);

	@Select("SELECT code FROM jp_editorial_board WHERE id = #{ebid}")
	String selectCodeByEbid(@Param("ebid") String ebid);

	EditorialBoard selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") EditorialBoard record,
			@Param("example") EditorialBoardExample example);

	int updateByExample(@Param("record") EditorialBoard record, @Param("example") EditorialBoardExample example);

	int updateByPrimaryKeySelective(EditorialBoard record);

	int updateByPrimaryKey(EditorialBoard record);
}