package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.IndexMapper;
import com.jp.dao.IntroduceDao;
import com.jp.entity.Index;
import com.jp.entity.IndexExample;
import com.jp.entity.Introduce;
import com.jp.entity.IntroduceQuery;
import com.jp.entity.IntroduceQuery.Criteria;
import com.jp.service.IntroduceService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class IntroduceServiceImpl implements IntroduceService {
	@Autowired
	private IntroduceDao itdao;
	@Autowired
	private IndexMapper indexMapper;

	@Override
	public PageModel<Introduce> pageQuery(PageModel<Introduce> pageModel, Introduce introduce) throws Exception {
		IntroduceQuery iq = new IntroduceQuery();
		Criteria criteria = iq.createCriteria();
		criteria.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
		// criteria.andFamilyidEqualTo(introduce.getFamilyid());
		introduce.setDeleteflag(0);
		if (StringTools.trimNotEmpty(introduce.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(introduce.getDeleteflag());
		}
		iq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Introduce> list = itdao.selectByExample(iq);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Introduce>(list));
		return pageModel;
	}

	@Override
	public Introduce get(String introduceid) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		IntroduceQuery iq = new IntroduceQuery();
		Criteria createCriteria = iq.createCriteria();
		createCriteria.andIntroduceidEqualTo(introduceid);
		List<Introduce> introduce = itdao.selectByExample(iq);
		if (introduce != null && introduce.size() > 0) {
			if (introduce.get(0).getCreatetime() != null) {
				introduce.get(0).setCreatetimeStr(formatter.format(introduce.get(0).getCreatetime()));
			}
			return introduce.get(0);
		} else {
			return null;
		}
	}

	@Override
	public JsonResponse saveIntroduce(HttpServletRequest request, Introduce introduce) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		// 编辑
		try {
			if (request.getCharacterEncoding() == null) {
				request.setCharacterEncoding("UTF-8");
			}
			if (StringTools.trimNotEmpty(introduce.getIntroduceid())) {
				introduce.setUpdateid(CurrentUserContext.getCurrentUserId());
				introduce.setUpdatetime(new Date());
				status = itdao.updateByPrimaryKeySelective(introduce);
			} else {
				// 新增
				String introduceid = UUIDUtils.getUUID();
				introduce.setIntroduceid(introduceid);
				introduce.setCreateid(CurrentUserContext.getCurrentUserId());
				introduce.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				introduce.setDeleteflag(0);
				Date insertDate = new Date();
				introduce.setCreatetime(insertDate);
				introduce.setType("ELSE");
				status = itdao.insertSelective(introduce);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public int batchDelete(String[] introduceids) throws Exception {
		return itdao.batchDelete(introduceids);
	}

	/**
	 * 以下方法用于api
	 */

	@Override
	public JsonResponse getIntroduceList(Introduce entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if ("".equals(entity.getFamilyid()) || entity.getFamilyid() == null) {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("当前家族不存在");
				return res;
			}
			IntroduceQuery introduceExample = new IntroduceQuery();
			introduceExample.or().andFamilyidEqualTo(entity.getFamilyid()).andDeleteflagEqualTo(0);
			introduceExample.setOrderByClause("sort asc");
			java.util.List<Introduce> introduces = itdao.selectByExample(introduceExample);

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(introduces);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public JsonResponse getIntroduceDetail(Introduce entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if ("".equals(entity.getIntroduceid()) || entity.getIntroduceid() == null) {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("当前目录不存在");
				return res;
			}

			IntroduceQuery introduceExample = new IntroduceQuery();
			introduceExample.or().andIntroduceidEqualTo(entity.getIntroduceid()).andDeleteflagEqualTo(0);
			List<Introduce> introduces = itdao.selectByPrimaryID(entity.getIntroduceid());

			if (introduces.size() > 0) {
				Introduce introduce = introduces.get(0);
				// 获取上一章
				Map<String, String> map = new HashMap<String, String>();
				map.put("familyid", introduce.getFamilyid());
				map.put("sort", introduce.getSort().toString());
				Introduce preintroduce = itdao.selectPreIntroduce(map);
				if (preintroduce != null) {
					introduce.setPreintroduceid(preintroduce.getIntroduceid());
					introduce.setPreintroducetitle(preintroduce.getIntroducetitle());
				}
				///////
				// 获取下一章
				Introduce preintroduce2 = itdao.selectNextIntroduce(map);
				if (preintroduce2 != null) {
					introduce.setNextintroduceid(preintroduce2.getIntroduceid());
					introduce.setNextintroducetitle(preintroduce2.getIntroducetitle());
				}

				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(introduce);
			} else {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("获取失");
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public JsonResponse getMenuList(Introduce entity, HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = request.getHeader("userid");
			if (StringUtils.isBlank(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("用户非法，请重试！");
				return res;
			}
			IndexExample example = new IndexExample();
			example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			example.setOrderByClause("sort asc");
			if (entity.getStart() != null && entity.getCount() != null) {
				int start = entity.getStart().intValue();
				int count = entity.getCount().intValue();
				example.setPageNo(start / count + 1);
				example.setPageSize(count);
			}
			String familyid = request.getHeader("familyid");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("familyid", familyid);
			List<Index> list = indexMapper.selectByExample(example);
			for (Index index : list) {
				if ("XS".equals(index.getCode())) {
					params.put("type", "XS");
				}
				if ("PX".equals(index.getCode())) {
					params.put("type", "PX");
				}
				if ("JX".equals(index.getCode())) {
					params.put("type", "JX");
				}
				Introduce introduce = itdao.getIntroduceByType(params);
				if(introduce != null ) {
					index.setParam1(introduce.getIntroduceid());
					index.setParam2(introduce.getSort() + "");
				}
				
			}

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(list);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

}
