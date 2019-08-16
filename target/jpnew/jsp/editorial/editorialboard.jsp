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
		<input type="hidden" name="id" id="id" value="${editorial.id }" />
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span
				class="c-red">*</span>编委会类别：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<select id='codetype' class="select input-text">
					<option value="0" selected>请选择</option>
					<option value="1"<c:if test="${editorial.codetype==1}">selected</c:if> >省</option>
					<option value="2"<c:if test="${editorial.codetype==2}">selected</c:if> >市</option>
					<option value="3"<c:if test="${editorial.codetype==3}">selected</c:if> >区</option>
					<option value="4"<c:if test="${editorial.codetype==4}">selected</c:if> >分支</option>
				</select>
			</div>
		</div>
		<div class="row cl" id="distpicker_name">
			<label class="form-label col-xs-4 col-sm-3"><span
				class="c-red">*</span>编委会名称：</label>
			<div class="formControls col-xs-8 col-sm-8 branch_name" >
				<select class="select input-text">
					<option value="0" selected>请选择</option>
				</select>
			</div> 
			<div class="formControls col-xs-8 col-sm-8 province" data-toggle="distpicker" style="display:none">
				<select size="1" class="select input-text"><option value="0" selected></option></select>
		    </div>
		   	<div class="formControls col-xs-8 col-sm-8 city" data-toggle="distpicker" style="display:none">
				<select size="1" class="select input-text" style="width:49%;"><option value="0" selected></option></select>
		        <select size="1" class="select input-text" style="width:50%;"></select>
		    </div>
		    <div class="formControls col-xs-8 col-sm-8 district" data-toggle="distpicker" style="display:none">
				<select size="1" class="select input-text" style="width:33%;"><option value="0" selected></option></select>
		        <select size="1" class="select input-text" style="width:32%;"></select>
		        <select class="select input-text" size="1" style="width:33%;"></select>
		    </div>
		    <!-- <div class="formControls col-xs-8 col-sm-8 branchid" style="display:none">
				<select size="1" class="input-text select" data-val="${user.branchid }" name="branchid" id="branchid">
				</select>
			</div> -->
			<div class="formControls col-xs-8 col-sm-8 branchid" style="display:none">
				 <select size="1" class="input-text select" data-placeholder="请选择分支" id="branchid" data-val="">
					</select>
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
			<label class="form-label col-xs-4 col-sm-3">描述：</label>
			<div class="formControls col-xs-8 col-sm-8">
				<input type="text" class="input-text" value="${editorial.eddesc }"
					id="eddesc" name="eddesc">
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-8 col-xs-offset-4 col-sm-offset-3" style="text-align:right">
				<input class="btn btn-primary radius" type="button" onclick="save();" value="提交" style="width:120px;"> 
				<input class="btn btn-default radius" type="button" onclick="closeIframe()" value="取消" style="width:120px">
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
		var editorialname = "";
		var code = "";
		if($("#codetype").val() == 1){
			editorialname = $(".province option:selected").val();
			code =$(".province option:selected").attr("data-code");
		}else if($("#codetype").val() == 2){
			editorialname = $(".city option:selected").val();
			editorialname += $(".city option:selected").eq(1).val();
			code =$(".city option:selected").eq(1).attr("data-code");
		}else if($("#codetype").val() == 3){
			editorialname = $(".district option:selected").val();
			editorialname += $(".district option:selected").eq(1).val();
			editorialname += $(".district option:selected").eq(2).val();
			code =$(".district option:selected").eq(2).attr("data-code");
		}else if($("#codetype").val() == 4){
			editorialname = $("#branchid option:selected").text();
			code = $("#branchid option:selected").val();
		}else{
			window.parent.layer.msg('请选择编委会!', {
				icon : 7,
				time : 1000
			});
			return;
		}
		if(name == "" || code== ""){
			window.parent.layer.msg('请选择编委会!', {
				icon : 7,
				time : 1000
			});
			return;
		}
		editorialname = editorialname+"分编委会";
		console.log(editorialname);
		console.log(code);
		console.log($("#id").val());
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:{
				"codetype":$("#codetype").val(),
				"id":$("#id").val(),
				"name":editorialname,
				"code":code,
				"eddesc":$("#eddesc").val()
				
			},
			url : '<%=basePath%>editorial/save?curSec=' + Math.random(),
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
		initSelect($("#codetype").val());
		$("#codetype").change(function(){
			var html = '';
			initSelect($("#codetype").val());
			
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