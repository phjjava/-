<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath%>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<nav class="breadcrumb"><i class="Hui-iconfont"></i> <a href="/" class="maincolor">首页</a> 
		<span class="c-999 en">&gt;</span>
		<span class="c-666">系统管理</span> 
		<span class="c-999 en">&gt;</span>
		<span class="c-666">菜单管理</span> 
	</nav>
<div class="Hui-article">
	<article class="cl pd-20">
		<div class="cl pd-5 bg-1 bk-gray">
			<span class="l">
			<a class="btn btn-primary radius" href="javascript:;" onclick="sysFunction_add('新增菜单','<%=basePath%>jsp/system/function.jsp','800')">
				<i class="Hui-iconfont">&#xe600;</i> 新增菜单
			</a>
			</span>
		</div>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-hover table-bg">
					<thead>
						<tr>
							<th scope="col" colspan="5">菜单管理</th>
						</tr>
						<tr class="text-c">
							<th width="30">序号</th>
							<th width="200">名称</th>
							<th width="50">状态</th>
							<th width="80">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sysFunction" items="${pageModel.list }" varStatus="status">
							<tr class="text-c">
								<td>${status.index + 1 }</td>
								<td>${sysFunction.functionname }</td>
						
								<td><c:if test="${sysFunction.status == 1 }">
								    <span class="label label-default radius">停用</span>
								</c:if>
								<c:if test="${sysFunction.status == 0 }">
									<span class="label label-success radius">启用</span>
								</c:if></td>
								<td class="f-14">
									<a href="javascript:;" onclick="searchChildList('${sysFunction.functionid}')">管理子菜单</a>&nbsp;|
									<a href="javascript:;" onclick="sysFunction_edit('编辑','${sysFunction.functionid}','1')">编辑</a>&nbsp;|
									<a href="javascript:;" onclick="sysFunction_del('${sysFunction.functionid}')">删除</a>
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
		url : basePath + 'system/sysfunction/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}

function searchChildList(parentid){
	
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : {"parentid":parentid},
		url : basePath + 'system/sysfunction/childlist?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
	
	
}

/*菜单添加*/
function sysFunction_add(title,url,id,w,h){
	layer_show(title,url,w,h);
}

/*菜单编辑*/
function sysFunction_edit(title,functionid,id,w,h){
	var url = basePath + 'system/sysfunction/get?functionid='+functionid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
} 
/*菜单删除*/
function sysFunction_del(functionid){
	layer.confirm('确认删除吗？',function(index){
		$.ajax({
			type : 'post',
			dataType : 'text',
			data : {"functionid":functionid},
			url : basePath + 'system/sysfunction/delete?curSec=' + Math.random(),
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