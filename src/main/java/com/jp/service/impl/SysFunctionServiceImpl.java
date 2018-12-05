package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.PageModel;
import com.jp.dao.SysFunctionDao;
import com.jp.entity.SysFunction;
import com.jp.entity.SysFunctionQuery;
import com.jp.entity.SysFunctionQuery.Criteria;
import com.jp.service.SysFunctionService;
import com.jp.util.StringTools;

@Service
public class SysFunctionServiceImpl implements SysFunctionService {

	@Autowired
	private SysFunctionDao sysFunctionDao;

	@Override
	public PageModel<SysFunction> pageQuery(PageModel<SysFunction> pageModel,SysFunction sysFunction) throws Exception {
		
		SysFunctionQuery sfq = new SysFunctionQuery();
		
		Criteria createCriteria = sfq.createCriteria();
		
		if(StringTools.trimNotEmpty(sysFunction.getFunctionname())){
			createCriteria.andFunctionnameLike("%"+sysFunction.getFunctionname().trim()+"%");
		}
		if(StringTools.notEmpty(sysFunction.getParentid())){
			createCriteria.andParentidEqualTo(sysFunction.getParentid());
		}
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		sfq.setOrderByClause(" sort desc");
		List<SysFunction> list = sysFunctionDao.selectByExample(sfq);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<SysFunction>(list));
		return pageModel;
	}
	
	
	@Override
	public List<SysFunction> selectListByParnetid(String parentid) throws Exception {
		
		SysFunctionQuery sfq = new SysFunctionQuery();
		
		Criteria createCriteria = sfq.createCriteria();
		
		if(StringTools.notEmpty(parentid)){
			createCriteria.andParentidEqualTo(parentid);
		}
		sfq.setOrderByClause(" sort desc");

		List<SysFunction> list = sysFunctionDao.selectByExample(sfq);
		return list;
	}
	
	@Override
	public List<SysFunction> selectFunctionListByVersionid(String versionid) throws Exception {
		
		return sysFunctionDao.selectFunctionListByVersionid(versionid);
	}
	
	@Override
	public int insert(SysFunction sysFunction) throws Exception {
		
		return sysFunctionDao.insertSelective(sysFunction);
	}
	
	@Override
	public int delete(String functionid) throws Exception{
		return sysFunctionDao.deleteByPrimaryKey(functionid);
	}

	@Override
	public int update(SysFunction sysFunction) throws Exception{
		return sysFunctionDao.updateByPrimaryKeySelective(sysFunction);
	}
	
	@Override
	public SysFunction get(String functionid) throws Exception{
		return sysFunctionDao.selectByPrimaryKey(functionid);
	}
}
