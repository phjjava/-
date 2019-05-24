package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.EventDao;
import com.jp.dao.EventreadDao;
import com.jp.entity.EventQuery.Criteria;
import com.jp.entity.EventreadQuery;
import com.jp.entity.Event;
import com.jp.entity.EventQuery;
import com.jp.service.EventService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.entity.EventVO;
import com.jp.entity.Eventread;

@Service
public class EventServiceImpl implements EventService {
	
	private final Logger log_ = LogManager.getLogger(EventServiceImpl.class);
			
	@Autowired
	private EventDao edao;
	@Autowired
	private EventreadDao erdao;

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

	/**
	* 以下方法用于api
	*/
	
	@Override
	public JsonResponse getEventList(Event entity) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			if (entity.getFamilyid() == null || "".equals(entity.getFamilyid())) {
	            result.setMsg("缺少家族ID参数，请重试");
	            res = new JsonResponse(result);
	            return res;
	        }
	        Map<String, String> map = new HashMap<String, String>();
	        if (entity.getStart() != null)
	            map.put("start", entity.getStart().toString());
	        if (entity.getCount() != null)
	            map.put("count", entity.getCount().toString());
	        map.put("familyid", entity.getFamilyid());
	        List<Event> events = edao.getEventList(map);
	        for (Event event : events) {
	            EventreadQuery eventReadExample = new EventreadQuery();
	            eventReadExample.or().andEventidEqualTo(event.getEventid());
	            int count = erdao.countByExample(eventReadExample);

	            String content = event.getEventcontent();
				if (content!=null&&content.length()>0) {
					//去除html标签
					content  = content.replaceAll("</?[^>]+>", "");
					content  = content.replaceAll("\\s*|\t|\r|\n", "");
					content	 = content.replaceAll("&nbsp;"," ");
					//截取1/10长度
					content  = content.substring(0, (content.length()/10));
					event.setEventcontent(content);
				} 
	            event.setReadcount(count);
	        }
	        
	        result = new Result(MsgConstants.RESUL_SUCCESS);
	        res = new JsonResponse(result);
		} catch (Exception e) {
			res = new JsonResponse(result);
			log_.error("[EventServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getEventDetail(Event entity) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			if (entity.getEventid() == null || "".equals(entity.getEventid())) {
	            result.setMsg("缺少大事记ID参数，请重试");
	            res = new JsonResponse(result);
	            return res;
	        }
	        //作为阅读者信息传入
	        if(entity.getUpdateid()==null ||"".equals(entity.getUpdateid())) {
	            result.setMsg("缺少阅读者ID参数，请重试");
	            res = new JsonResponse(result);
	            return res;
	        }

	        Event event = edao.selectByPrimaryKey(entity.getEventid());

	        // 插入最新的阅读者信息
	        Eventread eventRead = new Eventread();
	        eventRead.setId(UUIDUtils.getUUID());
	        eventRead.setCreatetime(new Date());
	        eventRead.setEventid(event.getEventid());
	        eventRead.setUserid(entity.getUpdateid());
	        erdao.insert(eventRead);

	        // 统计最新的阅读数量信息
	        EventreadQuery eventReadExample = new EventreadQuery();
	        eventReadExample.or().andEventidEqualTo(entity.getEventid());
	        int count = erdao.countByExample(eventReadExample);

	        EventVO eventVO = new EventVO();
	        eventVO.setCount(count);
	        eventVO.setEvent(event);
	        
	        result = new Result(MsgConstants.RESUL_SUCCESS);
	        res = new JsonResponse(result);
	        res.setData(eventVO);
		} catch (Exception e) {
			res = new JsonResponse(result);
			log_.error("[EventServiceImpl---Error:]", e);
		}
		return res;
	}
}
