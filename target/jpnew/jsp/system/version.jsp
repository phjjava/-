<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<article class="page-container">
<form action="" method="post" class="form form-horizontal" id="form-version-add">
	<input type="hidden" id="versionid" name="versionid" value="${sysVersion.versionid}" />
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>版本名称：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysVersion.versionname}" name="versionname" id="versionname">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>版本控制数量：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${sysVersion.usercount}" name="usercount" id="usercount">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3" >功能权限：</label>
		<div class="formControls col-xs-8 col-sm-8" id="checkboxlist">
			<c:forEach var="plist" items="${plist}" varStatus="status">
				<dl class="permission-list">
					<dt >
						<label  style="font-weight:bold">
							<input type="checkbox"<c:if test="${plist.isfunctionid != null }">checked</c:if> id="${plist.functionid}" value="${plist.functionid}" name="checkedFunction" />
							${plist.functionname}
						</label>
					</dt>
					<dd>
						<c:set var="plistfunctionid" value="${plist.functionid }" scope="page" />
						<c:forEach var="clist" items="${clist}" varStatus="status">
							<c:if test="${clist.parentid == plistfunctionid}">
								<label class="kongge">
								<input type="checkbox" type="checkbox" name="checkedFunction" value="${clist.functionid}" <c:if test="${clist.isfunctionid != null }">checked</c:if> id="${plist.functionid}_${clist.functionid }">
								${clist.functionname}
								</label>
							</c:if>
						</c:forEach>
					</dd>
				</dl>
			</c:forEach>
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="button" onclick="save();" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
		</div>
	</div>
</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script>
function save(){
	if($("#form-version-add").valid()){
		 var functionids = new Array();
		$("input[name='checkedFunction']:checked").each(function(i){
			functionids.push($(this).val());
		});
		console.log(functionids);
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:{"functionids":functionids,
				"versionid":$("#versionid").val(),
				"versionname":$("#versionname").val(),
				"usercount":$("#usercount").val(),
				
			
			},
			url : '<%=basePath%>system/sysversion/save?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					window.parent.searchs();
					window.parent.layer.msg('保存成功!', {icon: 6,time:1000});
					layer_close();
				}else{
					window.parent.layer.msg('保存失败!',{icon:1,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	}
}
$(function() {
	$("#form-version-add").validate({
		rules:{
			versionname:{
				required:true,
				minlength:2,
				maxlength:50
			},
			usercount:{
				required:true,
			}
			
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
		}
		
	});
	
	$(".permission-list dt input:checkbox").click(function(){
		$(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
	});
	$("dd input:checkbox").click(function(){
		var l =$(this).parent().parent().find("input:checked").length;
		var l2=$(this).parents(".permission-list").find("dd").find("input:checked").length;
		if($(this).prop("checked")){
			$(this).find("dt input:checkbox").prop("checked",true);
			$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
		}else{
			if(l==0){
				$(this).find("dt input:checkbox").prop("checked",false);
			}
			if(l2==0){
				$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
			}
		}
	});
	
});
</script>