<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="com.jp.common.ConstantUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String downLoadUrl = ConstantUtils.JIAPU_DOWNLOAD_URL;
%>

<nav class="breadcrumb"><i class="Hui-iconfont"></i> 首页
	<span class="c-999 en">&gt;</span>
	<span class="c-666">发布管理</span> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">家族大事记管理</span> 
</nav>
<div class="Hui-article">
  <article class="cl pd-30">
	 <div class="cl pd-5 bg-1 bk-gray">
	  <span class="l">
	     <a href="javascript:;" onclick="batchdelete();" class="btn btn-danger radius">
	        <i class="Hui-iconfont">&#xe6e2;</i> 批量删除
	     </a> </span><span class="l">
	     <a href="javascript:;" onclick="event_add('大事记管理','${event.eventid}','800','1200')" class="btn btn-primary radius" style="margin-left:4px;">
	        <i class="Hui-iconfont">&#xe600;</i> 新增大事记
	     </a> 
	   </span>  
	</div>
	<div class="mt-10">
		<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
			<table class="table table-border table-bordered table-hover table-bg" style="margin-top:5px;">
		  <thead>
			<tr class="text-c">
				<th width="25"><input name="controlAll" id="controlAll" type="checkbox" value="" onclick="checkAll();"></th>
				<th width="25">序号</th>
				<th width="150">标题</th>
				<th width="80">事件发生时间</th>
				<th width="40">浏览量</th>
				<th width="110">图片</th>
				<!-- <th width="40">删除状态</th> -->
				<th width="80">操作</th>
			</tr>
		  </thead>
		  <tbody>
		     <c:forEach var="event" items="${pageModel.list}" varStatus="status">
				<tr class="text-c">
					<td><input type="checkbox" value="${event.eventid }" name="selected"></td>
					<td>${status.index + 1 }</td>
					<td>${event.eventtitle}</td>
					<td><fmt:formatDate value="${event.createtime}" pattern="yyyy-MM-dd"/></td>
					<td>${event.readNum}</td>
					<td>
					  <img src="<%=downLoadUrl%>${event.imgurl}" style="width:190px;height:150px;background-color:#f8f8f8;margin:15px 7px 0 7px;" >
					</td>
					<%-- <td class="td-status">
							  <c:if test="${event.deleteflag == 0}">
								<span class="label label-success radius">正常</span>
							</c:if>
							<c:if test="${event.deleteflag == 1 }">
							    <span class="label label-default radius">已删除</span>
							</c:if>
					</td> --%>
					<td class="td-manage" style="">
					    <a title="编辑" href="javascript:;" onclick="event_edit('编辑大事记','${event.eventid}','800','1200')" class="ml-5" style="text-decoration:none;color:#5a98de">查看并修改</a>
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
<script type="text/javascript">
function event_add(title,eventid,id,w,h){
	var url = basePath + 'event/get?eventid='+eventid+'&curSec='+Math.random();
    layer_show(title,url,w,h);
}
/*编辑大事记*/
function event_edit(title,eventid,id,w,h){
	var url = basePath + 'event/get?eventid='+eventid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
/*大事记-置顶*/
function event_start(id,branchid){
	layer.confirm('确认要置顶吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(id,0);
		
	});
}
/*大事记-撤销*/
function event_stop(id,branchid){
	layer.confirm('确认要撤销吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(id,1);
	});
}
function changeStatus(id,type){
	$.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:{"eventid":id,"type":type},
		url : '<%=basePath%>event/changeStatus?curSec='+Math.random(),
		success:function(data,status){
			if(status == 'success' && data == '1'){
				searchs();
				if(type == 1){
					layer.msg('已撤销!',{icon: 5,time:1000});
				}else{
					layer.msg('已置顶!', {icon: 6,time:1000});
				}
				
			}else{
				alert("撤销失败");
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
}
//复选框全选、全不选
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
		url : basePath + 'event/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}

function batchdelete(){
	//拿到选中的checkbox
	var eventids = "";
	var ck = $(':input[name=selected]');
    ck.each(function(){
      if($(this).is(':checked')){
    	  eventids += $(this).val()+ "," ;
       }
    })
    if(eventids == null||eventids==""){
    	//alert("请至少选择一个删除复选框");
    	layer.alert('请至少选择一个删除项', {icon: 5});
    	return false;
    }
    $.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:"eventids="+eventids,
		url : '<%=basePath%>event/batchDelete?curSec='+Math.random(),
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
}
</script>