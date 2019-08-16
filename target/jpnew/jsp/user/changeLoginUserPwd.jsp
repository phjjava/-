<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/basecss.jsp"></jsp:include>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="user-import" enctype="multipart/form-data">
		<input type="hidden" value="${userContext.user.userid }" id="userid" name="userid">
		<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-2">原始密码：</label> 
			<div class="formControls col-xs-8 col-sm-8">
				<!-- <input type='password' class="input-password w200 ml-8" id="oldpassword" name="oldpassword"/><div style="display: inline" id="tip1"></div> -->
				<!-- <input type="password" class="input-text w200 ml-15"  id="oldpassword" name="oldpassword"><div style="display: inline" id="tip1"> -->
				<input id="oldpassword" class="input-text valid" autocomplete="off" placeholder="密码" name="oldpassword" type="password">
				<span style="display: inline" id="tip1">
			</div>
		</div>	
		<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-2">新设密码：</label> 
			<div class="formControls col-xs-8 col-sm-8">
				<!-- <input type='password'class="input-text w200 ml-15" id="password1" name="password1" placeholder="长度必须6-18"/><div style="display: inline" id="tip2"> -->
				<input id="password1" class="input-text valid" autocomplete="off" placeholder="长度必须6-18" name="password1" type="password">
				<span style="display: inline" id="tip2">
			</div>
		</div>
		<div class="row cl" id="addNation">
			<label class="form-label col-xs-4 col-sm-2">确认密码：</label> 
			<div class="formControls col-xs-8 col-sm-8">
				<input id="password2" class="input-text valid" autocomplete="off" placeholder="必须与新设密码保持一致" name="password2" type="password">
				<span style="display: inline" id="tip3">
				<!-- <input type='password' id="password2" name="password2" placeholder="必须与新设密码保持一致"/><div style="display: inline" id="tip3"> -->
			</div>
		</div>	
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<div id="tip4"></div>
			</div>
	    </div>			
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<input class="btn btn-primary radius" type="button" id="btn" value="&nbsp;&nbsp;修改&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
			</div>
	    </div>
	</form>
</article>
<jsp:include page="../common/basejs.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>lib/jquery.base64.js"></script>
<script type="text/javascript">
$(document).ready(function(){                  
    $("#oldpassword").blur(function(){  
            var param=$("#oldpassword").val(); 
            param=encodeBase64(param);
            //alert(param+"输入密码");
            $.ajax({  
                url:"<%=basePath%>user/checkoldpassword?curSec="+Math.random(),  
                data:{oldpassword:param},                   
                success:function(e){  
                    if(e.status==1){                              
                         $("#tip1").html("<font color=\"green\" size=\"2\">Ok</font>");  
                    }   
                    else{                                   
                        $("#tip1").html("<font color=\"red\" size=\"2\">原始密码错误</font>");  
                    }  
                }                   
            });  
       });  
      $("#password1").blur(function(){  
          var num=$("#password1").val().length;  
          if(num<6){  
               $("#tip2").html("<font color=\"red\" size=\"2\">  密码过短</font>");                  
          }  
          else if(num>18){  
               $("#tip2").html("<font color=\"red\" size=\"2\">  密码过长</font>");                   
          }  
          else{  
              $("#tip2").html("<font color=\"green\" size=\"2\"> Ok</font>");                  
          }  
      }) ;  
      $("#password2").blur(function(){  
          var tmp=$("#password1").val();  
          var num=$("#password2").val().length;  
          if($("#password2").val()!=tmp){  
              $("#tip3").html("<font color=\"red\" size=\"2\">两次不一致</font>");                   
          }  
          else{  
              if(num>=6&&num<=18){  
                  $("#tip3").html("<font color=\"green\" size=\"2\">  Ok</font>");                      
              }                   
              else{  
                  $("#tip3").html("<font color=\"red\" size=\"2\">请按提示正确填写</font>");                             
              }                  
          }  
      });  
          $("#btn").click(function(){  
              var flag=true;  
              var old=$("#oldpassword").val();  
              var pass=$("#password1").val(); 
              var pass2=$("#password2").val();  
              var num1=$("#password1").val().length;  
              var num2=$("#password2").val().length;  
              if(num1!=num2||num1<6||num2<6||num1>18||num2>18||pass!=pass2){  
                  flag=false;  
              }  
              else{  
                  flag=true; 
                  pass=encodeBase64(pass);
                  old=encodeBase64(old);
              }  
              if(flag){
            	 // alert("提交修改密码请求");
              $.ajax({  
                  url:"<%=basePath%>/user/editPassowrd?curSec="+Math.random(),  
                  data:{oldpassword:old,password:pass},  
                  success:function(e){                           
                      if(e.status==1){  
                          $("#tip4").show().html("<font color=\"green\" size=\"3\">修改成功!</font>");  
                          $("#oldpassword").val("");  
                          $("#password1").val("");  
                          $("#password2").val("");  
                          $("#tip1").empty();  
                          $("#tip2").empty();  
                          $("#tip3").empty();  
                          $("#tip4").delay(2000).hide(0);   
                          window.parent.layer.msg('修改密码成功!', {icon: 6,time:2000});
                		  layer_close();
                      }  
                      else{  
                          $("#tip4").show().html("<font color=\"red\" size=\"3\">原始密码错误!</font>");  
                      }                                       
                     }  
              });  
          }  
          else{  
                
              $("#tip4").show().html("<font color=\"red\" size=\"3\">请按提示操作</font>");  
          }  
          });                    
    }); 
    
function encodeBase64(mingwen,times){    
    var code="";    
    var num=1;    
    if(typeof times=='undefined'||times==null||times==""){    
       num=1;    
    }else{    
       var vt=times+"";    
       num=parseInt(vt);    
    }    
    if(typeof mingwen=='undefined'||mingwen==null||mingwen==""){    
    }else{    
        $.base64.utf8encode = true;    
        code=mingwen;    
        for(var i=0;i<num;i++){    
           code=$.base64.btoa(code);    
        }    
    }    
    return code;    
};      
</script>
