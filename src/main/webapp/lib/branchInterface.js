$(function(){
    $.ajax({
        type:'post',
        dataType:'json',
        async: false,
        url : ' http://localhost:8080/jpweb/user/selectUserItem?curSec='+Math.random(),
        success:function(data,status){
            localStorage.setItem("website", JSON.stringify(data));
        },
        error:function(e) {
            console.log(e);
        }
    });
})


