<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String parentid = request.getParameter("parentid") == null ? "" : request.getParameter("parentid");
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />

<article class="page-container">
<form action="" method="post" class="form form-horizontal" id="form-sysfunction-add">
	<input type="hidden" id="functionid" name="functionid" value="${sysFunction.functionid}"/>
	<input type="hidden" id="parentid" name="parentid" value="${sysFunction.parentid}"/>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>菜单名称：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysFunction.functionname}" name="functionname">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"> </span>ICON：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysFunction.icon}" name="icon">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>URL：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysFunction.functionurl}" name="functionurl">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>排序：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysFunction.sort}" name="sort">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>状态：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<span class="select-box">
				<select name="status" class="loadoption select" data="publicConstant.status" data-val="${sysFunction.status }">
			</select>
			</span>
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"> </span>描述：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysFunction.description}" name="description">
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
<script type="text/javascript">
var parentid = '<%=parentid %>';

if(parentid != null && parentid != ''){
	$('#parentid').val(parentid);
}
function save(){
	if($("#form-sysfunction-add").valid()){
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:$("#form-sysfunction-add").serialize(),
			url : '<%=basePath%>system/sysfunction/save?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					window.parent.searchChildList($("#parentid").val());
					window.parent.layer.msg('保存成功!', {icon: 6,time:1000});
					layer_close();
				}else{
					layer.msg('保存失败!',{icon:1,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	}
}


$(function() {
	getOption();
	$("#form-sysfunction-add").validate({
		rules:{
			functionname:{
				required:true,
				minlength:2,
				maxlength:8
			},
			functionurl:{
				required:true,
			},
			sort:{
				required:true,
			},
			status:{
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