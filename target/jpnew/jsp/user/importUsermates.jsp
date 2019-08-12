<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="user-import" enctype="multipart/form-data">
		<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-2" style="text-align:right">模板下载：</label> 
			<div class="formControls col-xs-8 col-sm-8">
				<a href="<%=basePath%>static/excel/导入配偶模板.xls">点击下载导入配偶模板XLS</a><br/>
				<%-- <a href="<%=basePath%>static/excel/导入配偶模板(新).xlsx">点击下载导入配偶模板XLSX</a> --%>
			</div>
		</div>	
		<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-2" style="text-align:right">选择文件：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="file" name="file" id="myfile"/>
			</div>
		</div>
		<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-4" id="result"></label>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<input class="btn btn-primary radius" type="button" onclick="importData();" value="&nbsp;&nbsp;导入&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;">
			</div>
	    </div>
	</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<script type="text/javascript">
     //导入
	function importData(){
		var url = basePath + "user/importUsermates";
		var gwy=$("#myfile").val();
		if(gwy==""){
			alert("请选择要导入的文件!");
			return;
		}
		if(gwy != "" && gwy.indexOf(".xls")==-1 && gwy.indexOf(".xlsx")==-1){
			alert("只能导入EXCEL数据文件!");
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
                    alert("请求失败，请稍后再试");
                },
                success: function(data) {
                  if(data.status == '1'){
	                   $("#result").html("导入成功");
             		  window.parent.searchs();
            		 // window.parent.layer.msg('导入成功!'+data.msg, {icon: 6,time:5000});
            		  window.parent.layer.msg(data.msg, {icon: 6,time:5000});
            		  layer_close();
	                 }else if(data.status == '2'){
	                   $("#result").html(data.msg);
	                   window.parent.layer.msg(data.msg, {icon: 5,time:2000});
	                 }else if(data.status == '500'){
	                  // window.parent.layer.msg('导入配偶失败，请验证直系用户信息是否正确!', {icon: 5,time:2000});
	                   window.parent.layer.msg(data.msg, {icon: 5,time:5000});
	                 }else{
	                   $("#result").html(data.msg);
	                   window.parent.layer.msg(data.msg, {icon: 5,time:2000});
	                 }
                }
            });
		});
	}
</script>
