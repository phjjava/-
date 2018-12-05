package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.PageModel;
import com.jp.dao.PlatformDao;
import com.jp.entity.Platform;
import com.jp.service.PlatformService;
@Service
public class PlatformServiceImpl implements PlatformService{
	@Autowired
	private PlatformDao platformDao;
	@Override
	public PageModel selectPlatformList(PageModel pageModel, Platform platform) throws Exception {
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Platform> platformList = platformDao.selecPlatformList(platform);
		pageModel.setList(platformList);
		pageModel.setPageInfo(new PageInfo<Platform>(platformList));
		return pageModel;
	}
	@Override
	public String deleteVersion(Integer id) {
		String result = null;
		try{
			platformDao.deleteVersion(id);
			result = "1";
		}catch(Exception e){
			e.printStackTrace();
			result = "0";
		}
		return result;
	}
	@Override
	public void save(Platform platform) {
		platformDao.save(platform);
	}
	@Override
	public void update(Platform platform) {
		platformDao.update(platform);
	}
	@Override
	public Platform selectone(String id) {
		return platformDao.selectone(id);
	}
	@Override
	public void isOpen(Integer id,Integer isUsed) {
		platformDao.isOpen(id,isUsed);
	}
	@Override
	public void closeAllVersion(String fileType) {
		platformDao.closeAllVersion(fileType);
	}
}
