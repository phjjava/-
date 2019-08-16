<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	window.UEDITOR_HOME_URL ="<%=basePath%>lib/ueditor/1.4.3/";
</script>
<article class="page-container">
   <form action=""class="form form-horizontal" enctype="multipart/form-data" method="post" id="instruction_save">
    <input type="hidden" name="instructionid" value="${instruction.instructionid}"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">家训：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<script id="editor" type="text/plain" value="${instruction.instructioncontent}" style="width:100%;height:400px;"></script> 
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<a onclick="instruction_save();" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe632;</i> 保存</a>
			</div>
		</div>
   </form>
</article>

<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/webuploader/0.1.5/webuploader.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/ueditor/1.4.3/ueditor.all.min.js"> </script> 
<script type="text/javascript" src="<%=basePath%>lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">

function instruction_save(){
	var formData = new FormData($("#instruction_save")[0]);
	var ue = UE.getEditor('editor');
    var instructioncontent = ue.getContent();
    formData.append('instructioncontent',instructioncontent);
	$.ajax({
		 type:"post",
		 dataType:"text",
		 async: true,
		 data:formData,
		 url : '<%=basePath%>instruction/save?curSec='+Math.random(),
		 processData : false, 
		 contentType : false,
		 success:function(data,status){
			 if(status=="success"&&data=="1"){
				window.parent.layer.msg('保存成功!', {icon: 6,time:1000});
			 }else{
				window.parent.layer.msg('保存失败!',{icon:1,time:1000});
			 }
		 },
		 error:function(e){
			 console.log(e);
		 }
	  });
}
$(function(){
	
	//回显副文本编辑器
 	var ue = UE.getEditor('editor');
	ue.ready(function(){
		var instructioncontent = '${instruction.instructioncontent}';
		ue.setContent(instructioncontent);
		ue.setAutoHeightEnabled(true);
	}); 
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
});
</script>