package com.jp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.EditorialBoardMapper;
import com.jp.dao.FunctionRoleMapper;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.EditorialBoard;
import com.jp.entity.EditorialBoardExample;
import com.jp.entity.FunctionRoleExample;
import com.jp.entity.FunctionRoleKey;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.service.EditorialBoardService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

@Service
public class EditorialBoardServiceImpl implements EditorialBoardService {
	private final Logger log_ = LogManager.getLogger(EditorialBoardServiceImpl.class);

	@Autowired
	private EditorialBoardMapper editorialBoardMapper;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@Autowired
	private FunctionRoleMapper functionRoleMapper;
	@Autowired
	private BranchDao branchMapper;

	@Override
	public JsonResponse pageQuery(PageModel<EditorialBoard> pageModel, EditorialBoard entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (pageModel.getPageNo() == null) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("分页参数pageNo不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (pageModel.getPageSize() == null) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("分页参数pageSize不能为空！");
				res = new JsonResponse(result);
				return res;
			}
			//当前登录人 familyid
			String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			if (StringTools.isEmpty(familyid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("header中参数familyid为空!");
				res = new JsonResponse(result);
				return res;
			}
			EditorialBoardExample example = new EditorialBoardExample();
			example.or().andFamilyidEqualTo(familyid);
			example.setOrderByClause("type desc,sort asc");
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<EditorialBoard> list = editorialBoardMapper.selectByExample(example);
			if (list != null) {
				pageModel.setList(list);
				pageModel.setPageInfo(new PageInfo<EditorialBoard>(list));
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(pageModel);
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
	public JsonResponse getEditorialBoard(String id) {
		Result result = null;
		JsonResponse res = null;
		if (StringTools.trimIsEmpty(id)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		EditorialBoard eb = null;
		try {
			eb = editorialBoardMapper.selectByPrimaryKey(id);
			List<Map<String, Object>> listCity = new ArrayList<>();
			if (eb != null) {
				String codes = eb.getCode();
				String[] codeList = codes.split(",");
				for (String code : codeList) {
					if (code.length() > 10) {//分支
						Map<String, Object> map = branchMapper.selectByBranchid(code);
						List<String> blist = new ArrayList<>();
						blist.add(map.get("cityCode").toString());
						//替换前端需要的集合类型
						map.put("cityCode", blist);
						listCity.add(map);
					} else {//地区编码
						Map<String, Object> map = editorialBoardMapper.selectCityByCode(code);
						List<String> clist = new ArrayList<>();
						List<String> list = getCodeList(clist, map.get("pid").toString());
						Collections.reverse(list);//按省市区code反转排序
						list.add(code);
						map.put("cityCode", list);
						listCity.add(map);
					}
				}
				eb.setCityList(listCity);
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(eb);
				return res;
			} else {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("没有数据！");
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
	}

	/**
	 * 根据pid递归查询省市区
	 * @param clist
	 * @param pid
	 * @return
	 */
	public List<String> getCodeList(List<String> clist, String pid) {
		Map<String, Object> map = editorialBoardMapper.selectCityById(pid);
		if ("0".equals(pid)) {
			return clist;
		} else {
			clist.add(map.get("code").toString());
		}
		getCodeList(clist, map.get("parent_id").toString());
		return clist;
	}

	@Override
	public JsonResponse del(String id) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		try {
			//先删除管理员
			UserManagerExample umExample = new UserManagerExample();
			umExample.or().andEbidEqualTo(id);
			List<UserManager> userManagers = userManagerMapper.selectByExample(umExample);
			if (userManagers.size() > 0) {
				status = userManagerMapper.deleteByExample(umExample);
				if (status < 1) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("管理员删除时失败！");
					res = new JsonResponse(result);
					return res;
				}
			}
			// 再删除《角色功能》
			FunctionRoleExample frExample = new FunctionRoleExample();
			frExample.or().andEbidEqualTo(id);
			List<FunctionRoleKey> functionRoles = functionRoleMapper.selectByExample(frExample);
			if (functionRoles.size() > 0) {
				status = functionRoleMapper.deleteByExample(frExample);
				if (status < 1) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("角色功能删除时失败！");
					res = new JsonResponse(result);
					return res;
				}
			}
			status = editorialBoardMapper.deleteByPrimaryKey(id);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				result.setMsg("编委会删除成功！");
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse selecteditorialBoardList() {
		Result result = null;
		JsonResponse res = null;
		List<EditorialBoard> list = new ArrayList<>();
		try {
			//当前登录人 userid
			String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
			if (StringTools.isEmpty(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户非法！");
				res = new JsonResponse(result);
				return res;
			}
			//当前登录人 familyid
			String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			if (StringTools.isEmpty(familyid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("header中参数familyid为空!");
				res = new JsonResponse(result);
				return res;
			}
			//	UserManager manager = userManagerMapper.selectManagerByUserid(userid, ebid).get(0);
			// 总编委会主任查询所有的编委会列表
			EditorialBoardExample example = new EditorialBoardExample();
			example.or().andFamilyidEqualTo(familyid);
			example.setOrderByClause("type desc");
			list = editorialBoardMapper.selectByExample(example);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(list);
		return res;
	}

	@Override
	public JsonResponse save(EditorialBoard eb) {
		Result result = null;
		JsonResponse res = null;
		if (StringTools.trimIsEmpty(eb.getEddesc())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数eddesc不能为空!");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(eb.getName())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数name不能为空!");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(eb.getCode())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数code不能为空!");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(eb.getCodetype())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数codetype不能为空!");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(eb.getSort())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数sort不能为空!");
			res = new JsonResponse(result);
			return res;
		}
		Integer status = 0;
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			if (StringTools.notEmpty(eb.getId())) {// 修改
				status = editorialBoardMapper.updateByPrimaryKeySelective(eb);
				//同步管理员中的编委会名称
				UserManager um = new UserManager();
				um.setEbname(eb.getName());
				UserManagerExample ume = new UserManagerExample();
				ume.or().andEbidEqualTo(eb.getId());
				userManagerMapper.updateByExampleSelective(um, ume);
			} else {// 新增
				eb.setId(UUIDUtils.getUUID());
				eb.setFamilyid(familyid);
				eb.setType(0);
				status = editorialBoardMapper.insertSelective(eb);
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

}
