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
<link rel="Bookmark" href="<%=basePath%>images/favicon.ico" >
<link rel="Shortcut Icon" href="<%=basePath%>images/favicon.ico" />
<jsp:include page="../common/basecss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var basePath = '<%=basePath%>';

</script>
<title>家谱平台首页</title>
</head>
<body>
<!-- 头部 -->
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="<%=basePath%>system/index">家谱平台管理系统</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">H-ui</a> 
			<span class="logo navbar-slogan f-l mr-10 hidden-xs"></span> 
			<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A">${sessionScope.systemUserContext.name } &nbsp;<i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<!-- <li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li>
							<li><a href="#">切换账户</a></li> -->
							<li><a href="javascript:void(0);" onclick="logout();">退出</a></li>
				       </ul>
			       </li>
				</ul>
	         </nav>
       </div>
</div>
</header>
<form id="pageForm" method="post">
	<input type="hidden" id="pageNo" name="pageNo" value="${empty pageModel.pageInfo.pageNum ? 1 : pageModel.pageInfo.pageNum}"> 
	<input type="hidden" id="pageSize" name="pageSize" value="${empty pageModel.pageInfo.pageSize ? 10 : pageModel.pageInfo.pageSize}"> 
	<input type="hidden" id="sortOrder" name="sortOrder" value="${pageModel.sortOrder }" /> 
	<input type="hidden" id="sortType" name="sortType" value="${pageModel.sortType }" />
	<input type="hidden" id="familyid" name="familyid" value="${pageModel.familyid }"/>
</form>
<script type="text/javascript">
function logout() {
	layer.confirm('确定退出系统吗？',function(index){
		$.ajax({
			type : "get",
			url : basePath + "system/loginout?curSec=" + Math.random(),
			success : function(data) {
				window.location.href = basePath + "jsp/system/login.jsp";
			},
			error : function() {
			}
		});
	});
}
</script>
<!-- 菜单加载 -->
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2">
		<c:if test="${menushow == 'true' }">
			<dl id="main-menu">
				<dt>
					<a style="text-decoration:none;" href="<%=basePath%>system/index">
						<i class="Hui-iconfont">&#xe616;</i> 后台首页<i class="Hui-iconfont menu_dropdown-arrow"></i>
					</a>
				</dt>
			</dl>
			<dl id="authority-management">
				<dt><i class="Hui-iconfont">&#xe62d;</i> 权限管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a onclick="pageChange('system/sysfunction/list');" href="javascript:;">菜单管理</a></li>
						<li><a onclick="pageChange('system/sysversion/list');" href="javascript:;">版本管理</a></li>
						<li><a onclick="pageChange('system/sysversionauth/list');" href="javascript:;">版本特权管理</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="authority-management">
				<dt><i class="Hui-iconfont">&#xe62d;</i> 家族管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a onclick="pageChange('system/family/list');" href="javascript:;">家族管理</a></li>
						
					</ul>
				</dd>
			</dl>
			<dl id="authority-management">
				<dt><i class="Hui-iconfont">&#xe62d;</i> 功能菜单管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a onclick="pageChange('system/functionMenu/list');" href="javascript:;">菜单管理</a></li>
					</ul>
				</dd>
			</dl>
			<dl id="authority-management">
				<dt><i class="Hui-iconfont">&#xe62d;</i> 版本发布管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a onclick="pageChange('system/platform/list?fileType=2');" href="javascript:;">安卓版本</a></li>
						<li><a onclick="pageChange('system/platform/list?fileType=1');" href="javascript:;">ios版本</a></li>
						
					</ul>
				</dd>
			</dl>
			<dl id="authority-management">
				<dt><i class="Hui-iconfont">&#xe62d;</i> 祭品管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a onclick="pageChange('system/oblationType/list');" href="javascript:;">分类管理</a></li>
						
						
					</ul>
				</dd>
			</dl>
			<dl id="authority-management">
				<dt><i class="Hui-iconfont Hui-iconfont-manage">&nbsp;</i>数据监控<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<li><a onclick="pageToSql('system/druid/sql.html');" href="javascript:;" >SQL监控</a></li>
						
					</ul>
				</dd>
			</dl>
		</c:if>
	</div>	
</aside>

<div class="dislpayArrow hidden-xs">
	<a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a>
</div>
<section class="Hui-article-box">
<!-- 首页加载  -->
<%-- <jsp:include page="./../common/main.jsp"></jsp:include> --%>
</section>
<!-- js引入 -->
<jsp:include page="./../common/basejs.jsp"></jsp:include>
</body>
</html>