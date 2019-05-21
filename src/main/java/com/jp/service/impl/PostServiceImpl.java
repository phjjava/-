package com.jp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.PostMapper;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.Post;
import com.jp.entity.PostExample;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Resource
	private PostMapper postMapper;
	@Resource
	private UserManagerMapper userManagerMapper;

	@Override
	public PageModel<Post> pageQuery(PageModel<Post> pageModel, Post entity) {
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		PostExample example = new PostExample();
		example.setOrderByClause("ismanager desc,type desc");
		example.or().andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
		List<Post> rtnlist = postMapper.selectByExample(example);
		pageModel.setList(rtnlist);
		pageModel.setPageInfo(new PageInfo<Post>(rtnlist));
		return pageModel;
	}

	@Override
	public Post getPost(String id) {
		return postMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer update(Post post) {

		UserManagerExample example = new UserManagerExample();
		example.or().andPostidEqualTo(post.getId());
		UserManager manager = new UserManager();
		manager.setPostname(post.getName());
		userManagerMapper.updateByExampleSelective(manager, example);

		return postMapper.updateByPrimaryKeySelective(post);
	}

	@Override
	public Integer insert(Post post) {

		return postMapper.insertSelective(post);
	}

	@Override
	public Integer del(Post post) {
		UserManagerExample example = new UserManagerExample();
		example.or().andPostidEqualTo(post.getId());
		userManagerMapper.deleteByExample(example);
		return postMapper.deleteByPrimaryKey(post.getId());
	}

	@Override
	public List<Post> selectPostList(Post post) {

		PostExample example = new PostExample();
		// 分编委
		example.or().andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId()).andTypeEqualTo(post.getType());

		return postMapper.selectByExample(example);
	}

}
