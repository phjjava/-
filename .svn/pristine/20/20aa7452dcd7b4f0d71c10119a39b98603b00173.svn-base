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
import com.jp.common.PageModel;
import com.jp.dao.NoticetopDao;
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
    public String list(PageModel<NoticeVO> pageModel,Notice notice, ModelMap model){
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
 		model.put("pageModel", pageModel);
 		model.put("notice", notice);
 	} catch (Exception e) {
 		e.printStackTrace();
 		log_.error("[JPSYSTEM]", e);
 	}
 	   return "notice/noticeList";
 	   
    }
    @RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
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
			model.put("notice", notice);
			model.put("ntlist", ntlist);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return "notice/notice";
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
      public String changeStatus(Notice notice)  {
      	String result = null;
      	try {
      		noticeservice.changeStatus(notice);
   			result="1";
   		} catch (Exception e) {
   			result="0";
   			e.printStackTrace();
   			log_.error("[JPSYSTEM]", e);
   		}
      	return result;
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
	public String saveNotice(Notice notice,@RequestParam("file")MultipartFile[] file,HttpServletRequest request,String fileids)  {
		String result = null;
		String url = null;
		String ntfidArray [] = null;
		try{
			if (request.getCharacterEncoding() == null) {
				request.setCharacterEncoding("UTF-8");
				}
			if(StringTools.trimNotEmpty(fileids)){
				String ntfid = fileids.substring(0, fileids.length()-1);
	     		ntfidArray = ntfid.split(",");
			}
			List<String> fileNams = new  ArrayList<String>();
			List<File> fileList = new ArrayList<File>();
			File ntfile = null;
			String fileName = "";
			for(MultipartFile fileM : file) {
				fileName = fileM.getOriginalFilename();
				fileNams.add(fileName);
			}
			String pathDir = "/upload";
			String logoRealPathDir = request.getSession().getServletContext().getRealPath(pathDir);
			for(int i = 0; i < file.length; i++) {
				ntfile = new File(logoRealPathDir+file[i].getOriginalFilename());
				if(!ntfile.exists()) {
					file[i].transferTo(ntfile);
					fileList.add(ntfile);
				}
			}
			String status = "";
			if(fileList != null && fileList.size() > 0){
				 status = UploadUtil.taskFileUpload(fileList, fileNams);
				 Gson gson = new GsonBuilder().create();
				 if(StringTools.trimNotEmpty(status)){
					 JsonObject json = gson.fromJson(status, JsonObject.class);
					 JsonObject jsonInfo = gson.fromJson(json.get("data"), JsonObject.class);
					 url = jsonInfo.get("url").toString();
				 }
			}			
		    Noticefile ntf = null;
		    List<Noticefile> ntList  = new ArrayList<Noticefile>();
		    if(StringTools.trimNotEmpty(url)){
			    for( int i = 0; i < file.length; i ++){
			    	ntf = new Noticefile();
			    	ntf.setBranchid(notice.getBranchid());
			    	ntf.setFileurl(url);
			    	ntf.setFileid(UUIDUtils.getUUID());
			    	ntf.setFilename(fileName);
			    	ntList.add(ntf);
			    }
		    }
		    noticeservice.saveNotice(notice, ntList,ntfidArray);
		    result = "1";
		}catch(Exception e){
			result = "0";
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);

		}
		return result;
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
    public String batchDelete(String noticeids){
    	String result=null;
    	try {
	 		String noticeid = noticeids.substring(0, noticeids.length());
	 		String noticeArray [] = noticeid.split(",");
	 		noticeservice.batchDelete(noticeArray);
	 		result="1";
	 	} catch (Exception e) {
	 		result = "0";
	 		e.printStackTrace();
	 		log_.error("[JPSYSTEM]", e);
	 	}
	    	return result;
	    }
}
