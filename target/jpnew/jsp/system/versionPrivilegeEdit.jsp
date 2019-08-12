<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<article class="page-container">
<form action="" method="post" class="form form-horizontal" id="form-version-add">
	<input type="hidden" id="id" name="id" value="${sysVersionP.id}" />
	<input type="hidden" id="vid" name="vid" value="${sysVersionP.versionid}" />
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>版本名称：</label>
		<div class="formControls col-xs-8 col-sm-6">
		   <span class="select-box">
			       <select class="select" size="1" id="versionid">
					    <option value="">请选择版本</option>
					    <c:forEach var="sysVersionP" items="${versionList}" varStatus="status">
						  <option value="${sysVersionP.versionid}">${sysVersionP.versionname}</option>
						</c:forEach>
					</select> 
			</span>
		</div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>特权code：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysVersionP.privilegecode}" name=privilegecode id="privilegecode">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>特权名称：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysVersionP.privilegename}" name="privilegename" id="privilegename">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>特权值：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysVersionP.privilegevalue}" name="privilegevalue" id="privilegevalue">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>排序：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysVersionP.sort}" name="sort" id="sort">
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="button" onclick="save();" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			<input class="btn btn-primary radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
		</div>
	</div>
</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script>
$(document).ready(function(){  
    var vid=$("#vid").val();
    //根据值让option选中 
    $("#versionid option[value='"+vid+"']").attr("selected","selected"); 
}); 

function save(){
	if($("#form-version-add").valid()){
		var versionid = $("#versionid").val();
		var versionname = $("#versionid").find("option:selected").text();
		if(versionid == ""){
			layer.alert('请选择版本', {icon: 6});
			return false;
		}		
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:{
				"id":$("#id").val(),
				"versionid":$("#versionid").val(),
				"versionname":versionname,
				"privilegecode":$("#privilegecode").val(),
				"privilegename":$("#privilegename").val(),
				"privilegevalue":$("#privilegevalue").val(),
				"sort":$("#sort").val(),
			
			},
			url : '<%=basePath%>system/sysversionauth/save?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					window.parent.searchs();
					window.parent.layer.msg('保存成功!', {icon: 6,time:1000});
					layer_close();
				}else{
					window.parent.layer.msg('保存失败!',{icon:1,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	}
}
$(function() {
	$("#form-version-add").validate({
		rules:{
			versionname:{
				required:true,
				minlength:2,
				maxlength:50
			},
			privilegecode:{
				required:true,
			},
			privilegename:{
				required:true,
			},
			privilegevalue:{
				required:true,
			},
			sort:{
				required:true,
			}
			
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
		}
		
	});
	
});
</script>