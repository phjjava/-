package com.jp.dao;

import java.util.List;
import java.util.Map;

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


    int updateByPrimaryKey(EditorialBoard record);
    //根据分支区域  城市  县城code值查询编委会
	EditorialBoard selectbyEditor(@Param("areacode")String areacode, @Param("citycode")String citycode, @Param("xcode")String xcode, @Param("familyId")String familyId);

	EditorialBoard selectbyEditorTwo(@Param("areacode")String areacode, @Param("citycode")String citycode, @Param("xcode")String xcode,@Param("familyId")String familyId);

	EditorialBoard selectbyEditorThree(@Param("areacode")String areacode, @Param("citycode")String citycode, @Param("xcode")String xcode,@Param("familyId")String familyId);

	int updateByPrimaryKeySelective(EditorialBoard record);

	@Select("SELECT `code` cityCode,`level`,`name` cityName,parent_id pid FROM `jp_city` WHERE `code` = #{code}")
	Map<String, Object> selectCityByCode(@Param("code") String code);

	@Select("SELECT * FROM `jp_city` WHERE `id` = #{id}")
	Map<String, Object> selectCityById(@Param("id") String id);

}