<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath%>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>

<nav class="breadcrumb"><i class="Hui-iconfont"></i> 首页 <span class="c-999 en">&gt;</span><span class="c-666">权限管理</span><span class="c-999 en">&gt;</span><span class="c-666">职务管理</span> 
</nav>
<div class="Hui-article">
	<article class="cl pd-30">
		<div class="cl pd-5 bg-1 bk-gray">
		  <span class="l">
		    <a href="javascript:;" onclick="post_add('新增职务','<%=basePath %>post/get','800','400')" class="btn btn-primary radius">
		      <i class="Hui-iconfont">&#xe600;</i> 新增职务
		    </a>
		  </span>  
	    </div>
		<div class="mt-10">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-bg" style="margin-top:5px;">
					<thead>
						<tr class="text-c">
							<th width="25">序号</th>
							<th width="100">职务</th>
							<th width="50">编委会</th>
							<th width="100">描述</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody>
					   <c:forEach var="post" items="${pageModel.list}" varStatus="status">
						<tr class="text-c">
							<td>${status.index + 1 }</td>
							<td>${post.name}</td>
							<td>
								
								<c:if test="${post.type == 1  && post.ismanager == 1}">
								    <span>总编会主任<span>	
								</c:if>
								<c:if test="${post.type == 1  && post.ismanager == 0}">
								    <span>总编会成员<span>	
								</c:if>
								<c:if test="${post.type == 0  && post.ismanager == 1 }">
								    <span>分编会主任<span>	
								</c:if>
								<c:if test="${post.type == 0  && post.ismanager == 0}">
								    <span>分编会成员<span>	
								</c:if>
							</td>
							<td>${post.roledesc}</td>
							<td class="td-manage">
								<a href="javascript:;" onclick="post_edit('编辑编委会','${post.id}','800','400')">编辑</a>&nbsp;|&nbsp;
							  <a href="javascript:;" onclick="post_del('${post.id}')">删除</a>
								
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
		url : basePath + 'post/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}
</script>
<script type="text/javascript">
/*管理员-增加*/
function post_add(title,id,w,h){
	var url = basePath + 'post/get?id='+id+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
/*管理员-编辑*/
function post_edit(title,id,w,h){
	var url = basePath + 'post/get?id='+id+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
/*管理员-删除*/
function post_del(id){
	layer.confirm('删除职务将会删除此职务的管理员，确认删除吗？',function(index){
		$.ajax({
			type: 'post',
			data: "id="+id,
			url: basePath + 'post/del?&curSec='+Math.random(),
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
function editorial_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
		$(obj).remove();
		layer.msg('已停用!',{icon: 5,time:1000});
	});
}

/*管理员-启用*/
function admin_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
		$(obj).remove();
		layer.msg('已启用!', {icon: 6,time:1000});
	});
}
</script>