
/**
 * 修改管理员登录密码
 */
$(function () {
    layui.use(['form' ,'layer'], function() {
        var form = layui.form;
        //确认修改密码
        form.on("submit(setPwd)",function () {
            setPwd();
            return false;
        });
    })

})

function setPwd() {
    var pwd = $('#pwd').val();
    var isPwd = $('#isPwd').val();

    $.ajax({
        type:'POST',
        url:'/adminUser/setPwd',
        data:{
            pwd:pwd,
            isPwd:isPwd
        },
        success:function (data) {
            if(data.code=="1"){
                layer.alert(data.message,function () {
                    layer.closeAll();
                    window.location.href="/logout";
                });
            }else{
                layer.alert(data.message,function () {
                    layer.closeAll();
                });
            }
        }
    })
}
