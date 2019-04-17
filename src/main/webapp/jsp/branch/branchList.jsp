<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath%>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<nav class="breadcrumb"><i class="Hui-iconfont"></i> 首页
	<span class="c-999 en">&gt;</span>
	<span class="c-666">用户管理</span> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">分支管理</span> 
</nav>
<div class="Hui-article">
	<article class="cl pd-30">
		<div class="cl pd-5 bg-1 bk-gray">
			<span class="l">
				<a class="btn btn-primary radius" href="javascript:;" onclick="branch_add('新增分支','<%=basePath%>/jsp/branch/branch.jsp','','700','550')"><i class="Hui-iconfont">&#xe600;</i> 新增分支
				</a>
			</span>
			<div class="formControls col-xs-4 col-sm-4" style="margin-left:40px;">
			    <input type="text" class="input-text"  placeholder="分支名称" id="branchname" value="${branch.branchname }">
			</div>
			<div class="formControls col-xs-5 col-sm-5">
				<button name="" id="" class="btn btn-success radius" type="button" onclick="searchs(1);"><i class="Hui-iconfont">&#xe665;</i> 搜索分支</button>
			</div>
		</div>
		<div class="mt-10">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-hover table-bg">
					<thead>
						
						<tr class="text-c">
							<th width="30">序号</th>
							<th width="200">名称</th>
							<th width="50">成员总数</th>
							<th width="200">地区</th>
							<th width="70">起始人</th>
							<th width="70">状态</th>
							<th width="80">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="branch" items="${pageModel.list }" varStatus="status">
							<tr class="text-c">
								<td>${status.index + 1 }</td>
								<td>${branch.branchname }</td>
								<td>${branch.usercount}</td>
								<td>${branch.area}${branch.cityname }${branch.xname }${branch.address} </td>
								<td>${branch.beginname}</td>
								<td class="td-status">
									<c:if test="${branch.status == 1 }">
									    <span class="label label-default radius">停用</span>
									</c:if>
									<c:if test="${branch.status == 0}">
										<span class="label label-success radius">启用</span>
									</c:if>
								</td>
								<td class="f-14">
									<a href="javascript:;" onclick="branch_edit('编辑分支','${branch.branchid}','','700','700')">编辑</a>&nbsp;|
									<c:if test="${branch.status == 0 }">
										<a  onclick="branch_stop('${branch.branchid}')" href="javascript:;" >停用</a>
									</c:if>
									<c:if test="${branch.status == 1}">
										<a  onclick="branch_start('${branch.branchid}')" href="javascript:;" >启用</a>
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
// initBranch();

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
	var branchname = $("#branchname").val();
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&branchname="+branchname,
		url : basePath + 'branch/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			
			appContent.html(data); //将链接中的地址替换
			
		},
		error : function() {

		}
	});
}

/*分支添加*/
function branch_add(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*分支编辑*/
function branch_edit(title,branchid,id,w,h){
	var url = basePath + 'branch/get?branchid='+branchid+'&curSec='+Math.random();
	
	layer_show(title,url,w,h);
} 
/*Banner-停用*/
function branch_stop(id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(id,1);
	});
}
function branch_start(id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(id,0);
		
	});
}

//停用Banner
function changeStatus(id,statusflag){
	$.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:"branchid="+id+"&status="+statusflag,
		url : '<%=basePath%>branch/changeStatus?curSec='+Math.random(),
		success:function(data,status){
			if(status == 'success' && data == '1'){
				searchs();
				if(statusflag == 1){
					layer.msg('已停用!',{icon: 5,time:1000});
				}else{
					layer.msg('已启用!', {icon: 6,time:1000});
				}
				
			}else{
				alert("停用失败！");
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
}
</script>
<!-- 选择分支_js -->
<script async type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script>
<!-- async 异步加载属性 -->
<script async type="text/javascript" src="<%=basePath%>lib/branchInterface1.js"></script> 