package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.PageModel;
import com.jp.dao.SysFuncVersionDao;
import com.jp.dao.SysVersionDao;
import com.jp.entity.SysFuncVersion;
import com.jp.entity.SysFuncVersionQuery;
import com.jp.entity.SysVersion;
import com.jp.entity.SysVersionQuery;
import com.jp.entity.SysVersionQuery.Criteria;
import com.jp.service.SysVersionService;
import com.jp.util.StringTools;

@Service
public class SysVersionServiceImpl implements SysVersionService {

	@Autowired
	private SysVersionDao sysVersionDao;

	@Autowired
	private SysFuncVersionDao sysFuncVersionDao;

	@Override
	public PageModel<SysVersion> pageQuery(PageModel<SysVersion> pageModel,SysVersion sysVersion) throws Exception {

		SysVersionQuery sfq = new SysVersionQuery();

		Criteria createCriteria = sfq.createCriteria();

		if (StringTools.trimNotEmpty(sysVersion.getVersionname())) {
			createCriteria.andVersionnameLike("%" + sysVersion.getVersionname().trim() + "%");
		}

		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<SysVersion> list = sysVersionDao.selectByExample(null);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<SysVersion>(list));
		return pageModel;
	}

	@Override
	public int insert(SysVersion sysVersion, String [] functionids) throws Exception {
		int count = sysVersionDao.insertSelective(sysVersion);
		
		if(functionids != null && functionids.length > 0){
			sysFuncVersionDao.insertBatch(sysVersion.getVersionid(),functionids);
		}
		
		if(count == 1){
			return count;
		}else{
			return 0;
		}
	}

	@Override
	public int update(SysVersion sysVersion, String [] functionids) throws Exception {
		int count = sysVersionDao.updateByPrimaryKeySelective(sysVersion);
		
		sysFuncVersionDao.deleteByVersionid(sysVersion.getVersionid());
		
		if(functionids != null && functionids.length > 0){
			sysFuncVersionDao.insertBatch(sysVersion.getVersionid(),functionids);
		}
		if(count == 1){
			return count;
		}else{
			return 0;
		}
	}

	@Override
	public int delete(String versionid) throws Exception {
		return sysVersionDao.deleteByPrimaryKey(versionid);
	}

	@Override
	public int deleteFuncVersionByVersionid(String versionid) throws Exception {

		SysFuncVersionQuery sfvq = new SysFuncVersionQuery();
		com.jp.entity.SysFuncVersionQuery.Criteria createCriteria = sfvq.createCriteria();
		createCriteria.andVersionidEqualTo(versionid);

		return sysFuncVersionDao.deleteByExample(sfvq);

	}

	public int insertFuncVersion(SysFuncVersion sysFuncVersion) throws Exception {
		
		return sysFuncVersionDao.insert(sysFuncVersion);
		
	}

	@Override
	public SysVersion get(String versionid) throws Exception {
		return sysVersionDao.selectByPrimaryKey(versionid);
	}
	
	@Override
	public List<SysVersion> getSysVersionList() throws Exception {

		SysVersionQuery sfq = new SysVersionQuery();
		Criteria createCriteria = sfq.createCriteria();
		
		List<SysVersion> list = sysVersionDao.selectByExample(sfq);

		return list;
	}
}
