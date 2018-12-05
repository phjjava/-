package com.jp.dao;

import com.jp.entity.Instruction;
import com.jp.entity.InstructionQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstructionDao {
    int countByExample(InstructionQuery example);

    int deleteByExample(InstructionQuery example);

    int deleteByPrimaryKey(String instructionid);

    int insert(Instruction record);

    int insertSelective(Instruction record);

    List<Instruction> selectByExampleWithBLOBs(InstructionQuery example);

    List<Instruction> selectByExample(InstructionQuery example);

    Instruction selectByPrimaryKey(String instructionid);

    int updateByExampleSelective(@Param("record") Instruction record, @Param("example") InstructionQuery example);

    int updateByExampleWithBLOBs(@Param("record") Instruction record, @Param("example") InstructionQuery example);

    int updateByExample(@Param("record") Instruction record, @Param("example") InstructionQuery example);

    int updateByPrimaryKeySelective(Instruction record);

    int updateByPrimaryKeyWithBLOBs(Instruction record);

    int updateByPrimaryKey(Instruction record);
}