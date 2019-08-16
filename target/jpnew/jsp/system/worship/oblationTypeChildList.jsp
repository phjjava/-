<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="com.jp.common.ConstantUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String downLoadUrl = ConstantUtils.JIAPU_DOWNLOAD_URL;
%>
<script type="text/javascript" src="<%=basePath%>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<nav class="breadcrumb"><i class="Hui-iconfont"></i> <a href="/" class="maincolor">首页</a> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">系统管理</span> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">祭品管理</span> 
	<span class="c-999 en">&gt;</span>
	<span class="c-666">管理子菜单</span> 
</nav>
<div class="Hui-article">
	<article class="cl pd-20">
		<div class="cl pd-5 bg-1 bk-gray">
			<span class="l">
			<a class="btn btn-primary radius" href="javascript:;" onclick="function_add('新增祭品','<%=basePath%>system/oblation/edit','800')">
				<i class="Hui-iconfont">&#xe600;</i> 新增祭品
			</a>
			<a class="btn btn-primary radius" href="javascript:;" onclick="searchs();">
				<i class="Hui-iconfont">&#xe66b;</i> 返回
			</a>
			</span>
		</div>
		<input type="hidden" id="oblationtypeid" name="oblationtypeid" value="${oblationtypeid }"/>
		<div class="mt-20">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
				<table class="table table-border table-bordered table-hover table-bg">
					<thead>
						<tr>
							<th scope="col" colspan="5">管理子菜单</th>
						</tr>
						<tr class="text-c">
							<th width="30">序号</th>
							<th width="200">名称</th>
							<th width="00">祭拜时常</th>
							<th width="00">价格</th>
							<th width="90">图片</th>
							<th width="50">排序</th>
							<th width="50">状态</th>
							<th width="00">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="worshipOblation" items="${childList}" varStatus="status">
							<tr class="text-c">
								<td>${status.index + 1 }</td>
								<td>${worshipOblation.oblation }</td>
								<td>${worshipOblation.duration }分钟</td>
								<td>${worshipOblation.price }</td>
								<td>
						    	<img src="<%=downLoadUrl%>${worshipOblation.imgurl}" style="width:190px;height:150px;background-color:#f8f8f8;margin:15px 7px 0 7px;" >
								</td>
								<td>${worshipOblation.sort }</td>
								<td><c:if test="${worshipOblation.deleteflag == 1 }">
								    <span class="label label-default radius">停用</span>
								</c:if>
								<c:if test="${worshipOblation.deleteflag == 0 }">
									<span class="label label-success radius">启用</span>
								</c:if></td>
								<td class="f-14">
									<a href="javascript:;" onclick="oblation_edit('编辑','${worshipOblation.id}')">编辑</a>&nbsp;|
									<a href="javascript:;" onclick="oblation_del('${worshipOblation.id}')">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<jsp:include page="../../common/basepage.jsp"></jsp:include>
			</div>
		</div>
	</article>
</div>
<script type="text/javascript" src="<%=basePath%>lib/jquery.cookie.js"></script>
<script type="text/javascript">

/*菜单添加*/
function function_add(title,url,id,w,h){
	//alert($("#oblationtypeid").val());
	$.cookie("oblationtypeid",$("#oblationtypeid").val());
	layer_show(title,url,w,h);
}

/*菜单编辑*/
function oblation_edit(title,id,w,h){
	$.cookie("oblationtypeid",$("#oblationtypeid").val());
	var url = basePath + 'system/oblation/getOblation?id='+id+'&curSec='+Math.random();
	layer_show(title,url,w,h);
} 

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
	$.ajax({
		type : 'post',
		dataType : 'text',
		data :$('#pageForm').serialize(),
		url : basePath + 'system/oblationType/list?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {
			
		}
	});
}

function searchChildList(oblationtypeid){
	
	$.ajax({
		type : 'post',
		dataType : 'text',
		data : {"oblationtypeid":oblationtypeid},
		url : basePath + 'system/oblationType/childlist?curSec=' + Math.random(),
		//async : false,
		success : function(data) {
			//alert(data);
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {

		}
	});
	
	
}

/*菜单删除*/
function oblation_del(id){
	layer.confirm('确认删除吗？',function(index){
		$.ajax({
			type : 'post',
			dataType : 'text',
			data : {"id":id},
			url : basePath + 'system/oblation/del?curSec=' + Math.random(),
			//async : false,
			success : function(data,status) {
				//alert(data);
				if(status == 'success' && data == '1'){
					searchChildList($("#oblationtypeid").val());
					layer.msg('删除成功!', {icon: 6,time:1000});
				}else{
					layer.msg('删除失败!',{icon:1,time:1000});
				}
			},
			error : function() {

			}
		});
	});
}
</script>