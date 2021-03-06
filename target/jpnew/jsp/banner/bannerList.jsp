<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="com.jp.common.ConstantUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String downLoadUrl = ConstantUtils.JIAPU_DOWNLOAD_URL;
%>
<script type="text/javascript" src="<%=basePath %>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<nav class="breadcrumb">
<i class="Hui-iconfont"></i> 首页
	<span class="c-999 en">&gt;</span>
	<span class="c-666">发布管理</span> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">Banner管理</span> 
</nav>
<div class="Hui-article">
  <article class="cl pd-30">
    <div class="cl pd-5 bg-1 bk-gray">
	  <span class="l">
	    <a href="javascript:;" id="all" onclick="batchdelete();" class="btn btn-danger radius">
	      <i class="Hui-iconfont">&#xe6e2;</i> 批量删除
	    </a> 
	  </span>
	  <span class="l" style="margin-left:4px;">
	    <a title="新增广告" href="javascript:;" onclick="banner_add('新增广告','<%=basePath%>/jsp/banner/banner.jsp','','1200')" class="btn btn-primary radius" style="text-decoration:none">
	      <i class="Hui-iconfont">&#xe600;</i>新增广告
	    </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  </span> 
	  <span class="inline" style="margin-left:15px;">
	    <select name="deleteflag" id="deleteflag"  class="select" style="width:150px;height:30px;padding-left:5px;">
			<option value="">全部状态</option>
			<option value="0">已启用</option>
			<option value="1">已删除</option>
		</select>
	  </span>&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-success radius" id="" name="" onclick="searchs(1);">
		<i class="Hui-iconfont">&#xe665;</i> 查询
		</button>
    </div>
	<div class="mt-10">
		<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
			<table class="table table-border table-bordered table-bg" style="margin-top:5px;">
				<thead>
					<tr class="text-c">
						<th width="25"><input name="controlAll" id="controlAll" type="checkbox" value="" onclick="checkAll();"></th>
						<th width="40">序号</th>
						<th width="150">标题</th>
						<th width="90">跳转标题</th>
						<th width="150">图片</th>
						<th width="40">状态</th>
						<th width="100">操作</th>
					</tr>
				</thead>
				<tbody>
				  <c:forEach var="banner" items="${pageModel.list}" varStatus="status">
					<tr class="text-c">
						<td><input type="checkbox" value="${banner.bannerid }" name="selected"></td>
						<td>${status.index + 1 }</td>
						<td>${banner.bannername}</td>
						<td>
						  ${banner.goname}
						</td>
						<td>
							<a title="点击查看大图" href="<%=downLoadUrl%>${banner.bannerweburl}" target="_Blank">
								<img alt="" src="<%=downLoadUrl%>${banner.bannerphoneurl}" height="35" width="35" />
							</a>
						</td>
					<%-- 	<td><img src="<%=downLoadUrl%>${banner.bannerphoneurl}" height="35" width="35" class="showpic"  /></td> --%>
						<td class="td-status">
							<c:if test="${banner.deleteflag == 0 }">
							    <span class="label label-success radius">正常</span>
							</c:if>
							<c:if test="${banner.deleteflag == 1}">
								<span class="label label-default radius">已删除</span>
							</c:if>
						</td>
						<td class="td-manage" style="">
						  <c:if test="${banner.deleteflag == 1 }">
						  	<a  onClick="banner_start('${banner.bannerid}')" href="javascript:;" >恢复</a>&nbsp;|					    
						  </c:if>
						  <c:if test="${banner.deleteflag == 0}">
							<a  onClick="banner_stop('${banner.bannerid}')" href="javascript:;" >删除</a>&nbsp;|
						  </c:if>
						  <a title="编辑" href="javascript:;" onclick="banner_edit('编辑广告','${banner.bannerid}','500','1200')">编辑</a>
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
function checkAll() {
	var checklist = document.getElementsByName ("selected");
	   if(document.getElementById("controlAll").checked){
	      for(var i=0;i<checklist.length;i++){
	      checklist[i].checked = true;
	   } 
	 }else{
	  for(var j=0;j<checklist.length;j++){
	     checklist[j].checked = false;
	  }
	}
}
$(function(){
	var deleteflag='${banner.deleteflag}';
	$("#deleteflag").val(deleteflag);
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
	var deleteflag = $("#deleteflag").val();
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&deleteflag="+deleteflag,
		url : basePath + 'banner/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}
/*Banner-发布*/
function banner_add(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*Banner-编辑*/
function banner_edit(title,bannerid,id,w,h){
	var url = basePath + 'banner/get?bannerid='+bannerid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
/*Banner-停用*/
function banner_stop(id){
	layer.confirm('确认要删除吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(id,1);
	});
}
function banner_start(id){
	layer.confirm('确认要恢复吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(id,0);
		
	});
}
//停用Banner
function changeStatus(id,deleteflag){
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:"bannerid="+id+"&deleteflag="+deleteflag,
			url : '<%=basePath%>banner/changeStatus?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					searchs();
					if(deleteflag == 1){
						layer.msg('已删除!',{icon: 5,time:1000});
					}else{
						layer.msg('已恢复!', {icon: 6,time:1000});
					}
					
				}else{
					alert("删除失败");
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
}
function batchdelete(){
	//拿到选中的checkbox
	var bannerids = "";
	var ck = $(':input[name=selected]');
    ck.each(function(){
      if($(this).is(':checked')){
    	  bannerids += $(this).val()+ "," ;
       }
    })
    if(bannerids == null||bannerids==""){
    	//alert("请至少选择一个删除复选框");
    	layer.alert('请至少选择一个删除项', {icon: 5});
    	return false;
    }
    $.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:"bannerids="+bannerids,
		url : '<%=basePath%>banner/batchDelete?curSec='+Math.random(),
		success:function(data,status){
			if(status == 'success' && data == '1'){
				searchs();
				layer.msg('已删除!',{icon: 5,time:1000});
			}else{
				layer.msg('未删除!', {icon: 6,time:1000});
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
}
</script>