<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="com.jp.common.ConstantUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String downLoadUrl = ConstantUtils.JIAPU_DOWNLOAD_URL;
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui_select.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />

<nav class="breadcrumb"><i class="Hui-iconfont"></i> 首页
	<span class="c-999 en">&gt;</span>
	<span class="c-666">发布管理</span> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">动态管理</span> 
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
	    <a title="新增动态" href="javascript:;" onclick="dynamic_add('新增动态','<%=basePath%>jsp/dynamic/dynamic.jsp','500','1200')" class="btn btn-primary radius" style="text-decoration:none">
	      <i class="Hui-iconfont">&#xe600;</i>新增动态</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  </span> 
	  <span class="formControls col-xs-8 col-sm-5">
			   <select name="branchid" id="branchid" class="branch-select select" data-val="${dynamic.branchid }" ></select>
		</span>
		<select name="type" id="type" value="${dynamic.type}" class="select" style="width:100px;height:35px;">
			<option value="">置顶状态</option>
			<option value="1">已置顶</option>
			<option value="0">未置顶</option>
		</select>
		<%-- <select name="deleteflag" id="deleteflag" value="${dynamic.deleteflag }" class="select" style="width:80px;height:30px;">
			<option value="">删除状态</option>
			<option value="1">已删除</option>
			<option value="0">已启用</option>
		</select> --%>
		<button class="btn btn-success radius" id="" name="" onclick="searchs();" style="height:34px;margin-top:-3px;margin-left:10px;"><i class="Hui-iconfont">&#xe665;</i> 查询</button></span>
    </div>
	<div class="mt-10">
		<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
		   <table class="table table-border table-bordered table-hover table-bg" style="margin-top:5px;">
		     <thead>
			    <tr class="text-c">
					<th width="25"><input name="controlAll" id="controlAll" type="checkbox" value="" onclick="checkAll();"></th>
					<th width="25">序号</th>
					<th width="130">标题</th>
					<th width="90">图片</th>
					<th width="60">发布日期</th>
					<th width="90">所属分支</th>
					<th width="50">浏览量</th>
					<!-- <th width="40">删除状态</th> -->
					<th width="80">操作</th>
			    </tr>
		     </thead>
		     <tbody>
		        <c:forEach var="dynamic" items="${pageModel.list}" varStatus="status">
				    <tr class="text-c">
						<td><input type="checkbox" value="${dynamic.dyid }" name="selected"></td>
						<td>${status.index + 1 }</td>
						<td>${dynamic.dytitle}</td>
						<td>
						    <img src="<%=downLoadUrl%>${dynamic.imgurl}" style="width:190px;height:150px;background-color:#f8f8f8;margin:15px 7px 0 7px;" >
						</td>
						<td><fmt:formatDate value="${dynamic.createtime}" pattern="yyyy-MM-dd"/></td>
						
						<td>
							<c:choose>
							    <c:when test="${dynamic.dytype == '0'}">
						       		家族动态
						    	</c:when>
						    	<c:otherwise>
						        	${dynamic.branchname}
						    	</c:otherwise>
								</c:choose>
						</td>
						<td>${dynamic.readNum}</td>
						<%-- <td class="td-status">
							<c:if test="${dynamic.deleteflag == 0}">
								<span class="label label-success radius">正常</span>
							</c:if>
							<c:if test="${dynamic.deleteflag == 1}">
							    <span class="label label-default radius">已删除</span>
							</c:if>
						</td> --%>
						<td class="td-manage" style="">
						    <a title="编辑" href="javascript:;" onclick="dynamic_edit('编辑动态','${dynamic.dyid}','500','1200')" >查看并编辑</a>
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
<script type="text/javascript" src="<%=basePath%>js/distpicker.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>
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
//回显
$(function(){
	initBranch();
	$("#type").val('${dynamic.type}');
	$("#deleteflag").val($('#deleteflag').attr('data-val'));
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
	var dyid = $("#dyid").val();
	var area = $("#area").val();
	var cityname = $("#cityname").val();
	var xname = $("#xname").val();
	var branchname = $("#branchname").val();
	var branchid = $("#branchid").val();
	if(branchid==null){
		branchid="";
	}
	var type = $("#type").val();
	var deleteflag = $("#deleteflag").val();
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&area="+area+"&cityname="+cityname+"&xname="+xname+"&branchid="+branchid+"&branchname="+branchname+"&type="+type,
		url : basePath + 'dynamic/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}
function changeStatus(id,type,branchid){
	$.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:{"dyid":id,"type":type,"branchid":branchid},
		url : '<%=basePath%>dynamic/changeStatus?curSec='+Math.random(),
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
function batchdelete(){
	//拿到选中的checkbox
	var dyids = "";
	var ck = $(':input[name=selected]');
    ck.each(function(){
      if($(this).is(':checked')){
    	  dyids += $(this).val()+ "," ;
       }
    })
    if(dyids == null||dyids==""){
    	layer.alert('请至少选择一个删除项', {icon: 5});
    	return false;
    }
	layer.confirm('确认要批量删除吗？',function(index){
	    $.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:"dyids="+dyids,
			url : '<%=basePath%>dynamic/batchDelete?curSec='+Math.random(),
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
function dynamic_add(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*动态-编辑*/
function dynamic_edit(title,dyid,id,w,h){
	var url = basePath + 'dynamic/get?dyid='+dyid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
/*动态-撤销*/
function dynamic_stop(id,branchid){
	layer.confirm('确认要撤销吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(id,1,branchid);
	});
} 

/*动态-置顶*/
function dynamic_start(id,branchid){
	layer.confirm('确认要置顶吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(id,0,branchid);
		
	});
} 
</script>
