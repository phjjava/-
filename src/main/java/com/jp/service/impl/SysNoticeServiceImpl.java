package com.jp.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.SysNoticeDao;
import com.jp.dao.SysNoticeTypeDao;
import com.jp.entity.BannerHomePage;
import com.jp.entity.BannerQuery;
import com.jp.entity.SysNotice;
import com.jp.entity.SysNoticeType;
import com.jp.entity.BannerQuery.Criteria;
import com.jp.service.SysNoticeService;

@Service
public class SysNoticeServiceImpl implements SysNoticeService{
	private final Logger log_ = LogManager.getLogger(SysNoticeServiceImpl.class);
	@Autowired
	private SysNoticeDao noticedao;
	
	@Autowired
	private SysNoticeTypeDao noticetypedao;
	@Override
	//公告列表
	public List<SysNotice> selectNotice() {
		
		// TODO Auto-generated method stub
		return noticedao.selectByExample();
	}
	//类型下拉框
	@Override
	public List<SysNoticeType> selecttypelist() {
		// TODO Auto-generated method stub
		return noticetypedao.selectByExample();
	}
	//修改
	@Override
	public Integer update(SysNotice notice) {
		// TODO Auto-generated method stub
		return noticedao.updateByPrimaryKeySelective(notice);
	}
	//新增
	@Override
	public Integer insert(SysNotice notice) {
		// TODO Auto-generated method stub
		return noticedao.insertSelective(notice);
	}
	@Override
	public List<SysNotice> selectOne(String noticeid,Integer code) {
		// TODO Auto-generated method stub
		//用于区分前端后台调用接口
		if(code==1) {
			noticedao.updatecount(noticeid);
		}
		return noticedao.selectOne(noticeid);
	}
	@Override
	public List<SysNoticeType> selecttype() {
		// TODO Auto-generated method stub
		return noticetypedao.selecttypelist();
	}
	@Override
	public List<SysNoticeType> selecttypeone(String typeid) {
		// TODO Auto-generated method stub
		return noticetypedao.selecttypeone(typeid);
	}
	@Override
	public Integer updatetype(SysNoticeType noticetype) {
		// TODO Auto-generated method stub
		return noticetypedao.updateByPrimaryKeySelective(noticetype);
	}
	@Override
	public Integer inserttype(SysNoticeType noticetype) {
		// TODO Auto-generated method stub
		return noticetypedao.insertSelective(noticetype);
	}
	@Override
	public int noticetypeDeleteAll(String[] noticetypeids) throws Exception {
		// TODO Auto-generated method stub
		return noticetypedao.noticetypeDeleteAll(noticetypeids);
	}
	@Override
	public int noticeDeleteAll(String[] mationtypeids) throws Exception {
		// TODO Auto-generated method stub
		return noticedao.noticeDeleteAll(mationtypeids);
	}
	@Override
	public PageModel<SysNotice> pageQuery(PageModel<SysNotice> pageModel, SysNotice notice, String noticetitle) {
		// TODO Auto-generated method stub
				BannerQuery bq = new BannerQuery();
				Criteria createCriteria = bq.createCriteria();
				if(notice.getDeleteflag() != null){
					createCriteria.andDeleteflagEqualTo(notice.getDeleteflag());
				}
				PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
				List<SysNotice> list;
				if(bq.equals(null)) {
					list = noticedao.selectByExample();
				}else {
					list = noticedao.selectByExample(bq,noticetitle);
				}
				
				
				pageModel.setList(list);
				pageModel.setPageInfo(new PageInfo<SysNotice>(list));
				return pageModel;
	}
	@Override
	public Integer changeStatus(SysNotice notice) {
		// TODO Auto-generated method stub
		int count=noticedao.updateByPrimaryKeySelective(notice);
		if(count==1){
		  return count;
		}else{
		  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		  return 0;
		}
	}
	@Override
	public JsonResponse pageQueryApi(PageModel<SysNotice> pageModel, SysNotice notice) {
		JsonResponse res = null;
		Result result = null;
		BannerQuery bq = new BannerQuery();
		Criteria createCriteria = bq.createCriteria();
		if(notice.getDeleteflag() != null){
			createCriteria.andDeleteflagEqualTo(notice.getDeleteflag());
		}
		bq.setOrderByClause("createtime DESC");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<SysNotice> list = noticedao.selectByExampleNew(bq);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(list);
			return res;
	}
	
}
