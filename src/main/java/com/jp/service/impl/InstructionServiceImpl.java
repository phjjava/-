package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.PageModel;
import com.jp.dao.InstructionDao;
import com.jp.entity.Instruction;
import com.jp.entity.InstructionQuery;
import com.jp.entity.InstructionQuery.Criteria;
import com.jp.service.InstructionService;
import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

@Service
public class InstructionServiceImpl implements InstructionService {
	@Autowired
	private InstructionDao itsdao;

	@Override
	public PageModel<Instruction> pageQuery(PageModel<Instruction> pageModel, Instruction instruction)
			throws Exception {
		InstructionQuery itq = new InstructionQuery();
		Criteria criteria = itq.createCriteria();
		instruction.setDeleteflag(0);
		if (StringTools.trimNotEmpty(instruction.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(instruction.getDeleteflag());
		}
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Instruction> list = itsdao.selectByExample(itq);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Instruction>(list));
		return pageModel;
	}

	@Override
	public Instruction get(String instructionid) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		InstructionQuery itq = new InstructionQuery();
		Criteria criteria = itq.createCriteria();
		criteria.andInstructionidEqualTo(instructionid);
		List<Instruction> instruction = itsdao.selectByExample(itq);
		if (instruction != null && instruction.size() > 0) {
			if (instruction.get(0).getCreatetime() != null) {
				instruction.get(0).setCreatetimeStr(formatter.format(instruction.get(0).getCreatetime()));
			}
			return instruction.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String saveInstruction(Instruction instruction) {
		String result = "";
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		try {
			//编辑
			if (StringTools.trimNotEmpty(instruction.getInstructionid())) {
				instruction.setUpdateid(userid);
				instruction.setUpdatetime(new Date());
				itsdao.updateByPrimaryKeySelective(instruction);
				result = "0";
			} else {
				//新增
				String instructionid = UUIDUtils.getUUID();
				instruction.setInstructionid(instructionid);
				instruction.setCreateid(userid);
				instruction.setFamilyid(familyid);
				instruction.setDeleteflag(0);
				Date insertDate = new Date();
				instruction.setCreatetime(insertDate);
				itsdao.insertSelective(instruction);
				result = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Result saveCover(Instruction instruction) {
		Result result = new Result();
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		//save or update
		if (StringTools.trimNotEmpty(instruction.getInstructionid())) {
			//update
			instruction.setUpdateid(userid);
			instruction.setUpdatetime(new Date());
			int insertRt = itsdao.updateByPrimaryKeySelective(instruction);
			if (insertRt != 1) {
				result.setStatus(0);
				result.setMsg("更新失败");
			}
			result.setStatus(1);
		} else {
			//save
			//init
			String instructionid = UUIDUtils.getUUID();
			instruction.setInstructionid(instructionid);
			instruction.setCreateid(userid);
			instruction.setCreatetime(new Date());
			instruction.setDeleteflag(ConstantUtils.DELETE_FALSE);
			instruction.setFamilyid(familyid);
			int insertRt = itsdao.insertSelective(instruction);
			if (insertRt != 1) {
				result.setStatus(0);
				result.setMsg("保存失败");
			}
			result.setStatus(1);
		}

		return result;
	}

}
