package com.jp.service;

import com.jp.common.PageModel;
import com.jp.entity.Instruction;
import com.jp.entity.Introduce;
import com.jp.util.Result;

public interface InstructionService {
	PageModel<Instruction> pageQuery(PageModel<Instruction> pageModel,Instruction instruction) throws Exception;
	Instruction get(String instructionid) throws Exception;
	String saveInstruction(Instruction instruction);
	/**
	 * 
	 * @描述 保存家谱(家训)封面
	 * @作者 jinlizhi
	 * @时间 2017年5月31日下午5:16:58
	 * @参数 @param instruction
	 * @参数 @return
	 * @return Result
	 */
	Result saveCover(Instruction instruction);
}
