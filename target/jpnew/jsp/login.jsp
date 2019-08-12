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
<title>家族管理后台</title>
</head>
<body>
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal" id="login-form" method="post">
      <div style="text-align:center;font-size:28px;margin:10px 0 20px 0;">家族管理后台</div>
      <div class="row cl">
        <label class="form-label col-xs-2"><i class="Hui-iconfont" >&#xe60d;</i></label>
        <div class="formControls col-xs-7">
          <input id="phone" name="phone" type="text" placeholder="账户" class="input-text size-L">
        </div>
        
      </div>
      <div class="row cl">
        <label class="form-label col-xs-2"><i class="Hui-iconfont">&#xe605;</i></label>
        <div class="formControls col-xs-7">
          <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-xs-2"><i class="Hui-iconfont">&#xe63f;</i></label>
        <div class="formControls col-xs-9">
          <input class="input-text size-L" type="text" placeholder="验证码" id="code" name="code" style="width:180px;">
          <img src="<%=basePath%>code/code" onclick="changeImg();" id="imgObj" style="margin-left:9px;"> <a id="kanbuq" onclick="changeImg();" href="javascript:;" class="shuaxin"></a> </div>
      </div>
      <div class="row cl" style="margin-left:70px;width:100%"><label class="col-xs-10" id="errinfo" style="color: red;"></label></div>
      <div class="row cl">
        <div class="formControls col-xs-9 col-xs-offset-2">
          <input type="button" id="loginBtn" onclick="login()"	 
								data-am-loading="{spinner: 'circle-o-notch', loadingText: '正在登录...'}" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" style="width:170px;">
          <input type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;" style="width:170px;margin-left:10px;">
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
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript">

	var basePath = '<%=basePath%>';
	function changeImg(){
		$("#imgObj").attr("src",basePath + "code/code?rmd="+new Date().getTime());
	}
	
	$(function(){
		$('body').keyup(function (e) {
            if (e.keyCode == 13) {
            	login();
            }
        });
	});
	
	function check(){
		if (jQuery.trim($("#phone").val()) == "") {
			$("#errinfo").html("账户不能为空.");
			$("#phone").focus();
			return false;
		}
		if (jQuery.trim($("#password").val()) == "") {
			$("#errinfo").html("密码不能为空.");
			$("#password").focus();
			return false;
		}
		if (jQuery.trim($("#code").val()) == "") {
			$("#errinfo").html("验证码不能为空.");
			$("#code").focus();
			return false;
		}
		
		return true;
	}
	function login(){
		if(check()){
			var $btn = $('#loginBtn');
			$btn.button('loading');
			$.ajax({
				type : "post",
				url : basePath + "login/login?curSec=" + Math.random(),
				dataType: 'json',
				data : $('#login-form').serialize(),
				success : function(data) {
					if (data.result == "true") {
						window.location.href = basePath + "index/init";
					}else if(data.result == "trues"){//存在多个家族
						window.location.href = basePath + "login/tochoose";
						
					}else {
						$btn.button('reset');
						changeImg();//刷新验证码
						$("#errinfo").html(data.info).show();
					}
				},
				error : function() {
					$btn.button('reset');
					$("#errinfo").html("登录失败，请稍后重试...");
				}
			});
		}
	}
</script>
</body>
</html>