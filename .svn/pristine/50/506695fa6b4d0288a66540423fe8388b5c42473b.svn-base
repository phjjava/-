package com.jp.controller;

import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Post;
import com.jp.entity.User;
import com.jp.service.PostService;
import com.jp.util.GsonUtil;
import com.jp.util.Result;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;



@Controller
@RequestMapping("post")
public class PostController {

	private final Logger log_ = LogManager.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	


	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<Post> pageModel, Post entity, ModelMap model) {
		try {
			//entity.setUserid(CurrentUserContext.getCurrentUserId());
			postService.pageQuery(pageModel, entity);
			if (pageModel.getList() != null) {
				if (pageModel.getList().size() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						postService.pageQuery(pageModel, entity);
					}
				}
			}
			model.put("pageModel", pageModel);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "post/postList";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {

			String id = request.getParameter("id");
			Post post = postService.getPost(id);
			model.put("post", post);

		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		
		return "post/post";
	}

	
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Post post, ModelMap model) {
		Integer result = null;
		//String[] functionids = request.getParameterValues("functionids[]");
		try {
			if (StringTools.notEmpty(post.getId())) {// 修改
				
				result = postService.update(post);
			} else {// 新增
				post.setId(UUIDUtils.getUUID());
				post.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				post.setCreatetime(new Date());
				result = postService.insert(post);
			}
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectPostList", method = RequestMethod.POST)
	public String selectUserItem(HttpServletRequest request, Post post, ModelMap model) {
		String gsonStr = null;
		try {
		
			List<Post> list = postService.selectPostList(post);
			gsonStr = GsonUtil.GsonString(list);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
		return gsonStr;
	}
	
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public Result deleteEditorialBoard(Post entity) {
		Result result=new Result();
		try {
			int status =postService.del(entity);
			result.setData(status);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result.setData(0);
			return result;
		}
		return result;
	}
	
}
