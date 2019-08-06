package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.jp.common.PageModel;
import com.jp.dao.IntroudceTemplateDao;
import com.jp.dao.IntroudceTemplateDetailDao;
import com.jp.entity.InstructionTemplateQuery;
import com.jp.entity.InstructionTemplateQuery.Criteria;
import com.jp.entity.IntroudceTemplate;
import com.jp.entity.IntroudceTemplateDetail;
import com.jp.service.IntroudceTemplateDetailService;
import com.jp.util.StringTools;

@Service
public class IntroudceTemplateDetailServiceImpl implements IntroudceTemplateDetailService{
	@Autowired IntroudceTemplateDetailDao inDetailDao;
	
	@Autowired IntroudceTemplateDao inDao;
	@Override
	public IntroudceTemplateDetail get(String id) {
		// TODO Auto-generated method stub
		return inDetailDao.selectByPrimaryKey(id);
	}

	@Override
	public PageModel<IntroudceTemplateDetail> pageQuery(PageModel<IntroudceTemplateDetail> pageModel,
			IntroudceTemplateDetail intemplateDetail,String title) throws Exception {
		// TODO Auto-generated method stub
		InstructionTemplateQuery iq = new InstructionTemplateQuery();
		Criteria criteria = iq.createCriteria();
		//criteria.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
		// criteria.andFamilyidEqualTo(introduce.getFamilyid());
		intemplateDetail.setDeleteflag(0);
		if (StringTools.trimNotEmpty(intemplateDetail.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(intemplateDetail.getDeleteflag());
		}
		iq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<IntroudceTemplateDetail> list = inDetailDao.selectByExample(title);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public Integer insert(IntroudceTemplateDetail intemplateDetail) {
		// TODO Auto-generated method stub
		return inDetailDao.insertSelective(intemplateDetail);
	}

	@Override
	public Integer update(IntroudceTemplateDetail intemplateDetail) {
		// TODO Auto-generated method stub
		return inDetailDao.updateByPrimaryKeySelective(intemplateDetail);
	}

	@Override
	public Integer changeStatus(IntroudceTemplateDetail intemplateDetail) {
		// TODO Auto-generated method stub
		int count=inDetailDao.updateByPrimaryKeySelective(intemplateDetail);
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
		inDetailDao.intemplateDeleteAll(ids);
	}

	@Override
	public List<IntroudceTemplate> selecttypelist() {
		// TODO Auto-generated method stub
		return inDao.selectByExample();
	}

	
}
