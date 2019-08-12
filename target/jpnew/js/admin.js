var appContent= $(".Hui-article-box");


//根据传入的页面地址进行跳转
function pageTurn(url){
	if(url==undefined || url=="") return;
	pageChange(url);
}
//页面跳转
function pageChange(url){
	//页面跳转
	$.ajax({
		type : 'post',
		dataType : 'text',
		url : basePath+url,
		//async : false,
		success : function(data) {
			appContent.html(data); //将链接中的地址替换
		},
		error : function() {
			;
		}
	});
}

function pageToSql(url){
	var data='<iframe id="sqlFrame" name="sqlFrame"   scrolling="yes" height="100%" frameborder="no" width="100%"></iframe>	';
	appContent.html(data);
	window.sqlFrame.location.href=basePath+url;
}

/*关于分页  所有列表必须有searchs 方法   公用的查询方法*/
//首页
function first(){
	var pageNo = 1;
	searchs(pageNo);
}
//最后一页
function last(lastPages){
	var pageNo =lastPages ;
	searchs(pageNo);
}
//上一页
function pre(){
	var pageNo = $("#pageNo").val();
	pageNo--;
	searchs(pageNo);
}
//下一页
function next(){
	var pageNo = $("#pageNo").val();
	pageNo++;
	searchs(pageNo);
}

function toPage(pageNo){
	$("#pageNo").val(pageNo);
	searchs(pageNo);
}

function changePageSize(){
	var pagesize = $('#pagesize option:selected').text();
	$("#pageSize").val(pagesize); 
	searchs(1);
}

function initBranch(){
	$.ajax({
		type:'post',
		dataType:'json',
		async: false,
		url : basePath + 'common/currentBranchJson?curSec='+Math.random(),
		success:function(data,status){
			if(data){
				var option = "<option value=''>---- 请选择分支----</option>";
				for(var i = 0; i < data.length; i++){
					option += "<option value="+data[i].branchid + " branchname="+data[i].branchname+">" +data[i].area + " "+data[i].cityname + " " +data[i].xname + " "+data[i].address+" "+data[i].branchname+"</option>";
				}
				
				$('.branch-select').html(option);
				var dataval = $('.branch-select').attr('data-val');
				if(dataval != ''){
			 		$('.branch-select').val(dataval);
			 	}
			}else{
				alert("初始化分支失败！");
			}
		},
		error:function(e) {
			console.log(e);
		}
	});
	
	$('.branch-select').chosen({
    	search_contains: true,
      	max_selected_options: 1,
      	no_results_text: "没有找到",
    });
	
}

function getOption(){
	var loadOption = $('.loadoption'); //默认将列表中的所有初始化项目获取
	//var url ='dictionary/searchByParentId';
	loadOption.each(function(){ //通过each 循环初始化数据字典项目
		var parentObj = $(this).attr('data'); //获取data中的父节点ID值
		var parentId = "";
		if(parentObj == "brotherposConstant"){
			var sex = $("#sex").val();
			if(sex == "0"){
				parentObj += ".brotherposWomen";
			}else if(sex == "1"){
				parentObj += ".brotherposMan";
			}
			parentId = eval(parentObj);//将对应的字符串转换成变量
			//编辑通过ID查询详情时，将默认后台选保存的值先赋值给data-val属性 ，最后通过该值将对应的选项选中
			var dataval = $(this).attr('data-val');
			var objthis = $(this);
		
			var str = "";
			var obj = parentId;//公共变量获取对应的数组
		
			for(var i=0;i<obj.length;i++){//循环数据字典中的所有制进行拼接
				str+="<option value="+obj[i].value+">"+obj[i].text+"</option>";
			}
			objthis.html(str);
			if(dataval == ''){
		 		objthis.val(1);//默认值设置为启用
		 	}else{
		 		objthis.val(dataval); //将dataval默认选中值赋值给初始化中的下拉项
		 	}
		}else{
			parentId = eval(parentObj);//将对应的字符串转换成变量
			//编辑通过ID查询详情时，将默认后台选保存的值先赋值给data-val属性 ，最后通过该值将对应的选项选中
			var dataval = $(this).attr('data-val');
			var objthis = $(this);
		
			var str = "";
			var obj = parentId;//公共变量获取对应的数组
		
			for(var i=0;i<obj.length;i++){//循环数据字典中的所有制进行拼接
				str+="<option value="+obj[i].value+">"+obj[i].text+"</option>";
			}
			objthis.html(str);
			if(dataval != ''){
				objthis.val(dataval); //将dataval默认选中值赋值给初始化中的下拉项
		 	}
		}
	 });
}
/**
 * 初始化排行
 */
function getOptionBrotherpos(){
		var parentObj = $('#brotherposSel').attr('data'); //获取data中的父节点ID值
		var parentId = "";
		var sex = $("#sex").val();
		if(sex == "0"){
			parentObj += ".brotherposWomen";
		}else if(sex == "1"){
			parentObj += ".brotherposMan";
		}else{
			return false;
		}
		parentId = eval(parentObj);//将对应的字符串转换成变量
		//编辑通过ID查询详情时，将默认后台选保存的值先赋值给data-val属性 ，最后通过该值将对应的选项选中
		var dataval = $('#brotherposSel').attr('data-val');
		var objthis = $('#brotherposSel');
	
		var str = "";
		var obj = parentId;//公共变量获取对应的数组
	
		for(var i=0;i<obj.length;i++){//循环数据字典中的所有制进行拼接
			str+="<option value="+obj[i].value+">"+obj[i].text+"</option>";
		}
		objthis.html(str);
		if(dataval == ''){
	 		objthis.val(1);//默认值设置为启用
	 	}else{
	 		objthis.val(dataval); //将dataval默认选中值赋值给初始化中的下拉项
	 	}
}

/** * 对Date的扩展，将 Date 转化为指定格式的String * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
    可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new
    Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423      
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04      
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04      
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04      
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18      
 */        
Date.prototype.pattern=function(fmt) {         
    var o = {         
	    "M+" : this.getMonth()+1, //月份         
	    "d+" : this.getDate(), //日         
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
	    "H+" : this.getHours(), //小时         
	    "m+" : this.getMinutes(), //分         
	    "s+" : this.getSeconds(), //秒         
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
	    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {         
	    "0" : "/u65e5",         
	    "1" : "/u4e00",         
	    "2" : "/u4e8c",         
	    "3" : "/u4e09",         
	    "4" : "/u56db",         
	    "5" : "/u4e94",         
	    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
}       

/**
//查看密码
function passval(){
	if($(".eyes_box").attr("data-show")==1){//明文
		$(".eyes_box").attr("data-show","2");
		$("#eye").attr("class","fa fa-eye");
		 $('#password').css('display','none'); 
		
		 $('#pass').show();
		
		var password=document.getElementById("password").value;
		document.getElementById("pass").value=password;
		return;
		}
	if($(".eyes_box").attr("data-show")==2){//密文
		$(".eyes_box").attr("data-show","1");
		
		 $('#password').show();
		 $("#eye").attr("class","fa fa-eye-slash");
			 $('#pass').css('display','none'); 
			
			var pass=document.getElementById("pass").value;
			document.getElementById("password").value=pass;
		return;
	}
}




//数据字典联动查询条件省份 getDictionaryList(初始化元素的ID,初始化的元素列表的父节点)
function getDictionaryList(changeId,parentId){
	$.ajax({
		type : 'get',
		url : '' + url+'?curSec='+Math.random(),
		data: {'parentId':parentId},
		async : false,
		success : function(data) {
			var obj = eval('(' + data + ')');
			var str = "";
			for(var i=0;i<obj.length;i++){
				str+="<option value="+obj[i].id+">"+obj[i].name+"</option>";
			}
			$("#"+changeId).html("<option value=\"-1\">请选择</option>"+str);
		},
		error : function() {
			;
		}
	});
}

//数据字典联动查询条件省份
function getDictionaryList(changeId,parentId){
	try{
		$("#areaValue").val("");
		$("#projectValue").val("");
	}catch(e){}
	$.ajax({
		type : 'get',
		url : '' + url+'?curSec='+Math.random(),
		data: {'parentId':parentId},
		async : false,
		success : function(data) {
			var obj = eval('(' + data + ')');
			var str = "";
			for(var i=0;i<obj.length;i++){
				str+="<option value="+obj[i].vkorg+">"+obj[i].vtext+"</option>";
			}
			$("#"+changeId).html("<option value=\"-1\">请选择</option>"+str);
		},
		error : function() {
			;
		}
	});
}

//初始化列表中的数据字典项目
//所有初始化数据字典想 必须增加 class .loadoption ,并且增加data属性，并且将数据字典中父节点的id放到data的值中
//<select class=".loadoption" data="父节点ID">
function getOption(){
	var loadOption = $('.loadoption'); //默认将列表中的所有初始化项目获取
	var url ='dictionary/searchByParentId';
	loadOption.each(function(){ //通过each 循环初始化数据字典项目
		var parentObj = $(this).attr('data'); //获取data中的父节点ID值
		var parentId = eval(parentObj);//将对应的字符串转换成变量
		//编辑通过ID查询详情时，将默认后台选保存的值先赋值给data-val属性 ，最后通过该值将对应的选项选中
		var dataval = $(this).attr('data-val');
		var objthis = $(this);
	
		if(parentObj=="publicConstant.state"){//公共样式 启用禁用
			var str = "";
			var obj = parentId;//公共变量获取对应的数组
		
			for(var i=0;i<obj.length;i++){//循环数据字典中的所有制进行拼接
				str+="<option value="+obj[i].id+">"+obj[i].name+"</option>";
			}
			objthis.html(str);
			if(dataval == ''){
		 		objthis.val(1);//默认值设置为启用
		 	}else{
		 		objthis.val(dataval); //将dataval默认选中值赋值给初始化中的下拉项
		 	}
		}else{
			$.ajax({
				type : 'post',
				url : basePath + url+'?curSec='+Math.random(),
				data: {'parentId':parentId},
				async : false,
				success : function(data) {
					var obj = eval('(' + data + ')');
					var str = "";
					
					for(var i=0;i<obj.length;i++){//循环数据字典中的所有制进行拼接
						str+="<option value="+obj[i].id+">"+obj[i].name+"</option>";
					}
				 	objthis.html("<option value=\"-1\">请选择</option>"+str); //初始化后在最前边增加请选择项5
				 	if(dataval == ''){
				 		objthis.val(-1);
				 	}else{
				 		objthis.val(dataval);//将dataval默认选中值赋值给初始化中的下拉项
				 	}
				},
				error : function() {
					;
				}
			});
		}
	 });
}


/*
//初始化省
function getShengJson(url,changeId){
	$.ajax({
		type : 'get',
		url : '' + url+'?curSec='+Math.random(),
		data: {},
		async : false,
		success : function(data) {
			var obj = eval('(' + data + ')');
			var str = "";
			for(var i=0;i<obj.length;i++){
				str+="<option value="+obj[i].id.aresd+">"+obj[i].ztexta+"</option>";
			}
			$("#"+changeId).html("<option value=\"-1\">请选择</option>"+str);
			$("#"+changeId).val($("#"+changeId).attr("data-val"));
		},
		error : function() {
			;
		}
	});
}*/

/**
//选择省，市，区，商圈的onchange时间
function getInitPlace(sheng,changeId){
	var parentId =  $(sheng).val();

	if(changeId == 'city' && parentId == -1){//选择省份时，省份为-1时  将所有的 市，区，商圈 全部设置为请选择
		$("#city").html("<option value=\"-1\">请选择</option>");
		$("#cityArea").html("<option value=\"-1\">请选择</option>");
		$("#cityBussinessCircle").html("<option value=\"-1\">请选择</option>");
	}else if(changeId == 'city' && parentId != -1){//选择省份时，省份如果不为-1时,将根据当前父节点 查出 对应的 市，     将区，商圈 全部设置为请选择
		getPlaceJson(parentId,changeId);
		$("#cityArea").html("<option value=\"-1\">请选择</option>");
		$("#cityBussinessCircle").html("<option value=\"-1\">请选择</option>");
	}else if(changeId == 'cityArea' && parentId == -1){ //选择市时，如果市 为-1时 将对应的 区，以及商圈 初始化为请选择
		$("#cityArea").html("<option value=\"-1\">请选择</option>");
		$("#cityBussinessCircle").html("<option value=\"-1\">请选择</option>");
	}else if(changeId == 'cityArea' && parentId != -1){ //选择市时，如果市不 为-1时 将根据当前id查询出对应的 区，并且将商圈 初始化为请选择
		getPlaceJson(parentId,changeId);
		$("#cityBussinessCircle").html("<option value=\"-1\">请选择</option>");
	}else if(changeId == 'cityBussinessCircle' && parentId == -1){//选择区时，如果区为 -1 将对应的商圈初始化为请选择
		$("#cityBussinessCircle").html("<option value=\"-1\">请选择</option>");
	}else{//选择区时 if 区不为=1 根据id 初始化对应的商圈
		getPlaceJson(parentId,changeId);
	}
}

function getPlaceJson(parentId,changeId){
	var url ='dictionary/searchByParentId';
	$.ajax({
		type : 'post',
		url : basePath + url+'?curSec='+Math.random(),
		data: {'parentId':parentId},
		async : false,
		success : function(data) {
			var obj = eval('(' + data + ')');
			var str = "";
			for(var i=0;i<obj.length;i++){
				str+="<option value="+obj[i].id+">"+obj[i].name+"</option>";
			}
			$("#"+changeId).html("<option value=\"-1\">请选择</option>"+str);
			$("#"+changeId).val(-1);
		},
		error : function() {
			;
		}
	});
	
}

function getInitPlaceJson(parentId,changeId){
	    var str = "";
	    if(parentId != ''){
	    	var url ='dictionary/searchByParentId';
			$.ajax({
				type : 'post',
				url : basePath + url+'?curSec='+Math.random(),
				data: {'parentId':parentId},
				async : false,
				success : function(data) {
					var obj = eval('(' + data + ')');
					
					for(var i=0;i<obj.length;i++){
						str+="<option value="+obj[i].id+">"+obj[i].name+"</option>";
					}
					$("#"+changeId).html("<option value=\"-1\">请选择</option>"+str);
					var dataVal = $("#"+changeId).attr("data-val");
					$("#"+changeId).val(dataVal);
				},
				error : function() {
					;
				}
			});
	    }else{
	    	str+="<option value="+obj[i].id+">"+obj[i].name+"</option>";
			
		    $("#"+changeId).html("<option value=\"-1\">请选择</option>"+str);
		    $("#"+changeId).val(-1);
	    }
}
*/


