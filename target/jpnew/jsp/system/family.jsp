<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />

<article class="page-container">
<form action="" method="post" 
class="form form-horizontal" id="form-family-add">
    <input type="hidden" class="input-text" value="${family.familyid }" name="familyid" id="familyid">
    <input type="hidden" class="input-text" value="${user.userid }" name="userid" id="userid">
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>家族总编委会主任姓名：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${user.username }" name="username">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>手机号：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${user.phone }" name="phone" id="phone" >
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>家族名称：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${family.familyname}" name="familyname">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>家族姓氏：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${family.surname}" name="surname">
		</div>
	</div>
<%-- 	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>家族编码：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${family.familycode}" name="familycode">
		</div>
	</div> --%>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>家族版本：</label>
		<div class="formControls col-xs-8 col-sm-6">
		   <span class="select-box">
			       <select class="select" size="1" id="versionid">
					    <option value="">请选择版本</option>
					    <c:forEach var="version" items="${versionList}" varStatus="status">
						  <option value="${version.versionid}">${version.versionname}</option>
						</c:forEach>
					</select> 
			</span>
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
<script type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="<%=basePath%>static/h-ui.admin/js/H-ui.admin.page.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin.js"></script>
<script type="text/javascript" src="<%=basePath%>js/distpicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script>
//判断手机号合法性
function checkPhone(phone){ 
  if(!(/^1[3|4|5|7|8]\d{9}$/.test(phone)))
    return false; 
  else
	  return true;
}
$(function(){
	var versionid = '${family.version}';
	$("#versionid").val(versionid);
});


function save(){	
	var phone = document.getElementById('phone').value;
	if(checkPhone(phone)==false) 
		{
		 layer.alert('手机号码有误，请重填', {icon: 6});
		return;
		}

	if($("#form-family-add").valid()){
		var versionid = $("#versionid").val();
		var versionname = $("#versionid").find("option:selected").text();
		if(versionid == ""){
			layer.alert('请选择版本', {icon: 6});
			return false;
		}		
		$.ajax({
			type:'post',
			dataType:'json',
			async: true,
			data:$("#form-family-add").serialize()+"&version="+versionid+"&versionname="+versionname,
			url : '<%=basePath%>system/family/merge?curSec='+Math.random(),
			success:function(data){
				if(data.status == 0 ){
					window.parent.searchs();
					window.parent.layer.msg('保存成功!', {icon: 6,time:1000});
					layer_close();
				}else{
					window.parent.layer.msg(data.msg,{icon:5,time:2000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	}
}

/* 
$("#area").change(function(){
  $("#areacode").val($("#area option:selected").attr("data-code"));
});

$("#cityname").change(function(){
  $("#citycode").val($("#cityname option:selected").attr("data-code"));
});

$("#xname").change(function(){
  $("#xcode").val($("#xname option:selected").attr("data-code"));
});

$("#beginuserid").change(function(){
  $("#beginname").val($("#beginuserid option:selected").attr("beginname"));
  $("#parentid").val($("#beginuserid option:selected").attr("parentid"));
  $("#familyid").val($("#beginuserid option:selected").attr("familyid"));
}); */

$(function() {
	$("#form-family-add").validate({
		rules:{
			username:{
				required:true,
				minlength:2,
				maxlength:50
			},
			phone:{
				required:true,
				remote:{   
                    url : "<%=basePath%>system/family/validatePhone", //后台处理程序     
                    type: "post",  //数据发送方式   
                    async:false,
                    dataType: "json",       //接受数据格式       
                    data:  {                     //要传递的数据   
                    	familyid: function() {   
                        	return $("#familyid").val();   
                        },
                       	username: function() {   
                        	return $("#userid").val();   
                        },
                        phone: function() {   
                        	return $("#phone").val();   
                        } 
                   	}   

                 }  
			},
			familyname:{
				required:true,
			},
			familycode:{
				required:true,
			}
		},
		messages: {
			"phone": {
				remote: "该手机号系统中已存在2个家族，无法再次添加",
			},
        },
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
		}
	}); 
});
</script>
