package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.EventDao;
import com.jp.entity.EventQuery.Criteria;
import com.jp.entity.Event;
import com.jp.entity.EventQuery;
import com.jp.service.EventService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventDao edao;

	@Override
	public PageModel<Event> pageQuery(PageModel<Event> pageModel,Event event) throws Exception {
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		event.setFamilyid(CurrentUserContext.getCurrentFamilyId());
		List<Event> list = edao.selecteventread(event);
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Event>(list));
		return pageModel;
	}

	@Override
	public Event get(String eventid) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		EventQuery eq = new EventQuery();
		Criteria createCriteria = eq.createCriteria();
		createCriteria.andEventidEqualTo(eventid);
		List<Event> event = edao.selectByExample(eq);
		if(event != null && event.size() > 0){
			if(event.get(0).getCreatetime() != null){
				event.get(0).setCreatetimeStr(formatter.format(event.get(0).getCreatetime()));
			}
			return event.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public int batchDelete(String[] eventids) throws Exception {
		return edao.batchDelete(eventids);
	}

	@Override
	public String saveEvent(Event event) {
		String result = "";
		try {
			if (StringTools.trimNotEmpty(event.getEventid())) {
				event.setUpdateid(CurrentUserContext.getCurrentUserId());
				event.setUpdatetime(new Date());
				edao.updateByPrimaryKeySelective(event);
				result = "0";
			} else {
				String eventid = UUIDUtils.getUUID();
				event.setEventid(eventid);
				event.setCreateid(CurrentUserContext.getCurrentUserId());
				event.setCreatename(CurrentUserContext.getCurrentUserName());
				event.setType(1);
				event.setDeleteflag(0);
				event.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				Date insertDate=new Date();
				event.setCreatetime(insertDate);
				edao.insertSelective(event);
				result = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int changeStatus(Event event) {
		int count=edao.updateByPrimaryKeySelective(event);
		if(count==1){
			  return count;
			}else{
			  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			  return 0;
			}
	}
}
