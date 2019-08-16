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
		id="form-editorial-add">
		<input type="hidden" name="id" id="id" value="${post.id }" />
		
		<div class="row cl" id="distpicker_name">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">职务名称：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<input type="text" class="input-text" value="${post.name }"
						id="name" name="name">
				</div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span
					class="c-red">*</span>编委会类型：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<select id='type' class="select input-text">
						<option value="" selected>---- 请选择 ----</option>
						<option value="1"<c:if test="${post.type==1}">selected</c:if> >总编委</option>
						<option value="0"<c:if test="${post.type==0}">selected</c:if> >分编委</option>
					</select>
				</div>
			</div>
			 
			<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span
				class="c-red">*</span>角色类型：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<select id='ismanager' class="select input-text">
						<option value="" selected>---- 请选择 ----</option>
						<option value="1"<c:if test="${post.ismanager==1}">selected</c:if> >主任</option>
						<option value="0"<c:if test="${post.ismanager==0}">selected</c:if> >成员</option>
					</select>
				</div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">描述：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<input type="text" class="input-text" value="${post.roledesc }"
						id="roledesc" name="roledesc">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">排序：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<input type="text" class="input-text" oninput = "value=value.replace(/[^\d]/g,'')" value="${post.sort }"
						id="sort" name="sort"/>
				</div>
			</div>
		</div>
	<!-- 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span
				class="c-red">*</span>编委会类型：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select id='roleType' class="select input-text">
					<option value="0" selected>请选择</option>
					<option value="1"<c:if test="${role.ismanager==0&&role.type==1}">selected</c:if> >总编委</option>
					<option value="2"<c:if test="${role.ismanager==1&&role.type==0}">selected</c:if> >分编委</option>
				</select>
			</div>
		</div>
	-->
		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="button"
					onclick="save();" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"> 
					<input class="btn btn-primary radius" type="button" onclick="closeIframe()"
					value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath%>js/distpicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript"
	src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript"
	src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">

	

function save(){
	if($("#form-editorial-add").valid()){
		//if($("#roleType").val()!=0){
		/*
		 var functionids = new Array();
		$("input[name='checkedFunction']:checked").each(function(i){
			functionids.push($(this).val());
		});
		console.log(functionids);*/
		var type = $("#type").val();
		if(type==''){
			window.parent.layer.msg('请选择编委会!', {
				icon : 7,
				time : 1000
			});
			return;
		}
		
		var ismanager = $("#ismanager").val();
		if(ismanager==''){
			window.parent.layer.msg('请选择角色!', {
				icon : 7,
				time : 1000
			});
			return;
		}
		
		if(type==1 && ismanager==1){
			window.parent.layer.msg('不可创建总编委会主任!', {
				icon : 7,
				time : 1000
			});
			return;
		}
		var sort =$("#sort").val();
		if(sort==''){
			sort = 99;
		}
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:{
				"name":$("#name").val(),
				"id":$("#id").val(),
				"roledesc":$("#roledesc").val(),
				"type":type,
				"sort":sort,
				"ismanager":ismanager
				
			},
			url : '<%=basePath%>post/save?curSec=' + Math.random(),
				success : function(data, status) {
					if (status == 'success' && data == '1') {
						window.parent.searchs();
						window.parent.layer.msg('保存成功!', {
							icon : 6,
							time : 1000
						});
						layer_close();
					} else {
						window.parent.layer.msg('保存失败!', {
							icon : 5,
							time : 1000
						});
					}
				},
				error : function(e) {
					console.log(e);
				}
			});
	/*}else{
		window.parent.layer.msg('请选择角色类型!', {
			icon : 7,
			time : 1000
		});
	}*/
		}
	}
	$(function() {
		
		$("#type").change(function(){
			var type = $("#type").find("option:selected").val();
			
			if(type==1){
				
				$("#ismanager option[value='1']").attr("style","display:none");
			}else{
				
				$("#ismanager option[value='1']").attr("style","display:block");
			}
		});
		
		$("#form-editorial-add").validate({
			rules : {
				name : {
					required : true,
					minlength : 2,
					maxlength : 50
				}
			},
			onkeyup : false,
			focusCleanup : true,
			success : "valid",
			submitHandler : function(form) {
			}

		});
		

	});
	function initSelect(codetype){
		switch(codetype){
		case '1'://省
			$(".branch_name").hide();
			$("#distpicker_name .formControls").hide();
			$(".province").show();
			break;
		case '2'://市
			$(".branch_name").hide();
			$("#distpicker_name .formControls").hide();
			$(".city").show();
			break;
		case '3'://区
			$(".branch_name").hide();
			$("#distpicker_name .formControls").hide();
			$(".district").show();
			break;
		case '4'://分支
			$(".branch_name").hide();
			$("#distpicker_name .formControls").hide();
			$(".branchid").show();
			//初始化分支
			$.ajax({
				type:'post',
				dataType:'json',
				async: false,
				url : '<%=basePath%>branch/initBranch?curSec='+Math.random(),
				success:function(data,status){
					if(data){
						var optionStrM = "";
						var dataval = $('#branchid').attr('data-val');
						var valArray = dataval.split(",");
						for(var i = 0; i < data.length; i++){
							if(valArray.indexOf(data[i].branchid + "_" +data[i].branchname) != -1){
								optionStrM += "<option selected value='" + data[i].branchid+"' >" +data[i].area + " "+data[i].cityname + " " +data[i].xname + " "+data[i].branchname+"</option>";
							}else{
								optionStrM += "<option value='" + data[i].branchid+"' >" +data[i].area + " "+data[i].cityname + " " +data[i].xname + " "+data[i].branchname+"</option>";
							}
						}
						$('#branchid').html(optionStrM);
						
					}else{
						alert("初始化人员失败！");  
					}
				},
				error:function(e) {
					console.log(e);
				}
			});
			break;
		default:	
			$(".branch_name").hide();
			$("#distpicker_name .formControls").hide();
			var html = '<div class="formControls col-xs-8 col-sm-8 branch_name" >'
				+'<select class="select input-text">'
				+'<option value="0" selected>请选择</option>'
				+'</select>'
				+'</div>';
			$("#distpicker_name").append(html);
		};
	}
	//点击取消
	function closeIframe(){
		layer_close();
	}
</script>