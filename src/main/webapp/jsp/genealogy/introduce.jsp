<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<article class="page-container">
   <form action=""class="form form-horizontal" enctype="multipart/form-data" method="post" id="introduce_save">
    <input type="hidden" name="introduceid" value="${introduce.introduceid}"/>
     <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${introduce.introducetitle}" id="introducetitle" name="introducetitle">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">排序值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${introduce.sort}" placeholder="" id="sort" name="sort">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">内容：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<!-- 加载编辑器 -->
				<div id="eWCen" style="display: none;">${introduce.introducedetail}</div>
				<div id="eWebEd" style="border: 1px solid #fefbfb;"></div>

				<!-- <script id="editor" type="text/plain" value="${introduce.introducedetail}" ></script>  -->
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2" style="text-align:right">
			    <input class="btn btn-primary radius" type="button" onclick="introduce_save();" value="确认" style="width:120px;">
				<input class="btn btn-default radius" type="button" onclick="layer_close();" value="取消" style="width:120px;">
			</div>
		</div>
   </form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/webuploader/0.1.5/webuploader.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/ueditor/1.4.3/ueditor.all.min.js"> </script> 
<script type="text/javascript" src="<%=basePath%>lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=basePath%>/ewebeditor/ewebeditor.js"></script>
<script type="text/javascript">

  // ------------------------编辑器-------------/------------
  var eWebEds = '<INPUT type="hidden" name="content1" value="" id="contentId">'
	eWebEds += '<iframe ID="eWebEditor1" src="<%=basePath%>ewebeditor/ewebeditor.htm?id=content1&style=coolblue"'
	eWebEds += 'frameborder="0" scrolling="no" width="100%" HEIGHT="350"></iframe>'
    $('#eWebEd').html(eWebEds);
// ------------------------编辑器-------------\------------

function introduce_save(){
	var formData = new FormData($("#introduce_save")[0]);
	// var ue = UE.getEditor('editor');
	// var introducedetail = ue.getContent();
	var introducedetail = document.getElementById("eWebEditor1").contentWindow.getHTML()
	

    formData.append('introducedetail',introducedetail);
	$.ajax({
		 type:"post",
		 dataType:"text",
		 async: true,
		 data:formData,
		 url : '<%=basePath%>introduce/save?curSec='+Math.random(),
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
	var eWCen = $('#eWCen').html();
	EWEBEDITOR.SetHtmlAsync("content1", eWCen);
	
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	$list = $("#fileList"),
	$btn = $("#btn-star"),
	state = "pending",
	uploader;

	var uploader = WebUploader.create({
		auto: true,
		swf: 'lib/webuploader/0.1.5/Uploader.swf',
	
		// 文件接收服务端。
		server: 'fileupload.php',
	
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick: '#filePicker',
	
		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		resize: false,
		// 只允许选择图片文件。
		accept: {
			title: 'Images',
			extensions: 'gif,jpg,jpeg,bmp,png',
			mimeTypes: 'image/*'
		}
	});
	uploader.on( 'fileQueued', function( file ) {
		var $li = $(
			'<div id="' + file.id + '" class="item">' +
				'<div class="pic-box"><img></div>'+
				'<div class="info">' + file.name + '</div>' +
				'<p class="state">等待上传...</p>'+
			'</div>'
		),
		$img = $li.find('img');
		$list.append( $li );
	
		// 创建缩略图
		// 如果为非图片文件，可以不用调用此方法。
		// thumbnailWidth x thumbnailHeight 为 100 x 100
		uploader.makeThumb( file, function( error, src ) {
			if ( error ) {
				$img.replaceWith('<span>不能预览</span>');
				return;
			}
	
			$img.attr( 'src', src );
		}, thumbnailWidth, thumbnailHeight );
	});
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
		var $li = $( '#'+file.id ),
			$percent = $li.find('.progress-box .sr-only');
	
		// 避免重复创建
		if ( !$percent.length ) {
			$percent = $('<div class="progress-box"><span class="progress-bar radius"><span class="sr-only" style="width:0%"></span></span></div>').appendTo( $li ).find('.sr-only');
		}
		$li.find(".state").text("上传中");
		$percent.css( 'width', percentage * 100 + '%' );
	});
	
	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file ) {
		$( '#'+file.id ).addClass('upload-state-success').find(".state").text("已上传");
	});
	
	// 文件上传失败，显示上传出错。
	uploader.on( 'uploadError', function( file ) {
		$( '#'+file.id ).addClass('upload-state-error').find(".state").text("上传出错");
	});
	
	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
		$( '#'+file.id ).find('.progress-box').fadeOut();
	});
	uploader.on('all', function (type) {
        if (type === 'startUpload') {
            state = 'uploading';
        } else if (type === 'stopUpload') {
            state = 'paused';
        } else if (type === 'uploadFinished') {
            state = 'done';
        }

        if (state === 'uploading') {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });

    $btn.on('click', function () {
        if (state === 'uploading') {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
	
	// var ue = UE.getEditor('editor');
	
});
</script>