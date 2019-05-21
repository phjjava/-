package com.jp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.NoticetopDao;
import com.jp.entity.Dynamicfile;
import com.jp.entity.Notice;
import com.jp.entity.Noticefile;
import com.jp.entity.NoticefileQuery;
import com.jp.entity.Noticetop;
import com.jp.entity.NoticetopQuery;
import com.jp.entity.NoticeVO;
import com.jp.service.BranchService;
import com.jp.service.NoticeService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.UploadUtil;

@Controller
@RequestMapping("notice")
public class NoticeController {
    private final Logger log_ = LogManager.getLogger(NoticeController.class);
    @Autowired
    private NoticeService noticeservice;
    @Autowired
    private NoticetopDao noticetopDao;
    
    @RequestMapping(value="/list",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse list(PageModel<NoticeVO> pageModel,Notice notice, ModelMap model){
    	Result result = null;
    	JsonResponse res = null;
 	   	try {
	 		noticeservice.pageQuery(pageModel,notice);
	 		if(pageModel.getList()!=null){
	 			if(pageModel.getPageSize()==0){
	 				if(pageModel.getPageNo()!=null&&!"1".equals(pageModel.getPageNo())){
	 					pageModel.setPageNo(pageModel.getPageNo() - 1);
	 					noticeservice.pageQuery(pageModel,notice);
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
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
	public JsonResponse get(HttpServletRequest request, ModelMap model) {
    	Result result = null;
    	JsonResponse res = null;
		try {
			String noticeid = request.getParameter("noticeid");
			Notice notice = noticeservice.get(noticeid);
			NoticefileQuery nfq = new NoticefileQuery();
			//获取置顶top信息
			initNoticeTop(noticeid, notice);
			com.jp.entity.NoticefileQuery.Criteria criteria = nfq.createCriteria();
			if(StringTools.trimNotEmpty(notice.getNoticeid())){
				criteria.andNoticeidEqualTo(notice.getNoticeid());
			}
			List<Noticefile> ntlist = noticeservice.selectntfile(nfq);
			notice.setNoticeFiles(ntlist);
			result = new Result(MsgConstants.RESUL_SUCCESS);
	 		res = new JsonResponse(result);
	 		res.setData(notice);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
	 		res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return res;
	}
    /**
     * 
     * @描述 初始化noticetop信息
     * @作者 jinlizhi
     * @时间 2017年5月21日下午10:27:56
     * @参数 @param noticeid
     * @参数 @param notice
     * @return void
     */
	private void initNoticeTop(String noticeid, Notice notice) {
		NoticetopQuery example=new NoticetopQuery();
		example.or().andNoticeidEqualTo(noticeid);
		List<Noticetop> noticetopList = noticetopDao.selectByExample(example);
		if(noticetopList != null && ! noticetopList.isEmpty()){
			StringBuffer sb=new StringBuffer();
			for (Noticetop noticetop : noticetopList) {
				sb.append(noticetop.getBranchid());
				sb.append(",");
			}
			String braStr = sb.toString();
			String substring = braStr.substring(0, braStr.length()-1);
			notice.setTobranchid(substring);
		}
	}
   
    @ResponseBody
   	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
      public JsonResponse changeStatus(Notice notice)  {
    	Result result = new Result(MsgConstants.RESUL_FAIL);
    	JsonResponse res = null;
    	Integer count = 0;
      	try {
      		count = noticeservice.changeStatus(notice);
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
     * @描述 公告编辑或保存
     * @作者 sj
     * @时间 2017年5月9日下午4:19:45
     * @参数 @param notice
     * @参数 @return
     * @return String
     */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JsonResponse saveNotice(Notice notice,HttpServletRequest request,String fileids)  {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		String ntfidArray [] = null;
		try{
			List<Noticefile> nfList  = notice.getNoticeFiles();
			if(StringTools.trimNotEmpty(notice.getNoticeid())) {
				for (int i = 0; i < nfList.size(); i++) {
					ntfidArray[i] = nfList.get(i).getFileid();  //更新公告时，如果有附件，先删除附件再重新写入
				}
			}
		    noticeservice.saveNotice(notice, nfList,ntfidArray);
		    result = new Result(MsgConstants.RESUL_SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
    /**
     * @描述 动态批量删除
     * @作者 hongjun
     * @时间 2017年5月11日上午9:37:29
     * @参数 @param noticeids
     * @参数 @return
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public JsonResponse batchDelete(String noticeids){
    	Result result = new Result(MsgConstants.RESUL_FAIL);
 	    JsonResponse res = null;
    	try {
	 		String noticeid = noticeids.substring(0, noticeids.length());
	 		String noticeArray [] = noticeid.split(",");
	 		noticeservice.batchDelete(noticeArray);
	 		result = new Result(MsgConstants.RESUL_SUCCESS);
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		log_.error("[JPSYSTEM]", e);
	 	}
		res = new JsonResponse(result);
		return res;
    }
}
