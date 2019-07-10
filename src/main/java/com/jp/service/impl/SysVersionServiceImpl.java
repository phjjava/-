package com.jp.service.impl;

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
import com.jp.dao.SysFuncVersionDao;
import com.jp.dao.SysVersionDao;
import com.jp.entity.SysFuncVersion;
import com.jp.entity.SysFuncVersionQuery;
import com.jp.entity.SysVersion;
import com.jp.entity.SysVersionQuery;
import com.jp.entity.SysVersionQuery.Criteria;
import com.jp.service.SysVersionService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class SysVersionServiceImpl implements SysVersionService {

	private final Logger log_ = LogManager.getLogger(SysVersionServiceImpl.class);

	@Autowired
	private SysVersionDao sysVersionDao;

	@Autowired
	private SysFuncVersionDao sysFuncVersionDao;

	@Override
	public PageModel<SysVersion> pageQuery(PageModel<SysVersion> pageModel, SysVersion sysVersion) throws Exception {

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
	public JsonResponse delete(String versionid) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (versionid == null || "".equals(versionid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数versionid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			status = sysVersionDao.deleteByPrimaryKey(versionid);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[delete方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
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
	public List<SysVersion> getSysVersionList() throws Exception {

		SysVersionQuery sfq = new SysVersionQuery();
		Criteria createCriteria = sfq.createCriteria();

		List<SysVersion> list = sysVersionDao.selectByExample(sfq);

		return list;
	}

	@Override
	public JsonResponse save(SysVersion sysVersion, String[] functionids) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (sysVersion.getVersionname() == null || "".equals(sysVersion.getVersionname())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数versionname不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (sysVersion.getUsercount() == null || "".equals(sysVersion.getUsercount() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数usercount不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			if (StringTools.notEmpty(sysVersion.getVersionid())) {//修改
				status = sysVersionDao.updateByPrimaryKeySelective(sysVersion);
				sysFuncVersionDao.deleteByVersionid(sysVersion.getVersionid());
				if (functionids != null && functionids.length > 0) {
					status = sysFuncVersionDao.insertBatch(sysVersion.getVersionid(), functionids);
				}
			} else {//新增
				sysVersion.setVersionid(UUIDUtils.getUUID());
				status = sysVersionDao.insertSelective(sysVersion);
				if (functionids != null && functionids.length > 0) {
					status = sysFuncVersionDao.insertBatch(sysVersion.getVersionid(), functionids);
				}
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[save方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}
}
