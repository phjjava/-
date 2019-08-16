<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/webuploader/0.1.5/webuploader.css"  />
<article class="page-container">
	<form class="form form-horizontal" id="photo_edit">
	    <input type="hidden" value="${branchphoto.imgid}" name="imgid">
	    <input type="hidden" value="${branchphoto.albumid}" name="albumid">
	    <input type="hidden" value="${branchphoto.branchid}" name="branchid">
	    <%-- <input typr="hidden" value="${returnTable}" name="returnTable"> --%>
		<div class="row cl">
			<label class="form-label col-xs-3 col-sm-1" style="text-align:right">相片描述：</label>
			<div class="formControls col-xs-8 col-sm-10">
				<textarea name="description" style="width:100%;height:100px; border:1px solid #dddddd">${branchphoto.description }</textarea>
			</div>
		</div>

		<div class="row cl">
			<div class="col-xs-8 col-sm-10 col-xs-offset-3 col-sm-offset-1" style="text-align:right">
				<%-- <button onClick="photo_save('${userid}','${returnTable}');" class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button> --%>
				<input class="btn btn-primary radius" type="button" onclick="photo_save('${branchphoto.albumid}','${branchphoto.branchid}');" value="确认" style="width:120px">
				<button onClick="layer_close();" class="btn btn-default radius" type="button" style="width:120px">取消</button>
			</div>
		</div>
	</form>
</article>
<script type="text/javascript" src="<%=basePath%>lib/webuploader/0.1.5/webuploader.min.js"></script>
<script>
function photo_save(albumid,branchid){
	$.ajax({
		type:'post',
		dataType:'text',
		async: true,
		data:$("#photo_edit").serialize(),
		url : '<%=basePath%>album/mergeBranchPhoto?curSec='+Math.random(),
		success:function(data){
			if(data == "1"){
				layer.msg('编辑成功!', {icon: 6,time:1000});
				window.parent.refpage(albumid,branchid);
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
</script>

