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
	<span class="c-666">名人录管理</span> 
</nav>
<div class="Hui-article">
  <article class="cl pd-30">
	<div class="cl pd-5 bg-1 bk-gray mt-0">
 	  <span class="l">
		  <a href="javascript:;" onclick="batchdelete();" class="btn btn-danger radius">
		     <i class="Hui-iconfont">&#xe6e2;</i> 批量删除
		  </a> 
	  </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	  <span class="l" style="margin-left:4px;">
	    <a title="新增名人录" href="javascript:;" onclick="famous_add('新增名人录','<%=basePath%>/jsp/famous/famous.jsp','500','1200')" class="btn btn-primary radius" style="text-decoration:none">
	      <i class="Hui-iconfont">&#xe600;</i>新增名人录
	    </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  </span> 
	  <span class="select-box inline" style="padding:0;margin-left:10px;">
			<select name="issee" id="issee" class="select" style="width:200px;height:30px;">
				<option value="">全部状态</option>
				<option value="0">显示</option>
				<option value="1">隐藏</option>
			</select>
		</span>
		<input type="text" name="username" id="username" placeholder=" 请输入姓名" value="${usercontent.username}" style="width:200px" class="input-text">
		<button name="" id="" class="btn btn-success radius" type="submit" onclick="searchs(1);" style="margin-left:15px;"><i class="Hui-iconfont">&#xe665;</i> 查询</button> 
	</div>
	<div class="mt-10">
		<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
			<table class="table table-border table-bordered table-hover table-bg" style="margin-top:5px;">
		<thead>
			<tr class="text-c">
			    <th width="20"><input type="checkbox" value="" name=""></th>
				<th width="20">序号</th>
				<th width="40">姓名</th>
				<th width="80">所属世系</th>
				<th width="80">浏览量</th>
				<th width="80">缩略图</th>
				<th width="40">排序</th>
				<th width="70">首页轮播显示</th>
				<th width="80">操作</th>
			</tr>
		</thead>
		<tbody>
		    <c:forEach var="usercontent" items="${pageModel.list}" varStatus="status">
			<tr class="text-c">
			    <td><input type="checkbox" value="${usercontent.userid}" name="selected"></td>
				<td>${status.index + 1 }</td>
				<td>${usercontent.username}</td>
				<td>${usercontent.genlevel}</td>
				<td>${usercontent.readcount}</td>
				<td>
					<a  title="点击查看大图" href="<%=downLoadUrl%>${usercontent.imgurl}" target="_Blank">
						<img alt="" src="<%=downLoadUrl%>${usercontent.imgurl}" height="30" width="30" />
					</a>				
				</td>
				<td>${usercontent.sort}</td>
				
				<c:if test="${usercontent.issee == 0}">
				 	<td class="td-status"><span class="label label-success radius">已显示</span></td>
				</c:if>
				<c:if test="${usercontent.issee == 1}">
				 	<td class="td-status"><span class="label label-default radius">已隐藏</span></td>
				</c:if>
				<td class="td-manage" style="">
						  <c:if test="${usercontent.issee == 1 }">
						    <a  onClick="famous_start('${usercontent.userid}')" href="javascript:;" >显示</a>&nbsp;|
						  </c:if>
						  <c:if test="${usercontent.issee == 0}">
							<a  onClick="famous_stop('${usercontent.userid}')" href="javascript:;" >隐藏</a>&nbsp;|
						  </c:if>
						  <a title="查看并编辑" href="javascript:;" onclick="famous_edit('编辑名人录','${usercontent.userid}','1200')">编辑</a>
						</td>
				<!--<td class="td-manage" style=""><a style="text-decoration:none;color:#5a98de" onClick="admin_stop(this,'10001')" href="javascript:;" title="停用">是否显示</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a title="编辑" href="javascript:;" onclick="famous_edit('编辑名人录','${usercontent.userid}','600','600')"  class="ml-5" style="text-decoration:none;color:#5a98de">查看并修改</a></td>-->
			</tr>
			</c:forEach>
		</tbody>
	</table>
		<jsp:include page="../common/basepage.jsp"></jsp:include>
		</div>
	</div>
  </article>
</div>
<script type="text/javascript" src="<%=basePath %>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">

//回显
$(function(){
	$("#issee").val('${usercontent.issee}');
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
	var issee=$("#issee").val();
	var username=$("#username").val();
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&username="+username+"&issee="+issee,
		url : basePath + 'famous/list?curSec=' + Math.random(),
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


/*Famous-发布*/
function famous_add(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*Famous-编辑*/
function famous_edit(title,id,w,h){	
	var url = basePath + 'famous/get?userid='+id+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}


/*管理员-停用*/
function famous_stop(userid){
	layer.confirm('确认要隐藏吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(userid,1);
		
	});
}

/*管理员-启用*/
function famous_start(userid){
	layer.confirm('确认要显示吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		changeStatus(userid,0);
	
	});
}

//改变显示状态
function changeStatus(userid,issee){	
	$.ajax({		
		type:'post',
		dataType:'text',
		async: true,
		data:"userid="+userid+"&issee="+issee,
		url : basePath + 'famous/changeStatus?curSec=' + Math.random(),
		
		success:function(data){
		//	alert(data);
			if(data == '1'){
				if(issee == 1){
					layer.msg('已隐藏!', {icon: 5,time:1000});
				}else{
					layer.msg('已启用!',{icon: 6,time:1000});
				}
				searchs(1);
			}else{
				alert("停用失败");
				searchs(1);
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
} 

//批量删除
function batchdelete(){
	//拿到选中的checkbox
	var userids = "";
	var ck = $(':input[name=selected]');
    ck.each(function(){
      if($(this).is(':checked')){
    	  userids += $(this).val()+ "," ;
       }
    })
    if(userids == null||userids==""){
    	//alert("请至少选择一个删除复选框");
    	layer.alert('请至少选择一个删除项', {icon: 5});
    	return false;
    }
	layer.confirm('确认要删除吗？删除后将无法恢复',function(index){
	    $.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:"userids="+userids,
			url : '<%=basePath%>famous/batchDelete?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					searchs();
					layer.msg('已删除!',{icon: 6,time:1000});
				}else{
					layer.msg('删除失败!', {icon: 5,time:1000});
				}
			},
			error:function(e) {
				layer.msg('删除失败!请联系管理员', {icon: 5,time:1000});
				console.log(e);
			}
		});
	});

}
</script>