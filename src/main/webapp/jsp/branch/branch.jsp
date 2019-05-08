<%@page import="com.jp.common.ConstantUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String JIAPU_UPLOAD_URL = ConstantUtils.JIAPU_UPLOAD_URL;
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/h-ui.admin/css/style.css" />

<!-- <link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" /> -->
<!-- 新版本 -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/assets/css/amazeui.min.css" />



<article class="page-container">
<form action="" method="post" class="form form-horizontal" id="form-branch-add">
	<input type="hidden" id="branchid" name="branchid" value="${branch.branchid}"/>
	<!-- 2019/05/08 -->
	<input type="hidden" id="beginuserid" name="beginuserid" value="${branch.beginuserid}"/>

	<input type="hidden" id="areacode" name="areacode" value="${branch.areacode}"/>
	<input type="hidden" id="citycode"  name="citycode" value="${branch.citycode}"/>
	<input type="hidden" id="xcode"  name="xcode" value="${branch.xcode}"/>
	<input type="hidden" id="beginname"  name="beginname" value="${branch.beginname}"/>
<%-- 	<input type="hidden" id="parentid"  name="parentid" value="${branch.parentid}"/> --%>
	<input type="hidden" id="familyid"  name="familyid" value="${branch.familyid}"/>
	
<!-- 	<input type="text" name="updatetime" value="2017-05-03 11:23:55"/> -->
	<div class="row cl">
		<label class="form-label col-xs-3 col-sm-3" style="text-align:right"><span class="c-red">*</span>分支名称：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${branch.branchname}" name="branchname" id="branchname">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-3 col-sm-3" style="text-align:right">
			<span class="c-red">*</span>省份：<br/><br/>
			<span class="c-red">*</span>市：<br/><br/>
			<span class="c-red">*</span>区/县：</br><br/>
		</label>
		<div class=" col-xs-8 col-sm-6" data-toggle="distpicker" id="distpicker">
		<span class="select-box">
			<select name="area" id="area" class="select form-control" data-province="${branch.area }"></select><div><br/></div>
			<select name="cityname" id="cityname" class="select form-control" data-city="${branch.cityname }"></select><div><br/></div>
		    <select name="xname" id="xname" class="select form-control" data-district="${branch.xname }"></select>
		</span>
		</div>
  </div>
  <div class="row cl">
		<label class="form-label col-xs-3 col-sm-3" style="text-align:right"><span class="c-red">*</span>详细地址：</label>
		<div class="formControls col-xs-8 col-sm-6">
			<input type="text" class="input-text" value="${branch.address}" name="address" id="address">
		</div>
	</div>
  <div class="row cl">
	<label class="form-label col-xs-3 col-sm-3" style="text-align:right">请添加发起人：</label>
	<div class="formControls col-xs-8 col-sm-6" id="searchSelectBox">
		<!-- <select name="beginuserid" id="beginuserid" class="my-select select"  data-val="${branch.beginuserid}" >
		</select> -->

		<!-- <select 
			style="display:none"
			name="beginuserid" 
			id="beginuserid" 
			class="my-select select"  
			data-val="${branch.beginuserid}" 
			data-am-selected="{btnWidth: '100%', searchBox: 1}">
		</select> -->

		<input type="text" class="input-text" value="${branch.beginname} ${branch.genlevel} ${branch.area}${branch.cityname}${branch.xname}${branch.address}" placeholder="请输入姓名" style="width: 85%;" id="searInput">
		<input class="btn btn-primary radius" type="button" style="height: 30px;" value="搜索" id="search" >

		<div id="searchSelect">
				<div></div>
		</div>
	</div>
</div>
<div class="row cl"> 
	<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
		<input class="btn btn-primary radius" type="button" onclick="save();" value="&nbsp;&nbsp;&nbsp;&nbsp;提交&nbsp;&nbsp;&nbsp;&nbsp;">
		<input class="btn btn-primary radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;&nbsp;&nbsp;取消&nbsp;&nbsp;&nbsp;&nbsp;">
	</div>
</div>
</form>
</article>
<script type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="<%=basePath%>static/h-ui.admin/js/H-ui.admin.page.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin.js"></script>
<script type="text/javascript" src="<%=basePath%>js/distpicker.js"></script>

<!-- <script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script> -->
<!-- 新版本 -->
<script type="text/javascript" src="<%=basePath%>lib/assets/js/amazeui.min.js"></script>


<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script> 


<script>

	// ----------------------------请添加发起人---------------------------/------------------


	// 请添加发起人_搜索功能
	$('#search').click(function(){

		var searchVal = $('#searInput').val();
		if(searchVal.length > 10){
			layer.alert("搜索只写姓名", {icon: 7});
			return false
		}
		$('#searchSelect').show();

		$.ajax({
			type:'post',
			dataType:'json',
			async: true,
			data:{
				"username": $('#searInput').val(),
				"isdirect1": '1'
			},
			url : '<%=basePath%>/user/searchUser?curSec='+Math.random(),
			success:function(data){
				var data = data.data;
				if(data.length == 0) {
					layer.alert("无此人请重新输入", {icon: 7});
				}
				
				var html = '';
				for(var i=0; i<data.length; i++){
					html += '<div branchid="'+data[i].branchid+'"  username="'+data[i].username+'" userid="'+data[i].userid+'" familyid="'+data[i].familyid+'" >'+data[i].username+' '+data[i].genlevel+'世'+' '+data[i].address+'</div>'
				}
				$('#searchSelect').html(html)

				var SelectDiv = $('#searchSelect div')
				for(var i=0; i<SelectDiv.length; i++){
					SelectDiv[i].index = i
					// 加背景色
					SelectDiv[i].onmouseover = function () {
						for(var j=0; j<SelectDiv.length; j++){
							SelectDiv[j].className = ''
						}
						this.className = 'activeBlue'
					}
					SelectDiv[i].onclick = function () {

						$('#branchid').val($(this).attr('branchid'))
						$('#beginname').val($(this).attr('username'))
						$('#beginuserid').val($(this).attr('userid'))
						$('#familyid').val($(this).attr('familyid'))


						checkBeginer($(this).attr('userid'))
					// 给input搜索框赋值 branchid
					$('#searInput').val($(this).html())
						$('#searchSelect').hide();
					}
				}
			},
			error:function(e) {
				console.log(e);
			}	
		})
	})

	// ----------------------------请添加发起人---------------------------\------------------


	// 获取“添加发起人”_数据
	/* var data = JSON.parse(localStorage.getItem("website1"))
	var optionStr = "<option value=''>---- 请选择 ----</option>";
	var genlevel = "";
	// 初始显示数据条数
	var forNum = 7;
	// 添加发起人_数据添加
	function seleFun(forNum) {
			for(var i = 0; i < forNum; i++){
			if( data[i].genlevel == undefined){
				genlevel = '未知';
			}else{
				genlevel = data[i].genlevel;
			}
			optionStr+='<option familyid="'+data[i].familyid+'" beginname="'+data[i].username+'" parentid="'+data[i].branchid+'" value="' + data[i].userid + '">'
			+data[i].username+' '+ data[i].phone + ' ' + data[i].genlevel+'世 ';
			if(data[i].address){
				optionStr += " "+data[i].address;
			}
			optionStr += "</option>";
			}
			$('.my-select').html(optionStr);
			var dataval = $('#beginuserid').attr('data-val');

			if(dataval != ''){
			$('#beginuserid').val(dataval);
			}
	} */
	// seleFun(forNum)
	// 添加发起人_滑滚动条增加数据
	function checkscroll() {　
		forNum += 1;
		if(data.length > forNum) {
			seleFun(forNum)
		}
	}

var sameBranchFlag=false;
function save(){
	//校验分支名称
	if(sameBranchFlag){
		layer.alert("分支名称重复请重设", {icon: 7});
		return false;
	}

	if($("#form-branch-add").valid()){
		console.log($("#form-branch-add").serialize())
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:$("#form-branch-add").serialize(),
			url : '<%=basePath%>branch/save?curSec='+Math.random(),
			success:function(data){
				if(data == '1'){
					window.parent.searchs();
					window.parent.layer.msg('保存成功！', {icon: 6,time:1000});
					layer_close();
				}if(data == '-1'){
					window.parent.layer.msg('当前版本不能创建更多分支！请升级家族会员！',{icon:1,time:3000});
				}else{
					window.parent.layer.msg('保存失败！',{icon:1,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	}
}

$("#area").change(function(){
  $("#areacode").val($("#area option:selected").attr("data-code"));
});

$("#cityname").change(function(){
  $("#citycode").val($("#cityname option:selected").attr("data-code"));
});

$("#xname").change(function(){
  $("#xcode").val($("#xname option:selected").attr("data-code"));
});

$("#beginuserid").change(function(){
	//增加对发起人的提示校验
	var dataUserid=$("#beginuserid option:selected").val();
	// 判断dataUserid有无值
	if(dataUserid.length > 1) {
	/* 	checkBeginer(dataUserid); */
	}


  $("#beginname").val($("#beginuserid option:selected").attr("beginname"));
//   $("#parentid").val($("#beginuserid option:selected").attr("parentid"));
  $("#familyid").val($("#beginuserid option:selected").attr("familyid"));
});

//分支名称输入栏绑定失去焦点事件校验重复

/* $("#branchname").blur(function(){
	//校验分支名称
	var checkBranchName=$("input[name='branchname']").val();
	var rt=validateBranchname(checkBranchName);
	console.warn(rt);
	if(!rt){
		sameBranchFlag=true;
	}
});
 */

function checkBeginer(arg0){
	$.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:"beginuserid="+arg0,
		url : '<%=basePath%>branch/checkBeginer?curSec='+Math.random(),
		success:function(data,status){
			if(data=='false'){
				 layer.alert("此用户已经发起过其他分支", {icon: 7});
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
}

function validateBranchname(arg0){
	var flag =true;
	$.ajax({
		type:'post',
		dataType:'text',
		async: false,
		data:"branchname="+arg0,
		url : '<%=basePath%>branch/validateBranchname?curSec='+Math.random(),
		success:function(data,status){
			if(data=='false'){
				 flag =false;
				 layer.alert("分支名称重复请重设", {icon: 7});
				 
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
	sameBranchFlag=false;
	return flag;
}


$(function() {
	$("#form-branch-add").validate({
		rules:{
			branchname:{
				required:true,
				minlength:2,
				maxlength:50
			},
			area:{
				required:true,
			},
			cityname:{
				required:true,
			},
			xname:{
				required:true,
			},
			beginuserid:{
				required:true,				
			}
			
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
		}
		
	}); 
});
</script>
<style>
	#searchSelectBox {
		position: relative;
	}
	#searchSelect {
		width: 80%;
		min-height: 25px;
		margin-top: 10px;
		overflow-y: auto;
		border: 1px solid #ddd;
	  position: absolute;
		background: #fff;
		z-index: 100;
		max-height: 180px;
		display: none;
	}
	#searchSelect div {
		cursor: pointer;
	}
	.activeBlue {
		background: #5a98de;
	}

</style>



