package com.jp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.UserDao;
import com.jp.dao.WorshipAnnexMapper;
import com.jp.dao.WorshipMapper;
import com.jp.dao.WorshipOblationMapper;
import com.jp.dao.WorshipOblationTypeMapper;
import com.jp.entity.User;
import com.jp.entity.UserQuery;
import com.jp.entity.Worship;
import com.jp.entity.WorshipAnnex;
import com.jp.entity.WorshipAnnexExample;
import com.jp.entity.WorshipOblation;
import com.jp.entity.WorshipOblationExample;
import com.jp.entity.WorshipOblationType;
import com.jp.entity.WorshipOblationTypeExample;
import com.jp.entity.WorshipOblationVO;
import com.jp.entity.WorshipVO;
import com.jp.service.UserService;
import com.jp.service.WorshipService;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

@Service
public class WorshipServiceImpl implements WorshipService {

	private final Logger log_ = LogManager.getLogger(WorshipServiceImpl.class);

	@Resource
	private WorshipMapper worshipMapper;
	@Resource
	private WorshipOblationMapper oblationMapper;
	@Resource
	private WorshipOblationTypeMapper oblationTypeMapper;
	@Resource
	private WorshipAnnexMapper annexMapper;
	@Resource
	private UserDao userDao;
	@Resource
	private UserService userService;

	@Override
	public JsonResponse worship(Worship entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			JsonResponse demoUser = userService.checkDemoUser();
			if (demoUser.getCode() == 1) {
				return demoUser;
			}
			if (entity.getWorshipid() == null || "".equals(entity.getWorshipid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worishipid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (entity.getCreateid() == null || "".equals(entity.getCreateid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("当前用户createid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (entity.getOblationid() == null || "".equals(entity.getOblationid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数oblationid不能为空！");
				res = new JsonResponse(result);
				return res;
			}

			String uuid = UUIDUtils.getUUID();
			entity.setId(uuid);
			entity.setCreatetime(new Date());
			entity.setDeleteflag(ConstantUtils.DELETE_FALSE);
			WorshipOblation oblation = oblationMapper.selectByPrimaryKey(entity.getOblationid());
			entity.setOblation(oblation.getOblation());
			entity.setOblationtype(oblation.getOblationtype());
			entity.setOblationtypeid(oblation.getOblationtypeid());
			User createuser = userDao.selectByPrimaryKey(entity.getCreateid());
			User worshipuser = userDao.selectByPrimaryKey(entity.getWorshipid());
			entity.setCreatename(createuser.getUsername());
			entity.setWorshipname(worshipuser.getUsername());
			int status = worshipMapper.insertSelective(entity);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[worship方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse getWorships(Worship entity, int start, int count) {
		Result result = null;
		JsonResponse res = null;
		List<Worship> worships = null;
		try {
			if (entity.getWorshipid() == null || "".equals(entity.getWorshipid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worishipid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("worshipid", entity.getWorshipid());
			//params.put("createid",WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID));
			params.put("deleteflag", ConstantUtils.DELETE_FALSE);
			params.put("start", start);
			params.put("count", count);

			worships = worshipMapper.getWorships(params);

		} catch (Exception e) {
			log_.error("[getWorships方法---异常:]", e);
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
	public JsonResponse getWorshipDetali(Worship entity) {
		Result result = null;
		JsonResponse res = null;
		WorshipVO worshipVo = null;
		try {
			if (entity.getWorshipid() == null || "".equals(entity.getWorshipid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worishipid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (entity.getCreateid() == null || "".equals(entity.getCreateid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数当前用户createid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			worshipVo = new WorshipVO();
			UserQuery example = new UserQuery();
			example.or().andUseridEqualTo(entity.getWorshipid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			List<User> list = userDao.selectByExample(example);
			if (list != null && list.size() > 0) {
				worshipVo.setImg(list.get(0).getImgurl());
			}
			WorshipOblationTypeExample example1 = new WorshipOblationTypeExample();
			example1.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			List<WorshipOblationType> types = oblationTypeMapper.selectByExample(example1);
			List<Worship> rtnlist = new ArrayList<>();
			for (WorshipOblationType type : types) {

				List<Worship> worships = worshipMapper.selectNoTimeOutByType(entity.getWorshipid(), type.getId());
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

	@Override
	public JsonResponse getWorshipOblations(Worship entity) {
		Result result = null;
		JsonResponse res = null;
		List<WorshipOblationVO> vos = null;
		try {
			if (entity.getCreateid() == null || "".equals(entity.getCreateid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数当前用户createid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			WorshipOblationExample example = new WorshipOblationExample();
			example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			example.setOrderByClause("sort asc");
			List<WorshipOblation> oblations = oblationMapper.selectByExample(example);
			WorshipOblationTypeExample ex = new WorshipOblationTypeExample();
			ex.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			ex.setOrderByClause("sort asc");
			List<WorshipOblationType> types = oblationTypeMapper.selectByExample(ex);

			vos = new ArrayList<WorshipOblationVO>();
			for (WorshipOblationType type : types) {
				WorshipOblationVO vo = new WorshipOblationVO();
				vo.setTypeid(type.getId());
				vo.setTypename(type.getTypename());
				List<WorshipOblation> list = new ArrayList<WorshipOblation>();
				boolean flag;
				for (int i = 0; i < oblations.size(); i++) {
					WorshipOblation oblation = oblations.get(i);
					flag = false;
					if (oblation.getOblationtypeid().equals(type.getId())) {
						list.add(oblation);
						flag = true;
					}
					if (flag) {
						oblations.remove(i);
						i--;
					}
				}
				vo.setOblations(list);
				vos.add(vo);
			}
		} catch (Exception e) {
			log_.error("[getWorshipOblations方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(vos);
		return res;
	}

	@Override
	public JsonResponse uploadPhoto(Worship entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			JsonResponse demoUser = userService.checkDemoUser();
			if (demoUser.getCode() == 1) {
				return demoUser;
			}
			if (entity.getWorshipid() == null || "".equals(entity.getWorshipid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worshipid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (entity.getAnnex() == null) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("附件不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			WorshipAnnexExample example = new WorshipAnnexExample();

			WorshipAnnex annex = entity.getAnnex();
			annex.setDeleteflag(0);
			annex.setUserid(entity.getWorshipid());
			example.or().andUseridEqualTo(entity.getWorshipid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			List<WorshipAnnex> list = annexMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				annex.setId(list.get(0).getId());
				annexMapper.updateByPrimaryKeySelective(annex);
			} else {
				annex.setId(UUIDUtils.getUUID());
				annexMapper.insertSelective(annex);
			}
		} catch (Exception e) {
			log_.error("[uploadPhoto方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

	public List<Worship> computingTime(List<Worship> worships) {
		Long time = System.currentTimeMillis();
		List<Worship> rtnList = new ArrayList<Worship>();
		if (worships != null && worships.size() > 0) {
			for (Worship worship : worships) {
				WorshipOblation oblation = oblationMapper.selectByPrimaryKey(worship.getOblationid());
				Date date = worship.getCreatetime();
				if (time - date.getTime() < oblation.getDuration() * 1000 * 60) {
					rtnList.add(worship);
				}
			}
		}
		return rtnList;
	}
	//
	// @Override
	// public Result getActiveUserOblations(Worship entity) {
	// Result result = new Result();
	// if(entity.getWorshipid()==null || "".equals(entity.getWorshipid())) {
	// result.setStatus(Constants.RESULT_FAIL);
	// result.setMsg("参数被祭拜人worshipid为空");
	// return result;
	// }
	//
	// List<Worship> worships = worshipMapper.getActiveUserOblations(entity);
	//
	// result.setData(worships);
	// result.setStatus(Constants.RESULT_SUCCESS);
	// result.setMsg("获取成功");
	// return result;
	// }

	@Override
	public JsonResponse getMyWorships(Worship entity, int start, int count) {
		Result result = null;
		JsonResponse res = null;
		List<Worship> list = null;
		try {
			if (entity.getWorshipid() == null || "".equals(entity.getWorshipid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数worishipid不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("worshipid", entity.getWorshipid());
			params.put("createid", WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID));
			params.put("deleteflag", ConstantUtils.DELETE_FALSE);
			params.put("start", start);
			params.put("count", count);

			list = worshipMapper.getWorships(params);
		} catch (Exception e) {
			log_.error("[getMyWorships方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(list);
		return res;
	}

}
