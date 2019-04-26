<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath %>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<nav class="breadcrumb">
	<i class="Hui-iconfont"></i>
	<a href="/" class="maincolor">首页</a> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">系统管理</span> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">版本特权管理</span> 
</nav>
<div class="Hui-article">
	<article class="cl pd-20">
		<div class="cl pd-5 bg-1 bk-gray">
			<span class="l">
				<a class="btn btn-primary radius" href="javascript:;" onclick="version_add('新增版本特权','<%=basePath %>system/sysversionauth/get','800')">
					<i class="Hui-iconfont">&#xe600;</i> 新增版本特权
				</a>
			</span>
		</div>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-hover table-bg">
					<thead>
						<tr>
							<th scope="col" colspan="5">版本特权管理</th>
						</tr>
						<tr class="text-c">
							<th width="30">序号</th>
							<th width="50">版本名称</th>
							<th width="50">版本特权code</th>
							<th width="50">版本特权名称</th>
							<th width="50">版本特权值</th>
							<th width="50">排序</th>
							<th width="50">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="versionauth" items="${pageModel.list }" varStatus="status">
							<tr class="text-c">
								<td>${status.index + 1 }</td>
								<td>${versionauth.versionname }</td>
								<td>${versionauth.privilegecode}</td>
								<td>${versionauth.privilegename}</td>
								<td>${versionauth.privilegevalue}</td>
								<td>${versionauth.sort}</td>
								<td class="f-14">
									<a href="javascript:;" onclick="version_edit('编辑版本','${versionauth.id}','1')">编辑</a>&nbsp;|
									<a href="javascript:;" onclick="version_del('${versionauth.id}')">删除</a>
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
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize(),
		url : basePath + 'system/sysversionauth/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}

/*版本添加*/
function version_add(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*版本编辑*/
function version_edit(title,versionid,id,w,h){
	var url = basePath + 'system/sysversion/get?versionid='+versionid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
} 
/*版本删除*/
function version_del(versionid){
	layer.confirm('确认删除吗？',function(index){
		$.ajax({
			type : 'post',
			dataType : 'text',
			data : {"versionid":versionid},
			url : basePath + 'system/sysversion/delete?curSec=' + Math.random(),
			//async : false,
			success : function(data,status) {
				if(status == 'success' && data == '1'){
					searchs();
					layer.msg('删除成功!', {icon: 6,time:1000});
				}else{
					layer.msg('删除失败!',{icon:1,time:1000});
				}
			},
			error : function() {

			}
		});
	});
}
</script>