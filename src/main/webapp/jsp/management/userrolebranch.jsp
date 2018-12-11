<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />
<article class="cl pd-20">
	<form action="" method="post" class="form form-horizontal" id="userrolebranch-add">
	<input type="hidden" value="" name="branchname">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>管理员：</label>
			<div class="formControls col-xs-8 col-sm-9" id="useridDiv">
				<select id="userid" class="user-select select" data-val="${userrole.userid }" >
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>角色：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" >
					<select  id="roleid" class="select"data-val="${userrole.roleid }" >
					</select>
				</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">管理分支：</label>
			<div class="formControls col-xs-8 col-sm-9">
<!-- 				<span class="select-box" style="width:150px;"> -->
					<select data-placeholder="请选择分支" id="branchid" multiple data-val="${userrole.branchidStrs}" class="branch-select select">
					</select>
<!-- 				</span>  -->
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
<script type="text/javascript" src="<%=basePath%>amaze/js/amazeui.chosen.min.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript">
function save(){
	var branchid = $("#branchid").val();
	var userid = $("#userid").val();
	//var familyid = $("#userid").attr("familyid");
	var familyid = $("#userid option:selected").attr("familyid");
	if(userid == ""){
		layer.msg('请选择用户！',{icon:7,time:1000});
		return false;
	}
	var roleid = $("#roleid").val();
	if(roleid == ""){
		layer.msg('请选择角色！',{icon:7,time:1000});
		return false;
	}
	$.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:$("#userrolebranch-add").serialize()+"&branchid="+branchid+"&userid="+userid+"&roleid="+roleid+"&familyid="+familyid,
		url : '<%=basePath%>userrole/mergeManagent?curSec='+Math.random(),
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

$(function() {
	var editTestData="${userrole.userid }";
	var dataUsername="${userrole.username }";
	//如果为编辑不初始化管理员下拉栏
	if(editTestData!=null&&editTestData!=''){
		$("#userid").remove();
		var htmlStr1='<div hidden><input id="userid"  disabled="disabled"  class="input-text" value="" name="userid" type="text"></div>';
		var htmlStr2='  '+'<input id="showname"  readonly="readonly"  class="input-text" value="'+dataUsername+'" name="showname" type="text">';
		$("#useridDiv").append(htmlStr1+htmlStr2);
	}else{
		$.ajax({
			type:'post',
			dataType:'json',
			async: false,
			data:{"ismanager" : 1},
			url : '<%=basePath%>user/selectUserItemLive?curSec='+Math.random(),
			success:function(data,status){
				if(data){
					var optionStr = "<option value=''>---- 请选择 ----</option>";
					for(var i = 0; i < data.length; i++){
						optionStr+='<option familyid="'+data[i].familyid+'" beginname="'+data[i].username+'" parentid="'+data[i].branchid+'" value="' + data[i].userid + '">'
						+data[i].username+' '+ data[i].phone + ' ' + data[i].genlevel+'世</option> ';
					}
					$('.user-select').html(optionStr);
					var dataval = $('#userid').attr('data-val');
					
					if(dataval != ''){
				 		$('#userid').val(dataval);
				 	}
				}else{
					alert("初始化人员失败！");
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
		
		$('.user-select').chosen({
	    	search_contains: true,
	      	max_selected_options: 1,
	      	no_results_text: "没有找到",
	    });		
	}

	
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
						optionStrM += "<option selected value='" + data[i].branchid + "_" +data[i].branchname+"' >" +data[i].area + " "+data[i].cityname + " " +data[i].xname + " "+data[i].branchname+"</option>";
					}else{
						optionStrM += "<option value='" + data[i].branchid + "_" +data[i].branchname+"' >" +data[i].area + " "+data[i].cityname + " " +data[i].xname + " "+data[i].branchname+"</option>";
					}
				}
				$('.branch-select').html(optionStrM);
				
			}else{
				alert("初始化人员失败！");  
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
	$('.branch-select').chosen({
      	search_contains: true,
      	no_results_text: "没有找到",
    });
	//初始化角色
	$.ajax({
		type:'post',
		dataType:'json',
		async: false,
		url : '<%=basePath%>role/selectRoleList?curSec='+Math.random(),
		success:function(data,status){
			if(data){
				var optionStr = "<option value=''>---- 请选择 ----</option>";
				for(var i = 0; i < data.length; i++){
					optionStr+="<option value=" + data[i].roleid + ">"+ data[i].rolename+"</option>";
				}
				$('#roleid').html(optionStr);
				var dataval = $('#roleid').attr('data-val');
				
				if(dataval != ''){
			 		$('#roleid').val(dataval);
			 	}
			}else{
				alert("初始化角色失败！");
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
	
	var roleid = '${userrole.roleid}';
	$("#roleid").val(roleid);
	var userid = '${userrole.userid}';
	$("#userid").val(userid);
});
</script>