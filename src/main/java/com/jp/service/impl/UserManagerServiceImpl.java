package com.jp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.jp.dao.FunctionRoleMapper;
import com.jp.dao.PostMapper;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.FunctionRoleExample;
import com.jp.entity.FunctionRoleKey;
import com.jp.entity.Post;
import com.jp.entity.PostExample;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.service.UserManagerService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

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
	public JsonResponse pageQuery(PageModel<UserManager> pageModel, UserManager entity) {
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
			//当前登录人 userid
			String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
			if (StringTools.isEmpty(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户非法！");
				res = new JsonResponse(result);
				return res;
			}
			UserManagerExample example = new UserManagerExample();
			example.or().andUseridEqualTo(userid);
			example.setOrderByClause("ismanager desc,ebtype desc");
			List<UserManager> managers = userManagerMapper.selectByExample(example);
			if (managers == null || managers.size() == 0) {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				return res;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<UserManager> list = new ArrayList<UserManager>();
			for (UserManager manager : managers) {
				example.clear();
				example.setOrderByClause("-sort desc");
				// 不查询自己
				params.put("id", manager.getId());
				if (StringTools.trimNotEmpty(entity.getUsername())) {
					params.put("username", entity.getUsername());
				}
				if (StringTools.trimNotEmpty(entity.getEbid())) {
					params.put("ebid", entity.getEbid());
				}
				if (manager.getEbtype() == 1) {
					// 总编委会查询所有
					params.put("familyid", manager.getFamilyid());
					break;
				}
				params.put("ebid", entity.getEbid());
			}
			list = userManagerMapper.selectByParams(params);
			if (list != null) {
				pageModel.setList(list);
				pageModel.setPageInfo(new PageInfo<UserManager>(list));
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
	public UserManager getUserManager(String id) throws Exception {

		return userManagerMapper.selectByPrimaryKey(id);
	}

	@Override
	public JsonResponse del(String id) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		try {
			UserManager manager = userManagerMapper.selectByManagerId(id);
			if (manager != null) {
				FunctionRoleExample roleEx = new FunctionRoleExample();
				roleEx.or().andUseridEqualTo(manager.getUserid());
				List<FunctionRoleKey> functionRoleKey = functionRoleMapper.selectByExample(roleEx);
				if (functionRoleKey != null && functionRoleKey.size() != 0) {
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
	public List<UserManager> selectManagerByUserid(String userid, String ebid) {
		/*UserManagerExample example = new UserManagerExample();
		example.or().andUseridEqualTo(userid);
		example.setOrderByClause("ebtype desc,ismanager desc");
		return userManagerMapper.selectByExample(example);*/
		if (StringTools.trimIsEmpty(userid)) {
			return null;
		}
		return userManagerMapper.selectManagerByUserid(userid, ebid);
	}

	@Override
	public UserManager selectByFamilyId(String familyid) {
		UserManagerExample example = new UserManagerExample();
		example.or().andFamilyidEqualTo(familyid).andEbtypeEqualTo(1).andIsmanagerEqualTo(1);
		return userManagerMapper.selectByExample(example).get(0);
	}

	@Override
	public JsonResponse getPost(int type) {
		Result result = null;
		JsonResponse res = null;
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		List<Post> allPost = null;
		PostExample example = new PostExample();
		if (type == 1) {
			example.or().andTypeEqualTo(type).andFamilyidEqualTo(familyid).andIsmanagerNotEqualTo(1);
		} else {
			example.or().andTypeEqualTo(type).andFamilyidEqualTo(familyid);
		}
		try {
			allPost = postMapper.selectByExample(example);
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
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			entity.setFamilyid(familyid);
			FunctionRoleExample example = new FunctionRoleExample();
			example.or().andUseridEqualTo(entity.getUserid()).andEbidEqualTo(entity.getEbid())
					.andPostidEqualTo(entity.getPostid());
			List<FunctionRoleKey> functionRole = functionRoleMapper.selectByExample(example);
			if (functionRole != null && functionRole.size() != 0) {
				// 先把原有的《角色功能》删除
				status = functionRoleMapper.deleteByExample(example);
				if (status < 1) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("角色功能删除失败！");
					res = new JsonResponse(result);
					return res;
				}
			}
			// 把新授权或编辑的《角色功能》添加
			status = functionRoleMapper.insertBatch(entity.getUserid(), functionids, entity.getEbid(),
					entity.getPostid());
			if (status < 1) {
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
