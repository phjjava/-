package com.jp.service.impl;

import com.github.pagehelper.PageHelper;
import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.UserDao;
import com.jp.dao.WorshipAncestorMapper;
import com.jp.dao.WorshipOblationMapper;
import com.jp.dao.WorshipOblationTypeMapper;
import com.jp.entity.User;
import com.jp.entity.UserQuery;
import com.jp.entity.Worship;
import com.jp.entity.WorshipAncestor;
import com.jp.entity.WorshipAncestorVO;
import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationType;
import com.jp.entity.WorshipOblationTypeExample;
import com.jp.entity.WorshipVO;
import com.jp.service.WorshipAncestorService;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SongMingWei
 * @since 2019-07-19
 */
@Service
public class WorshipAncestorServiceImpl  implements WorshipAncestorService {
	
	private final Logger log_ = LogManager.getLogger(WorshipAncestorServiceImpl.class);

	@Resource
	private WorshipAncestorMapper worshipAncestorMapper;
	@Resource
	private WorshipOblationMapper oblationMapper;
	@Resource
	private WorshipOblationTypeMapper oblationTypeMapper;
	@Resource
	private UserDao userDao;
	
	@Override
	public JsonResponse worshipAncestor(WorshipAncestor entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getWorshipid() == null || "".equals(entity.getWorshipid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worishipid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
//			if (entity.getCreateid() == null || "".equals(entity.getCreateid())) {
//				result = new Result(MsgConstants.RESUL_FAIL);
//				result.setMsg("当前用户createid不能为空！");
//				res = new JsonResponse(result);
//				return res;
//			}
			if (entity.getOblationid() == null || "".equals(entity.getOblationid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数oblationid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
//			if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
//				result = new Result(MsgConstants.RESUL_FAIL);
//				result.setMsg("参数familyid不能为空！");
//				res = new JsonResponse(result);
//				return res;
//			}
			if (entity.getWorshipname() == null || "".equals(entity.getWorshipname())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worshipname不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			//获取请求头中的数据 familyid userid
			String familyid = WebUtil.getHeaderInfo("familyid");
			String createid = WebUtil.getHeaderInfo("userid");
			String uuid = UUIDUtils.getUUID();
			entity.setId(uuid);
			entity.setCreateid(createid);
			entity.setFamilyid(familyid);
			entity.setCreatetime(new Date());
			entity.setDeleteflag(ConstantUtils.DELETE_FALSE);
			WorshipOblation oblation = oblationMapper.selectByPrimaryKey(entity.getOblationid());
			entity.setOblation(oblation.getOblation());
			entity.setOblationtype(oblation.getOblationtype());
			entity.setOblationtypeid(oblation.getOblationtypeid());
			User createuser = userDao.selectByPrimaryKey(createid);			
			entity.setCreatename(createuser.getUsername());
			int status = worshipAncestorMapper.insertSelective(entity);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[worshipAncestor方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse getWorshipAncestors(WorshipAncestor entity, int start, int count) {
		Result result = null;
		JsonResponse res = null;
		List<WorshipAncestor> worships = null;
		try {
			if (entity.getWorshipid() == null || "".equals(entity.getWorshipid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worishipid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			//获取请求头中的数据 familyid createid
			String familyid = WebUtil.getHeaderInfo("familyid");
			PageHelper.startPage(start, count);
			/*
			 * WorshipExample example = new WorshipExample();
			 * example.or().andWorshipidEqualTo(entity.getWorshipid()).andDeleteflagEqualTo(
			 * ConstantUtils.DELETE_FALSE); example.setOrderByClause("createtime desc");
			 * worships = worshipMapper.selectByExample(example);
			 */
			worships = worshipAncestorMapper.selectByWorshipid(entity.getWorshipid(),familyid);
		} catch (Exception e) {
			log_.error("[getWorshipAncestors方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(worships);
		return res;
	}

	@Override
	public JsonResponse getMyWorshipAncestors(WorshipAncestor entity, int start, int count) {
		Result result = null;
		JsonResponse res = null;
		List<WorshipAncestor> worships = null;
		try {
			if (entity.getWorshipid() == null || "".equals(entity.getWorshipid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worishipid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			//获取请求头中的数据 familyid userid
			String familyid = WebUtil.getHeaderInfo("familyid");
			String createid = WebUtil.getHeaderInfo("userid");
			PageHelper.startPage(start, count);
			/*
			 * WorshipExample example = new WorshipExample();
			 * example.or().andWorshipidEqualTo(entity.getWorshipid()).andDeleteflagEqualTo(
			 * ConstantUtils.DELETE_FALSE); example.setOrderByClause("createtime desc");
			 * worships = worshipMapper.selectByExample(example);
			 */
			worships = worshipAncestorMapper.selectByWorshipidAndCreateid(entity.getWorshipid(),familyid,createid);
		} catch (Exception e) {
			log_.error("[getMyWorshipAncestors方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(worships);
		return res;
	}

	@Override
	public JsonResponse getWorshipDetali(WorshipAncestor entity) {
		Result result = null;
		JsonResponse res = null;
		WorshipAncestorVO worshipVo = null;
		try {
			if (entity.getWorshipid() == null || "".equals(entity.getWorshipid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worishipid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			
			//获取请求头中的数据 familyid createid
			String familyid = WebUtil.getHeaderInfo("familyid");
			worshipVo = new WorshipAncestorVO();
			
			WorshipOblationTypeExample example1 = new WorshipOblationTypeExample();
			example1.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			List<WorshipOblationType> types = oblationTypeMapper.selectByExample(example1);
			List<WorshipAncestor> rtnlist = new ArrayList<>();
			for (WorshipOblationType type : types) {
				List<WorshipAncestor> worships = worshipAncestorMapper.selectNoTimeOutByType(familyid,entity.getWorshipid(),type.getId());
				
				rtnlist.addAll(worships);
			}

			worshipVo.setOblationImgs(rtnlist);
		} catch (Exception e) {
			log_.error("[getWorshipDetali方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(worshipVo);
		return res;
	}

}
