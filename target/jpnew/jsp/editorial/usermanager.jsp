<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.chosen-search{display: none;}
</style>
<jsp:include page="../common/basecss.jsp"></jsp:include>

<!-- <link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.min.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" /> -->

<!-- 新版本 -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/assets/css/amazeui.min.css" />


<article class="cl pd-20">
	<form action="" method="post" class="form form-horizontal" id="userrolebranch-add">
	<input type="hidden" value="" name="branchname">
	<input type="hidden" value="${manager.id }" name="id" id="id">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>管理员：</label>
			<div class="formControls col-xs-8 col-sm-9" id="useridDiv">
				<!-- <select id="userid" class="user-select select" name='userid' data-val="${manager.userid }" >
				</select> -->

				<select 
					name="userid" 
					id="userid" 
					class="user-select select"
					data-val="${manager.userid }"
					data-am-selected="{btnWidth: '100%', searchBox: 1}">
				</select>
				
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>编委会：</label>
			<div class="formControls col-xs-8 col-sm-9">
				
					<!-- <select  name="ebid" id="ebid" class="select"  data-val="${manager.ebid}_${manager.ebtype}" >
					</select> -->
					<select 
						name="ebid" 
						id="ebid" 
						class="select"
						data-val="${manager.ebid}_${manager.ebtype}"
						data-am-selected="{btnWidth: '100%', searchBox: 1}">
					</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>职务：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" >
					<select id='post' class="select" name="postid" data-val="${manager.postid }">
						<option value=''>---- 请选择 ----</option>
					</select>
				</span>
			</div>
		</div>
		<!-- 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">管理分支：</label>
			<div class="formControls col-xs-8 col-sm-9">
 				
					<select data-placeholder="请选择分支" id="branchid" multiple data-val="" class="branch-select select">
					</select>
 				 
			</div>
		</div>
		  -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">功能权限：</label>
			<div class="formControls col-xs-8 col-sm-9" id="checkboxlist">
				<c:forEach var="plist" items="${plist}" varStatus="status">
					<dl class="permission-list">
						<dt>
							<label style="font-weight: bold"> <input type="checkbox"
								<c:if test="${plist.isfunctionid != null }">checked</c:if>
								id="${plist.functionid}" value="${plist.functionid}"
								name="checkedFunction" /> ${plist.functionname}
							</label>
						</dt>
						<dd>
							<c:set var="plistfunctionid" value="${plist.functionid }"
								scope="page" />
							<c:forEach var="clist" items="${clist}" varStatus="status">
								<c:if test="${clist.parentid == plistfunctionid}">
									<label class="kongge"> <input type="checkbox"
										type="checkbox" name="checkedFunction"
										value="${clist.functionid}"
										<c:if test="${clist.isfunctionid != null }">checked</c:if>
										id="${plist.functionid}_${clist.functionid }">
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
			<div class="col-xs-9 col-sm-10 col-xs-offset-3 col-sm-offset-2">
				<input class="btn btn-primary radius" type="button" onclick="save();" value="&nbsp;&nbsp;&nbsp;&nbsp;提交&nbsp;&nbsp;&nbsp;&nbsp;">
			</div>
		</div>
		 
	</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<!-- <script type="text/javascript" src="<%=basePath%>amaze/js/amazeui.chosen.min.js"></script> -->
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script> 

<script type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script> 
<!-- 新版本 -->
<script type="text/javascript" src="<%=basePath%>lib/assets/js/amazeui.min.js"></script>

<script type="text/javascript">
function save(){
	
	var userid = $("#userid").val();
	//console.log(userid);
	var username = $("#userid option:selected").attr("beginname");
	//var familyid = $("#userid").attr("familyid");
	var familyid = $("#userid option:selected").attr("familyid");
	if(userid == ""){
		layer.msg('请选择用户！',{icon:7,time:1000});
		return false;
	}
	var ebid = $("#ebid").val();
	var arr=new Array();
	arr = ebid.split("_");
	var ebid = arr[0];
	var ebtype = arr[1];
	var ebname = $("#ebid option:selected").text();
	if(ebid == ""){
		layer.msg('请选择编委会！',{icon:7,time:1000});
		return false;
	}
	var postid = $("#post").val();
	
	if(postid == ""){
		layer.msg('请选择职务',{icon:7,time:1000});
		return false;
	}
	
	var functionids = new Array();
	$("input[name='checkedFunction']:checked").each(function(i){
		functionids.push($(this).val());
	});
	$.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:{
			"id": $("#id").val(),
			"userid":userid,
			"username":username,
			"ebid":ebid,
			"ebname":ebname,
			"ebtype":ebtype,
			"postid":postid,
			//"postname":postname,
			//"ismanager":ismanager,
			"functionids":functionids
			
		},
		url : '<%=basePath%>userManager/save?curSec='+Math.random(),
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




				
		// 获取“添加发起人”_数据
		var data = JSON.parse(localStorage.getItem("website3"))
		var optionStr = "<option value=''>---- 请选择 ----</option>";
		var genlevel = "";
		// 初始显示数据条数
		var forNum = 7;
		// 添加发起人_数据添加
		function seleFun(forNum) {
				for(var i = 0; i < forNum; i++){
						optionStr+='<option familyid="'+data[i].familyid+'" beginname="'+data[i].username+'" parentid="'+data[i].branchid+'" value="' + data[i].userid + '">'
						//+data[i].username+' '+ data[i].phone + ' ' + data[i].genlevel+'世 ';
						+data[i].username+' '+ data[i].genlevel+'世 ';
						if(data[i].branchname){
							optionStr+= ' '+data[i].branchname;
						}
						if(data[i].address){
							optionStr+= ' '+data[i].address;
						}
						optionStr+= "</option>";
				}
				$('.user-select').html(optionStr);
				var dataval = $('#userid').attr('data-val');
				
				if(dataval != ''){
					$('#userid').val(dataval);
				}
		}
		seleFun(forNum)
		// 添加发起人_滑滚动条增加数据
		function checkscroll() {　
			forNum += 1;
			if(data.length > forNum) {
				seleFun(forNum)
			}
	}
	//初始化编委会
	$.ajax({
		type:'post',
		dataType:'json',
		async: false,
		url : '<%=basePath%>editorial/selecteditorialBoardList?curSec='+Math.random(),
		success:function(data,status){
			if(data){
				var optionStrM = "<option value=''>---- 请选择 ----</option>";
				var dataval = $('#ebid').attr('data-val');
				
				//var valArray = dataval.split(",");
				for(var i = 0; i < data.length; i++){
					
					//console.log(JSON.stringify(valArray));
					if(dataval.indexOf(data[i].id + "_" +data[i].name) != -1){
						optionStrM += "<option selected value='" + data[i].id+"_"+data[i].type+"' >"+data[i].name + "</option>";
					}else{
						optionStrM += "<option value='" + data[i].id + "_"+data[i].type+"' >"+data[i].name+"</option>";
					}
				}
				$('#ebid').html(optionStrM);
				if(dataval != ''){
			 		$('#ebid').val(dataval);
			 		initPost(dataval.split("_")[1]);
			 	}
				
			}else{
				alert("初始编委会失败！");  
			}
		},
		error:function(e) {
			console.log(e);
		}
	});

$(function() {
	var editTestData="${manager.id }";
	var dataUsername="${manager.username }";

	$(".chosen-search").show();

	$(".chosen-search").show();
	
	$('#ebid').change(function(){
		var ebid = $("#ebid").val();
		var arr=new Array();
		arr = ebid.split("_");
		var ebid = arr[0];
		var ebtype = arr[1];
		if(ebtype==1){
			initPost(1);
		}else{
			initPost(0);
		}
	});
	
	

	var userid = '${manager.userid}';
	$("#userid").val(userid);
	
	$(".permission-list dt input:checkbox").click(
			function() {
				$(this).closest("dl").find("dd input:checkbox").prop(
						"checked", $(this).prop("checked"));
			});
	$("dd input:checkbox")
			.click(
					function() {
						var l = $(this).parent().parent().find(
								"input:checked").length;
						var l2 = $(this).parents(".permission-list").find(
								"dd").find("input:checked").length;
						if ($(this).prop("checked")) {
							$(this).find("dt input:checkbox").prop(
									"checked", true);
							$(this).parents(".permission-list").find("dt")
									.first().find("input:checkbox").prop(
											"checked", true);
						} else {
							if (l == 0) {
								$(this).find("dt input:checkbox").prop(
										"checked", false);
							}
							if (l2 == 0) {
								$(this).parents(".permission-list").find(
										"dt").first()
										.find("input:checkbox").prop(
												"checked", false);
							}
						}
					});
});
function initPost(type){
	//初始化职务
	$.ajax({
		type:'post',
		dataType:'json',
		async: false,
		data:{
			"type":type
		},
		url : '<%=basePath%>post/selectPostList?curSec='+Math.random(),
		success:function(data,status){
			if(data){
				var optionStrM = "<option value=''>---- 请选择 ----</option>";
				var dataval = $('#post').attr('data-val');
				var valArray = dataval.split(",");
				for(var i = 0; i < data.length; i++){
					
					if(valArray.indexOf(data[i].id + "_" +data[i].name) != -1){
						optionStrM += "<option selected value='" + data[i].id+"' >"+data[i].name + "</option>";
					}else{
						optionStrM += "<option value='" + data[i].id + "' >"+data[i].name+"</option>";
					}
				}
				$('#post').html(optionStrM);
				if(dataval != ''){
			 		$('#post').val(dataval);
			 	}
			}else{
				alert("初始职务失败！");  
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
}
</script>