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
import com.jp.common.PageModel;
import com.jp.common.Result;
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
	@RequestMapping(value = "/selectlist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse homepagelist(PageModel<SysNotice> pageModel, SysNotice notice, ModelMap model,String noticetitle) {
		Result result = null;
		JsonResponse res = null;
		try {
			noticeService.pageQuery(pageModel, notice,noticetitle);
			if (pageModel.getList() != null) {
				if (pageModel.getPageSize() == 0) {
					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
						pageModel.setPageNo(pageModel.getPageNo() - 1);
						noticeService.pageQuery(pageModel, notice,noticetitle);
					}
				}
			}

			result = new Result(MsgConstants.RESUL_SUCCESS);	
			res = new JsonResponse(result);
			res.setData(pageModel.getList());
			res.setCount(pageModel.getPageInfo().getTotal());
			
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return res;
	}
	
	/**
	 * 详情
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectOne", method = RequestMethod.POST)
    public JsonResponse selectOne(String noticeid,Integer code)  {
		Result result = null;
		JsonResponse res = null;
    	List<SysNotice> gotypeList = null;
    	try {
    		gotypeList = noticeService.selectOne(noticeid,code);
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
	 * 状态更改
	 * @param banner
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public JsonResponse changeStatus(SysNotice notice) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			count = noticeService.changeStatus(notice);
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
	
	/**
	 * 
	 * @描述 公告批量删除
	 * @时间 2017年5月10日下午5:32:11
	 * @参数 @param banner
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/noticeDeleteAll", method = RequestMethod.POST)
	public JsonResponse noticeDeleteAll(String noticeids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			// a,b,c
			String mationid = noticeids.substring(0, noticeids.length());
			//按逗号截取放入数组
			String mationtypeArray[] = mationid.split(",");
			noticeService.noticeDeleteAll(mationtypeArray);
			//返回成功
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
	
	/**
	 * 类型表列表
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/findtypelist", method = RequestMethod.POST)
    public JsonResponse findtypelist()  {
		Result result = null;
		JsonResponse res = null;
    	List<SysNoticeType> gotypeList = null;
    	try {
    		gotypeList = noticeService.selecttype();
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
	 * 类型表详情
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/selecttypeone", method = RequestMethod.POST)
	public JsonResponse selecttypeone(String typeid)  {
		Result result = null;
		JsonResponse res = null;
		List<SysNoticeType> gotypeList = null;
		try {
			gotypeList = noticeService.selecttypeone(typeid);
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
	 * 类型表新增/编辑
	 * @param mation
	 * @param model
	 * @param mationid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/typesave", method = RequestMethod.POST)
	public JsonResponse typesave(SysNoticeType noticetype, ModelMap model,String typeid) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			if (StringTools.notEmpty(noticetype.getTypeid())) {
				// 修改
				/*mation.setUpdatetime(new Date());
				mation.setUpdateid(CurrentSystemUserContext.getSystemUserContext().getUserid());*/
				/*if(typeid != null){
					mationtype.setImgid(typeid);
				}*/
				count = noticeService.updatetype(noticetype);
			} else {
				//新增
				noticetype.setDeleteflag(ConstantUtils.DELETE_FALSE);
				noticetype.setCreateid(CurrentSystemUserContext.getSystemUserContext().getUserid());
				noticetype.setTypeid(UUIDUtils.getUUID()); 
				noticetype.setCreatetime(new Date());
				/*if(typeid != null){
					mation.setImgid(mationid);
				}*/
				count = noticeService.inserttype(noticetype);
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
	/**
	 * 
	 * @描述 公告类型批量删除
	 * @时间 2017年5月10日下午5:32:11
	 * @参数 @param banner
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/noticetypeDeleteAll", method = RequestMethod.POST)
	public JsonResponse noticetypeDeleteAll(String noticetypeids) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		try {
			// a,b,c
			String mationid = noticetypeids.substring(0, noticetypeids.length());
			//按逗号截取放入数组
			String mationtypeArray[] = mationid.split(",");
			noticeService.noticetypeDeleteAll(mationtypeArray);
			//返回成功
			result = new Result(MsgConstants.RESUL_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
	
	/**
	 * api接口
	 * 公告列表接口
	 * @param pageModel
	 * @return
	 */
	@RequestMapping(value = "/apilist", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse namelist(PageModel<SysNotice> pageModel, SysNotice notice) {
		return noticeService.pageQueryApi(pageModel, notice);
	}
}
