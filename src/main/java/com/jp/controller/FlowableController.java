package com.jp.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.HttpApprovalHistory;
import com.jp.entity.Notice;
import com.jp.entity.UserManager;
import com.jp.service.FlowableService;
import com.jp.service.NoticeService;
import com.jp.service.UserService;
import com.jp.util.WebUtil;


@Controller
@RequestMapping("expense")
public class FlowableController {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private FlowableService flowableService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private NoticeService noticeService;
	private final Logger log_ = LogManager.getLogger(FlowableController.class);

	/**
	 * 查看组中当前待审核任务
	 */

	@RequestMapping(value = "/deploy", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse deploy(PageModel<Notice> pageModel, Notice notice) {// userid为登录用户id
		Result result = null;
		JsonResponse res = null;
		// 根据登录userId查询当前登录人所有待审核
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		List<Task> list = processEngine.getTaskService()//
				.createTaskQuery()//
				.taskCandidateUser(userid)// 指定组中的人任务查询
				.list();
		String noticenew = "";
		String tiskid="";
		// 遍历待处理任务,查询出公告信息
		if(null!=list && list.size()!=0) {
			for (Task task : list) {
				tiskid = task.getId();
				String variable = (String) runtimeService.getVariable(task.getProcessInstanceId(), "noticeid");
				noticenew = noticenew + "," + "'" + variable + "'";
				//将得到taskid存入对应的公告
				noticeService.updateNoticeTask(variable,tiskid);
			}
			String noticeid = noticenew.substring(1);// 截取拼接字符
			return noticeService.selectExamine(pageModel, noticeid, notice);
		}else {
			result = new Result(MsgConstants.TO_EXAMIN);
			result.setMsg("无待审核！");
			res = new JsonResponse(result);
			return res;
		}

	}
	
	/**
	 * 公告已审核列表
	 */
	@RequestMapping(value = "/aleady", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse aleady(PageModel<Notice> pageModel, String userid, Notice notice,String noticeid) {//String noticeid无用
		Result result = null;
		JsonResponse res = null;
		  //调用对应方法,返回的数据为分编委会人员userid值 
		String userIdNew=flowableService.deploynew(noticeid);
		  //调用对应方法,返回总编委会所有成员userid 
		String userIdNewTwo=flowableService.deploynewTwo(noticeid);
		String over=userIdNew+userIdNewTwo; 
		String overnew=over.substring(1);
		  //采用逗号分隔字符放入集合 
		String[] split = overnew.split(","); 
		List<String> list=new ArrayList<String>(); 
		 for (int i = 0; i < split.length; i++) {
		  list.add(split[i]); 
		  } 
		 //查询当前用户所属家族
		 String familyid=userService.selectFamilyId(userid);
		 Integer overCount=0;
		 for (String str : list) {
			 if(str.equals(userid)) {
			 	 overCount++;
			 	 } 
			 }
		 if(overCount!=0) {
			 return noticeService.selectAleadyNotice(list,pageModel,notice,familyid);
		 }else {
			 result = new Result(MsgConstants.TO_ALERDY);
				result.setMsg("无已审核！");
				res = new JsonResponse(result);
				return res;
		 }
		
	}
	
	
	
	/**
	 * 批准
	 *
	 * @param taskId 任务ID
	 */

	@RequestMapping(value = "/apply",method=RequestMethod.POST)
	@ResponseBody
	public JsonResponse apply(String taskId,String noticeid,String userid,String content,String title,String imgurl) {
		Result result = null;
		JsonResponse res = null;
		//获取流程实例id
		Task processInstance = processEngine.getTaskService().createTaskQuery().taskId(taskId).list().get(0);
		String processInstanceId=processInstance.getProcessInstanceId();
		Task tasknew = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		ExecutionEntity ee = (ExecutionEntity) processEngine.getRuntimeService().createExecutionQuery()
		            .executionId(tasknew.getExecutionId()).singleResult();
		    // 当前审批节点
		    String crruentActivityId = ee.getActivityId();
		    BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(tasknew.getProcessDefinitionId());
		    FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(crruentActivityId);
		    // 输出连线
		    List<SequenceFlow> outFlows = flowNode.getOutgoingFlows();
		    FlowElement sourceFlowElement=null;
		    for (SequenceFlow sequenceFlow : outFlows) {
		    	sourceFlowElement= sequenceFlow.getSourceFlowElement();
		            if(sourceFlowElement.getName().equals("总编委会成员")) {
		            	//执行修改公告状态码
		            	String examinestatus="1";
		            	noticeService.updateNoticeExamin(noticeid,examinestatus);
		            }
		    }
		
		  Task task = taskService.createTaskQuery()
				  .taskId(taskId)
				  .singleResult();
		  
		  if(task == null) {
			  result = new Result(MsgConstants.RESUL_FAIL);
			  	result.setMsg("此流程不存在");
				res = new JsonResponse(result);
				return res;
		}
		  //查询该条审批公告
		  Notice notice=flowableService.selectNotice(noticeid);
		  String ebname="";
		 //判断节点所在位置将该用户编委会放入记录表
		  if(sourceFlowElement.getName().equals("总编委会成员")) {
			  //查询所属总编委会
			  ebname=flowableService.selectEbname(notice.getCreateid());
		  }else if(sourceFlowElement.getName().equals("分编委会")) {
			  UserManager deploynewFour = flowableService.deploynewFour(notice.getCreateid());
			  ebname=deploynewFour.getEbname();
		  }
	        Authentication.setAuthenticatedUserId(userid);// 批注人的名称
	        // 添加批注信息
	        processEngine.getTaskService().addComment(taskId, processInstanceId,
	        		"通过" + ":" + content+":"+imgurl+":"+ebname);// comment为批注内容
		  //通过审核
		  HashMap<String, Object> map = new HashMap<>();
		  map.put("outcome", "通过");
		  taskService.complete(taskId, map);
		  result = new Result(MsgConstants.RESUL_SUCCESS);
		  result.setMsg("通过审批");
		  res = new JsonResponse(result);
		return res;
	}
	
	/**
	 * 拒绝
	 */

	@ResponseBody
	@RequestMapping(value = "/reject",method=RequestMethod.POST)
	public JsonResponse reject(String taskId,String noticeid,String userid,String content,String title,String imgurl) {
		Result result = null;
		JsonResponse res = null;
		//获取流程实例id
		Task processInstance = processEngine.getTaskService().createTaskQuery().taskId(taskId).list().get(0);
		String processInstanceId=processInstance.getProcessInstanceId();
		Task tasknew = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		ExecutionEntity ee = (ExecutionEntity) processEngine.getRuntimeService().createExecutionQuery()
		            .executionId(tasknew.getExecutionId()).singleResult();
		    // 当前审批节点
		    String crruentActivityId = ee.getActivityId();
		    BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(tasknew.getProcessDefinitionId());
		    FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(crruentActivityId);
		    // 输出连线
		    List<SequenceFlow> outFlows = flowNode.getOutgoingFlows();
		    FlowElement sourceFlowElement=null;
		    for (SequenceFlow sequenceFlow : outFlows) {
		    	sourceFlowElement= sequenceFlow.getSourceFlowElement();
		    }
		 //查询该条审批公告
		  Notice notice=flowableService.selectNotice(noticeid);
		  String ebname="";
		 //判断节点所在位置将该用户编委会放入记录表
		  if(sourceFlowElement.getName().equals("总编委会成员")) {
			  //查询所属总编委会
			  ebname=flowableService.selectEbname(notice.getCreateid());
		  }else if(sourceFlowElement.getName().equals("分编委会")) {
			  UserManager deploynewFour = flowableService.deploynewFour(notice.getCreateid());
			  ebname=deploynewFour.getEbname();
		  }
		//向flowable历史记录表commit中添加数据(查看记录时查询所需数据)
        Authentication.setAuthenticatedUserId(userid);// 批注人的名称
        // 添加批注信息
        processEngine.getTaskService().addComment(taskId, processInstanceId,
        		"驳回" + ":" + content+":"+imgurl+":"+ebname);// comment为批注内容   content建议   ebname编委会名
		HashMap<String, Object> map = new HashMap<>();
		map.put("outcome", "驳回");
		taskService.complete(taskId, map);
		//修改当前审批公告状态
		String examinestatus="2";
		noticeService.updateNoticeExamin(noticeid,examinestatus);
		result = new Result(MsgConstants.RESUL_SUCCESS);
	    result.setMsg("驳回");
		res = new JsonResponse(result);
		return res;
	}
	
	
	/**
	 * 审核详情
	 */
	@ResponseBody
	@RequestMapping(value = "/details",method=RequestMethod.POST)
	public JsonResponse details(String noticeid,String taskId) {
		Result result = null;
		JsonResponse res = null;
		/*
		 * //查询审批公告(创建人,创建日期) List<Notice> list=flowableService.selectNotice(noticeid);
		 * //获取当前人所在的总编委会 String nodenameover=flowableService.deploynewFour();
		 * System.out.println("所在总编委会="+nodenameover); //获取当前人所在的分编委会 String
		 * nodename=flowableService.deploynewFive();
		 * System.out.println("所在分编委会="+nodename);
		 */
		//查询当前节点进行判断,当前节点之前的节点都是审核通过,之后包括当前节点都是待审核中
		 Task tasknew = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		    ExecutionEntity ee = (ExecutionEntity) processEngine.getRuntimeService().createExecutionQuery()
		            .executionId(tasknew.getExecutionId()).singleResult();
		    // 当前审批节点
		    String crruentActivityId = ee.getActivityId();
		    BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(tasknew.getProcessDefinitionId());
		    FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(crruentActivityId);
		    // 输出连线
		    List<SequenceFlow> outFlows = flowNode.getOutgoingFlows();
		    for (SequenceFlow sequenceFlow : outFlows) {
		            FlowElement sourceFlowElement = sequenceFlow.getSourceFlowElement();
		            if(sourceFlowElement.getName().equals("分编委会")) {
		            	
		            	
		            }
		            if(sourceFlowElement.getName().equals("总编委会成员")) {
		            	
		            	
		            }
		    }
		   // System.out.println("list更改后="+list);
		  Task task = taskService.createTaskQuery()
				  .taskId(taskId)
				  .singleResult();
		  
		  if(task == null) {
			  result = new Result(MsgConstants.RESUL_FAIL);
			  	result.setMsg("此流程不存在");
				res = new JsonResponse(result);
				return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		//res.setData(list);
		return res;
	}
	
	/**
     * 流程审批记录
	 * @throws Exception 
     */
	@RequestMapping(value = "/listnew",method=RequestMethod.POST)
	@ResponseBody
	  public JsonResponse getApproveHistory(String taskId,String noticeid) throws Exception {
		Result result = null;
		JsonResponse res = null;
		//查询审批公告(创建人,创建日期,标题)
	  Notice notice=flowableService.selectNotice(noticeid);
	  //获取当前人所在的总编委会
	  String nodenameover = flowableService.selectEbname(notice.getCreateid());
	  notice.setLeard(nodenameover);//放入编委会名称
	  List<HttpApprovalHistory> httpApprovalHistoryList = new ArrayList<HttpApprovalHistory>();
	   List<Comment> list =getProcessComments(taskId); 
	   HttpApprovalHistory httpApprovalHistory=null;
	   for (Comment comment : list) {
			   httpApprovalHistory = new HttpApprovalHistory();
			   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   httpApprovalHistory.setApprovalTime(dateFormat.format(comment.getTime()));
			   httpApprovalHistory
			   		.setApprovalUserName(userService.selectByPrimaryKey((comment.getUserId())).getUsername());
			   String[] str =comment.getFullMessage().split(":");//我前面添加建立的规则 
			  String agree = str[0];
			  String approvalOpinion=str[1];
			  String imgUrl=str[2];
			  String editorial=str[3];
			  //查询审批人所属编委会
			  //UserManager editorial=flowableService.deploynewFour(notice.getCreateid());
			  //String approvalOpinion = comment.getFullMessage().substring(2);
			  httpApprovalHistory.setAgree(agree);
			  httpApprovalHistory.setApprovalOpinion(approvalOpinion);
			  httpApprovalHistory.setImgurl(imgUrl);
			  httpApprovalHistory.setEditorial(editorial);
			  httpApprovalHistoryList.add(httpApprovalHistory); 
			  notice.setRecord(httpApprovalHistoryList);
		  } 
	   String username="";
	   //下一级待审核人信息
	  ListIterator<HistoricIdentityLink> listIterator = historyService.getHistoricIdentityLinksForTask(taskId).listIterator();
	  while(listIterator.hasNext()) {
		  HistoricIdentityLink nextname = listIterator.next();
		  username = username+","+nextname.getUserId();
	  }
	  //截取去除第一个逗号
	  String userid= username.substring(1);
	  String[] split = userid.split(",");
	 //获取当前任务节点：
	  Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
	  if(null!=task) {
	    ExecutionEntity ee = (ExecutionEntity) processEngine.getRuntimeService().createExecutionQuery()
	            .executionId(task.getExecutionId()).singleResult();
	    // 查询前审批节点
	    String crruentActivityId = ee.getActivityId();
	    BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(task.getProcessDefinitionId());
	    FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(crruentActivityId);
	    // 输出连线
	    List<SequenceFlow> outFlows = flowNode.getOutgoingFlows();
	    FlowElement sourceFlowElement=null;
	    for (SequenceFlow sequenceFlow : outFlows) {
	    	sourceFlowElement = sequenceFlow.getSourceFlowElement();
	    }
	  
	  //当前审批节点
	    System.out.println("sourceFlowElement.getName()="+sourceFlowElement.getName());
	    String name="";
        if ("总编委会成员".equals(sourceFlowElement.getName())) {
               //下一节点为总编委会,查询名称
        	String ebname=flowableService.selectEbname(split[0]);//编委会名
        		//用户表中查询该用户的姓名
        	
        	for (int i = 0; i < split.length; i++) {
        		name=name+","+flowableService.selectUsername(split[i]);
			}
            String nameSplit=name.substring(6);//人名
            httpApprovalHistory = new HttpApprovalHistory();
            httpApprovalHistory.setEditorial(ebname);
            httpApprovalHistory.setAgree("待审核");
            httpApprovalHistory.setApprovalUserName(nameSplit);
            httpApprovalHistoryList.add(httpApprovalHistory); 
            notice.setRecord(httpApprovalHistoryList);
        }else{
        	if(task!=null) {
        		 UserManager namenode=flowableService.deploynewFour(notice.getCreateid());
            	 String ebname=namenode.getEbname();
             	for (int i = 0; i < split.length; i++) {
             		name=name+","+flowableService.selectUsername(split[i]);
     			}
                String nameSplit=name.substring(6);//人名
                httpApprovalHistory = new HttpApprovalHistory();
                httpApprovalHistory.setEditorial(ebname);//设置编委会名称
                httpApprovalHistory.setAgree("待审核");//审核状态
                httpApprovalHistory.setApprovalUserName(nameSplit);//待审批人名字（多人）
                httpApprovalHistoryList.add(httpApprovalHistory); //将数据放入集合
                notice.setRecord(httpApprovalHistoryList);//添加进notice对象中
        	}
        	
        }
	  }
	        result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(notice);
			return res;
	}
	 

    /**
     * 获取历史审批记录comment
     * 
     * @param taskId
     * @return
     */
    public List<Comment> getProcessComments(String taskId) {
        List<Comment> historyCommnets = new ArrayList<>();
        // 1) 获取流程实例的ID
        HistoricTaskInstance task = processEngine.getHistoryService().createHistoricTaskInstanceQuery().taskId(taskId)
                .singleResult();
        String processInstanceId = task.getProcessInstanceId();
        // 2）通过流程实例查询所有的(用户任务类型)历史活动
        List<HistoricActivityInstance> hais = processEngine.getHistoryService().createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).activityType("userTask").list();
        // 3）查询每个历史任务的批注
        for (HistoricActivityInstance hai : hais) {
            String historytaskId = hai.getTaskId();
            List<org.flowable.engine.task.Comment> comments = processEngine.getTaskService().getTaskComments(historytaskId);
            // 4）如果当前任务有批注信息，添加到集合中
            if (comments != null && comments.size() > 0) {
                historyCommnets.addAll(comments);
            }
        }
        // 排序
        Collections.sort(historyCommnets, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return (int) -(o2.getTime().getTime() - o1.getTime().getTime());
            }
        });
        // 5）返回
        return historyCommnets;
    }
    
    
 // 以下为测试代码***********************************************

 	/**
 	 * 获取审批管理列表(个人任务)
 	 */
 	@RequestMapping(value = "/list")
 	@ResponseBody
 	public void list(String userId) {
 		String processDefinitionId = "myProcess:1:4";
 		Map<String,Object> map = new HashMap<String,Object>();
 		//获取bpmnModel对象
 		BpmnModel model1 = repositoryService.getBpmnModel(processDefinitionId);
 	//	BpmnModel model1 = new BpmnJsonConverter().convertToBpmnModel(modelNode);
 		org.flowable.bpmn.model.Process process = model1.getProcesses().get(0);
 		//获取全部的FlowElement（流元素）信息
 		Collection<FlowElement> flowElements = process.getFlowElements();
 		for (FlowElement flowElement : flowElements) {
 			//假设是开始节点
 			if(flowElement instanceof StartEvent){
 				StartEvent startEvent = (StartEvent)flowElement;
 				map.put("startEvent", startEvent);
 			}
 			//假设是任务节点
 			if(flowElement instanceof UserTask) {
 				UserTask userTask = (UserTask)flowElement;
 				List<String> candidateUsers = userTask.getCandidateUsers();
 				map.put("userTask", userTask);
 			}
 			//假设是结束节点
 			if(flowElement instanceof EndEvent) {
 				EndEvent endEvent = (EndEvent)flowElement;
 				map.put("endEvent", endEvent);
 			}
 			//假设是连接线
 			if(flowElement instanceof SequenceFlow) {
 				SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
 				map.put("sequenceFlow", sequenceFlow);
 			}
 		}
 	}


 	/**
 	 * 生成流程图
 	 *
 	 * @param processId 任务ID
 	 */
 	@RequestMapping(value = "/processDiagram")
 	public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
 		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
          // 流程走完的不显示图
 		if (pi == null) {
 			return;
 		}
 		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
 		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
 		String InstanceId = task.getProcessInstanceId();
 		List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(InstanceId).list();

 		// 得到正在执行的Activity的Id
 		List<String> activityIds = new ArrayList<>();
 		List<String> flows = new ArrayList<>();
 		for (Execution exe : executions) {
 			List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
 			activityIds.addAll(ids);
 		}

 		// 获取流程图
 		BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
 		ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
 		ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
 		InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows,
 				engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(),
 				engconf.getClassLoader(), 1.0, false);
 		OutputStream out = null;
 		byte[] buf = new byte[1024];
 		int legth = 0;
 		try {
 			out = httpServletResponse.getOutputStream();
 			while ((legth = in.read(buf)) != -1) {
 				out.write(buf, 0, legth);
 			}
 		} finally {
 			if (in != null) {
 				in.close();
 			}
 			if (out != null) {
 				out.close();
 			}
 		}
 	}
 	
 	
}
