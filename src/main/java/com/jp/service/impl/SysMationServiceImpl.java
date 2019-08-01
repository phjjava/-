package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.SysMationdao;
import com.jp.dao.SysMtionTypeDao;
import com.jp.entity.BannerHomePage;
import com.jp.entity.BannerQuery;
import com.jp.entity.MationType;
import com.jp.entity.SysMation;
import com.jp.entity.BannerQuery.Criteria;
import com.jp.service.SysMationService;

@Service
public class SysMationServiceImpl implements SysMationService{
	@Autowired
    private SysMationdao badao;
	@Autowired
	private SysMtionTypeDao typedao;
	@Override
	public PageModel<SysMation> pageQuery(PageModel<SysMation> pageModel, SysMation mation,String mationtitle) {
		// TODO Auto-generated method stub
		BannerQuery bq = new BannerQuery();
		System.out.println("bq="+bq);
		Criteria createCriteria = bq.createCriteria();
		if(mation.getDeleteflag() != null){
			createCriteria.andDeleteflagEqualTo(mation.getDeleteflag());
		}
		
		bq.setOrderByClause("createtime DESC");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<SysMation> list;
		if(bq.equals(null)) {
			list = badao.selectByExample();
		}else {
			list = badao.selectByExample(mationtitle);
		}
		
		
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<SysMation>(list));
		return pageModel;
	}
	@Override
	public List<SysMation> selectByGoType(String goType) {
		// TODO Auto-generated method stub
		return badao.selectByExample();
	}
	@Override
	public Integer update(SysMation mation) {
		// TODO Auto-generated method stub
		return badao.updateByPrimaryKeySelective(mation);
	}
	@Override
	public Integer insert(SysMation mation) {
		// TODO Auto-generated method stub
		return badao.insertSelective(mation);
	}
	@Override
	public int batchDeleteAll(String[] mationids) throws Exception {
		// TODO Auto-generated method stub
		return badao.mationDeleteAll(mationids);
	}
	@Override
	public List<MationType> selecttypelist() {
		// TODO Auto-generated method stub
		return typedao.selecttypelist();
	}
	@Override
	public Integer updatetype(MationType mationtype) {
		// TODO Auto-generated method stub
		return typedao.updateByPrimaryKeySelective(mationtype);
	}
	@Override
	public Integer inserttype(MationType mationtype) {
		// TODO Auto-generated method stub
		return typedao.insertSelective(mationtype);
	}
	@Override
	public int mationtypeDeleteAll(String[] mationtypeids) throws Exception {
		// TODO Auto-generated method stub
		return typedao.mationtypeDeleteAll(mationtypeids);
	}
	

}
