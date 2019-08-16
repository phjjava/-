package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.PageModel;
import com.jp.dao.SynopsisDao;
import com.jp.entity.BannerHomePage;
import com.jp.entity.BannerQuery;
import com.jp.entity.JpSynopsis;
import com.jp.entity.BannerQuery.Criteria;
import com.jp.service.SynopsisService;

@Service
public class SynopsisServiceImpl implements SynopsisService{
	@Autowired
	private SynopsisDao syDao;

	@Override
	public PageModel<JpSynopsis> pageQuery(PageModel<JpSynopsis> pageModel, JpSynopsis jpSynopsis) {
		// TODO Auto-generated method stub
		BannerQuery bq = new BannerQuery();
		Criteria createCriteria = bq.createCriteria();
		if(jpSynopsis.getDeleteflag() != null){
			createCriteria.andDeleteflagEqualTo(jpSynopsis.getDeleteflag());
		}
		bq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<JpSynopsis> list = syDao.selectByExample(bq);
		
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<JpSynopsis>(list));
		return pageModel;
	}

	@Override
	public JpSynopsis get(String id) {
		// TODO Auto-generated method stub
		return syDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer changeStatus(JpSynopsis jpSynopsis) {
		// TODO Auto-generated method stub
		int count=syDao.updateByPrimaryKeySelective(jpSynopsis);
		if(count==1){
		  return count;
		}else{
		  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		  return 0;
		}
	}

	@Override
	public Integer update(JpSynopsis jpSynopsis) {
		// TODO Auto-generated method stub
		return syDao.updateByPrimaryKeySelective(jpSynopsis);
	}

	@Override
	public Integer insert(JpSynopsis jpSynopsis) {
		// TODO Auto-generated method stub
		return syDao.insertSelective(jpSynopsis);
	}

	@Override
	public int batchDelete(String[] ids) {
		// TODO Auto-generated method stub
		return syDao.batchDelete(ids);
	}

}
