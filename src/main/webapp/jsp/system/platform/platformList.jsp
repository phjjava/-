<%@page import="com.jp.common.CurrentUserContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui_select.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />
<script type="text/javascript">
	/*成员添加
	*/
	function member_add(title,url,w,h){
		layer_show(title,url,w,h);
	}
	/*给单个成员增加配偶
	*/
	function addVersion(title,userid,id,w,h){
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
	/*删除版本*/
	function deleteVersionConfirm(id){
		layer.confirm('确认要删除吗？',function(index){
			deleteVersion(id);
		});
	}	
	//删除版本
	function deleteVersion(id){
			$.ajax({
				type:'post',
				dataType:'text',
				async: true,
				data:"id="+id,
				url : '<%=basePath%>system/platform/deleteVersion?curSec='+Math.random(),
				success:function(data,status){
					if(data == '1'){
						searchs(1);
						layer.msg('已删除!',{icon: 6,time:1500});
					}else{
						layer.msg('删除失败!', {icon: 5,time:1000});
					}
				},
				error:function(e) {
					console.log(e);
				}
			});
	}
	//增加版本
	function add(title,fileType){
		var w = 600;
		var h = 400;
		var url = basePath + 'jsp/system/platform/platform.jsp?fileType='+fileType;
		layer_show(title,url,w,h);
	}
	/*版本编辑
	*/
	function version_edit(id){
		var w = 600;
		var h = 400;
		var url = basePath + 'system/platform/edit?id='+id+'&curSec='+Math.random();
		layer_show("版本编辑",url,w,h);
	}
	//开启或关闭版本
	function operateVersionConfirm(id,fileType,isOpen){
		var isUse = null;
		if(isOpen == 0){
			isUse = "开启";
		}else{
			isUse = "关闭";
		}
		layer.confirm('确认'+isUse+'吗？',function(index){
			operateVersion(id,fileType);
		});
	}	
	//开启或关闭版本
	function operateVersion(id,fileType){
			$.ajax({
				type:'post',
				dataType:'text',
				async: true,
				data:"id="+id,
				url : '<%=basePath%>system/platform/isOpen?curSec='+Math.random(),
				success:function(data,status){
					if(data == '1'){
						searchs(1);
						layer.msg('操作成功!',{icon: 6,time:1500});
					}else{
						layer.msg('操作失败!', {icon: 5,time:1000});
					}
				},
				error:function(e) {
					console.log(e);
				}
			});
	}
</script>
<nav class="breadcrumb">
<i class="Hui-iconfont">&#xe67f;</i> 
首页 <span class="c-gray en">&gt;</span> 
升级管理 
<span class="c-gray en">&gt;</span> 
升级管理 
</nav>
<div class="Hui-article">
	<article class="cl pd-30">
		<div class="text-c" style=" text-align:left">
		    
			<span class="col-xs-6" style="padding:0;">
				<input type="text" class="input-text" style="width:20%" placeholder="版本" id="versionName" value="${versionName }">
			</span>
	        <span class="col-xs-2">
			     <button type="button" class="btn btn-success radius" onclick="searchs(1);"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>	
			</span>		
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20" style="margin-top:50px;">
		    <span class="l">
		        <a class="btn btn-primary radius" href="javascript:;" onclick="add('新增版本','${fileType}')"><i class="Hui-iconfont">&#xe600;</i> 新增版本</a>&nbsp;&nbsp;
		</span>
		</div>
		<div class="mt-10">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-hover table-bg">
					<thead>
						<tr class="text-c">
							<th width="30">序号</th>
							<th width="30">文件名称</th>
							<th width="30">版本名称</th>
							<th width="30">版本号</th>
							<th width="45">最小更新版本号</th>
							<th width="45">升级描述</th>
							<th width="30">版本类别</th>
							<th width="30">下载路径</th>
							<th width="30">上传时间</th>
							<th width="15">是否开启</th>
							<th width="30">操作</th>
						</tr>
					</thead>
					<tbody>
					  <c:forEach var="platform" items="${pageModel.list}" varStatus="status">
						<tr class="text-c">
							<td>${status.index + 1 }</td>
							<td>${platform.fileRealName }</td>
							<td>${platform.versionName }</td>
							<td>${platform.versionNo }</td>
							<td>${platform.minUpdateVersion }</td>
							<td>${platform.upgradeDes }</td>
							<td>
							 <c:if test="${platform.fileType == 1}">ios版本</c:if>
							 <c:if test="${platform.fileType == 2}">安卓版本</c:if>
							</td>
							<td>${platform.downloadUrl }</td>
							<td>${platform.uploadTime }</td>
							<td>
							 <c:if test="${platform.isUsed == 0}">已关闭</c:if>
							 <c:if test="${platform.isUsed == 1}"><span style="color:red;">已开启</span></c:if>
							</td>
							<td>
							<a onClick="version_edit('${platform.id}')" href="javascript:;" >编辑</a>
							<c:if test="${platform.isUsed == 0}">
							<a onClick="operateVersionConfirm('${platform.id}','${platform.fileType}','${platform.isUsed}')" href="javascript:;" >开启版本</a>
							</c:if>
							<c:if test="${platform.isUsed == 1}">
							<a onClick="operateVersionConfirm('${platform.id}','${platform.fileType}','${platform.isUsed}')" href="javascript:;" >关闭版本</a>
							</c:if>
							<a onClick="deleteVersionConfirm('${platform.id}')" href="javascript:;" >删除</a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<jsp:include page="../../common/basepage.jsp"></jsp:include>
			</div>
		</div>
	</article>
</div>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>

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
	var versionName = $("#versionName").val();
	var fileType = '${fileType}';
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&versionName="+versionName+"&fileType="+fileType,
		url : basePath + 'system/platform/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}


</script>