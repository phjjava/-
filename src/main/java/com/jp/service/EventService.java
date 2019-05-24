package com.jp.service;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Event;

public interface EventService {
	 PageModel<Event> pageQuery(PageModel<Event> pageModel,Event event) throws Exception;
	 Event get(String eventid) throws Exception;   
	 int batchDelete(@Param("array") String eventids[]) throws Exception;
	 String saveEvent(Event event);
	 int changeStatus(Event event);
	 
	 /**
		* 以下方法用于api
	 */
	 
	 JsonResponse getEventList(Event entity);
	 
	 JsonResponse getEventDetail(Event entity);
}
