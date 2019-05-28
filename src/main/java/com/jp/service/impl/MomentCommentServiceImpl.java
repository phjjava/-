package com.jp.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.MomentCommentMapper;
import com.jp.dao.MomentCommentTimelineMapper;
import com.jp.dao.MomentMapper;
import com.jp.dao.UserDao;
import com.jp.entity.Moment;
import com.jp.entity.MomentComment;
import com.jp.entity.MomentCommentTimeline;
import com.jp.entity.User;
import com.jp.service.MomentCommentService;
import com.jp.util.UUIDUtils;

@Service
public class MomentCommentServiceImpl implements MomentCommentService {
	
	private final Logger log_ = LogManager.getLogger(MomentCommentServiceImpl.class);
	
	@Resource
	private MomentMapper momentMapper;
	@Resource
	private MomentCommentMapper momentCommentMapper;
	@Resource
	private MomentCommentTimelineMapper momentCommentTimelineMapper;
	@Resource
	private UserDao userMapper;

	@Override
	public JsonResponse createMomentComment(MomentComment entity) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			if(StringUtils.isBlank(entity.getMomentId())) {
				result.setMsg("参数momentId为空！");
				res = new JsonResponse(result);
				return res;
			}
			if(StringUtils.isBlank(entity.getUserid())) {
				result.setMsg("参数userId为空！");
				return res;
			}
			if(StringUtils.isBlank(entity.getTargetUserid())) {
				result.setMsg("参数targetUserid为空！");
				return res;
			}
			if(StringUtils.isBlank(entity.getContent())) {
				result.setMsg("参数content为空！");
				return res;
			}
			Moment moment = momentMapper.selectByPrimaryKey(entity.getMomentId());
			if(moment == null) {
				result.setMsg("抱歉，不存在的族圈消息！");
				return res;
			}
			//回复人
			User user = userMapper.selectByPrimaryKey(entity.getUserid());
			//回复目标
			User targetUser = userMapper.selectByPrimaryKey(entity.getTargetUserid());
			//评论主表
			entity.setId(UUIDUtils.getUUID());
			entity.setTargetUserName(targetUser.getUsername());
			entity.setCreatetime(new Date());
			entity.setCreateby(entity.getUserid());
			entity.setDeleteflag(ConstantUtils.DELETE_FALSE);
			int status = momentCommentMapper.insert(entity);
			if(status > 0) {
				//族圈评论时间轴表插入数据
				MomentCommentTimeline commentTimeline = new MomentCommentTimeline();
				commentTimeline.setId(UUIDUtils.getUUID());
				commentTimeline.setMomentId(entity.getMomentId());
				commentTimeline.setMomentCommentId(entity.getId());
				commentTimeline.setCreatetime(new Date());
				commentTimeline.setCreateby(entity.getUserid());
				commentTimeline.setDeleteflag(ConstantUtils.DELETE_FALSE);
				status = momentCommentTimelineMapper.insert(commentTimeline);
				if(status > 0) {
					result = new Result(MsgConstants.RESUL_SUCCESS);
					res = new JsonResponse(result);
					return res;
				}else {
					result.setMsg("网络连接失败！");
					res = new JsonResponse(result);
					return res;
				}
			}
			result.setMsg("评论族圈信息异常！");
			res = new JsonResponse(result);
			return res;
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[MomentCommentServiceImpl---Error:]", e);
			return res;
		}
	}

	@Override
	public JsonResponse delMomentComment(MomentComment entity) {
		return null;
	}

	@Override
	public JsonResponse getAllMomentComment(MomentComment entity) {
		return null;
	}

}
