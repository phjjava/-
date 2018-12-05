<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String fileType = request.getParameter("fileType");
	request.setAttribute("type",fileType); 
%>
<jsp:include page="../../common/basecss.jsp"></jsp:include>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal"
		id="version-add" enctype="multipart/form-data">
		<div class="row cl">
		    <input type="hidden" id="fileType" name="fileType" value="<%=fileType%>">
		    <input type="hidden" name="id" id="versionId" value="${platform.id}">
			<label class="form-label col-xs-4 col-sm-2">版本名称：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${platform.versionName}" id="versionName" name="versionName">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">版本号：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${platform.versionNo}" id="versionNo" name="versionNo">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">最小更新版本号：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${platform.minUpdateVersion}" id="minUpdateVersion" name="minUpdateVersion">
				<span style="color:red;">如向下兼容所有版本，此项请填0</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">升级描述：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${platform.upgradeDes}" id="upgradeDes" name="upgradeDes">
			</div>
		</div>
		<c:if test="${type == '1' || platform.fileType == '1'}">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">文件地址：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${platform.downloadUrl}" id="downloadUrl" name="downloadUrl">
			</div>
		</div>
		</c:if>
		<c:if test="${type == '2' || platform.fileType == '2'}">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">已有文件：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" disabled class="input-text" value="${platform.fileRealName}">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">选择文件：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="file" name="file" id="myfile" />
			</div>
		</div>
		</c:if>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<input class="btn btn-primary radius" type="button"
					onclick="add();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button"
					onclick="layer_close();" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
<jsp:include page="../../common/basejs.jsp"></jsp:include>
<script type="text/javascript">
     //导入
	function add(){
		   
		var versionName = $("#versionName").val();
		var versionNo = $("#versionNo").val();
		var minUpdateVersion = $("#minUpdateVersion").val();
		if(versionName == ''){
			layer.alert('版本名称为空!', {icon: 5});
			return false;
		}
		if(versionNo == ''){
			layer.alert('版本号为空!', {icon: 5});
			return false;
		}
		var re = /^[0-9]+$/ ;
	    var num = re.test(versionNo);
	    if(num == false){
	    	layer.alert('版本号请填写整数!', {icon: 5});
	    	return false;
	    }
		if(minUpdateVersion == ''){
			layer.alert('最小更新版本号为空!', {icon: 5});
			return false;
		}
	    var minUpdateVersionNum = re.test(minUpdateVersion);
	    if(minUpdateVersionNum == false){
	    	layer.alert('最小更新版本号请填写整数!', {icon: 5});
	    	return false;
	    }
		var url = basePath + "system/platform/merge";
		var gwy=$("#myfile").val();
		var versionId = $("#versionId").val();
		var fileType = "<%=fileType%>";
		if(versionId == null && fileType == '2'){
			if(gwy==""){
				 layer.alert('请选择要导入的文件!', {icon: 5});
				 return;
			}
			if(fileType == '1'){
				if(gwy != "" && gwy.indexOf(".ipa")==-1){
					 layer.alert('只能导入ios数据文件!', {icon: 5});
					return;	
				}
			}else{
				if(gwy != "" && gwy.indexOf(".apk")==-1){
					 layer.alert('只能导入安卓数据文件!', {icon: 5});
					return;	
				}
			}
		}
		layer.confirm('确认要保存吗？',function(index){
	         var formData = new FormData($("#version-add")[0]);
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
	                  if(data == 1){
                		  window.parent.searchs();
                		  window.parent.layer.msg("保存成功", {icon: 6,time:5000});
                		  layer_close();
                	  }else{
		                  window.parent.layer.msg('操作失败，请联系管理员!', {icon: 5,time:2000});
		              }
		                 
	                }
	            });
		});
	}
</script>
