<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<nav class="breadcrumb">
	<i class="Hui-iconfont"></i>
	<span class="c-666">首页</span>
	<span class="third_url_is_show" style="display: none;">
		<span class="c-999 en">&gt;</span>
	</span>
</nav>
<div class="Hui-article">
	<article class="cl pd-20">
		<p class="f-20 text-success" style="font-size:22px;line-height:50px;">欢迎使用家族后台管理系统 <span class="f-14"></span></p>
		<div style="overflow:hidden;">
		     <div style="float:left;overflow:hidden;width:32%;background-color:#f8f8f8;height:100px;border-radius:5px;">
		          <div style="width:30%;height:100px;background-color:#5a98de;float:left;text-align:center;line-height:100px;"><img src="<%=basePath%>static/h-ui.admin/images/shenhe.png" /></div>
		          <div style="float:left;text-align:center;width:70%">
		          		<div style="font-size:40px;">${countIndex.applycount }</div>
		          		<div>待审核成员数</div>
		          </div>
		     </div>
		     <div style="float:left;overflow:hidden;width:32%;background-color:#f8f8f8;height:100px;border-radius:5px;margin-left:2%;">
		          <div style="width:30%;height:100px;background-color:#ff5722;float:left;text-align:center;line-height:100px;"><img src="<%=basePath%>static/h-ui.admin/images/chengyuan.png" /></div>
		          <div style="float:left;text-align:center;width:70%">
		          		<div style="font-size:40px;">${countIndex.usercount }</div>
		          		<div>家族总成员数</div>
		          </div>
		     </div>
		     <div style="float:left;overflow:hidden;width:32%;background-color:#f8f8f8;height:100px;border-radius:5px;margin-left:2%;">
		          <div style="width:30%;height:100px;background-color:#5eb95e;float:left;text-align:center;line-height:100px;"><img src="<%=basePath%>static/h-ui.admin/images/shixi.png" /></div>
		          <div style="float:left;text-align:center;width:70%">
		          		<div style="font-size:40px;">${countIndex.shicount }</div>
		          		<div>家族世系数</div>
		          </div>
		     </div>
		</div>
		
		<table class="table table-border table-bordered table-bg" style="margin-top:20px;">
			<tbody>
				<tr class="text-c">
					<td style="width:200px;background-color:#f5fafe;font-size:14px;">家族分支数：</td>
					<td style="font-size:14px;text-align:left;font-weight:bold">&nbsp;&nbsp;&nbsp;${countIndex.branchcount }</td>
				</tr>
				<!-- <tr class="text-c">
					<td style="width:200px;background-color:#f5fafe;font-size:14px;">管理员数：</td>
					<td style="font-size:14px;text-align:left;font-weight:bold">&nbsp;&nbsp;&nbsp;24</td>
				</tr> -->
			</tbody>
		</table> 
	</article>
</div>