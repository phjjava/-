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
import com.jp.dao.MomentLikeMapper;
import com.jp.dao.MomentLikeTimelineMapper;
import com.jp.dao.MomentMapper;
import com.jp.dao.UserDao;
import com.jp.entity.Moment;
import com.jp.entity.MomentLike;
import com.jp.entity.MomentLikeExample;
import com.jp.entity.MomentLikeTimeline;
import com.jp.entity.MomentLikeTimelineExample;
import com.jp.entity.User;
import com.jp.service.MomentLikeService;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

@Service
public class MomentLikeServiceImpl implements MomentLikeService {

	@Resource
	private MomentLikeMapper momentLikeMapper;
	@Resource
	private MomentLikeTimelineMapper momentLikeTimelineMapper;
	@Resource
	private MomentMapper momentMapper;

	@Resource
	private UserDao userMapper;

	/**
	 * 点赞
	 */
	@Override
	public JsonResponse createMomentLike(MomentLike entity) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getMomentId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数momentId为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userId为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (checkIsLike(entity.getMomentId(), entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户已点赞该条族圈信息！");
			res = new JsonResponse(result);
			return res;
		}
		// 点赞表数据写入
		MomentLike momentLike = new MomentLike();
		String uuid = UUIDUtils.getUUID(); // 点赞信息id
		String currentUserId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID); // 当前登录用户
		Date date = new Date();
		User user = userMapper.selectByPrimaryKey(currentUserId);
		momentLike.setId(uuid);
		momentLike.setMomentId(entity.getMomentId());
		momentLike.setUserid(entity.getUserid());
		momentLike.setDeleteflag(ConstantUtils.DELETE_FALSE);
		momentLike.setCreateby(currentUserId);
		momentLike.setUpdateby(currentUserId);
		momentLike.setCreatetime(date);
		int status = momentLikeMapper.insert(momentLike);
		// 点赞时间轴数据写入
		MomentLikeTimeline momentLikeTimeLine = new MomentLikeTimeline();
		momentLikeTimeLine.setId(UUIDUtils.getUUID());
		momentLikeTimeLine.setMomentId(entity.getMomentId());
		momentLikeTimeLine.setMomentLikeId(uuid);
		momentLikeTimeLine.setUserid(entity.getUserid());
		momentLikeTimeLine.setDeleteflag(ConstantUtils.DELETE_FALSE);
		momentLikeTimeLine.setCreateby(currentUserId);
		momentLikeTimeLine.setUpdateby(currentUserId);
		momentLikeTimeLine.setUsername(user.getUsername());
		momentLikeTimeLine.setCreatetime(date);
		momentLikeTimelineMapper.insert(momentLikeTimeLine);
		// 族圈表更新点赞数+1
		Moment moment = momentMapper.selectByPrimaryKey(entity.getMomentId());
		moment.setLikeNum(moment.getLikeNum() + 1);
		status = momentMapper.updateByPrimaryKey(moment);
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("点赞成功！");
			res = new JsonResponse(result);
			System.out.println("点赞res="+res);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		result.setMsg("点赞失败！");
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 取消点赞
	 */
	@Override
	public JsonResponse cancelMomentLike(MomentLike entity) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getMomentId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数momentId为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数userId为空！");
			res = new JsonResponse(result);
			return res;
		}
		MomentLikeExample ex = new MomentLikeExample();
		MomentLikeTimelineExample timelineEx = new MomentLikeTimelineExample();
		ex.or().andMomentIdEqualTo(entity.getMomentId()).andUseridEqualTo(entity.getUserid())
				.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		List<MomentLike> momentLikeList = momentLikeMapper.selectByExample(ex);
		MomentLike momentLike = null;
		MomentLikeTimeline momentLikeTimeLine = null;
		if (momentLikeList != null && momentLikeList.size() > 0) {
			momentLike = momentLikeList.get(0);
			momentLike.setDeleteflag(ConstantUtils.DELETE_TRUE);
			// 更新jp_moment_like表的deleteflag标识
			momentLikeMapper.updateByPrimaryKey(momentLike);

			timelineEx.or().andMomentLikeIdEqualTo(momentLike.getId()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			List<MomentLikeTimeline> timeLineList = momentLikeTimelineMapper.selectByExample(timelineEx);
			if (timeLineList != null && timeLineList.size() > 0) {
				momentLikeTimeLine = timeLineList.get(0);
				momentLikeTimeLine.setDeleteflag(ConstantUtils.DELETE_TRUE);
				// 更新jp_moment_like_timeline表的deleteflag标识
				momentLikeTimelineMapper.updateByPrimaryKey(momentLikeTimeLine);
			}
			// 族圈表更新点赞数-1
			Moment moment = momentMapper.selectByPrimaryKey(entity.getMomentId());
			if (moment.getLikeNum() != null && moment.getLikeNum() > 0) {
				moment.setLikeNum(moment.getLikeNum() - 1);
				momentMapper.updateByPrimaryKey(moment);
			}
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("取消点赞成功！");
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 判断当前登录用户是否点赞过该条族圈信息-公共方法
	 * 
	 * @param momentId
	 *            族圈信息id
	 * @param userid
	 *            点赞用户id
	 * @return
	 */
	public boolean checkIsLike(String momentId, String userid) {
		boolean checkIsLike = false;
		MomentLikeExample ex = new MomentLikeExample();
		ex.or().andMomentIdEqualTo(momentId).andUseridEqualTo(userid).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		List<MomentLike> momentLikeList = momentLikeMapper.selectByExample(ex);
		if (momentLikeList != null && momentLikeList.size() > 0) {
			checkIsLike = true;
		}
		return checkIsLike;
	}

	/**
	 * 根据族圈信息id获取族圈的点赞时间轴详情
	 * 
	 * @param momentId
	 *            族圈信息id
	 * @return
	 */
	@Override
	public JsonResponse getMomentLikeInfo(String momentId) {
		MomentLikeTimelineExample ex = new MomentLikeTimelineExample();
		ex.or().andMomentIdEqualTo(momentId).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
		ex.setOrderByClause(" createtime desc ");
		List<MomentLikeTimeline> momentLikeList = momentLikeTimelineMapper.selectByExample(ex);
		Result result = new Result(MsgConstants.RESUL_SUCCESS);
		JsonResponse res = new JsonResponse(result);
		res.setData(momentLikeList);
		return res;
	}

}
