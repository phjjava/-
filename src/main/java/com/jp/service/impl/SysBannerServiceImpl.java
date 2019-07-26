package com.jp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.SysBannerDao;
import com.jp.dao.BranchDao;
import com.jp.dao.BranchalbumMapper;
import com.jp.dao.BranchphotoMapper;
import com.jp.dao.DynamicMapper;
import com.jp.dao.EventDao;
import com.jp.dao.IntroduceDao;
import com.jp.dao.MationMapper;
import com.jp.dao.NoticeMapper;
import com.jp.dao.UserDao;
import com.jp.entity.BannerHomePage;
import com.jp.entity.BannerQuery;
import com.jp.entity.Branchalbum;
import com.jp.entity.BranchalbumExample;
import com.jp.entity.Branchphoto;
import com.jp.entity.Dynamic;
import com.jp.entity.DynamicExample;
import com.jp.entity.Event;
import com.jp.entity.GoTypeResult;
import com.jp.entity.Introduce;
import com.jp.entity.IntroduceQuery;
import com.jp.entity.MationExample;
import com.jp.entity.Notice;
import com.jp.entity.NoticeExample;
import com.jp.entity.NoticeVO;
import com.jp.entity.SysMation;
import com.jp.entity.SysUser;
import com.jp.entity.Usercontent;
import com.jp.entity.BannerQuery.Criteria;
import com.jp.service.SysBannerService;
import com.jp.util.DateUtils;
import com.jp.util.StringTools;

@Service
public class SysBannerServiceImpl implements SysBannerService{
	@Autowired
	private SysBannerDao pagehomeDao;
	
	@Autowired
    private MationMapper badao;
    
	
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
	public List<GoTypeResult> selectByGoType(String goType) { 
		SysMation goTypeResult = null;
		List<GoTypeResult> goTypeResultList = new ArrayList<GoTypeResult>();
		//Integer type = 1;//CurrentUserContext.getUserContext().getUsermanagers().get(0).getEbtype();
		//String familyid = CurrentUserContext.getCurrentFamilyId();
		//动态
		 if(goType.equals("1")){
			 MationExample example = new MationExample();
			List<SysMation> list = badao.selectByExample(example);
			HashMap map=new HashMap();
			System.out.println("跳转资讯list="+list);
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					 goTypeResult = new SysMation();
					 goTypeResult.setMationid(list.get(i).getMationid());
					 goTypeResult.setMationname(list.get(i).getMationname());
					 goTypeResult.setImgid(list.get(i).getImgid());
					 goTypeResult.setImgurl(list.get(i).getImgurl());
					 goTypeResult.setDeleteflag(list.get(i).getDeleteflag());
					 goTypeResult.setCreateid(list.get(i).getCreateid());
					 goTypeResult.setCreatetime(list.get(i).getCreatetime());
					 goTypeResult.setUpdateid(list.get(i).getUpdateid());
					 goTypeResult.setUpdateid(list.get(i).getUpdateid());
					 goTypeResult.setUpdatetime(list.get(i).getUpdatetime());
					
				}
			}
		}
		return goTypeResultList;
	}


}
