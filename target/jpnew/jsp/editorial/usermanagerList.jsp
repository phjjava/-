<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath %>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<nav class="breadcrumb">
   <i class="Hui-iconfont">&#xe67f;</i> 首页 
   <span class="c-gray en">&gt;</span> 权限管理 
   <span class="c-gray en">&gt;</span> 管理员管理
</nav>
<div class="Hui-article">
	<article class="cl pd-30">
		<div class="cl pd-5 bg-1 bk-gray">
			<span class="l"> 
			  <a href="javascript:;" onclick="userrolebranch_add('添加管理员','<%=basePath%>/userManager/get','','800','450')" class="btn btn-primary radius">
			    <i class="Hui-iconfont">&#xe600;</i> 添加管理员
			  </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
			<span class="inline">
				<input type="text"  id="username" class="input-text" style="width:200px;margin-left:20px;padding-left:10px;" value="${manager.username}" placeholder="请输入姓名"/>
				<!-- <select name="" class="select" style="width:150px;height:30px;">
					<option value="0">角色</option>
					<option value="1">总编委</option>
					<option value="2">分编委</option>
				</select>
				<select name="" class="select" style="width:150px;height:30px;">
					<option value="0">全部状态</option>
					<option value="1">已启用</option>
					<option value="2">未启用</option>
				</select> -->
			</span>
			<!-- <button name="" id="" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 查询<tton> -->
			<button type="button" class="btn btn-success radius" id="" name="" onclick="searchs(1);" style="margin-left:15px;"><i class="Hui-iconfont">&#xe665;</i> 查询</button></span>
		</div>
		<div class="mt-10">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
			     <table class="table table-border table-bordered table-bg" style="margin-top:5px;">
					<thead>
						<tr class="text-c">
							<th width="40">序号</th>
							<th width="150">管理员姓名</th>
							<th width="60">角色</th>
							<th width="80">职务</th>
							<th width="150">编委会</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="manager" items="${pageModel.list }" varStatus="status">
							<tr class="text-c">
								<td>${status.index + 1}</td>
								<td>${manager.username}</td>
								<td>
									<c:choose>
									<c:when test="${manager.ismanager == 1}">
					
					
										<span>主任<span>		
									</c:when>
									<c:otherwise>
										<span>成员<span>		
									</c:otherwise>
									</c:choose>
								</td>
								<td>${manager.postname}</td>
								<td>${manager.ebname}</td>
								
								<td class="td-manage" style="">
								    
									
<%-- 									<c:if test="${user.deleteflag == 1}">
										 <a style="text-decoration:none;color:#5a98de" onClick="userrolebranch_start('${user.userid}')" href="javascript:;" title="停用">启用</a>
									</c:if> --%>
									<c:choose>
									<c:when test="${manager.ismanager == 1 && manager.ebtype == 1}">
										<span style="color:red">家族管理员<span>		
									</c:when>
									<c:otherwise>
										<a title="编辑" href="javascript:;" onclick="userrolebranch_edit('编辑成员','${manager.id}','','800','450')" onclick="userrolebranch_edit('管理员编辑','manager/usermanager.jsp','1')" class="ml-5" style="text-decoration:none;color:#5a98de">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;
								
										 <a style="text-decoration:none;color:#5a98de" onClick="userrolebranch_delete('${manager.id}')" href="javascript:;" title="停用">删除</a>
									</c:otherwise>
								    </c:choose>
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
<script type="text/javascript">
/*管理员-增加*/
function userrolebranch_add(title,url,id,w,h){
	layer_show(title,url,w,h);  
}
/* /*管理员-编辑*/

 
/*编辑
*/
function userrolebranch_edit(title,id,pid,w,h){
	//console.log(id)
	var url = basePath + 'userManager/get?id='+id+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
/*管理员-删除*/
function userrolebranch_delete(id){
	
	layer.confirm('确认要删除吗？删除后如需恢复请重新添加',function(index){
		$.ajax({
			type: 'POST',
			data:"id="+id,
			url: basePath + 'userManager/del?curSec=' + Math.random(),
			dataType: 'json',
			success: function(data){
				if(data.data==1){
					layer.msg('已删除!',{icon:1,time:2000});
					searchs();
				}else{
					layer.msg('删除失败请联系管理员!',{icon:7,time:1000});
				}
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}


/*管理员-停用*/
function admin_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none;color:#5a98de">是否启用</a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已停用</span>');
		$(obj).remove();
		layer.msg('已停用!',{icon: 5,time:1000});
	});
}

/*管理员-启用*/
function userrolebranch_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none;color:#5a98de">是否停用</a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
		$(obj).remove();
		layer.msg('已启用!', {icon: 6,time:1000});
	});
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
	var username = $("#username").val();
	
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&username="+username,
		url : basePath + 'userManager/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {
		}
	});
}
</script>
<!-- 选择分支_js -->
<script async type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script>
<!-- async 异步加载属性 -->
<script async type="text/javascript" src="<%=basePath%>lib/branchInterface3.js"></script> 