package com.jp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.PageModel;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.entity.SysVersionPrivilege;
import com.jp.service.SysVersionPrivilegeService;

@Service
public class SysVersionPrivilegeServiceImpl implements SysVersionPrivilegeService {
	
	@Autowired
	private SysVersionPrivilegeMapper sysVersionPrivilegeMapper;

	@Override
	public PageModel<SysVersionPrivilege> pageQuery(PageModel<SysVersionPrivilege> pageModel,
			SysVersionPrivilege sysVersionPrivilege) throws Exception {
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<SysVersionPrivilege> list = sysVersionPrivilegeMapper.selectByRecord(sysVersionPrivilege);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<SysVersionPrivilege>(list));
		return pageModel;
	}
	
	

}
