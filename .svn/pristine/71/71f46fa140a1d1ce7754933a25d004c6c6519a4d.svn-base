<%@page import="com.jp.common.CurrentUserContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String currentUserid=CurrentUserContext.getCurrentUserId();
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui_select.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />
<script type="text/javascript">
	/*成员添加
	*/
	function member_add(title,url,w,h){
		layer_show(title,url,w,h);
	}
	/*成员编辑
	*/
	function member_edit(title,userid,id,w,h){
		var w = 1300;
		var url = basePath + 'user/edit?userid='+userid+'&curSec='+Math.random();
		layer_show(title,url,w,h);
	}
	/*给单个成员增加配偶
	*/
	function member_addmates(title,userid,id,w,h){
		var w = 800;
		var h = 600;
		var url = basePath + 'user/addmate?userid='+userid+'&curSec='+Math.random();
		layer_show(title,url,w,h);
	}
	/*用户状态-停用*/
	function user_stop(id){
		layer.confirm('确认要停用吗？',function(index){
			changeStatus(id,3);
		});
	}
	/*用户状态-恢复停用*/
	function user_start(id){
		layer.confirm('确认要恢复吗？',function(index){
			changeStatus(id,0);
		});
	}	
	//更改用户状态
	function changeStatus(id,change_status){
			$.ajax({
				type:'post',
				dataType:'text',
				async: true,
				data:"userid="+id+"&status="+change_status,
				url : '<%=basePath%>user/changeStatus?curSec='+Math.random(),
				success:function(data,status){
					if(status == 'success' && data == '1'){
						searchs(1);
						if(change_status == 0){
							layer.msg('已恢复!', {icon: 6,time:1500});
						}else{
							layer.msg('已停用!',{icon: 5,time:1500});
						}						
					}else{
						layer.msg('状态更改失败!', {icon: 6,time:1000});
						searchs(1);
					}
				},
				error:function(e) {
					console.log(e);
				}
			});
	}
	//导入用户
	function importUser(title,userid,id,w,h){
		var w = 500;
		var h = 300;
		var url = basePath + 'jsp/user/importUser.jsp';
		layer_show(title,url,w,h);
	}
	//导入配偶
	function importUsermates(title,userid,id,w,h){
		var w = 500;
		var h = 300;
		var url = basePath + 'jsp/user/importUsermates.jsp';
		layer_show(title,url,w,h);
	}
</script>
<nav class="breadcrumb">
<i class="Hui-iconfont">&#xe67f;</i> 
首页 <span class="c-gray en">&gt;</span> 
用户管理 
<span class="c-gray en">&gt;</span> 
成员管理 
</nav>
<div class="Hui-article">
	<article class="cl pd-30">
		<div class="text-c" style=" text-align:left">
		    
			<span class="col-xs-6" style="padding:0;">
				<input type="text" class="input-text" style="width:20%" placeholder="姓名" id="username" value="${user.username }">
			    <input type="text" class="input-text" style="width:20%" placeholder="世系" id="genlevel" value="${user.genlevel }">
				<span class="select-box" style="width:25%">
					<select class="select" size="1" name="status" id="status" value="${user.status }">
					    <option value="">选择状态</option>
						<option value="0">正常</option>
						<option value="3">停用</option>
					</select>
				</span>
				<span class="select-box" style="width:30%">
					<select class="select" size="1" name="isdirect" id="isdirect" value="${user.isdirect }">
						<option value="">选择亲属关系</option>
						<option value="1">直系</option>
						<option value="0">非直系</option>
					</select>
				</span>
			</span>
			<span class="formControls col-xs-4" style=" text-align:center;padding:0;">
				<select id="branchid" class="branch-select select" data-val="${user.branchid }">
				</select>
	        </span>
	        <span class="col-xs-2">
			     <button type="button" class="btn btn-success radius" onclick="searchs(1);"><i class="Hui-iconfont">&#xe665;</i> 搜索成员</button>	
			</span>		
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20" style="margin-top:50px;">
		    <span class="l">
		        <a class="btn btn-primary radius" href="javascript:;" onclick="member_edit('新增成员','','1')"><i class="Hui-iconfont">&#xe600;</i> 新增成员</a>&nbsp;&nbsp;
	            <a href="javascript:;" onclick="importUser('导入用户','','1')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe645;</i> 导入用户</a>&nbsp;&nbsp;
	            <a href="javascript:;" onclick="importUsermates('导入配偶','','1')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe645;</i> 导入配偶</a>
		</span>
		</div>
		<div class="mt-10">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-hover table-bg">
					<thead>
						<tr class="text-c">
							<th width="30">序号</th>
							<th width="30">增加配偶</th>
							<th width="30">姓名</th>
							<th width="30">性别</th>
							<th width="50">生辰</th>
							<th width="30">排行</th>
							<th width="40">世系</th>
							<th width="110">隶属分支</th>
							<th width="40">状态</th>
							<th width="50">操作</th>
						</tr>
					</thead>
					<tbody>
					  <c:forEach var="user" items="${pageModel.list}" varStatus="status">
						<tr class="text-c">
							<td>${status.index + 1 }</td>
							<td>
							 <c:if test="${user.isdirect == 1 }">
							  <a href="javascript:;" onclick="member_addmates('增加配偶','${user.userid}','1','${user.sex}')">增加配偶</a>
							 </c:if> 
							</td>
							<td>${user.username }</td>
							<td>
								<c:if test="${user.sex == 0}">女</c:if>
								<c:if test="${user.sex == 1}">男</c:if>
							</td>
							<td><fmt:formatDate value="${user.searchBirthday}" pattern="yyyy-MM-dd"/></td>
							<td>${user.brotherpos }</td>
							<td>${user.genlevel }</td>
							<td>${user.branchname }</td>
							<td class="td-status">
								<c:if test="${user.status == 0 }">
								    <span class="label label-success radius">正常</span>
								</c:if>
								<c:if test="${user.status == 3}">
									<span class="label label-default radius">停用</span>
								</c:if>
							</td>	
								
								<td class="f-14">
								<c:if test="${user.ismanager == 0 }">		
								  <c:if test="${user.status == 3 }">
								  	<a  onClick="user_start('${user.userid}')" href="javascript:;" >恢复</a>				    
								  </c:if>
								  <c:if test="${user.status == 0}">
									<a  onClick="user_stop('${user.userid}')" href="javascript:;" >停用</a>&nbsp;|
									<a href="javascript:;" onclick="member_edit('编辑成员','${user.userid}','1')" >编辑</a>
								  </c:if>							
								</c:if>		
								<c:if test="${user.ismanager == 1 }">		
								  <a href="javascript:;" onclick="member_edit('编辑成员','${user.userid}','1')" >编辑</a>
								  <!-- <span style="color:red">家族管理员<span>		 -->				
								</c:if>		
								<%-- <a href="javascript:;" onclick="if(confirm('确定停用当前用户吗？')){changeStatus('${user.userid}');}">停用</a>&nbsp;|	 --%>						
								</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<jsp:include page="../common/basepage.jsp"></jsp:include>
			</div>
		</div>
	</article>
</div>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>

<script type="text/javascript">
$(function(){
	//初始化分支
	initBranch();
	var isdirect = '${user.isdirect}';
	$("#isdirect").val(isdirect);
	$('.my-select').chosen({
    	search_contains: true,
      	max_selected_options: 1,
      	no_results_text: "没有找到",
    });
	var status = '${user.status}';
	$("#status").val(status);
	
});
//查询方法
function searchs(pageNoTemp) {
	var sortType = $("#sortType").val();
	var sortOrder = $("#sortOrder").val();
	var pageNo=$("#pageNo").val();
	if(pageNoTemp != undefined){
		$("#pageNo").val(pageNoTemp);
	}
	var pagesize = $('#pagesize option:selected').text();
	if (pagesize == 'All') {
		pagesize = 100000;
	}
	var branchid = $("#branchid").val();
	var username = $("#username").val();
	var genlevel = $("#genlevel").val();
	var isdirect = $("#isdirect").val();
	//var searchBirthplace = $("#searchBirthplace").val();
	var status = $("#status").val();
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&branchid="+branchid+"&username="+username+"&genlevel="+genlevel+"&status="+status+"&isdirect="+isdirect,
		url : basePath + 'user/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {
			
		}
	});
}


</script>