package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Notice;
import com.jp.entity.NoticeVO;
import com.jp.entity.Noticefile;
import com.jp.entity.NoticefileQuery;

public interface NoticeService {
	PageModel<NoticeVO> pageQuery(PageModel<NoticeVO> pageModel, Notice notice) throws Exception;

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
}
