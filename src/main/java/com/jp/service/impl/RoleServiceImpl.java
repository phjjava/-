package com.jp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jp.dao.FuncRoleDao;
import com.jp.dao.PostMapper;
import com.jp.dao.RoleDao;
import com.jp.dao.UserDao;
import com.jp.dao.UserManagerMapper;
import com.jp.dao.UserbranchDao;
import com.jp.entity.Branch;
import com.jp.entity.BranchEditorBoard;
import com.jp.entity.EditorialBoard;
import com.jp.entity.EditorialBoardExample;
import com.jp.entity.EditorialBoardUser;
import com.jp.entity.EditorialBoardVO;
import com.jp.entity.Post;
import com.jp.entity.PostExample;
import com.jp.entity.Role;
import com.jp.entity.RoleQuery;
import com.jp.entity.RoleQuery.Criteria;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.entity.Userbranch;
import com.jp.entity.UserbranchQuery;
import com.jp.service.RoleService;
import com.jp.util.StringTools;
import com.jp.util.WebUtil;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private BranchDao branchMapper;
	@Autowired
	private FuncRoleDao funcRoleDao;
	@Autowired
	private EditorialBoardMapper editorialBoardMapper;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@Autowired
	private UserDao userMapper;
	@Autowired
	private UserbranchDao userBranchMapper;
	@Autowired
	private PostMapper postMapper;

	@Override
	public PageModel<Role> pageQuery(PageModel<Role> pageModel, Role role) throws Exception {

		RoleQuery rq = new RoleQuery();
		Criteria createCriteria = rq.createCriteria();
		if (StringTools.notEmpty(role.getFamilyid())) {
			createCriteria.andFamilyidEqualTo(role.getFamilyid());
		}

		createCriteria.andTypeEqualTo(1);
		createCriteria.andIsmanagerEqualTo(0);

		RoleQuery rq2 = new RoleQuery();
		Criteria createCriteria2 = rq2.createCriteria();

		if (StringTools.notEmpty(role.getFamilyid())) {
			createCriteria2.andFamilyidEqualTo(role.getFamilyid());
		}
		// 查询type 0 其他 1 总编委会主任
		createCriteria2.andTypeEqualTo(0);
		// 查询Ismanager 0 其他 1 总编委会主任
		rq.or(createCriteria2);

		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<Role> list = roleDao.selectByExample(rq);

		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Role>(list));

		return pageModel;
	}

	@Override
	public int insert(Role role, String[] functionids) throws Exception {
		int count = roleDao.insertSelective(role);

		funcRoleDao.deleteByPrimaryKey(role.getRoleid());

		if (functionids != null && functionids.length > 0) {
			funcRoleDao.insertBatch(role.getRoleid(), functionids);
		}
		if (count == 1) {
			return count;
		} else {
			return 0;
		}
	}

	@Override
	public Role get(String roleid) throws Exception {

		return roleDao.selectByPrimaryKey(roleid);
	}

	@Override
	public int update(Role role, String[] functionids) throws Exception {

		int count = roleDao.updateByPrimaryKeySelective(role);

		funcRoleDao.deleteByPrimaryKey(role.getRoleid());

		if (functionids != null && functionids.length > 0) {
			funcRoleDao.insertBatch(role.getRoleid(), functionids);
		}
		if (count == 1) {
			return count;
		} else {
			return 0;
		}

	}

	@Override
	public List<Role> selectRoleList(String familyid) throws Exception {

		// RoleQuery rq = new RoleQuery();
		//
		// Criteria createCriteria = rq.createCriteria();
		// // 查询type 0 其他 1 总编委会主任
		// createCriteria.andTypeNotEqualTo(1).andIsmanagerNotEqualTo(1);
		// // 查询Ismanager 0 其他 1 总编委会主任
		//// createCriteria.andIsmanagerEqualTo(0);
		// createCriteria.andFamilyidEqualTo(familyid);

		// List<Role> list = roleDao.selectByExample(rq);
		List<Role> list = roleDao.selectByFamilyid(familyid);
		return list;
	}

	@Override
	public Role selectRoleByUserid(String familyid, String userid) throws Exception {
		return roleDao.selectRoleByUserid(familyid, userid);
	}

	@Override
	public int del(String roleid) {
		return roleDao.deleteByPrimaryKey(roleid);
	}

	@Override
	public JsonResponse getAdminListNew(Role entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID");
			res = new JsonResponse(result);
			return res;
		}

		// 定义返回list
		List<EditorialBoardVO> editorialBoards = new ArrayList<EditorialBoardVO>();
		//////////////////////////////////////////
		// 构建总编委会
		EditorialBoardVO editorialBoard = new EditorialBoardVO();
		editorialBoard.setName("总编委会");
		editorialBoard.setId(null);
		editorialBoard.setType(1);
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "1");
		map.put("familyid", entity.getFamilyid());
		// 获取总编委会成员
		List<EditorialBoardUser> total_editorialBoardUsers = roleDao.selectTotalEditorialBoardUsers(map);
		editorialBoard.setEditorialBoardUsers(total_editorialBoardUsers);
		// 添加总编委会
		editorialBoards.add(editorialBoard);
		////////////////////////////////////////

		// 获取城市列表
		List<BranchEditorBoard> branchEditorBoards = branchMapper.selectCityByFamilyid(entity.getFamilyid());

		for (BranchEditorBoard branchEditorBoard : branchEditorBoards) {
			// 构建分支编委会
			EditorialBoardVO branch_editorialBoard = new EditorialBoardVO();
			branch_editorialBoard.setType(0);
			branch_editorialBoard.setId(branchEditorBoard.getBranchid());
			branch_editorialBoard.setName(branchEditorBoard.getAreaname() + "分编委会");
			List<EditorialBoardUser> branch_editorialBoardUsers = new ArrayList<EditorialBoardUser>();
			// 获取城市编委会管理员列表
			Map<String, String> param = new HashMap<String, String>();
			param.put("familyid", entity.getFamilyid());
			param.put("areacode", branchEditorBoard.getAreacode());
			List<String> userlist = branchMapper.selectBranchByCity(param);
			for (String string : userlist) {
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("type", "0");
				map2.put("familyid", entity.getFamilyid());
				map2.put("userid", string);
				EditorialBoardUser branch_editorialBoardUser = roleDao.selectBranchEditorialBoardUsers(map2);

				map2.remove("type");
				map2.put("areacode", branchEditorBoard.getAreacode());
				List<Branch> branchs = branchMapper.selectBranchByUser(map2);
				if (branch_editorialBoardUser != null) {
					branch_editorialBoardUser.setBranchs(branchs);
					branch_editorialBoardUsers.add(branch_editorialBoardUser);
				}
			}
			branch_editorialBoard.setEditorialBoardUsers(branch_editorialBoardUsers);
			editorialBoards.add(branch_editorialBoard);
		}
		// 返回编委会列表
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(editorialBoards);
		return res;
	}

	@Override
	public JsonResponse getEditorilaBoardList(Role entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}

		// 获取该家族下得所有编委会
		EditorialBoardExample example = new EditorialBoardExample();
		example.or().andFamilyidEqualTo(entity.getFamilyid());
		example.setOrderByClause("-sort desc");
		List<EditorialBoard> list = editorialBoardMapper.selectByExample(example);

		for (EditorialBoard e : list) {

			List<UserManager> managers = userManagerMapper.selectByEbid(e.getId());
			for (UserManager m : managers) {
				User user = userMapper.selectByPrimaryKey(m.getUserid());
				m.setImgurl(user.getImgurl());
				m.setGenlevel(user.getGenlevel());
				UserbranchQuery exapmle1 = new UserbranchQuery();
				exapmle1.or().andUseridEqualTo(m.getUserid());
				List<Userbranch> branchs = userBranchMapper.selectByExample(exapmle1);
				m.setBranchs(branchs);
			}
			e.setUserManager(managers);
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(list);
		return res;
	}

	@Override
	public JsonResponse getBranchAdminCityList(Role entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少家族ID");
			res = new JsonResponse(result);
			return res;
		}
		String rolename = entity.getRolename();
		if (entity.getRolename() == null || "".equals(entity.getRolename()))
			rolename = "分编委会成员";
		else
			rolename = entity.getRolename();
		RoleQuery roleExample = new RoleQuery();
		roleExample.or().andFamilyidEqualTo(entity.getFamilyid()).andRolenameEqualTo(rolename);
		List<Role> roles = roleDao.selectByExample(roleExample);
		// 没有编委会成员的返回提醒信息
		if (roles.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前没有'分编委会成员'角色，请定义");
			res = new JsonResponse(result);
			return res;
		}
		// 赋值分编委会成员
		// 分支管理员管理的所有分支 ID
		Map<String, String> map = new HashMap<String, String>();
		map.put("familyid", entity.getFamilyid());
		List<Branch> branchs = branchMapper.getBranchAdminCityList(map);
		if (branchs.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("没有当前分支对应的城市列表");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(branchs);
		return res;
	}

	@Override
	public JsonResponse getEditorilaBoardListNew(Role entity) {
		Result result = null;
		JsonResponse res = null;
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (familyid == null || "".equals(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		//获取该家族下得所有编委会
		EditorialBoardExample example = new EditorialBoardExample();
		example.or().andFamilyidEqualTo(familyid);
		example.setOrderByClause("-sort desc");
		List<EditorialBoard> list = editorialBoardMapper.selectByExample(example);
		PostExample postExample = new PostExample();
		UserManagerExample userManagerExample = new UserManagerExample();
		boolean flag = true;
		for (int k = 0; k < list.size(); k++) {
			flag = true;
			EditorialBoard e = list.get(k);
			//获取职务列表
			postExample.clear();
			postExample.or().andFamilyidEqualTo(familyid).andTypeEqualTo(e.getType());
			postExample.setOrderByClause("-sort desc");

			List<Post> posts = postMapper.selectByExample(postExample);
			if (posts != null && posts.size() > 0) {
				for (int i = 0; i < posts.size(); i++) {
					Post p = posts.get(i);
					//获取管理员列表
					userManagerExample.clear();
					userManagerExample.or().andPostidEqualTo(p.getId()).andEbidEqualTo(e.getId());

					userManagerExample.setOrderByClause("-sort desc");
					List<UserManager> managers = userManagerMapper.selectByExample(userManagerExample);
					if (managers != null && managers.size() > 0) {
						flag = false;
						for (UserManager m : managers) {
							//获取用户头像和世系信息
							User user = userMapper.selectByPrimaryKey(m.getUserid());
							if (user != null) {
								m.setImgurl(user.getImgurl());
								m.setGenlevel(user.getGenlevel());
							}

						}
						p.setManagers(managers);
					} else {
						posts.remove(i);
						i = i - 1;
					}

				}
			}
			e.setPosts(posts);
			if (flag) {
				list.remove(k);
				k = k - 1;
			}
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		res.setData(list);
		return res;
	}
}
