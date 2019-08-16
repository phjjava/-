<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<title>家训查看</title>
   <div class="cl pd-20" style=" background-color:#5bacb6">
	<dl style="width:100%;text-align:center; color:#fff">
		<dt>
			<span class="f-18">奥氏家训</span>
		</dt>
	</dl>
</div>
<div class="pd-20">
	<table class="table">
		<tbody>
			<tr>
				<th class="text-r" width="80">家训内容：</th>
				<td>奥氏家族家训第一条：1
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="pd-20">
	<table class="table">
		<tbody>
		    <tr>
				<th class="text-r" width="80">所属家族：</th>
				<td>奥氏家族</td>
			</tr>
			<tr>
				<th class="text-r" width="80">创建者：</th>
				<td>奥34</td>
			</tr>
			<tr>
				<th class="text-r">创建时间：</th>
				<td>2013-09-32</td>
			</tr>
		</tbody>
	</table>
</div>
