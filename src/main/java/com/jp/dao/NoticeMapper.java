package com.jp.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Notice;
import com.jp.entity.NoticeExample;
import com.jp.entity.NoticeVO;

public interface NoticeMapper {
	int countByExample(NoticeExample example);

	int deleteByExample(NoticeExample example);

	int deleteByPrimaryKey(String noticeid);

	int insert(Notice record);

	int insertSelective(Notice record);

	List<Notice> selectByExampleWithBLOBs(NoticeExample example);

	List<Notice> selectByExample(NoticeExample example);

	List<Notice> selectNotices(Map<String, Object> params);

	Notice selectByPrimaryKey(String noticeid);

	int updateByExampleSelective(@Param("record") Notice record, @Param("example") NoticeExample example);

	int updateByExampleWithBLOBs(@Param("record") Notice record, @Param("example") NoticeExample example);

	int updateByExample(@Param("record") Notice record, @Param("example") NoticeExample example);

	int updateByPrimaryKeySelective(Notice record);

	int updateByPrimaryKeyWithBLOBs(Notice record);

	int updateByPrimaryKey(Notice record);

	int batchDelete(@Param("array") String noticeid[]);

	List<Notice> selectBranchNoticeList(Notice notice);

	/**
	 * 
	 * @描述 查询带阅读量的公告列表
	 * @作者 jinlizhi
	 * @时间 2017年5月25日下午3:07:43
	 * @参数 @param nq
	 * @参数 @return
	 * @return List<NoticeVO>
	 */
	List<NoticeVO> selectNoticeMangeList(NoticeExample nq);

	List<Notice> findByIdsMap(Map<String, Object> map);

	List<Notice> selectByCityCode(Map<String, Object> params);
	//查询当前人待审核公告
	List<Notice> selectExamine(@Param(value="pageModel")PageModel<Notice> pageModel,@Param(value="noticeid") String noticeid);
	//存放taskid
	void updateByExampleNew(@Param(value="variable")String variable,@Param(value="taskid") String taskid);
	//修改审批状态码
	void updateNoticeExamin(@Param(value="noticeid")String noticeid, @Param(value="examinestatus")String examinestatus);
	//查询审批的公告
	List<Notice> selectAleadyNotice(@Param(value="pageModel")PageModel<Notice> pageModel, @Param(value="list")List<String> list, @Param(value="familyid")String familyid);
	//查询公告发起人以及发起日期
	Notice selectNotice(String noticeid);
	//查询当前人待审核公告条数
	String selectExamineCount(@Param(value="noticeid")String noticeid);
	//查询当前人已审核公告条数
	String selectAleadyCount(@Param(value="familyid")String familyid);

}