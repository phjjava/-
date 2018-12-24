<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="com.jp.common.ConstantUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String downLoadUrl = ConstantUtils.JIAPU_DOWNLOAD_URL;
%>

<jsp:include page="../common/basecss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/webuploader/0.1.5/webuploader.css"  />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui_select.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />
<article class="page-container">
	<form class="form form-horizontal" id="banner_add">
	  <input type="hidden" id="bannerid" name="bannerid" value="${banner.bannerid}"/>
	  <input type="hidden" id="goname" name="goname"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${banner.bannername}" id="bannername" name="bannername">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${banner.bannerdesc}" id="bannerdesc" name="bannerdesc">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">手机图片地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" disabled value="${banner.bannerphoneurl}" id="bannerphoneurl" name="bannerphoneurl">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">网页图片地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" disabled value="${banner.bannerweburl}" id="bannerweburl" name="bannerweburl">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">跳转类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<%-- <input type="text" class="input-text"  value="${banner.gotype}" id="gotype" name="gotype"> --%>
				<select class="loadoption select" size="1" onchange="initGoType(this.value);" data="goTypeConstant.goTypeStr" data-val="${banner.gotype}" name="gotype" id="gotype">
				</select>
			</div>
		</div>
		
		<div class="row cl" id="urlId">
			<label class="form-label col-xs-4 col-sm-2">跳转地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="goType select" size="1"  data-val="${banner.bannerurl}" name="bannerurlId" id="gotypeUrl">
				</select>
			</div>
		</div>
		<div class="row cl" id="url">
			<label class="form-label col-xs-4 col-sm-2">跳转地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  value="${banner.bannerurl}" id="bannerurl" name="bannerurl">
			</div>
		</div>
		<div class="row cl" id="showImg">
			<label class="form-label col-xs-4 col-sm-2">预览：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<img src="" height="200" width="200"  id="preImg" />
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">图片上传：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div class="uploader-list-container"> 
					<div class="queueList">
						<div id="dndArea" class="placeholder">
							<div id="filePicker-2"></div>
							<p style="margin-bottom:90px">仅支持单张上传，图片格式为jpg或png，图片比例2：1（建议尺寸1000×500px） ,大小不超过50M</p>
						</div>
					</div>
					<div class="statusBar" style="display:none;">
						<div class="progress"> <span class="text">0%</span> <span class="percentage"></span> </div>
						<div class="info"></div>
						<div class="btns">
							<div id="filePicker2"></div>
							<div class="uploadBtn">开始上传</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2" style="text-align:right">
				<button onclick="banner_save();" class="btn btn-primary radius" style="width:120px;"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
				<button onclick="layer_close();" class="btn btn-default radius" style="width:120px;">取消</button>
			</div>
		</div>
	</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>
<script>
//全局变量后期通过服务器启动来初始化
/* var imageServer='http://192.168.20.1:8080/fileupload/jsp/controller.jsp?action=uploadimage&encode=utf-8';
var imgPreUrl='http://192.168.20.1:8080/fileupload';  */
var imageServer='http://59.110.174.146:8081/fileupload/jsp/controller.jsp?action=uploadimage&encode=utf-8';
//var imageServer='http://192.168.0.69:8080/fileupload/jsp/controller.jsp?action=uploadimage&encode=utf-8';
//var imgPreUrl='http://192.168.0.69:8080/fileupload/fileupload';  
var imgPreUrl='<%=downLoadUrl%>';  
//var fileServer='http://192.168.0.66:8080/fileupload/jsp/controller.jsp';


function preShowIMG(){
	var pUrl=$("#bannerphoneurl").val();
	if(pUrl!=null&&pUrl!=''){
		var imgSRC=imgPreUrl+pUrl;
		setTimeout(function () {
		 	$("#preImg").attr("src",imgSRC);	
			$("#preImg").show();
		}, 1500);
	}else{
		$("#preImg").hide();
	}	
}
//初始化跳转地址
function initGoType(goType){
	var type = $("#gotype").val();
	if(type == '8'){
		$("#urlId").hide();
		$("#gotypeUrl").val("");
		$('#gotypeUrl').attr('data-val','');
		$("#url").show();
	}else{
		$("#urlId").show();
		$("#url").hide();
		$("#bannerurl").val("");
	}
	if(type != '8'){
		$.ajax({
			type:'post',
			dataType:'json',
			async: false,
			url : basePath + 'banner/bannerJson?goType='+goType+'&curSec='+Math.random(),
			success:function(data,status){
				if(data){
					var option = "<option value=''>---- 请选择跳转地址----</option>";
					
					for(var i = 0; i < data.length; i++){
						option += "<option value='"+data[i].id + "'>" +data[i].name;
						if(data[i].albumname){
							option += "&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].albumname;
						}
						if(data[i].date){
							option += "&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].date;
						}
						option += "</option>";
						
					}
					
					
					$("#gotypeUrl").empty();
					$('.goType').html(option);
					var dataval = $('#gotypeUrl').attr('data-val');
					if(dataval != ''){
				 		$('#gotypeUrl').val(dataval);
				 	}
					$("#gotypeUrl").chosen("destroy");
					$('#gotypeUrl').chosen({
				  	     search_contains: true,
				    	 max_selected_options: 1,
				    	 no_results_text: "没有找到",
				   });
				}else{
					alert("初始化失败！");
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
	}else{
		
	}
}
$(function(){
	//$('#bannerphoneurl').attr("disabled",true); 
	//$('#bannerweburl').attr("disabled",true); 
	var saveType = '${banner.gotype}';
	if(saveType != '8'){
		var type = $("#gotype").val();
		if(saveType == ""){
			initGoType('1');
		}else{
			initGoType(saveType);
		}
		$("#url").hide();
		$("#bannerurl").val("");
	}else{
		$("#urlId").hide();
		$("#gotypeUrl").val("");
	}
});
function banner_save(){
	var goname = $("#gotypeUrl").find("option:selected").text();
	if(goname == null || goname == ''){
		goname = $("#bannerurl").val();
	}
	$("#goname").val(goname);
 	if($("#banner_add").valid()){
 		//bannerphoneurl
 		$('#bannerphoneurl').removeAttr("disabled"); 
 		$('#bannerweburl').removeAttr("disabled"); 
		$.ajax({
			type:'post',
			dataType:'text',
			async: true,
			data:$("#banner_add").serialize(),
			url : '<%=basePath%>banner/save?curSec='+Math.random(),
			success:function(data,status){
				if(status == 'success' && data == '1'){
					window.parent.searchs();
					window.parent.layer.msg('保存成功!', {icon: 6,time:1000});
					layer_close();
				}else{
					window.parent.layer.msg('保存失败!',{icon:1,time:1000});
				}
			},
			error:function(e) {
				console.log(e);
			}
		});
 	}

}

$(function(){
	/* var gotype = $("#gotype").val();
	if(gotype != '8'){
		$("#bannerurl").attr("disabled", true);
	} */
	getOption();
	$("#banner_add").validate({
		rules:{
			bannername:{
				required:true,
				minlength:2,
				maxlength:50
			},
			bannerurl:{
				required:true,
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
		}
		
	}); 
	$('.skin-minimal input').click({
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
		server: imageServer,
	
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
      
});

$(function(){
  preShowIMG();
});

(function( $ ){
    // 当domReady的时候开始初始化
    $(function() {
        var $wrap = $('.uploader-list-container'),

            // 图片容器
            $queue = $( '<ul class="filelist"></ul>' )
                .appendTo( $wrap.find( '.queueList' ) ),

            // 状态栏，包括进度和控制按钮
            $statusBar = $wrap.find( '.statusBar' ),

            // 文件总体选择信息。
            $info = $statusBar.find( '.info' ),

            // 上传按钮
            $upload = $wrap.find( '.uploadBtn' ),

            // 没选择文件之前的内容。
            $placeHolder = $wrap.find( '.placeholder' ),

            $progress = $statusBar.find( '.progress' ).hide(),

            // 添加的文件数量
            fileCount = 0,

            // 添加的文件总大小
            fileSize = 0,

            // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,

            // 缩略图大小
            thumbnailWidth = 110 * ratio,
            thumbnailHeight = 110 * ratio,

            // 可能有pedding, ready, uploading, confirm, done.
            state = 'pedding',

            // 所有文件的进度信息，key为file id
            percentages = {},
            // 判断浏览器是否支持图片的base64
            isSupportBase64 = ( function() {
                var data = new Image();
                var support = true;
                data.onload = data.onerror = function() {
                    if( this.width != 1 || this.height != 1 ) {
                        support = false;
                    }
                }
                data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                return support;
            } )(),

            // 检测是否已经安装flash，检测flash的版本
            flashVersion = ( function() {
                var version;

                try {
                    version = navigator.plugins[ 'Shockwave Flash' ];
                    version = version.description;
                } catch ( ex ) {
                    try {
                        version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                                .GetVariable('$version');
                    } catch ( ex2 ) {
                        version = '0.0';
                    }
                }
                version = version.match( /\d+/g );
                return parseFloat( version[ 0 ] + '.' + version[ 1 ], 10 );
            } )(),

            supportTransition = (function(){
                var s = document.createElement('p').style,
                    r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                s = null;
                return r;
            })(),

            // WebUploader实例
            uploader;

        if ( !WebUploader.Uploader.support('flash') && WebUploader.browser.ie ) {

            // flash 安装了但是版本过低。
            if (flashVersion) {
                (function(container) {
                    window['expressinstallcallback'] = function( state ) {
                        switch(state) {
                            case 'Download.Cancelled':
                                alert('您取消了更新！')
                                break;

                            case 'Download.Failed':
                                alert('安装失败')
                                break;

                            default:
                                alert('安装已成功，请刷新！');
                                break;
                        }
                        delete window['expressinstallcallback'];
                    };

                    var swf = 'expressInstall.swf';
                    // insert flash object
                    var html = '<object type="application/' +
                            'x-shockwave-flash" data="' +  swf + '" ';

                    if (WebUploader.browser.ie) {
                        html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                    }

                    html += 'width="100%" height="100%" style="outline:0">'  +
                        '<param name="movie" value="' + swf + '" />' +
                        '<param name="wmode" value="transparent" />' +
                        '<param name="allowscriptaccess" value="always" />' +
                    '</object>';

                    container.html(html);

                })($wrap);

            // 压根就没有安转。
            } else {
                $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
            }

            return;
        } else if (!WebUploader.Uploader.support()) {
            alert( 'Web Uploader 不支持您的浏览器！');
            return;
        }


        // 实例化
        uploader = WebUploader.create({
            pick: {
                id: '#filePicker-2',
                label: '点击选择图片'
            },
            formData: {
                uid: 123
            },
            dnd: '#dndArea',
            paste: '#uploader',
            swf: 'lib/webuploader/0.1.5/Uploader.swf',
            chunked: false,
            chunkSize: 512 * 1024,
            server: '<%=basePath%>banner/savePhoto',
            // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
            disableGlobalDnd: true,
            fileNumLimit: 20,
            fileSizeLimit: 200 * 1024 * 1024,    // 200 M
            fileSingleSizeLimit: 50 * 1024 * 1024,    // 50 M
    		accept: {
    			title: 'Images',
    			extensions: 'gif,jpg,jpeg,bmp,png',
    			mimeTypes: 'image/*'
    		}
        });
        var flag = true;
        // 拖拽时不接受 js, txt 文件。
        uploader.on( 'dndAccept', function( items ) {
            var denied = false,
                len = items.length,
                i = 0,
                // 修改js类型
                unAllowed = 'text/plain;application/javascript ';

            for ( ; i < len; i++ ) {
                // 如果在列表里面
                if ( ~unAllowed.indexOf( items[ i ].type ) ) {
                    denied = true;
                    break;
                }
            }

            return !denied;
        });

        uploader.on('dialogOpen', function() {
            console.log('here');
        });

        // uploader.on('filesQueued', function() {
        //     uploader.sort(function( a, b ) {
        //         if ( a.name < b.name )
        //           return -1;
        //         if ( a.name > b.name )
        //           return 1;
        //         return 0;
        //     });
        // });

        // 添加“添加文件”的按钮，
         uploader.addButton({
            id: '#filePicker2',
            label: '换一张'
        });  
        
        uploader.on('ready', function() {
            window.uploader = uploader;
        });

        // 当有文件添加进来时执行，负责view的创建
        function addFile( file ) {
            var $li = $( '<li id="' + file.id + '">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>'+
                    '<p class="progress"><span></span></p>' +
                    '</li>' ),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    '<span class="rotateRight">向右旋转</span>' +
                    '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find( 'p.imgWrap' ),
                $info = $('<p class="error"></p>'),

                showError = function( code ) {
                    switch( code ) {
                        case 'exceed_size':
                            text = '文件大小超出';
                            break;

                        case 'interrupt':
                            text = '上传暂停';
                            break;

                        default:
                            text = '上传失败，请重试';
                            break;
                    }

                    $info.text( text ).appendTo( $li );
                };

            if ( file.getStatus() === 'invalid' ) {
                showError( file.statusText );
            } else {
                // @todo lazyload
                $wrap.text( '预览中' );
                uploader.makeThumb( file, function( error, src ) {
                    var img;

                    if ( error ) {
                        $wrap.text( '不能预览' );
                        return;
                    }

                    if( isSupportBase64 ) {
                        img = $('<img src="'+src+'">');
                        $wrap.empty().append( img );
                    } else {
                        $.ajax('lib/webuploader/0.1.5/server/preview.php', {
                            method: 'POST',
                            data: src,
                            dataType:'json'
                        }).done(function( response ) {
                            if (response.result) {
                                img = $('<img src="'+response.result+'">');
                                $wrap.empty().append( img );
                            } else {
                                $wrap.text("预览出错");
                            }
                        });
                    }
                }, thumbnailWidth, thumbnailHeight );

                percentages[ file.id ] = [ file.size, 0 ];
                file.rotation = 0;
            }

            file.on('statuschange', function( cur, prev ) {
            	if(flag){
            		if ( prev === 'progress' ) {
                        $prgress.hide().width(0);
                    } else if ( prev === 'queued' ) {
                        $li.off( 'mouseenter mouseleave' );
                        $btns.remove();
                    }

                    // 成功
                    if ( cur === 'error' || cur === 'invalid' ) {
                        console.log( file.statusText );
                        showError( file.statusText );
                        percentages[ file.id ][ 1 ] = 1;
                    } else if ( cur === 'interrupt' ) {
                        showError( 'interrupt' );
                    } else if ( cur === 'queued' ) {
                        percentages[ file.id ][ 1 ] = 0;
                    } else if ( cur === 'progress' ) {
                        $info.remove();
                        $prgress.css('display', 'block');
                    } else if ( cur === 'complete' ) {
                        $li.append( '<span class="success"></span>' );
                    }

                    $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
            	}
                
            });

            $li.on( 'mouseenter', function() {
                $btns.stop().animate({height: 30});
            });

            $li.on( 'mouseleave', function() {
                $btns.stop().animate({height: 0});
            });

            $btns.on( 'click', 'span', function() {
                var index = $(this).index(),
                    deg;

                switch ( index ) {
                    case 0:
                        uploader.removeFile( file );
                        return;

                    case 1:
                        file.rotation += 90;
                        break;

                    case 2:
                        file.rotation -= 90;
                        break;
                }

                if ( supportTransition ) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                    // use jquery animate to rotation
                    // $({
                    //     rotation: rotation
                    // }).animate({
                    //     rotation: file.rotation
                    // }, {
                    //     easing: 'linear',
                    //     step: function( now ) {
                    //         now = now * Math.PI / 180;

                    //         var cos = Math.cos( now ),
                    //             sin = Math.sin( now );

                    //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                    //     }
                    // });
                }


            });

            $li.appendTo( $queue );
        }
        // 负责view的销毁
        function removeFile( file ) {
            var $li = $('#'+file.id);

            delete percentages[ file.id ];
            updateTotalProgress();
            $li.off().find('.file-panel').off().end().remove();
        }

        function updateTotalProgress() {
            var loaded = 0,
                total = 0,
                spans = $progress.children(),
                percent;

            $.each( percentages, function( k, v ) {
                total += v[ 0 ];
                loaded += v[ 0 ] * v[ 1 ];
            } );

            percent = total ? loaded / total : 0;


            spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
            spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
            updateStatus();
        }

        function updateStatus() {
            var text = '', stats;

            if ( state === 'ready' ) {
                text = '选中' + fileCount + '张图片，共' +
                        WebUploader.formatSize( fileSize ) + '。';
            } else if ( state === 'confirm' ) {
                stats = uploader.getStats();
                if ( stats.uploadFailNum ) {
                    text = '已成功上传' + stats.successNum+ '张照片至XX相册，'+
                        stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
                }

            } else {
                stats = uploader.getStats();
                if(flag){
                	text = '共' + fileCount + '张（' +
                    WebUploader.formatSize( fileSize )  +
                    '），已上传' + stats.successNum + '张';

		            if ( stats.uploadFailNum ) {
		                text += '，失败' + stats.uploadFailNum + '张';
		            }
                }else{
                	 text = '已成功上传' + stats.successNum+ '张照片，'+
                     stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
                }
            }
            $info.html( text );
        }

        function setState( val ) {
            var file, stats;

            if ( val === state ) {
                return;
            }

            $upload.removeClass( 'state-' + state );
            $upload.addClass( 'state-' + val );
            state = val;

            switch ( state ) {
                case 'pedding':
                    $placeHolder.removeClass( 'element-invisible' );
                    $queue.hide();
                    $statusBar.addClass( 'element-invisible' );
                    uploader.refresh();
                    break;

                case 'ready':
                    $placeHolder.addClass( 'element-invisible' );
                    $( '#filePicker2' ).removeClass( 'element-invisible');
                    $queue.show();
                    $statusBar.removeClass('element-invisible');
                    uploader.refresh();
                    break;

                case 'uploading':
                    $( '#filePicker2' ).addClass( 'element-invisible' );
                    $progress.show();
                    $upload.text( '暂停上传' );
                    break;

                case 'paused':
                    $progress.show();
                    $upload.text( '继续上传' );
                    break;

                case 'confirm':
                    $progress.hide();
                    $( '#filePicker2' ).removeClass( 'element-invisible' );
                    $upload.text( '开始上传' );

                    stats = uploader.getStats();
                    if ( stats.successNum && !stats.uploadFailNum ) {
                        setState( 'finish' );
                        return;
                    }
                    break;
                  case 'finish':
                    stats = uploader.getStats();
                    if (stats.successNum ){
                    	if(flag){
                    		layer.msg('上传成功!', {icon: 6,time:1000});
                    	}else{
                    		layer.msg('上传失败!', {icon: 6,time:1000});
                    	}
                    } else {
                        // 没有成功的图片，重设
                        state = 'done';
                        location.reload();
                    } 
                    break; 
            }
            updateStatus();
        }
        

         uploader.onUploadProgress = function( file, percentage ) {
            var $li = $('#'+file.id),
                $percent = $li.find('.progress span');

            $percent.css( 'width', percentage * 100 + '%' );
            percentages[ file.id ][ 1 ] = percentage;
            updateTotalProgress();
        }; 
        
        uploader.onFileQueued = function( file ) {
            fileCount++;
            fileSize += file.size;

            if ( fileCount === 1 ) {
                $placeHolder.addClass( 'element-invisible' );
                $statusBar.show();
            }

            addFile( file );
            setState( 'ready' );
            updateTotalProgress();
        };

        uploader.onFileDequeued = function( file ) {
            fileCount--;
            fileSize -= file.size;

            if ( !fileCount ) {
                setState( 'pedding' );
            }

            removeFile( file );
            updateTotalProgress();

        };
        
        uploader.on("uploadAccept", function(object, ret){
            if(ret != null && ret != '') {
                var data = JSON.parse(ret._raw);
                if(data != null && data != '') {
                	//alert(data.result);
                    if(data == '0') {
                    	flag = false;
                    } else {
                    	$("#bannerphoneurl").val(data.iconUrl);
                    	$("#bannerweburl").val(data.url);
                        flag = true;
                        preShowIMG();
                    }
                }
            }
        });
        uploader.on( 'uploadComplete', function( file ) {  
            $( '#'+file.id ).find('.progress').remove();  
        }); 
        uploader.on( 'all', function( type ) {
            var stats;
            switch( type ) {
                case 'uploadFinished':
                    setState( 'confirm' );
                    break;

                case 'startUpload':
                    setState( 'uploading' );
                    break;

                case 'stopUpload':
                    setState( 'paused' );
                    break;

            }
        });

        uploader.onError = function( code ) {
            alert( 'Eroor: ' + code );
        };
        //上传事件
       
       	$upload.on('click', function() {
           if ( $(this).hasClass( 'disabled' ) ) {
               return false;
           }
           if ( state === 'ready' ) {
               uploader.upload();
           } else if ( state === 'paused' ) {
               uploader.upload();
           } else if ( state === 'uploading' ) {
               uploader.stop();
           } 
         
        }); 

        $info.on( 'click', '.retry', function() {
            uploader.retry();
        } );

        $info.on( 'click', '.ignore', function() {
            alert( 'todo' );
        } );

        $upload.addClass( 'state-' + state );
        updateTotalProgress();
    });
    
})( jQuery );
</script>
