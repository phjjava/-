package com.jp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.PageModel;
import com.jp.dao.InstructionDao;
import com.jp.entity.Instruction;
import com.jp.entity.InstructionQuery;
import com.jp.service.InstructionService;
import com.jp.util.Result;
import com.jp.util.WebUtil;

@Controller
@RequestMapping("instruction")
public class InstructionController {
	@Autowired
	private InstructionService itsservice;
	@Autowired
	private InstructionDao instructionDao;

	private final Logger log_ = LogManager.getLogger(InstructionController.class);

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<Instruction> pageModel, Instruction instruction, ModelMap model) {
		try {
			itsservice.pageQuery(pageModel, instruction);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						itsservice.pageQuery(pageModel, instruction);
					}
				}
			}
			model.put("pageModel", pageModel);
			model.put("introduce", instruction);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "genealogy/instructionList";

	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {
			String itsid = request.getParameter("instructionid");
			Instruction instruction = itsservice.get(itsid);
			model.put("instruction", instruction);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return "genealogy/instruction";
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveIntroduce(Instruction instruction, HttpServletRequest request) {
		String result = null;
		try {
			if (request.getCharacterEncoding() == null) {
				request.setCharacterEncoding("UTF-8");
			}
			itsservice.saveInstruction(instruction);
			result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result;
	}

	/**
	 * 
	 * @描述 跳转到家族(家训)封面页面前获取当前家族封面信息
	 * @作者 jinlizhi
	 * @时间 2017年5月31日上午11:11:52
	 * @参数 @param request
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/preEditCover")
	public String preEditCover(HttpServletRequest request, ModelMap model) {
		try {
			//当前登录人 familyid
			String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			InstructionQuery example = new InstructionQuery();
			example.setOrderByClause("createtime desc");
			example.or().andFamilyidEqualTo(familyid);
			//获取当前家训信息
			List<Instruction> selectRt = instructionDao.selectByExample(example);
			if (selectRt != null && selectRt.size() > 0) {
				//查询有结果则返回
				Instruction instruction = selectRt.get(0);
				model.put("instruction", instruction);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return "genealogy/frontList";
	}

	/**
	 * 
	 * @描述  保存家训封面
	 * @作者 jinlizhi
	 * @时间 2017年5月31日下午5:12:04
	 * @参数 @param request
	 * @参数 @param instruction
	 * @参数 @return
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCover")
	public Result saveCover(HttpServletRequest request, Instruction instruction) {
		Result result = new Result();
		try {
			result = itsservice.saveCover(instruction);
		} catch (Exception e) {
			result.setStatus(0);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return result;
	}

	/**
	 * 
	 * @描述 跳转到家族(家训)页面前获取当前家族封面信息
	 * @作者 jinlizhi
	 * @时间 2017年6月1日下午7:56:53
	 * @参数 @param request
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@RequestMapping(value = "/preEditInstruction")
	public String preEditInstruction(HttpServletRequest request, ModelMap model) {
		try {
			//当前登录人 familyid
			String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			InstructionQuery example = new InstructionQuery();
			example.setOrderByClause("createtime desc");
			example.or().andFamilyidEqualTo(familyid);
			//获取当前家训信息
			List<Instruction> selectRt = instructionDao.selectByExample(example);
			if (selectRt != null && selectRt.size() > 0) {
				//查询有结果则返回
				Instruction instruction = selectRt.get(0);
				model.put("instruction", instruction);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return "genealogy/instruction";
	}
}
