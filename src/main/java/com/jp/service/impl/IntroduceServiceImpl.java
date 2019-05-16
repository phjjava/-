package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.IntroduceDao;
import com.jp.entity.Introduce;
import com.jp.entity.IntroduceQuery;
import com.jp.entity.IntroduceQuery.Criteria;
import com.jp.service.IntroduceService;
import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
@Service
public class IntroduceServiceImpl implements IntroduceService {
    @Autowired
    private IntroduceDao itdao;
    @Override
	public PageModel<Introduce> pageQuery(PageModel<Introduce> pageModel, Introduce introduce) throws Exception {
		IntroduceQuery iq=new IntroduceQuery();
		Criteria criteria=iq.createCriteria();
		criteria.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
	//	criteria.andFamilyidEqualTo(introduce.getFamilyid());
		introduce.setDeleteflag(0);
		if(StringTools.trimNotEmpty(introduce.getDeleteflag())){
			criteria.andDeleteflagEqualTo(introduce.getDeleteflag());
		}
		iq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Introduce> list = itdao.selectByExample(iq);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Introduce>(list));
		return pageModel;
	}

	@Override
	public Introduce get(String introduceid) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		IntroduceQuery iq = new IntroduceQuery();
		Criteria createCriteria = iq.createCriteria();
		createCriteria.andIntroduceidEqualTo(introduceid);
		List<Introduce> introduce = itdao.selectByExample(iq);
		if(introduce != null && introduce.size() > 0){
			if(introduce.get(0).getCreatetime() != null){
				introduce.get(0).setCreatetimeStr(formatter.format(introduce.get(0).getCreatetime()));
			}
			return introduce.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String saveIntroduce(Introduce introduce) {
		String result = "";
		//编辑
		if (StringTools.trimNotEmpty(introduce.getIntroduceid())) {
			introduce.setUpdateid(CurrentUserContext.getCurrentUserId());
			introduce.setUpdatetime(new Date());
			itdao.updateByPrimaryKeySelective(introduce);
			result = "0";
		}else{
			//新增
			String introduceid = UUIDUtils.getUUID();
			introduce.setIntroduceid(introduceid);
			introduce.setCreateid(CurrentUserContext.getCurrentUserId());
			introduce.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			introduce.setDeleteflag(0);
			Date insertDate=new Date();
			introduce.setCreatetime(insertDate);
			introduce.setType("ELSE");
			itdao.insertSelective(introduce);
			result = "0";
		}
		return result;
	}

	@Override
	public int batchDelete(String[] introduceids) throws Exception {
		return itdao.batchDelete(introduceids);
	}

}
