﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="<%=basePath%>static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="<%=basePath%>images/wy.ico"/>
<title>家谱平台管理后台</title>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal" action="" method="post" id="loginForm">
    <div style="text-align:center;font-size:28px;margin:20px 0 40px 0;">家谱平台管理后台</div>
      <div class="row cl">
        <label class="form-label col-xs-2"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-xs-7">
          <input id="" name="loginname" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-xs-2"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-xs-7">
          <input id="" name="password" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
     <!--  <div class="row cl">
        <div class="formControls col-xs-9 col-xs-offset-2">
          <label for="online">
            <input type="checkbox" name="online" id="online" value="">
            使我保持登录状态</label>
        </div>
      </div> -->
      <div class="row cl">
      <div class="formControls col-xs-9 col-xs-offset-2">
        <span id="errinfo" style="color:red;font-size:14px;"><span></div>
        <div class="formControls col-xs-9 col-xs-offset-2" style="margin-top:15px">
 
          <input name="" type="button" onclick="login();" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" style="width:170px;">
          <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;" style="width:170px;margin-left:10px;">
        </div>
        
      </div>
      <div style="text-align:center; font-size:12px;color:#808080;line-height:70px;">Copyright©2017 家谱系统 All rights reserved.</div>
    </form>
  </div>
</div>
<script type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script>
</body>
<script>
	$(function(){
		$('body').keyup(function (e) {
	        if (e.keyCode == 13) {
	        	login();
	        }
	    });
	});
   function login(){
	   if($("#loginForm").valid()){
			$.ajax({
				type:'post',
				dataType:'json',
				async: true,
				data:$("#loginForm").serialize(),
				url : '<%=basePath%>system/login?curSec='+Math.random(),
				success:function(data){
					if (data.result == "true") {
						var url = '<%=basePath%>'+data.indexUrl;
						window.location.href = url;
					}else {
						$("#errinfo").html(data.info).show();
					}
				},
				error:function(e) {
					console.log(e);
				}
			});
		}
   }
   $(function() {
		$("#loginForm").validate({
			rules:{
				loginname:{
					required:true,
					minlength:2,
					maxlength:50
				},
				password:{
					required:true,
				}
			},
			messages:
			{
				loginname:"请填写用户名",
				password:"请填写密码"
			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
			}
		}); 
	});
</script>
</html>