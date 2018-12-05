function userLogin(){
			var name=$("#username").val().trim();
			var password=$("#password").val().trim();
			alert(name + "," + password);
			$("#username_span").html("");
			$("#password_span").html("");
			//格式检测
			var ok=true;
			if(name==""){
				$("#username_span").html("用户不能为空");
				ok=false;
			}
			if(password==""){
				$("#password_span").html("密码不能为空");
				ok=false;
			}	
			if(ok){  //检测格式通过
				//发送ajax请求
				$.ajax({
					url:path+"/login/checklogin",
					type:"post",
					data:{"name":name,"password":password},
					dataType:"json",
					success:function(result){
					//result是服务器返回的JSON结果
					if(result.status==0){
						//将用户信息保存到Cookie
						var userId=result.data.userid;
						addCookie("userId",userId,2);
						window.location.href="menu.jsp";
					}else if(result.status==1){ //用户名错
						$("#username_span").html(result.msg);
					}else if(result.status==2){
						$("#password_span").html(result.msg);
					}
					},
					error:function(){
						alert("登录失败!");
					}
				});
			}
		};

		
