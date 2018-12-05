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
}