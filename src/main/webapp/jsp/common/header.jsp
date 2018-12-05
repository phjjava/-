<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"><a class="logo navbar-logo f-l mr-10 hidden-xs" href="<%=basePath %>index/init">${userContext.user.familyname }后台管理系统</a> 
		<a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">H-ui</a> 
			<span class="logo navbar-slogan f-l mr-10 hidden-xs"></span> 
			<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A">${sessionScope.userContext.usermanagers[0].ebname } : ${sessionScope.userContext.user.username } &nbsp;<i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" onClick="member_edit('个人信息','${userContext.user.userid }','1')">个人信息</a></li>
							<li><a href="javascript:;" onClick="changePwd('更改登陆密码','${userContext.user.userid }','1')">修改密码</a></li>
							<!-- <li><a href="#">切换账户</a></li>  -->
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
			url : basePath + "login/loginout?curSec=" + Math.random(),
			success : function(data) {
				window.location.href = basePath + "jsp/login.jsp";
			},
			error : function() {
			}
		});
	});
}
/*当前登录人个人信息编辑
*/
function member_edit(title,userid,id,w,h){
	var w = 1300;
	var url = basePath + 'user/edit?userid='+userid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}

//当前登陆人修改密码
function changePwd(title,userid,id,w,h){
	var w = 500;
	var h = 400;
	var url = basePath + 'jsp/user/changeLoginUserPwd.jsp';
	layer_show(title,url,w,h);
}
</script>