<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<nav class="breadcrumb"><i class="Hui-iconfont"></i> <a href="/" class="maincolor">首页</a> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">家谱管理</span> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">家训设置</span> 
</nav>
<div class="Hui-article">
  <article class="cl pd-20">
	<div class="cl pd-5 bg-1 bk-gray">
	  <span class="l">
	     <a href="javascript:;" onclick="instruction_add('新增家训','<%=basePath%>jsp/genealogy/instruction.jsp','500','1200')" class="btn btn-primary radius">
	         <i class="Hui-iconfont">&#xe600;</i> 新增家训
	     </a>
	  </span> 
	  <span class="l"> </span> 
    </div>
	<div class="mt-20">
		<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
	       <table class="table table-border table-bordered table-hover table-bg" style="margin-top:5px">
				<thead>
					<tr class="text-c">
						<th width="100">所属家族</th>
						<th width="80">创建时间</th>
						<th width="80">操作</th>
					</tr>
				</thead>
				<tbody>
				  <c:forEach var="instruction" items="${pageModel.list}" varStatus="status">
					<tr class="text-c">
						<td>${instruction.familyid }</td>
						<td><fmt:formatDate value="${instruction.createtime}" pattern="yyyy-MM-dd"/></td>
						<td class="td-manage" style="">
						   <a title="修改" href="javascript:;" onclick="instruction_edit('家训设置','${instruction.instructionid}','500','1200')" class="ml-5" style="text-decoration:none;color:#5a98de">查看并修改</a>
						</td>
					</tr>
				  </c:forEach>
				</tbody>
	       </table>
		<jsp:include page="../common/basepage.jsp"></jsp:include>
		</div>
	</div>
 </article>
</div>
<script type="text/javascript" src="<%=basePath%>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">
function searchs(pageNoTemp) {
	var sortType = $("#sortType").val();
	var sortOrder = $("#sortOrder").val();
	var pageNo=$("#pageNo").val();
	if(pageNoTemp != undefined){
		$("#pageNo").val(pageNoTemp);
	}
	var pagesize = $('#pagesize option:selected').text();
	if (pagesize == 'All') {
		pagesize = 100000;
	}
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize(),
		url : basePath + 'instruction/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}
/*新增家训*/
function instruction_add(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*家训编辑*/
function instruction_edit(title,instructionid,id,w,h){
	var url = basePath + 'instruction/get?instructionid='+instructionid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
</script>