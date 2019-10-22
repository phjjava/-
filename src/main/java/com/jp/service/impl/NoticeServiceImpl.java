package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.controller.FlowableController;
import com.jp.dao.BranchDao;
import com.jp.dao.NoticeMapper;
import com.jp.dao.NoticefileDao;
import com.jp.dao.NoticereadDao;
import com.jp.dao.NoticetopDao;
import com.jp.dao.UserDao;
import com.jp.entity.Banner;
import com.jp.entity.BannerQuery;
import com.jp.entity.Branch;
import com.jp.entity.BranchKey;
import com.jp.entity.EditorialBoard;
import com.jp.entity.EditorialBoardExample;
import com.jp.entity.InstructionTemplateQuery;
import com.jp.entity.JpXing;
import com.jp.entity.Notice;
import com.jp.entity.NoticeDetailVO;
import com.jp.entity.NoticeExample;
import com.jp.entity.NoticeExample.Criteria;
import com.jp.entity.NoticeVO;
import com.jp.entity.Noticefile;
import com.jp.entity.NoticefileQuery;
import com.jp.entity.Noticeread;
import com.jp.entity.NoticereadQuery;
import com.jp.entity.Noticetop;
import com.jp.entity.NoticetopQuery;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.service.FlowableService;
import com.jp.service.NoticeService;
import com.jp.service.UserContextService;
import com.jp.service.UserService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

@Service
public class NoticeServiceImpl implements NoticeService {

	private final Logger log_ = LogManager.getLogger(NoticeServiceImpl.class);
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private NoticefileDao noticeFileMapper;
	@Autowired
	private NoticetopDao noticeTopMapper;
	@Autowired
	private NoticereadDao noticeReadMapper;
	@Autowired
	private UserDao userMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private BranchDao branchMapper;
	//审批流有关
	@Autowired
    private RuntimeService runtimeService;
	@Autowired
    private TaskService taskService;
	@Autowired
    private RepositoryService repositoryService;
	@Autowired
    private ProcessEngine processEngine;
	@Autowired
	private FlowableService flowableService;
	@Autowired
	private UserContextService userContextService;

	@Override
	public JsonResponse pageQuery(PageModel<NoticeVO> pageModel, Notice notice) {
		Result result = null;
		JsonResponse res = null;
		if (pageModel.getPageNo() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageNo不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (pageModel.getPageSize() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageSize不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人 userid
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		if (StringTools.isEmpty(userid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("用户非法！");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (StringTools.isEmpty(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数familyid为空!");
			res = new JsonResponse(result);
			return res;
		}
		//当前登录人所管理的编委会id
		String ebid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_EBID);
		if (StringTools.isEmpty(ebid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("header中参数ebid为空!");
			res = new JsonResponse(result);
			return res;
		}
		try {
			NoticeExample nq = new NoticeExample();
			Criteria criteria = nq.or();
			criteria.andFamilyidEqualTo(familyid);
			if (StringTools.trimNotEmpty(notice.getType())) {
				criteria.andTypeEqualTo(notice.getType());
			}
			if (StringTools.trimNotEmpty(notice.getBranchid())) {
				criteria.andBranchidEqualTo(notice.getBranchid());
			}
			if (StringTools.trimNotEmpty(notice.getDeleteflag())) {
				criteria.andDeleteflagEqualTo(notice.getDeleteflag());
			}
			List<UserManager> managers = userContextService.getUserManagers(userid, ebid);
			UserManager manager = managers.get(0);

			List<String> branchIds = userContextService.getBranchIds(familyid, userid, ebid);
			PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
			List<NoticeVO> list = new ArrayList<>();
			if (manager.getEbtype() == 1) {// 验证是否是总编委会主任
				//	currentBranchIds.add("0");
				nq.setOrderByClause("createtime DESC");
				list = noticeMapper.selectNoticeMangeList(nq);
			} else {
				if (branchIds.size() < 1) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("您的账号当前没有分支");
					res = new JsonResponse(result);
					return res;
				}
				criteria.andBranchidIn(branchIds);
				nq.setOrderByClause("createtime DESC");
				list = noticeMapper.selectNoticeMangeList(nq);
			}
			if (list != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(list);
				res.setCount(new PageInfo<NoticeVO>(list).getTotal());
				return res;
			}
		} catch (Exception e) {
			log_.error("[pageQuery方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public Notice get(String noticeid) throws Exception {
		//当前登录人 familyid
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Notice notice = noticeMapper.selectByPrimaryKey(noticeid);
		if (notice != null) {
			BranchKey key = new BranchKey();
			key.setBranchid(notice.getBranchid());
			key.setFamilyid(familyid);
			Branch branch = branchMapper.selectByPrimaryKey(key);
			if (branch != null) {
				notice.setBranchnamePlus(branch.getArea() + "_" + branch.getCityname() + "_" + branch.getXname() + "_"
						+ branch.getAddress() + "_" + branch.getBranchname());
			}
			notice.setCreatetimeStr(formatter.format(notice.getCreatetime()));
			return notice;
		} else {
			return null;
		}
	}

	@Override
	public int changeStatus(Notice notice) {
		int count = noticeMapper.updateByPrimaryKeySelective(notice);
		if (count == 1) {
			return count;
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
	}

	@Override
	public JsonResponse saveNotice(Notice notice) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (StringTools.trimIsEmpty(notice.getNoticetitle())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数noticetitle不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (StringTools.trimIsEmpty(notice.getNoticetype())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数noticetype不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {

			String userId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
			String familyId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
			User user=userMapper.selectByPrimaryKey(userId);
			

			if (StringTools.trimIsEmpty(userId)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户非法！");
				res = new JsonResponse(result);
				return res;
			}
			
			if (StringTools.trimIsEmpty(familyId)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("header中参数familyid为空!");
				res = new JsonResponse(result);
				return res;
			}
			if (StringTools.trimNotEmpty(notice.getNoticeid())) {
				notice.setUpdateid(userId);
				notice.setFamilyid(familyId);

				if (notice.getNoticetype() == 0) {
					notice.setBranchid("0");
				}
				notice.setUpdatetime(new Date());
				status = noticeMapper.updateByPrimaryKeySelective(notice);
				// 维护修改noticetop表关系
				NoticetopQuery dq = new NoticetopQuery();
				dq.or().andNoticeidEqualTo(notice.getNoticeid());
				noticeTopMapper.deleteByExample(dq);
				if (StringTools.trimNotEmpty(notice.getTobranchid())) {
					// 先删除再重新添加
					status = saveNoticetop(notice, notice.getNoticeid());
				}
			} else {

				//生成公告id
				String noticeid = UUIDUtils.getUUID();
				//部署流程文件
				  RepositoryService  repositoryService = processEngine.getRepositoryService(); 
				  Deployment deployment = repositoryService.createDeployment().addClasspathResource("ExpenseProcessNotice.bpmn20.xml") .deploy(); 
				//获取用户已经部署好的流程
				  ProcessDefinition processDefinition =repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()) .singleResult();
				  System.out.println("Found process definition : " +processDefinition.getName());
				//启动审批流程
				HashMap<String, Object> map = new HashMap<>();
				 //调用对应方法,返回的数据为分编委会人员userid值
				 String  userIdNew=flowableService.deploynew(noticeid);
				 //调用对应方法,返回总编委会所有成员userid
				 String  userIdNewTwo=flowableService.deploynewTwo(noticeid);
				 //调用对应方法,返回总编委会主任userid
				 //String  userIdNewThree=flowableService.deploynewThree();
				
				 map.put("noticeid", noticeid);//放入公告id
				 map.put("title", notice.getNoticetitle());//放入公告标题
			     map.put("content", notice.getNoticecontent());//放入公告内容
			   //判断该家族是否含有分编委会(有则设置为true，没有设置false)
				 if("未找到对应的编委会"==userIdNew) {
					 	map.put("outcome", "false");
					}else if("跳过"==userIdNew){
						map.put("outcome", "跳过");//总编委主任发起情况
					}else {
						map.put("outcome", "true");
					}
		        map.put("taskUser", userId);
		        map.put("userOneIds", userIdNew);//设置候选人
		        map.put("userTwoIds", userIdNewTwo);//设置候选人
		        //map.put("userThreeIds", userIdNewThree);//设置候选人
				ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess",map);//启动流程
				System.out.println("流程创建成功Id为:"+processInstance.getId());
				 
				//判断流程是否全部通过全部通过后，新增公告	//以下是未增加审批流源代码
				notice.setNoticeid(noticeid);
				notice.setCreateid(userId);
				notice.setCreatename(user.getUsername());
				notice.setFamilyid(familyId);
				if (notice.getNoticetype() == 0) {
					notice.setBranchid("0");
				}
				Date insertDate = new Date();
				notice.setCreatetime(insertDate);
				notice.setUpdateid(userId);
				notice.setUpdatetime(insertDate);
				notice.setDeleteflag(ConstantUtils.DELETE_FALSE);
				notice.setExaminestatus(0);//0:审核中 1:通过 2:驳回
				if (notice.getCreatetimeStr() != null) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					notice.setCreatetime(formatter.parse(notice.getCreatetimeStr()));
				}
				status = noticeMapper.insertSelective(notice);
				// 维护保存dytop表关系
				if (StringTools.trimNotEmpty(notice.getTobranchid())) {
					status = saveNoticetop(notice, noticeid);
				}
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 保存通知置顶信息
	 */
	private int saveNoticetop(Notice notice, String noticeid) {
		String branchid = notice.getTobranchid();
		String[] branchids = branchid.split(",");
		int status = 0;
		for (String str : branchids) {
			// 切除分支名字
			String subId = str.substring(0, 32);
			Noticetop dytop = new Noticetop();
			dytop.setBranchid(subId);
			dytop.setNoticeid(noticeid);
			dytop.setId(UUIDUtils.getUUID());
			status = noticeTopMapper.insertSelective(dytop);
		}
		return status;
	}

	@Override
	public int batchDelete(String[] noticeids) throws Exception {
		return noticeMapper.batchDelete(noticeids);
	}

	@Override
	public List<Noticefile> selectntfile(NoticefileQuery example) {

		return noticeFileMapper.selectByExample(example);
	}

	@Override
	public JsonResponse getNoticeDetailExt(Notice entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getNoticeid() == null || "".equals(entity.getNoticeid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不存在公告信息");
			res = new JsonResponse(result);
			return res;
		}
		NoticeVO noticeVO = new NoticeVO();
		// 获取公告详情
		NoticeExample noticeExample = new NoticeExample();
		noticeExample.or()/* .andBranchidEqualTo(entity.getBranchid()) */
				.andNoticeidEqualTo(entity.getNoticeid()).andDeleteflagEqualTo(0);
		List<Notice> notices = noticeMapper.selectByExample(noticeExample);
		if (notices.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不存在公告信息");
			res = new JsonResponse(result);
			return res;
		}
		noticeVO.setNotice(notices.get(0));

		// 设置更新时间
		// noticeVO.setCreatetime(notices.get(0).getUpdatetime());
		// 获取公告附件列表
		NoticefileQuery noticeFileExample = new NoticefileQuery();
		noticeFileExample.or().andBranchidEqualTo(entity.getBranchid()).andNoticeidEqualTo(entity.getNoticeid());
		List<Noticefile> noticeFiles = noticeFileMapper.selectByExample(noticeFileExample);
		noticeVO.setCountFiles(noticeFiles.size());

		// 插入公告已读人员列表
		// NoticeRead noticeRead = new NoticeRead();
		// noticeRead.setId(UUIDFactory.getUUIDStr());
		// noticeRead.setNoticeid(entity.getNoticeid());
		// noticeRead.setUserid(entity.getUpdateid());
		// noticeRead.setCreatetime(new Date());
		// noticeReadMapper.insert(noticeRead);

		NoticereadQuery noticeReadExample = new NoticereadQuery();
		noticeReadExample.or().andNoticeidEqualTo(entity.getNoticeid());
		List<Noticeread> noticeReads = noticeReadMapper.selectByExample(noticeReadExample);
		// noticeVO.setNoticeReads(noticeReads);
		noticeVO.setCountReads(noticeReads.size());
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(noticeVO);
		return res;
	}

	@Override
	public JsonResponse sendNotice(Notice entity) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = userService.checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (entity.getNoticetitle() == null || "".equals(entity.getNoticetitle())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少公告标题参数");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getNoticecontent() == null || "".equals(entity.getNoticecontent())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少公告内容参数");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getCreateid() == null || "".equals(entity.getCreateid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少用户ID参数");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getNoticetype() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少公告类型noticetype参数");
			res = new JsonResponse(result);
			return res;
		}
		int status = 0;
		try {
			//生成公告id
			String uuid = UUIDUtils.getUUID();
			//部署流程文件
			  RepositoryService  repositoryService = processEngine.getRepositoryService(); 
			  Deployment deployment = repositoryService.createDeployment().addClasspathResource("ExpenseProcessNotice.bpmn20.xml") .deploy(); 
			//获取用户已经部署好的流程
			  ProcessDefinition processDefinition =repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()) .singleResult();
			  System.out.println("Found process definition : " +processDefinition.getName());
			//启动审批流程
			HashMap<String, Object> map = new HashMap<>();
			 //调用对应方法,返回的数据为分编委会人员userid值
			 String  userIdNew=flowableService.deploynew(uuid);
			 //调用对应方法,返回总编委会所有成员userid
			 String  userIdNewTwo=flowableService.deploynewTwo(uuid);
			 //调用对应方法,返回总编委会主任userid
			 //String  userIdNewThree=flowableService.deploynewThree();
			
			 map.put("noticeid", uuid);//放入公告id
			 map.put("title", entity.getNoticetitle());//放入公告标题
		     map.put("content", entity.getNoticecontent());//放入公告内容
		   //判断该家族是否含有分编委会(有则设置为true，没有设置false)
			 if("未找到对应的编委会"==userIdNew) {
				 	map.put("outcome", "false");
				}else if("跳过"==userIdNew){
					map.put("outcome", "跳过");//总编委主任发起情况
				}else {
					map.put("outcome", "true");
				}
			User user = userMapper.selectByPrimaryKey(entity.getCreateid());//未加审批原码
	        map.put("taskUser", entity.getCreateid());
	        map.put("userOneIds", userIdNew);//设置候选人
	        map.put("userTwoIds", userIdNewTwo);//设置候选人
	        //map.put("userThreeIds", userIdNewThree);//设置候选人
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess",map);//启动流程
			System.out.println("流程创建成功Id为:"+processInstance.getId());
			
			
			//未加审批原码
			BranchKey branchKey = new BranchKey();
			branchKey.setBranchid(user.getBranchid());
			branchKey.setFamilyid(user.getFamilyid());
			Branch branch = branchMapper.selectByPrimaryKey(branchKey);
			entity.setExaminestatus(0);//0:审核中 1:通过 2:驳回
			entity.setNoticeid(uuid);
			entity.setDeleteflag(0);
			entity.setCreatetime(new Date());
			entity.setCreateid(entity.getCreateid());
			entity.setCreatename(user.getUsername());
			entity.setUpdateid(entity.getCreateid());
			entity.setUpdatetime(new Date());
			entity.setImgurl(user.getImgurl());
			entity.setBranchid(user.getBranchid());
			entity.setBranchname(branch.getBranchname());
			entity.setType(0);
			status = noticeMapper.insertSelective(entity);
			List<Noticefile> noticeFiles = entity.getNoticeFiles();
			if (noticeFiles != null && noticeFiles.size() > 0) {
				for (Noticefile noticeFile : noticeFiles) {
					noticeFile.setNoticeid(uuid);
					noticeFile.setFileid(UUIDUtils.getUUID());
					noticeFile.setBranchid(user.getBranchid());
					status = noticeFileMapper.insert(noticeFile);
				}
			}
		} catch (Exception e) {
			log_.error("[sendNotice方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		if (status > 0) {
			result = new Result(MsgConstants.RESUL_SUCCESS);
			result.setMsg("发布成功");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse getNoticelistOfBranch(Notice entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getBranchid() == null || "".equals(entity.getBranchid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不存在分支信息");
			res = new JsonResponse(result);
			return res;
		}
		// 需要返回的总的公告列表（包括置顶公告和本分支公告）
		List<Notice> notices = new ArrayList<Notice>();

		// 查询是否有针对该分支的置顶公告，有则加在公告列表最前
		NoticetopQuery noticeTopExample = new NoticetopQuery();
		noticeTopExample.or().andBranchidEqualTo(entity.getBranchid());
		List<Noticetop> noticeTops = noticeTopMapper.selectByExample(noticeTopExample);
		for (Noticetop noticeTop : noticeTops) {
			NoticeExample noticeExample = new NoticeExample();
			noticeExample.or().andNoticeidEqualTo(noticeTop.getNoticeid()).andDeleteflagEqualTo(0);
			List<Notice> nts = noticeMapper.selectByExample(noticeExample);
			if (nts.size() > 0)
				for (Notice notice : nts) {
					notice.setType(1);
					notices.add(notice);
				}
		}

		// 查询本分支所有公告
		NoticeExample noticeExample = new NoticeExample();
		noticeExample.setPageSize(entity.getCount());
		noticeExample.setPageNo(entity.getStart());
		noticeExample.or().andBranchidEqualTo(entity.getBranchid()).andDeleteflagEqualTo(0);
		noticeExample.setOrderByClause("updatetime desc");
		List<Notice> notices2 = noticeMapper.selectByExample(noticeExample);
		if (notices2.size() > 0)
			notices.addAll(notices2);

		if (notices2.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("没有公告信息");
			res = new JsonResponse(result);
			return res;
		}

		List<NoticeDetailVO> noticeDetailVOs = new ArrayList<NoticeDetailVO>();
		for (Notice notice : notices) {
			JsonResponse result2 = getNoticeDetail(notice);
			if (result2.getCode() == 0) {
				noticeDetailVOs.add((NoticeDetailVO) result2.getData());
			}
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("获取成功");
		res = new JsonResponse(result);
		res.setData(noticeDetailVOs);
		return res;
	}

	@Override
	public JsonResponse getNoticeDetail(Notice entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getNoticeid() == null || "".equals(entity.getNoticeid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不存在公告信息");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getUpdateid() == null || "".equals(entity.getUpdateid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不存在阅读者ID信息");
			res = new JsonResponse(result);
			return res;
		}
		NoticeDetailVO noticeDetailVO = new NoticeDetailVO();
		// 获取公告详情
		NoticeExample noticeExample = new NoticeExample();
		noticeExample.or()/* .andBranchidEqualTo(entity.getBranchid()) */
				.andNoticeidEqualTo(entity.getNoticeid()).andDeleteflagEqualTo(0);
		List<Notice> notices = noticeMapper.selectByExample(noticeExample);
		if (notices.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("不存在该公告信息");
			res = new JsonResponse(result);
			return res;
		}
		noticeDetailVO.setNotice(notices.get(0));

		// 设置更新时间
		// noticeVO.setCreatetime(notices.get(0).getUpdatetime());
		// 获取公告附件列表
		NoticefileQuery noticeFileExample = new NoticefileQuery();
		noticeFileExample.or()/* .andBranchidEqualTo(entity.getBranchid()) */
				.andNoticeidEqualTo(entity.getNoticeid());
		List<Noticefile> noticeFiles = noticeFileMapper.selectByExample(noticeFileExample);
		noticeDetailVO.setNoticeFiles(noticeFiles);
		noticeDetailVO.setCountFiles(noticeFiles.size());

		// 插入公告已读人员列表
		Noticeread noticeRead = new Noticeread();
		noticeRead.setId(UUIDUtils.getUUID());
		noticeRead.setNoticeid(entity.getNoticeid());
		noticeRead.setUserid(entity.getUpdateid());
		noticeRead.setCreatetime(new Date());
		noticeReadMapper.insert(noticeRead);

		NoticereadQuery noticeReadExample = new NoticereadQuery();
		noticeReadExample.or().andNoticeidEqualTo(entity.getNoticeid());
		List<Noticeread> noticeReads = noticeReadMapper.selectByExample(noticeReadExample);
		noticeDetailVO.setNoticeReads(noticeReads);
		noticeDetailVO.setCountReads(noticeReads.size());
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("获取成功");
		res = new JsonResponse(result);
		res.setData(noticeDetailVO);
		return res;
	}

	@Override
	public JsonResponse getMyPublishl(Notice entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getCreateid() == null || "".equals(entity.getCreateid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("缺少用户ID参数");
			res = new JsonResponse(result);
			return res;
		}
		NoticeExample noticeExample = new NoticeExample();
		noticeExample.or().andCreateidEqualTo(entity.getCreateid()).andDeleteflagEqualTo(0);
		List<Notice> notices = noticeMapper.selectByExample(noticeExample);
		if (notices.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("您还没有发布过公告！");
			res = new JsonResponse(result);
			return res;
		}
		NoticereadQuery noticeReadExample = new NoticereadQuery();

		for (Notice notice : notices) {
			noticeReadExample.clear();
			noticeReadExample.or().andNoticeidEqualTo(notice.getNoticeid());
			List<Noticeread> noticeReads = noticeReadMapper.selectByExample(noticeReadExample);
			notice.setCountReads(noticeReads.size());
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("获取成功");
		res = new JsonResponse(result);
		res.setData(notices);
		return res;
	}

	@Override
	public JsonResponse getNoticelist(Notice entity) {
		Result result = null;
		JsonResponse res = null;
		if (entity.getMeautype() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数meautype为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getStart() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数start为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (entity.getCount() == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数count为空！");
			res = new JsonResponse(result);
			return res;
		}

		List<Notice> notices = new ArrayList<Notice>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", entity.getStart());
		params.put("count", entity.getCount());
		try {
			if (0 == entity.getMeautype()) {
				// 获取家族得相册列表
				params.put("familyid", WebUtil.getHeaderInfo("familyid"));
				params.put("noticetype", 0);
				notices = noticeMapper.selectNotices(params);

			} else if (1 == entity.getMeautype()) {
				// 获取全部动态
				params.put("familyid", WebUtil.getHeaderInfo("familyid"));
				notices = noticeMapper.selectNotices(params);
			} else if (2 == entity.getMeautype()) {
				if (entity.getBranchid() == null || "".equals(entity.getBranchid())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数branchid为空！");
					res = new JsonResponse(result);
					return res;
				}
				// 按照城市编码获取公告列表
				params.put("cityCode", entity.getBranchid());
				params.put("familyid", WebUtil.getHeaderInfo("familyid"));
				notices = noticeMapper.selectByCityCode(params);
			} else if (3 == entity.getMeautype()) {
				if (entity.getBranchid() == null || "".equals(entity.getBranchid())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数branchid为空！");
					res = new JsonResponse(result);
					return res;
				}
				// 按照分支id获取动态列表
				params.put("branchid", entity.getBranchid());
				notices = noticeMapper.selectNotices(params);
			}
			if (notices.size() > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				result.setMsg("获取成功");
				res = new JsonResponse(result);
				res.setData(notices);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("没有数据");
		res = new JsonResponse(result);
		return res;
	}
	/**
	 * 查询当前人待审核公告
	 */
	@Override
	public JsonResponse selectExamine(PageModel<Notice> pageModel,String noticeid,Notice notice) {
		System.out.println("进入NoticeService方法");
		// TODO Auto-generated method stub
		JsonResponse res = null;
		Result result = null;
		InstructionTemplateQuery iq = new InstructionTemplateQuery();
		com.jp.entity.InstructionTemplateQuery.Criteria criteria = iq.createCriteria();
		notice.setDeleteflag(0);
		if (StringTools.trimNotEmpty(notice.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(notice.getDeleteflag());
		}
		iq.setOrderByClause("createtime");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		List<Notice> list = noticeMapper.selectExamine(pageModel,noticeid);
		res.setData(list);
		return res;
	}

	@Override
	public void updateNoticeTask(String variable, String taskid) {
		// TODO Auto-generated method stub
		noticeMapper.updateByExampleNew(variable, taskid);
	}

	@Override
	public void updateNoticeExamin(String noticeid, String examinestatus) {
		// TODO Auto-generated method stub
		noticeMapper.updateNoticeExamin(noticeid,examinestatus);
	}
	/**
	 * 查询已经审核的公告列表
	 */
	@Override
	public JsonResponse selectAleadyNotice(List<String> list, PageModel<Notice> pageModel, Notice notice,String familyid) {
		// TODO Auto-generated method stub
		JsonResponse res = null;
		Result result = null;
		InstructionTemplateQuery iq = new InstructionTemplateQuery();
		com.jp.entity.InstructionTemplateQuery.Criteria criteria = iq.createCriteria();
		notice.setDeleteflag(0);
		if (StringTools.trimNotEmpty(notice.getDeleteflag())) {
			criteria.andDeleteflagEqualTo(notice.getDeleteflag());
		}
		iq.setOrderByClause("createtime");
		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		List<Notice> noticelist = noticeMapper.selectAleadyNotice(pageModel,list,familyid);
		res.setData(noticelist);
		return res;
	}
	/**
	 * 个人待审核公告条数
	 */
	@Override
	public String selectExamineCount(String noticeid) {
		// TODO Auto-generated method stub
		return noticeMapper.selectExamineCount(noticeid);
	}

	@Override
	public String selectAleadyCount(String familyid) {
		// TODO Auto-generated method stub
		return noticeMapper.selectAleadyCount(familyid);
	}

}
