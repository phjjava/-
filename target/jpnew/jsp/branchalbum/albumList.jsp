<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui_select.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />
<nav class="breadcrumb">
   <i class="Hui-iconfont">&#xe67f;</i> 首页 
   <span class="c-gray en">&gt;</span> 相册管理 
   <span class="c-gray en">&gt;</span> 相册 
</nav>
<div class="Hui-article">
<article class="cl pd-30">
	<div class="cl pd-5 bg-1 bk-gray"> 
	 <span class="l">
	   <a href="javascript:;" onclick="batchdelete()" class="btn btn-danger radius">
	      <i class="Hui-iconfont">&#xe6e2;</i> 批量删除
	   </a> 
	   <a href="javascript:;" onclick="album_add('新建相册','','','','','400')" class="btn btn-primary radius">
	      <i class="Hui-iconfont">&#xe600;</i> 新建相册
	   </a>
	   </span>
	   <span class="formControls col-xs-8 col-sm-5" style="margin-left:40px;">
			<select id="branchid" class="branch-select select" data-val="${branchalbum.branchid }" >
			</select>
		</span>
		<select name="deleteflag" id="deleteflag"  class="select" style="width:90px;height:35px;padding-left:5px;">
				<option value="">状态</option>
				<option value="0">已启用</option>
				<option value="1">已删除</option>
	   </select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="btn btn-success radius" onclick="searchs(1);" type="button" style="height:32px;"><i class="Hui-iconfont">&#xe665;</i> 查询
	 
	 </div>
	<div class="mt-10">
		<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
	      <table class="table table-border table-bordered table-hover table-bg table-sort">
		     <thead>
				<tr class="text-c">
					<th width="25"><input name="controlAll" id="controlAll" type="checkbox" value="" onclick="checkAll();"></th>
					<th width="25">序号</th>
					<th width="150">相册名称</th>
					<th width="100">所属分支</th>
					<th width="60">相片张数</th>
					<th width="60">排序</th>
					<th width="80">发布日期</th>
					<th width="40">删除状态</th>
					<th width="80">操作</th>
				</tr>
		    </thead>
		    <tbody>
		       <c:forEach var="branchalbum" items="${pageModel.list}" varStatus="status">
			   <tr class="text-c">
					<td><input type="checkbox" value="${branchalbum.albumid }" name="selected"></td>
					<td>${status.index + 1}</td>
					<td>${branchalbum.albumname}</td>
					<!-- <td>${branchalbum.branchname}</td>
					 -->
					<td>
						<c:choose>
						    <c:when test="${branchalbum.type == '0'}">
						       	家族相册
						    </c:when>
						    <c:otherwise>
						        ${branchalbum.branchname}
						    </c:otherwise>
						</c:choose>
					</td>
					
					<td>${branchalbum.albumNum}张</td>
					<td>${branchalbum.sort}</td>
					<td><fmt:formatDate value="${branchalbum.createtime}" pattern="yyyy-MM-dd"/></td>
					<td class="td-status">
						<c:if test="${branchalbum.deleteflag == 1}">
							<span class="label label-default radius">已删除</span>
						</c:if>
						<c:if test="${branchalbum.deleteflag == 0 }">
						    <span class="label label-success radius">已启用</span>
						</c:if>
					</td>
					<td class="td-manage">
					  <c:if test="${branchalbum.deleteflag == 1 }">
					  	<a  onClick="album_recover('${branchalbum.albumid}','${branchalbum.branchid}')" href="javascript:;" >恢复</a>					    
					  </c:if>
					  <c:if test="${branchalbum.deleteflag == 0 }">	
					  	   <c:if test="${branchalbum.albumNum != 0}">					
						   		<a class="maincolor" href="javascript:;" onClick="album_show('相册查看','${branchalbum.albumid}','${branchalbum.branchid}','','')" style="text-decoration:none;color:#5a98de">删除照片</a>&nbsp;&nbsp;|&nbsp;&nbsp;
						   </c:if>
						   <a title="编辑" href="javascript:;" onclick="album_add('相册编辑','${branchalbum.albumid}','${branchalbum.branchid}','1')" class="ml-5" style="text-decoration:none;color:#5a98de">编辑相册</a>
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
<script type="text/javascript" src="<%=basePath%>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>
<script type="text/javascript">
$(function(){
	//初始化分支
	initBranch();
	//回显
	$("#deleteflag").val('${branchalbum.deleteflag}');
});
/*相册查看详情
*/
function album_show(title,albumid,branchid,w,h){
	var w = 1300;
	var url = basePath + 'album/showPhoto?albumid='+albumid+'&branchid='+branchid+'&curSec='+Math.random();
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
	var branchid = $("#branchid").val();
	var deleteflag = $("#deleteflag").val();
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&branchid="+branchid+"&deleteflag="+deleteflag,
		url : basePath + 'album/list?curSec=' + Math.random(),
		async : true,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}
</script>
<script type="text/javascript">
/*相册-添加*/
function album_add(title,albumid,branchid,id,w,h){
	var w = 1200;
	var url = basePath + 'album/get?albumid='+albumid+'&branchid='+branchid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
/* function album_edit(title,albumid,id,w,h){
	var url = basePath + 'album/get?albumid='+albumid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
} */
//相册批量删除
function batchdelete(){
	//拿到选中的checkbox
	var albumids = "";
	var ck = $(':input[name=selected]');
    ck.each(function(){
      if($(this).is(':checked')){
    	  albumids += $(this).val()+ "," ;
       }
    })
    if(albumids == null||albumids==""){
    	//alert("请至少选择一个删除复选框");
    	layer.alert('请至少选择一个删除项', {icon: 7});
    	return false;
    }
	layer.confirm('确认要删除吗？',function(index){
	    $.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:"albumids="+albumids,
			url : '<%=basePath%>album/batchDelete?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					searchs();
					layer.msg('已删除!',{icon: 6,time:1000});
				}else{
					layer.msg('删除失败!', {icon: 5,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	});
}
//序号全选
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
//恢复删除相册
function album_recover(id,branchid){
	layer.confirm('确认要恢复吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(id,0,branchid);
		
	});
}
//修改状态
function changeStatus(id,type,branchid){
	$.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:{"albumid":id,"deleteflag":type,"branchid":branchid},
		url : '<%=basePath%>album/changeStatus?curSec='+Math.random(),
		success:function(data,status){
			if(status == 'success' && data == '1'){
				searchs();
				if(type == 1){
					layer.msg('已删除!',{icon: 5,time:1000});
				}else{
					layer.msg('已恢复!', {icon: 6,time:1000});
				}
				
			}else{
				//alert("撤销失败");
				layer.msg('撤销失败!',{icon: 5,time:1000});
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
}

</script> 