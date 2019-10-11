package com.jp.dao;

import com.jp.entity.EditorialBoard;
import com.jp.entity.EditorialBoardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EditorialBoardMapper {
    int countByExample(EditorialBoardExample example);

    int deleteByExample(EditorialBoardExample example);

    int deleteByPrimaryKey(String id);

    int insert(EditorialBoard record);

    int insertSelective(EditorialBoard record);

    List<EditorialBoard> selectByExample(EditorialBoardExample example);

    EditorialBoard selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") EditorialBoard record, @Param("example") EditorialBoardExample example);

    int updateByExample(@Param("record") EditorialBoard record, @Param("example") EditorialBoardExample example);

    int updateByPrimaryKeySelective(EditorialBoard record);

    int updateByPrimaryKey(EditorialBoard record);
    //根据分支区域  城市  县城code值查询编委会
	EditorialBoard selectbyEditor(@Param("areacode")String areacode, @Param("citycode")String citycode, @Param("xcode")String xcode, @Param("familyId")String familyId);

	EditorialBoard selectbyEditorTwo(@Param("areacode")String areacode, @Param("citycode")String citycode, @Param("xcode")String xcode,@Param("familyId")String familyId);

	EditorialBoard selectbyEditorThree(@Param("areacode")String areacode, @Param("citycode")String citycode, @Param("xcode")String xcode,@Param("familyId")String familyId);
}