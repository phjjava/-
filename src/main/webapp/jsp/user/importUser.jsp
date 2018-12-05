<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal"
		id="user-import" enctype="multipart/form-data">
		<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-2" style="text-align:right">模板下载：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<a href="<%=basePath%>static/excel/导入直系用户模板.xls">点击下载导入用户模板XLS</a><br />
				<%-- <a href="<%=basePath%>static/excel/导入直系用户模板.xlsx">点击下载导入用户模板XLSX</a> --%>
			</div>
		</div>
		<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-2" style="text-align:right">选择文件：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="file" name="file" id="myfile" />
			</div>
		</div>
		<!-- 	<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-4" id="result" style="color:red"></label>
		</div> -->
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<input class="btn btn-primary radius" type="button"
					onclick="importData();" value="&nbsp;&nbsp;导入&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button"
					onclick="layer_close();" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<script type="text/javascript">
     //导入
	function importData(){
		var url = basePath + "user/importUser";
		var gwy=$("#myfile").val();
		if(gwy==""){
			 layer.alert('请选择要导入的文件!', {icon: 5});
			return;
		}
		if(gwy != "" && gwy.indexOf(".xls")==-1 && gwy.indexOf(".xlsx")==-1){
			 layer.alert('只能导入EXCEL数据文件!', {icon: 5});
			return;	
		}
		layer.confirm('确认要导入吗？',function(index){

	         var formData = new FormData($("#user-import")[0]);
	         $.ajax({
	                cache: true,
	                type: "POST",
	                url:url,
	                processData : false, 
					// 告诉jQuery不要去设置Content-Type请求头
					contentType : false,
	                data: formData,
	                async: false,
	                error: function(request) {
	                    layer.alert('请求失败，请稍后再试', {icon: 5});
	                },
	                success: function(data) {
	                  if(data.status == 1){
	                	  {
	                		  window.parent.searchs();
	                		  window.parent.layer.msg(data.data1, {icon: 6,time:5000});
	                		  layer_close();
	                	  }
		                 }else if(data.status == 2){
		                	 window.parent.layer.msg('导入用户数超过版本最高用户数!', {icon: 5,time:2000});
		                 }else if(data.status == 500){
		                	 window.parent.layer.msg(data.data1,{icon:5,time:5000});
		                 }else{
		                	 window.parent.layer.msg('导入用户失败，请联系管理员!', {icon: 5,time:2000});
		                 }
	                  
	                }
	            });
			
		});
		//if(confirm("确认导入吗？")){
		//}
	}
</script>
