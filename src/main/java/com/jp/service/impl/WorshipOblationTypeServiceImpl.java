package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.UserDao;
import com.jp.dao.WorshipAnnexMapper;
import com.jp.dao.WorshipMapper;
import com.jp.dao.WorshipOblationMapper;
import com.jp.dao.WorshipOblationTypeMapper;
import com.jp.entity.SysUser;
import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationExample;
import com.jp.entity.WorshipOblationType;
import com.jp.entity.WorshipOblationTypeExample;
import com.jp.service.WorshipOblationTypeService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class WorshipOblationTypeServiceImpl implements WorshipOblationTypeService {
	private final Logger log_ = LogManager.getLogger(WorshipOblationTypeServiceImpl.class);

	@Resource
	private WorshipMapper worshipMapper;
	@Resource
	private WorshipAnnexMapper worshipAnnexMapper;
	@Resource
	private WorshipOblationMapper oblationMapper;
	@Resource
	private WorshipOblationTypeMapper oblationTypeMapper;
	@Resource
	private WorshipAnnexMapper annexMapper;
	@Resource
	private UserDao userMapper;

	@Override
	public JsonResponse pageQuery(PageModel<WorshipOblationType> pageModel) {
		Result result = null;
		JsonResponse res = null;
		if (pageModel.getPageNo() == null || "".equals(pageModel.getPageNo() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageNo不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (pageModel.getPageSize() == null || "".equals(pageModel.getPageSize() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageSize不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			WorshipOblationTypeExample example = new WorshipOblationTypeExample();
			example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			example.setOrderByClause("sort asc");
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<WorshipOblationType> list = oblationTypeMapper.selectByExample(example);
			if (list != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(list);
				res.setCount(new PageInfo<WorshipOblationType>(list).getTotal());
				return res;
			}
		} catch (Exception e) {
			log_.error("[pageQuery---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse selectListByParnetid(String oblationtypeid) {
		Result result = null;
		JsonResponse res = null;
		if (oblationtypeid == null || "".equals(oblationtypeid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			WorshipOblationExample example = new WorshipOblationExample();
			example.or().andOblationtypeidEqualTo(oblationtypeid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			example.setOrderByClause(" sort asc");
			List<WorshipOblation> list = oblationMapper.selectByExample(example);
			if (list != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(list);
				return res;
			}
		} catch (Exception e) {
			log_.error("[selectListByParnetid方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse save(WorshipOblationType oblationType) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (oblationType.getTypename() == null || "".equals(oblationType.getTypename())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数typename不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (oblationType.getSort() == null || "".equals(oblationType.getSort() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数sort不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			SysUser user = CurrentSystemUserContext.getSystemUserContext();
			if (StringTools.notEmpty(oblationType.getId())) {//修改
				status = oblationTypeMapper.updateByPrimaryKeySelective(oblationType);
			} else {//新增
				oblationType.setId(UUIDUtils.getUUID());
				oblationType.setCraeteid(user.getUserid());
				oblationType.setCraetename(user.getName());
				oblationType.setCreatetime(new Date());
				oblationType.setDeleteflag(ConstantUtils.DELETE_FALSE);
				status = oblationTypeMapper.insert(oblationType);
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[save---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse del(WorshipOblationType oblationType) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (oblationType.getId() == null || "".equals(oblationType.getId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			oblationType.setDeleteflag(ConstantUtils.DELETE_TRUE);
			WorshipOblationExample ex = new WorshipOblationExample();
			ex.or().andOblationtypeidEqualTo(oblationType.getId());
			WorshipOblation ob = new WorshipOblation();
			ob.setDeleteflag(ConstantUtils.DELETE_TRUE);
			status = oblationTypeMapper.updateByPrimaryKeySelective(oblationType);
			if (status > 0) {
				oblationMapper.updateByExampleSelective(ob, ex);
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[del---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse getOblationTypeById(String id) {
		Result result = null;
		JsonResponse res = null;
		if (id == null || "".equals(id)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			WorshipOblationType oblationType = oblationTypeMapper.selectByPrimaryKey(id);
			if (oblationType != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(oblationType);
				return res;
			}
		} catch (Exception e) {
			log_.error("[getOblationTypeById---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

}
