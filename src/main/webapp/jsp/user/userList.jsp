<%@page import="com.jp.common.CurrentUserContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String currentUserid=CurrentUserContext.getCurrentUserId();
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui_select.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css" />
<script type="text/javascript">
	/*新增家族成员时判断家族容纳人数时候超出版本限制人数
	*/
	function check_familyusercount(){
		$.ajax({
			type:'post',
			dataType:'json',
			async: false,
			data:{},
			url : '<%=basePath%>user/check?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '0'){
					//调用新增成员方法
					member_edit('新增成员','','1');
				}else{
					layer.msg('家族人数已超出家族容纳最大人数!', {icon: 6,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	}
	/*成员添加
	*/
	function member_add(title,url,w,h){
		layer_show(title,url,w,h);
	}
	/*成员编辑
	*/
	function member_edit(title,userid,id,w,h){
		var w = 1300;
		var url = basePath + 'user/edit?userid='+userid+'&curSec='+Math.random();
		layer_show(title,url,w,h);
	}
	/*给单个成员增加配偶
	*/
	function member_addmates(title,userid,id,w,h){
		var w = 800;
		var h = 600;
		var url = basePath + 'user/addmate?userid='+userid+'&curSec='+Math.random();
		layer_show(title,url,w,h);
	}
	/*用户状态-停用*/
	function user_stop(id){
		layer.confirm('确认要停用吗？',function(index){
			changeStatus(id,3);
		});
	}
	/*用户状态-恢复停用*/
	function user_start(id){
		layer.confirm('确认要恢复吗？',function(index){
			changeStatus(id,0);
		});
	}	
	//更改用户状态
	function changeStatus(id,change_status){
			$.ajax({
				type:'post',
				dataType:'text',
				async: true,
				data:"userid="+id+"&status="+change_status,
				url : '<%=basePath%>user/changeStatus?curSec='+Math.random(),
				success:function(data,status){
					if(status == 'success' && data == '1'){
						searchs(1);
						if(change_status == 0){
							layer.msg('已恢复!', {icon: 6,time:1500});
						}else{
							layer.msg('已停用!',{icon: 5,time:1500});
						}						
					}else{
						layer.msg('状态更改失败!', {icon: 6,time:1000});
						searchs(1);
					}
				},
				error:function(e) {
					console.log(e);
				}
			});
	}
	//导入用户
	function importUser(title,userid,id,w,h){
		var w = 500;
		var h = 300;
		var url = basePath + 'jsp/user/importUser.jsp';
		layer_show(title,url,w,h);
	}
	//导入配偶
	function importUsermates(title,userid,id,w,h){
		var w = 500;
		var h = 300;
		var url = basePath + 'jsp/user/importUsermates.jsp';
		layer_show(title,url,w,h);
	}
</script>
<nav class="breadcrumb">
<i class="Hui-iconfont">&#xe67f;</i> 
首页 <span class="c-gray en">&gt;</span> 
用户管理 
<span class="c-gray en">&gt;</span> 
成员管理 
</nav>
<div class="Hui-article">
	<article class="cl pd-30">
		<div class="text-c" style=" text-align:left">
		    
			<span class="col-xs-6" style="padding:0;">
				<input type="hidden" class="input-text" id="excelid">
				<input type="text" class="input-text" style="width:20%" placeholder="姓名" id="username" value="${user.username }">
			    <input type="text" class="input-text" style="width:20%" placeholder="世系" id="genlevel" value="${user.genlevel }">
				<span class="select-box" style="width:25%">
					<select class="select" size="1" name="status" id="status" value="${user.status }">
					    <option value="">选择状态</option>
						<option value="0">正常</option>
						<option value="3">停用</option>
					</select>
				</span>
				<span class="select-box" style="width:30%">
					<select class="select" size="1" name="isdirect" id="isdirect" value="${user.isdirect }">
						<option value="">选择亲属关系</option>
						<option value="1">直系</option>
						<option value="0">非直系</option>
					</select>
				</span>
			</span>
			<span class="formControls col-xs-4" style=" text-align:center;padding:0;">
				<select id="branchid" class="branch-select select" data-val="${user.branchid }">
				</select>
	        </span>
	        <span class="col-xs-2">
			     <button type="button" class="btn btn-success radius" onclick="searchs(1);"><i class="Hui-iconfont">&#xe665;</i> 搜索成员</button>	
			</span>		
		</div>
		<div class="cl pd-5 bg-1 bk-gray mt-20" style="margin-top:50px;">
		    <span class="l">
			    <a href="javascript:;" id="all" onclick="batchdelete();" class="btn btn-danger radius">
			      <i class="Hui-iconfont">&#xe6e2;</i> 批量删除
			    </a> &nbsp;&nbsp;
		        <a class="btn btn-primary radius" href="javascript:;" onclick="check_familyusercount()"><i class="Hui-iconfont">&#xe600;</i> 新增成员</a>&nbsp;&nbsp;
	            <a href="javascript:;" id="btn_show_dialog" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe645;</i> 导入用户</a>&nbsp;&nbsp;
	            <a href="javascript:;" id="btn_show_dialog1" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe645;</i> 导入配偶</a>
	            <!-- <a href="javascript:;" onclick="importUsermates('导入配偶','','1')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe645;</i> 导入配偶</a> -->
		</span>
		</div>
		<div class="mt-10">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-hover table-bg">
					<thead>
						<tr class="text-c">
						<th width="25"><input name="controlAll" id="controlAll" type="checkbox" value="" onclick="checkAll();"></th>
							<th width="30">序号</th>
							<th width="30">增加配偶</th>
							<th width="30">姓名</th>
							<th width="30">性别</th>
							<th width="50">生辰</th>
							<th width="30">排行</th>
							<th width="40">世系</th>
							<th width="110">隶属分支</th>
							<th width="40">状态</th>
							<th width="50">操作</th>
						</tr>
					</thead>
					<tbody>
					  <c:forEach var="user" items="${pageModel.list}" varStatus="status">
						<tr class="text-c">
							<td><input type="checkbox" value="${user.userid }" name="selected"></td>
							<td>${status.index + 1 }</td>
							<td>
							 <c:if test="${user.isdirect == 1 }">
							  <a href="javascript:;" onclick="member_addmates('增加配偶','${user.userid}','1','${user.sex}')">增加配偶</a>
							 </c:if> 
							</td>
							<td>${user.username }</td>
							<td>
								<c:if test="${user.sex == 0}">女</c:if>
								<c:if test="${user.sex == 1}">男</c:if>
							</td>
							<!--  
							
							-->
							<td>${user.searchBirthday}</td>
							<td>${user.brotherpos }</td>
							<td>${user.genlevel }</td>
							<td>${user.branchname }</td>
							<td class="td-status">
								<c:if test="${user.status == 0 }">
								    <span class="label label-success radius">正常</span>
								</c:if>
								<c:if test="${user.status == 3}">
									<span class="label label-default radius">停用</span>
								</c:if>
							</td>	
								
								<td class="f-14">
								<c:if test="${user.ismanager == 0 }">		
								  <c:if test="${user.status == 3 }">
								  	<a  onClick="user_start('${user.userid}')" href="javascript:;" >恢复</a>				    
								  </c:if>
								  <c:if test="${user.status == 0}">
									<a  onClick="user_stop('${user.userid}')" href="javascript:;" >停用</a>&nbsp;|
									<a href="javascript:;" onclick="member_edit('编辑成员','${user.userid}','1')" >编辑</a>
								  </c:if>							
								</c:if>		
								<c:if test="${user.ismanager == 1 }">		
								  <a href="javascript:;" onclick="member_edit('编辑成员','${user.userid}','1')" >编辑</a>
								  <!-- <span style="color:red">家族管理员<span>		 -->				
								</c:if>		
								<%-- <a href="javascript:;" onclick="if(confirm('确定停用当前用户吗？')){changeStatus('${user.userid}');}">停用</a>&nbsp;|	 --%>						
								</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<jsp:include page="../common/basepage.jsp"></jsp:include>
			</div>
		</div>
	</article>
</div>

<!--导入用户弹出框-->
    <div id="dialog" class="dialog hide" minheight="320" minwidth="660">
        <div id="
" class="dlg_top">
                <label class="dlg_title">导入用户</label>                
                <input class="dlg_btn_close dlg_btn_ico layui-layer-ico layui-layer-close1" id="hide" type="button"/>                
                <input class="dlg_btn_ico layui-layer-ico layui-layer-max dlg_btn_max_top" id="maxShow" type="button"/>
        </div>
        <div class="dlg_content page-container">
            <form action="" method="post" class="form form-horizontal" id="user-import" enctype="multipart/form-data">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3" style="text-align:right">模板下载：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<a href="<%=basePath%>static/excel/导入直系用户模板.xls">点击下载导入用户模板XLS</a><br />
						<%-- <a href="<%=basePath%>static/excel/导入直系用户模板.xlsx">点击下载导入用户模板XLSX</a> --%>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3" style="text-align:right">请选择分支：</label>
					<div class="formControls col-xs-8 col-sm-9 branchid" >
						 <select size="1" class="input-text select" data-placeholder="请选择分支" id="branchid1" data-val="" style="display:block;">
							</select>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3" style="text-align:right">选择文件：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="file" name="file" id="myfile" />
					</div>
				</div>
				<!-- 	<div class="row cl" id="addNation">
					<label class="form-label col-xs-4 col-sm-4" id="result" style="color:red"></label>
				</div> -->
				<div class="row cl">
					<div style="text-align: center;margin-top:40px;">
						<input class="btn btn-primary radius" id="dlg_submit" type="button" value="&nbsp;&nbsp;上传&nbsp;&nbsp;">
						<input class="btn btn-primary radius dlg_btn_close" id="btn_close" type="button" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;">
					</div>
				</div>
			</form>
        </div>
    </div>
    
    <!--回显导入用户弹出框-->
    <div id="usershow" class="dialog hide" minheight="1000" minwidth="1000">
        <div id="dlg_top" class="dlg_top">
                <label class="dlg_title">请确认是否导入</label>                
                <input class="dlg_btn_close dlg_btn_ico layui-layer-ico layui-layer-close1" id="hide1" type="button"/>                
                <input class="dlg_btn_ico layui-layer-ico layui-layer-max dlg_btn_max_top1" id="maxShow1"  type="button"/>
        </div>
        <div class="dlg_content page-container">
        
            <form action="" method="post" class="form form-horizontal" id="user-import" enctype="multipart/form-data">
				<div class="row cl individual">
				<div>
					<table class="table table-border table-bordered table-hover table-bg" >
						<thead>
							<tr class="text-c">
								<th width="30">序号</th>
								<th width="40">状态</th>
								<th width="30">姓名</th>
								<th width="30">性别</th>
								<th width="30">直系用户</th>
								<th width="40">世系</th>
							
								
							</tr>
						</thead>
						<tbody id="content">
						
						</tbody>
					</table>
				</div>
					<div style="text-align: center;margin-top:40px;">
						<input class="btn btn-primary radius" id="importUser" type="button" value="&nbsp;&nbsp;导入&nbsp;&nbsp;">
						<input class="btn btn-primary radius dlg_btn_close" id="btn_close1" type="button" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;">
					</div>
				</div>
			</form>
        </div>
    </div>
    
    <!--导入配偶弹出框-->
    <div id="dialog1" class="dialog hide" minheight="320" minwidth="660">
        <div class="dlg_top">
                <label class="dlg_title">导入配偶</label>                
                <input class="dlg_btn_close dlg_btn_ico layui-layer-ico layui-layer-close1" id="hide2" type="button"/>                
                <input class="dlg_btn_ico layui-layer-ico layui-layer-max dlg_btn_max_top" id="maxShow2" type="button"/>
        </div>
        <div class="dlg_content page-container">
            <form action="" method="post" class="form form-horizontal" id="user-import1" enctype="multipart/form-data">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-4" style="text-align:right">模板下载：</label> 
			<div class="formControls col-xs-8 col-sm-8">
				<a href="<%=basePath%>static/excel/导入配偶模板.xls">点击下载导入配偶模板XLS</a><br/>
				<%-- <a href="<%=basePath%>static/excel/导入配偶模板(新).xlsx">点击下载导入配偶模板XLSX</a> --%>
			</div>
		</div>	
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3" style="text-align:right">请选择分支：</label>
				<div class="formControls col-xs-8 col-sm-9 branchid" >
					<select size="1" class="input-text select" data-placeholder="请选择分支" id="branchid2" data-val="" style="display:block;">
					</select>
				</div>
			</div>
			<div class="row cl" id="addNation">
				<label class="form-label col-xs-4 col-sm-3" style="text-align:right">选择文件：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="file" name="file" id="myfile1" />
				</div>
			</div>
			<!-- 	<div class="row cl" id="addNation">
				<label class="form-label col-xs-4 col-sm-4" id="result" style="color:red"></label>
			</div> -->
			<div class="row cl">
				<div style="text-align: center;margin-top:40px;">
					<input class="btn btn-primary radius" id="dlg_submit1" type="button" value="&nbsp;&nbsp;上传&nbsp;&nbsp;">
					<input class="btn btn-primary radius dlg_btn_close" id="btn_close3" type="button" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;">
				</div>
			</div>
	</form>
        </div>
    </div>
    
    <!--回显导入配偶弹出框-->
    <div id="usershow1" class="dialog hide" minheight="1000" minwidth="1000">
        <div id="dlg_top" class="dlg_top">
                <label class="dlg_title">请确认是否导入</label>                
                <input class="dlg_btn_close dlg_btn_ico layui-layer-ico layui-layer-close1" id="hide3" type="button"/>                
                <input class="dlg_btn_ico layui-layer-ico layui-layer-max dlg_btn_max_top1" id="maxShow3"  type="button"/>
        </div>
        <div class="dlg_content page-container">
            <form action="" method="post" class="form form-horizontal" id="user-import1" enctype="multipart/form-data">
				
				
				<div class="row cl individual">
					<div>
						<table class="table table-border table-bordered table-hover table-bg" >
							<thead>
								<tr class="text-c">
								<th width="30">序号</th>
								<th width="40">状态</th>
								<th width="30">姓名</th>
								<th width="30">性别</th>
								<th width="30">直系用户</th>
								<th width="40">世系</th>
								</tr>
							</thead>
							<tbody id="content1">
							
							</tbody>
						</table>
					</div>
						
					<div style="text-align: center;margin-top:40px;">
						<input class="btn btn-primary radius" id="importUserMate" type="button" value="&nbsp;&nbsp;导入&nbsp;&nbsp;">
						<input class="btn btn-primary radius dlg_btn_close" id="btn_close2" type="button" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;">
					</div>
				</div>
			</form>
        </div>
    </div>
    
    <div id="half"></div>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>js/jquery-1.11.0.min.js"></script> --%>
<script type="text/javascript" src="<%=basePath%>js/main.js"></script>

<script type="text/javascript">
$(function(){
	//初始化分支
	initBranch();
	var isdirect = '${user.isdirect}';
	$("#isdirect").val(isdirect);
	$('.my-select').chosen({
    	search_contains: true,
      	max_selected_options: 1,
      	no_results_text: "没有找到",
    });
	var status = '${user.status}';
	$("#status").val(status);
	
		//初始化导入用户分支
		$.ajax({
			type:'post',
			dataType:'json',
			async: false,
			url : '<%=basePath%>branch/initBranch?curSec='+Math.random(),
			success:function(data,status){
				if(data){
					var optionStrM = "";
					var dataval = $('#branchid1').attr('data-val');
					var valArray = dataval.split(",");
					for(var i = 0; i < data.length; i++){
						if(valArray.indexOf(data[i].branchid + "_" +data[i].branchname) != -1){
							optionStrM += "<option selected value='" + data[i].branchid+"' >" +data[i].area + " "+data[i].cityname + " " +data[i].xname + " "+data[i].address+" "+data[i].branchname+"</option>";
						}else{
							optionStrM += "<option value='" + data[i].branchid+"' >" +data[i].area + " "+data[i].cityname + " " +data[i].xname + " "+data[i].address+" "+data[i].branchname+"</option>";
						}
					}
					$('#branchid1').html(optionStrM);
				var optionStrM1 = "";
					var dataval1 = $('#branchid2').attr('data-val');
					var valArray1 = dataval1.split(",");
					for(var i = 0; i < data.length; i++){
						if(valArray1.indexOf(data[i].branchid + "_" +data[i].branchname) != -1){
							optionStrM1 += "<option selected value='" + data[i].branchid+"' >" +data[i].area + " "+data[i].cityname + " "+data[i].address+" " +data[i].xname + " "+data[i].branchname+"</option>";
						}else{
							optionStrM1 += "<option value='" + data[i].branchid+"' >" +data[i].area + " "+data[i].cityname + " " +data[i].xname + " "+data[i].address+" "+data[i].branchname+"</option>";
						}
					}
					$('#branchid2').html(optionStrM1); 
					
				}else{
					alert("初始化人员失败！");  
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
		$('#branchid1').chosen({
	  	     search_contains: true,
	    	 max_selected_options: 1,
	    	 no_results_text: "没有找到",
	   });
		$("#branchid1_chosen").css("width","90%");
		$('#branchid2').chosen({
	  	     search_contains: true,
	    	 max_selected_options: 1,
	    	 no_results_text: "没有找到",
	   });
		$("#branchid2_chosen").css("width","90%");
	 
});

$("#importUser").click(function(){
	var excelid = $("#excelid").val();
	var url = basePath + "user/confirmImport";
	importUsers(url,excelid);
	$("#myfile").val("");
});
$("#importUserMate").click(function(){
	var excelid = $("#excelid").val();
	var url = basePath + "user/confirmImport";
	importUsers(url,excelid);
	$("#myfile1").val("");
	
});
function importUsers(url,excelid){
	$.ajax({
		type:'post',
		dataType:'json',
        url:url,
		// 告诉jQuery不要去设置Content-Type请求头
		
        data: {
        	"excelid":excelid
        },
        success: function(data) {
        	  if(data.status==1){
        		  window.parent.searchs();
        		  window.parent.layer.msg(data.msg, {icon: 6,time:5000});
        	  }else{
        		  window.parent.layer.msg(data.msg, {icon: 5,time:2000});
        	  }
          
        },
        error: function(request) {
            layer.alert('请求失败，请稍后再试', {icon: 5});
        },
    });
}


$("#dlg_submit").click(function(){
	var url = basePath + "user/importUser";
	var myfile = $("#myfile").val();
     if (myfile == "") {
    	 layer.alert('请选择要导入的文件!', {icon: 5});
         return false
     }else {
         //检验文件类型是否正确
         var exec = (/[.]/.exec(myfile)) ? /[^.]+$/.exec(myfile.toLowerCase()) : '';
         if (exec != "xlsx"&& exec != "xls") {
        	 layer.alert('只能导入EXCEL数据文件!', {icon: 5});
             return false;
         }
     }
     var formData = new FormData($("#user-import")[0]);
     formData.append("branchid",$("#branchid1").val());
   
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
              
              if(data.status == 1){
            	  var excelid = data.data2;
            	  var  data = data.data;
            	  var html = '';
            	  for(var i =0 ; i < data.length ; i++){
            		  html += '<tr>';
            		  html += '<td>'+(i+1)+'</td>';
            		  if(data[i].msg){
            			  html += '<td style="color:red">'+data[i].msg+'</td>';
            		  }else{
            			  html += '<td></td>';
            		  }
            		  html += '<td>'+data[i].username+'</td>';
            		  if(data[i].sex==1){
            			  html += '<td>男</td>';
            		  }else{
            			  html += '<td>女</td>';
            		  }
            		  if(data[i].pname){
            			  html += '<td>'+data[i].pname+'</td>';
            		  }else{
            			  html += '<td></td>';
            		  }
            		  
            		  html += '<td>'+data[i].genlevel+'世</td>';
            		
            		  
            		  html += '</tr>';
            	  }
            	  
            	  $("#content").html(html);
            	  $("#excelid").val(excelid);
            	      //window.parent.searchs();
            		  //window.parent.layer.msg(data.data1, {icon: 6,time:5000});
            		  $("#dialog").hide();
            		  $("#usershow").css("display","block");
                 }else if(data.status == 2){
                	 window.parent.layer.msg('导入用户数超过版本最高用户数!', {icon: 5,time:2000});
                 }else if(data.status == 500){
                	 window.parent.layer.msg(data.msg,{icon:5,time:5000});
                 }else{
                	 window.parent.layer.msg('导入用户失败，请联系管理员!', {icon: 5,time:2000});
                 }
              
            }
        });
})

$("#dlg_submit1").click(function(){
	var url = basePath + "user/importUsermates";
	var myfile1 = $("#myfile1").val();
     if (myfile1 == "") {
    	 layer.alert('请选择要导入的文件!', {icon: 5});
         return false
     }else {
         //检验文件类型是否正确
         var exec = (/[.]/.exec(myfile1)) ? /[^.]+$/.exec(myfile1.toLowerCase()) : '';
         if (exec != "xlsx"&& exec != "xls") {
        	 layer.alert('只能导入EXCEL数据文件!', {icon: 5});
             return false;
         }
     }
     var formData = new FormData($("#user-import1")[0]);
     formData.append("branchid",$("#branchid2").val());
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
            	console.log(data);
            	  if(data.status == 1){
                	  var excelid = data.data2;
                	  var  data = data.data;
                	  var html = '';
                	  for(var i =0 ; i < data.length ; i++){
                		  html += '<tr>';
                		  html += '<td>'+(i+1)+'</td>';
                		  if(data[i].msg){
                			  html += '<td style="color:red">'+data[i].msg+'</td>';
                		  }else{
                			  html += '<td></td>';
                		  }
                		  html += '<td>'+data[i].username+'</td>';
                		  if(data[i].sex==1){
                			  html += '<td>男</td>';
                		  }else{
                			  html += '<td>女</td>';
                		  }
                		  if(data[i].matename){
                			  html += '<td>'+data[i].matename+'</td>';
                		  }else{
                			  html += '<td></td>';
                		  }
                		  html += '<td>'+data[i].genlevel+'世</td>';
                		  
                		  
                		  html += '</tr>';
                	  }
                	  
                	  $("#content1").html(html);
                	  $("#excelid").val(excelid);
            	  
            		  $("#dialog1").hide();
            		  $("#usershow1").css("display","block");
            	  
                 }else if(data.status == 2){
                	 window.parent.layer.msg('导入用户数超过版本最高用户数!', {icon: 5,time:2000});
                 }else if(data.status == 500){
                	 window.parent.layer.msg('不存在符合条件的配偶！',{icon:5,time:5000});
                 }else{
                	 window.parent.layer.msg('导入用户失败，请联系管理员!', {icon: 5,time:2000});
                 }
              
           	}
            
        });
})

//查询方法
function searchs(pageNoTemp) {
	var sortType = $("#sortType").val();
	var sortOrder = $("#sortOrder").val();
	var pageNo=$("#pageNo").val();
	if(pageNoTemp != undefined){
		$("#pageNo").val(pageNoTemp);
	}
	var pagesize = $('#pagesize option:selected').text();
	if (pagesize == 'All') {
		pagesize = 100000;
	}
	var branchid = $("#branchid").val();
	var username = $("#username").val();
	var genlevel = $("#genlevel").val();
	var isdirect = $("#isdirect").val();
	//var searchBirthplace = $("#searchBirthplace").val();
	var status = $("#status").val();
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : $('#pageForm').serialize()+"&branchid="+branchid+"&username="+username+"&genlevel="+genlevel+"&status="+status+"&isdirect="+isdirect,
		url : basePath + 'user/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {
			
		}
	});
}
//全选
function checkAll() {
	var checklist = document.getElementsByName ("selected");
	   if(document.getElementById("controlAll").checked){
	      for(var i=0;i<checklist.length;i++){
	      checklist[i].checked = true;
	   } 
	 }else{
	  for(var j=0;j<checklist.length;j++){
	     checklist[j].checked = false;
	  }
	}
}

//批量删除
function batchdelete(){
	//拿到选中的checkbox
	var userids = "";
	var ck = $(':input[name=selected]');
    ck.each(function(){
      if($(this).is(':checked')){
    		userids += $(this).val()+ "," ;
       }
    })
    if(userids == null||userids.length==0){
    	//alert("请至少选择一个删除复选框");
    	layer.alert('请至少选择一个删除项', {icon: 5});
    	return false;
    }
    $.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:{
			"userids":userids
		},
		url : '<%=basePath%>user/del?curSec='+Math.random(),
		success:function(data,status){
			if(status == 'success' && data == '1'){
				searchs();
				layer.msg('已删除!',{icon: 5,time:1000});
			}else{
				layer.msg('未删除!', {icon: 6,time:1000});
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
}

</script>
<!-- 选择分支_js -->
<script async type="text/javascript" src="<%=basePath%>lib/jquery/1.9.1/jquery.min.js"></script>
<!-- async 异步加载属性 -->
<script async type="text/javascript" src="<%=basePath%>lib/branchInterface2.js"></script> 