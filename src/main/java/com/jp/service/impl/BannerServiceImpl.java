package com.jp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.BannerDao;
import com.jp.dao.BranchDao;
import com.jp.dao.BranchalbumMapper;
import com.jp.dao.BranchphotoMapper;
import com.jp.dao.DynamicMapper;
import com.jp.dao.EventDao;
import com.jp.dao.IntroduceDao;
import com.jp.dao.NoticeMapper;
import com.jp.dao.UserDao;
import com.jp.entity.Banner;
import com.jp.entity.BannerQuery;
import com.jp.entity.BannerQuery.Criteria;
import com.jp.entity.Branch;
import com.jp.entity.Branchalbum;
import com.jp.entity.BranchalbumExample;
import com.jp.entity.Branchphoto;
import com.jp.entity.BranchphotoExample;
import com.jp.entity.Dynamic;
import com.jp.entity.DynamicExample;
import com.jp.entity.Event;
import com.jp.entity.GoTypeResult;
import com.jp.entity.Introduce;
import com.jp.entity.IntroduceQuery;
import com.jp.entity.Notice;
import com.jp.entity.NoticeExample;
import com.jp.entity.NoticeVO;
import com.jp.entity.Usercontent;
import com.jp.service.BannerService;
import com.jp.util.DateUtils;
import com.jp.util.GsonUtil;
import com.jp.util.StringTools;
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bdao;
    @Autowired
    private DynamicMapper dynamicDao;
    @Autowired
    private BranchDao branchDao;
    @Autowired
    private BranchalbumMapper badao;
    @Autowired
	private EventDao edao;
    @Autowired
	private UserDao userDao;
    @Autowired
    private IntroduceDao itdao;
    @Autowired
    private NoticeMapper noticedao;
    @Autowired
    BranchphotoMapper branchphotoDao;
	@Override
	public PageModel<Banner> pageQuery(PageModel<Banner> pageModel,Banner banner) throws Exception {
		BannerQuery bq = new BannerQuery();
		Criteria createCriteria = bq.createCriteria();
		if(banner.getDeleteflag() != null){
			createCriteria.andDeleteflagEqualTo(banner.getDeleteflag());
		}
		createCriteria.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
		bq.setOrderByClause("createtime DESC");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Banner> list = bdao.selectByExample(bq);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Banner>(list));
		return pageModel;
	}

	@Override
	public Banner get(String bannerid) throws Exception {
		BannerQuery dq = new BannerQuery();
		Criteria createCriteria = dq.createCriteria();
		createCriteria.andBanneridEqualTo(bannerid);
		List<Banner> banner = bdao.selectByExample(dq);
		return banner.get(0);
	}

	@Override
	public int changeStatus(Banner banner) throws Exception {
		
		int count=bdao.updateByPrimaryKeySelective(banner);
		if(count==1){
		  return count;
		}else{
		  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		  return 0;
		}
	}

	@Override
	public int insert(Banner banner) throws Exception {
		
		return bdao.insertSelective(banner);
	}

	@Override
	public int update(Banner banner) throws Exception {
		return bdao.updateByPrimaryKeySelective(banner);
	}

	@Override
	public int batchDelete(String[] bannerids) throws Exception {
		return bdao.batchDelete(bannerids);
	}
	
	@Override
	public String selectByGoType(String goType) { 
		GoTypeResult goTypeResult = null;
		List<GoTypeResult> goTypeResultList = new ArrayList<GoTypeResult>();
		Integer type = CurrentUserContext.getUserContext().getUsermanagers().get(0).getEbtype();
		String familyid = CurrentUserContext.getCurrentFamilyId();
		//动态
		if(goType.equals("1")){
			List<String> branchIds = CurrentUserContext.getCurrentBranchIds();
			
//			if(type == 1){
//				branchIds.clear();//验证是否是总编委会
//				Branch branch = new Branch();
//				branch.setFamilyid(CurrentUserContext.getCurrentFamilyId());
//				List<Branch> branchList = branchDao.selectBranchList(branch);
//				if(branchList != null && branchList.size() > 0){
//					for (int i = 0; i < branchList.size(); i++) {
//						branchIds.add(branchList.get(i).getBranchid());
//					}
//				}
//			}
			List<Dynamic> dynamicList = null;
			if(!branchIds.equals("")){
				if(type==1) {
					DynamicExample ex = new DynamicExample();
					ex.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
					dynamicList = dynamicDao.selectByExample(ex);
				}else {
					dynamicList = dynamicDao.selectGoType(branchIds);
				}
				 
			}
			if(dynamicList != null){
				 for(int j = 0; j < dynamicList.size(); j ++){
					 goTypeResult = new GoTypeResult();
					 goTypeResult.setId(dynamicList.get(j).getDyid());
					 goTypeResult.setName(dynamicList.get(j).getDytitle());
					 goTypeResultList.add(goTypeResult);
				 }
			}
		}else if(goType.equals("2")){
			BranchalbumExample example = new BranchalbumExample();
			example.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			List<Branchalbum> list = badao.selectBranchAlbumMangeList(example);
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					 goTypeResult = new GoTypeResult();
					 goTypeResult.setId(list.get(i).getAlbumid());
					 goTypeResult.setName(list.get(i).getAlbumname());
					 goTypeResultList.add(goTypeResult);
				}
			}
		}else if(goType.equals("3")){
			Event event = new Event();
			event.setFamilyid(familyid);
			List<Event> list = edao.selecteventread(event);
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					 goTypeResult = new GoTypeResult();
					 goTypeResult.setId(list.get(i).getEventid());
					 goTypeResult.setName(list.get(i).getEventtitle());
					 goTypeResultList.add(goTypeResult);
				}
			}
		}else if(goType.equals("4")){
			Usercontent usercontent = new Usercontent();
			usercontent.setFamilyid(familyid);
			List<Usercontent> list = userDao.selectUserContentList(usercontent);
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					 goTypeResult = new GoTypeResult();
					 goTypeResult.setId(list.get(i).getUserid());
					 goTypeResult.setName(list.get(i).getUsername());
					 goTypeResultList.add(goTypeResult);
				}
			}
		}else if(goType.equals("5")){
			Introduce introduce = new Introduce();
			IntroduceQuery iq = new IntroduceQuery();
			com.jp.entity.IntroduceQuery.Criteria criteria = iq.createCriteria();
			criteria.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
			introduce.setDeleteflag(0);
			if(StringTools.trimNotEmpty(introduce.getDeleteflag())){
				criteria.andDeleteflagEqualTo(introduce.getDeleteflag());
			}
			iq.setOrderByClause("sort");
			List<Introduce> list = itdao.selectByExample(iq);
			if(list != null){
				for (int i = 0; i < list.size(); i++) {
					 goTypeResult = new GoTypeResult();
					 goTypeResult.setId(list.get(i).getIntroduceid());
					 goTypeResult.setName(list.get(i).getIntroducetitle());
					 goTypeResultList.add(goTypeResult);
				}
			}
		}else if(goType.equals("6")){
			Notice notice = new Notice();
			NoticeExample nq = new NoticeExample();
			com.jp.entity.NoticeExample.Criteria criteria = nq.createCriteria();
			if(StringTools.trimNotEmpty(notice.getType())){
				criteria.andTypeEqualTo(notice.getType());
			}
			if(StringTools.trimNotEmpty(notice.getBranchid())){
				criteria.andBranchidEqualTo(notice.getBranchid());
			}
			if(StringTools.trimNotEmpty(notice.getDeleteflag())){
				criteria.andDeleteflagEqualTo(notice.getDeleteflag());
			}
			List<String> currentBranchIds = CurrentUserContext.getCurrentBranchIds();
			if (currentBranchIds!=null&&currentBranchIds.size()>0) {
				criteria.andBranchidIn(CurrentUserContext.getCurrentBranchIds());
				List<NoticeVO> list = noticedao.selectNoticeMangeList(nq);
				if(list != null){
					for (int i = 0; i < list.size(); i++) {
						 goTypeResult = new GoTypeResult();
						 goTypeResult.setId(list.get(i).getNoticeid());
						 goTypeResult.setName(list.get(i).getNoticecontent());
						 goTypeResultList.add(goTypeResult);
					}
				}
			}
		}else if(goType.equals("7")){
			List<String> branchids = CurrentUserContext.getCurrentBranchIds();
			if(StringTools.trimNotEmpty(branchids)){
				List<Branchphoto> list = new ArrayList<>();
				if(type==1) {
					list = branchphotoDao.selectByFamilyid(familyid);
				}else {
					list = branchphotoDao.selectByBranch(branchids);
				}
				
				if(list != null){
					for (int i = 0; i < list.size(); i++) {
						 goTypeResult = new GoTypeResult();
						 goTypeResult.setId(list.get(i).getImgurl());
						 goTypeResult.setName(list.get(i).getDescription());
						 goTypeResult.setAlbumname(list.get(i).getAlbumname());
						 goTypeResult.setDate(DateUtils.FormatDate(list.get(i).getCreatetime(), "yyyy-MM-dd"));
						 goTypeResultList.add(goTypeResult);
					}
				}
			}
		}
		return GsonUtil.GsonString(goTypeResultList);
	}
}
