<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理 <span class="c-gray en">&gt;</span> 成员申请审核 <!-- <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a> --></nav>
	<div class="Hui-article">
	 <article class="cl pd-30">
		<div class="cl pd-5 bg-1 bk-gray">
			<div class="formControls col-xs-3 col-sm-3" style="padding:0">
				<select class="select" style="width:48%;height:30px;" name="status" id="status" value="${user.status }">
					<option value="">全部</option>
					<option value="1">审核中</option>
					<option value="2">审核未通过</option>
					<option value="3">停用</option>
				</select>
			    <input type="text" class="input-text" style="width:48%" placeholder="姓名" id="username" value="${user.username }">
			</div>
			<div class="formControls col-xs-8 col-sm-8">
				<button name="" id="" class="btn btn-success radius" type="button" onclick="searchs(1);"><i class="Hui-iconfont">&#xe665;</i> 搜索成员</button>
			</div>
		</div>
		<div class="mt-10">
		 <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
			<table class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
					    <th width="30">序号</th>
						<th width="30">姓名</th>
				        <th width="30">性别</th>
		                <th width="50">生辰</th>
						<th width="30">排行</th>
						<th width="40">世系</th>
						<th width="100">隶属分支</th>
						<th width="30">状态</th>
						<th width="80">操作</th>
					</tr>
				</thead>
				<tbody>
					  <c:forEach var="user" items="${pageModel.list}" varStatus="status">
						   <tr class="text-c">
						        <td>${status.index + 1 }</td>
								<td>${user.username }</td>
							    <td>
								    <c:if test="${user.sex == 1}">
								        男
								    </c:if>
								    <c:if test="${user.sex == 0}">
								         女
								    </c:if>
							    </td>
								<td><fmt:formatDate value="${user.searchBirthday }" pattern="yyyy-MM-dd"/></td>
								<td>${user.brotherpos }</td>
								<td>${user.genlevel }</td>
								<td>${user.branchname }</td>
								<c:if test="${user.status == 1}">
							     <td></td>
							    </c:if>
							    <c:if test="${user.status == 2}">
							     <td><span class="label label-default radius">已拒绝</span></td>
							    </c:if>
							    <c:if test="${user.status == null}">
							     <td></td>
							    </c:if>
							    <c:if test="${user.status == 3}">
							    <td><span class="label label-default radius">已停用</span></td>
							    </c:if>
								<td class="f-14"><a title="查看详情" href="javascript:;" onclick="member_show('成员详情','${user.userid}','1')" >查看详情</a>&nbsp;|
								<a title="启用" href="javascript:;" onclick="if(confirm('确定通过当前用户吗？')){changeStatus('${user.userid}','0');}">通过</a>
								<c:if test="${user.status != 2}">
								&nbsp;|
								<a title="拒绝" href="javascript:;" onclick="if(confirm('确定拒绝当前用户吗？')){changeStatus('${user.userid}','2');}">拒绝</a></td>
								</c:if>
				           </tr>
			          </c:forEach>
				</tbody>
			</table>
			<jsp:include page="../common/basepage.jsp"></jsp:include>
		  </div>
		</div>
	</article>
</div>
<script>
$(function(){
	var status = '${user.status}';
	$("#status").val(status);
});

function member_show(title,userid,id,w,h){
	var w = 1300;
	var url = basePath + 'user/edit?userid='+userid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
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
	
	var status = $("#status").val();
	var username = $("#username").val();
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&status="+status+"&username="+username,
		url : basePath + 'user/listToReview?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}
//通过或停用用户
function changeStatus(userid,status){
	$.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:"userid="+userid+"&status="+status,
		url : '<%=basePath%>user/changeStatusOfReview?curSec='+Math.random(),
		success:function(data,status){
			if(status == 'success' && data == '1'){
				//alert("操作成功");
				layer.alert("操作成功", {icon: 1});
				searchs(1);
			}else{
				//alert("操作失败");
				layer.alert("操作失败", {icon: 7});
				searchs(1);
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
}
</script>
