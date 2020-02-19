/**
 * 角色管理
 */
//'formSelects.render('permissions');
// 日期格式处理
layui.use('laytpl', function(){
    var laytpl = layui.laytpl;
    laytpl.toDateString = function(d, format){
        //下面2行替换后在即可实现
        d = d.replace("-", "/");
        d = d.replace("T", " ");

        var date = new Date(d || new Date())
            ,ymd = [
            this.digit(date.getFullYear(), 4)
            ,this.digit(date.getMonth() + 1)
            ,this.digit(date.getDate())
        ]
            ,hms = [
            this.digit(date.getHours())
            ,this.digit(date.getMinutes())
            ,this.digit(date.getSeconds())
        ];

        format = format || 'yyyy-MM-dd HH:mm:ss';

        return format.replace(/yyyy/g, ymd[0])
            .replace(/MM/g, ymd[1])
            .replace(/dd/g, ymd[2])
            .replace(/HH/g, hms[0])
            .replace(/mm/g, hms[1])
            .replace(/ss/g, hms[2]);
    };
    //数字前置补零
   laytpl.digit = function (num, length, end) {
       var str = '';
       num = String(num);
       length = length || 2;
       for(var i = num.length; i < length; i++){
           str += '0';
       }
       return num < Math.pow(10, length) ? str + (num|0) : num;
    };
})

var pageCurr;
var form;
var formSelects;

$(function() {

    layui.use(['table','util'], function(){
        var table = layui.table;
        var util = layui.util
        form = layui.form;
        formSelects =layui.formSelects;

        tableIns=table.render({
            elem: '#adminRoleList',
            url:'/adminRole/getAdminRoleList',
            method: 'post', //默认：get请求
            cellMinWidth: 80,
            page: true,
            request: {
                pageName: 'pageNum', //页码的参数名称，默认：pageNum
                limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
            },
            response:{
                statusName: 'code', //数据状态的字段名称，默认：code
                statusCode: 200, //成功的状态码，默认：0
                countName: 'totals', //数据总数的字段名称，默认：count
                dataName: 'list' //数据列表的字段名称，默认：data
            },
            cols: [[
                {type:'numbers'}
                ,{field:'roleName', title:'角色名称',align:'center'}
                ,{field:'roleDesc', title:'角色描述',align:'center'}
                ,{field:'permissions', title:'权限',align:'center'}
                ,{field:'createTime', title:'创建时间',align:'center'}
                ,{field:'updateTime', title:'更新时间',align:'center'}
                ,{field:'roleStatus', title:'是否有效',align:'center'}
                ,{fixed:'right',title:'操作',align:'center', toolbar:'#optBar'}
            ]],
            done: function(res, curr, count){
                $("[data-field='roleStatus']").children().each(function(){
                    if($(this).text()=='1'){
                        $(this).text("有效")
                    }else if($(this).text()=='0'){
                        $(this).text("失效")
                    }
                });
                pageCurr=curr;

            }
        });


        //监听工具条
        table.on('tool(roleTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                //删除
                delRole(data,data.id);
            } else if(obj.event === 'edit'){
                //编辑
                edit(data);
            }else if(obj.event === 'recover'){
                //恢复
                recoverRole(data,data.id);
            }
        });

        //监听提交
        form.on('submit(roleSubmit)', function(data){
            /* pageCurr=0;*/
            formSubmit(data);
            return false;
        });

    });

});

//提交表单
function formSubmit(obj){
    $.ajax({
        type: "post",
        data: $("#roleForm").serialize(),
        url: "/adminRole/setAdminRole",
        success: function (data) {
            if (data.code == 1) {
                layer.alert(data.message,function(){
                    layer.closeAll();
                    load(obj);
                });
            } else {
                layer.alert(data.message);
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                load(obj);
            });
        }
    });
}

//新增
function add() {
    edit(null,"新增");
}
//打开编辑框
function edit(data,title){
    var pid = null;
    if(data == null){
        $("#id").val("");
    }else{
        //回显数据
        $("#id").val(data.id);
        $("#roleName").val(data.roleName);
        $("#roleDesc").val(data.roleDesc);
    }

        formSelects.data('permissions','server' ,{
            url: '/adminPermission/adminParentPermissionList',
            keyName: 'name',
            keyVal: 'id',
            success: function(id, url, searchVal, result){      //使用远程方式的success回调

                console.log(result);    //返回的结果
            },
            error: function(id, url, searchVal, err){           //使用远程方式的error回调
                                                                //同上
                console.log(err);   //err对象
            },
        });



    var pageNum = $(".layui-laypage-skip").find("input").val();
    $("#pageNum").val(pageNum);

    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px','550px'],
        content:$('#setRole'),
        end:function(){
            cleanRole();
        }
    });
}

//重新加载table
function load(obj){
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

//删除
function delRole(obj,id) {
    if(null!=id){
        layer.confirm('您确定要删除吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/adminRole/updateAdminRoleStatus",{"id":id,"status":0},function(data){
                if (data.code == 1) {
                    layer.alert(data.message,function(){
                        layer.closeAll();
                        load(obj);
                    });
                } else {
                    layer.alert(data.message);
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}
//恢复
function recoverRole(obj,id) {
    if(null!=id){
        layer.confirm('您确定要恢复吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/adminRole/updateAdminRoleStatus",{"id":id,"status":1},function(data){
                if (data.code == 1) {
                    layer.alert(data.message,function(){
                        layer.closeAll();
                        load(obj);
                    });
                } else {
                    layer.alert(data.message);
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}


function cleanRole() {
    $("#roleName").val("");
    $("#roleDesc").val("");
    $("#permissions").val("");
}

