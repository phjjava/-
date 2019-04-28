package com.jp.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.PageModel;
import com.jp.dao.SysVersionPrivilegeMapper;
import com.jp.entity.SysVersionPrivilege;
import com.jp.service.SysVersionPrivilegeService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

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

	@Override
	public SysVersionPrivilege get(String id) throws Exception {
		return sysVersionPrivilegeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int delete(String id) throws Exception {
		return sysVersionPrivilegeMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int saveOrUpdate(SysVersionPrivilege sysVersionP) throws Exception {
		int count = 0;
		sysVersionP.setDeleteflag(0);
		sysVersionP.setCreateby(CurrentSystemUserContext.getSystemUserContext().getUserid());
		sysVersionP.setUpdateby(CurrentSystemUserContext.getSystemUserContext().getUserid());
		sysVersionP.setCreatetime(new Date());
		sysVersionP.setUpdatetime(new Date());
		if(StringTools.notEmpty(sysVersionP.getId())){
			// 修改版本特权
			count = sysVersionPrivilegeMapper.updateByPrimaryKeySelective(sysVersionP);
		}else {
			// 新增版本特权
			sysVersionP.setId(UUIDUtils.getUUID());
			count = sysVersionPrivilegeMapper.insert(sysVersionP);
		}
		return count;
	}

}
