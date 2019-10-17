package com.jp.service;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Notice;
import com.jp.entity.NoticeVO;
import com.jp.entity.Noticefile;
import com.jp.entity.NoticefileQuery;

public interface NoticeService {
	JsonResponse pageQuery(PageModel<NoticeVO> pageModel, Notice notice);

	Notice get(String noticeid) throws Exception;

	int changeStatus(Notice notice);

	JsonResponse saveNotice(Notice notice);

	int batchDelete(@Param("array") String noticeids[]) throws Exception;

	List<Noticefile> selectntfile(NoticefileQuery example);

	JsonResponse getNoticeDetailExt(Notice notice);

	JsonResponse sendNotice(Notice notice);

	JsonResponse getNoticelistOfBranch(Notice notice);

	JsonResponse getNoticeDetail(Notice notice);

	JsonResponse getMyPublishl(Notice notice);

	JsonResponse getNoticelist(Notice notice);
	//审批查询当前人待审核公告
	JsonResponse selectExamine(PageModel<Notice> pageModel, String noticeid,Notice notice);
	//修改taskid,存放如表中
	void updateNoticeTask(String variable, String tiskid);
	//修改公告审核状态
	void updateNoticeExamin(String noticeid, String examinestatus);
	//查询已经审核的公告列表
	JsonResponse selectAleadyNotice(List<String> list, PageModel<Notice> pageModel, Notice notice, String familyid);
	//审批查询当前人待审核公告条数
	String selectExamineCount(String noticeid);
	//已审批查询当前人待审核公告条数
	String selectAleadyCount(String familyid);
}
