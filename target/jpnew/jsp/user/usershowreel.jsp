<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/webuploader/0.1.5/webuploader.css"  />
<article class="page-container">
	<form class="form form-horizontal" id="banner_add">
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-1">&nbsp;&nbsp;&nbsp;&nbsp;作品描述：</label>
			<div class="formControls col-xs-9 col-sm-10">
				<textarea style="width:100%;height:60px; border:1px solid #dddddd"></textarea>
			</div>
		</div>

		<div class="row cl">
			<div class="col-xs-9 col-sm-10 col-xs-offset-3 col-sm-offset-1">
				<button onClick="banner_save();" class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
				<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</article>
<script type="text/javascript" src="<%=basePath%>lib/webuploader/0.1.5/webuploader.min.js"></script> 

