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
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="usermate-add">
	     <input type="hidden" value="${user.genlevel}" name="genlevel">
	     <input type="hidden" value="${user.userid}" name="userid">
	     <input type="hidden" value="${user.mateid}" name="mateid">
	     <input type="hidden" value="${user.username}" name="usernameBefore" id="usernameBefore">
		 <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>配偶类型：</label>
			<div class="formControls col-xs-8 col-sm-6" id="inputradio">
				 <c:if test="${empty user.mateid}">
				     <c:if test="${user.sex == 0 }">
				     <input type="radio" value="0" name="matetype">
					 <label for="sex-1">丈夫</label>
				     </c:if>
				     <c:if test="${user.sex == 1 }">
				     <input type="radio"  value="1" name="matetype">
					 <label for="sex-1">妻子</label>
				     </c:if>
			     </c:if>
				 <input type="radio"  value="2" name="matetype" id="typeOther">
				 <label for="sex-1" id="typeOther1">其他</label>
					<select hidden disabled style="width:100px;" id="matetypeid" name="matetypeStr" class="loadoption  select" size="1" data="mateConstant.matetypeStr" >
					</select>	
			</div>
	    </div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
			<div class="formControls col-xs-8 col-sm-6">
				<input type="text" class="input-text"  name="username">
			</div>
	    </div>
	    <div class="row cl">
				<div class="fl">
				     <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>在世否：</label>
			         <input class="vm mtb-0 ml-15" type="radio" id="livestatus" value="0" name="livestatus" onclick="hideDP()" checked>
					 <label for="sex-1" class="vm mtb-0">在世</label>
					 <input class="vm mtb-0" type="radio" id="livestatus" value="1" name="livestatus"  onclick="showDP()" >
					 <label for="sex-1" class="vm mtb-0">离世</label>
				</div>
		 </div>
				 <div style="display:none" id="die-date" class="row cl">
				     <label class="form-label col-xs-4 col-sm-3" style="line-height:31px;">离世日期：</label>
				     <div class="formControls col-xs-8 col-sm-6">
					      <input type="text" name="dietimeStr" value="${user.dietimeStr}" placeholder="点击选择日期" onfocus="WdatePicker({maxDate:'\'%y-%M-%d\'}'})" id="dietimeStr" class="input-text Wdate">
				 	</div>
				  </div>	
				<div style="display:none" id="die-place" class="row cl">	    
				 	<label class="form-label col-xs-4 col-sm-3" style="line-height:31px;">安葬地：</label>
				 	 <div class="formControls col-xs-8 col-sm-6">
					     <input type="text" class="input-text" value="${user.fixplace}" name="fixplace">
				 	</div>
				 </div>
	    <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
			<div class="formControls col-xs-8 col-sm-6">
				<span class="select-box">
					<select class="select" size="1" name="sex" id="sex" disabled>
						<option value="-1">请选择性别</option>
						<option value="1">男</option>
						<option value="0">女</option>
					</select>
				</span>
			</div>
	    </div>
	    <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>手机号：</label>
			<div class="formControls col-xs-8 col-sm-6">
				<input type="text" class="input-text"  name="phone" id="tel">
			</div>
	    </div>
	    <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"></span>生日：</label>
			<div class="formControls col-xs-8 col-sm-6">
				<input type="text" name="birthdayStr" placeholder="点击选择日期" onfocus="WdatePicker({maxDate:'\'%y-%M-%d\'}'})" id="" class="input-text Wdate">
			</div>
	    </div>
	     <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">备注：</label>
			<div class="formControls col-xs-8 col-sm-6">
			    <textarea name="remark" cols="" rows=""  class="textarea"  placeholder="个人简介" onKeyUp="$.Huitextarealength(this,1000)"></textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/1000</p>
			</div>
	    </div>
	    <div class="row cl"  id="inputNation">
			<label class="form-label col-xs-4 col-sm-3">民族：</label>
			<div class="formControls col-xs-8 col-sm-6" >
            <span class="select-box">
				<select id="nationSel"  class="loadoption  select" size="1" data="nationConstant.nation" onchange="ifOther();">
				</select>
			</span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">出生地：</label>
			<div class="formControls col-xs-8 col-sm-6" data-toggle="distpicker">
	            <select id="barea" size="1" class="select" data-province="" style="height:30px;width:30%;"></select>
			    <select id="bcityname" size="1" class="select" data-city=""  style="height:30px;width:30%;"></select>
			    <select id="bxname" class="select" size="1"  data-district=""  style="height:30px;width:30%;"></select>
		   </div>
		</div>
	   <!--  <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>生日：</label>
			<div class="formControls col-xs-8 col-sm-6">
				<input type="text" name="birthdayStr" placeholder="点击选择日期" onfocus="WdatePicker({maxDate:'\'%y-%M-%d\'}'})" id="" class="input-text Wdate">
			</div>
	    </div> -->
	    
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="button" onclick="save();" value="&nbsp;&nbsp;确认&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
			</div>
	    </div>
	</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/distpicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript">
	$(function(){
		getOption();
		var sex = '${user.sex}';
		var matesxe;
		if(sex == 1){
			matesxe = 0;
		}else{
			matesxe = 1;
		}
		$("#sex").val(matesxe);
		
		//判断是否已有配偶,
		var mateflag=${empty user.mateid};
		if(!mateflag){
			$("#typeOther").attr('checked','true');
			$("#typeOther").hide();
			$("#typeOther1").hide();
			$("#matetypeid").show();
			$("#matetypeid").attr("disabled",false);
			$("#tel").attr("disabled",true);
			$("#tel").parent().parent().hide();
			$("#livestatus").parent().parent().hide();
			//其他类型不对手机进行校验
			$("#usermate-add").validate({
				rules:{
					username:{
						required:true,
						minlength:2,
						maxlength:50
					},
				},
				messages: {
					"username": {
						required: "填写用户姓名",
						minlength:"名称最小长度为2",
					},
		        },
				onkeyup:false,
				focusCleanup:true,
				success:"valid",
				submitHandler:function(form){
				}
			}); 
		}else{
			//带手机参数校验
			$("#usermate-add").validate({
				rules:{
					username:{
						required:true,
						minlength:2,
						maxlength:50
					},
					phone:{
						required:true,
						isMobile:true,
						remote:{   
		                    url : "<%=basePath%>user/validatePhone", //后台处理程序     
		                    type: "post",  //数据发送方式   
		                    async:false,
		                    dataType: "json",       //接受数据格式       
		                    data:  {                     //要传递的数据   
		                       	userid: function() {   
		                        	return $("#userid").val();   
		                        },
		                        phone: function() {   
		                        	return $("#tel").val();   
		                        } 
		                   	}   
		
		                 }  
					},
				},
				messages: {
					"phone": {
						required: "填写手机号",
						isMobile:"请填写正确的手机号",
						remote: "手机号重复",
					},
					"username": {
						required: "填写用户姓名",
					},
		        },
				onkeyup:false,
				focusCleanup:true,
				success:"valid",
				submitHandler:function(form){
				}
			}); 
		}
		//配偶类型绑定点击事件
		$("input[name='matetype']").click( function () { 
			var ckData=$('input[name="matetype"]:checked').val();
			if(ckData==2){
				$("#matetypeid").show();
				$("#matetypeid").attr("disabled",false);
				$("#tel").attr("disabled",true);
				$("#tel").parent().parent().hide();
				$("#livestatus").parent().parent().hide();
			}else{
				$("#matetypeid").hide();
				$("#matetypeid").attr("disabled",true);
				$("#tel").attr("disabled",false);
				$("#tel").parent().parent().show();
				$("#livestatus").parent().parent().show();
			}
		});
	});
	//动态民族其他选项输入框
	function ifOther(){
		 var nationSel = $("#nationSel").val();
		 if(nationSel == "其他"){
			 var div = $("div[id='inputNation']");
			 var appendInput  = '<div class="row cl">';
			 	 appendInput += "<label id='nationInput' class='form-label col-xs-4 col-sm-3'></span>其他民族：</label>";
				 appendInput+= "<div id ='nationInputDiv' class='formControls col-xs-8 col-sm-6'>";
				 appendInput+= "<input class='input-text w200' type='text' id='nationInputVal'>";
				 appendInput+= "</div>";
				 appendInput+= "</div>";
			     div.after(appendInput);
		 }else{
			 var nationInput = $("#nationInput")
			 var nationInputDiv = $("div[id='nationInputDiv']");
			 if(nationInput != undefined){
				 nationInput.remove();
				 nationInputDiv.remove();
			 }
		 }
	 }
	
	//显示隐藏在世离世信息
	function hideDP(){
		 document.getElementById('die-date').style.display='none';
		 document.getElementById('die-place').style.display='none';
		$("#tel").attr("disabled",false);
		$("#tel").parent().parent().show();

	}
	function showDP(){
		document.getElementById('die-date').style.display='block';
		document.getElementById('die-place').style.display='block';
		$("#tel").attr("disabled",true);
		$("#tel").parent().parent().hide();
	}
	function save(){
		if($("#usermate-add").valid()){
		//var mateType = $("#matetype").val();
		var mateType = $('#inputradio input[name="matetype"]:checked ').val();
		if(mateType == undefined){
			//alert("请选择配偶类型");
			layer.alert('请选择配偶类型', {icon: 7});
			return false;
		}
		var birArea = "";
		birArea = $("#barea").val()+"@@"+$("#bcityname").val()+"@@"+$("#bxname").val();
		var sex = $("#sex").val();
		var usernameBefore = $("#usernameBefore").val();
		//民族参数
		var nation = "";
		var nationInput =  $("#nationInputVal").val();
		if(nationInput != undefined){
			nation = nationInput;
		}else{
			nation = $("#nationSel").val();
		}
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:$("#usermate-add").serialize()+"&birthplace="+birArea+"&sex="+sex+"&nation="+nation,
			url : '<%=basePath%>user/mergeMate?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					window.parent.layer.msg('保存成功!', {icon: 6,time:1000});
					window.parent.searchs();
					layer_close();
				}else if(data == '2'){
					//alert("保存失败,用户数量超过版本最大用户数量限制");
					window.parent.layer.msg('保存失败,用户数量超过版本最大用户数量限制成功!', {icon: 5,time:1000});
				}else if(data == '500'){
					window.parent.layer.msg('数据库有重复,保存失败!',{icon:5,time:2000});
				}else{
					//alert("保存失败");
					window.parent.layer.msg('保存失败', {icon: 5,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
		};
	}   
</script>
