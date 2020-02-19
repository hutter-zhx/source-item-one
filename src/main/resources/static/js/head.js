/**
 * 获取登录用户权限菜单
 * */
//获取路径uri
var pathUri=window.location.href;
$(function () {

    layui.use('element', function(){
        var element = layui.element;
        // 左侧导航区域（可配合layui已有的垂直导航）
        $.get("/adminPermission/getUserPerms",function(data){
            if(data!=null){
                getMenus(data.perm);
                element.render('nav');
            }else{
                layer.alert("权限不足，请联系管理员",function () {
                    //退出
                    window.location.href="/logout";
                });
            }
        });
    });

})

/**
 * 动态添加菜单
 * @param data
 */
function getMenus(data) {

    var ul = $("<ul class='layui-nav layui-nav-tree'  lay-filter='test'></ul>")
    for (var i = 0;i<data.length;i++){
        var node = data[i];
        console.log(node)
        var li=$("<li class='layui-nav-item'></li>");
        var a=$("<a class='' href='javascript:;'>"+node.name+"</a>");
        li.append(a)
        //获取children子节点
       var childArry  =  node.childrens;
        if(childArry.length>0){
            var dl=$("<dl class='layui-nav-child'></dl>");
            for (var y in childArry) {
                var dd=$("<dd><a href='"+childArry[y].url+"'>"+childArry[y].name+"</a></dd>");
                //判断选中状态
                if(pathUri.indexOf(childArry[y].url)>0){
                    li.addClass("layui-nav-itemed");
                    dd.addClass("layui-this")
                }
                dl.append(dd);
            }
            li.append(dl);
        }else {
            var dl=$("<dl class='layui-nav-child'></dl>");

                var dd=$("<dd><a href='"+node.url+"'>"+node.name+"</a></dd>");
                //判断选中状态
                if(pathUri.indexOf(node.url)>0){
                    li.addClass("layui-nav-itemed");
                    dd.addClass("layui-this")
                }
                dl.append(dd);

            li.append(dl);
        }
        ul.append(li);
    }
    $(".layui-side-scroll").append(ul);
}

/**
 * 弹出修改密码框
 */
function updateUsePwd() {
    layui.use('layer',function () {
        var layer = layui.layer
        layer.open({
            type:1,
            title: "修改密码",
            fixed:false,
            resize :false,
            shadeClose: true,
            area: ['450px'],
            content:$('#pwdDiv')
        });
    })
    // layer.open({
    //     type:1,
    //     title: "修改密码",
    //     fixed:false,
    //     resize :false,
    //     shadeClose: true,
    //     area: ['450px'],
    //     content:$('#pwdDiv')
    // });
}
