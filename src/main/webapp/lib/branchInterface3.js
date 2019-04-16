$(function(){
    // 权限管理_管理员管理_添加用户_管理员
    console.log('异步执行website3')
    $.ajax({
        type:'post',
        dataType:'json',
        async: false,
        url : basePath+'user/selectUserItemLive?curSec='+Math.random(),
        success:function(data,status){
            console.log(data)
            localStorage.removeItem('website1')
            localStorage.removeItem('website2')
            localStorage.setItem("website3", JSON.stringify(data));
        },
        error:function(e) {
            console.log(e);
        }
    });
})