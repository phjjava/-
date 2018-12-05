<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="dataTables_info" id="DataTables_Table_0_info" role="status" aria-live="polite">
	<span class="r">共有数据：<strong>${pageModel.pageInfo.total }</strong>&nbsp;条</span>
	第 ${pageModel.pageInfo.pageNum }&nbsp;页，显示 
	<select aria-controls="DataTables_Table_0" id="pagesize" onchange="changePageSize();" data-val="${pageModel.pageSize}"  class="select" style="width: 50px;">
		<option value="10">10</option>
		<option value="25">25</option>
		<option value="50">50</option>
		<option value="100">100</option>
	</select>
	 条，
</div>
<div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_0_paginate">
	<%-- <c:choose>
		<c:when test="${pageModel.pageInfo.hasPreviousPage }">
			<a class="paginate_button previous disabled" href="javascript:first();" aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0" id="DataTables_Table_0_previous">
			首页
			</a>
		</c:when>
		<c:otherwise>
			<a href="javascript:void(0);" class="paginate_button previous disabled" style="cursor: default;text-decoration: none;color: gray;">
			首页
			</a>
		</c:otherwise>
	</c:choose> --%>
	<c:choose>
		<c:when test="${pageModel.pageInfo.hasPreviousPage }">
			<a class="paginate_button previous disabled" href="javascript:pre();" aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0" id="DataTables_Table_0_previous">
			上一页
			</a>
		</c:when>
		<c:otherwise>
			<a class="paginate_button previous disabled" href="javascript:void(0);" style="cursor: default;text-decoration: none;color: gray;">
			上一页
			</a>
		</c:otherwise>
	</c:choose>
	
	<span>
		<c:forEach var="page" items="${pageModel.pages}">
			<a class="paginate_button <c:if test="${pageModel.pageNo==page.key }">current</c:if>" aria-controls="DataTables_Table_0" data-dt-idx="1" tabindex="0" href="javascript:toPage('${page.key }');">${page.value }</a>
		</c:forEach>
	</span>
	<c:choose>
		<c:when test="${pageModel.pageInfo.hasNextPage }">
			<a class="paginate_button next disabled" href="javascript:next();" aria-controls="DataTables_Table_0" data-dt-idx="2" tabindex="0" id="DataTables_Table_0_next">
			下一页
			</a>
		</c:when>
		<c:otherwise>
			<a class="paginate_button next disabled" href="javascript:void(0);" style="cursor: default;text-decoration: none;color: gray;">
			下一页
			</a>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript">
$(function(){
	$("#pagesize").val($("#pagesize").attr("data-val"));
})
</script>
	<%-- <c:choose>
		<c:when test="${pageModel.pageInfo.hasNextPage }">
			<a href="javascript:last(${pageModel.pageInfo.pages });"  class="paginate_button next disabled" aria-controls="DataTables_Table_0" data-dt-idx="2" tabindex="0" id="DataTables_Table_0_next">
			尾页
			</a>
		</c:when>
		<c:otherwise>
			<a class="paginate_button next disabled" style="cursor: default;text-decoration: none;color: gray;">
			尾页
			</a>
		</c:otherwise>
	</c:choose> --%>
</div>