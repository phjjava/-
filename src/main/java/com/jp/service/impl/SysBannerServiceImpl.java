package com.jp.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.PageModel;
import com.jp.dao.SysBannerDao;
import com.jp.dao.BranchphotoMapper;
import com.jp.dao.SysMationdao;
import com.jp.entity.BannerHomePage;
import com.jp.entity.BannerQuery;
import com.jp.entity.SysMation;
import com.jp.entity.BannerQuery.Criteria;
import com.jp.service.SysBannerService;


@Service
public class SysBannerServiceImpl implements SysBannerService{
	@Autowired
	private SysBannerDao pagehomeDao;
	
	@Autowired
    private SysMationdao badao;
    
	
    @Autowired
    BranchphotoMapper branchphotoDao;

	@Override
	public PageModel<BannerHomePage> pageQuery(PageModel<BannerHomePage> pageModel, BannerHomePage banner) {
		// TODO Auto-generated method stub
		BannerQuery bq = new BannerQuery();
		Criteria createCriteria = bq.createCriteria();
		if(banner.getDeleteflag() != null){
			createCriteria.andDeleteflagEqualTo(banner.getDeleteflag());
		}
		/*createCriteria.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());*/
		
		bq.setOrderByClause("createtime DESC");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<BannerHomePage> list = pagehomeDao.selectByExample(bq);
		
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<BannerHomePage>(list));
		return pageModel;
	}

	@Override
	public BannerHomePage get(String bannerid) throws Exception {
		// TODO Auto-generated method stub
		BannerQuery dq = new BannerQuery();
		Criteria createCriteria = dq.createCriteria();
		createCriteria.andBanneridEqualTo(bannerid);
		List<BannerHomePage> banner = pagehomeDao.selectByExample(dq);
		return banner.get(0);
	}

	@Override
	public Integer changeStatus(BannerHomePage banner) {
		// TODO Auto-generated method stub
		int count=pagehomeDao.updateByPrimaryKeySelective(banner);
		if(count==1){
		  return count;
		}else{
		  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		  return 0;
		}
	}

	@Override
	public Integer update(BannerHomePage banner) {
		// TODO Auto-generated method stub
		return pagehomeDao.updateByPrimaryKeySelective(banner);
	}

	@Override
	public Integer insert(BannerHomePage banner) {
		// TODO Auto-generated method stub
		return pagehomeDao.insertSelective(banner);
	}

	@Override
	public int batchDelete(String[] bannerids) {
		// TODO Auto-generated method stub
		return pagehomeDao.batchDelete(bannerids);
	}
	
	@Override
	public List<SysMation>  selectByGoType(String goType) { 
		//SysMation goTypeResult = null;
		//List<SysGoTypeResult> goTypeResultList = new ArrayList<SysGoTypeResult>();
		//动态
		 if(goType.equals("1")){
			 //MationExample example = new MationExample();
			 return badao.selectByExample();
		 }else {
			 return null;
		 }
		
	}

	@Override
	public void realDelete(String bannerid) {
		// TODO Auto-generated method stub
		pagehomeDao.deleteByPrimaryKey(bannerid);
	}


	@Override
	public SysMation SelectMationOne(String mationid) {
		// TODO Auto-generated method stub	
		return badao.selectByPrimaryKey(mationid);
	}

	@Override
	public int batchDeleteAll(String[] bannerids) {
		// TODO Auto-generated method stub
		return pagehomeDao.batchDeleteAll(bannerids);
		
	}

	@Override
	public void updateCount(String mationid) {
		// TODO Auto-generated method stub
		badao.updateCount(mationid);
	}

	


}
