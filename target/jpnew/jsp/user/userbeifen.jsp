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
<script type="text/javascript" src="<%=basePath%>js/distpicker.js"></script>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="user-add">
	    <input type="hidden" value="${user.userid }" id="userid" name="userid">
		<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-1"><span class="c-red">*</span>姓名：</label>
				<div class="formControls col-xs-8 col-sm-2">
					<input name="username" id="username" type="text" class="input-text" value="${user.username }" />
				</div>
			<label class="form-label col-xs-4 col-sm-1">性别：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<span class="select-box">
					<select class="select" size="1" name="sex" id="sex" value="${user.sex }" onchange="getOptionBrotherpos();">
						<option value="-1">请选择性别</option>
						<option value="1">男</option>
						<option value="0">女</option>
					</select>
				</span>
			</div>
		 	<label class="form-label col-xs-4 col-sm-1">民族：</label>
			<div class="formControls col-xs-8 col-sm-2">
			    <span class="select-box">
			        <!-- 
			         <select class="select" id="nationSel" size="1" name="nation" value="${userInfo.nation }" onchange="ifOther();">
					</select>
					-->
					<select id="nationSel" class="loadoption  select" size="1" data="nationConstant.nation" data-val="${userInfo.nation }"  onchange="ifOther();">
					</select>
				</span>
			</div>
			<!-- <label class="form-label col-xs-4 col-sm-1">生日：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<input type="text" name="111" placeholder="生日" onfocus="WdatePicker({maxDate:'\'%y-%M-%d\'}'})" id="" class="input-text Wdate">
			</div> -->
		 	
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-1">曾用名：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<input type="text" class="input-text"  value="${user.usedname }" placeholder="" id="" name="usedname">
			</div>
			<label class="form-label col-xs-4 col-sm-1">政治背景：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<span class="select-box">
				    <!-- <select class="select" size="1" value="${userinfo.background}" name="background">
						<option value="-1" selected>请选择政治背景</option>
						<option value="0">群众</option>
						<option value="1">团员</option>
						<option value="2">中共党员</option>
					</select> -->
					<select class="loadoption   select" size="1" data="backgroundConstant.background" data-val="${userinfo.background}" name="background" id="background">
				    </select>
				</span>
			</div>
			<label class="form-label col-xs-4 col-sm-1">排行：</label>
			<div class="formControls col-xs-8 col-sm-5">
			   <span class="select-box">
			     <!-- <input type="text" class="input-text" value="${user.brotherpos}" placeholder="" id="" name="brotherpos"> -->
			     <select id="brotherposSel" class="loadoption   select" size="1" data="brotherposConstant" data-val="${user.brotherpos}"  name="brotherpos">
				 </select>
			   </span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-1">隶属分支：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<span class="select-box">
					<select class="select" size="1" name="branchid" id="branchid" value="${user.branchid}">
					      <option value="">请选择分支</option>
					    <c:forEach var="branch" items="${branchList}" varStatus="status">
						  <option value="${branch.branchid}">${branch.branchname}</option>
						</c:forEach>
					</select>
				</span>
			</div>
			<label class="form-label col-xs-4 col-sm-1">身份证号：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text" value="${user.idcard}"  id="" name="idcard">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-1">手机号：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<input type="text" class="input-text" value="${userInfo.tel}" id="tel" name="tel">
			</div>
			<label class="form-label col-xs-4 col-sm-1">微信号：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<input type="text" class="input-text" value="${userInfo.weixin}" id="" name="weixin">
			</div>
			<label class="form-label col-xs-4 col-sm-1">QQ号：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<input type="text" class="input-text" value="${userInfo.qq}" id="" name="qq">
			</div>
			<label class="form-label col-xs-4 col-sm-1">邮箱：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<input type="text" class="input-text" value="${userInfo.mail}" placeholder="" id="" name="mail">
			</div>
		</div>
		<div class="row cl" >
		    <label class="form-label col-xs-4 col-sm-1">生日：</label>
			<div class="formControls col-xs-8 col-sm-2">
				<input type="text" name="birthdayStr" value="${userInfo.birthdayStr }" placeholder="点击选择日期" onfocus="WdatePicker({maxDate:'\'%y-%M-%d\'}'})" id="" class="input-text Wdate">
			</div>
			<label class="form-label col-xs-4 col-sm-1">父亲：</label>
			<div class="formControls col-xs-8 col-sm-2" id="addGenlevel">
			    <!-- <input type="text" class="input-text" value="${user.pname}" placeholder="" id="" name="pname"> -->
				<select id="pname" class="my-select select" >
		        </select>
			</div>
			<!-- 暂无此字段 -->
			<!-- <label class="form-label col-xs-4 col-sm-1">母亲：</label>
			<div class="formControls col-xs-8 col-sm-3">
				<input type="text" class="input-text"  id="" name="">
			</div> -->
			
			<label class="form-label col-xs-4 col-sm-1">配偶：</label>
			<div class="formControls col-xs-8 col-sm-2">
			    <!--<input type="text" class="input-text" value="${user.matename}" placeholder="" id="" name="matename">  -->
			    <select name="beginuserid" id="matename" class="my-select-mate select" >
		        </select>
			</div>
		</div>
		<div class="row cl">
		   <!--  
			<label class="form-label col-xs-4 col-sm-1">出生地：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<span class="select-box">
					<select class="select" size="1" name="birthplace" value="${userInfo.birthplace}">
						<option value="0">鄂尔多斯市东胜分支</option>
						<option value="1">鄂尔多斯市乌审旗分支</option>
					</select>
				</span>
			</div>
			-->
			<div data-toggle="distpicker">
			        <label class="form-label col-xs-4 col-sm-1">出生地：</label>
					<div class="formControls col-xs-8 col-sm-3">
						<select id="barea" size="1" class="select" data-province="${userInfo.birthplaceP}"></select>
					</div>
					<div class="formControls col-xs-8 col-sm-3">
						 <select id="bcityname" size="1" class="select" data-city="${userInfo.birthplaceC}"></select>
					</div>
					<div class="formControls col-xs-8 col-sm-3">
						<select id="bxname" class="select" size="1" data-district="${userInfo.birthplaceX}"></select>
					</div>
			</div>
		</div>
		<div class="row cl">
			<div data-toggle="distpicker">
			        <label class="form-label col-xs-4 col-sm-1">常住地：</label>
					<div class="formControls col-xs-8 col-sm-3">
						<select id="harea" size="1" class="select" data-province="${userInfo.homeplaceP}"></select>
					</div>
					<div class="formControls col-xs-8 col-sm-3">
						 <select id="hcityname" size="1" class="select" data-city="${userInfo.homeplaceC}"></select>
					</div>
					<div class="formControls col-xs-8 col-sm-3">
						<select  id="hxname" class="select" size="1" data-district="${userInfo.homeplaceX}"></select>
					</div>
			</div>
		</div>
		<div class="row cl">
		    <label class="form-label col-xs-4 col-sm-1">是否在世：</label>
		    <div class="formControls col-xs-8 col-sm-2">
				 <input type="radio" id="livestatus" value="0" name="livestatus" <c:if test="${user.livestatus == 0}">checked</c:if>>
				 <label for="sex-1">离世</label>
				 <input type="radio" id="livestatus" value="1" name="livestatus" <c:if test="${user.livestatus == 1}">checked</c:if>>
				 <label for="sex-1">在世</label>
			</div>
			<label class="form-label col-xs-4 col-sm-1">坟墓所在地：</label>
			<div class="formControls col-xs-8 col-sm-3">
				<input type="text" class="input-text" value="${user.fixplace}" name="fixplace">
			</div>
		</div>
		<!--<div class="row cl">
			<label class="form-label col-xs-4 col-sm-1">从事行业：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text" value="${userEdu.major}"  id="" name="major">
			</div>
			<label class="form-label col-xs-4 col-sm-1">毕业院校：</label>
			<div class="formControls col-xs-8 col-sm-5">
				<input type="text" class="input-text" value="${userEdu.university}"  id="" name="university">
			</div>
		</div> -->
		<div class="row cl" id="workExp">
		    <label class="form-label col-xs-4 col-sm-1">工作经历：</label>
			<a class="btn btn-primary radius" href="javascript:;" onclick="addWorkExp();"><i class="Hui-iconfont">&#xe600;</i>增加</a>
		</div>
		<c:forEach var="workList" items="${workList}" varStatus="status">
		   <div class='row cl' id="workExp${status.index + 1 }">
		           <label class="form-label col-xs-4 col-sm-1">工作经历：</label>
		           <div class="formControls col-xs-8 col-sm-2">
		    	          <input type="text" class="input-text" value="${workList.company }" id="company${status.index + 1 }" name="">
		            </div>
		           <div class="formControls col-xs-8 col-sm-2">
		             <input type="text" class="input-text" value="${workList.position }" id="position${status.index + 1 }" name="">
		           </div>
		           <a class="ml-5" href="javascript:;" onclick="removeWorkExp(this);" style="text-decoration:none;color:#5a98de">删除</a>
		   </div>
		</c:forEach>
		<div class="row cl" id="eduExp">
		    <label class="form-label col-xs-4 col-sm-1">教育经历：</label>
			<a class="btn btn-primary radius" href="javascript:;" onclick="addEduExp();"><i class="Hui-iconfont">&#xe600;</i>增加</a>
		</div>
		<c:forEach var="eduList" items="${eduList}" varStatus="status">
		   <div class='row cl' id="workExp${status.index + 1 }">
		           <label class="form-label col-xs-4 col-sm-1">教育经历：</label>
		           <div class="formControls col-xs-8 col-sm-2">
		    	          <input type="text" class="input-text" value="${eduList.university }" id="university${status.index + 1 }" name="">
		            </div>
		           <div class="formControls col-xs-8 col-sm-2">
		             <input type="text" class="input-text" value="${eduList.major }" id="major${status.index + 1 }" name="">
		           </div>
		           <a class="ml-5" href="javascript:;" onclick="removeEduExp(this);" style="text-decoration:none;color:#5a98de">删除</a>
		   </div>
		</c:forEach>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-1">个人简介：</label>
			<div class="formControls col-xs-8 col-sm-11">
				<textarea name="remark" cols="" rows=""  class="textarea"  placeholder="个人简介" onKeyUp="textarealength(this,1000)">${userInfo.remark }</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/1000</p>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="button" onclick="save();" value="&nbsp;&nbsp;确认&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
			</div>
	    </div>
	</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>jsp/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>
<script type="text/javascript">
	$(function(){
		var sex = '${user.sex}';
		$("#sex").val(sex);
		getOption();
		var nation = '${userInfo.nation}';
		$("#nationSel").val(nation);
		if($("#nationSel").val() == "-1"){
			 var div = $("div[id='addNation']");
			 var appendInput = "<label id='nationInput' class='form-label col-xs-4 col-sm-1'></span>民族：</label>";
				 appendInput+= "<div id ='nationInputDiv' class='formControls col-xs-8 col-sm-2'>";
				 appendInput+= "<input class='input-text' value = '${userInfo.nation}' type='text' id='nationInputVal'>";
				 appendInput+= "</div>";
			     div.append(appendInput);
		}
		//世系回显
		var genlevel = '${user.pid}';
		if(genlevel == ""){
			$("#addGenlevel").val("-1");
			var div = $("div[id='addGenlevel']");
			var appendShiXiInput = "<label id='appendShiXiInput' class='form-label col-xs-4 col-sm-1'></span>世系：</label>";
			appendShiXiInput+= "<div id ='appendShiXiInput' class='formControls col-xs-8 col-sm-2'>";
			appendShiXiInput+= "<input class='input-text' type='text' value='${user.genlevel}' id='ShiXiInputVal'>";
			appendShiXiInput+= "</div>";
			div.after(appendShiXiInput);
		}else{
			$("#addGenlevel").val(genlevel);
		}
		var branchid = '${user.branchid}';
		$("#branchid").val(branchid);
		var background = '${userInfo.background}';
		$("#background").val(background);
		//初始化父亲 配偶
		$.ajax({
			type:'post',
			dataType:'json',
			async: false,
			url : '<%=basePath%>user/selectPnameAndMate?curSec='+Math.random(),
			success:function(data,status){
				if(data){
					var optionStrP = "<option value='-1'>---- 请选择 ----</option>";
					var optionStrM = "<option value=''>---- 请选择 ----</option>";
					for(var i = 0; i < data.length; i++){
						if(data[i].isdirect == 1){
							optionStrP += "<option value=" + data[i].userid + ">"+data[i].genlevel+"世"+data[i].username+"</option>";
						}else{
							optionStrM += "<option value=" + data[i].userid + ">"+data[i].genlevel+"世"+data[i].username+"</option>";
						}
					}
					$('.my-select').html(optionStrP);
					$('.my-select-mate').html(optionStrM);
				}else{
					alert("初始化人员失败！");
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
		
		$('.my-select').chosen({
	    	search_contains: true,
	      	max_selected_options: 1,
	      	no_results_text: "没有找到",
	        });
		$('.my-select-mate').chosen({
	    	search_contains: true,
	      	max_selected_options: 1,
	      	no_results_text: "没有找到",
	    });
	});
	//当父亲没有选择值时，手动输入排多少世
	$("#pname").on('change', function(e, params) {
			var pname = $("#pname").val();
			if(pname == "-1"){
				var div = $("div[id='addGenlevel']");
				var appendShiXiInput = "<label id='appendShiXiInput' class='form-label col-xs-4 col-sm-1'></span>世系：</label>";
				appendShiXiInput+= "<div id ='appendShiXiInput' class='formControls col-xs-8 col-sm-2'>";
				appendShiXiInput+= "<input class='input-text' type='text' id='ShiXiInputVal'>";
				appendShiXiInput+= "</div>";
				div.after(appendShiXiInput);
			}else{
				 var shixiInput = $("#appendShiXiInput")
				 var shixiInputDiv = $("div[id='appendShiXiInput']");
				 if(shixiInput != undefined){
					 shixiInput.remove();
					 shixiInputDiv.remove();
				 }
			}
	});
	function save(){
		var sex = $("#sex").val();
		var tel = $("#tel").val();
		var homeplace = $("#homeplace").val();
		var username = $("#username").val();
		if(sex == ""){
			alert("请选择性别");
			return false;
		}
		if(tel == ""){
			alert("请填写手机号");
			return false;
		}
        if(homeplace == ""){
        	alert("请选择常住地");
        	return false;
		}
        if(username == ""){
        	alert("请填写姓名");
        	return false;
		}
		
		//计算教育经历行数
		var eduExpArray = "";
		for(var i = 1; i <= 4; i++){
	        if($("#university"+i).val() != null && $("#university"+i).val() != undefined && $("#major"+i).val() != null && $("#major"+i).val() != undefined){
	          if($("#university"+i).val() == "" || $("#major"+i).val() == ""){
	        	  alert("教育经历有空值，请填完整");
	        	  return;
	          }else{
	        	  var eduExp = $("#university"+i).val()+"_"+$("#major"+i).val();
	              eduExpArray += eduExp+"@@";
	          }
	          
	        }
	      }
		//计算够工作经历
		var workExpArray = "";
		for(var i = 1; i <= 4; i++){
	        if($("#company"+i).val() != null && $("#company"+i).val() != undefined && $("#position"+i).val() != null && $("#position"+i).val() != undefined){
	          if($("#company"+i).val() == "" || $("#position"+i).val() == ""){
	        	  alert("工作经历有空值，请填完整");
	        	  return;
	          }else{
	        	  var workExp = $("#company"+i).val()+"_"+$("#position"+i).val();
	              workExpArray += workExp+"@@";
	          }
	        }
	     }
		var nation = "";
		var nationInput =  $("#nationInputVal").val();
		if(nationInput != undefined){
			nation = nationInput;
		}else{
			nation = $("#nationSel").val();
		}
		var parentName = "";
		var genlevelInput =  $("#ShiXiInputVal").val();
		if(genlevelInput != undefined){
			if(genlevelInput == ""){
				alert("请填写世系");
				return false;
			}else{
				parentName = genlevelInput;
			}
		}else{
			parentName = $("#pname").val();
		}
		var fatherName = $("#pname").val();
		//出生地省市县  barea  bcityname  bxname 
		var birArea = "";
		birArea = $("#barea").val()+"@@"+$("#bcityname").val()+"@@"+$("#bxname").val();
		//常住地 省市县 harea hcityname hxname 
		var homArea = "";
		homArea = $("#harea").val()+"@@"+$("#hcityname").val()+"@@"+$("#hxname").val();
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:$("#user-add").serialize()+"&eduExpArray="+eduExpArray+"&workExpArray="+workExpArray+"&nation="+nation
			     +"&birthplace="+birArea+"&homeplace="+homArea+"&parentName="+parentName+"&fatherName="+fatherName,
			     //"eduExpArray":eduExpArray,
				 //"workExpArray":workExpArray
			url : '<%=basePath%>user/merge?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					alert("保存成功");
					layer_close();
				}else{
					alert("保存失败");
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	}   
    //增加教育经历
	function addEduExp(){
		var length = "";
		for(var i = 1; i <= 4; i++){
		   var a = $("div[id='eduExp"+i+"']").length;
		    if(a == 0){
		    	length = i;
		    	break;
		    }
		}
		var idLength = length;
		var divLast = "";
		if(idLength == 1){
			divLast = $("div[id='eduExp']");
		}else{
		    divLast = $("div[id='eduExp"+(idLength-1)+"']");
		}
		var appendDiv =     "<div class='row cl' id='eduExp"+(idLength)+"'>";
		    appendDiv+=        "<label class='form-label col-xs-4 col-sm-1'>教育经历：</label>";
		    appendDiv+=        "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=	          "<input type='text' class='input-text' value='' placeholder='毕业院校' id='university"+(idLength)+"'>";
		    appendDiv+=        "</div>" ;
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=           "<input type='text' class='input-text' value='' placeholder='所学专业' id='major"+(idLength)+"' name=''>";
		    appendDiv+=         "</div>";
		    appendDiv+=         "<a class='ml-5' href='javascript:;' onclick='removeEduExp(this);' style='text-decoration:none;color:#5a98de'>删除</a>";
		    appendDiv+=     "</div>";         
		    divLast.after(appendDiv);
	} 
	 //删除教育经历
	function removeEduExp(obj){
		$(obj).parent().remove();
	}
	//增加工作经历
	function addWorkExp(){
		var length = "";
		for(var i = 1; i <= 4; i++){
		   var a = $("div[id='workExp"+i+"']").length;
		    if(a == 0){
		    	length = i;
		    	break;
		    }
		}
		var idLength = length;
		var divLast = "";
		if(idLength == 1){
			divLast = $("div[id='workExp']");
		}else{
			divLast = $("div[id='workExp"+(idLength-1)+"']");
		}
		var appendDiv =     "<div class='row cl' id='workExp"+(idLength)+"'>";
		    appendDiv+=        "<label class='form-label col-xs-4 col-sm-1'>工作经历：</label>";
		    appendDiv+=        "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=	          "<input type='text' class='input-text' value='' placeholder='请输入工作单位' id='company"+(idLength)+"' name=''>";
		    appendDiv+=        "</div>" ;
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=           "<input type='text' class='input-text' value='' placeholder='请输入职位' id='position"+(idLength)+"' name=''>";
		    appendDiv+=         "</div>";
		    /**
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=         "<input type='text' placeholder='开始时间' onfocus='WdatePicker({maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}'})' id='datemin' class='input-text Wdate'>";
		    appendDiv+=         "</div>";
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=         "<input type='text' placeholder='结束时间' onfocus='WdatePicker({maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}'})' id='datemin' class='input-text Wdate'>";
		    appendDiv+=         "</div>";
		    **/                 
		    appendDiv+=         "<a class='ml-5' href='javascript:;' onclick='removeWorkExp(this);' style='text-decoration:none;color:#5a98de'>删除</a>";
		    appendDiv+=     "</div>";         
		    divLast.after(appendDiv);
	} 
	 //删除工作经历
	function removeWorkExp(obj){
		$(obj).parent().remove();
	}
	 function ifOther(){
		 var nationSel = $("#nationSel").val();
		 if(nationSel == "其他"){
			 var div = $("div[id='addNation']");
			 var appendInput = "<label id='nationInput' class='form-label col-xs-4 col-sm-1'></span>民族：</label>";
				 appendInput+= "<div id ='nationInputDiv' class='formControls col-xs-8 col-sm-2'>";
				 appendInput+= "<input class='input-text' type='text' id='nationInputVal'>";
				 appendInput+= "</div>";
			     div.append(appendInput);
		 }else{
			 var nationInput = $("#nationInput")
			 var nationInputDiv = $("div[id='nationInputDiv']");
			 if(nationInput != undefined){
				 nationInput.remove();
				 nationInputDiv.remove();
			 }
		 }
	 }
</script>
