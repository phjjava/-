package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private final Logger log_ = LogManager.getLogger(PlatformServiceImpl.class);

	@Autowired
	private PlatformDao platformDao;

	@Override
	public JsonResponse selectPlatformList(PageModel<Platform> pageModel, Platform platform) {
		Result result = null;
		JsonResponse res = null;
		if (pageModel.getPageNo() == null || "".equals(pageModel.getPageNo() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageNo不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (pageModel.getPageSize() == null || "".equals(pageModel.getPageSize() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageSize不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		try {
			List<Platform> platformList = platformDao.selecPlatformList(platform);
			if (platformList != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(platformList);
				res.setCount(new PageInfo<Platform>(platformList).getTotal());
				return res;
			}
		} catch (Exception e) {
			log_.error("[selectPlatformList---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse deleteVersion(Integer id) {
		Result result = null;
		JsonResponse res = null;
		if (id == null || "".equals(id + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			int status = platformDao.deleteVersion(id);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[deleteVersion---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse saveOrEdit(Platform platform) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (platform.getVersionName() == null || "".equals(platform.getVersionName())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数versionName不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (platform.getVersionNo() == null || "".equals(platform.getVersionNo() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数versionNo不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (platform.getMinUpdateVersion() == null || "".equals(platform.getMinUpdateVersion())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数minUpdateVersion不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (platform.getDownloadUrl() == null || "".equals(platform.getDownloadUrl())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数downloadUrl不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (platform.getFileType() == null || "".equals(platform.getFileType() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数fileType不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (platform.getFileType() == 2) {
			//安卓文件名必传
			if (platform.getFileRealName() == null || "".equals(platform.getFileRealName())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数fileRealName不能为空！");
				res = new JsonResponse(result);
				return res;
			}
		}
		if (platform.getFileType() == 1) {
			//后台设置ios文件名
			String[] urls = platform.getDownloadUrl().split("/");
			if (urls.length != 0) {
				platform.setFileRealName(urls[urls.length - 1]);
			}
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (platform.getId() != null && !"".equals(platform.getId() + "")) {//编辑
				platform.setUploadTime(formatter.format(new Date()));
				status = platformDao.update(platform);
			} else {//新增
				platform.setUploadTime(formatter.format(new Date()));
				status = platformDao.save(platform);
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[save---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse selectone(Integer id) {
		Result result = null;
		JsonResponse res = null;
		if (id == null || "".equals(id + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			Platform platform = platformDao.selectone(id);
			if (platform != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(platform);
				return res;
			}
		} catch (Exception e) {
			log_.error("[selectone---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse isOpen(Integer id) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		Platform platform = platformDao.selectone(id);
		try {
			if (platform.getIsUsed() == 0) {
				// 开启操作
				status = platformDao.closeAllVersion(platform.getFileType());
				status = platformDao.isOpen(platform.getId(), 1);
			} else {
				// 关闭操作
				status = platformDao.isOpen(platform.getId(), 0);
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[FunctionMenuServiceImpl---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;

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
