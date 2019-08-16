$(function(){
    // 用户管理_分支管理_新增分支_发起人
    $.ajax({
        type:'post',
        dataType:'json',
        async: false,
        url : basePath+'user/selectUserItem?curSec='+Math.random(),
        success:function(data,status){
            // localStorage是全局属性 存储为5MB 超过会报错，存之前需把其他的存储数据删除如“website2”
            localStorage.removeItem('website2')
            localStorage.removeItem('website3')
            localStorage.setItem("website1", JSON.stringify(data));
        },
        error:function(e) {
            console.log(e);
        }
    });
})


