package com.jp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.PageModel;
import com.jp.dao.JpXingMapper;
import com.jp.entity.BannerHomePage;
import com.jp.entity.InstructionTemplateQuery;
import com.jp.entity.IntroudceTemplate;
import com.jp.entity.JpXing;
import com.jp.service.JpXingService;
import com.jp.util.StringTools;

@Service
public class JpXingServiceImpl implements JpXingService{
	@Autowired
	private JpXingMapper xingMapper;

	@Override
	public Integer update(JpXing jpxing) {
		// TODO Auto-generated method stub
		return xingMapper.updateByPrimaryKeySelective(jpxing);
	}

	@Override
	public Integer insert(JpXing jpxing) {
		// TODO Auto-generated method stub
		return xingMapper.insertSelective(jpxing);
	}

	@Override
	public PageModel<JpXing> pageQuery(PageModel<JpXing> pageModel, JpXing xing,String xname) {
		// TODO Auto-generated method stub
		InstructionTemplateQuery iq = new InstructionTemplateQuery();
		com.jp.entity.InstructionTemplateQuery.Criteria criteria = iq.createCriteria();
		xing.setDeleteflag(0);
		if (StringTools.trimNotEmpty(xing.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(xing.getDeleteflag());
		}
		iq.setOrderByClause("sort");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		if(xname!=null) {
			List<JpXing> list = xingMapper.selectByExample1(xname,iq);
			pageModel.setList(list);
			return pageModel;
		}else {
			List<JpXing> list = xingMapper.selectByExample(iq);
			pageModel.setList(list);
			pageModel.setPageInfo(new PageInfo<JpXing>(list));
			return pageModel;
		}
		
		
	}

	@Override
	public JpXing get(String id) {
		// TODO Auto-generated method stub
		return xingMapper.selectByPrimaryKey(id);
	}

	@Override
	public int xingDeleteAll(String[] ids) {
		// TODO Auto-generated method stub
		return xingMapper.xingDeleteAll(ids);
	}

	@Override
	public int SelectCount(String xname) {
		// TODO Auto-generated method stub
		return xingMapper.SelectCount(xname);
	}

}
