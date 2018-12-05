<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>选择家族</title>
</head>
<body>
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
   <div style="text-align:center;font-size:26px;margin-bottom:60px;margin-top:20px;">请选择家族</div>
   <ul>
        
  	<c:forEach var="user" items="${loginUserList}" varStatus="status">
  		<li style="text-align:center;line-height:90px;"><span style="font-size:14px;color:#808080">进入:&nbsp;&nbsp;&nbsp;</span><a href="javascript:void(0);" onclick="choose('${user.userid}');" style="border:2px dashed #cccccc;font-size:20px;width:200px;line-height:60px;padding:20px 50px;text-decoration:none;">${user.familyname }</a></li>
  	</c:forEach>
  	</ul>
  </div>
</div>
<script type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript">
var basePath = '<%=basePath%>';
//存在多家族 根据userid 选择对应的家族
function choose(userid){
	$.ajax({
		type : "post",
		url : basePath + "login/choose?curSec=" + Math.random(),
		dataType: 'json',
		data : {
			"userid" : userid
		},
		success : function(data) {
			if (data.result == "true") {
				window.location.href = basePath + "index/init";
			}else {
				alert(data.info);
			}
		},
		error : function() {
			$btn.button('reset');
			$("#errinfo").html("登录失败，请稍后重试...");
		}
	});
	
}

</script>
</body>
</html>