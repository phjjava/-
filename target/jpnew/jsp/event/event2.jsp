<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../common/basecss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/webuploader/0.1.5/webuploader.css"  />

<article class="page-container">
	<form class="form form-horizontal" enctype="multipart/form-data" method="post" id="event_add">
	  <input type="hidden" name="eventid" value="${event.eventid}"/>
	  <input type="hidden" value="${event.imgurl}" name="imgurl" id="imgurl">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${event.eventtitle}" id="eventtitle" name="eventtitle">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">图片上传：</label>
			<div id="TCrop">
				
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">内容：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<script id="editor" value="${event.eventcontent}" name="" type="text/plain" ></script> 
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<input class="btn btn-primary radius" type="button" onclick="eventSave();" value="&nbsp;&nbsp;确认&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>lib/webuploader/0.1.5/webuploader.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/ueditor/1.4.3/ueditor.all.min.js"> </script> 
<script type="text/javascript" src="<%=basePath%>lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript">
  function eventSave(){
	  var formData = new FormData($("#event_add")[0]);
	  var ue = UE.getEditor('editor');
      var eventcontent = ue.getContent();
	  formData.append('eventcontent',eventcontent);
	  $.ajax({
		 type:"post",
		 dataType:"text",
		 async: true,
		 data:formData,
		 url : '<%=basePath%>event/save?curSec='+Math.random(),
		 processData : false, 
		 contentType : false,
		 success:function(data,status){
			 if(status=="success"&&data=="1"){
			    window.parent.searchs();
				window.parent.layer.msg('保存成功!', {icon: 6,time:1000});
				layer_close();
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
		var ue = UE.getEditor('editor',{initialFrameWidth :850,
			initialFrameHeight:350});
		ue.ready(function(){
			var eventcontent = '${event.eventcontent}';
			ue.setContent(eventcontent);
		});
	 
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$list = $("#fileList"),
	$btn = $("#btn-star"),
	state = "pending";
	
	
});
 
</script>
<script>
/* (function( $ ){ */
	$(function(){
		Crop.init({
	        id: 'TCrop',
	        /* 上传路径 */
	        url: '<%=basePath%>event/savePhoto',
	        /* 允许上传的图片的后缀 */
	        allowsuf: ['jpg', 'jpeg', 'png', 'gif'],
	        /* JCrop参数设置 */
	        cropParam: {
	            minSize: [95, 27],          // 选框最小尺寸
	            maxSize: [2222, 2222],        // 选框最大尺寸
	            allowSelect: true,          // 允许新选框
	            allowMove: true,            // 允许选框移动
	            allowResize: true,          // 允许选框缩放
	            dragEdges: true,            // 允许拖动边框
				aspectRatio:95/27,
	            onChange: function(c) {
					
				},   // 选框改变时的事件，参数c={x, y, x1, y1, w, h}
	            onSelect: function(c) {}    // 选框选定时的事件，参数c={x, y, x1, y1, w, h}
	        },
	        /* 是否进行裁剪，不裁剪则按原图上传，默认进行裁剪 */
	        isCrop: true,
	        /* 图片上传完成之后的回调，无论是否成功上传 */
	        onComplete: function(data) {
	            console.log(JSON.stringify(data));
	        }
	    });
	})
	
/* })( jQuery ); */
</script>
