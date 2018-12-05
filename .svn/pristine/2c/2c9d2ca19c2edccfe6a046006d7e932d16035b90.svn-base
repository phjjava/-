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
<jsp:include page="common/basecss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var basePath = '<%=basePath%>';

</script>

<title>家谱后台首页</title>
</head>
<body>
<!-- 头部加载 -->
<jsp:include page="common/header.jsp"></jsp:include>

<!-- 菜单加载 -->
<jsp:include page="common/menu.jsp"></jsp:include>

<div class="dislpayArrow hidden-xs">
	<a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a>
</div>
<section class="Hui-article-box">
<!-- 首页加载  -->
<jsp:include page="common/main.jsp"></jsp:include>
	
</section>
<!-- js引入 -->
<jsp:include page="common/basejs.jsp"></jsp:include>
</body>
</html>