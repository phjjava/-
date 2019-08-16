<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<nav class="breadcrumb"><i class="Hui-iconfont"></i> 首页
	<span class="c-999 en">&gt;</span>
	<span class="c-666">家谱管理</span> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">章节管理</span> 
</nav>
<div class="Hui-article">
  <article class="cl pd-30">
	<div class="cl pd-5 bg-1 bk-gray">
	  <span class="l">
	     <a href="javascript:;" id="all" onclick="batchdelete()" class="btn btn-danger radius">
	         <i class="Hui-iconfont">&#xe6e2;</i>批量删除
	     </a>&nbsp;
	     <a href="javascript:;" onclick="introduce_add('新增章节','<%=basePath%>jsp/genealogy/introduce.jsp','','1200','550')" class="btn btn-primary radius">
	         <i class="Hui-iconfont">&#xe600;</i> 新增章节
	     </a>
	  </span> 
	  <span class="l"> </span> 
    </div>
	<div class="mt-10">
		<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
	       <table class="table table-border table-bordered table-hover table-bg" style="margin-top:5px">
				<thead>
					<tr class="text-c">
						<th width="25"><input name="controlAll" id="controlAll" type="checkbox" value="" onclick="checkAll();"></th>
						<th width="25">序号</th>			
						<th width="200">标题</th>
						<th width="80">创建时间</th>
						<th width="40">排序值</th>
						<th width="80">操作</th>
					</tr>
				</thead>
				<tbody>
				  <c:forEach var="introduce" items="${pageModel.list}" varStatus="status">
					<tr class="text-c">
						<td><input type="checkbox" value="${introduce.introduceid }" name="selected"></td>
						<td>${status.index + 1 }</td>
						<td>${introduce.introducetitle}</td>
						<td><fmt:formatDate value="${introduce.createtime}" pattern="yyyy-MM-dd"/></td>
						<td>${introduce.sort}</td>
						<td class="td-manage" style="">
						   <a title="修改" href="javascript:;" onclick="introduce_edit('编辑章节','${introduce.introduceid}','500','1200')" class="ml-5" style="text-decoration:none;color:#5a98de">查看并修改</a>
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
function checkAll() {
	var checklist = document.getElementsByName ("selected");
	 if(document.getElementById("controlAll").checked){
	   for(var i=0;i<checklist.length;i++){
	      checklist[i].checked = true;
	   } 
	 }else{
	   for(var j=0;j<checklist.length;j++){
	     checklist[j].checked = false;
	  }
  }
}
function batchdelete(){
	//拿到选中的checkbox
	var introduceids = "";
	var ck = $(':input[name=selected]');
    ck.each(function(){
      if($(this).is(':checked')){
    	  introduceids += $(this).val()+ "," ;
       }
    })
    if(introduceids == null||introduceids==""){
    	layer.alert('请至少选择一个删除项', {icon: 5});
    	return false;
    }
	layer.confirm('确认要批量删除吗？',function(index){
	    $.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:"introduceids="+introduceids,
			url : '<%=basePath%>introduce/batchDelete?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					searchs();
					layer.msg('已删除!',{icon: 6,time:1000});
				}else{
					layer.msg('删除失败!', {icon: 5,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	});

}
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
		url : basePath + 'introduce/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
}
/*章节-发布*/
function introduce_add(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*章节-编辑*/
function introduce_edit(title,introduceid,id,w,h){
	var url = basePath + 'introduce/get?introduceid='+introduceid+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
</script>