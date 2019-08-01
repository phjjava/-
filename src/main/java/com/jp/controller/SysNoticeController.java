package com.jp.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.CurrentSystemUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.entity.MationType;
import com.jp.entity.SysMation;
import com.jp.entity.SysNotice;
import com.jp.entity.SysNoticeType;
import com.jp.service.SysNoticeService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("system/sysnotice")
public class SysNoticeController {
	private final Logger log_ = LogManager.getLogger(BannerController.class);
	@Autowired
	private SysNoticeService noticeService;
	@ResponseBody
	@RequestMapping(value = "/selectlist", method = RequestMethod.POST)
    public JsonResponse bannerJson()  {
		Result result = null;
		JsonResponse res = null;
    	List<SysNotice> gotypeList = null;
    	try {
    		gotypeList = noticeService.selectNotice();
    			result = new Result(MsgConstants.RESUL_SUCCESS);
        		res = new JsonResponse(result);
        		res.setData(gotypeList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
    	return res;
    }
	
	/**
	 * 调取下拉框类型值（增加修改时调取使用）
	 * @param mation
	 * @param model
	 * @param mationid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selecttypelist", method = RequestMethod.POST)
    public JsonResponse selecttypelist()  {
		Result result = null;
		JsonResponse res = null;
    	List<SysNoticeType> gotypeList = null;
    	try {
    		gotypeList = noticeService.selecttypelist();
    			result = new Result(MsgConstants.RESUL_SUCCESS);
        		res = new JsonResponse(result);
        		res.setData(gotypeList);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
    	return res;
    }
	
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse save(SysNotice notice, ModelMap model,String mationid,String relevanceid,Integer stick) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			if (StringTools.notEmpty(notice.getNoticeid())) {
				// 修改
				notice.setUpdatetime(new Date());
				notice.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				notice.setRelevanceid(relevanceid);
				notice.setStick(stick);
				/*if(mationid != null){
					notice.setImgid(mationid);
				}*/
				count = noticeService.update(notice);
			} else {
				//新增
				notice.setDeleteflag(ConstantUtils.DELETE_FALSE);
				notice.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				notice.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				notice.setNoticeid(UUIDUtils.getUUID()); 
				notice.setUpdatetime(new Date());
				notice.setCreatetime(new Date());
				notice.setRelevanceid(relevanceid);
				notice.setStick(stick);
				notice.setCount(0);
				/*if(mationid != null){
					notice.setImgid(mationid);
				}*/
				count = noticeService.insert(notice);
			}
			if(count > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
}
