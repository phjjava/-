package com.jp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jp.common.JsonResponse;
import com.jp.entity.GlobalSearch;
import com.jp.service.GlobalSearchService;

/**
 * 全局搜索
 * @author samsung
 *
 */
@Controller
@RequestMapping("globalSearch")
public class GlobalSearchController {
	
	@Autowired
	private GlobalSearchService globalService;
	
	/**
     * 关键字搜索所有动态
     * @throws IOException
     */
	@ResponseBody
	@RequestMapping(value = "/searchAll", method = RequestMethod.GET)
    public JsonResponse searchAll(GlobalSearch entity,HttpServletRequest request) {
        return globalService.searchAll(entity,request);
    }
    
    /**
     * 通讯录更多
     * @throws IOException
     */
	@ResponseBody
	@RequestMapping(value = "/searchMoreUser", method = RequestMethod.GET)
    public JsonResponse searchMoreUser(GlobalSearch entity,HttpServletRequest request) {
        return globalService.searchMoreUser(entity,request);
    }
    
    
    /**
     * 动态更多
     * @throws IOException
     */
	@ResponseBody
	@RequestMapping(value = "/searchMoreDynamics", method = RequestMethod.GET)
    public JsonResponse searchMoreDynamics(GlobalSearch entity,HttpServletRequest request) {
        return globalService.searchMoreDynamics(entity,request);
    }
    
    
    /**
     * 大事记更多
     * @throws IOException
     */
	@ResponseBody
	@RequestMapping(value = "/searchMoreEvents", method = RequestMethod.GET)
    public JsonResponse searchMoreEvents(GlobalSearch entity,HttpServletRequest request) {
        return globalService.searchMoreEvents(entity,request);
    }
    
    
    /**
     * 人物志更多
     * @throws IOException
     */
	@ResponseBody
	@RequestMapping(value = "/searchMoreUserContentVOs", method = RequestMethod.GET)
    public JsonResponse searchMoreUserContentVOs(GlobalSearch entity,HttpServletRequest request) {
        return globalService.searchMoreUserContentVOs(entity,request);
    }


    /**
     * 相册更多
     * @throws IOException
     */
	@ResponseBody
	@RequestMapping(value = "/searchMoreBranchAlbums", method = RequestMethod.GET)
    public JsonResponse searchMoreBranchAlbums(GlobalSearch entity,HttpServletRequest request) {
        return globalService.searchMoreBranchAlbums(entity,request);
    }

}
