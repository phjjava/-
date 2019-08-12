<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<nav class="breadcrumb">
<i class="Hui-iconfont">&#xe67f;</i> 
首页 <span class="c-gray en">&gt;</span> 
家族管理 
<span class="c-gray en">&gt;</span> 
家族管理  
</nav>
<div class="Hui-article">
	<article class="cl pd-20">
		<div class="cl pd-5 bg-1 bk-gray"><span class="l">
		<a class="btn btn-primary radius" href="javascript:;" onclick="family_edit('新增家族','','1')"><i class="Hui-iconfont">&#xe600;</i> 新增家族</a>&nbsp;&nbsp;
		</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>
		<!-- <button type="button" class="btn btn-success radius" id="" name="" onclick="searchs(1);"><i class="Hui-iconfont">&#xe665;</i> 搜索家族</button></span> -->
		</div>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-hover table-bg">
					<thead>
						<tr>
							<th scope="col" colspan="9">家族管理</th>
						</tr>
						<tr class="text-c">
							<th width="30">序号</th>
<!-- 							<th width="30">家族编号</th> -->
							<th width="30">名称</th>
							<th width="30">姓氏</th>
							<th width="50">用户权限</th>
							<th width="50">状态</th>
							<th width="50">操作</th>
						</tr>
					</thead>
					<tbody>
					  <c:forEach var="family" items="${pageModel.list}" varStatus="status">
						<tr class="text-c">
							<td>${status.index + 1 }</td>
<%-- 							<td>${family.familycode }</td> --%>
							<td>${family.familyname }</td>
							<td>${family.surname }</td>
							<td>${family.versionname }</td>
							<td>
								<c:if test="${family.status == 1 }">
								    <span class="label label-default radius">停用</span>
								</c:if>
								<c:if test="${family.status == 0}">
									<span class="label label-success radius">启用</span>
								</c:if>
							</td>
							<td class="f-14">
							<a href="javascript:;" onclick="family_edit('编辑成员','${family.familyid}','1')">编辑</a>&nbsp;|
							<c:if test="${family.status == 0 }">
								<a  onclick="family_stop('${family.familyid}')" href="javascript:;" >停用</a>
							</c:if>
							<c:if test="${family.status == 1}">
								<a href="javascript:;" onclick="family_start('${family.familyid}','${family.status}')">启用</a>
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
	/*家族新增或编辑*/
	function family_edit(title,familyid,id,w,h){
		var w = 900;
		var h = 500;
		var url = basePath + 'system/family/edit?familyid='+familyid+'&curSec='+Math.random();
		layer_show(title,url,w,h);
	}
	function family_stop(id){
		layer.confirm('确认要停用吗？',function(index){
			changeStatus(id,1);
		});
	}
	function family_start(id){
		layer.confirm('确认要启用吗？',function(index){
			changeStatus(id,0);
			
		});
	}
	//停用家族
	function changeStatus(id,statusflag){
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:{"familyid":id,"status":statusflag},
			url : '<%=basePath%>system/family/changeStatus?curSec='+Math.random(),
			success:function(data,status){
				searchs();
				if(statusflag == 1){
					layer.msg('已停用!',{icon: 5,time:1000});
				}else{
					layer.msg('已启用!', {icon: 6,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
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
		$.ajax({
			type : 'post',
			dataType : 'text',
			data : $('#pageForm').serialize(),
			url : basePath + 'system/family/list?curSec=' + Math.random(),
			//async : false,
			success : function(data) {
				appContent.html(data); //将链接中的地址替换
			},
			error : function() {
	
			}
		});
	}
</script>