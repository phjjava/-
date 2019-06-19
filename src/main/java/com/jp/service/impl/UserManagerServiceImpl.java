package com.jp.service.impl;

import java.util.ArrayList;
import java.util.List;

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
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<UserManager> list = new ArrayList<UserManager>();
		// List<UserManager> rtnlist = new ArrayList<UserManager>();
		for (UserManager manager : managers) {
			example.clear();
			example.setOrderByClause("ismanager desc");

			if (manager.getEbtype() == 1) {
				// 总编委会查询所有

				if (!StringUtils.isEmpty(entity.getUsername())) {
					example.or().andIdNotEqualTo(manager.getId()).andFamilyidEqualTo(manager.getFamilyid())
							.andUsernameLike("%" + entity.getUsername() + "%");
				} else {
					example.or().andIdNotEqualTo(manager.getId()).andFamilyidEqualTo(manager.getFamilyid());
				}

				// list = userManagerMapper.selectByExample(example);
				// rtnlist.addAll(list);
				break;
			}
			if (!StringUtils.isEmpty(entity.getUsername())) {
				example.or().andEbidEqualTo(manager.getEbid()).andIdNotEqualTo(manager.getId())
						.andUsernameLike("%" + entity.getUsername() + "%");
			} else {
				example.or().andEbidEqualTo(manager.getEbid()).andIdNotEqualTo(manager.getId());
			}

			// list = userManagerMapper.selectByExample(example);
			// rtnlist.addAll(list);
		}
		list = userManagerMapper.selectByExample(example);

		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<UserManager>(list));

		return pageModel;
	}

	@Override
	public UserManager getUserManager(String id) throws Exception {

		return userManagerMapper.selectByPrimaryKey(id);
	}

	/**
	 * 废弃了，后期再删
	 */
	@Override
	public Integer insert(UserManager entity, String[] functionids) throws Exception {
		entity.setFamilyid(CurrentUserContext.getCurrentFamilyId());
		if (functionids != null && functionids.length > 0) {
			FunctionRoleExample example = new FunctionRoleExample();
			// example.clear();
			example.or().andUseridEqualTo(entity.getUserid()).andEbidEqualTo(entity.getEbid());
			functionRoleMapper.deleteByExample(example);

			functionRoleMapper.insertBatch(entity.getUserid(), functionids, entity.getEbid(), entity.getPostid());
		}
		Post post = postMapper.selectByPrimaryKey(entity.getPostid());
		entity.setPostname(post.getName());
		entity.setIsmanager(post.getIsmanager());
		return userManagerMapper.insertSelective(entity);

	}

	/**
	 * 废弃了，后期再删
	 */
	@Override
	public Integer update(UserManager entity, String[] functionids) throws Exception {
		entity.setFamilyid(CurrentUserContext.getCurrentFamilyId());
		if (functionids != null && functionids.length > 0) {
			FunctionRoleExample example = new FunctionRoleExample();
			// example.clear();
			example.or().andUseridEqualTo(entity.getUserid()).andEbidEqualTo(entity.getEbid())
					.andPostidEqualTo(entity.getPostid());
			functionRoleMapper.deleteByExample(example);
			// for(String functionid : functionids) {
			// example.clear();
			// example.or().andUseridEqualTo(CurrentUserContext.getCurrentUserId())
			// .andFunctionidEqualTo(functionid)
			// .andEbidEqualTo(entity.getEbid());
			// functionRoleMapper.deleteByExample(example);
			// }

			functionRoleMapper.insertBatch(entity.getUserid(), functionids, entity.getEbid(), entity.getPostid());
		}
		Post post = postMapper.selectByPrimaryKey(entity.getPostid());
		entity.setPostname(post.getName());
		entity.setIsmanager(post.getIsmanager());
		return userManagerMapper.updateByPrimaryKeySelective(entity);

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
			if (status < 1) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("管理员删除失败！");
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[del方法(删除管理员)---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("管理员删除成功！");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public List<UserManager> selectManagerByUserid(String userid) {
		UserManagerExample example = new UserManagerExample();
		example.or().andUseridEqualTo(userid);
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
			// 先把原有的《角色功能》删除
			functionRoleMapper.deleteByExample(example);
			// 把新授权或编辑的《角色功能》添加
			functionRoleMapper.insertBatch(entity.getUserid(), functionids, entity.getEbid(), entity.getPostid());
			Post post = postMapper.selectByPrimaryKey(entity.getPostid());
			entity.setPostname(post.getName());
			entity.setIsmanager(post.getIsmanager());
			if (StringTools.notEmpty(entity.getId())) {// 修改
				int status = userManagerMapper.updateByPrimaryKeySelective(entity);
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
				int status = userManagerMapper.insertSelective(entity);
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
