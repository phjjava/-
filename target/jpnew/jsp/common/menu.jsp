<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2">
		
		
		
		<c:forEach var="parentMenu" items="${parentFunctions }">
			<c:choose>
			<c:when test="${parentMenu.functionurl == 'index/init' }">
				 <dl id="main-menu">
					<dt>
						<a style="text-decoration:none;" href="<%=basePath%>index/init">
						<i class="Hui-iconfont Hui-iconfont-home"></i> 后台首页<i class="Hui-iconfont menu_dropdown-arrow"></i>
						</a>
					</dt>
				</dl>
			<%-- 	<dl id="main-menu">
			<dt><a href="<%=basePath%>index/init"><i class="Hui-iconfont Hui-iconfont-home"></i> 后台首页<i class="Hui-iconfont menu_dropdown-arrow"></i></a></dt>
		</dl> --%>
			</c:when>
			<c:otherwise>
			<dl>
				<dt>
					<i class="Hui-iconfont ${parentMenu.icon}"></i> ${parentMenu.functionname}<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd>
					<ul>
						<c:forEach var="menu" items="${childFunctionsMap[parentMenu.functionid]}">
							<li><a onclick="pageChange('${menu.functionurl}');" data-title="${menu.functionname}" href="javascript:void(0)">${menu.functionname}</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
			</c:otherwise>
			</c:choose>
			
		</c:forEach>
		
		<!-- <dl id="user-management">
			<dt><i class="Hui-iconfont">&#xe620;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a onclick="pageChange('branch/list');" data-title="分支管理" href="javascript:void(0)">分支管理</a></li>
					<li><a onclick="pageChange('user/list');" data-title="成员管理" href="javascript:void(0)">成员管理</a></li>
					<li><a onclick="pageChange('user/listToReview');" data-title="成员申请审核" href="javascript:void(0)">成员申请审核</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="publish-management">
			<dt><i class="Hui-iconfont">&#xe622;</i> 发布管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a onclick="pageChange('dynamic/list');" data-title="动态管理" href="javascript:;">动态管理</a></li>
					<li><a onclick="pageChange('notice/list');" data-title="公告管理" href="javascript:;">公告管理</a></li>
					<li><a onclick="pageChange('banner/list');" data-title="Banner管理" href="javascript:;">Banner管理</a></li>
					<li><a onclick="pageChange('famous/list');" data-title="名人录管理" href="javascript:;">名人录管理</a></li>
					<li><a onclick="pageChange('event/list');" data-title="家族大事记管理" href="javascript:;">家族大事记管理</a></li>
			</ul>
		</dd>
		</dl>
	    <dl id="genealogy-management">
			<dt><i class="Hui-iconfont">&#xe616;</i> 家谱管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
	        <dd>
				<ul>
					<li><a onclick="pageChange('jsp/genealogy/frontList.jsp');" data-title="封面设置" href="javascript:;">封面设置</a></li>
					<li><a onclick="pageChange('jsp/genealogy/principleList.jsp');" data-title="家训设置" href="javascript:">家训设置</a></li>
			        <li><a onclick="pageChange('jsp/genealogy/chapterList.jsp');" data-title="章节管理" href="javascript:">章节管理</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="album-management">
			<dt><i class="Hui-iconfont">&#xe60d;</i> 相册管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a onclick="pageChange('album/list');" data-title="相册管理" href="javascript:;">相册管理</a></li>
			   </ul>
		   </dd>
	   </dl>
		<dl id="authority-management">
			<dt><i class="Hui-iconfont">&#xe62d;</i> 权限管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a onclick="pageChange('role/list');" data-title="角色管理" href="javascript:;">角色管理</a></li>	
					<li><a onclick="pageChange('userrole/list');" data-title="权限管理" href="javascript:;">权限管理</a></li>
					
			</ul>
		</dd>
		</dl> -->
</div>
</aside>