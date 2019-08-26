package com.jp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.UserTagMapper;
import com.jp.dao.UserTagUserMapper;
import com.jp.entity.UserTag;
import com.jp.entity.UserTagUser;
import com.jp.service.UserService;
import com.jp.service.UserTagService;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

/**
 * <p>
 * 族圈用户标签表 服务实现类
 * </p>
 *
 */
@Service
public class UserTagServiceImpl implements UserTagService {

	@Resource
	private UserTagMapper userTagMapper;
	@Resource
	private UserTagUserMapper userTagUserMapper;
	@Resource
	private UserService userService;

	@Override
	public JsonResponse createUserTag(UserTag entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = userService.checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.USERID_IS_NULL);
			res = new JsonResponse(result);
			return res;

		}
		if (StringUtils.isBlank(entity.getTagName())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数tagName不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getUserIds())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userIds不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		String[] userIdArr = entity.getUserIds().split(",");
		if (userIdArr.length == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userIds格式错误！");
			res = new JsonResponse(result);
			return res;
		}
		String uuid = UUIDUtils.getUUID(); // 用户标签id
		String currentUserId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID); // 当前登录用户
		// 族圈用户标签表数据写入
		insertUserTag(uuid, entity.getUserid(), entity.getTagName(), currentUserId);
		// 族圈用户标签与用户关系表数据循环写入
		for (String userid : userIdArr) {
			insertTagUser(uuid, userid, currentUserId);
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse removeUserTag(UserTag entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = userService.checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (StringUtils.isBlank(entity.getId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数标签信息id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		// 根据标签id删除用户标签相关数据
		deleteUserTag(entity);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse editUserTag(UserTag entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = userService.checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (StringUtils.isBlank(entity.getId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数标签id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getTagName())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数tagName不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getUserIds())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userIds不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		String[] userIdArr = entity.getUserIds().split(",");
		if (userIdArr.length == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userIds格式错误！");
			res = new JsonResponse(result);
			return res;
		}
		// 主表更新，从表先删后插
		// 重新写入用户标签相关数据
		String currentUserId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID); // 当前登录用户
		// 族圈用户标签表数据更新
		userTagMapper.updateById(entity);
		// 根据标签id删除用户标签相关数据
		userTagMapper.deleteByTagId(entity);
		// 族圈用户标签与用户关系表数据循环写入
		for (String userid : userIdArr) {
			insertTagUser(entity.getId(), userid, currentUserId);
		}

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse getTagUserList(UserTag entity) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数标签信息id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		// 根据标签id获取标签详情
		UserTag userTag = userTagMapper.selectByPrimaryKey(entity.getId());
		// 根据标签id获取标签下用户详情
		if (userTag != null) {
			userTag.setTagUserList(getTagUsers(userTag.getId()));
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userTag);
		return res;
	}

	@Override
	public JsonResponse getTagListByUserid(UserTag entity) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		List<UserTag> userTagList = userTagMapper.selectListByUserid(entity.getUserid());

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userTagList);
		return res;
	}

	/**
	 * 族圈用户标签信息表数据写入
	 * 
	 * @param tagId
	 * @param userid
	 * @param tagName
	 * @param currentUserId
	 */
	public void insertUserTag(String tagId, String userid, String tagName, String currentUserId) {
		// 族圈用户标签表数据写入
		UserTag userTag = new UserTag();
		userTag.setId(tagId);
		userTag.setUserid(userid);
		userTag.setTagName(tagName);
		userTag.setDeleteflag(ConstantUtils.DELETE_FALSE);
		userTag.setCreateby(currentUserId);
		userTag.setCreatetime(new Date());
		userTag.setUpdateby(currentUserId);
		userTagMapper.insert(userTag);
	}

	/**
	 * 族圈用户标签与用户关系表数据写入
	 * 
	 * @param tagId
	 *            标签id
	 * @param userid
	 *            标签包含的用户id
	 * @paran currentUserId 当前登录用户id
	 */
	public void insertTagUser(String tagId, String userid, String currentUserId) {
		UserTagUser tagUser = new UserTagUser();
		tagUser.setId(UUIDUtils.getUUID());
		tagUser.setTagId(tagId);
		tagUser.setUserid(userid);
		tagUser.setDeleteflag(ConstantUtils.DELETE_FALSE);
		tagUser.setCreateby(currentUserId);
		tagUser.setCreatetime(new Date());
		tagUser.setUpdateby(currentUserId);
		userTagUserMapper.insert(tagUser);
	}

	/**
	 * 删除用户标签公共方法
	 * 
	 * @param entity
	 */
	public void deleteUserTag(UserTag entity) {
		// 根据标签id删除用户标签表数据
		UserTag userTag = userTagMapper.selectByPrimaryKey(entity.getId());
		userTagMapper.deleteById(userTag);
		// 根据标签id删除用户标签与用户关系表数据
		userTagMapper.deleteByTagId(entity);
	}

	/**
	 * 获取单个标签下的用户list
	 * 
	 * @param tagId
	 */
	public List<UserTagUser> getTagUsers(String tagId) {
		List<UserTagUser> tagUserList = userTagUserMapper.selectList(tagId);
		return tagUserList;
	}

	/**
	 * 获取多个标签下的用户list，发布族圈当showType=select时写入时间轴表调用
	 * 
	 * @param tagIds
	 *            标签id数组
	 * @return
	 */
	public List<UserTagUser> getManyTagUsers(String[] tagIds) {
		List<UserTagUser> tagUserList = userTagUserMapper.selectListByTagIds(tagIds);
		return tagUserList;
	}

}
