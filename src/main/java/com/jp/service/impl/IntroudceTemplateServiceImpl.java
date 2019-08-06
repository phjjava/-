package com.jp.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.IntroudceTemplateDao;
import com.jp.entity.InstructionTemplateQuery;
import com.jp.entity.IntroudceTemplate;
import com.jp.service.IntroudceTemplateService;
import com.jp.util.StringTools;

@Service
public class IntroudceTemplateServiceImpl implements IntroudceTemplateService{
	@Autowired IntroudceTemplateDao inDao;

	@Override
	public IntroudceTemplate get(String id) {
		return inDao.selectByPrimaryKey(id);
	}

	@Override
	public PageModel<IntroudceTemplate> pageQuery(PageModel<IntroudceTemplate> pageModel, IntroudceTemplate intemplate)
			throws Exception {
		InstructionTemplateQuery iq = new InstructionTemplateQuery();
		com.jp.entity.InstructionTemplateQuery.Criteria criteria = iq.createCriteria();
		//criteria.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
		// criteria.andFamilyidEqualTo(introduce.getFamilyid());
		intemplate.setDeleteflag(0);
		if (StringTools.trimNotEmpty(intemplate.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(intemplate.getDeleteflag());
		}
		iq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<IntroudceTemplate> list = inDao.selectByExample(iq);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<IntroudceTemplate>(list));
		return pageModel;
	}

	@Override
	public Integer insert(IntroudceTemplate intemplate) {
		// TODO Auto-generated method stub
		return inDao.insertSelective(intemplate);
	}

	@Override
	public Integer update(IntroudceTemplate intemplate) {
		// TODO Auto-generated method stub
		return inDao.updateByPrimaryKeySelective(intemplate);
	}

	@Override
	public Integer changeStatus(IntroudceTemplate intemplate) {
		// TODO Auto-generated method stub
		int count=inDao.updateByPrimaryKeySelective(intemplate);
		if(count==1){
		  return count;
		}else{
		  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		  return 0;
		}
	}

	@Override
	public void batchDelete(String[] ids) throws Exception {
		// TODO Auto-generated method stub
		inDao.intemplateDeleteAll(ids);
	}

}
