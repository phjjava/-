package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.DycommentDao;
import com.jp.dao.DynamicMapper;
import com.jp.dao.DynamicfileDao;
import com.jp.dao.DypriseDao;
import com.jp.dao.DyreadDao;
import com.jp.dao.DytopDao;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.Branch;
import com.jp.entity.BranchKey;
import com.jp.entity.Dycomment;
import com.jp.entity.DycommentQuery;
import com.jp.entity.Dynamic;
import com.jp.entity.DynamicDetailVO;
import com.jp.entity.DynamicExample;
import com.jp.entity.DynamicVO;
import com.jp.entity.Dynamicfile;
import com.jp.entity.DynamicfileQuery;
import com.jp.entity.DynamicfileQuery.Criteria;
import com.jp.entity.Dyprise;
import com.jp.entity.DypriseQuery;
import com.jp.entity.Dyread;
import com.jp.entity.DyreadQuery;
import com.jp.entity.Dytop;
import com.jp.entity.DytopQuery;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.service.DynamicService;
import com.jp.util.HTMLUtil;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class DynamicServiceImpl implements DynamicService {

	private final Logger log_ = LogManager.getLogger(DynamicServiceImpl.class);

	@Autowired
	private DynamicMapper dydao;
	@Autowired
	private DynamicfileDao dyfdao;
	@Autowired
	private DypriseDao dypdao;
	@Autowired
	private DycommentDao dycdao;
	@Autowired
	private DyreadDao dyrdao;
	@Resource
	private DytopDao dytopDao;
	@Resource
	private BranchDao branchDao;
	@Resource
	private UserManagerMapper userManagerMapper;

	@Override
	public JsonResponse pageQuery(PageModel<Dynamic> pageModel, Dynamic dynamic) {
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
		try {
			List<String> branchList = CurrentUserContext.getCurrentBranchIds();
			if (StringTools.trimIsEmpty(branchList)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("您的账号当前没有分支");
				res = new JsonResponse(result);
				return res;
			}
			String familyid = CurrentUserContext.getCurrentFamilyId();
			String userid = CurrentUserContext.getCurrentUserId();

			List<Dynamic> list = new ArrayList<Dynamic>();

			UserManagerExample example = new UserManagerExample();
			example.or().andUseridEqualTo(userid);
			example.setOrderByClause("ebtype desc,ismanager desc");
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<UserManager> managers = userManagerMapper.selectByExample(example);
			UserManager manager = managers.get(0);
			if (manager.getEbtype() == 1) {// 验证是否是总编委会
				DynamicExample example1 = new DynamicExample();
				example1.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);

				// list = dydao.selectByExample(example1);
				dynamic.setFamilyid(familyid);
				list = dydao.selectReadOfManager(dynamic);
			} else {
				list = dydao.selectdyread(dynamic, branchList);
			}
			if (list != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(list);
				res.setCount(new PageInfo<Dynamic>(list).getTotal());
				return res;
			}
		} catch (Exception e) {
			log_.error("[pageQuery方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse get(String dyid) {
		Result result = null;
		JsonResponse res = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Dynamic dynamic = dydao.selectByPrimaryKey(dyid);
			if (dynamic != null) {
				BranchKey key = new BranchKey();
				key.setBranchid(dynamic.getBranchid());
				key.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				Branch branch = branchDao.selectByPrimaryKey(key);
				if (branch != null) {
					dynamic.setBranchnamePlus(branch.getArea() + "_" + branch.getCityname() + "_" + branch.getXname()
							+ "_" + branch.getAddress() + "_" + branch.getBranchname());
				}
				dynamic.setCreatetimeStr(formatter.format(dynamic.getCreatetime()));
			}
			// 获取置顶top信息
			initDyTop(dyid, dynamic);
			DynamicfileQuery dfq = new DynamicfileQuery();
			Criteria criteria = dfq.createCriteria();
			if (StringTools.trimNotEmpty(dynamic.getDyid())) {
				criteria.andDyidEqualTo(dynamic.getDyid());
			}
			List<Dynamicfile> dylist = dyfdao.selectByExample(dfq);
			dynamic.setDynamicFiles(dylist); // 动态包含附件列表
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(dynamic); // 动态
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return res;

	}

	/**
	 * 
	 * @描述 初始化dytop信息
	 * @作者 jinlizhi
	 * @时间 2017年5月19日下午9:32:29
	 * @参数 @param dyid
	 * @参数 @param dynamic
	 * @return void
	 */
	private void initDyTop(String dyid, Dynamic dynamic) {
		DytopQuery example = new DytopQuery();
		example.or().andDyidEqualTo(dyid);
		List<Dytop> dytopList = dytopDao.selectByExample(example);
		if (dytopList != null && !dytopList.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			// StringBuffer sbname = new StringBuffer();
			List<Dytop> list = new ArrayList<>();
			for (Dytop dytop : dytopList) {
				BranchKey key = new BranchKey();
				key.setBranchid(dytop.getBranchid());
				key.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				Branch branch = branchDao.selectByPrimaryKey(key);
				if (branch != null) {
					dytop.setTobranchName(branch.getArea() + "_" + branch.getCityname() + "_" + branch.getXname() + "_"
							+ branch.getAddress() + "_" + branch.getBranchname());
				}
				list.add(dytop);
				// sbname.append(branch.getArea() + "_" + branch.getCityname() + "_" +
				// branch.getXname() + "_" + branch.getBranchname());
				// sbname.append(",");
				sb.append(dytop.getBranchid());
				sb.append(",");
			}
			String braStr = sb.toString();
			String substring = braStr.substring(0, braStr.length() - 1);
			// String subname = sbname.substring(0, sbname.length() - 1);
			dynamic.setTobranchid(substring);
			dynamic.setDytops(list);
		}
	}

	public JsonResponse changeStatus(Dynamic dynamic) {
		Result result = null;
		JsonResponse res = null;
		try {
			int count = dydao.updateByPrimaryKeySelective(dynamic);
			if (count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[changeStatus方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}

	}

	@Override
	public JsonResponse saveDynamic(Dynamic dynamic, List<Dynamicfile> dylist) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		try {
			// 编辑
			if (StringTools.trimNotEmpty(dynamic.getDyid())) {
				/*
				 * if(dynamic.getCreatetimeStr() != null){ SimpleDateFormat formatter = new
				 * SimpleDateFormat("yyyy-MM-dd");
				 * dynamic.setUpdatetime(formatter.parse(dynamic.getCreatetimeStr())); }
				 */
				if (dynamic.getDytype() == 0) {
					dynamic.setBranchid("0");
				}
				dynamic.setUpdateid(CurrentUserContext.getCurrentUserId());
				dynamic.setUpdatetime(new Date());
				status = dydao.updateByPrimaryKeySelective(dynamic);
				// 先删除，原有的附件
				dyfdao.deleteFileByDyid(dynamic.getDyid());
				if (dylist != null && dylist.size() > 0) {
					for (int i = 0; i < dylist.size(); i++) {
						dylist.get(i).setDyid(dynamic.getDyid());
						dylist.get(i).setFileid(UUIDUtils.getUUID());
						dylist.get(i).setBranchid(dynamic.getBranchid());
					}
					// 把新的附件新增
					status = dyfdao.insertdyfileSelective(dylist);
				}
				// 维护修改dytop表关系
				DytopQuery dq = new DytopQuery();
				dq.or().andDyidEqualTo(dynamic.getDyid());
				dytopDao.deleteByExample(dq);
				if (StringTools.trimNotEmpty(dynamic.getTobranchid())) {
					// 先删除再重新添加
					status = saveDytop(dynamic, dynamic.getDyid());
				}
			} else {
				if (dynamic.getDytype() == 0) {
					dynamic.setBranchid("0");
				}
				// 新增
				String dyid = UUIDUtils.getUUID();
				dynamic.setType(0);
				dynamic.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				dynamic.setCreateid(CurrentUserContext.getCurrentUserId());
				dynamic.setCreatename(CurrentUserContext.getCurrentUserName());
				dynamic.setDyid(dyid);
				dynamic.setDeleteflag(0);
				Date insertDate = new Date();
				dynamic.setCreatetime(insertDate);
				/*
				 * if(dynamic.getCreatetimeStr() != null){ SimpleDateFormat formatter = new
				 * SimpleDateFormat("yyyy-MM-dd");
				 * dynamic.setCreatetime(formatter.parse(dynamic.getCreatetimeStr())); }
				 */
				status = dydao.insertSelective(dynamic);
				if (dylist != null && dylist.size() > 0) {
					for (int i = 0; i < dylist.size(); i++) {
						dylist.get(i).setFileid(UUIDUtils.getUUID());
						dylist.get(i).setBranchid(dynamic.getBranchid());
						dylist.get(i).setDyid(dyid);
					}
					status = dyfdao.insertdyfileSelective(dylist);
				}
				// 维护保存dytop表关系
				if (StringTools.trimNotEmpty(dynamic.getTobranchid())) {
					status = saveDytop(dynamic, dyid);
				}
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	private int saveDytop(Dynamic dynamic, String dyid) {
		String branchid = dynamic.getTobranchid();
		String[] branchids = branchid.split(",");
		int status = 0;
		for (String str : branchids) {
			// 切除分支名字
			String subId = str.substring(0, 32);
			Dytop dytop = new Dytop();
			dytop.setBranchid(subId);
			dytop.setDyid(dyid);
			dytop.setId(UUIDUtils.getUUID());
			status = dytopDao.insertSelective(dytop);
		}
		return status;
	}

	@Override
	public JsonResponse batchDelete(String dyids) {
		Result result = null;
		JsonResponse res = null;
		try {
			// a,b,c
			String dyid = dyids.substring(0, dyids.length());
			String dyidArray[] = dyid.split(",");
			int status = dydao.batchDelete(dyidArray);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[batchDelete方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 以下方法用于api
	 */

	@Override
	public JsonResponse getDylist(Dynamic entity) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			if (entity.getMeautype() == null) {
				result.setMsg("参数meautype为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
				result.setMsg("参数familyid为空！");
				res = new JsonResponse(result);
				return res;
			}

			List<Dynamic> dynamics = new ArrayList<Dynamic>();
			DynamicExample example = new DynamicExample();
			example.setOrderByClause("createtime desc");
			if (entity.getStart() != null && entity.getCount() != null) {
				example.setPageNo(entity.getStart().intValue() + 1);
				example.setPageSize(entity.getCount().intValue());
			}

			if (0 == entity.getMeautype()) {
				// 获取家族得相册列表
				example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE).andFamilyidEqualTo(entity.getFamilyid())
						.andDytypeEqualTo(0);
				dynamics = dydao.selectByExample(example);

			} else if (1 == entity.getMeautype()) {
				// 获取全部动态
				example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE).andFamilyidEqualTo(entity.getFamilyid());
				dynamics = dydao.selectByExample(example);
			} else if (2 == entity.getMeautype()) {
				if (entity.getBranchid() == null || "".equals(entity.getBranchid())) {
					result.setMsg("参数branchid为空！");
					return res;
				}

				// 按照城市编码获取动态列表
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("startRow", entity.getStart());
				params.put("pageSize", entity.getCount());
				params.put("cityCode", entity.getBranchid());
				params.put("familyid", entity.getFamilyid());

				dynamics = dydao.selectByCityCode(params);
			} else if (3 == entity.getMeautype()) {
				if (entity.getBranchid() == null || "".equals(entity.getBranchid())) {
					result.setMsg("参数branchid为空！");
					return res;
				}
				// 按照分支id获取动态列表
				example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE).andBranchidEqualTo(entity.getBranchid());
				dynamics = dydao.selectByExample(example);
			}

			for (Dynamic dy : dynamics) {
				JsonResponse result2 = getDyDetailExt(dy);
				if (result2.getCode() == 0) {
					DynamicVO data = (DynamicVO) result2.getData();
					String content = data.getDynamic().getDycontent();
					// System.out.println("原动态内容============="+content);
					content = content.replaceAll("</?[^>]+>", "");
					content = content.replaceAll("\\s*|\t|\r|\n", "");
					content = content.replaceAll("&nbsp;", " ");
					if (content != null && content.length() > 20) {
						// 去除html标签
						// 截取1/10长度
						content = content.substring(0, 20);
					}
					dy.setDycontent(content);
					dy.setCountReads(data.getCountReads());
					dy.setCountFiles(data.getCountFiles());
					dy.setCountComments(data.getCountComments());
					dy.setCountPrises(data.getCountPrises());
				}

			}

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(dynamics);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[DynamicServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getDyDetailExt(Dynamic entity) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		if (entity.getDyid() == null || "".equals(entity.getDyid())) {
			result.setMsg("参数动态id为空");
			return res;
		}
		DynamicVO dynamicVO = new DynamicVO();
		// 获取动态详情
		DynamicExample dynamicExample = new DynamicExample();
		dynamicExample.or()/* .andBranchidEqualTo(entity.getBranchid()) */
				.andDyidEqualTo(entity.getDyid()).andDeleteflagEqualTo(0);
		List<Dynamic> dynamics = dydao.selectByExample(dynamicExample);
		for (Dynamic dynamic : dynamics) {
			if (dynamic.getDycontent() != null || !"".equals(dynamic.getDycontent()))
				dynamic.setDycontent(HTMLUtil.delHTMLTag(dynamic.getDycontent()));
		}
		if (dynamics.size() == 0) {
			result.setMsg("不存在该动态信息");
			return res;
		}
		dynamicVO.setDynamic(dynamics.get(0));
		// 获取动态创建时间：取更新时间
		// 获取动态附件
		DynamicfileQuery dynamicFileExample = new DynamicfileQuery();
		dynamicFileExample.or().andBranchidEqualTo(entity.getBranchid()).andDyidEqualTo(entity.getDyid());
		List<Dynamicfile> dynamicFiles = dyfdao.selectByExample(dynamicFileExample);
		// dynamicVO.setDynamicFiles(dynamicFiles);
		dynamicVO.setCountFiles(dynamicFiles.size());
		// 获取动态点赞
		DypriseQuery dyPriseExample = new DypriseQuery();
		dyPriseExample.or().andBranchidEqualTo(entity.getBranchid()).andDyidEqualTo(entity.getDyid());
		dyPriseExample.setOrderByClause("createtime asc");
		List<Dyprise> dyPrises = dypdao.selectByExample(dyPriseExample);
		// dynamicVO.setDyPrises(dyPrises);
		dynamicVO.setCountPrises(dyPrises.size());
		// 获取评论列表
		DycommentQuery dyCommentExample = new DycommentQuery();
		dyCommentExample.or().andBranchidEqualTo(entity.getBranchid()).andDyidEqualTo(entity.getDyid())
				.andDeleteflagEqualTo(0);
		dyCommentExample.setOrderByClause("createtime desc");
		List<Dycomment> dyComments = dycdao.selectByExample(dyCommentExample);
		dynamicVO.setCountComments(dyComments.size());

		DyreadQuery dyReadExample = new DyreadQuery();
		dyReadExample.or().andDyidEqualTo(entity.getDyid());
		dyReadExample.setOrderByClause("createtime desc");
		List<Dyread> dyReads = dyrdao.selectByExample(dyReadExample);
		dynamicVO.setCountReads(dyReads.size());

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(dynamicVO);
		return res;
	}

	@Override
	public JsonResponse getDyDetail(Dynamic entity) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			if (entity.getDyid() == null || "".equals(entity.getDyid())) {
				result.setMsg("不存在动态信息");
				res = new JsonResponse(result);
				return res;
			}
			if (entity.getUpdateid() == null || "".equals(entity.getUpdateid())) {
				result.setMsg("不存在阅读人ID信息");
				res = new JsonResponse(result);
				return res;
			}
			DynamicDetailVO dynamicVO = new DynamicDetailVO();
			// 获取动态详情
			DynamicExample dynamicExample = new DynamicExample();
			dynamicExample.or()/* .andBranchidEqualTo(entity.getBranchid()) */
					.andDyidEqualTo(entity.getDyid()).andDeleteflagEqualTo(0);
			List<Dynamic> dynamics = dydao.selectByExample(dynamicExample);
			if (dynamics.size() == 0) {
				result.setMsg("不存在该动态信息");
				res = new JsonResponse(result);
				return res;
			}
			// 替换html标签
			dynamicVO.setDynamic(dynamics.get(0));
			// 获取动态创建时间：取更新时间
			// dynamicVO.setCreatetime(dynamics.get(0).getUpdatetime());
			// 获取动态附件
			DynamicfileQuery dynamicFileExample = new DynamicfileQuery();
			dynamicFileExample.or().andDyidEqualTo(entity.getDyid());
			List<Dynamicfile> dynamicFiles = dyfdao.selectByExample(dynamicFileExample);
			dynamicVO.setDynamicFiles(dynamicFiles);
			dynamicVO.setCountFiles(dynamicFiles.size());
			// 获取动态点赞
			DypriseQuery dyPriseExample = new DypriseQuery();
			dyPriseExample.or().andDyidEqualTo(entity.getDyid());
			dyPriseExample.setOrderByClause("createtime asc");
			List<Dyprise> dyPrises = dypdao.selectByExample(dyPriseExample);
			dynamicVO.setDyPrises(dyPrises);
			dynamicVO.setCountPrises(dyPrises.size());
			// 获取评论列表
			DycommentQuery dyCommentExample = new DycommentQuery();
			dyCommentExample.or().andDyidEqualTo(entity.getDyid()).andDeleteflagEqualTo(0);
			dyCommentExample.setOrderByClause("createtime desc");
			List<Dycomment> dyComments = dycdao.selectByExample(dyCommentExample);
			dynamicVO.setDyComments(dyComments);
			dynamicVO.setCountComments(dyComments.size());
			// 插入已读用户列表
			Dyread dyRead = new Dyread();
			dyRead.setId(UUIDUtils.getUUID());
			dyRead.setDyid(entity.getDyid());
			dyRead.setUserid(entity.getUpdateid());
			dyRead.setCreatetime(new Date());
			dyrdao.insert(dyRead);

			DyreadQuery dyReadExample = new DyreadQuery();
			dyReadExample.or().andDyidEqualTo(entity.getDyid());
			dyReadExample.setOrderByClause("createtime desc");
			List<Dyread> dyReads = dyrdao.selectByExample(dyReadExample);
			dynamicVO.setCountReads(dyReads.size());

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(dynamicVO);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[DynamicServiceImpl---Error:]", e);
		}
		return res;
	}

}
