<!-- 相册详情 -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="com.jp.common.ConstantUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String downLoadUrl = ConstantUtils.JIAPU_DOWNLOAD_URL;
%>
<link rel="stylesheet" href="<%=basePath%>lib/lightbox2/2.8.1/css/lightbox.css">
<div style="margin-top:20px;">
      <a href="javascript:;" onclick="returnAblum('${type}')" class="btn btn-primary  radius"> <i class="Hui-iconfont">&#xe66b;</i>返回列表</a>&nbsp;&nbsp;
      <c:if test="${type == 0}">
	      
		    <a title="上传照片" href="javascript:;" onclick="photo_add('上传照片','${userAlbum.albumid }','','${userAlbum.type }','800','600')" class="btn btn-danger radius" style="text-decoration:none">
		      <i class="Hui-iconfont">&#xe600;</i>上传照片</a>
		  
      </c:if>
      <c:if test="${type == 1}">
	      <!-- <span class="l" style="margin-left:4px;"> -->
		    <a title="上传作品" href="javascript:;" onclick="photo_add('上传作品','${userAlbum.albumid }','','${userAlbum.type }','800','600')" class="btn btn-danger radius" style="text-decoration:none">
		      <i class="Hui-iconfont">&#xe600;</i>上传作品</a>
		<!--   </span>  -->
      </c:if>
</div>
<div style="margin-top:10px;color:#666666;overflow:hidden;">
	 <c:if test="${type == 0}">
       <div style="float:left;"><span style="color:#333333;">相册标题：</span>${userAlbum.albumname}</div>
	 </c:if>
	 <c:if test="${type == 1}">
       <div style="float:left;"><span style="color:#333333;">作品标题：</span>${userAlbum.albumname}</div>
	 </c:if>
	 <%-- <div style="float:right;"><span style="color:#333333;">创建时间：</span><fmt:formatDate value="${userAblum.createtime}" pattern="yyyy-MM-dd"/></div> --%>
</div>
<div style="color:#666666;">
    <c:if test="${type == 0}">
       <span style="color:#333333;">相册简介：</span>${userAlbum.remark}
    </c:if>
    <c:if test="${type == 1}">
       <span style="color:#333333;">作品简介：</span>${userAlbum.remark}
    </c:if>
</div> 
<div style="overflow:hidden"  id="imgGroup">
     <c:forEach var="photo" items="${photoList}" varStatus="status">
       <div style="float:left">
            <div style="position:relative;">
            <a href="<%=downLoadUrl%>${photo.imgurl}" data-lightbox="example-set" title="${photo.description }">
                 <img src="<%=downLoadUrl%>${photo.imgurl}" style="width:190px;height:150px;background-color:#f8f8f8;margin:15px 7px 0 7px;" >
            </a>
            </div>
            <div style="overflow:hidden;background-color:#f5fafe;height:30px;margin:0 7px;padding:5px">
                 <div style="float:left;width:130px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${photo.description }</div>                     
                 <img src="<%=basePath %>images/bianji.png" style="float:right;cursor:pointer;margin-left:5px;" onclick="userphoto_edit('编辑照片','${photo.imgid}','${photo.albumid }','${photo.userid }','${type}','550','200')">
                 <img src="<%=basePath %>images/shanchu.png" style="float:right;cursor:pointer;"  onclick="photo_del(this,'${photo.imgid}','${photo.albumid }','${photo.userid }')">
            </div>
       </div>
     </c:forEach>
</div>
<script src="<%=basePath%>lib/lightbox2/2.8.1/js/lightbox.js"></script>
<script>
function photo_add(title,albumid,userid,type,w,h){
	var userid = $("#userid").val();
	var url = basePath + 'user/toAddPhoto?userid='+userid+'&albumid='+albumid+'&type='+type+'&curSec='+Math.random();
	layer_show(title,url,w,h);
}
   function photo_del(obj,imgid,albumid,userid){
		layer.confirm('确认要删除吗？',function(){
			$.ajax({
				type:'post',
				dataType:'text',
				async: true,
				data:"userid="+userid+"&albumid="+albumid+"&imgid="+imgid,
				url : '<%=basePath%>user/deletPhoto?curSec='+Math.random(),
				success: function(data){
					if(data == '1'){
						$(obj).parent().parent().remove();
						layer.msg('已删除!',{icon:1,time:1000});
					}
				},
				error:function(data) {
					console.log(data.msg);
				},
			});		
		});
   }
   //给单张照片增加说明
   function userphoto_edit(title,imgid,albumid,userid,type,w,h){
	 	var url = basePath + 'user/edituserphoto?userid='+userid+'&albumid='+albumid+'&type='+type+'&imgid='+imgid+'&curSec='+Math.random();
	 	layer_show(title,url,w,h);
   }
  </script>