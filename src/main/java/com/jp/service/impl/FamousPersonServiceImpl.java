package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.FamousPersonMapper;
import com.jp.entity.BannerQuery;
import com.jp.entity.BannerQuery.Criteria;
import com.jp.entity.FamousPerson;
import com.jp.service.FamousPersonService;

@Service
public class FamousPersonServiceImpl implements FamousPersonService {
	@Autowired
	private FamousPersonMapper personMapper;

	@Override
	public PageModel<FamousPerson> pageQuery(PageModel<FamousPerson> pageModel, FamousPerson famousPerson) {
		BannerQuery bq = new BannerQuery();
		Criteria createCriteria = bq.createCriteria();
		if (famousPerson.getDeleteflag() != null) {
			createCriteria.andDeleteflagEqualTo(famousPerson.getDeleteflag());
		}

		bq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<FamousPerson> list = personMapper.selectByExample(bq);

		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<FamousPerson>(list));
		return pageModel;
	}

	@Override
	public FamousPerson get(String famousid) {
		// TODO Auto-generated method stub
		return personMapper.selectByPrimaryKey(famousid);
	}

	@Override
	public Integer changeStatus(FamousPerson famousPerson) {
		// TODO Auto-generated method stub
		return personMapper.updateByPrimaryKeySelective(famousPerson);
	}

	@Override
	public Integer update(FamousPerson famousPerson) {
		// TODO Auto-generated method stub
		return personMapper.updateByPrimaryKeySelective(famousPerson);
	}

	@Override
	public Integer insert(FamousPerson famousPerson) {
		// TODO Auto-generated method stub
		return personMapper.insertSelective(famousPerson);
	}

	@Override
	public Integer batchDeleteAll(String[] famousids) {
		// TODO Auto-generated method stub
		return personMapper.batchDeleteAll(famousids);
	}

	@Override
	public JsonResponse pageQueryApi(PageModel<FamousPerson> pageModel, FamousPerson famousPerson) {
		// TODO Auto-generated method stub
		JsonResponse res = null;
		Result result = null;
		BannerQuery bq = new BannerQuery();
		Criteria createCriteria = bq.createCriteria();
		if (famousPerson.getDeleteflag() != null) {
			createCriteria.andDeleteflagEqualTo(famousPerson.getDeleteflag());
		}
		bq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<FamousPerson> list = personMapper.selectByExampleNew(bq);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(list);
		return res;
	}

}
