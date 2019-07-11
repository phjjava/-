package com.jp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.UserDao;
import com.jp.dao.UsercontentDao;
import com.jp.entity.UserContentVO;
import com.jp.entity.Usercontent;
import com.jp.service.UserContentService;

@Service("userContentServiceImpl")
public class UserContentServiceImpl implements UserContentService {

	@Resource
	private UsercontentDao UserContentMapper;
	@Resource
	private UserDao userMapper;

	@Override
	public JsonResponse getUserContentList(Usercontent entity, int start, int count) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数家族ID不能为空！");
			res = new JsonResponse(result);
			return res;
		}

		Map<String, String> map = new HashMap<String, String>();
		// map.put("userid", entity.getUserid());
		map.put("familyid", entity.getFamilyid());
		map.put("start", String.valueOf(start));
		map.put("count", String.valueOf(count));
		List<UserContentVO> userContentVOs = UserContentMapper.selectUserContentList(map);
		// 截取内容，保证预览的速度
		for (UserContentVO userContentVO : userContentVOs) {
			if (userContentVO.getContent() != null || !"".equals(userContentVO.getContent())) {
				String content = userContentVO.getContent();
				/*
				 * if (content.length() > 100) content = content.substring(0, 100);
				 * userContentVO.setContent(content);
				 */
				if (content != null && content.length() > 0) {
					// 去除html标签
					content = content.replaceAll("</?[^>]+>", "");
					content = content.replaceAll("\\s*|\t|\r|\n", "");
					content = content.replaceAll("&nbsp;", " ");
					// 截取1/10长度 截取70个字
					if (content.length() > 70)
						content = content.substring(0, 70);
					userContentVO.setContent(content);
				}
			}
		}

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userContentVOs);
		return res;
	}

	@Override
	public JsonResponse getHomePageContents(Usercontent entity, int count) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数家族ID不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("familyid", entity.getFamilyid());
		map.put("count", String.valueOf(count));
		List<Usercontent> userContents = UserContentMapper.selectUserContents(map);

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userContents);
		return res;
	}

	@Override
	public JsonResponse getPersonContent(Usercontent entity) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(entity.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数用户ID不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		Usercontent userContent = UserContentMapper.selectByPrimaryKey(entity.getUserid());
		if (userContent == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("当前用户不存在名人录！");
			res = new JsonResponse(result);
			return res;
		}
		userContent.setUsername(userMapper.selectByPrimaryKey(entity.getUserid()).getUsername());
		if (userContent.getReadcount() != null) {
			userContent.setReadcount(userContent.getReadcount() + 1);
		} else {
			userContent.setReadcount(0);
		}

		UserContentMapper.updateByPrimaryKeySelective(userContent);

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(userContent);
		return res;
	}

	@Override
	public JsonResponse publicContent(Usercontent userContent) {
		Result result = null;
		JsonResponse res = null;
		if (StringUtils.isBlank(userContent.getUserid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数用户ID不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringUtils.isBlank(userContent.getFamilyid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数家族ID不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		Usercontent userContent2 = UserContentMapper.selectByPrimaryKey(userContent.getUserid());
		// 不存则则插入操作
		if (userContent2 == null) {
			userContent.setCreateid(userContent.getUserid());
			userContent.setUpdateid(userContent.getUserid());
			userContent.setCreatetime(new Date());
			userContent.setUpdatetime(new Date());
			userContent.setFamilyid(userContent.getFamilyid());
			userContent.setReadcount(0);
			userContent.setImgurl(userContent.getImgurl());
			userContent.setIssee(userContent.getImgurl() == null ? 1 : 0);
			UserContentMapper.insert(userContent);
		}
		// 存在则更新操作
		else {
			UserContentMapper.updateByPrimaryKeySelective(userContent);
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}

}
