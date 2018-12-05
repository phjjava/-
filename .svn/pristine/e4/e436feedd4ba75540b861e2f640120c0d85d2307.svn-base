package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.BranchDao;
import com.jp.dao.DynamicMapper;
import com.jp.dao.DynamicfileDao;
import com.jp.dao.DytopDao;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.Dynamic;
import com.jp.entity.DynamicExample;
import com.jp.entity.DynamicExample.Criteria;
import com.jp.entity.Dynamicfile;
import com.jp.entity.DynamicfileQuery;
import com.jp.entity.Dytop;
import com.jp.entity.DytopQuery;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.service.DynamicService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class DynamicServiceImpl implements DynamicService {
	@Autowired
	private DynamicMapper dydao;
	@Autowired
	private DynamicfileDao dyfdao;
	@Resource
	private DytopDao dytopDao;
	@Resource
	private BranchDao branchDao;
	@Resource
	private UserManagerMapper userManagerMapper;
	@Override
	public PageModel<Dynamic> pageQuery(PageModel<Dynamic> pageModel,Dynamic dynamic) throws Exception {
		//Dynamic dynamic = new Dynamic();
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<String> branchList = CurrentUserContext.getCurrentBranchIds();
		String familyid = CurrentUserContext.getCurrentFamilyId();
		String userid  = CurrentUserContext.getCurrentUserId();
		
		List<Dynamic> list = new ArrayList<Dynamic>();
		
		UserManagerExample example = new UserManagerExample();
		example.or().andUseridEqualTo(userid);
		example.setOrderByClause("ebtype desc,ismanager desc");
		List<UserManager> managers = userManagerMapper.selectByExample(example);
		UserManager manager = managers.get(0);
		if(manager.getEbtype() == 1 ){//验证是否是总编委会
			DynamicExample example1 = new DynamicExample();
			example1.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
								
			list = dydao.selectByExample(example1);
		}else {
			list = dydao.selectdyread(dynamic,branchList);
		}
		
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Dynamic>(list));
		return pageModel;
	}

	@Override
	public Dynamic get(String dyid) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		DynamicExample dq = new DynamicExample();
		Criteria createCriteria = dq.createCriteria();
		createCriteria.andDyidEqualTo(dyid);
		List<Dynamic> dynamic = dydao.selectByExample(dq);
		if(dynamic != null && dynamic.size() > 0){
			if(dynamic.get(0).getCreatetime() != null){
				dynamic.get(0).setCreatetimeStr(formatter.format(dynamic.get(0).getCreatetime()));
			}
			return dynamic.get(0);
		}else{
			return null;
		}
	}

	
	public int changeStatus(Dynamic dynamic) throws Exception {
		int count=dydao.updateByPrimaryKeySelective(dynamic);
		if(count==1){
			  return count;
			}else{
			  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			  return 0;
			}
	}

	@Override
	public String saveDynamic(Dynamic dynamic,List<Dynamicfile> dylist,String[] dyfids) {
		String result = "";
		try{
			//编辑
				if (StringTools.trimNotEmpty(dynamic.getDyid())) {
					/*if(dynamic.getCreatetimeStr() != null){
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						dynamic.setUpdatetime(formatter.parse(dynamic.getCreatetimeStr()));
					}*/
					if(dynamic.getDytype()==0) {
						 dynamic.setBranchid("0");
					}
					dynamic.setUpdateid(CurrentUserContext.getCurrentUserId());
					dynamic.setUpdatetime(new Date());
					dydao.updateByPrimaryKeySelective(dynamic);
					for (int i = 0; i < dylist.size(); i++) {
						dylist.get(i).setDyid(dynamic.getDyid());
					}
					if(dylist != null && dylist.size() > 0){
						dyfdao.insertdyfileSelective(dylist);
					}
					if(dyfids!=null){
						dyfdao.deletefile(dyfids);
					}
					result = "0";
					//维护修改dytop表关系
					if(StringTools.trimNotEmpty(dynamic.getTobranchid())){
						//先删除再重新添加
						DytopQuery dq=new DytopQuery();
						dq.or().andDyidEqualTo(dynamic.getDyid());
						dytopDao.deleteByExample(dq);
						saveDytop(dynamic, dynamic.getDyid());
					}
			}else{
				if(dynamic.getDytype()==0) {
					 dynamic.setBranchid("0");
				}
				//新增
				String dyid = UUIDUtils.getUUID();
				dynamic.setType(0);
				dynamic.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				dynamic.setCreateid(CurrentUserContext.getCurrentUserId());
				dynamic.setCreatename(CurrentUserContext.getCurrentUserName());
				dynamic.setDyid(dyid);
				dynamic.setDeleteflag(0);
				Date insertDate=new Date();
				dynamic.setCreatetime(insertDate);
				/*if(dynamic.getCreatetimeStr() != null){
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					dynamic.setCreatetime(formatter.parse(dynamic.getCreatetimeStr()));
				}*/
				dydao.insertSelective(dynamic);
				for (int i = 0; i < dylist.size(); i++) {
					dylist.get(i).setDyid(dyid);
				}
				if(dylist != null && dylist.size() > 0){
					dyfdao.insertdyfileSelective(dylist);
				}
				
				result = "0";
				//维护保存dytop表关系
				if(StringTools.trimNotEmpty(dynamic.getTobranchid())){
					saveDytop(dynamic, dyid);
				}

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	private void saveDytop(Dynamic dynamic, String dyid) {
		String branchid = dynamic.getTobranchid();
		String[] branchids = branchid.split(",");
		for (String str : branchids) {
			//切除分支名字
			String subId = str.substring(0, 32);
			Dytop dytop=new Dytop();
			dytop.setBranchid(subId);
			dytop.setDyid(dyid);
			dytop.setId(UUIDUtils.getUUID());
			dytopDao.insertSelective(dytop);
		}
	}

	@Override
	public int batchDelete(String[] dyids) throws Exception {
		return dydao.batchDelete(dyids);
	}

	@Override
	public List<Dynamicfile> selectdyfile(DynamicfileQuery example) {
		return dyfdao.selectByExample(example);
	}
	


}
