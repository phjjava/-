package com.jp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.EditorialBoardMapper;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.EditorialBoard;
import com.jp.entity.EditorialBoardExample;
import com.jp.entity.UserManager;
import com.jp.service.EditorialBoardService;
import com.jp.util.StringTools;

@Service
public class EditorialBoardServiceImpl implements EditorialBoardService {

	@Autowired
	private EditorialBoardMapper editorialBoardMapper;

	@Autowired
	private UserManagerMapper userManagerMapper;

	@Override
	public PageModel<EditorialBoard> pageQuery(PageModel<EditorialBoard> pageModel, EditorialBoard entity)
			throws Exception {

		EditorialBoardExample example = new EditorialBoardExample();

		if (StringTools.notEmpty(entity.getFamilyid())) {

			example.or().andFamilyidEqualTo(entity.getFamilyid());
		}
		example.setOrderByClause("type desc,sort asc");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<EditorialBoard> list = editorialBoardMapper.selectByExample(example);

		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<EditorialBoard>(list));

		return pageModel;
	}

	@Override
	public EditorialBoard getEditorialBoard(String id) throws Exception {

		return editorialBoardMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer insert(EditorialBoard entity) throws Exception {
		return editorialBoardMapper.insertSelective(entity);

	}

	@Override
	public Integer update(EditorialBoard entity) throws Exception {
		return editorialBoardMapper.updateByPrimaryKeySelective(entity);

	}

	@Override
	public int del(String id) {
		return editorialBoardMapper.deleteByPrimaryKey(id);
	}

	@Override
	public JsonResponse selecteditorialBoardList(String userid) {
		Result result = null;
		JsonResponse res = null;
		List<EditorialBoard> list = new ArrayList<>();
		try {
			List<UserManager> managers = userManagerMapper.selectMnangers(userid);
			EditorialBoardExample example = new EditorialBoardExample();
			for (UserManager manager : managers) {
				example.clear();
				// if(manager.getIsmanager() == 1 ) {
				// 总编委会主任查询所有的编委会列表
				EditorialBoard eb = editorialBoardMapper.selectByPrimaryKey(manager.getEbid());
				if (eb == null) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("编委会不存在！！");
					res = new JsonResponse(result);
					return res;
				}
				example.or().andFamilyidEqualTo(eb.getFamilyid());
				example.setOrderByClause("type desc");
				List<EditorialBoard> elist = editorialBoardMapper.selectByExample(example);
				list.addAll(elist);
				break;
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(list);
		return res;
	}

}
