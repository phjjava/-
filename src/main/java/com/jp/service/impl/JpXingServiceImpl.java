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
import com.jp.dao.JpXingMapper;
import com.jp.entity.InstructionTemplateQuery;
import com.jp.entity.JpXing;
import com.jp.service.JpXingService;
import com.jp.util.StringTools;

@Service
public class JpXingServiceImpl implements JpXingService{
	@Autowired
	private JpXingMapper xingMapper;

	@Override
	public Integer update(JpXing jpxing) {
		// TODO Auto-generated method stub
		return xingMapper.updateByPrimaryKeySelective(jpxing);
	}

	@Override
	public Integer insert(JpXing jpxing) {
		// TODO Auto-generated method stub
		return xingMapper.insertSelective(jpxing);
	}

	@Override
	public PageModel<JpXing> pageQuery(PageModel<JpXing> pageModel, JpXing xing,String xname) {
		// TODO Auto-generated method stub
		InstructionTemplateQuery iq = new InstructionTemplateQuery();
		com.jp.entity.InstructionTemplateQuery.Criteria criteria = iq.createCriteria();
		xing.setDeleteflag(0);
		if (StringTools.trimNotEmpty(xing.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(xing.getDeleteflag());
		}
		iq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		if(xname==null) {
			return null;
		}else if(xname.equals("ALL")){
			List<JpXing> list = xingMapper.selectByExample(iq);
			pageModel.setList(list);
			pageModel.setPageInfo(new PageInfo<JpXing>(list));
			return pageModel;
		}else{
			List<JpXing> list = xingMapper.selectByExample1(xname,iq);
			if(list!=null) {
				xingMapper.updateRcount(xname);
			}
			pageModel.setList(list);
			return pageModel;
		}
		
		
	}

	@Override
	public JpXing get(String id) {
		// TODO Auto-generated method stub
		return xingMapper.selectByPrimaryKey(id);
	}

	@Override
	public int xingDeleteAll(String[] ids) {
		// TODO Auto-generated method stub
		return xingMapper.xingDeleteAll(ids);
	}

	@Override
	public int SelectCount(String xname) {
		// TODO Auto-generated method stub
		return xingMapper.SelectCount(xname);
	}

	@Override
	public List<JpXing> hotlist() {
		// TODO Auto-generated method stub
		return xingMapper.hotlist();
	}
	/**
	 * api百家姓接口
	 */
	@Override
	public JsonResponse pageQuery1(PageModel<JpXing> pageModel, JpXing xing,String xname) {
		// TODO Auto-generated method stub
		JsonResponse res = null;
		Result result = null;
		InstructionTemplateQuery iq = new InstructionTemplateQuery();
		com.jp.entity.InstructionTemplateQuery.Criteria criteria = iq.createCriteria();
		xing.setDeleteflag(0);
		if (StringTools.trimNotEmpty(xing.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(xing.getDeleteflag());
		}
		iq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		//判断查询条件
		if(xname==null) {
			return null;
		}else if(xname.equals("ALL")){
			List<JpXing> list = xingMapper.selectByExampleNew(iq);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(list);
			if(list==null) {
				res.setCount(0);
			}else {
				if(xname.equals("ALL")) {
					xname=null;
					res.setCount(xingMapper.SelectCount(xname));
				}else {
					res.setCount(xingMapper.SelectCount(xname));
				}
			}
			return res;
		}else{
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			List<JpXing> list = xingMapper.selectByExample1(xname,iq);
			if(list!=null) {
				xingMapper.updateRcount(xname);
			}
			if(list==null) {
				res.setCount(0);
			}else {
				res.setCount(xingMapper.SelectCount(xname));
			}
			res.setData(list);
			return res;
		}
		
		
	}
	

}
