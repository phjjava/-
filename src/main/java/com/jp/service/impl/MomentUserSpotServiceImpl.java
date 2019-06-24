package com.jp.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.MomentUserSpotMapper;
import com.jp.dao.UserDao;
import com.jp.entity.MomentUserSpot;
import com.jp.entity.MomentUserSpotExample;
import com.jp.entity.User;
import com.jp.service.MomentUserSpotService;
import com.jp.util.UUIDUtils;

@Service
public class MomentUserSpotServiceImpl implements MomentUserSpotService {

	@Autowired
	private MomentUserSpotMapper momentUserSpotMapper;
	@Autowired
	private UserDao userMapper;

	@Override
	public JsonResponse createUserSpot(MomentUserSpot entity) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getUserIds())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userIds为空！");
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
		for (String userid : userIdArr) {
			insertUserSpot(entity.getUserid(), userid);
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("用户关注成功！");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse cancelUserSpot(MomentUserSpot entity) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid不能为空！");
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
		List<String> userIdList = new ArrayList<>(Arrays.asList(userIdArr));
		MomentUserSpotExample ex = new MomentUserSpotExample();
		ex.or().andUseridEqualTo(entity.getUserid()).andSpotUseridIn(userIdList);
		int status = momentUserSpotMapper.deleteByExample(ex);
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("用户取消关注成功！");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		result.setMsg("用户取消关注失败！");
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse getUserSpotList(MomentUserSpot entity) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		MomentUserSpotExample ex = new MomentUserSpotExample();
		ex.setOrderByClause(" createtime desc ");
		ex.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE).andUseridEqualTo(entity.getUserid());
		List<MomentUserSpot> userSpotList = momentUserSpotMapper.selectByExample(ex);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("获取用户关注列表成功！");
		res = new JsonResponse(result);
		res.setData(userSpotList);
		return res;
	}

	/**
	 * 族圈用户关注表数据写入
	 * 
	 * @param spotUserid
	 *            关注的用户id
	 * @paran currentUserId 当前登录用户id
	 */
	public void insertUserSpot(String currentUserId, String spotUserid) {
		MomentUserSpotExample ex = new MomentUserSpotExample();
		ex.or().andUseridEqualTo(currentUserId).andSpotUseridEqualTo(spotUserid)
				.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		int haveNum = momentUserSpotMapper.countByExample(ex);
		if (haveNum == 0) {
			MomentUserSpot userSpot = new MomentUserSpot();
			userSpot.setId(UUIDUtils.getUUID());
			userSpot.setUserid(currentUserId);
			userSpot.setSpotUserid(spotUserid);
			userSpot.setDeleteflag(ConstantUtils.DELETE_FALSE);
			userSpot.setCreatetime(new Date());
			userSpot.setUpdatetime(new Date());
			userSpot.setCreateby(currentUserId);
			userSpot.setUpdateby(currentUserId);
			User user = userMapper.selectByPrimaryKey(spotUserid);
			userSpot.setSpotUserName(user.getUsername());
			momentUserSpotMapper.insert(userSpot);
		}
	}
}
