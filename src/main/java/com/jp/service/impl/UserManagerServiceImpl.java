package com.jp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.FunctionRoleMapper;
import com.jp.dao.PostMapper;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.FunctionRoleExample;
import com.jp.entity.FunctionRoleKey;
import com.jp.entity.Post;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.service.UserManagerService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class UserManagerServiceImpl implements UserManagerService {

	private final Logger log_ = LogManager.getLogger(UserManagerServiceImpl.class);

	@Autowired
	private FunctionRoleMapper functionRoleMapper;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@Autowired
	private PostMapper postMapper;

	@Override
	public PageModel<UserManager> pageQuery(PageModel<UserManager> pageModel, UserManager entity) throws Exception {
		UserManagerExample example = new UserManagerExample();
		example.or().andUseridEqualTo(entity.getUserid());
		example.setOrderByClause("ismanager desc,ebtype desc");
		List<UserManager> managers = userManagerMapper.selectByExample(example);
		if (managers == null || managers.size() == 0) {
			return pageModel;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<UserManager> list = new ArrayList<UserManager>();
		// List<UserManager> rtnlist = new ArrayList<UserManager>();
		for (UserManager manager : managers) {
			example.clear();
			example.setOrderByClause("-sort desc");
			// 不查询自己
			params.put("id", manager.getId());
			if (!StringUtils.isEmpty(entity.getUsername())) {
				params.put("username", entity.getUsername());
			}
			if (manager.getEbtype() == 1) {
				// 总编委会查询所有
				params.put("familyid", manager.getFamilyid());
				break;
			}
			params.put("ebid", entity.getEbid());

		}
		list = userManagerMapper.selectByParams(params);

		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<UserManager>(list));

		return pageModel;
	}

	@Override
	public UserManager getUserManager(String id) throws Exception {

		return userManagerMapper.selectByPrimaryKey(id);
	}

	@Override
	public JsonResponse del(String id) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		try {
			UserManager manager = userManagerMapper.selectByPrimaryKey(id);
			if (manager != null) {
				FunctionRoleExample roleEx = new FunctionRoleExample();
				roleEx.or().andUseridEqualTo(manager.getUserid());
				List<FunctionRoleKey> functionRoleKey = functionRoleMapper.selectByExample(roleEx);
				if (functionRoleKey != null) {
					// 先删除《角色功能》
					status = functionRoleMapper.deleteByExample(roleEx);
					if (status < 1) {
						result = new Result(MsgConstants.RESUL_FAIL);
						result.setMsg("角色功能删除失败！");
						res = new JsonResponse(result);
						return res;
					}
				}
				status = userManagerMapper.deleteByPrimaryKey(id);
				if (status > 0) {
					result = new Result(MsgConstants.RESUL_SUCCESS);
					result.setMsg("管理员删除成功！");
					res = new JsonResponse(result);
					return res;
				}
			}
		} catch (Exception e) {
			log_.error("[del方法(删除管理员)---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		result.setMsg("管理员删除失败！");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public List<UserManager> selectManagerByUserid(String userid) {
		UserManagerExample example = new UserManagerExample();
		example.or().andUseridEqualTo(userid);
		example.setOrderByClause("ebtype desc,ismanager desc");
		return userManagerMapper.selectByExample(example);
	}

	@Override
	public UserManager selectByFamilyId(String familyid) {
		UserManagerExample example = new UserManagerExample();
		example.or().andFamilyidEqualTo(familyid).andEbtypeEqualTo(1).andIsmanagerEqualTo(1);
		return userManagerMapper.selectByExample(example).get(0);
	}

	@Override
	public JsonResponse getPost() {
		Result result = null;
		JsonResponse res = null;
		List<Post> allPost = null;
		try {
			allPost = postMapper.selectAllPost();
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(allPost);
		return res;
	}

	@Override
	public JsonResponse save(UserManager entity, String[] functionids) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (entity.getUserid() == null || "".equals(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getUsername() == null || "".equals(entity.getUsername())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数getUsername不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getPostid() == null || "".equals(entity.getPostid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数getPostid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getEbid() == null || "".equals(entity.getEbid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数getEbid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getEbtype() == null || "".equals(entity.getEbtype().toString())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数getEbtype不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (functionids == null || functionids.length == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数functionids不能为空，请至少指定一个权限！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			entity.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			FunctionRoleExample example = new FunctionRoleExample();
			example.or().andUseridEqualTo(entity.getUserid()).andEbidEqualTo(entity.getEbid())
					.andPostidEqualTo(entity.getPostid());
			List<FunctionRoleKey> functionRole = functionRoleMapper.selectByExample(example);
			if (functionRole != null && functionRole.size() != 0) {
				// 先把原有的《角色功能》删除
				status = functionRoleMapper.deleteByExample(example);
				if (status == 0) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("角色功能删除失败！");
					res = new JsonResponse(result);
					return res;
				}
			}
			// 把新授权或编辑的《角色功能》添加
			status = functionRoleMapper.insertBatch(entity.getUserid(), functionids, entity.getEbid(),
					entity.getPostid());
			if (status == 0) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("角色功能添加失败！");
				res = new JsonResponse(result);
				return res;
			}
			Post post = postMapper.selectByPrimaryKey(entity.getPostid());
			entity.setPostname(post.getName());
			entity.setIsmanager(post.getIsmanager());
			if (StringTools.notEmpty(entity.getId())) {// 修改
				status = userManagerMapper.updateByPrimaryKeySelective(entity);
				if (status > 0) {
					result = new Result(MsgConstants.RESUL_SUCCESS);
					result.setMsg("修改成功");
					res = new JsonResponse(result);
					return res;
				}
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("修改失败");
				res = new JsonResponse(result);
				return res;
			} else {// 新增
				entity.setId(UUIDUtils.getUUID());
				status = userManagerMapper.insertSelective(entity);
				if (status > 0) {
					result = new Result(MsgConstants.RESUL_SUCCESS);
					result.setMsg("新增成功");
					res = new JsonResponse(result);
					return res;
				}
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("新增失败");
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[保存或编辑管理员save方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
	}

	// @Override
	// public List<EditorialBoard> selecteditorialBoardList(String userid) {
	// List<UserManager> managers = userManagerMapper.selectMnangers(userid);
	// List<EditorialBoard> list = new ArrayList<>();
	// for(UserManager manager : managers) {
	// EditorialBoard eb =
	// editorialBoardMapper.selectByPrimaryKey(manager.getEbid());
	// list.add(eb);
	// }
	// return list;
	// }

}
