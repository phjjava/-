<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.jp.common.ConstantUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String chooseTab = request.getParameter("chooseTab") == null ? "" : request.getParameter("chooseTab");
	String uploadUrl = ConstantUtils.JIAPU_UPLOAD_URL;
	String downLoadUrl = ConstantUtils.JIAPU_DOWNLOAD_URL;
	String upAvatarUrl = ConstantUtils.JIAPU_AVATAR_UPLOAD_URL;
	String downAvatarUrl = ConstantUtils.JIAPU_AVATAR_DOWNLOAD_URL;
%>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<!-- <link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" /> -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/webuploader/0.1.5/webuploader.css"  />

<!-- 新版本 -->
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/assets/css/amazeui.min.css" />

<style>
.xuanxiang-tab{overflow:hidden;padding:0;margin:0;background-color:#f5fafe;border-bottom:2px solid #5a98de}
.xuanxiang-tab li{float:left; width:120px;height:35px;line-height:35px; text-align:center;font-size:16px;cursor:pointer}
.clearfix:after { visibility: hidden; display: block; font-size: 0; content: ""; clear: both; height: 0; }
.clearfix { *zoom: 1; }
.w200{width:200px;}
.fl{float:left;}
.w-auto{width:auto !important;}
.input-title{width:90px;text-align:right;line-height:31px;}
.vm{vertical-align: middle;}
.mtb-0{margin-top:0 !important;margin-bottom:0 !important;}
.pl-0{padding-left:0 !important;}
.mtb-8{margin:15px 0;}
.ml-15{margin-left:15px !important;}
#matename_chosen{line-height:31px;height:31px;}
.chosen-single{height:31px !important;line-height:31px !important;}
.ml-30{margin-left:30px;}
#inputNation{}
</style>
<article class="page-container">
    <ul class="xuanxiang-tab">
    	<li id="basic-title" onclick="chooseTab('basic');">基本资料</li>
    	<c:if test="${user.userid != null }">
	    	<li onclick="chooseTab('photo');" id="photo-title">个人相册</li>
	    	<li onclick="chooseTab('work');" id="work-title">个人作品</li>
    	</c:if>
    </ul>
	<form action="" method="post" class="form form-horizontal" id="user-add">
	   <div style="display:block;" id="basic">
	  <!-- 基本信息 -->
	  <div style="font-size:16px;border-bottom:6px solid #eeeeee;margin-top:15px;line-height:30px;color:#333333;font-weight:bold">基本信息</div>
	    <input type="hidden" value="${user.userid }" id="userid" name="userid">
	    <input type="hidden" value="${user.isdirect }" id="userDType" name="isdirect">
	    <input type="hidden" value="${user.branchname }" id="branchname" name="branchname">
	    <div id="addNation">
			<div class="clearfix mtb-8">
		        <label class="input-title fl" style="line-height:60px;">上传头像：</label> 
			    <div class="fl ml-15">
<%-- 			    	<img src="<%=basePath %>images/touxiang_03.png" style="cursor:pointer">
			    	<input type="hidden" value="${user.imgurl }" id="avatar" name="imgurl"> --%>
						<div class="col-sm-5">
							<div id="avatarPreId">
								<div>
									<img id="preImg"
										src="<%=basePath %>images/touxiang_03.png"
										height="60px" width="60px">
								</div>
							</div>
						</div>
	    	
			    </div>
	    	<div class="clearfix mtb-12">
	    				<div class="col-sm-2">
							<div id="avatarBtnId" 
								class="head-scu-btn upload-btn webuploader-container">
								<div class="webuploader-pick">
									<i class="fa fa-upload"></i>上传
								</div>
							</div>
						</div>
						<input id="avatar" value="${user.imgurl }" name="imgurl" type="hidden">		
	    	</div>
	    	<div class="clearfix mtb-8">
		    	<div class="fl">
					<label class="input-title"><span class="c-red">*</span>姓名：</label>
					<input name="username" id="username" type="text" class="input-text w200 ml-15" value="${user.username }" />
		    	</div>
		    	<div class="fl">
					<label class="input-title"><span class="c-red">*</span>性别：</label>
					<span class="select-box w-auto">
						<select class="select" size="1" name="sex" id="sex" onchange="getOptionBrotherpos();">
							<option value="-1">请选择性别</option>
							<option value="1">男</option>
							<option value="0">女</option>
						</select>
					</span>
		    	</div>
		    	<div class="fl clearfix">
					 <label class="input-title fl">民族：</label>
		    	
					<div id="inputNation" class="fl clearfix">
					    <span class="select-box w-auto fl">
					        <!-- 
					         <select class="select" id="nationSel" size="1" name="nation" value="${userInfo.nation }" onchange="ifOther();">
							</select>
							-->
							<select id="nationSel" class="loadoption  select" size="1" data="nationConstant.nation" data-val="${userInfo.nation }" odata-val="${userInfo.otherNation }" onchange="ifOther();">
							</select>
						</span>
					</div>
		    	</div>
	    	</div>
	    	
			<div class="clearfix mtb-8" style="height:40px;">
				<div class="fl">
				     <label class="input-title"><span class="c-red">*</span>在世否：</label>
			         <input class="vm mtb-0 ml-15" type="radio" id="livestatus" value="0" name="livestatus" onclick="hideDP()" <c:if test="${user.livestatus == 0}">checked</c:if>>
					 <label for="sex-1" class="vm mtb-0">在世</label>
					 <input class="vm mtb-0" type="radio" id="livestatus" value="1" name="livestatus"  onclick="showDP()" <c:if test="${user.livestatus == 1}" >checked</c:if>>
					 <label for="sex-1" class="vm mtb-0">离世</label>
				</div>
				 <div style="display:none" id="die-date">
				     <label class="input-title fl" style="line-height:31px;">离世日期：</label>
				     <div class="formControls col-xs-8 col-sm-2">
					      <input type="text" name="dietimeStr" value="${user.dietimeStr}" placeholder="点击选择日期" onfocus="WdatePicker({maxDate:'\'%y-%M-%d\'}'})" id="dietimeStr" class="input-text Wdate">
				     </div>
				     <label class="input-title fl" style="line-height:31px;">安葬地：</label>
				     <div class="formControls col-xs-8 col-sm-5">
					     <input type="text" class="input-text" value="${user.fixplace}" name="fixplace">
				     </div>
				 </div>
			</div>
			
			<div class="clearfix mtb-8">
				<label class="input-title"><span class="c-red">*</span>手机号：</label>
				<input type="text" class="input-text w200 ml-15" value="${user.phone}" id="tel" name="phone">
			</div>
			
			<div class="clearfix mtb-8">
				<label class="input-title">身份证号：</label>
				<input type="text" class="input-text w200 ml-15" value="${user.idcard}"  id="" name="idcard">
			</div>
			
			<div class="clearfix mtb-8">
				<label class="input-title fl" style="margin-top:10px;margin-right:19px;">政治背景：</label>
				<div class="fl w200" style="margin-top:10px;">
					<span class="select-box">
					    <!-- <select class="select" size="1" value="${userinfo.background}" name="background">
							<option value="-1" selected>请选择政治背景</option>
							<option value="0">群众</option>
							<option value="1">团员</option>
							<option value="2">中共党员</option>
						</select> -->
						<select class="loadoption select" size="1" data="backgroundConstant.background" data-val="${userinfo.background}" name="background" id="background">
					    </select>
					</span>
				</div>
			</div>
			
			<div class="clearfix mtb-8">
				<label class="input-title fl">父(母)亲：</label>
				<div class="formControls col-xs-8 col-sm-2" id="addGenlevel" style="padding-left:19px;">
				    <!-- <input type="text" class="input-text" value="${user.pname}" placeholder="" id="" name="pname"> -->
					<!-- <select id="pname" class="my-select select" >
					</select> -->
					<select 
						id="pname" 
						class="my-select select"  
						data-am-selected="{btnWidth: '100%', searchBox: 1}">
					</select>
				</div>
				<div id="shixi">
					<label class='form-label col-xs-4 col-sm-1'><span class='c-red'>*</span>世系：</label>
					<div id ='appendShiXiInput' class='formControls col-xs-8 col-sm-2'>
						<input class='input-text' type='text' value='${user.genlevel}' id='ShiXiInputVal'>
					</div>
				</div>
				
				
				<label class="input-title fl">排行：</label>
				<div class="formControls col-xs-8 col-sm-2">
				   <span class="select-box">
				     <!-- <input type="text" class="input-text" value="${user.brotherpos}" placeholder="" id="" name="brotherpos"> -->
				     <select id="brotherposSel" class="loadoption   select" size="1" data="brotherposConstant" data-val="${user.brotherpos}"  name="brotherpos">
					 </select>
				   </span>
				</div>
				
				<label class="input-title fl">是否亲生：</label>
				<div class="formControls col-xs-8 col-sm-2">
				    <!-- <input type="text" class="input-text" value="${user.pname}" placeholder="" id="" name="pname"> -->
					<select name="isdirectjj" class="loadoption   select" size="1" data="userConstant.isdirect" data-val="${user.isdirect}" style="height:30px;">
					    <option value="1">是</option>
						<option value="0">否</option>
					</select>
				</div>
			</div>
			
			<div class="clearfix mtb-8">
				<label class="input-title fl" style="line-height:36px;">隶属分支：</label>
				<div class="formControls col-xs-8 col-sm-5">
					<span class="select-box" style="border:none;">
						<!-- <select size="1" class="branch-select select" data-val="${user.branchid }" name="branchid" id="branchid">
						</select> -->
						<select 
							name="branchid"
							id="branchid" 
							class="branch-select select"
							data-val="${user.branchid }"
							data-am-selected="{btnWidth: '100%', searchBox: 1}">
						</select>

					</span>
				</div>
			</div>
			
			<div class="clearfix mtb-8">
				<label class="input-title">曾用名：</label>
				<input type="text" class="input-text w200 ml-15"  value="${user.usedname }" placeholder="" id="" name="usedname">
			</div>
			
			<div class="clearfix mtb-8">
				<label class="input-title">生日：</label>
				<input type="text" name="birthdayStr" value="${userInfo.birthdayStr }" placeholder="点击选择日期" onfocus="WdatePicker({maxDate:'\'%y-%M-%d\'}'})" id="" class="input-text Wdate w200 ml-15">
			</div>
			
			<div class="clearfix mtb-8">
				<div data-toggle="distpicker" class="clearfix fl">
			        <label class="input-title fl">出生地：</label>
					<div class="formControls fl" style="padding-left:19px;">
						<select id="barea" size="1" class="select" data-province="${userInfo.birthplaceP}" style="height:30px;width:33%;"></select>
						<select id="bcityname" size="1" class="select" data-city="${userInfo.birthplaceC}" style="height:30px;width:28%;"></select>
						<select id="bxname" class="select" size="1" data-district="${userInfo.birthplaceX}" style="height:30px;width:28%;"></select>
					</div>
				</div>
					<label class="form-label col-xs-4 col-sm-1">详细地址：</label>
					<div class="formControls col-xs-8 col-sm-5">
					     <input type="text" class="input-text" value="${userInfo.birthDetail}"  id="birthDetail" name="">
					</div>
			</div>
           
			<div class="clearfix mtb-8">
				<div data-toggle="distpicker" class="clearfix fl">
			        <label class="input-title fl">常住地：</label>
					<div class="formControls fl" style="padding-left:19px;">
						<select id="harea" size="1" class="select" data-province="${userInfo.homeplaceP}" style="height:30px;width:33%;"></select>
						<select id="hcityname" size="1" class="select" data-city="${userInfo.homeplaceC}" style="height:30px;width:28%;"></select>
						<select  id="hxname" class="select" size="1" data-district="${userInfo.homeplaceX}" style="height:30px;width:28%;"></select>
					</div>
				</div>
				<label class="form-label col-xs-4 col-sm-1">详细地址：</label>
				<div class="formControls col-xs-8 col-sm-5">
			      <input type="text" class="input-text" value="${userInfo.homeDetail}"  id="homeDetail" name="">
		        </div>
			</div>
			
			<div class="clearfix mtb-8">
				<label class="input-title fl">是否结婚：</label>
				<div class="formControls col-xs-8 col-sm-2" style="padding-left:19px;">
				    <!-- <input type="text" class="input-text" value="${user.pname}" placeholder="" id="" name="pname"> -->
					<select name="isMarry" class="loadoption   select" size="1" data="userConstant.isMarry" data-val="${user.isMarry}" style="height:30px;" id="ifMarry" onchange="displayMate()">
					    <option value="0">是</option>
						<option value="1">否</option>
					</select>
				</div>
				<label class="input-title fl">配偶：</label>
				<div class="formControls col-xs-8 col-sm-2" style="padding-left:19px;">
				    <!--<input type="text" class="input-text" value="${user.matename}" placeholder="" id="" name="matename">  -->
				    <!-- <select name="beginuserid" id="matename" class="my-select-mate select" style="height:28px;">
					</select> -->
					<select 
						name="beginuserid"
						id="matename" 
						class="my-select-mate select"
						data-am-selected="{btnWidth: '100%', searchBox: 1}">
					</select>
				</div>
				<label class="input-title fl">其他配偶：</label>
				<div class="formControls col-xs-8 col-sm-2" style="padding-left:19px;">
			        <c:forEach var="mate" items="${mateList}" varStatus="status">
			         <span>${mate.matetype} : ${mate.name}</span>
			        </c:forEach>
				</div>
			</div>
			
	<!--联系方式-->
			<div style="font-size:16px;border-bottom:6px solid #eeeeee;margin-top:15px;line-height:30px;color:#333333;font-weight:bold">联系方式</div>
			<div class="row cl" style="overflow:hidden">
			    <div class="col-sm-5">
				      <label class="input-title fl">微信号：</label>
				      <div class="formControls fl w200 ml-15">
					      <input type="text" class="input-text" value="${userInfo.weixin}" id="" name="weixin">     
				      </div>
				      <div class="formControls fl ml-30">
		      		  	<input type="checkbox" id="wxsee" value="" <c:if test="${userInfo.wxsee == 1 }">checked</c:if> style="width:16px;height:16px;">
		      		  	<span>隐藏</span>
				      </div>
				</div>
				<div class="col-sm-5">
				     <label class="input-title fl">QQ号：</label>
				     <div class="formControls fl w200 ml-15">
					     <input type="text" class="input-text" value="${userInfo.qq}" id="" name="qq">
				     </div>
				     <div class="formControls fl ml-30">
				         <input type="checkbox"  id="qqsee" value=""  <c:if test="${userInfo.qqsee == 1 }">checked</c:if> style="width:16px;height:16px;" >
				         <span>隐藏</span>
				     </div>
				</div>
				<div class="col-sm-5">
				     <label class="input-title fl">邮箱：</label>
				     <div class="formControls fl w200 ml-15">
					     <input type="text" class="input-text" value="${userInfo.mail}" placeholder="" id="" name="mail">
				     </div>
				     <div class="formControls fl ml-30">
				         <input type="checkbox" id="mailsee" <c:if test="${userInfo.mailsee == 1 }">checked</c:if> value="" style="width:16px;height:16px;">
				         <span>隐藏</span>
				     </div>
				</div>
				<div class="col-sm-5">
				     <label class="input-title fl">电话：</label>
				     <div class="formControls fl w200 ml-15">
					     <input type="text" class="input-text"  placeholder="" id="" value="${userInfo.tel}" name="tel">
				     </div>
				     <div class="formControls fl ml-30">
				         <input type="checkbox" <c:if test="${userInfo.telsee == 1 }">checked</c:if> style="width:16px;height:16px;" id="telsee" value="">
				         <span>隐藏</span>
				     </div>
				</div>
			</div>
			
		</div>
	<!--工作经历-->
		<div style="font-size:16px;border-bottom:6px solid #eeeeee;margin-top:15px;line-height:30px;color:#333333;font-weight:bold">工作经历</div>	
		<div class="row cl" id="workExp">
		    <label class="form-label col-xs-4 col-sm-1">工作经历：</label>
			<a class="btn btn-success radius" href="javascript:;" onclick="addWorkExp();" style="margin-left:13px;"><i class="Hui-iconfont">&#xe600;</i>增加</a>
		</div>
		<c:forEach var="workList" items="${workList}" varStatus="status">
		   <div class='row cl' id="workExp${status.index + 1 }">
		           <label class="form-label col-xs-4 col-sm-1">工作经历：</label>
		           <div class="formControls col-xs-8 col-sm-2">
		    	          <input type="text"  class='input-text' value="${workList.company }" id="company${status.index + 1 }" name="">
		            </div>
		           <div class="formControls col-xs-8 col-sm-2">
		             <input type="text" class="input-text" value="${workList.position }" id="position${status.index + 1 }" name="">
		           </div>
	               <div class='formControls col-xs-8 col-sm-2'>
	                <input type='text' placeholder='开始时间' onfocus='WdatePicker()'  value="${workList.datefromStr }" id='workdatefrom${status.index + 1}' class='input-text Wdate'>
	               </div>
	               <div class='formControls col-xs-8 col-sm-2'>
		             <input type='text' placeholder='结束时间' onfocus='WdatePicker()' value="${workList.datetoStr }" id='workdateto${status.index + 1}' class='input-text Wdate'>
		           </div>
		           <div class='formControls col-xs-8 col-sm-11'>
		                 <textarea placeholder=' 工作内容...' style='border:1px solid #dddddd;margin-left:106px;width:72%;margin-top:10px' id='workcontent${status.index + 1}'>${workList.workcontent}</textarea>
		           </div>
		           <input class='ml-5' <c:if test="${workList.issecret == 1 }">checked</c:if> type='checkbox' id='issecret${status.index + 1}' value="" style='width:15px;height:15px;color:#5a98de;margin-left:20px;margin-top:0;'>
		           	隐藏
		           <a class="ml-5" href="javascript:;" onclick="removeWorkExp(this);" style="text-decoration:none;color:#5a98de">删除</a>
		   </div>
		</c:forEach>
	<!--教育经历-->
		<div style="font-size:16px;border-bottom:6px solid #eeeeee;margin-top:15px;line-height:30px;color:#333333;font-weight:bold">教育经历</div>		
		<div class="row cl" id="eduExp" >
		    <label class="form-label col-xs-4 col-sm-1">教育经历：</label>
			<a class="btn btn-success radius" href="javascript:;" onclick="addEduExp();" style="margin-left:13px;"><i class="Hui-iconfont">&#xe600;</i>增加</a>
		</div>
		<c:forEach var="eduList" items="${eduList}" varStatus="status">
		   <div class='row cl' id="eduExp${status.index + 1 }">
		           <label class="form-label col-xs-4 col-sm-1">教育经历：</label>
		           <div class="formControls col-xs-8 col-sm-2">
		    	          <input type="text" class="input-text" value="${eduList.university }" id="university${status.index + 1 }" name="">
		            </div>
		           <div class="formControls col-xs-8 col-sm-2">
		             <input type="text" class="input-text" value="${eduList.major }" id="major${status.index + 1 }" name="">
		           </div>
		            <div class='formControls col-xs-8 col-sm-2'>
	                <input type='text' placeholder='开始时间' onfocus='WdatePicker()'  value="${eduList.datefromStr }" id='edudatefrom${status.index + 1}' class='input-text Wdate'>
	               </div>
	               <div class='formControls col-xs-8 col-sm-2'>
		             <input type='text' placeholder='结束时间' onfocus='WdatePicker()' value="${eduList.datetoStr }" id='edudateto${status.index + 1}' class='input-text Wdate'>
		           </div>
		           <div class='formControls col-xs-8 col-sm-11'>
		                 <textarea placeholder=' 教育内容...' style='border:1px solid #dddddd;margin-left:106px;width:72%;margin-top:10px' id='educontent${status.index + 1}'>${eduList.educontent}</textarea>
		           </div>
		           <input class='ml-5'  type='checkbox' <c:if test="${eduList.issecret == 1 }">checked</c:if> id='eduissecret${status.index + 1}' value="" style='width:15px;height:15px;color:#5a98de;margin-left:20px;margin-top:0;'>
		           	隐藏
		           <a class="ml-5" href="javascript:;" onclick="removeEduExp(this);" style="text-decoration:none;color:#5a98de">删除</a>
		   </div>
		</c:forEach>
	<!--个人简介-->
	    <div style="font-size:16px;border-bottom:6px solid #eeeeee;margin-top:15px;line-height:30px;color:#333333;font-weight:bold">个人简介</div>
		<div class="row cl">
			<div class="formControls col-xs-8 col-sm-12">
				<textarea name="remark" cols="" rows=""  class="textarea"  placeholder="个人简介" ">${userInfo.remark }</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/1000</p>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3" style="text-align:right">
				<input class="btn btn-primary radius" type="button" onclick="save();" value="&nbsp;&nbsp;确认&nbsp;&nbsp;" style="width:100px">
				<input class="btn btn-primary radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;" style="width:100px">
			</div>
	    </div>
	  </div>
	   </div>
	</form>
	<!--个人相册部分-->
	<c:if test="${user.userid != null }">
	<div id="photo">
	  <div id="xiangceliebiao">
        <button onclick="useralbum_add('0','${user.userid}','','','600','300')" class="btn btn-success radius" type="button" style="margin-top:20px;"> 新建相册</button>
        <div style="overflow:hidden">
           <c:forEach var="userAblum" items="${userAblumList}" varStatus="status">
             <c:if test="${userAblum.type == 0 }">
	            <div style="width:240px;background-color:#f5f5f5;height:250px;float:left; margin:10px 5px 0 5px;">
	                <a href="#" style="color:#333333" onclick="xiangcexiangqing('${userAblum.albumid}','${userAblum.type}')"><img src="<%=downLoadUrl%>${userAblum.imgurl}" style="width:220px;height:180px;border:1px solid #eeeeee;margin:10px;"></a>
	                <div style="padding:0 10px;overflow:hidden;font-size:14px;">
	                    <div style="overflow:hidden;">
	                         <div style="float:left;"><a href="#" style="color:#333333" onclick="xiangcexiangqing('${userAblum.albumid}')">${userAblum.remark}</a></div>
	                         <img src="<%=basePath %>images/bianji.png" style="float:right;cursor:pointer;margin-left:5px;" onclick="useralbum_add('0','${user.userid}','${userAblum.albumid}','','600','300')">
	                         <img src="<%=basePath %>images/shanchu.png" style="float:right;cursor:pointer;" onclick="album_del(this,'${userAblum.albumid}')">
	                    </div>
	                    <div style="overflow:hidden;">
	                        <div style="float:left;"><fmt:formatDate value="${userAblum.createtime}" pattern="yyyy-MM-dd"/></div>
	                	    <div style="float:right;font-weight:bold">${userAblum.photoNumbers}张</div>
	                    </div>
	                </div>
	            </div>
	           </c:if>
            </c:forEach>
        </div>
       </div>
       <div style="font-size:14px;display:none;" id="xiangcexiangqing">
       </div>
	</div>
	<!-- 个人作品部分 -->
	<div id="work">
	  <div style="" id="zuopinliebiao">
        <button onclick="useralbum_add('1','${user.userid}','','','600','300')" class="btn btn-success radius" type="button" style="margin-top:20px;"> 新建作品集</button>
         <div style="overflow:hidden">
             <c:forEach var="userAblum" items="${userAblumList}" varStatus="status">
              <c:if test="${userAblum.type == 1 }">
	            <div style="width:240px;background-color:#f5f5f5;height:250px;float:left; margin:10px 5px 0 5px;">
	                <a style="color:#333333" onclick="xiangcexiangqing('${userAblum.albumid}','${userAblum.type}')">
	                <img src="<%=downLoadUrl%>${userAblum.imgurl}" style="width:220px;height:180px;border:1px solid #eeeeee;margin:10px;"></a>
	                <div style="padding:0 10px;overflow:hidden;font-size:14px;">
	                    <div style="overflow:hidden;">
	                         <div style="float:left;"><a href="#" style="color:#333333" onclick="xiangcexiangqing('${userAblum.albumid}','${userAblum.type}')">${userAblum.remark}</a></div>
	                         <img src="<%=basePath %>images/bianji.png" style="float:right;cursor:pointer;margin-left:5px;" onclick="useralbum_add('1','${user.userid}','${userAblum.albumid}','','600','300')">
	                         <img src="<%=basePath %>images/shanchu.png" style="float:right;cursor:pointer;" onclick="album_del(this,'${userAblum.albumid}')">
	                    </div>
	                    <div style="overflow:hidden;">
	                        <div style="float:left;"><fmt:formatDate value="${userAblum.createtime}" pattern="yyyy-MM-dd"/></div>
	                	    <div style="float:right;font-weight:bold">${userAblum.photoNumbers}张</div>
	                    </div>
	                </div>
	            </div>
	           </c:if>
            </c:forEach>
        </div> 
       </div>
       <div style="font-size:14px;display:none;" id="zuopinxiangqing">
       </div>
	</div>
	</c:if>
</article>

<script type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="<%=basePath%>static/h-ui.admin/js/H-ui.admin.page.js"></script>
<script type="text/javascript" src="<%=basePath%>js/constant.js"></script>
<script type="text/javascript" src="<%=basePath%>js/distpicker.js"></script>
<!-- <script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script> -->
<!-- <script type="text/javascript" src="<%=basePath%>js/admin.js"></script> -->


<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/webuploader/0.1.5/webuploader.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/common/Feng.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/common/web-upload-object.js"></script> 


<!-- 新版本 -->
<script type="text/javascript" src="<%=basePath%>lib/assets/js/amazeui.min.js"></script>

<script type="text/javascript">
var imgPreUrl='<%=downAvatarUrl%>'; 
var upAvatarUrl='<%=upAvatarUrl%>'; 




var appContent= $(".Hui-article-box");


//根据传入的页面地址进行跳转
function pageTurn(url){
	if(url==undefined || url=="") return;
	pageChange(url);
}
//页面跳转
function pageChange(url){
	//页面跳转
	$.ajax({
		type : 'post',
		dataType : 'text',
		url : basePath+url,
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {
			;
		}
	});
}

function pageToSql(url){
	var data='<iframe id="sqlFrame" name="sqlFrame"   scrolling="yes" height="100%" frameborder="no" width="100%"></iframe>	';
	appContent.html(data);
	window.sqlFrame.location.href=basePath+url;
}

/*关于分页  所有列表必须有searchs 方法   公用的查询方法*/
//首页
function first(){
	var pageNo = 1;
	searchs(pageNo);
}
//最后一页
function last(lastPages){
	var pageNo =lastPages ;
	searchs(pageNo);
}
//上一页
function pre(){
	var pageNo = $("#pageNo").val();
	pageNo--;
	searchs(pageNo);
}
//下一页
function next(){
	var pageNo = $("#pageNo").val();
	pageNo++;
	searchs(pageNo);
}

function toPage(pageNo){
	$("#pageNo").val(pageNo);
	searchs(pageNo);
}

function changePageSize(){
	var pagesize = $('#pagesize option:selected').text();
	$("#pageSize").val(pagesize); 
	searchs(1);
}

function initBranch(){
	$.ajax({
		type:'post',
		dataType:'json',
		async: false,
		url : basePath + 'common/currentBranchJson?curSec='+Math.random(),
		success:function(data,status){
			if(data){
				var option = "<option value=''>---- 请选择分支----</option>";
				for(var i = 0; i < data.length; i++){
					option += "<option value="+data[i].branchid + " branchname="+data[i].branchname+">" +data[i].area + " "+data[i].cityname + " " +data[i].xname + " "+data[i].address+" "+data[i].branchname+"</option>";
				}
				
				$('.branch-select').html(option);
				var dataval = $('.branch-select').attr('data-val');
				if(dataval != ''){
			 		$('.branch-select').val(dataval);
			 	}
			}else{
				alert("初始化分支失败！");
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
	
	
}

function getOption(){
	var loadOption = $('.loadoption'); //默认将列表中的所有初始化项目获取
	//var url ='dictionary/searchByParentId';
	loadOption.each(function(){ //通过each 循环初始化数据字典项目
		var parentObj = $(this).attr('data'); //获取data中的父节点ID值
		var parentId = "";
		if(parentObj == "brotherposConstant"){
			var sex = $("#sex").val();
			if(sex == "0"){
				parentObj += ".brotherposWomen";
			}else if(sex == "1"){
				parentObj += ".brotherposMan";
			}
			parentId = eval(parentObj);//将对应的字符串转换成变量
			//编辑通过ID查询详情时，将默认后台选保存的值先赋值给data-val属性 ，最后通过该值将对应的选项选中
			var dataval = $(this).attr('data-val');
			var objthis = $(this);
		
			var str = "";
			var obj = parentId;//公共变量获取对应的数组
		
			for(var i=0;i<obj.length;i++){//循环数据字典中的所有制进行拼接
				str+="<option value="+obj[i].value+">"+obj[i].text+"</option>";
			}
			objthis.html(str);
			if(dataval == ''){
		 		objthis.val(1);//默认值设置为启用
		 	}else{
		 		objthis.val(dataval); //将dataval默认选中值赋值给初始化中的下拉项
		 	}
		}else{
			parentId = eval(parentObj);//将对应的字符串转换成变量
			//编辑通过ID查询详情时，将默认后台选保存的值先赋值给data-val属性 ，最后通过该值将对应的选项选中
			var dataval = $(this).attr('data-val');
			var objthis = $(this);
		
			var str = "";
			var obj = parentId;//公共变量获取对应的数组
		
			for(var i=0;i<obj.length;i++){//循环数据字典中的所有制进行拼接
				str+="<option value="+obj[i].value+">"+obj[i].text+"</option>";
			}
			objthis.html(str);
			if(dataval != ''){
				objthis.val(dataval); //将dataval默认选中值赋值给初始化中的下拉项
		 	}
		}
	 });
}
/**
 * 初始化排行
 */
function getOptionBrotherpos(){
		var parentObj = $('#brotherposSel').attr('data'); //获取data中的父节点ID值
		var parentId = "";
		var sex = $("#sex").val();
		if(sex == "0"){
			parentObj += ".brotherposWomen";
		}else if(sex == "1"){
			parentObj += ".brotherposMan";
		}else{
			return false;
		}
		parentId = eval(parentObj);//将对应的字符串转换成变量
		//编辑通过ID查询详情时，将默认后台选保存的值先赋值给data-val属性 ，最后通过该值将对应的选项选中
		var dataval = $('#brotherposSel').attr('data-val');
		var objthis = $('#brotherposSel');
	
		var str = "";
		var obj = parentId;//公共变量获取对应的数组
	
		for(var i=0;i<obj.length;i++){//循环数据字典中的所有制进行拼接
			str+="<option value="+obj[i].value+">"+obj[i].text+"</option>";
		}
		objthis.html(str);
		if(dataval == ''){
	 		objthis.val(1);//默认值设置为启用
	 	}else{
	 		objthis.val(dataval); //将dataval默认选中值赋值给初始化中的下拉项
	 	}
}

/** * 对Date的扩展，将 Date 转化为指定格式的String * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
    可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new
    Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423      
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04      
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04      
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04      
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18      
 */        
Date.prototype.pattern=function(fmt) {         
    var o = {         
	    "M+" : this.getMonth()+1, //月份         
	    "d+" : this.getDate(), //日         
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
	    "H+" : this.getHours(), //小时         
	    "m+" : this.getMinutes(), //分         
	    "s+" : this.getSeconds(), //秒         
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
	    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {         
	    "0" : "/u65e5",         
	    "1" : "/u4e00",         
	    "2" : "/u4e8c",         
	    "3" : "/u4e09",         
	    "4" : "/u56db",         
	    "5" : "/u4e94",         
	    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
}  




// -----------------------------------父(母)亲：-------------------------------------------/-----------------

	//初始化父亲 配偶
	var userDType=$("#userDType").val();
	// 获取“添加发起人”_数据
	var data = JSON.parse(localStorage.getItem("website2"))
	var optionStrP = "<option value='-1'>---- 请选择 ----</option>";
	var optionStrM = "<option value=''>---- 请选择 ----</option>";
	var editUserId = $("#userid").val();
	var genlevel;
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
				if(data[i].isdirect == 1){
					if(editUserId != ''){
						if(data[i].userid != editUserId){
							optionStrP += "<option value=" + data[i].userid + ">"+genlevel+"世_"+data[i].username+"</option>";
						}
					}else{
							optionStrP += "<option value=" + data[i].userid + ">"+genlevel+"世_"+data[i].username+"</option>";
					}
				}else{
					optionStrM += "<option value=" + data[i].userid + ">"+genlevel+"世_"+data[i].username+"</option>";
				}
			}
			$('.my-select').html(optionStrP);
			if(userDType==1){
				$('.my-select-mate').html(optionStrM);
			}else{
				$('.my-select-mate').html(optionStrP);
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
	//当父亲没有选择值时，手动输入排多少世
	$("#pname").on('change', function(e, params) {
			// 显示隐藏 世系 
			var pname = $("#pname").val();
			if(pname.length > 2){
				$("#ShiXiInputVal").val(""); // 世系为空
				$('#shixi').hide()
			} else {
				$('#shixi').show()
			}	
	});
// -----------------------------------父(母)亲：-------------------------------------------\-----------------



//显示隐藏在世离世信息
	function showDP(){
		document.getElementById('die-date').style.display='block';
		$("#tel").attr("disabled",true);
		$("#tel").parent().hide();
	}
	function hideDP(){
		document.getElementById('die-date').style.display='none';
		$("#tel").attr("disabled",false);
		$("#tel").parent().show();
	}
    function displayMate(){
    	var ifMarry = $("#ifMarry").val();
    	if(ifMarry == '1'){
    		$("#matename").val('');
    	}
    }
    $("#branchid").change(function(){
	  $("#branchname").val($("#branchid option:selected").attr("branchname"));
	});
	function chooseTab(id){
		$('#basic').hide();
		$('#photo').hide();
		$('#work').hide();
		$('.xuanxiang-tab').find("li").css({'background-color':'#f5fafe','color':'#000000'});
		if(id != ''){
			$('#'+id).show();
			$('#'+id+'-title').css({'background-color':'#5a98de','color':'#ffffff'});
		}else{
			$('#basic').show();
			$('#basic-title').css({'background-color':'#5a98de','color':'#ffffff'});
		}
	}
	
	function preShowIMG(){
		var pUrl=$("#avatar").val();
		if(pUrl!=null&&pUrl!=''){
			var imgSRC=imgPreUrl+pUrl;
//			setTimeout(function () {
			 	$("#preImg").attr("src",imgSRC);	
				$("#preImg").show();
//			}, 1500);
		}
//		else{
//			$("#preImg").hide();
//		}	
	}	
	
	$(function(){
	    // 初始化头像上传
	    var avatarUp = new $WebUpload("avatar");
	    avatarUp.init();
	    //头像回显
	    preShowIMG();
	    
		initBranch();
		//默认设置为在世
		if('${user.livestatus}' == ''){
			$("input[type='radio']").eq(0).prop("checked",true);
		}
		
		//如果为离世 显示离世日期
		var livestatus = '${user.livestatus}';
		if(livestatus == 1){
			document.getElementById('die-date').style.display="block";
			$("#tel").attr("disabled",true);
			$("#tel").parent().hide();
		}
		var sex = '${user.sex}';
		if(sex != null){
			$("#sex").val(sex);
		}
		getOption();

		var background = '${userInfo.background}';
		$("#background").val(background);
	
		//配偶回显
		var mateid = '${user.mateid}';
		$("#matename").val(mateid);
		//父母亲回显
		var pid = '${user.pid}';
		$("#pname").val(pid);
		
	
		//回显其他民族
		var nationSel = $("#nationSel").val();
		if(nationSel == "其他"){
			ifOther();
			var nationStr=$("#nationSel").attr("odata-val");
			$("#nationInputVal").val(nationStr);
		}
		chooseTab('<%=chooseTab%>');//初始化选中tab
	});
	
	$(document).ready(function() {	
		$(document).keyup(function() {
	    var counter = $(".textarea").val().length; //获取文本域的字符串长度
	   // alert(counter);
		$(".textarea-length").html(counter);
		if(counter>1000)
			{
				window.parent.searchs();
				window.parent.layer.msg('文本字数超过限制!', {icon: 5,time:1000});
			}
		});
	});
	
	function save(){
		if($("#user-add").valid()){
			var counter = $(".textarea").val().length;
			if(counter>1000){
				layer.alert('个人简介字数超过限制!', {icon: 5,time:1000});		
				return;
			}
			onclickIfSee();
			//微信 qq 电话可见取值
			var wxsee = $("#wxsee").val();
			var qqsee = $("#qqsee").val();
			var mailsee = $("#mailsee").val();
			var telsee = $("#telsee").val();
			
			var sex = $("#sex").val();
			var tel = $("#tel").val();
			var homeplace = $("#homeplace").val();
			if(sex == "" || sex == "-1"){
				alert("请选择性别");
				return false;
			}
			var livestatus = $("#livestatus").val();
		/*	if(livestatus != "0"){
				if(tel == ""){
					alert("请填写手机号");
					return false;
				}
			}
		*/
			if(livestatus == "0"){
				$("#dietimeStr").val('');
			}
	        if(homeplace == ""){
	        	alert("请选择常住地");
	        	return false;
			}
			
			//计算教育经历行数
			var eduExpArray = "";
			for(var i = 1; i <= 4; i++){
		        if($("#university"+i).val() != null && $("#university"+i).val() != undefined && $("#major"+i).val() != null && $("#major"+i).val() != undefined){
		          if($("#university"+i).val() == "" || $("#major"+i).val() == ""){
		        	 // alert("教育经历有空值，请填完整");
		        	  layer.alert('教育经历有空值，请填完整', {icon: 7});
		        	  return;
		          }else{
		        	  var eduExp = $("#university"+i).val()+"_"+$("#major"+i).val()+"_"+$("#edudatefrom"+i).val()+"_"+$("#edudateto"+i).val()+"_"+$("#educontent"+i).val()+"_"+$("#eduissecret"+i).val();
		              eduExpArray += eduExp+"@@";
		          }
		          
		        }
		      } 
			//计算够工作经历
			var workExpArray = "";
			 for(var i = 1; i <= 4; i++){
		        if($("#company"+i).val() != null && $("#company"+i).val() != undefined && $("#position"+i).val() != null && $("#position"+i).val() != undefined){
		          if($("#company"+i).val() == "" || $("#position"+i).val() == ""){
		        	  //alert("工作经历有空值，请填完整");
		        	  layer.alert('工作经历有空值，请填完整', {icon: 7});
		        	  return;
		          }else{
		        	  var workExp = $("#company"+i).val()+"_"+$("#position"+i).val()+"_"+$("#workdatefrom"+i).val()+"_"+$("#workdateto"+i).val()+"_"+$("#workcontent"+i).val()+"_"+$("#issecret"+i).val();
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
				if($("#pname").val().length == '-1'){
					//alert("请填写世系");
					layer.alert('请填写世系', {icon: 7});
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
			birArea = $("#barea").val()+"@@"+$("#bcityname").val()+"@@"+$("#bxname").val()+"@@"+$("#birthDetail").val();
			//常住地 省市县 harea hcityname hxname 
			var homArea = "";
			homArea = $("#harea").val()+"@@"+$("#hcityname").val()+"@@"+$("#hxname").val()+"@@"+$("#homeDetail").val();
			if($("#user-add").valid()){
			$.ajax({
				type:'post',
				dataType:'text',
				async: true,
				data:$("#user-add").serialize()+"&eduExpArray="+eduExpArray+"&workExpArray="+workExpArray+"&nation="+nation
				     +"&birthplace="+birArea+"&homeplace="+homArea+"&parentName="+parentName+"&fatherName="+fatherName+"&wxsee="+wxsee+"&qqsee="+qqsee+"&mailsee="+mailsee+"&telsee="+telsee,
				url : '<%=basePath%>user/merge?curSec='+Math.random(),
				success:function(data,status){
					if(status == 'success' && data == '1'){
						window.parent.layer.msg('保存成功!', {icon: 6,time:1000});
						// layer_close();
					}else if(data == '2'){
						//alert("保存失败，用户数量超过版本最大用户数量");
						layer.alert('保存失败，用户数量超过版本最大用户数量', {icon: 5});
					}else if(data == '500'){
						layer.alert('数据库有重复,保存失败!',{icon:5,time:2000});
					}else{
						//alert("保存失败");
						layer.alert('保存失败', {icon: 5});
					}
				},
				error:function(e) {
					console.log(e);
				}
			});
			}
		}
	}
	//手机号验证
	$(function() {
	$("#user-add").validate({
			rules:{
				username:{
					required:true,
					minlength:2,
					maxlength:50
				},
				phone:{
					//required:true,
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
					//required: "填写手机号",
					isMobile:"请填写正确的手机号",					
					remote: "手机号重复",
				},
				"username": {
					required: "填写用户姓名",
				},
	        },
			onkeyup:false,
			focusCleanup:false,
 			errorPlacement: function(error, element) { //错误信息位置设置方法
				//error.insertAfter(element); 
 				var errorInfo=error.html();
 				//layer.alert(''+errorInfo, {icon: 7});
 				if(errorInfo!=""){
 					//alert(errorInfo);
 					layer.alert(''+errorInfo, {icon: 7});
 				}
				}, 
			success:"valid",
			submitHandler:function(form){
			}
		}); 
	});
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
		    appendDiv+=        "<label class='form-label col-xs-4 col-sm-1'></label>";
		    appendDiv+=        "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=	          "<input type='text' class='input-text' value='' placeholder='毕业院校' id='university"+(idLength)+"'>";
		    appendDiv+=        "</div>" ;
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=           "<input type='text' class='input-text' value='' placeholder='所学专业' id='major"+(idLength)+"' name=''>";
		    appendDiv+=         "</div>";
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=         "<input type='text' placeholder='开始时间' onfocus='WdatePicker()'  id='edudatefrom"+(idLength)+"' class='input-text Wdate'>";
		    appendDiv+=         "</div>";
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=         "<input type='text' placeholder='结束时间' onfocus='WdatePicker()'  id='edudateto"+(idLength)+"' class='input-text Wdate'>";
		    appendDiv+=         "</div>"; 
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-11'>";
		    appendDiv+=             "<textarea placeholder=' 教育内容...' style='border:1px solid #dddddd;margin-left:106px;width:72%;margin-top:10px;' id='educontent"+(idLength)+"'></textarea>"
		    appendDiv+=         "</div>"; 
		    appendDiv+=         "<input class='ml-5 vm'  type='checkbox' id='eduissecret"+(idLength)+"' value='' style='width:15px;height:15px;color:#5a98de;margin-left:20px;'><span class='vm'>隐藏</span>";
		    appendDiv+=         "<a class='ml-5 vm' href='javascript:;' onclick='removeEduExp(this);' style='text-decoration:none;color:#5a98de;margin-left:30px;'>删除</a>";
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
		    appendDiv+=        "<label class='form-label col-xs-4 col-sm-1'></label>";
		    appendDiv+=        "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=	          "<input type='text' class='input-text' value='' placeholder='请输入工作单位' id='company"+(idLength)+"' name=''>";
		    appendDiv+=        "</div>" ;
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=           "<input type='text' class='input-text' value='' placeholder='请输入职位' id='position"+(idLength)+"' name=''>";
		    appendDiv+=         "</div>";
		  
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=         "<input type='text' placeholder='开始时间' onfocus='WdatePicker()'  id='workdatefrom"+(idLength)+"' class='input-text Wdate'>";
		    appendDiv+=         "</div>";
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-2'>";
		    appendDiv+=         "<input type='text' placeholder='结束时间' onfocus='WdatePicker()'  id='workdateto"+(idLength)+"' class='input-text Wdate'>";
		    appendDiv+=         "</div>"; 
		    
		    appendDiv+=         "<div class='formControls col-xs-8 col-sm-11'>";
		    appendDiv+=             "<textarea placeholder=' 工作内容...' style='border:1px solid #dddddd;margin-left:106px;width:72%;margin-top:10px;' id='workcontent"+(idLength)+"'></textarea>"
		    appendDiv+=         "</div>"; 
		    appendDiv+=         "<input class='ml-5 vm'  type='checkbox' id='issecret"+(idLength)+"' value='' style='width:15px;height:15px;color:#5a98de;margin-left:20px;'><span class='vm'>隐藏</span>";
		    appendDiv+=         "<a class='ml-5 vm' href='javascript:;' onclick='removeWorkExp(this);' style='text-decoration:none;color:#5a98de;margin-left:20px;'>删除</a>";
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
			 var div = $("div[id='inputNation']");
			 var appendInput = "<label id='nationInput' class='input-title fl'></span>民族：</label>";
				 appendInput+= "<div id ='nationInputDiv' class='formControls fl'>";
				 appendInput+= "<input class='input-text w200' type='text' id='nationInputVal'>";
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
	 function onclickIfSee(){
		 $('#user-add').find('input[type=checkbox]').each(function(){
			 if($(this).prop("checked")){
				 $(this).val("1");
			 }else{
				 $(this).val("0");
			 } 
		 });
	 }
	 /**
	  * 相册详情
	  */
	 function xiangcexiangqing(albumid,type){
		//alert(type);
	 	var userid = $("#userid").val();
	 	 $.ajax({
	 		type:'post',
	 		dataType:'text',
	 		async: true,
	 		data:"albumid="+albumid+"&userid="+userid+"&type="+type,
	 		url : '<%=basePath%>user/userAlbumDetail?curSec='+Math.random(),
	 		success:function(data,status){
	 			if(type == '0'){
	 				document.getElementById('xiangceliebiao').style.display="none";
	 				document.getElementById('xiangcexiangqing').style.display="block";
		 			$("#xiangcexiangqing").html(data);
	 			}else{
	 				document.getElementById('zuopinliebiao').style.display="none";
	 				document.getElementById('zuopinxiangqing').style.display="block";
		 			$("#zuopinxiangqing").html(data);
	 			}
	 			
	 		},
	 		error:function(e) {
	 			console.log(e);
	 		}
	 	 });
	 	 //document.getElementById('xiangcexiangqing').style.display="block";	
	 }

	 function zuopinxiangqing(){
	 	 document.getElementById('zuopinliebiao').style.display="none";
	 	 document.getElementById('zuopinxiangqing').style.display="block";	
	 }

	 $("#imgGroup > div").on("click",function(){
	 	$("#modal-demo").modal("show")
	 	var imgUrl = $(this).find("img").attr("src");
	 	var imgHtml = "<img src='"+imgUrl+"'>";
	 	$("#imgBox").empty();
	 	$("#imgBox").append(imgHtml);
	 })
	 /*删除相册*/
	 function album_del(obj,albumid,imageid){
	 	var userid = $("#userid").val();
	 	layer.confirm('确认要删除吗？',function(){
	 		$.ajax({
	 			type:'post',
	 			dataType:'text',
	 			async: true,
	 			data:"userid="+userid+"&albumid="+albumid,
	 			url : '<%=basePath%>user/deleteUserAlbum?curSec='+Math.random(),
	 			success: function(data){
	 				if(data == '1'){
	 					$(obj).parent().parent().parent().remove();
	 					layer.msg('已删除!',{icon:1,time:1000});
	 				}
	 			},
	 			error:function(data) {
	 				console.log(data.msg);
	 			},
	 		});		
	 	});
	 }
	 function returnAblum(type){
		 if(type == '0'){
			  $('#xiangcexiangqing').hide();
			  $('#xiangceliebiao').show();
		 }else{
			 $('#zuopinxiangqing').hide();
			 $('#zuopinliebiao').show();
		 }
	 }
	 function useralbum_add(type,userid,albumid,id,w,h){
	 	var title = "";
	 	if(type == '0'){
	 		title = "新建相册";
	 	}else{
	 		title = "新建作品集";
	 	}
	 	var url = basePath + 'user/toAddAblum?userid='+userid+'&albumid='+albumid+'&type='+type+'&curSec='+Math.random();
	 	layer_show(title,url,w,h);
	 }
	 /*编辑照片*/
	 function userphoto_add(title,url,id,w,h){
	 	layer_show(title,url,w,h);
	 }
	 
	 function refpage(userid,chooseTab){
		var url = basePath + 'user/edit?userid='+userid+'&chooseTab='+chooseTab+'&curSec='+Math.random();
		window.location.href = url;
	}
</script>
