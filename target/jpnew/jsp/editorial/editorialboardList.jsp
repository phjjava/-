<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath%>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>

<nav class="breadcrumb"><i class="Hui-iconfont"></i> 首页 <span class="c-999 en">&gt;</span><span class="c-666">权限管理</span><span class="c-999 en">&gt;</span><span class="c-666">编委会管理</span> 
</nav>
<div class="Hui-article">
	<article class="cl pd-30">
		<div class="cl pd-5 bg-1 bk-gray">
		  <span class="l">
		    <a href="javascript:;" onclick="editorial_add('新增编委会','<%=basePath %>editorial/get','800','400')" class="btn btn-primary radius">
		      <i class="Hui-iconfont">&#xe600;</i> 新增编委会
		    </a>
		  </span>  
	    </div>
		<div class="mt-10">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-bg" style="margin-top:5px;">
					<thead>
						<tr class="text-c">
							<th width="25">序号</th>
							<th width="250">编委会</th>
							<th width="50">描述</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody>
					   <c:forEach var="editorial" items="${pageModel.list}" varStatus="status">
						<tr class="text-c">
							<td>${status.index + 1 }</td>
							<td>${editorial.name}</td>
							<td>${editorial.eddesc }</td>
							
							<td class="td-manage">
								<c:if test="${editorial.type == 1 }">
								    <span style="color:red">总编会不可编辑<span>	
								</c:if>
								<c:if test="${editorial.type == 0}">
									<a href="javascript:;" onclick="editorial_edit('编辑编委会','${editorial.id}','800','400')">编辑</a>&nbsp;|&nbsp;
							  <a href="javascript:;" onclick="editorial_del('${editorial.id}')">删除</a>
								</c:if>
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
		url : basePath + 'editorial/list?curSec=' + Math.random(),
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
function editorial_add(title,id,w,h){
	var url = basePath + 'editorial/get?id='+id+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
/*管理员-编辑*/
function editorial_edit(title,id,w,h){
	var url = basePath + 'editorial/get?id='+id+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
/*管理员-删除*/
function editorial_del(id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'post',
			data: "id="+id,
			url: basePath + 'editorial/del?&curSec='+Math.random(),
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