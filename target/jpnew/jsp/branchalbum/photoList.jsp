<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.jp.common.ConstantUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String downLoadUrl = ConstantUtils.JIAPU_DOWNLOAD_URL;
%>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>amaze/css/amazeui.chosen.css" />
<style>
.xuanxiang-tab{overflow:hidden;padding:0;margin:0;background-color:#f5fafe;border-bottom:2px solid #5a98de}
.xuanxiang-tab li{float:left; width:120px;height:35px;line-height:35px; text-align:center;font-size:16px;cursor:pointer}
</style>
<link rel="stylesheet" href="<%=basePath%>lib/lightbox2/2.8.1/css/lightbox.css">
<article class="page-container">
 <div style="font-size:14px;" id=""> 
  	   	<div style="overflow:hidden;font-size:16px;">
  	   	     <div style="float:left;"><span style="color:#999999;">相册标题：</span>${branchalbum.albumname}</div>
  	   	     <div style="float:right;"><fmt:formatDate value="${branchalbum.createtime}" pattern="yyyy-MM-dd"/></div>
  	   	</div>
  	   	<div style="color:#666666;font-size:16px;"><span style="color:#999999;">相册简介：</span>${branchalbum.remark}</div> 
       <div style="overflow:hidden"  id="imgGroup">
          <c:forEach var="photo" items="${photoList}" varStatus="status">
            <div style="float:left">
                 <div style="position:relative;">
                 	<a href="<%=downLoadUrl%>${photo.imgurl}" data-lightbox="example-set" title="${photo.description }">
                      <img src="<%=downLoadUrl%>${photo.imgurl}" style="width:190px;height:150px;background-color:#f8f8f8;margin:15px 7px 0 7px;" >
                      <!-- <input  style="position:absolute;top:15px;left:175px;width:18px;height:18px;" id="shanchu" type="checkbox"/> -->
                    </a>
                 </div>
                 <div style="overflow:hidden;background-color:#f5fafe;height:30px;margin:0 7px;padding:5px">
                      <div style="float:left;width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                      		<c:set var='pname' value="${photo.description}" scope="page"/> 
				            <c:if test="${fn:length(pname) > 15}">   
				                    ${fn:substring(pname,0,15)}...  
				            </c:if>  
				            <c:if test="${fn:length(pname) <= 15}">  
				                ${photo.description }  
				            </c:if>                       
                      </div>
                      <img src="<%=basePath %>images/bianji.png" style="float:right;cursor:pointer;margin-left:5px;margin-top:3px;width:12px;" onclick="photo_edit('编辑照片','${photo.branchid}','${photo.albumid }','${photo.imgid }','500','250')">
                      <img src="<%=basePath %>images/shanchu.png" style="float:right;cursor:pointer;width:14px;margin-top:3px;"  onclick="photo_del(this,'${photo.branchid}','${photo.imgid }','${photo.albumid }')">
                 </div>
            </div>
          </c:forEach>
       </div>
      </div>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/amazeui.chosen.min.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=basePath%>lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="<%=basePath%>lib/lightbox2/2.8.1/js/lightbox.js"></script>
<script type="text/javascript">
	 function refpage(albumid,branchid){
	    var url = basePath + 'album/showPhoto?albumid='+albumid+'&branchid='+branchid+'&curSec='+Math.random();
		window.location.href = url;
	 }
     function photo_del(obj,branchid,imgid,albumid){
		layer.confirm('确认要删除吗？',function(){
			$.ajax({
				type:'post',
				dataType:'text',
				async: true,
				data:"branchid="+branchid+"&albumid="+albumid+"&imgid="+imgid,
				url : '<%=basePath%>album/deletPhoto?curSec='+Math.random(),
				success: function(data){
					if(data == '1'){
						$(obj).parent().parent().remove();
						layer.msg('已删除!',{icon:1,time:1000});
						window.parent.searchs();
					}
				},
				error:function(data) {
					console.log(data.msg);
				},
			});		
		});
     }
     //给单张照片增加说明
    function photo_edit(title,branchid,albumid,imgid,w,h){
	 	var url = basePath + 'album/editbranchphoto?branchid='+branchid+'&albumid='+albumid+'&imgid='+imgid+'&curSec='+Math.random();
	 	layer_show(title,url,w,h);
    }
</script>
