$(function(){
    // 用户管理_成员管理_新增成员_父(母)亲
    console.log('异步执行website2')
    $.ajax({
        type:'post',
        dataType:'json',
        async: false,
        url : basePath+'user/selectPnameAndMate?curSec='+Math.random(),
        success:function(data,status){
            localStorage.removeItem('website1')
            localStorage.removeItem('website3')
            localStorage.setItem("website2", JSON.stringify(data));
        },
        error:function(e) {
            console.log(e);
        }
    });
})


