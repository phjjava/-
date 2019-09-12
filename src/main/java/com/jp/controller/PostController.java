package com.jp.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.Post;
import com.jp.service.PostService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

@Controller
@RequestMapping("post")
public class PostController {

	private final Logger log_ = LogManager.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Post> pageModel, Post entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			postService.pageQuery(pageModel, entity);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && 1 != pageModel.getPageNo()) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						postService.pageQuery(pageModel, entity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(pageModel);
		return res;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		Post post = null;
		try {

			String id = request.getParameter("id");
			post = postService.getPost(id);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		if (post == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			return res;
		}

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(post);
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse save(HttpServletRequest request, Post post) {
		Result result = null;
		JsonResponse res = null;
		Integer status = null;
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			if (StringTools.notEmpty(post.getId())) {// 修改

				status = postService.update(post);
			} else {// 新增
				post.setId(UUIDUtils.getUUID());
				post.setFamilyid(familyid);
				post.setCreatetime(new Date());
				status = postService.insert(post);
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

	@RequestMapping(value = "/selectPostList", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse selectUserItem(HttpServletRequest request, Post post) {
		Result result = null;
		JsonResponse res = null;
		List<Post> posts = null;
		try {
			posts = postService.selectPostList(post);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		if (posts == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(posts);
		return res;
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deleteEditorialBoard(Post entity) {
		Result result = null;
		JsonResponse res = null;

		try {
			int status = postService.del(entity);
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
