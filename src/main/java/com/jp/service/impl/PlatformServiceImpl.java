package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.PlatformDao;
import com.jp.entity.Platform;
import com.jp.service.PlatformService;

@Service
public class PlatformServiceImpl implements PlatformService {
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
		try {
			platformDao.deleteVersion(id);
			result = "1";
		} catch (Exception e) {
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
	public void isOpen(Integer id, Integer isUsed) {
		platformDao.isOpen(id, isUsed);
	}

	@Override
	public void closeAllVersion(Integer fileType) {
		platformDao.closeAllVersion(fileType);
	}

	@Override
	public JsonResponse getPlatform(Integer fileType) {
		Result result = null;
		JsonResponse res = null;
		if (fileType == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数fileType不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		Platform platForm = platformDao.selectPlatform(fileType);
		if (platForm == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("没有启用的版本");
			res = new JsonResponse(result);
			return res;
		} else {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(platForm);
			return res;
		}
	}

	@Override
	public JsonResponse getCurrentTime() {
		Result result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("返回当前时间成功");
		JsonResponse res = new JsonResponse(result);
		res.setData(new Date());
		return res;
	}
}
